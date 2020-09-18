package com.github.mpkorstanje.junit.platform.suite;

import com.github.mpkorstanje.junit.platform.testcases.JupiterTestCase;
import org.junit.platform.suite.api.IncludePackages;
import org.junit.platform.suite.api.SelectClasses;

@Suite
@IncludePackages("com.github.mpkorstanje.junit.platform")
@SelectClasses(JupiterTestCase.class)
class IncludePackagesSuite {

}
