package com.github.alexthe666.citadel.mixin.client;

import com.github.alexthe666.citadel.client.event.EventRenderSplashText;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.SplashRenderer;
import net.neoforged.neoforge.common.NeoForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SplashRenderer.class)
public class SplashRendererMixin {

    @Inject(method = "render", at = @At("HEAD"))
    private void citadel_render_before(GuiGraphics graphics, int width, int height, float partialTick, String splashText, int color, CallbackInfo ci) {
        EventRenderSplashText.Pre event = new EventRenderSplashText.Pre(splashText, graphics, partialTick, color);
        NeoForge.EVENT_BUS.post(event);
        if (event.isAllowed()) {
            ci.cancel();
        }
    }

    @Inject(method = "render", at = @At("RETURN"))
    private void citadel_render_after(GuiGraphics graphics, int width, int height, float partialTick, String splashText, int color, CallbackInfo ci) {
        EventRenderSplashText.Post event = new EventRenderSplashText.Post(splashText, graphics, partialTick);
        NeoForge.EVENT_BUS.post(event);
    }
}
