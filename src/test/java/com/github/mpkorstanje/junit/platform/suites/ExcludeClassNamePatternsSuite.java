package com.github.mpkorstanje.junit.platform.suites;

import com.github.mpkorstanje.junit.platform.suite.Suite;
import com.github.mpkorstanje.junit.platform.testcases.JupiterTestCase;
import com.github.mpkorstanje.junit.platform.testcases.TaggedTestCase;
import org.junit.platform.suite.api.ExcludeClassNamePatterns;
import org.junit.platform.suite.api.ExcludeTags;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.SelectPackages;

@Suite
@ExcludeClassNamePatterns(".*Jupiter.*")
@SelectPackages("com.github.mpkorstanje.junit.platform.testcases")
public class ExcludeClassNamePatternsSuite {

}
