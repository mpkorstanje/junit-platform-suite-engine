package com.github.mpkorstanje.junit.platform.suite;

import com.github.mpkorstanje.junit.platform.testcases.JupiterTestCase;
import com.github.mpkorstanje.junit.platform.testcases.TaggedTestCase;
import org.junit.platform.suite.api.IncludeTags;
import org.junit.platform.suite.api.SelectClasses;

@Suite
@IncludeTags("test-tag")
@SelectClasses({ JupiterTestCase.class, TaggedTestCase.class})
public class IncludeTagsSuite {

}
