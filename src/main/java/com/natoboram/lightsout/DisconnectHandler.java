
package com.natoboram.lightsout;

import java.util.List;

import org.slf4j.Logger;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;

@Environment(EnvType.SERVER)
public class DisconnectHandler implements ServerPlayConnectionEvents.Disconnect {
	private final Logger LOGGER;

	DisconnectHandler(final Logger LOGGER) {
		this.LOGGER = LOGGER;
	}

	@Override
	public void onPlayDisconnect(final ServerPlayNetworkHandler handler, final MinecraftServer server) {
		final PlayerManager playerManager = server.getPlayerManager();
		final List<ServerPlayerEntity> playerList = playerManager.getPlayerList();
		final int size = playerList.size();

		// The player count is 1 when the last player disconnects
		if (size > 1) {
			LOGGER.debug("Player disconnected, {} players remaining", size - 1);
			return;
		}

		LOGGER.info("Last player disconnected, stopping server");
		server.stop(false);
	}
}
