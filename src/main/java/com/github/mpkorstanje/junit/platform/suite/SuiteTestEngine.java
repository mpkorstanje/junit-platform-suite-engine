package com.github.mpkorstanje.junit.platform.suite;

import org.apiguardian.api.API;
import org.junit.platform.engine.EngineDiscoveryRequest;
import org.junit.platform.engine.EngineExecutionListener;
import org.junit.platform.engine.ExecutionRequest;
import org.junit.platform.engine.TestDescriptor;
import org.junit.platform.engine.TestEngine;
import org.junit.platform.engine.TestExecutionResult;
import org.junit.platform.engine.UniqueId;
import org.junit.platform.engine.discovery.DiscoverySelectors;
import org.junit.platform.engine.support.descriptor.AbstractTestDescriptor;
import org.junit.platform.launcher.EngineFilter;
import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.TestExecutionListener;
import org.junit.platform.launcher.TestIdentifier;
import org.junit.platform.launcher.TestPlan;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;

import static org.apiguardian.api.API.Status.INTERNAL;

@API(status = INTERNAL)
public final class SuiteTestEngine implements TestEngine {

    @Override
    public String getId() {
        return SuiteEngineDescriptor.ENGINE_ID;
    }

    @Override
    public TestDescriptor discover(EngineDiscoveryRequest discoveryRequest, UniqueId uniqueId) {

        Launcher launcher = LauncherFactory.create();
        LauncherDiscoveryRequest request = new LauncherDiscoveryRequestBuilder()
                .filters(EngineFilter.excludeEngines(SuiteEngineDescriptor.ENGINE_ID)) // Avoid recursion
                .selectors(DiscoverySelectors
                        .selectPackage("com.example")) // TODO: To be read from the class annotated with @Suite
                .build();

        TestPlan testPlan = launcher.discover(request);

        SuiteEngineDescriptor engineDescriptor = new SuiteEngineDescriptor(launcher, testPlan);
        testPlan.getRoots().stream()
                .map(testIdentifier -> createTestDescriptor(engineDescriptor, testIdentifier, testPlan))
                .forEach(testDescriptor -> {
                    engineDescriptor.addChild(testDescriptor);
                    testDescriptor.prune();
                });
        return engineDescriptor;
    }

    private TestDescriptor createTestDescriptor(SuiteEngineDescriptor engineDescriptor, TestIdentifier testIdentifier, TestPlan testPlan) {
        AbstractTestDescriptor testDescriptor = new SuiteTestDescriptor(engineDescriptor, testIdentifier);
        testPlan.getChildren(testIdentifier).stream()
                .map(childIdentifier -> createTestDescriptor(engineDescriptor, childIdentifier, testPlan))
                .forEach(testDescriptor::addChild);
        return testDescriptor;
    }

    @Override
    public void execute(ExecutionRequest request) {
        SuiteEngineDescriptor suiteEngineDescriptor = (SuiteEngineDescriptor) request.getRootTestDescriptor();
        EngineExecutionListener engineExecutionListener = request.getEngineExecutionListener();
        TestExecutionListener testExecutionListener = new EngineExecutionListenerAdaptor(
                suiteEngineDescriptor,
                engineExecutionListener
        );
        engineExecutionListener.executionStarted(suiteEngineDescriptor);
        Launcher launcher = suiteEngineDescriptor.getLauncher();
        TestPlan testPlan = suiteEngineDescriptor.getTestPlan();
        launcher.execute(testPlan, testExecutionListener);
        engineExecutionListener.executionFinished(suiteEngineDescriptor, TestExecutionResult.successful());
    }

}


