package com.natoboram.lightsout;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;

@Environment(EnvType.SERVER)
public class LightsOut implements DedicatedServerModInitializer {
	private static final String MOD_ID = "lightsout";

	/**
	 * This logger is used to write text to the console and the log file. It is
	 * considered best practice to use your mod id as the logger's name. That way,
	 * it's clear which mod wrote info, warnings, and errors.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	/**
	 * This code runs as soon as Minecraft is in a mod-load-ready state. However,
	 * some things (like resources) may still be uninitialized. Proceed with mild
	 * caution.
	 */
	@Override
	public void onInitializeServer() {
		ServerPlayConnectionEvents.DISCONNECT.register(new DisconnectHandler(LightsOut.LOGGER));
		ServerPlayConnectionEvents.JOIN.register(new JoinHandler(LightsOut.LOGGER));
		LOGGER.info("Loaded Lights Out!");
	}
}
