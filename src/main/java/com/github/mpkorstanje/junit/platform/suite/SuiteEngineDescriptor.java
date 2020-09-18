package com.github.mpkorstanje.junit.platform.suite;

import org.junit.platform.engine.UniqueId;
import org.junit.platform.engine.support.descriptor.EngineDescriptor;

class SuiteEngineDescriptor extends EngineDescriptor {

    static final String ENGINE_ID = "suite";

    SuiteEngineDescriptor(UniqueId uniqueId) {
        super(uniqueId, "Suite");
    }

    @Override
    public Type getType() {
        return Type.CONTAINER;
    }

}
