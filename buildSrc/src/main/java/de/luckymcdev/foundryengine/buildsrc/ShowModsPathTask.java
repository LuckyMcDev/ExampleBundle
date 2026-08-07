package de.luckymcdev.foundryengine.buildsrc;

import org.gradle.api.DefaultTask;
import org.gradle.api.GradleException;
import org.gradle.api.tasks.TaskAction;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public abstract class ShowModsPathTask extends DefaultTask {

    @TaskAction
    public void run() {
        File file = new File(FoundryBundlePlugin.GLOBAL_MODS_PATH_FILE);

        if (!file.exists()) {
            getLogger().lifecycle("No mods path recorded yet. Launch the client once via FoundryEngine to record it.");
            return;
        }

        Properties props = new Properties();
        try (FileInputStream in = new FileInputStream(file)) {
            props.load(in);
        } catch (IOException e) {
            throw new GradleException("Failed to read " + file, e);
        }

        getLogger().lifecycle("Current mods path: {}", props.getProperty("modsPath"));
    }
}