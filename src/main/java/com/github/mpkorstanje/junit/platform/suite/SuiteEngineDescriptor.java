package com.github.mpkorstanje.junit.platform.suite;

import org.junit.platform.engine.UniqueId;
import org.junit.platform.engine.UniqueId.Segment;
import org.junit.platform.engine.support.descriptor.EngineDescriptor;
import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.TestPlan;

class SuiteEngineDescriptor extends EngineDescriptor {

    static final String ENGINE_ID = "suite";
    private final Launcher launcher;
    private final TestPlan testPlan;

    SuiteEngineDescriptor(Launcher launcher, TestPlan testPlan) {
        super(UniqueId.forEngine(ENGINE_ID), "Suite");
        this.launcher = launcher;
        this.testPlan = testPlan;
    }

    UniqueId testInSuite(UniqueId originalTestId) {
        UniqueId uniqueId = getUniqueId();
        for (Segment segment : originalTestId.getSegments()) {
            uniqueId = uniqueId.append(segment);
        }
        return uniqueId;
    }

    @Override
    public Type getType() {
        return Type.CONTAINER;
    }

    TestPlan getTestPlan() {
        return testPlan;
    }

    Launcher getLauncher() {
        return launcher;
    }

}
