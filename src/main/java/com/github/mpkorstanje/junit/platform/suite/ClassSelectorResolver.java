package com.github.mpkorstanje.junit.platform.suite;

import org.junit.platform.engine.TestDescriptor;
import org.junit.platform.engine.discovery.ClassSelector;
import org.junit.platform.engine.discovery.DiscoverySelectors;
import org.junit.platform.engine.support.descriptor.AbstractTestDescriptor;
import org.junit.platform.engine.support.discovery.SelectorResolver;
import org.junit.platform.launcher.EngineFilter;
import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.TestIdentifier;
import org.junit.platform.launcher.TestPlan;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;

import java.util.Optional;

class ClassSelectorResolver implements SelectorResolver {
    @Override
    public Resolution resolve(ClassSelector selector, Context context) {
        Class<?> javaClass = selector.getJavaClass();
        Launcher launcher = LauncherFactory.create();
        LauncherDiscoveryRequest launcherDiscoveryRequest = new LauncherDiscoveryRequestBuilder()
                .filters(EngineFilter.excludeEngines(SuiteEngineDescriptor.ENGINE_ID)) // Avoid recursion
                .selectors(DiscoverySelectors
                        .selectPackage(
                                "com.example")) // TODO: To be read from the class annotated with @Suite
                .build();
        TestPlan testPlan = launcher.discover(launcherDiscoveryRequest);
        Optional<SuiteTestDescriptor> suite = context.addToParent(parent -> {
            SuiteTestDescriptor suiteTestDescriptor = new SuiteTestDescriptor(
                    parent.getUniqueId().append(SuiteTestDescriptor.SEGMENT_TYPE, javaClass.getName()),
                    javaClass,
                    testPlan,
                    launcher
            );
            testPlan.getRoots().stream()
                    .map(testIdentifier -> adapTestIdentifier(suiteTestDescriptor, testPlan, testIdentifier))
                    .forEach(engineDescriptor -> {
                        suiteTestDescriptor.addChild(engineDescriptor);
                        engineDescriptor.prune();
                    });
            return Optional.of(suiteTestDescriptor);
        });
        return suite.map(Match::exact).map(Resolution::match).orElseGet(Resolution::unresolved);
    }

    private TestDescriptor adapTestIdentifier(
            SuiteTestDescriptor suiteTestDescriptor,
            TestPlan testPlan,
            TestIdentifier testIdentifier
    ) {
        AbstractTestDescriptor testDescriptor = new TestIdentifierAdaptor(suiteTestDescriptor, testIdentifier);
        testPlan.getChildren(testIdentifier).stream()
                .map(childIdentifier -> adapTestIdentifier(suiteTestDescriptor, testPlan, childIdentifier))
                .forEach(testDescriptor::addChild);
        return testDescriptor;
    }

}
