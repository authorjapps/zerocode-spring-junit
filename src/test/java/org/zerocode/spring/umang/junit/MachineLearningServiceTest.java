package org.zerocode.spring.umang.junit;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.hamcrest.CoreMatchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.zerocode.spring.umang.AppConfig;
import org.zerocode.spring.umang.DataModelService;
import org.zerocode.spring.umang.MachineLearningService;
import org.zerocode.spring.umang.runner.ZeroCodeSpringJUnit4Runner;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

@RunWith(ZeroCodeSpringJUnit4Runner.class)
@ContextConfiguration(classes = {AppConfig.class})
public class MachineLearningServiceTest {

    //DI
    @Autowired
    @Qualifier("ml")
    DataModelService ml;

    @Test
    public void testGetCarrierCharges() {

        //assert correct type/impl
        assertThat(ml, instanceOf(MachineLearningService.class));

        //assert true
        assertThat(ml.isValid(""), is(true));

    }

    @Test
    public void testGitHubGetApi() throws IOException, InterruptedException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet request = new HttpGet("https://api.github.com//users/octocat");

        HttpResponse response = httpClient.execute(request);
        final String responseBodyActual = IOUtils.toString(response.getEntity().getContent(), "UTF-8");
        System.out.println("### response: \n" + responseBodyActual);

        assertThat(response.getStatusLine().getStatusCode(), CoreMatchers.is(200));
        assertThat(responseBodyActual, CoreMatchers.containsString("\"login\":\"octocat\""));

    }
}