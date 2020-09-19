package com.github.mpkorstanje.junit.platform.suite;

import com.github.mpkorstanje.junit.platform.testcases.Conditional;
import org.junit.platform.suite.api.SelectClasses;

import static org.junit.jupiter.engine.Constants.DEACTIVATE_ALL_CONDITIONS_PATTERN;
import static org.junit.jupiter.engine.Constants.DEACTIVATE_CONDITIONS_PATTERN_PROPERTY_NAME;

@Suite
@SelectClasses(Conditional.class)
@Configuration(key = DEACTIVATE_CONDITIONS_PATTERN_PROPERTY_NAME, value = DEACTIVATE_ALL_CONDITIONS_PATTERN)
class ConfigurationSuite {

}
