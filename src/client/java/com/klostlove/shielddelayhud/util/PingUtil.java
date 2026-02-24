package com.klostlove.shielddelayhud.util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.PlayerListEntry;

public class PingUtil {
    public static int getPing() {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null || client.getNetworkHandler() == null) {
            return 0;
        }

        PlayerListEntry entry = client.getNetworkHandler()
                .getPlayerListEntry(client.player.getUuid());

        if (entry == null) return 0;

        return entry.getLatency();
    }
}