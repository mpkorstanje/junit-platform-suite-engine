package com.github.mpkorstanje.junit.platform.suite;

import org.junit.platform.engine.TestDescriptor;
import org.junit.platform.engine.UniqueId;
import org.junit.platform.engine.support.descriptor.AbstractTestDescriptor;
import org.junit.platform.launcher.TestIdentifier;

final class SuiteTestDescriptor extends AbstractTestDescriptor {
    private final TestIdentifier testIdentifier;

    public SuiteTestDescriptor(SuiteEngineDescriptor engineDescriptor, TestIdentifier testIdentifier) {
        super(
                createId(engineDescriptor, testIdentifier),
                testIdentifier.getDisplayName(),
                testIdentifier.getSource().orElse(null)
        );
        this.testIdentifier = testIdentifier;
    }

    static UniqueId createId(SuiteEngineDescriptor engineDescriptor, TestIdentifier testIdentifier) {
        UniqueId testId = UniqueId.parse(testIdentifier.getUniqueId());
        return engineDescriptor.testInSuite(testId);
    }

    @Override
    public void prune() {
        // Don't
    }

    @Override
    public Type getType() {
        if (testIdentifier.isContainer() && testIdentifier.isTest()) {
            return Type.CONTAINER_AND_TEST;
        } else if (testIdentifier.isContainer()) {
            return Type.CONTAINER;
        }
        return Type.TEST;
    }

}
