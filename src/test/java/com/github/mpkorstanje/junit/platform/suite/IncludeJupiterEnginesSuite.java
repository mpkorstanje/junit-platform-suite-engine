package com.github.mpkorstanje.junit.platform.suite;

import com.github.mpkorstanje.junit.platform.testcases.JupiterTestCase;
import org.junit.jupiter.engine.descriptor.JupiterEngineDescriptor;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasses;

@Suite
@IncludeEngines(JupiterEngineDescriptor.ENGINE_ID)
@SelectClasses(JupiterTestCase.class)
public class IncludeJupiterEnginesSuite {

}
