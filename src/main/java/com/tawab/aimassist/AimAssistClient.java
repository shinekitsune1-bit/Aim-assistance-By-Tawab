package com.tawab.aimassist;

import net.fabricmc.api.ClientModInitializer;

public class AimAssistClient implements ClientModInitializer {

    public static final String MOD_ID = "aimassisttawab";

    @Override
    public void onInitializeClient() {
        System.out.println("[Aim Assist By Tawab] Mod loaded!");
    }
}
