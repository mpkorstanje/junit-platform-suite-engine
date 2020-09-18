package com.github.mpkorstanje.junit.platform.suites;

import com.github.mpkorstanje.junit.platform.suite.Suite;
import com.github.mpkorstanje.junit.platform.testcases.JupiterTestCase;
import org.junit.platform.suite.api.SelectClasses;

@Suite
@SelectClasses(JupiterTestCase.class)
public class SelectClassSuite {

}
