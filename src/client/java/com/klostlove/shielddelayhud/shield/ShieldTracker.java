package com.klostlove.shielddelayhud.shield;

import com.klostlove.shielddelayhud.ShieldDelayHUDClient;
import com.klostlove.shielddelayhud.util.PingUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundEvents;

public class ShieldTracker {

    private static boolean wasBlocking = false;
    private static long startNanoTime = 0L;
    private static long currentNanoTime = 0L;
    private static boolean active = false;
    private static boolean soundPlayed = false;

    public static void tick() {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null) return;

        boolean isBlocking = client.player.isUsingItem()
                && client.player.getActiveItem().isOf(Items.SHIELD);

        if (isBlocking && !wasBlocking) {
            startNanoTime = System.nanoTime();
            active = true;
            soundPlayed = false;
        }

        if (!isBlocking && wasBlocking) {
            active = false;
            soundPlayed = false;
        }

        wasBlocking = isBlocking;

        if (active) {
            currentNanoTime = System.nanoTime();
        }

        if (active && !soundPlayed && isReady() && ShieldDelayHUDClient.CONFIG.soundEnabled) {
            client.player.playSound(
                    SoundEvents.BLOCK_NOTE_BLOCK_XYLOPHONE.value(),
                    ShieldDelayHUDClient.CONFIG.soundVolume,
                    ShieldDelayHUDClient.CONFIG.soundPitch
            );
            soundPlayed = true;
        }
    }

    public static boolean isActive() {
        return active;
    }

    public static long getElapsedMs() {
        if (!active) return 0;
        return (currentNanoTime - startNanoTime) / 1_000_000;
    }

    public static long getTargetMs() {
        return 250 + PingUtil.getPing();
    }

    public static boolean isReady() {
        return getElapsedMs() >= getTargetMs();
    }
}