package com.github.mpkorstanje.junit.platform.suite;

import org.junit.jupiter.api.Test;
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
                .haveExactly(1, event(test(SelectClassesSuite.class.getName()), finishedSuccessfully()));
    }

    @Test
    void selectPackage() {
        EngineTestKit.engine(ENGINE_ID)
                .selectors(selectClass(SelectPackageSuite.class))
                .execute()
                .testEvents()
                .assertThatEvents()
                .haveAtLeastOne(event(test(SelectPackageSuite.class.getName()), finishedSuccessfully()));
    }

    @Test
    void excludeTags() {
        EngineTestKit.engine(ENGINE_ID)
                .selectors(selectClass(ExcludeTagsSuite.class))
                .execute()
                .testEvents()
                .assertThatEvents()
                .haveExactly(1, event(test(ExcludeTagsSuite.class.getName()), finishedSuccessfully()));
    }

    @Test
    void includeTags() {
        EngineTestKit.engine(ENGINE_ID)
                .selectors(selectClass(IncludeTagsSuite.class))
                .execute()
                .testEvents()
                .assertThatEvents()
                .haveExactly(1, event(test(IncludeTagsSuite.class.getName()), finishedSuccessfully()));
    }

    @Test
    void excludeClassNamePatterns() {
        EngineTestKit.engine(ENGINE_ID)
                .selectors(selectClass(ExcludeClassNamePatternsSuite.class))
                .execute()
                .testEvents()
                .assertThatEvents()
                .haveExactly(1, event(test(ExcludeClassNamePatternsSuite.class.getName()), finishedSuccessfully()));
    }

    @Test
    void includeClassNamePatterns() {
        EngineTestKit.engine(ENGINE_ID)
                .selectors(selectClass(IncludeClassNamePatternsSuite.class))
                .execute()
                .testEvents()
                .assertThatEvents()
                .haveExactly(1, event(test(IncludeClassNamePatternsSuite.class.getName()), finishedSuccessfully()));
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
                .haveExactly(1, event(test(IncludeJupiterEnginesSuite.class.getName()), finishedSuccessfully()));
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
                .haveExactly(1, event(test(IncludePackagesSuite.class.getName()), finishedSuccessfully()));
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

}
