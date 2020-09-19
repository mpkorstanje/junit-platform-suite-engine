package com.github.mpkorstanje.junit.platform.suite;

import com.github.mpkorstanje.junit.platform.testcases.Simple;
import org.junit.platform.suite.api.SelectClasses;


class NestedSelectClassesSuite {

    @Suite
    @SelectClasses(Simple.class)
    static class Jupiter{

    }

    @Suite
    @SelectClasses(com.github.mpkorstanje.junit.platform.testcases.Tagged.class)
    static class Tagged{

    }

}
