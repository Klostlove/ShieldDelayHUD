package com.klostlove.shielddelayhud.config;

import com.klostlove.shielddelayhud.ShieldDelayHUDClient;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.text.Text;

public class ModMenuIntegration implements ModMenuApi {

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> {

            ModConfig config = ShieldDelayHUDClient.CONFIG;

            ConfigBuilder builder = ConfigBuilder.create()
                    .setParentScreen(parent)
                    .setTitle(Text.literal("ShieldDelayHUD Settings"));

            ConfigEntryBuilder entryBuilder = builder.entryBuilder();

            // ================= GENERAL =================
            ConfigCategory general = builder.getOrCreateCategory(Text.literal("General"));
            general.addEntry(entryBuilder.startBooleanToggle(Text.literal("Enabled"), config.enabled)
                    .setDefaultValue(ModConfig.DEFAULT_ENABLED)
                    .setSaveConsumer(value -> config.enabled = value)
                    .build());
            general.addEntry(entryBuilder.startBooleanToggle(Text.literal("Show \"ms\""), config.showMs)
                    .setDefaultValue(ModConfig.DEFAULT_SHOW_MS)
                    .setSaveConsumer(value -> config.showMs = value)
                    .build());
            general.addEntry(entryBuilder.startIntSlider(Text.literal("Text Opacity"), config.textOpacity, 0, 100)
                    .setDefaultValue(ModConfig.DEFAULT_OPACITY)
                    .setSaveConsumer(value -> config.textOpacity = value)
                    .build());
            general.addEntry(entryBuilder.startColorField(Text.literal("Not Ready Color"), config.notReadyColor)
                    .setDefaultValue(ModConfig.DEFAULT_NOT_READY_COLOR)
                    .setSaveConsumer(value -> config.notReadyColor = value)
                    .build());
            general.addEntry(entryBuilder.startColorField(Text.literal("Ready Color"), config.readyColor)
                    .setDefaultValue(ModConfig.DEFAULT_READY_COLOR)
                    .setSaveConsumer(value -> config.readyColor = value)
                    .build());
            // Restore General
            general.addEntry(entryBuilder.startTextDescription(Text.literal(" ")).build());
            general.addEntry(entryBuilder.startBooleanToggle(Text.literal("Restore Defaults (General)"), false)
                    .setSaveConsumer(value -> { if (value) config.restoreDefaultsGeneral(); })
                    .build());

            // ================= POSITION =================
            ConfigCategory position = builder.getOrCreateCategory(Text.literal("Position"));
            position.addEntry(entryBuilder.startBooleanToggle(Text.literal("Center HUD"), config.centerHud)
                    .setDefaultValue(ModConfig.DEFAULT_CENTER)
                    .setSaveConsumer(value -> config.centerHud = value)
                    .build());
            position.addEntry(entryBuilder.startIntField(Text.literal("X Position"), config.x)
                    .setDefaultValue(ModConfig.DEFAULT_X)
                    .setTooltip(Text.literal("Ignored when Center HUD is enabled"))
                    .setSaveConsumer(value -> config.x = value)
                    .build());
            position.addEntry(entryBuilder.startIntField(Text.literal("Y Position"), config.y)
                    .setDefaultValue(ModConfig.DEFAULT_Y)
                    .setTooltip(Text.literal("Ignored when Center HUD is enabled"))
                    .setSaveConsumer(value -> config.y = value)
                    .build());
            position.addEntry(entryBuilder.startTextDescription(Text.literal(" ")).build());
            position.addEntry(entryBuilder.startBooleanToggle(Text.literal("Restore Defaults (Position)"), false)
                    .setSaveConsumer(value -> { if (value) config.restoreDefaultsPosition(); })
                    .build());

            // ================= SOUND =================
            ConfigCategory sound = builder.getOrCreateCategory(Text.literal("Sound"));
            sound.addEntry(entryBuilder.startBooleanToggle(Text.literal("Enable Sound"), config.soundEnabled)
                    .setDefaultValue(ModConfig.DEFAULT_SOUND_ENABLED)
                    .setSaveConsumer(value -> config.soundEnabled = value)
                    .build());
            sound.addEntry(entryBuilder.startFloatField(Text.literal("Volume"), config.soundVolume)
                    .setDefaultValue(ModConfig.DEFAULT_SOUND_VOLUME)
                    .setSaveConsumer(value -> config.soundVolume = value)
                    .setMin(0.0f)
                    .setMax(10.0f)
                    .build());
            sound.addEntry(entryBuilder.startFloatField(Text.literal("Pitch"), config.soundPitch)
                    .setDefaultValue(ModConfig.DEFAULT_SOUND_PITCH)
                    .setSaveConsumer(value -> config.soundPitch = value)
                    .setMin(0.0f)
                    .setMax(10.0f)
                    .build());
            sound.addEntry(entryBuilder.startTextDescription(Text.literal(" ")).build());
            sound.addEntry(entryBuilder.startBooleanToggle(Text.literal("Restore Defaults (Sound)"), false)
                    .setSaveConsumer(value -> { if (value) config.restoreDefaultsSound(); })
                    .build());

            builder.setSavingRunnable(config::save);
            return builder.build();
        };
    }
}