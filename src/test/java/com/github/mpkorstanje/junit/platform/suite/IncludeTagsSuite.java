package com.github.mpkorstanje.junit.platform.suite;

import com.github.mpkorstanje.junit.platform.testcases.Simple;
import com.github.mpkorstanje.junit.platform.testcases.Tagged;
import org.junit.platform.suite.api.IncludeTags;
import org.junit.platform.suite.api.SelectClasses;

@Suite
@IncludeTags("test-tag")
@SelectClasses({ Simple.class, Tagged.class})
class IncludeTagsSuite {

}
