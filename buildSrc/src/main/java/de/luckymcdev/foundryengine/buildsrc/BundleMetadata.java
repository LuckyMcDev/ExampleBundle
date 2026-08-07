package de.luckymcdev.foundryengine.buildsrc;

import java.util.List;

public record BundleMetadata(
        String bundleId,
        String version,
        String displayName,
        List<String> authors,
        String displayURL,
        String description,
        List<String> dependencies
) {
    public String folderName() {
        return bundleId + "-" + version;
    }
}