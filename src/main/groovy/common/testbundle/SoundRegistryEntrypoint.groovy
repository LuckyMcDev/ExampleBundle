package common.testbundle

import de.luckymcdev.foundryengine.builder.common.sound.SoundBuilder
import de.luckymcdev.foundryengine.event.common.BundleEvents
import de.luckymcdev.foundryengine.script.common.BundleEntrypoint

class SoundRegistryEntrypoint implements BundleEntrypoint {
	static def TEST_FORMATS = ["flac", "mp3", "ogg"]

	static def TEST_SONGS = TEST_FORMATS.collect { format ->
		SoundBuilder.create(CommonEntrypoint.id("testsong_${format}"))
				.subtitle("Test Song (${format.toUpperCase()})")
				.addSound(CommonEntrypoint.id("testsong_${format}"))
	}

	@Override
	void onLoad() {
		BundleEvents.registry {
			TEST_SONGS.each {sb ->
				it.sounds(sb)
			}
		}
	}

	@Override
	void onUnload() {

	}
}