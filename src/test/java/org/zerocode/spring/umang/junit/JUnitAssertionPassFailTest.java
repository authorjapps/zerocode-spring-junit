package org.zerocode.spring.umang.junit;

import org.hamcrest.core.Is;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.zerocode.spring.umang.runner.ZeroCodeSpringJUnit4Runner;

import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(ZeroCodeSpringJUnit4Runner.class)
public class JUnitAssertionPassFailTest {
    static int a = 2;
    static int b = 3;

    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    // 1st run passes, but subsequent run fails,  this was done deliberately
    // to see the failures reflect in the CSV report
    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    @Test
    public void testAddTwoNumbers_firstPassThenFail() {
        int sum = a + b;
        assertThat(sum, Is.is(5));

        a++;
        b++;
    }
}
