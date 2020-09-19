package com.github.mpkorstanje.junit.platform.suite;

import com.github.mpkorstanje.junit.platform.testcases.Simple;
import org.junit.platform.suite.api.SelectClasses;

@Suite
@SelectClasses(Simple.class)
public abstract class SelectAbstractSuite {

}
