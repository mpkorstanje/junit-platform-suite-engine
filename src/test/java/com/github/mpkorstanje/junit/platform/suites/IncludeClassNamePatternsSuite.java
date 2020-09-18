package com.github.mpkorstanje.junit.platform.suites;

import com.github.mpkorstanje.junit.platform.suite.Suite;
import org.junit.platform.suite.api.ExcludeClassNamePatterns;
import org.junit.platform.suite.api.IncludeClassNamePatterns;
import org.junit.platform.suite.api.SelectPackages;

@Suite
@IncludeClassNamePatterns(".*Jupiter.*")
@SelectPackages("com.github.mpkorstanje.junit.platform.testcases")
public class IncludeClassNamePatternsSuite {

}
