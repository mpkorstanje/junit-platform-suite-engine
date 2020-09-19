package com.github.mpkorstanje.junit.platform.suite;

import com.github.mpkorstanje.junit.platform.testcases.Simple;
import org.junit.platform.suite.api.ExcludePackages;
import org.junit.platform.suite.api.SelectClasses;

@Suite
@ExcludePackages("com.github.mpkorstanje.junit.platform")
@SelectClasses(Simple.class)
class ExcludePackagesSuite {

}
