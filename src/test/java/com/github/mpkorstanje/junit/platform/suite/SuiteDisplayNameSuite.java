package com.github.mpkorstanje.junit.platform.suite;

import com.github.mpkorstanje.junit.platform.testcases.SimpleTestCase;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.SuiteDisplayName;

@Suite
@SelectClasses(SimpleTestCase.class)
@SuiteDisplayName("Suite Display Name")
class SuiteDisplayNameSuite {

}
