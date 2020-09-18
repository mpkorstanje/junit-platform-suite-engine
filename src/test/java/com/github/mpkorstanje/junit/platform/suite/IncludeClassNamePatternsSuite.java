package com.github.mpkorstanje.junit.platform.suite;

import org.junit.platform.suite.api.IncludeClassNamePatterns;
import org.junit.platform.suite.api.SelectPackages;

@Suite
@IncludeClassNamePatterns(".*Simple.*")
@SelectPackages("com.github.mpkorstanje.junit.platform.testcases")
class IncludeClassNamePatternsSuite {

}
