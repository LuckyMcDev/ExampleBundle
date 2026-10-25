package de.luckymcdev.foundryengine.buildsrc;

import org.gradle.api.Project;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Reads {@code <project>/.foundryengine/instance.properties}, written by the FoundryEngine IntelliJ plugin.
 * Read through a file-contents provider so it is tracked by the configuration cache.
 */
record FoundryLocalConfig(String modsPath, List<String> modJars, String bundlesDir) {

    private static final String PATH = ".foundryengine/instance.properties";

    static FoundryLocalConfig read(Project project) {
        byte[] bytes = project.getProviders()
                .fileContents(project.getLayout().getProjectDirectory().file(PATH))
                .getAsBytes()
                .getOrNull();
        if (bytes == null) {
            return new FoundryLocalConfig(null, null, null);
        }

        Properties props = new Properties();
        try {
            props.load(new ByteArrayInputStream(bytes));
        } catch (IOException e) {
            project.getLogger().warn("Failed to read {}: {}", PATH, e.getMessage());
            return new FoundryLocalConfig(null, null, null);
        }

        List<String> jars = null;
        String jarProp = props.getProperty("modJars");
        if (jarProp != null) {
            jars = new ArrayList<>();
            for (String s : jarProp.split(",")) {
                if (!s.isBlank()) {
                    jars.add(s.trim());
                }
            }
        }
        return new FoundryLocalConfig(blankToNull(props.getProperty("modsPath")), jars,
                blankToNull(props.getProperty("bundlesDir")));
    }

    private static String blankToNull(String s) {
        return s == null || s.isBlank() ? null : s.trim();
    }
}
