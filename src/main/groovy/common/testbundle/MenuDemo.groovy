package common.testbundle

import client.testbundle.ChestScreen
import de.luckymcdev.foundryengine.common.builder.block.BlockBuilder
import de.luckymcdev.foundryengine.common.builder.blockentity.BlockEntityBuilder
import de.luckymcdev.foundryengine.common.builder.menu.EngineMenu
import de.luckymcdev.foundryengine.common.builder.menu.MenuBuilder
import de.luckymcdev.foundryengine.common.event.BundleEvents
import de.luckymcdev.foundryengine.common.script.BundleEntrypoint
import net.minecraft.network.chat.Component
import net.minecraft.resources.Identifier
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.SimpleContainer
import net.minecraft.world.InteractionResult

class MenuDemo implements BundleEntrypoint {

	static final String BUNDLEID = "testbundle"

	static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(BUNDLEID, path)
	}

	public static final BlockEntityBuilder<?> STORAGE_BE = BlockEntityBuilder.create(id("storage"))
			.onLoad { println "Storage BE loaded" }
			.onSave { println "Storage BE saved" }

	public static final MenuBuilder<EngineMenu> CHEST_MENU =
			MenuBuilder.<EngineMenu> create(id("chest_menu"))
					.supplier { type, containerId, playerInventory ->
						EngineMenu.builder()
								.container(new SimpleContainer(27))
								.size(3, 9)
								.build(type, containerId, playerInventory)
					}
					.screen(ChestScreen::new)

	public static final MenuBuilder<EngineMenu> STORAGE_MENU =
			MenuBuilder.<EngineMenu> create(id("storage_menu"))
					.supplier { type, containerId, playerInventory ->
						EngineMenu.builder()
								.container(new SimpleContainer(27))
								.size(3, 9)
								.build(type, containerId, playerInventory)
					}
					.containerFactory { type, containerId, playerInventory, extraData ->
						def x = extraData.readInt()
						def y = extraData.readInt()
						def z = extraData.readInt()
						println "Client: opening menu for block at $x $y $z"
						EngineMenu.builder()
								.container(new SimpleContainer(27))
								.size(3, 9)
								.build(type, containerId, playerInventory)
					}
					.screen(ChestScreen::new)

	public static final BlockBuilder STORAGE_BLOCK = BlockBuilder.create(id("storage_block"))
			.properties { it.strength(2.0f, 3.0f) }
			.blockEntity(STORAGE_BE)
			.use { state, level, pos, player, hitResult ->
				if (!level.isClientSide() && player instanceof ServerPlayer) {
					def serverPlayer = player as ServerPlayer
					serverPlayer.openMenu(STORAGE_MENU, { buf ->
						buf.writeInt(pos.x)
						buf.writeInt(pos.y)
						buf.writeInt(pos.z)
					})
				}
				return InteractionResult.SUCCESS
			}

	@Override
	void onLoad() {
		BundleEvents.registry {
			it.blocks(STORAGE_BLOCK)
			it.menus(CHEST_MENU, STORAGE_MENU)
		}
	}

	@Override
	void onUnload() {}
}