package com.github.mpkorstanje.junit.platform.suite;

import com.github.mpkorstanje.junit.platform.testcases.Conditional;
import com.github.mpkorstanje.junit.platform.testcases.Dynamic;
import com.github.mpkorstanje.junit.platform.testcases.Simple;
import com.github.mpkorstanje.junit.platform.testcases.Tagged;
import org.junit.jupiter.api.Test;
import org.junit.platform.engine.discovery.ClassNameFilter;
import org.junit.platform.testkit.engine.EngineTestKit;

import static com.github.mpkorstanje.junit.platform.suite.SuiteEngineDescriptor.ENGINE_ID;
import static org.junit.platform.engine.discovery.DiscoverySelectors.selectClass;
import static org.junit.platform.testkit.engine.EventConditions.container;
import static org.junit.platform.testkit.engine.EventConditions.displayName;
import static org.junit.platform.testkit.engine.EventConditions.event;
import static org.junit.platform.testkit.engine.EventConditions.finishedSuccessfully;
import static org.junit.platform.testkit.engine.EventConditions.test;

class SuiteEngineTest {

    @Test
    void selectClasses() {
        EngineTestKit.engine(ENGINE_ID)
                .selectors(selectClass(SelectClassesSuite.class))
                .execute()
                .testEvents()
                .assertThatEvents()
                .haveExactly(1, event(test(Simple.class.getName()), finishedSuccessfully()));
    }

    @Test
    void selectPackage() {
        EngineTestKit.engine(ENGINE_ID)
                .selectors(selectClass(SelectPackageSuite.class))
                .execute()
                .testEvents()
                .assertThatEvents()
                .haveAtLeastOne(event(test(Simple.class.getName()), finishedSuccessfully()));
    }

    @Test
    void excludeTags() {
        EngineTestKit.engine(ENGINE_ID)
                .selectors(selectClass(ExcludeTagsSuite.class))
                .execute()
                .testEvents()
                .assertThatEvents()
                .haveExactly(1, event(test(Simple.class.getName()), finishedSuccessfully()));
    }

    @Test
    void includeTags() {
        EngineTestKit.engine(ENGINE_ID)
                .selectors(selectClass(IncludeTagsSuite.class))
                .execute()
                .testEvents()
                .assertThatEvents()
                .haveExactly(1, event(test(Tagged.class.getName()), finishedSuccessfully()));
    }

    @Test
    void excludeClassNamePatterns() {
        EngineTestKit.engine(ENGINE_ID)
                .selectors(selectClass(ExcludeClassNamePatternsSuite.class))
                .execute()
                .testEvents()
                .assertThatEvents()
                .haveExactly(1, event(test(Tagged.class.getName()), finishedSuccessfully()));
    }

    @Test
    void includeClassNamePatterns() {
        EngineTestKit.engine(ENGINE_ID)
                .selectors(selectClass(IncludeClassNamePatternsSuite.class))
                .execute()
                .testEvents()
                .assertThatEvents()
                .haveExactly(1, event(test(Simple.class.getName()), finishedSuccessfully()));
    }

    @Test
    void excludeEngines() {
        EngineTestKit.engine(ENGINE_ID)
                .selectors(selectClass(ExcludeEnginesSuite.class))
                .execute()
                .testEvents()
                .assertThatEvents()
                .isEmpty();
    }

    @Test
    void includeJupiterEngines() {
        EngineTestKit.engine(ENGINE_ID)
                .selectors(selectClass(IncludeJupiterEnginesSuite.class))
                .execute()
                .testEvents()
                .assertThatEvents()
                .haveExactly(1, event(test(Simple.class.getName()), finishedSuccessfully()));
    }

    @Test
    void includeSuiteEngine() {
        EngineTestKit.engine(ENGINE_ID)
                .selectors(selectClass(IncludeSuiteEngineSuite.class))
                .execute()
                .testEvents()
                .assertThatEvents()
                .isEmpty();
    }

    @Test
    void excludePackages() {
        EngineTestKit.engine(ENGINE_ID)
                .selectors(selectClass(ExcludeEnginesSuite.class))
                .execute()
                .testEvents()
                .assertThatEvents()
                .isEmpty();
    }

    @Test
    void includePackages() {
        EngineTestKit.engine(ENGINE_ID)
                .selectors(selectClass(IncludePackagesSuite.class))
                .execute()
                .testEvents()
                .assertThatEvents()
                .haveAtLeastOne(event(test(Simple.class.getName()), finishedSuccessfully()));
    }

    @Test
    void suiteDisplayName() {
        EngineTestKit.engine(ENGINE_ID)
                .selectors(selectClass(SuiteDisplayNameSuite.class))
                .execute()
                .allEvents()
                .assertThatEvents()
                .haveExactly(1, event(container(displayName("Suite Display Name")), finishedSuccessfully()));
    }

    @Test
    void selectAbstract() {
        EngineTestKit.engine(ENGINE_ID)
                .selectors(selectClass(SelectAbstractSuite.class))
                .execute()
                .testEvents()
                .assertThatEvents()
                .isEmpty();
    }

    @Test
    void nestedSelectClasses() {
        EngineTestKit.engine(ENGINE_ID)
                .selectors(selectClass(NestedSelectClassesSuite.class))
                .execute()
                .testEvents()
                .assertThatEvents()
                .isEmpty();
    }

    @Test
    void filterClasses() {
        EngineTestKit.engine(ENGINE_ID)
                .selectors(selectClass(SelectClassesSuite.class), selectClass(SelectPackageSuite.class))
                .filters(ClassNameFilter.excludeClassNamePatterns(SelectPackageSuite.class.getName()))
                .execute()
                .testEvents()
                .assertThatEvents()
                .haveExactly(1, event(test(Simple.class.getName()), finishedSuccessfully()));
    }

    @Test
    void configuration() {
        EngineTestKit.engine(ENGINE_ID)
                .selectors(selectClass(ConfigurationSuite.class))
                .execute()
                .testEvents()
                .assertThatEvents()
                .haveExactly(1, event(test(Conditional.class.getName()), finishedSuccessfully()));
    }

    @Test
    void dynamicSuite() {
        EngineTestKit.engine(ENGINE_ID)
                .selectors(selectClass(DynamicSuite.class))
                .execute()
                .testEvents()
                .assertThatEvents()
                .haveExactly(2, event(test(Dynamic.class.getName()), finishedSuccessfully()));
    }

}
