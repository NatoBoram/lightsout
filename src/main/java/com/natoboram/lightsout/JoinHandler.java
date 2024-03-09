package com.natoboram.lightsout;

import java.util.List;

import org.slf4j.Logger;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.GameRules;

/**
 * Handles player join events.
 * <p>
 * When the first player joins, the time is set to 0 as if the
 * {@link net.minecraft.server.command.TimeCommand /time command} was used
 * except if the {@link net.minecraft.world.GameRules#DO_DAYLIGHT_CYCLE
 * doDaylightCycle} game rule is false.
 */
@Environment(EnvType.SERVER)
public class JoinHandler implements ServerPlayConnectionEvents.Join {
	private final Logger LOGGER;

	JoinHandler(final Logger LOGGER) {
		this.LOGGER = LOGGER;
	}

	@Override
	public void onPlayReady(final ServerPlayNetworkHandler handler, final PacketSender sender,
			final MinecraftServer server) {
		final PlayerManager playerManager = server.getPlayerManager();
		final List<ServerPlayerEntity> playerList = playerManager.getPlayerList();
		final int size = playerList.size();

		// The player count is 0 when the first player joins
		if (size > 0) {
			this.LOGGER.debug("Player joined, {} players connected", size);
			return;
		}

		final ServerWorld serverWorld = handler.player.getServerWorld();
		final boolean doDaylightCycle = serverWorld.getGameRules().getBoolean(GameRules.DO_DAYLIGHT_CYCLE);
		if (!doDaylightCycle) {
			LOGGER.warn("doDaylightCycle is {}, skipping", doDaylightCycle);
			return;
		}

		LOGGER.info("First player joined, setting time to 0");
		serverWorld.setTimeOfDay(0);
	}
}
