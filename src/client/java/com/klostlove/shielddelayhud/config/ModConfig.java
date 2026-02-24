package com.klostlove.shielddelayhud.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ModConfig {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path CONFIG_PATH = Path.of("config/shielddelayhud.json");

    public static ModConfig INSTANCE;

    // ================= DEFAULT VALUES =================
    public static final boolean DEFAULT_ENABLED = true;
    public static final boolean DEFAULT_SHOW_MS = true;
    public static final boolean DEFAULT_CENTER = true;

    public static final int DEFAULT_X = 2;
    public static final int DEFAULT_Y = 2;

    public static final int DEFAULT_OPACITY = 100;

    public static final int DEFAULT_NOT_READY_COLOR = 0xFF5555;
    public static final int DEFAULT_READY_COLOR = 0x55FF55;

    // SOUND defaults
    public static final boolean DEFAULT_SOUND_ENABLED = false;
    public static final float DEFAULT_SOUND_VOLUME = 1.0f;
    public static final float DEFAULT_SOUND_PITCH = 1.0f;

    // ================= CONFIG VALUES =================
    public boolean enabled = DEFAULT_ENABLED;

    public int x = DEFAULT_X;
    public int y = DEFAULT_Y;

    public boolean centerHud = DEFAULT_CENTER;
    public boolean showMs = DEFAULT_SHOW_MS;

    public int textOpacity = DEFAULT_OPACITY;

    public int notReadyColor = DEFAULT_NOT_READY_COLOR;
    public int readyColor = DEFAULT_READY_COLOR;

    // ================= SOUND CONFIG =================
    public boolean soundEnabled = DEFAULT_SOUND_ENABLED;
    public float soundVolume = DEFAULT_SOUND_VOLUME;
    public float soundPitch = DEFAULT_SOUND_PITCH;

    // ================= LOAD / SAVE =================
    public static ModConfig load() {
        try {
            if (Files.exists(CONFIG_PATH)) {
                INSTANCE = GSON.fromJson(Files.readString(CONFIG_PATH), ModConfig.class);
                return INSTANCE;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        INSTANCE = new ModConfig();
        INSTANCE.save();
        return INSTANCE;
    }

    public void save() {
        try {
            Files.createDirectories(CONFIG_PATH.getParent());
            Files.writeString(CONFIG_PATH, GSON.toJson(this));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void restoreDefaults() {
        restoreDefaultsGeneral();
        restoreDefaultsPosition();
        restoreDefaultsSound();
        save();
    }

    public void restoreDefaultsGeneral() {
        enabled = DEFAULT_ENABLED;
        showMs = DEFAULT_SHOW_MS;
        textOpacity = DEFAULT_OPACITY;
        notReadyColor = DEFAULT_NOT_READY_COLOR;
        readyColor = DEFAULT_READY_COLOR;
    }

    public void restoreDefaultsPosition() {
        centerHud = DEFAULT_CENTER;
        x = DEFAULT_X;
        y = DEFAULT_Y;
    }

    public void restoreDefaultsSound() {
        soundEnabled = DEFAULT_SOUND_ENABLED;
        soundVolume = DEFAULT_SOUND_VOLUME;
        soundPitch = DEFAULT_SOUND_PITCH;
    }
}