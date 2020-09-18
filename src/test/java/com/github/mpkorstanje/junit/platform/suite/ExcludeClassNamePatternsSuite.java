package com.github.mpkorstanje.junit.platform.suite;

import org.junit.platform.suite.api.ExcludeClassNamePatterns;
import org.junit.platform.suite.api.SelectPackages;

@Suite
@ExcludeClassNamePatterns(".*Jupiter.*")
@SelectPackages("com.github.mpkorstanje.junit.platform.testcases")
public class ExcludeClassNamePatternsSuite {

}
