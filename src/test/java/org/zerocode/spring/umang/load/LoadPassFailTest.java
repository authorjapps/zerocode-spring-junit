package org.zerocode.spring.umang.load;


import org.jsmart.zerocode.core.domain.LoadWith;
import org.jsmart.zerocode.core.domain.TestMapping;
import org.jsmart.zerocode.core.runner.parallel.ZeroCodeLoadRunner;
import org.junit.runner.RunWith;
import org.zerocode.spring.umang.junit.JUnitAssertionPassFailTest;
import org.zerocode.spring.umang.junit.MachineLearningServiceTest;

@LoadWith("load_generation.properties")
@TestMapping(testClass = JUnitAssertionPassFailTest.class, testMethod = "testAddTwoNumbers_firstPassThenFail")
@RunWith(ZeroCodeLoadRunner.class)
public class LoadPassFailTest {

}
