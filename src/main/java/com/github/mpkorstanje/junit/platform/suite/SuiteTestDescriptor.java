package com.github.mpkorstanje.junit.platform.suite;

import org.junit.platform.engine.UniqueId;
import org.junit.platform.engine.UniqueId.Segment;
import org.junit.platform.engine.support.descriptor.AbstractTestDescriptor;
import org.junit.platform.engine.support.descriptor.ClassSource;
import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.TestPlan;

final class SuiteTestDescriptor extends AbstractTestDescriptor {

    static final String SEGMENT_TYPE = "suite";

    private final Launcher launcher;
    private final TestPlan testPlan;

    public SuiteTestDescriptor(UniqueId id,
            Class<?> testClass,
            TestPlan testPlan,
            Launcher launcher) {
        super(id, testClass.getSimpleName(), ClassSource.from(testClass));
        this.testPlan = testPlan;
        this.launcher = launcher;
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
