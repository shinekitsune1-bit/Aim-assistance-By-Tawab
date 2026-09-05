package com.tawab.aimassist;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class AimAssistClient implements ClientModInitializer {

    public static final String MOD_ID = "aimassisttawab";

    private static boolean aimAssistEnabled = false;

    private static KeyBinding toggleKey;
    private static KeyBinding settingsKey;

    @Override
    public void onInitializeClient() {

        toggleKey = KeyBindingHelper.registerKeyBinding(
                new KeyBinding(
                        "key.aimassisttawab.toggle",
                        InputUtil.Type.KEYSYM,
                        GLFW.GLFW_KEY_EQUAL,
                        "category.aimassisttawab"
                )
        );

        settingsKey = KeyBindingHelper.registerKeyBinding(
                new KeyBinding(
                        "key.aimassisttawab.settings",
                        InputUtil.Type.KEYSYM,
                        GLFW.GLFW_KEY_RIGHT_SHIFT,
                        "category.aimassisttawab"
                )
        );

        ClientTickEvents.END_CLIENT_TICK.register(client -> {

            while (toggleKey.wasPressed()) {
                toggle();

                if (client.player != null) {
                    client.player.sendMessage(
                            net.minecraft.text.Text.literal(
                                    "Aim Assist: " +
                                            (aimAssistEnabled ? "ON" : "OFF")
                            ),
                            true
                    );
                }
            }

            while (settingsKey.wasPressed()) {
                if (client.currentScreen == null) {
                    client.setScreen(new AimAssistScreen());
                }
            }
        });

        System.out.println(
                "[Aim Assist By Tawab] Loaded successfully!"
        );
    }

    public static void toggle() {
        aimAssistEnabled = !aimAssistEnabled;
    }

    public static boolean isAimAssistEnabled() {
        return aimAssistEnabled;
    }
}
