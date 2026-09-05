package com.tawab.aimassist;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.text.Text;

public class AimAssistScreen extends Screen {

    private StrengthSlider strengthSlider;
    private DistanceSlider distanceSlider;
    private SmoothnessSlider smoothnessSlider;

    public AimAssistScreen() {
        super(Text.literal("Aim Assist By Tawab"));
    }

    @Override
    protected void init() {

        int centerX = this.width / 2;

        this.addDrawableChild(
                ButtonWidget.builder(
                        Text.literal(
                                AimAssistConfig.enabled
                                        ? "Aim Assist: ON"
                                        : "Aim Assist: OFF"
                        ),
                        button -> {
                            AimAssistConfig.enabled =
                                    !AimAssistConfig.enabled;

                            button.setMessage(
                                    Text.literal(
                                            AimAssistConfig.enabled
                                                    ? "Aim Assist: ON"
                                                    : "Aim Assist: OFF"
                                    )
                            );
                        }
                ).dimensions(
                        centerX - 100,
                        60,
                        200,
                        20
                ).build()
        );

        strengthSlider = new StrengthSlider(
                centerX - 100,
                95,
                200,
                20
        );

        distanceSlider = new DistanceSlider(
                centerX - 100,
                130,
                200,
                20
        );

        smoothnessSlider = new SmoothnessSlider(
                centerX - 100,
                165,
                200,
                20
        );

        this.addDrawableChild(strengthSlider);
        this.addDrawableChild(distanceSlider);
        this.addDrawableChild(smoothnessSlider);

        this.addDrawableChild(
                ButtonWidget.builder(
                        Text.literal("Done"),
                        button -> close()
                ).dimensions(
                        centerX - 100,
                        205,
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

        // Simple background — no blur
        context.fill(
                0,
                0,
                this.width,
                this.height,
                0xCC101010
        );

        context.drawCenteredTextWithShadow(
                this.textRenderer,
                this.title,
                this.width / 2,
                30,
                0xFFFFFF
        );

        super.render(
                context,
                mouseX,
                mouseY,
                delta
        );
    }

    private static class StrengthSlider extends SliderWidget {

        StrengthSlider(
                int x,
                int y,
                int width,
                int height
        ) {
            super(
                    x,
                    y,
                    width,
                    height,
                    Text.literal(""),
                    AimAssistConfig.strength / 100.0
            );

            updateMessage();
        }

        @Override
        protected void updateMessage() {
            setMessage(
                    Text.literal(
                            "Strength: " +
                                    Math.round(value * 100) +
                                    "%"
                    )
            );
        }

        @Override
        protected void applyValue() {
            AimAssistConfig.strength =
                    value * 100.0;
        }
    }

    private static class DistanceSlider extends SliderWidget {

        DistanceSlider(
                int x,
                int y,
                int width,
                int height
        ) {
            super(
                    x,
                    y,
                    width,
                    height,
                    Text.literal(""),
                    (AimAssistConfig.distance - 1.0) / 19.0
            );

            updateMessage();
        }

        @Override
        protected void updateMessage() {

            double distance =
                    1.0 + value * 19.0;

            setMessage(
                    Text.literal(
                            String.format(
                                    "Distance: %.1f blocks",
                                    distance
                            )
                    )
            );
        }

        @Override
        protected void applyValue() {

            AimAssistConfig.distance =
                    1.0 + value * 19.0;
        }
    }

    private static class SmoothnessSlider extends SliderWidget {

        SmoothnessSlider(
                int x,
                int y,
                int width,
                int height
        ) {
            super(
                    x,
                    y,
                    width,
                    height,
                    Text.literal(""),
                    AimAssistConfig.smoothness / 100.0
            );

            updateMessage();
        }

        @Override
        protected void updateMessage() {

            setMessage(
                    Text.literal(
                            "Smoothness: " +
                                    Math.round(value * 100) +
                                    "%"
                    )
            );
        }

        @Override
        protected void applyValue() {

            AimAssistConfig.smoothness =
                    value * 100.0;
        }
    }
                }                                            AimAssistConfig.enabled
                                                    ? "Aim Assist: ON"
                                                    : "Aim Assist: OFF"
                                    )
                            );
                        }
                ).dimensions(
                        centerX - 100,
                        60,
                        200,
                        20
                ).build()
        );

        strengthSlider = new StrengthSlider(
                centerX - 100,
                95,
                200,
                20
        );

        distanceSlider = new DistanceSlider(
                centerX - 100,
                130,
                200,
                20
        );

        smoothnessSlider = new SmoothnessSlider(
                centerX - 100,
                165,
                200,
                20
        );

        this.addDrawableChild(strengthSlider);
        this.addDrawableChild(distanceSlider);
        this.addDrawableChild(smoothnessSlider);

        this.addDrawableChild(
                ButtonWidget.builder(
                        Text.literal("Done"),
                        button -> close()
                ).dimensions(
                        centerX - 100,
                        205,
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
                30,
                0xFFFFFF
        );

        super.render(
                context,
                mouseX,
                mouseY,
                delta
        );
    }

    private static class StrengthSlider extends SliderWidget {

        StrengthSlider(
                int x,
                int y,
                int width,
                int height
        ) {
            super(
                    x,
                    y,
                    width,
                    height,
                    Text.literal(""),
                    AimAssistConfig.strength / 100.0
            );

            updateMessage();
        }

        @Override
        protected void updateMessage() {
            setMessage(
                    Text.literal(
                            "Strength: " +
                                    Math.round(value * 100) +
                                    "%"
                    )
            );
        }

        @Override
        protected void applyValue() {
            AimAssistConfig.strength =
                    value * 100.0;
        }
    }

    private static class DistanceSlider extends SliderWidget {

        DistanceSlider(
                int x,
                int y,
                int width,
                int height
        ) {
            super(
                    x,
                    y,
                    width,
                    height,
                    Text.literal(""),
                    (AimAssistConfig.distance - 1.0) / 19.0
            );

            updateMessage();
        }

        @Override
        protected void updateMessage() {

            double distance =
                    1.0 + value * 19.0;

            setMessage(
                    Text.literal(
                            String.format(
                                    "Distance: %.1f blocks",
                                    distance
                            )
                    )
            );
        }

        @Override
        protected void applyValue() {

            AimAssistConfig.distance =
                    1.0 + value * 19.0;
        }
    }

    private static class SmoothnessSlider extends SliderWidget {

        SmoothnessSlider(
                int x,
                int y,
                int width,
                int height
        ) {
            super(
                    x,
                    y,
                    width,
                    height,
                    Text.literal(""),
                    AimAssistConfig.smoothness / 100.0
            );

            updateMessage();
        }

        @Override
        protected void updateMessage() {

            setMessage(
                    Text.literal(
                            "Smoothness: " +
                                    Math.round(value * 100) +
                                    "%"
                    )
            );
        }

        @Override
        protected void applyValue() {

            AimAssistConfig.smoothness =
                    value * 100.0;
        }
    }
            }
