
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.client.RestTemplate;



/**
 *
 * @author A1328535
 *
 *         The Class SpringConfiguration.
 */
@Configuration
@Profile("prod")
public class SpringConfiguration {

	/** The Constant LOGGER. */
	/** The Constant LOGGER. */

	private static final String HTTPS = "https://";

	/** The key store name. */
	@Value("${keystore.name}")
	private String keyStoreName;

	/** The keystore password. */
	@Value("${keystore.password}")
	private String keystorePassword;

	/** The truststore name. */
	@Value("${truststore.name}")
	private String truststoreName;

	/** The truststore password. */
	@Value("${truststore.password}")
	private String truststorePassword;

	/**
	 * Gets the rest template device.
	 *
	 * @return the rest template device
	 */
	@Bean(name = "capService")
	public RestTemplate getRestTemplateDevice() {
		String url = PropertyManager.getProperty("prepaid-device-validation-url");

		if (url.contains(HTTPS)) {
			return new RestTemplate(sslConnectionFactory().getRequestFactory());
		} else {
			return new RestTemplate();
		}
	}

	/**
	 * Ssl connection factory.
	 *
	 * @return the ssl connection factory
	 */
	@Bean(name = "capSslFactory")
	public SSLConnectionFactory sslConnectionFactory() {

		SSLConnectionFactory sSLConnectionFactory = new SSLConnectionFactory.ConnectionFactoryBuilder(
				PropertyManager.getProperty("keyStore.fileName"), PropertyManager.getProperty("keyStore.password"))
						.socketTimeoutSecs(PropertyManager.getIntProperty("webservice.helper.socket.timeout", 150))
						.connectTimeoutSecs(PropertyManager.getIntProperty("webservice.helper.connect.timeout", 150))
						.requestTimeoutSecs(
								PropertyManager.getIntProperty("webservice.helper.connection.request.timeout", 150))
						.build();

		System.setProperty("javax.net.ssl.trustStore",
				PropertyManager.getProperty("secrets.path") + PropertyManager.getProperty("trustStore.fileName"));
		System.setProperty("javax.net.ssl.trustStorePassword", PropertyManager.getProperty("trustStore.password"));

		return sSLConnectionFactory;

	}


}
