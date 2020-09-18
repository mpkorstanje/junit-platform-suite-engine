package com.github.mpkorstanje.junit.platform.suite;

import org.junit.platform.engine.UniqueId;
import org.junit.platform.engine.support.descriptor.AbstractTestDescriptor;
import org.junit.platform.launcher.TestIdentifier;

final class TestIdentifierAdaptor extends AbstractTestDescriptor {
    private final TestIdentifier testIdentifier;

    public TestIdentifierAdaptor(SuiteTestDescriptor suiteTestDescriptor, TestIdentifier testIdentifier) {
        super(
                createId(suiteTestDescriptor, testIdentifier),
                testIdentifier.getDisplayName(),
                testIdentifier.getSource().orElse(null)
        );
        this.testIdentifier = testIdentifier;
    }

    static UniqueId createId(SuiteTestDescriptor suiteTestDescriptor, TestIdentifier testIdentifier) {
        UniqueId testId = UniqueId.parse(testIdentifier.getUniqueId());
        return suiteTestDescriptor.testInSuite(testId);
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
