package com.github.mpkorstanje.junit.platform.suite;

import org.junit.platform.engine.EngineExecutionListener;
import org.junit.platform.engine.TestDescriptor;
import org.junit.platform.engine.TestExecutionResult;
import org.junit.platform.engine.UniqueId;
import org.junit.platform.engine.reporting.ReportEntry;
import org.junit.platform.launcher.TestExecutionListener;
import org.junit.platform.launcher.TestIdentifier;

import java.util.HashMap;
import java.util.Map;

final class EngineExecutionListenerAdaptor implements TestExecutionListener {

    private final Map<UniqueId, TestDescriptor> dynamicTests = new HashMap<>();
    private final SuiteTestDescriptor suiteTestDescriptor;
    private final EngineExecutionListener delegate;

    public EngineExecutionListenerAdaptor(
            SuiteTestDescriptor suiteTestDescriptor,
            EngineExecutionListener delegate
    ) {
        this.suiteTestDescriptor = suiteTestDescriptor;
        this.delegate = delegate;
    }

    @Override
    public void dynamicTestRegistered(TestIdentifier testIdentifier) {
        UniqueId testId = UniqueId.parse(testIdentifier.getUniqueId());
        dynamicTests.put(testId, new TestIdentifierAdaptor(suiteTestDescriptor, testIdentifier));
    }

    @Override
    public void executionSkipped(TestIdentifier testIdentifier, String reason) {
        TestDescriptor mappedTest = findTestDescriptor(testIdentifier);
        delegate.executionSkipped(mappedTest, reason);
    }

    @Override
    public void executionStarted(TestIdentifier testIdentifier) {
        TestDescriptor mappedTest = findTestDescriptor(testIdentifier);
        delegate.executionStarted(mappedTest);
    }

    @Override
    public void executionFinished(TestIdentifier testIdentifier, TestExecutionResult testExecutionResult) {
        TestDescriptor mappedTest = findTestDescriptor(testIdentifier);
        delegate.executionFinished(mappedTest, testExecutionResult);
    }

    @Override
    public void reportingEntryPublished(TestIdentifier testIdentifier, ReportEntry entry) {
        TestDescriptor mappedTest = findTestDescriptor(testIdentifier);
        delegate.reportingEntryPublished(mappedTest, entry);
    }

    private TestDescriptor findTestDescriptor(TestIdentifier testIdentifier) {
        UniqueId testId = UniqueId.parse(testIdentifier.getUniqueId());
        UniqueId suiteTestId = suiteTestDescriptor.testInSuite(testId);
        return suiteTestDescriptor.getDescendants().stream()
                .map(TestDescriptor.class::cast)
                .filter(suiteTestDescriptor -> suiteTestId.equals(suiteTestDescriptor.getUniqueId()))
                .findFirst()
                .orElseGet(() -> dynamicTests.get(testId));
    }

}
