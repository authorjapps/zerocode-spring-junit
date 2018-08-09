# JUnit + Zerocode + Spring example
This demo project exaplins how [JUnit](https://github.com/junit-team/junit4) and [Zerocode](https://github.com/authorjapps/zerocode) test framework based integration-tests for a spring-boot application can make your life easy everyday.

> Keep it simple and easy while doing the load/stress testing

### Where is the Spring Junit test?
The Spring JUnit tests are located here-
+ `src/test/java/org/zerocode/spring/umang/junit/MachineLearningServiceTest.java`

You can run and debug this as JUnit.
  
### Which runner was used for the Spring test ?
The below runner was used -
+ `ZeroCodeSpringJUnit4Runner.class` , 
See below- 
```java
@RunWith(ZeroCodeSpringJUnit4Runner.class)
@ContextConfiguration(classes = {AppConfig.class})
public class MachineLearningServiceTest {
    ...
}
```

You can run and debug this as JUnit.

### Which runner was used for Load testing ?
The below runner -
+ `ZeroCodeLoadRunner.class`

See usage example below-
```java
@LoadWith("load_generation.properties")
@TestMapping(testClass = MachineLearningServiceTest.class, testMethod = "testGetCarrierCharges")
@RunWith(ZeroCodeLoadRunner.class)
public class TestLoadRunner {

}
```

### Where are the load reports generated after the load tests ?
The reports are generated under target as below -
+ `target/zerocode-junit-granular-report.csv`
  + Useful for load tests statistics
+ `target/zerocode-junit-interactive-fuzzy-search.html`
  + Useful for general purpose
