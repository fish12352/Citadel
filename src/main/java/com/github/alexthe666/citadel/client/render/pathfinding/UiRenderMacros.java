package com.github.alexthe666.citadel.client.render.pathfinding;

import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.joml.Quaternionf;
import com.mojang.math.Axis;

/**
 * UI渲染工具类，提供基础的2D渲染功能
 */
public class UiRenderMacros
{
    public static final double HALF_BIAS = 0.5;

    public static void drawLineRectGradient(final PoseStack ps,
                                            final int x,
                                            final int y,
                                            final int w,
                                            final int h,
                                            final int argbColorStart,
                                            final int argbColorEnd)
    {
        drawLineRectGradient(ps, x, y, w, h, argbColorStart, argbColorEnd, 1);
    }

    public static void drawLineRectGradient(final PoseStack ps,
                                            final int x,
                                            final int y,
                                            final int w,
                                            final int h,
                                            final int argbColorStart,
                                            final int argbColorEnd,
                                            final int lineWidth)
    {
        GuiGraphics graphics = new GuiGraphics(Minecraft.getInstance(), Minecraft.getInstance().renderBuffers().bufferSource());
        graphics.pose().pushPose();
        graphics.pose().mulPose(ps.last().pose());
        
        // Draw gradient outline
        graphics.fillGradient(x, y, x + w, y + lineWidth, argbColorStart, argbColorStart); // Top
        graphics.fillGradient(x, y + h - lineWidth, x + w, y + h, argbColorEnd, argbColorEnd); // Bottom
        graphics.fillGradient(x, y + lineWidth, x + lineWidth, y + h - lineWidth, argbColorStart, argbColorEnd); // Left
        graphics.fillGradient(x + w - lineWidth, y + lineWidth, x + w, y + h - lineWidth, argbColorStart, argbColorEnd); // Right
        
        graphics.pose().popPose();
        graphics.flush();
    }

    public static void drawLineRect(final PoseStack ps, final int x, final int y, final int w, final int h, final int argbColor)
    {
        drawLineRect(ps, x, y, w, h, argbColor, 1);
    }

    public static void drawLineRect(final PoseStack ps,
                                    final int x,
                                    final int y,
                                    final int w,
                                    final int h,
                                    final int argbColor,
                                    final int lineWidth)
    {
        GuiGraphics graphics = new GuiGraphics(Minecraft.getInstance(), Minecraft.getInstance().renderBuffers().bufferSource());
        graphics.pose().pushPose();
        graphics.pose().mulPose(ps.last().pose());
        
        // Draw solid color outline
        graphics.fill(x, y, x + w, y + lineWidth, argbColor); // Top
        graphics.fill(x, y + h - lineWidth, x + w, y + h, argbColor); // Bottom
        graphics.fill(x, y + lineWidth, x + lineWidth, y + h - lineWidth, argbColor); // Left
        graphics.fill(x + w - lineWidth, y + lineWidth, x + w, y + h - lineWidth, argbColor); // Right
        
        graphics.pose().popPose();
        graphics.flush();
    }

    public static void fill(final PoseStack ps, final int x, final int y, final int w, final int h, final int argbColor)
    {
        GuiGraphics graphics = new GuiGraphics(Minecraft.getInstance(), Minecraft.getInstance().renderBuffers().bufferSource());
        graphics.pose().pushPose();
        graphics.pose().mulPose(ps.last().pose());
        graphics.fill(x, y, x + w, y + h, argbColor);
        graphics.pose().popPose();
        graphics.flush();
    }

    public static void fillGradient(final PoseStack ps,
                                    final int x,
                                    final int y,
                                    final int w,
                                    final int h,
                                    final int argbColorStart,
                                    final int argbColorEnd)
    {
        GuiGraphics graphics = new GuiGraphics(Minecraft.getInstance(), Minecraft.getInstance().renderBuffers().bufferSource());
        graphics.pose().pushPose();
        graphics.pose().mulPose(ps.last().pose());
        graphics.fillGradient(x, y, x + w, y + h, argbColorStart, argbColorEnd);
        graphics.pose().popPose();
        graphics.flush();
    }

    public static void hLine(final PoseStack ps, final int x, final int xEnd, final int y, final int argbColor)
    {
        line(ps, x, y, xEnd, y, (argbColor >> 16) & 0xff, (argbColor >> 8) & 0xff, argbColor & 0xff, (argbColor >> 24) & 0xff);
    }

    public static void hLine(final PoseStack ps,
                             final int x,
                             final int xEnd,
                             final int y,
                             final int red,
                             final int green,
                             final int blue,
                             final int alpha)
    {
        line(ps, x, y, xEnd, y, red, green, blue, alpha);
    }

    public static void vLine(final PoseStack ps, final int x, final int y, final int yEnd, final int argbColor)
    {
        line(ps, x, y, x, yEnd, (argbColor >> 16) & 0xff, (argbColor >> 8) & 0xff, argbColor & 0xff, (argbColor >> 24) & 0xff);
    }

