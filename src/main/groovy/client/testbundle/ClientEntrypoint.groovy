package client.testbundle

import common.testbundle.BlockEntityDemoEntrypoint
import de.luckymcdev.foundryengine.client.render.blockentity.EngineBlockEntityRenderers
import de.luckymcdev.foundryengine.common.Common
import de.luckymcdev.foundryengine.common.event.BundleEvents
import de.luckymcdev.foundryengine.common.event.ClientEvents
import de.luckymcdev.foundryengine.common.script.BundleEntrypoint
import net.minecraft.client.Minecraft

class ClientEntrypoint implements BundleEntrypoint {

	@Override
	void onLoad() {
		ClientEvents.tick {
			def player = Minecraft.getInstance().player
			if (player != null) {
				if (player.tickCount % 20 == 0) {
					//player.sendSystemMessage(Component.literal("Test + " + player.tickCount))
				}
			}
		}

		ClientEvents.renderGui {
			//it.guiGraphics.fakeItem(new ItemStack(CommonEntrypoint.THIS_IS_A_ITEM.get()), 100, 200)
		}

		BundleEvents.registry {
			BlockEntityDemoEntrypoint.COUNTER_BE.renderer(EngineBlockEntityRenderers.noop())
		}

		Common.LOGGER.error("Test Load Server")
	}

	@Override
	void onUnload() {
	}
}