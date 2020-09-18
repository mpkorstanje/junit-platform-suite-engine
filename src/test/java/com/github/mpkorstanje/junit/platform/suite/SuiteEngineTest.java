package com.github.mpkorstanje.junit.platform.suite;

import com.github.mpkorstanje.junit.platform.suites.ExcludeClassNamePatternsSuite;
import com.github.mpkorstanje.junit.platform.suites.ExcludeEnginesSuite;
import com.github.mpkorstanje.junit.platform.suites.ExcludeTagsSuite;
import com.github.mpkorstanje.junit.platform.suites.IncludeClassNamePatternsSuite;
import com.github.mpkorstanje.junit.platform.suites.IncludeTagsSuite;
import com.github.mpkorstanje.junit.platform.suites.IncludeEnginesSuite;
import com.github.mpkorstanje.junit.platform.suites.IncludePackagesSuite;
import com.github.mpkorstanje.junit.platform.suites.SelectClassSuite;
import com.github.mpkorstanje.junit.platform.suites.SelectPackageSuite;
import org.junit.jupiter.api.Test;
import org.junit.platform.testkit.engine.EngineTestKit;

import static com.github.mpkorstanje.junit.platform.suite.SuiteEngineDescriptor.ENGINE_ID;
import static org.junit.platform.engine.discovery.DiscoverySelectors.selectClass;
import static org.junit.platform.testkit.engine.EventConditions.event;
import static org.junit.platform.testkit.engine.EventConditions.finishedSuccessfully;
import static org.junit.platform.testkit.engine.EventConditions.test;

class SuiteEngineTest {

    @Test
    void classSelector() {
        EngineTestKit.engine(ENGINE_ID)
                .selectors(selectClass(SelectClassSuite.class))
                .execute()
                .testEvents()
                .assertThatEvents()
                .haveExactly(1, event(test(SelectClassSuite.class.getName()), finishedSuccessfully()));
    }

    @Test
    void packageSelector() {
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
    void includedEngines() {
        EngineTestKit.engine(ENGINE_ID)
                .selectors(selectClass(IncludeEnginesSuite.class))
                .execute()
                .testEvents()
                .assertThatEvents()
                .haveExactly(1, event(test(IncludeEnginesSuite.class.getName()), finishedSuccessfully()));
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

}