    public static void vLine(final PoseStack ps,
                             final int x,
                             final int y,
                             final int yEnd,
                             final int red,
                             final int green,
                             final int blue,
                             final int alpha)
    {
        line(ps, x, y, x, yEnd, red, green, blue, alpha);
    }

    public static void line(final PoseStack ps, final int x, final int y, final int xEnd, final int yEnd, final int argbColor)
    {
        line(ps, x, y, xEnd, yEnd, (argbColor >> 16) & 0xff, (argbColor >> 8) & 0xff, argbColor & 0xff, (argbColor >> 24) & 0xff);
    }

    public static void line(final PoseStack ps,
                            final int x,
                            final int y,
                            final int xEnd,
                            final int yEnd,
                            final int red,
                            final int green,
                            final int blue,
                            final int alpha)
    {
        GuiGraphics graphics = new GuiGraphics(Minecraft.getInstance(), Minecraft.getInstance().renderBuffers().bufferSource());
        graphics.pose().pushPose();
        graphics.pose().mulPose(ps.last().pose());
        
        // Draw line using fill with minimal width
        double angle = Math.atan2(yEnd - y, xEnd - x);
        int length = (int) Math.sqrt((xEnd - x) * (xEnd - x) + (yEnd - y) * (yEnd - y));
        graphics.pose().pushPose();
        graphics.pose().translate(x, y, 0);
        graphics.pose().mulPose(Axis.ZP.rotation((float)angle));
        graphics.fill(0, -1, length, 1, (alpha << 24) | (red << 16) | (green << 8) | blue);
        graphics.pose().popPose();
        
        graphics.pose().popPose();
        graphics.flush();
    }

    public static void blit(final PoseStack ps, final ResourceLocation rl, final int x, final int y, final int w, final int h)
    {
        GuiGraphics graphics = new GuiGraphics(Minecraft.getInstance(), Minecraft.getInstance().renderBuffers().bufferSource());
        graphics.pose().pushPose();
        graphics.pose().mulPose(ps.last().pose());
        graphics.blit(rl, x, y, 0, 0, w, h, w, h);
        graphics.pose().popPose();
        graphics.flush();
    }

    public static void drawEntity(final PoseStack poseStack,
                            final int x,
                            final int y,
                                final double scale,
                                final float headYaw,
                                final float yaw,
                                final float pitch,
                                final Entity entity)
    {
        if (entity == null || entity.level() == null) return;
        
        final LivingEntity livingEntity = (entity instanceof LivingEntity) ? (LivingEntity) entity : null;
        final Minecraft mc = Minecraft.getInstance();
        
        GuiGraphics graphics = new GuiGraphics(mc, mc.renderBuffers().bufferSource());
        graphics.pose().pushPose();
        graphics.pose().mulPose(poseStack.last().pose());
        graphics.pose().translate(x, y, 1050.0F);
        graphics.pose().scale(1.0F, 1.0F, -1.0F);
        graphics.pose().translate(0.0D, 0.0D, 1000.0D);
        graphics.pose().scale((float) scale, (float) scale, (float) scale);
        
        final Quaternionf pitchRotation = Axis.XP.rotationDegrees(pitch);
        graphics.pose().mulPose(Axis.ZP.rotationDegrees(180.0F));
        graphics.pose().mulPose(pitchRotation);
        
        // Store original entity rotations
        final float oldYaw = entity.getYRot();
        final float oldPitch = entity.getXRot();
        final float oldYawOffset = livingEntity == null ? 0F : livingEntity.yBodyRot;
        final float oldPrevYawHead = livingEntity == null ? 0F : livingEntity.yHeadRotO;
        final float oldYawHead = livingEntity == null ? 0F : livingEntity.yHeadRot;
        
        // Set new rotations
        entity.setYRot(180.0F + headYaw);
        entity.setXRot(-pitch);
        if (livingEntity != null) {
            livingEntity.yBodyRot = 180.0F + yaw;
            livingEntity.yHeadRot = entity.getYRot();
            livingEntity.yHeadRotO = entity.getYRot();
        }
        
        // Render entity
        Lighting.setupForEntityInInventory();
        EntityRenderDispatcher dispatcher = mc.getEntityRenderDispatcher();
        pitchRotation.conjugate();
        dispatcher.overrideCameraOrientation(pitchRotation);
        dispatcher.setRenderShadow(false);
        
        RenderSystem.runAsFancy(() -> {
            dispatcher.render(entity, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F, graphics.pose(), graphics.bufferSource(), 0x00F000F0);
        });
        
        graphics.flush();
        
        // Restore original rotations
        dispatcher.setRenderShadow(true);
        entity.setYRot(oldYaw);
        entity.setXRot(oldPitch);
        if (livingEntity != null) {
            livingEntity.yBodyRot = oldYawOffset;
            livingEntity.yHeadRotO = oldPrevYawHead;
            livingEntity.yHeadRot = oldYawHead;
        }
        
        graphics.pose().popPose();
        Lighting.setupFor3DItems();
    }
}
