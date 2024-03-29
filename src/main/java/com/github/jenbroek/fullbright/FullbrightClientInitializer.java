package com.github.jenbroek.fullbright;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class FullbrightClientInitializer implements ClientModInitializer {

	public static final double FULLBRIGHT_GAMMA = 1000D;

	/*
	 * Assign a sane default value in case `gamma` is
	 * already equal to `FULLBRIGHT_GAMMA` at startup.
	 */
	private double oldGamma = 1D;

	@Override
	public void onInitializeClient() {
		KeyBinding keyBinding = KeyBindingHelper.registerKeyBinding(
			new KeyBinding( "key.fullbright.toggle", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_O, "category.fullbright")
		);

		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			if (keyBinding.wasPressed()) {
				var gammeValue = client.options.getGamma().getValue();
				if (gammeValue == FULLBRIGHT_GAMMA) {
					client.options.getGamma().setValue(oldGamma);
				} else {
					oldGamma = gammeValue;
					client.options.getGamma().setValue(FULLBRIGHT_GAMMA);
				}
			}
		});
	}

}
