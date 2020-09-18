package com.github.mpkorstanje.junit.platform.suite;

import org.junit.platform.engine.TestDescriptor;
import org.junit.platform.engine.discovery.ClassNameFilter;
import org.junit.platform.engine.discovery.ClassSelector;
import org.junit.platform.engine.discovery.DiscoverySelectors;
import org.junit.platform.engine.discovery.PackageNameFilter;
import org.junit.platform.engine.discovery.PackageSelector;
import org.junit.platform.engine.support.descriptor.AbstractTestDescriptor;
import org.junit.platform.engine.support.discovery.SelectorResolver;
import org.junit.platform.launcher.EngineFilter;
import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.TagFilter;
import org.junit.platform.launcher.TestIdentifier;
import org.junit.platform.launcher.TestPlan;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.suite.api.ExcludeClassNamePatterns;
import org.junit.platform.suite.api.ExcludeEngines;
import org.junit.platform.suite.api.ExcludePackages;
import org.junit.platform.suite.api.ExcludeTags;
import org.junit.platform.suite.api.IncludeClassNamePatterns;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.IncludePackages;
import org.junit.platform.suite.api.IncludeTags;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.SelectPackages;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;
import static org.junit.platform.commons.support.AnnotationSupport.findAnnotation;
import static org.junit.platform.commons.support.AnnotationSupport.findRepeatableAnnotations;

class ClassSelectorResolver implements SelectorResolver {
    @Override
    public Resolution resolve(ClassSelector selector, Context context) {
        Class<?> testClass = selector.getJavaClass();
        return toResolution(context.addToParent(parent -> newSuiteDescriptor(testClass, parent)));
    }

    private Resolution toResolution(Optional<SuiteTestDescriptor> suite) {
        return suite
                .map(Match::exact)
                .map(Resolution::match)
                .orElseGet(Resolution::unresolved);
    }

    private Optional<SuiteTestDescriptor> newSuiteDescriptor(Class<?> testClass, TestDescriptor parent) {
        LauncherDiscoveryRequest launcherDiscoveryRequest = createLauncherDiscoveryRequest(testClass);

        Launcher launcher = LauncherFactory.create();
        TestPlan testPlan = launcher.discover(launcherDiscoveryRequest);

        SuiteTestDescriptor suiteTestDescriptor = new SuiteTestDescriptor(
                parent.getUniqueId().append(SuiteTestDescriptor.SEGMENT_TYPE, testClass.getName()),
                testClass,
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
    }

    private LauncherDiscoveryRequest createLauncherDiscoveryRequest(Class<?> testClass) {
        LauncherDiscoveryRequestBuilder request = new LauncherDiscoveryRequestBuilder()
                // Avoid recursion
                .filters(EngineFilter.excludeEngines(SuiteEngineDescriptor.ENGINE_ID));

        findAnnotation(testClass, SelectClasses.class).map(SelectClasses::value)
                .ifPresent(classes -> request.selectors(selectClasses(classes)));
        findAnnotation(testClass, SelectPackages.class).map(SelectPackages::value)
                .ifPresent(packages -> request.selectors(selectPackages(packages)));

        findAnnotation(testClass, ExcludeTags.class).map(ExcludeTags::value)
                .ifPresent(tagExpressions -> request.filters(TagFilter.excludeTags(tagExpressions)));
        findAnnotation(testClass, IncludeTags.class).map(IncludeTags::value)
                .ifPresent(tagExpressions -> request.filters(TagFilter.includeTags(tagExpressions)));

        findAnnotation(testClass, ExcludeClassNamePatterns.class).map(ExcludeClassNamePatterns::value)
                .ifPresent(patterns -> request.filters(ClassNameFilter.excludeClassNamePatterns(patterns)));
        findAnnotation(testClass, IncludeClassNamePatterns.class).map(IncludeClassNamePatterns::value)
                .ifPresent(patterns -> request.filters(ClassNameFilter.includeClassNamePatterns(patterns)));

        findAnnotation(testClass, ExcludeEngines.class).map(ExcludeEngines::value)
                .ifPresent(engineIds -> request.filters(EngineFilter.excludeEngines(engineIds)));
        findAnnotation(testClass, IncludeEngines.class).map(IncludeEngines::value)
                .ifPresent(engineIds -> request.filters(EngineFilter.includeEngines(engineIds)));

        findAnnotation(testClass, ExcludePackages.class).map(ExcludePackages::value)
                .ifPresent(packageNames -> request.filters(PackageNameFilter.excludePackageNames(packageNames)));
        findAnnotation(testClass, IncludePackages.class).map(IncludePackages::value)
                .ifPresent(packageNames -> request.filters(PackageNameFilter.includePackageNames(packageNames)));

        findRepeatableAnnotations(testClass, Configuration.class).forEach(
                configuration -> request.configurationParameter(configuration.key(), configuration.value()));

        return request.build();
    }

    private List<ClassSelector> selectClasses(Class<?>[] classes) {
        return Arrays.stream(classes).map(DiscoverySelectors::selectClass).collect(toList());
    }

    private List<PackageSelector> selectPackages(String[] packages) {
        return Arrays.stream(packages).map(DiscoverySelectors::selectPackage).collect(toList());
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
