


import org.jsmart.zerocode.core.domain.LoadWith;
import org.jsmart.zerocode.core.domain.TestMapping;
import org.jsmart.zerocode.core.runner.parallel.ZeroCodeLoadRunner;
import org.jsmart.zerocode.core.runner.parallel.ZeroCodeMultiLoadRunner;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@LoadWith("load_generation.properties")
@TestMapping(testClass = TestLoadHardwareDeviceValidationDao.class, testMethod = "testGetDeviceValidationWithResponse")
//@RunWith(ZeroCodeLoadRunner.class)


@RunWith(ZeroCodeLoadRunner.class)
@ContextConfiguration(classes = TestConfiguration.class)
public class TesLoadRunner {

}
