package common.testbundle

import de.luckymcdev.foundryengine.common.builder.sound.SoundBuilder
import de.luckymcdev.foundryengine.common.event.BundleEvents
import de.luckymcdev.foundryengine.common.script.BundleEntrypoint

class SoundRegistry implements BundleEntrypoint {
	static def TEST_FORMATS = ["flac", "mp3", "ogg"]

	static def TEST_SONGS = TEST_FORMATS.collect { format ->
		SoundBuilder.create(CommonEntrypoint.id("testsong_${format}"))
				.subtitle("Test Song (${format.toUpperCase()})")
				.addSound(CommonEntrypoint.id("testsong_${format}"))
	}

	@Override
	void onLoad() {
		BundleEvents.registry {
			it.sounds(*TEST_SONGS)
		}
	}

	@Override
	void onUnload() {

	}
}