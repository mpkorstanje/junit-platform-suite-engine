package com.github.mpkorstanje.junit.platform.suite;

import org.junit.platform.suite.api.IncludePackages;
import org.junit.platform.suite.api.SelectPackages;

@Suite
@IncludePackages("com.github.mpkorstanje.junit.platform.testcases")
@SelectPackages("com.github.mpkorstanje.junit.platform")
class IncludePackagesSuite {

}
