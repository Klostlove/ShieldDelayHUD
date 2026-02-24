package com.klostlove.shielddelayhud;

import com.klostlove.shielddelayhud.config.ModConfig;
import com.klostlove.shielddelayhud.hud.ShieldHUDRenderer;
import net.fabricmc.api.ClientModInitializer;

public class ShieldDelayHUDClient implements ClientModInitializer {

    public static ModConfig CONFIG;

    @Override
    public void onInitializeClient() {
        CONFIG = ModConfig.load();
        ShieldHUDRenderer.register();
    }
}