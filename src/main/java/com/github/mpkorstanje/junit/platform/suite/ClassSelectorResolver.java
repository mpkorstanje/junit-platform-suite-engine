package com.github.mpkorstanje.junit.platform.suite;

import org.junit.platform.engine.TestDescriptor;
import org.junit.platform.engine.UniqueId;
import org.junit.platform.engine.discovery.ClassNameFilter;
import org.junit.platform.engine.discovery.ClassSelector;
import org.junit.platform.engine.discovery.DiscoverySelectors;
import org.junit.platform.engine.discovery.PackageNameFilter;
import org.junit.platform.engine.discovery.PackageSelector;
import org.junit.platform.engine.support.discovery.SelectorResolver;
import org.junit.platform.launcher.EngineFilter;
import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.TagFilter;
import org.junit.platform.launcher.TestIdentifier;
import org.junit.platform.launcher.TestPlan;
import org.junit.platform.launcher.core.LauncherConfig;
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
import java.util.function.Function;
import java.util.function.Predicate;

import static java.util.stream.Collectors.toList;
import static org.junit.platform.commons.support.AnnotationSupport.findAnnotation;
import static org.junit.platform.commons.support.AnnotationSupport.findRepeatableAnnotations;
import static org.junit.platform.engine.support.discovery.SelectorResolver.Resolution.unresolved;

class ClassSelectorResolver implements SelectorResolver {

    private static final IsSuiteClass isSuiteClass = new IsSuiteClass();

    private final Predicate<String> classNameFilter;

    ClassSelectorResolver(Predicate<String> classNameFilter) {
        this.classNameFilter = classNameFilter;
    }

    @Override
    public Resolution resolve(ClassSelector selector, Context context) {
        Class<?> testClass = selector.getJavaClass();
        if (isSuiteClass.test(testClass)) {
            // Nested tests are never filtered out
            if (classNameFilter.test(testClass.getName())) {
                return toResolution(context.addToParent(parent -> newSuiteDescriptor(testClass, parent)));
            }
        }
        // TODO: Support nest test classes
        return unresolved();
    }

    private Resolution toResolution(Optional<SuiteTestDescriptor> suite) {
        return suite
                .map(Match::exact)
                .map(Resolution::match)
                .orElseGet(Resolution::unresolved);
    }

    private Optional<SuiteTestDescriptor> newSuiteDescriptor(Class<?> testClass, TestDescriptor parent) {
        LauncherDiscoveryRequest launcherDiscoveryRequest = createLauncherDiscoveryRequest(testClass);

        LauncherConfig launcherConfig = LauncherConfig.builder()
                // TODO: Support launcher config annotations
                .build();

        Launcher launcher = LauncherFactory.create(launcherConfig);
        TestPlan testPlan = launcher.discover(launcherDiscoveryRequest);

        SuiteTestDescriptor suiteTestDescriptor = new SuiteTestDescriptor(
                parent.getUniqueId().append(SuiteTestDescriptor.SEGMENT_TYPE, testClass.getName()),
                testClass,
                testPlan,
                launcher
        );

        addEnginesToSuite(suiteTestDescriptor, testPlan, testIdentifier -> {
            UniqueId uniqueId = suiteTestDescriptor.uniqueIdInSuite(testIdentifier);
            return new JUnitPlatformTestDescriptor(uniqueId, testIdentifier);
        });

        return Optional.of(suiteTestDescriptor);
    }

    private void addEnginesToSuite(
            SuiteTestDescriptor suiteTestDescriptor,
            TestPlan testPlan,
            Function<TestIdentifier, TestDescriptor> createTestDescriptor) {
        testPlan.getRoots().stream()
                .map(testIdentifier -> adapTestIdentifier(testPlan, testIdentifier, createTestDescriptor))
                .forEach(suiteTestDescriptor::addChild);
    }

    private LauncherDiscoveryRequest createLauncherDiscoveryRequest(Class<?> testClass) {
        LauncherDiscoveryRequestBuilder request = new LauncherDiscoveryRequestBuilder()
                // Avoid recursion
                .filters(EngineFilter.excludeEngines(SuiteEngineDescriptor.ENGINE_ID));

        findAnnotation(testClass, SelectClasses.class).map(SelectClasses::value)
                .ifPresent(classes -> request.selectors(selectClasses(classes)));
        findAnnotation(testClass, SelectPackages.class).map(SelectPackages::value)
                .ifPresent(packages -> request.selectors(selectPackages(packages)));

        // TODO: Support plural select classpath resources/files
        // TODO: Support repeatable for SelectClasspathResource/SelectFile
        // TODO: Support file position SelectClasspathResource/SelectFile
        findAnnotation(testClass, SelectClasspathResource.class).map(SelectClasspathResource::value)
                .ifPresent(resource -> request.selectors(DiscoverySelectors.selectClasspathResource(resource)));
        findAnnotation(testClass, SelectFile.class).map(SelectFile::value)
                .ifPresent(file -> request.selectors(DiscoverySelectors.selectFile(file)));

        findAnnotation(testClass, SelectDirectories.class).map(SelectDirectories::value)
                .ifPresent(directory -> request.selectors(DiscoverySelectors.selectDirectory(directory)));

        // TODO: Support more selectors

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
            TestPlan testPlan,
            TestIdentifier testIdentifier,
            Function<TestIdentifier, TestDescriptor> mapper
    ) {
        TestDescriptor testDescriptor = mapper.apply(testIdentifier);
        testPlan.getChildren(testIdentifier).stream()
                .map(childIdentifier -> adapTestIdentifier(testPlan, childIdentifier, mapper))
                .forEach(testDescriptor::addChild);
        return testDescriptor;
    }

}
