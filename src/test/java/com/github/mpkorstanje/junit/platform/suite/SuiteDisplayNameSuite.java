package com.github.mpkorstanje.junit.platform.suite;

import com.github.mpkorstanje.junit.platform.testcases.Simple;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.SuiteDisplayName;

@Suite
@SelectClasses(Simple.class)
@SuiteDisplayName("Suite Display Name")
class SuiteDisplayNameSuite {

}
