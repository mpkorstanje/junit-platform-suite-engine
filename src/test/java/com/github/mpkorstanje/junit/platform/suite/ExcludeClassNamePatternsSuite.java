package com.github.mpkorstanje.junit.platform.suite;

import org.junit.platform.suite.api.ExcludeClassNamePatterns;
import org.junit.platform.suite.api.SelectPackages;

@Suite
@ExcludeClassNamePatterns({".*Simple.*"})
@SelectPackages("com.github.mpkorstanje.junit.platform.testcases")
class ExcludeClassNamePatternsSuite {

}
