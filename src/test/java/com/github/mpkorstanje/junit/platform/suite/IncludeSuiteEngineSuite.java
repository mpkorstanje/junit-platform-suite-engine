package com.github.mpkorstanje.junit.platform.suite;

import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectPackages;

@Suite
@IncludeEngines(SuiteEngineDescriptor.ENGINE_ID)
@SelectPackages("com.github.mpkorstanje.junit.platform.suite")
class IncludeSuiteEngineSuite {

}
