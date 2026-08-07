package de.luckymcdev.foundryengine.buildsrc;

import org.gradle.api.DefaultTask;
import org.gradle.api.artifacts.ConfigurationContainer;
import org.gradle.api.tasks.TaskAction;

import javax.inject.Inject;

public abstract class RefreshDynamicModsTask extends DefaultTask {

    @Inject
    protected abstract ConfigurationContainer getConfigurations();

    @TaskAction
    public void run() {
        getConfigurations().configureEach(config -> config.resolutionStrategy(strategy -> {
            strategy.cacheDynamicVersionsFor(0, "seconds");
            strategy.cacheChangingModulesFor(0, "seconds");
        }));

        getLogger().lifecycle("Dynamic mod dependency caches cleared. Re-run your build task to pick up jar changes.");
    }
}