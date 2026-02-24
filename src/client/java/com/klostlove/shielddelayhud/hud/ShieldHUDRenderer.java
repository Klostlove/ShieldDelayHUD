package com.klostlove.shielddelayhud.hud;

import com.klostlove.shielddelayhud.ShieldDelayHUDClient;
import com.klostlove.shielddelayhud.shield.ShieldTracker;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;

public class ShieldHUDRenderer {

    private static final float CENTER_OFFSET_Y = 12.5f;

    public static void register() {
        HudRenderCallback.EVENT.register(ShieldHUDRenderer::render);
    }

    private static void render(DrawContext context, RenderTickCounter tickCounter) {

        if (!ShieldDelayHUDClient.CONFIG.enabled) return;

        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null) return;

        ShieldTracker.tick();

        if (!ShieldTracker.isActive()) return;

        long elapsed = ShieldTracker.getElapsedMs();

        String text = ShieldDelayHUDClient.CONFIG.showMs
                ? elapsed + "ms"
                : String.valueOf(elapsed);

        int rawColor = ShieldTracker.isReady()
                ? ShieldDelayHUDClient.CONFIG.readyColor
                : ShieldDelayHUDClient.CONFIG.notReadyColor;

        int color = 0xFF000000 | rawColor;

        int x;
        int y;

        if (ShieldDelayHUDClient.CONFIG.centerHud) {

            int centerX = context.getScaledWindowWidth() / 2;
            int centerY = (int)(context.getScaledWindowHeight() / 2 + CENTER_OFFSET_Y);

            int textWidth = client.textRenderer.getWidth(text);

            x = centerX - textWidth / 2;
            y = centerY;

        } else {
            x = ShieldDelayHUDClient.CONFIG.x;
            y = ShieldDelayHUDClient.CONFIG.y;
        }

        context.drawText(
                client.textRenderer,
                text,
                x,
                y,
                color,
                true
        );
    }
}