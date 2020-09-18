package com.github.mpkorstanje.junit.platform.suite;

import org.junit.platform.engine.UniqueId;
import org.junit.platform.engine.support.descriptor.AbstractTestDescriptor;
import org.junit.platform.launcher.TestIdentifier;

final class JUnitPlatformTestDescriptor extends AbstractTestDescriptor {
    private final TestIdentifier testIdentifier;

    public JUnitPlatformTestDescriptor(UniqueId uniqueId, TestIdentifier testIdentifier) {
        super(
                uniqueId,
                testIdentifier.getDisplayName(),
                testIdentifier.getSource().orElse(null)
        );
        this.testIdentifier = testIdentifier;
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
