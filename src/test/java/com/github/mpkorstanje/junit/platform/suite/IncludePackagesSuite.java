package com.github.mpkorstanje.junit.platform.suite;

import com.github.mpkorstanje.junit.platform.testcases.SimpleTestCase;
import org.junit.platform.suite.api.IncludePackages;
import org.junit.platform.suite.api.SelectClasses;

@Suite
@IncludePackages("com.github.mpkorstanje.junit.platform")
@SelectClasses(SimpleTestCase.class)
class IncludePackagesSuite {

}
