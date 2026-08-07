package de.luckymcdev.foundryengine.buildsrc;

import com.electronwill.nightconfig.core.CommentedConfig;
import com.electronwill.nightconfig.toml.TomlFormat;
import org.gradle.api.GradleException;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.file.FileTree;
import org.gradle.api.tasks.Copy;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class FoundryBundlePlugin implements Plugin<Project> {

    public static final String GLOBAL_MODS_PATH_FILE =
            System.getProperty("user.home") + "/.foundryengine/last-mods-path.properties";

    @Override
    public void apply(Project project) {
        BundleMetadata metadata = readBundleMetadata(project);
        project.setVersion(metadata.version());
        project.getExtensions().add("foundryBundle", metadata);

        registerDynamicModsDependency(project);
        registerBundleTasks(project, metadata);
        registerUtilityTasks(project);
    }

    private BundleMetadata readBundleMetadata(Project project) {
        File resourcesDir = project.file("src/main/resources");
        File[] tomlFiles = resourcesDir.listFiles((dir, name) -> name.endsWith(".bundles.toml"));

        if (tomlFiles == null || tomlFiles.length == 0) {
            throw new GradleException("No *.bundles.toml file found in " + resourcesDir);
        }
        if (tomlFiles.length > 1) {
            throw new GradleException("Multiple *.bundles.toml files found in " + resourcesDir + ", expected exactly one");
        }

        File tomlFile = tomlFiles[0];
        CommentedConfig config;
        try (Reader reader = new FileReader(tomlFile)) {
            config = TomlFormat.instance().createParser().parse(reader);
        } catch (IOException e) {
            throw new GradleException("Failed to read " + tomlFile, e);
        }

        List<CommentedConfig> bundleList = config.get("bundles");
        if (bundleList == null || bundleList.isEmpty()) {
            throw new GradleException(tomlFile + " has no [[bundles]] entries");
        }
        if (bundleList.size() > 1) {
            throw new GradleException(tomlFile + " must contain exactly one [[bundles]] entry for a bundle project");
        }

        CommentedConfig entry = bundleList.get(0);

        String bundleId = requireString(entry, "bundleId", tomlFile);
        String displayName = requireString(entry, "displayName", tomlFile);
        String version = requireString(entry, "version", tomlFile);

        List<String> authors = Arrays.stream(entry.getOrElse("authors", "").toString().split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .toList();

        String displayURL = entry.get("displayURL");
        String description = entry.get("description");
        List<String> dependencies = entry.getOrElse("dependencies", List.of());

        return new BundleMetadata(bundleId, version, displayName, authors, displayURL, description, dependencies);
    }

    private String requireString(CommentedConfig entry, String key, File tomlFile) {
        String value = entry.get(key);
        if (value == null || value.isBlank()) {
            throw new GradleException(tomlFile + " is missing required field: " + key);
        }
        return value;
    }

    private void registerDynamicModsDependency(Project project) {
        File globalModsFile = new File(GLOBAL_MODS_PATH_FILE);
        if (!globalModsFile.exists()) {
            return;
        }

        Properties props = new Properties();
        try (InputStream in = new FileInputStream(globalModsFile)) {
            props.load(in);
        } catch (IOException e) {
            project.getLogger().warn("Failed to read {}: {}", GLOBAL_MODS_PATH_FILE, e.getMessage());
            return;
        }

        String modsPath = props.getProperty("modsPath");
        if (modsPath == null || modsPath.isBlank()) {
            return;
        }

        File modsDir = new File(modsPath);
        if (!modsDir.exists() || !modsDir.isDirectory()) {
            return;
        }

        FileTree modJars = project.fileTree(modsDir, tree -> tree.include("**/*.jar"));
        project.getDependencies().add("implementation", modJars);
    }

    private void registerBundleTasks(Project project, BundleMetadata metadata) {
        var buildBundle = project.getTasks().register("buildBundle", Copy.class, task -> {
            task.setGroup("build");
            task.setDescription("Bundle equivalent to jar");

            task.from("src/main/groovy", spec -> spec.into("scripts"));
            task.from("src/main/resources");
            task.into(project.getLayout().getBuildDirectory().dir("bundles/" + metadata.folderName()));
        });

        project.getTasks().register("deployBundle", Copy.class, task -> {
            task.setGroup("build");
            task.setDescription("Deploys the bundle to run/FoundryEngine/bundles");

            task.from(buildBundle);
            task.into(project.file("run/FoundryEngine/bundles/" + metadata.folderName()));
        });
    }

    private void registerUtilityTasks(Project project) {
        project.getTasks().register("showModsPath", ShowModsPathTask.class, task -> {
            task.setGroup("help");
            task.setDescription("Shows the last recorded mods path");
        });

        project.getTasks().register("clearModsPath", ClearModsPathTask.class, task -> {
            task.setGroup("development");
            task.setDescription("Clears the recorded mods path");
        });

        project.getTasks().register("refreshDynamicMods", RefreshDynamicModsTask.class, task -> {
            task.setGroup("development");
            task.setDescription("Forces re-resolution of dynamic mod dependencies");
        });
    }
}