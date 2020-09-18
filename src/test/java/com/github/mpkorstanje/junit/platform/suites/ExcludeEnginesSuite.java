package com.github.mpkorstanje.junit.platform.suites;

import com.github.mpkorstanje.junit.platform.suite.Suite;
import com.github.mpkorstanje.junit.platform.testcases.JupiterTestCase;
import com.github.mpkorstanje.junit.platform.testcases.TaggedTestCase;
import org.junit.jupiter.engine.descriptor.JupiterEngineDescriptor;
import org.junit.platform.suite.api.ExcludeEngines;
import org.junit.platform.suite.api.ExcludeTags;
import org.junit.platform.suite.api.SelectClasses;

@Suite
@ExcludeEngines(JupiterEngineDescriptor.ENGINE_ID)
@SelectClasses(JupiterTestCase.class)
public class ExcludeEnginesSuite {

}
