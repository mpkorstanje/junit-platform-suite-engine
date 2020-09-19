package com.github.mpkorstanje.junit.platform.testcases;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIf;

public class Conditional {

    @Test
    @DisabledIf("isTestDisabled")
    void test() {

    }

    boolean isTestDisabled() {
        return true;
    }

}
