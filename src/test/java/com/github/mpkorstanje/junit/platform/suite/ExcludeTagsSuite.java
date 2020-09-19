package com.github.mpkorstanje.junit.platform.suite;

import com.github.mpkorstanje.junit.platform.testcases.Simple;
import com.github.mpkorstanje.junit.platform.testcases.Tagged;
import org.junit.platform.suite.api.ExcludeTags;
import org.junit.platform.suite.api.SelectClasses;

@Suite
@ExcludeTags("test-tag")
@SelectClasses({ Simple.class, Tagged.class})
class ExcludeTagsSuite {

}
