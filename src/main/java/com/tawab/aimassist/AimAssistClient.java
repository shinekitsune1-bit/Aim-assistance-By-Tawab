package com.tawab.aimassist;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public class AimAssistClient implements ClientModInitializer {

    public static final String MOD_ID = "aimassisttawab";

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
                            Text.literal(
                                    "Aim Assist: " +
                                            (AimAssistConfig.enabled ? "ON" : "OFF")
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

            if (AimAssistConfig.enabled) {
                aimAtNearestTarget(client);
            }
        });

        System.out.println("[Aim Assist By Tawab] Loaded successfully!");
    }

    private static void aimAtNearestTarget(MinecraftClient client) {

        ClientPlayerEntity player = client.player;

        if (player == null || client.world == null) {
            return;
        }

        LivingEntity target = null;
        double closestDistance = AimAssistConfig.distance;

        for (LivingEntity entity : client.world.getEntitiesByClass(
                LivingEntity.class,
                player.getBoundingBox().expand(AimAssistConfig.distance),
                entity -> entity != player
                        && entity.isAlive()
                        && !entity.isSpectator()
        )) {

            double distance = player.distanceTo(entity);

            if (distance < closestDistance) {
                closestDistance = distance;
                target = entity;
            }
        }

        if (target == null) {
            return;
        }

        double dx = target.getX() - player.getX();
        double dy = target.getEyeY() - player.getEyeY();
        double dz = target.getZ() - player.getZ();

        double horizontalDistance =
                Math.sqrt(dx * dx + dz * dz);

        float targetYaw =
                (float) (Math.toDegrees(
                        Math.atan2(dz, dx)
                ) - 90.0);

        float targetPitch =
                (float) (-Math.toDegrees(
                        Math.atan2(
                                dy,
                                horizontalDistance
                        )
                ));

        float strength =
                (float) (AimAssistConfig.strength / 100.0);

        float smoothness =
                (float) (AimAssistConfig.smoothness / 100.0);

        float factor =
                Math.max(
                        0.01f,
                        strength * smoothness
                );

        float newYaw =
                player.getYaw()
                        + wrapDegrees(targetYaw - player.getYaw())
                        * factor;

        float newPitch =
                player.getPitch()
                        + (targetPitch - player.getPitch())
                        * factor;

        player.setYaw(newYaw);
        player.setPitch(newPitch);
    }

    private static float wrapDegrees(float degrees) {

        while (degrees >= 180.0f) {
            degrees -= 360.0f;
        }

        while (degrees < -180.0f) {
            degrees += 360.0f;
        }

        return degrees;
    }

    public static void toggle() {
        AimAssistConfig.enabled =
                !AimAssistConfig.enabled;
    }

    public static boolean isAimAssistEnabled() {
        return AimAssistConfig.enabled;
    }
                        }
