package com.github.mpkorstanje.junit.platform.suite;

import org.junit.platform.engine.EngineDiscoveryRequest;
import org.junit.platform.engine.TestDescriptor;
import org.junit.platform.engine.support.discovery.EngineDiscoveryRequestResolver;

class DiscoverySelectorResolver {

    private static final EngineDiscoveryRequestResolver<SuiteEngineDescriptor> resolver = EngineDiscoveryRequestResolver.<SuiteEngineDescriptor>builder()
            .addClassContainerSelectorResolver(new IsSuiteClass())
            .addSelectorResolver(context -> new ClassSelectorResolver(context.getClassNameFilter()))
            .addTestDescriptorVisitor(context -> TestDescriptor::prune)
            // TODO: Add package, module, ect
            .build();

    void resolveSelectors(EngineDiscoveryRequest request, SuiteEngineDescriptor engineDescriptor) {
        resolver.resolve(request, engineDescriptor);
    }

}
