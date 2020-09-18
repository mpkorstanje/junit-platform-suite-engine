package com.github.mpkorstanje.junit.platform.suite;

import com.github.mpkorstanje.junit.platform.testcases.JupiterTestCase;
import com.github.mpkorstanje.junit.platform.testcases.TaggedTestCase;
import org.junit.platform.suite.api.SelectClasses;


class NestedSelectClassesSuite {

    @Suite
    @SelectClasses(JupiterTestCase.class)
    static class Jupiter{

    }

    @Suite
    @SelectClasses(TaggedTestCase.class)
    static class Tagged{

    }

}
