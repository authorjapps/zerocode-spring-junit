package org.zerocode.spring.umang.junit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.zerocode.spring.umang.AppConfig;
import org.zerocode.spring.umang.DataModelService;
import org.zerocode.spring.umang.MachineLearningService;
import org.zerocode.spring.umang.runner.ZeroCodeSpringJUnit4Runner;

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
}