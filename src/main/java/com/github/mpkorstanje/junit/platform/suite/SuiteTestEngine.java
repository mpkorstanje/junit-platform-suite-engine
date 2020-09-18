package com.github.mpkorstanje.junit.platform.suite;

import org.apiguardian.api.API;
import org.junit.platform.engine.EngineDiscoveryRequest;
import org.junit.platform.engine.EngineExecutionListener;
import org.junit.platform.engine.ExecutionRequest;
import org.junit.platform.engine.TestDescriptor;
import org.junit.platform.engine.TestEngine;
import org.junit.platform.engine.TestExecutionResult;
import org.junit.platform.engine.UniqueId;
import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.TestExecutionListener;
import org.junit.platform.launcher.TestPlan;

import static org.apiguardian.api.API.Status.INTERNAL;

@API(status = INTERNAL)
public final class SuiteTestEngine implements TestEngine {

    @Override
    public String getId() {
        return SuiteEngineDescriptor.ENGINE_ID;
    }

    @Override
    public TestDescriptor discover(EngineDiscoveryRequest discoveryRequest, UniqueId uniqueId) {
        SuiteEngineDescriptor engineDescriptor = new SuiteEngineDescriptor(uniqueId);
        new DiscoverySelectorResolver().resolveSelectors(discoveryRequest, engineDescriptor);
        return engineDescriptor;
    }

    @Override
    public void execute(ExecutionRequest request) {
        SuiteEngineDescriptor suiteEngineDescriptor = (SuiteEngineDescriptor) request.getRootTestDescriptor();
        EngineExecutionListener engineExecutionListener = request.getEngineExecutionListener();

        engineExecutionListener.executionStarted(suiteEngineDescriptor);

        // TODO: Hierarchical?
        // TODO: Parallel execution?
        suiteEngineDescriptor.getChildren()
                .stream()
                .map(SuiteTestDescriptor.class::cast)
                .forEach(testDescriptor -> {
                    engineExecutionListener.executionStarted(testDescriptor);
                    TestExecutionListener testExecutionListener = new EngineExecutionListenerAdaptor(
                            testDescriptor,
                            engineExecutionListener
                    );
                    Launcher launcher = testDescriptor.getLauncher();
                    TestPlan testPlan = testDescriptor.getTestPlan();
                    launcher.execute(testPlan, testExecutionListener);

                    engineExecutionListener.executionFinished(testDescriptor, TestExecutionResult.successful());
                });

        engineExecutionListener.executionFinished(suiteEngineDescriptor, TestExecutionResult.successful());
    }

}


