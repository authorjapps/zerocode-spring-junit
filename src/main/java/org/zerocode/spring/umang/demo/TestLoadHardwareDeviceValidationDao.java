
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.jayway.restassured.module.mockmvc.RestAssuredMockMvc;
import org.apache.cassandra.exceptions.ConfigurationException;
import org.apache.thrift.transport.TTransportException;
import org.cassandraunit.utils.EmbeddedCassandraServerHelper;
import org.jsmart.zerocode.core.domain.LoadWith;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.data.cassandra.config.CassandraSessionFactoryBean;
import org.springframework.data.cassandra.core.CassandraAdminOperations;
import org.springframework.data.cassandra.core.CassandraAdminTemplate;
import org.springframework.data.cassandra.core.convert.MappingCassandraConverter;
import org.springframework.data.cassandra.core.cql.CqlIdentifier;
import org.springframework.data.cassandra.core.mapping.CassandraMappingContext;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.jayway.restassured.module.mockmvc.config.AsyncConfig.withTimeout;
import static org.junit.Assert.*;

/**
 * @author A1328535
 *
 */


@ActiveProfiles("unit-test")
@RunWith(ZeroCodeSpringJUnit4Runner.class)
@ContextConfiguration(classes = TestConfiguration.class)
@WebAppConfiguration
public class TestLoadHardwareDeviceValidationDao {

	/** The rest template. */
	@Mock
	private RestTemplate restTemplate;

	/** The context. */
	@Autowired
	private WebApplicationContext context;

	/** The prod details from magellan DAO. */
	@Autowired
	@InjectMocks
	private HardwareDeviceValidationDao hardwareDeviceValidationDao;

	/** The thrown. */
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	@Mock
	private CacheManager cacheManager;

	private static final String ACT_TYPE = "NEW";

	private static final String KEYSPACE_CREATION_QUERY = "CREATE KEYSPACE IF NOT EXISTS test WITH replication = { 'class': 'SimpleStrategy', 'replication_factor': '3' };";

	private static final String KEYSPACE_ACTIVATE_QUERY = "USE activationpackage;";

	private static final String DATA_TABLE_NAME = "activation_lines";
	private static final String DATA_BASE_TABLE_NAME = "activation_packages";
	@Autowired
	private CassandraAdminOperations adminTemplate;


	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		RestAssuredMockMvc.webAppContextSetup(context);
		RestAssuredMockMvc.config().asyncConfig(withTimeout(10, TimeUnit.SECONDS));
	}




	//@Before
	public void createTable() throws ConfigurationException {

		adminTemplate.getCqlOperations().execute(KEYSPACE_ACTIVATE_QUERY);

		adminTemplate.createTable(true, CqlIdentifier.cqlId(DATA_TABLE_NAME), ActivationLines.class, new HashMap<>());
		adminTemplate.createTable(true, CqlIdentifier.cqlId(DATA_BASE_TABLE_NAME), ActivationPackageEntity.class, new HashMap<>());

		ActivationPackageEntity package1 = new ActivationPackageEntity("007012340404201737486454", null,
				"0070-W234-04042017-37486454", line);


		adminTemplate.insert(lines);

		assertEquals(4, adminTemplate.count(ActivationLines.class));
	}



	@SuppressWarnings("unchecked")
	@Test
	public void testGetDeviceValidationWithResponse() {

			createTable();
			System.out.println(System.currentTimeMillis());
			HardwareGet payload = getPayload(true);
			MultiValueMap<String, String> requestHeader = getHeaders();
			DeviceValidationResponse deviceDetailsResponse = getMockDeviceValidation("PASS", false);

			ResponseEntity<DeviceValidationResponse> deviceDetailsEntity = ResponseEntity.ok(deviceDetailsResponse);




			DelayStubber.doSleep(Duration.ofSeconds(5),deviceDetailsEntity).when(restTemplate).exchange(Mockito.anyString(),
					Mockito.any(), Mockito.any(), Mockito.any(Class.class));
			DeviceValidationResponse response = hardwareDeviceValidationDao.getDeviceValidationResponse(payload,
					requestHeader, ACT_TYPE);
			System.out.println(System.currentTimeMillis());
			assertNotNull(response);
			assertEquals(response.getDeviceDetails().get(0).getValidationStatus(), "PASS");


	}




	
}
