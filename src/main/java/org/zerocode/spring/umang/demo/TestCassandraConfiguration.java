

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import org.apache.thrift.transport.TTransportException;
import org.cassandraunit.utils.EmbeddedCassandraServerHelper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.cassandra.config.CassandraClusterFactoryBean;
import org.springframework.data.cassandra.config.CassandraSessionFactoryBean;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.core.CassandraAdminOperations;
import org.springframework.data.cassandra.core.CassandraAdminTemplate;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.data.cassandra.core.convert.CassandraConverter;
import org.springframework.data.cassandra.core.convert.MappingCassandraConverter;
import org.springframework.data.cassandra.core.mapping.CassandraMappingContext;

import java.io.IOException;

/**
 * @author A1330716
 * @version 1.0
 * @since 04/06/2017
 *
 * This class contains configuration details of  Embedded cassandra Database for JUnit
 */
@Configuration
@Profile("unit-test")

public class TestCassandraConfiguration {

	private static final String KEYSPACE_CREATION_QUERY = "CREATE KEYSPACE IF NOT EXISTS activationpackage WITH replication = { 'class': 'SimpleStrategy', 'replication_factor': '3' };";

	private static final String KEYSPACE_ACTIVATE_QUERY = "USE activationpackage;";

	@Bean
	public CassandraClusterFactoryBean cluster() {
		final CassandraClusterFactoryBean cluster = new CassandraClusterFactoryBean();
		cluster.setContactPoints("127.0.0.1");
		cluster.setPort(9142);
		return cluster;
	}

	@Bean
	public CassandraMappingContext mappingContext() {
		return new CassandraMappingContext();
	}

	@Bean
	public CassandraConverter converter() {
		return new MappingCassandraConverter(mappingContext());
	}

	@Bean
	public Session session() {
		try {
			EmbeddedCassandraServerHelper.startEmbeddedCassandra(2000000000L);
		} catch (TTransportException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		final Cluster cluster = Cluster.builder().addContactPoints("127.0.0.1").withPort(9142).build();
		final Session session = cluster.connect();
		//CassandraSessionFactoryBean session = new CassandraSessionFactoryBean();
		//session.setCluster(cluster);
		//session.setKeyspaceName("activationpackage");
		session.execute(KEYSPACE_CREATION_QUERY);
		session.execute(KEYSPACE_ACTIVATE_QUERY);



		return session;
	}

	@Bean
	public CassandraOperations cassandraTemplate() {
		return new CassandraTemplate(session());
	}

	@Bean
	public CassandraAdminOperations cassandraAdminTemplate() {
		return new CassandraAdminTemplate(session(), converter());
	}

}
