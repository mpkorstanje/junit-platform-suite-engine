package com.github.mpkorstanje.junit.platform.suite;

import com.github.mpkorstanje.junit.platform.testcases.SimpleTestCase;
import com.github.mpkorstanje.junit.platform.testcases.TaggedTestCase;
import org.junit.platform.suite.api.SelectClasses;


class NestedSelectClassesSuite {

    @Suite
    @SelectClasses(SimpleTestCase.class)
    static class Jupiter{

    }

    @Suite
    @SelectClasses(TaggedTestCase.class)
    static class Tagged{

    }

}
