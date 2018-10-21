### Why this project is tagged as `less-common` ?
This approach has been set up and used by one of our user in USA(not revealed due to business reason of the Bank). `less-common` has been tagged because, this project actually doesn't use the usual JSON payload/assertions, instead this is a Cassandra application developed using Spring Boot. The `Solution Architect` used his creativity to load/stress test this application much before the app hits SIT/UAT and at the same time he was looking for some useful load/stress reports for analytics purpose. He wanted to do it in the production code base stubbing/virtualizing the dependancies to get the performance benchmaking of the app under different CPU/Memory conditions with reusing some of his existing junit test cases. JUnit runners were the best fit for this situation. Hence with help of him, we developed this Load Runner `ZeroCodeSpringJUnit4Runner`, so that anyone in the simillar situation can reuse this code or further extend it.


# JUnit + Zerocode + Spring example
This demo project exaplins how [JUnit](https://github.com/junit-team/junit4) and [Zerocode](https://github.com/authorjapps/zerocode) test framework based integration-tests for a spring-boot application can make your life easy everyday.

> Keep it simple and easy while doing the load/stress testing

### Where is the Spring Junit test?
The Spring JUnit tests are located here-
+ `src/test/java/org/zerocode/spring/umang/junit/MachineLearningServiceTest.java`

You can run and debug this as JUnit.
  
### Which runner was used for the Spring test ?
See: `ZeroCodeSpringJUnit4Runner.java` (see next section why this was used)
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

### Why this runner was used for the tests instead of JUnit runner or no runner at all ?
With JUnit default runner, the load tests still work fine, but no CSV reports are generated because to generate CSV reports, the ZeroCode framework needs to be aware of the tests. Via `ZeroCodeSpringJUnit4Runner` Zerocode framework listens to the tests and records the result in the CSV file.

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

