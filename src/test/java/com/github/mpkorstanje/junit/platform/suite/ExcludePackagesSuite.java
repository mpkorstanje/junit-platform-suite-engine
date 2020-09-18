package com.github.mpkorstanje.junit.platform.suite;

import com.github.mpkorstanje.junit.platform.testcases.SimpleTestCase;
import org.junit.platform.suite.api.ExcludePackages;
import org.junit.platform.suite.api.SelectClasses;

@Suite
@ExcludePackages("com.github.mpkorstanje.junit.platform")
@SelectClasses(SimpleTestCase.class)
class ExcludePackagesSuite {

}
