package com.tawab.aimassist;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class AimAssistScreen extends Screen {

    public AimAssistScreen() {
        super(Text.literal("Aim Assist By Tawab"));
    }

    @Override
    protected void init() {

        int centerX = this.width / 2;

        this.addDrawableChild(
                ButtonWidget.builder(
                        Text.literal(
                                "Aim Assist: " +
                                        (AimAssistClient.isAimAssistEnabled()
                                                ? "ON"
                                                : "OFF")
                        ),
                        button -> {

                            AimAssistClient.toggle();

                            button.setMessage(
                                    Text.literal(
                                            "Aim Assist: " +
                                                    (AimAssistClient.isAimAssistEnabled()
                                                            ? "ON"
                                                            : "OFF")
                                    )
                            );
                        }
                ).dimensions(
                        centerX - 100,
                        80,
                        200,
                        20
                ).build()
        );

        this.addDrawableChild(
                ButtonWidget.builder(
                        Text.literal("Done"),
                        button -> close()
                ).dimensions(
                        centerX - 100,
                        120,
                        200,
                        20
                ).build()
        );
    }

    @Override
    public void render(
            DrawContext context,
            int mouseX,
            int mouseY,
            float delta
    ) {

        this.renderBackground(
                context,
                mouseX,
                mouseY,
                delta
        );

        context.drawCenteredTextWithShadow(
                this.textRenderer,
                this.title,
                this.width / 2,
                40,
                0xFFFFFF
        );

        super.render(
                context,
                mouseX,
                mouseY,
                delta
        );
    }
            }                )
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
