package com.github.mpkorstanje.junit.platform.suite;

import org.junit.platform.commons.util.AnnotationUtils;
import org.junit.platform.commons.util.StringUtils;
import org.junit.platform.engine.UniqueId;
import org.junit.platform.engine.UniqueId.Segment;
import org.junit.platform.engine.support.descriptor.AbstractTestDescriptor;
import org.junit.platform.engine.support.descriptor.ClassSource;
import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.TestIdentifier;
import org.junit.platform.launcher.TestPlan;
import org.junit.platform.suite.api.SuiteDisplayName;

final class SuiteTestDescriptor extends AbstractTestDescriptor {

    static final String SEGMENT_TYPE = "suite";

    private final Launcher launcher;
    private final TestPlan testPlan;

    public SuiteTestDescriptor(UniqueId id,
            Class<?> testClass,
            TestPlan testPlan,
            Launcher launcher) {
        super(id, getSuiteDisplayName(testClass), ClassSource.from(testClass));
        this.testPlan = testPlan;
        this.launcher = launcher;
    }

    private static String getSuiteDisplayName(Class<?> testClass) {
        return AnnotationUtils.findAnnotation(testClass, SuiteDisplayName.class)
                .map(SuiteDisplayName::value)
                .filter(StringUtils::isNotBlank)
                .orElse(testClass.getName());
    }

    UniqueId uniqueIdInSuite(TestIdentifier testDescriptor) {
        UniqueId testId = UniqueId.parse(testDescriptor.getUniqueId());
        UniqueId idInSuite = getUniqueId();
        for (Segment segment : testId.getSegments()) {
            idInSuite = idInSuite.append(segment);
        }
        return idInSuite;
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
