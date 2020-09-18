package com.github.mpkorstanje.junit.platform.suite;

import com.github.mpkorstanje.junit.platform.testcases.JupiterTestCase;
import org.junit.platform.suite.api.SelectClasses;

@Suite
@SelectClasses(JupiterTestCase.class)
public abstract class SelectAbstractSuite {

}
