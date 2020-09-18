JUnit Platform Suite Engine
===========================

The `junit-platform-suite-engine` is a test engine that uses the JUnit Platform
Launcher to run suites of tests. This can be useful. For example when running
Cucumber.

See https://github.com/mpkorstanje/junit-platform-suite-demo for a demo.

Where previously you would have to create and run the launcher manually:

```java
public class RunCucumberTest {

    public static void main(String[] args) {
        Launcher launcher = LauncherFactory.create();

        LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder.request()
                .filters(EngineFilter.includeEngines("cucumber"))
                .selectors(DiscoverySelectors.selectClasspathResource("io/cucumber/skeleton"))
                .configurationParameter(GLUE_PROPERTY_NAME, "io.cucumber.skeleton")
                .build();

        launcher.execute(request);
    }
}
```

By using annotations the JUnit platform will execute the test for you:   

```java
@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("io/cucumber/skeleton")
@Configuration(key = GLUE_PROPERTY_NAME, value = "io.cucumber.skeleton")
public class RunCucumberTest {
}
```

Suites also work with multiple engines at the same time:

```java
@Suite
@SelectClasses(JupiterTest.class) // This selector is picked up by Jupiter
@SelectClasspathResource("io/cucumber/skeleton") // This selector is picked up by Cucumber
public class RunSuiteTest {

}
```
