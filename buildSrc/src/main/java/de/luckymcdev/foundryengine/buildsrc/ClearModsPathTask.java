package de.luckymcdev.foundryengine.buildsrc;

import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;

import java.io.File;

public abstract class ClearModsPathTask extends DefaultTask {

    @TaskAction
    public void run() {
        File file = new File(FoundryBundlePlugin.GLOBAL_MODS_PATH_FILE);

        if (file.exists() && file.delete()) {
            getLogger().lifecycle("Cleared recorded mods path.");
        } else {
            getLogger().lifecycle("No recorded mods path to clear.");
        }
    }
}