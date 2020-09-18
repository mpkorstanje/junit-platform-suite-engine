package com.github.mpkorstanje.junit.platform.suite;

import com.github.mpkorstanje.junit.platform.testcases.SimpleTestCase;
import org.junit.jupiter.engine.descriptor.JupiterEngineDescriptor;
import org.junit.platform.suite.api.ExcludeEngines;
import org.junit.platform.suite.api.SelectClasses;

@Suite
@ExcludeEngines(JupiterEngineDescriptor.ENGINE_ID)
@SelectClasses(SimpleTestCase.class)
class ExcludeEnginesSuite {

}
