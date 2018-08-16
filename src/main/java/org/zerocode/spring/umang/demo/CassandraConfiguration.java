

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;



import com.datastax.driver.core.ConsistencyLevel;
import com.datastax.driver.core.QueryOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.cassandra.config.CassandraClusterFactoryBean;
import org.springframework.data.cassandra.config.CassandraSessionFactoryBean;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.data.cassandra.core.convert.CassandraConverter;
import org.springframework.data.cassandra.core.convert.MappingCassandraConverter;
import org.springframework.data.cassandra.core.mapping.CassandraMappingContext;

/**
 *
 * @version 1.0
 * @since 2017-04-05
 *
 *        This class contains configuration details of cassandra Database
 */
@Configuration
@Profile("prod")
public class CassandraConfiguration {




    @Bean
    public CassandraClusterFactoryBean cluster() {
        CassandraClusterFactoryBean cluster = new CassandraClusterFactoryBean();
        cluster.setContactPoints(PropertyManager.getProperty("cassandra.contactpoints"));
        cluster.setPort(Integer.valueOf(PropertyManager.getProperty("cassandra.port")));
        cluster.setUsername(PropertyManager.getProperty("cassandra.username"));
        cluster.setPassword(PropertyManager.getProperty("cassandra.password"));
        cluster.setQueryOptions((new QueryOptions()).setConsistencyLevel(ConsistencyLevel.LOCAL_QUORUM));
        return cluster;
    }

    @Bean
    public CassandraMappingContext mappingContext() {
        return new CassandraMappingContext();
    }

    @Bean
    public CassandraConverter converter() {
        return new MappingCassandraConverter(this.mappingContext());
    }

    @Bean
    public CassandraSessionFactoryBean session() {
        CassandraSessionFactoryBean session = new CassandraSessionFactoryBean();
        session.setCluster(this.cluster().getObject());
        session.setKeyspaceName(PropertyManager.getProperty("cassandra.keyspace"));
        session.setConverter(this.converter());
        session.setSchemaAction(SchemaAction.NONE);
        return session;
    }

    @Bean
    public CassandraOperations cassandraTemplate() {
        LOGGER.info("::Creating the Connection::");
        return new CassandraTemplate(this.session().getObject());
    }

}
