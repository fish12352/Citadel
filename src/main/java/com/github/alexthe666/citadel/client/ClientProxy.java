package com.github.alexthe666.citadel.client;

import com.github.alexthe666.citadel.client.event.EventRenderSplashText;
import com.github.alexthe666.citadel.config.CitadelClientConfig;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.RenderGuiEvent;
import net.neoforged.neoforge.client.event.InputEvent;
import org.lwjgl.glfw.GLFW;

public class ClientProxy {

    @SubscribeEvent
    public void onRenderSplashText(RenderGuiEvent event) {
        if (CitadelClientConfig.INSTANCE.enableSplashText.get()) {
            EventRenderSplashText.Pre splashEvent = new EventRenderSplashText.Pre(null, null, 0.0f, 0);
            if (CitadelClientConfig.INSTANCE.removeSplashText.get()) {
                splashEvent.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public void onKeyPressed(InputEvent.Key event) {
        if (event.getKey() == GLFW.GLFW_KEY_F3 && event.getModifiers() == GLFW.GLFW_MOD_ALT) {
            if (CitadelClientConfig.INSTANCE.enablePatreonEffects.get()) {
                // ... existing code ...
            }
        }
    }

    @SubscribeEvent
    public void renderSplashTextBefore(EventRenderSplashText.Pre event) {
        if (!CitadelClientConfig.INSTANCE.enableSplashText.get() || CitadelClientConfig.INSTANCE.removeSplashText.get()) {
            event.setCanceled(true);
        }
    }
} 