package com.github.mpkorstanje.junit.platform.suite;

import org.junit.jupiter.api.Test;
import org.junit.platform.testkit.engine.EngineTestKit;

import java.io.OutputStreamWriter;

import static org.junit.platform.engine.discovery.DiscoverySelectors.selectClass;

class SuiteEngineTest {

    @Test
    void test(){
        EngineTestKit.engine("suite")
                .selectors(selectClass(ExampleTestCase.class))
                .execute()
                .allEvents()
                .debug(new OutputStreamWriter(System.out));
    }
}
