package com.github.alexthe666.citadel.client.render.pathfinding;

import com.github.alexthe666.citadel.Citadel;
import com.github.alexthe666.citadel.server.entity.pathfinding.raycoms.MNode;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderBuffers;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

import java.util.ConcurrentModificationException;
import java.util.HashSet;
import java.util.Set;

public class PathfindingDebugRenderer {
    public static final RenderBuffers renderBuffers = new RenderBuffers(256);
    private static final MultiBufferSource.BufferSource renderBuffer = renderBuffers.bufferSource();
    /**
     * Set of visited nodes.
     */
    public static Set<MNode> lastDebugNodesVisited = new HashSet<>();

    /**
     * Set of not visited nodes.
     */
    public static Set<MNode> lastDebugNodesNotVisited = new HashSet<>();

    /**
     * Set of nodes that belong to the chosen path.
     */
    public static Set<MNode> lastDebugNodesPath = new HashSet<>();

    /**
     * Render debugging information for the pathfinding system.
     *
     * @param ctx rendering context
     */
    public static void render(final WorldEventContext ctx) {
        try {
            for (final MNode n : lastDebugNodesVisited) {
                debugDrawNode(n, 0xffff0000, ctx);
            }

            for (final MNode n : lastDebugNodesNotVisited) {
                debugDrawNode(n, 0xff0000ff, ctx);
            }

            for (final MNode n : lastDebugNodesPath) {
                if (n.isReachedByWorker()) {
                    debugDrawNode(n, 0xffff6600, ctx);
                } else {
                    debugDrawNode(n, 0xff00ff00, ctx);
                }
            }
        } catch (final ConcurrentModificationException exc) {
            Citadel.LOGGER.catching(exc);
        }
    }

    private static void debugDrawNode(final MNode n, final int argbColor, final WorldEventContext ctx) {
        ctx.poseStack.pushPose();
        ctx.poseStack.translate(n.pos.getX() + 0.375d, n.pos.getY() + 0.375d, n.pos.getZ() + 0.375d);

        final Entity entity = Minecraft.getInstance().getCameraEntity();
        if (n.pos.closerThan(entity.blockPosition(), 5d)) {
            renderDebugText(n, ctx);
        }

        ctx.poseStack.scale(0.25F, 0.25F, 0.25F);

        WorldRenderMacros.renderBox(ctx.bufferSource, ctx.poseStack, BlockPos.ZERO, BlockPos.ZERO, argbColor);

        if (n.parent != null) {
            final VertexConsumer buffer = ctx.bufferSource.getBuffer(WorldRenderMacros.LINES);
            PoseStack.Pose pose = ctx.poseStack.last();
            Matrix4f matrix = pose.pose();

            final float pdx = n.parent.pos.getX() - n.pos.getX() + 0.125f;
            final float pdy = n.parent.pos.getY() - n.pos.getY() + 0.125f;
            final float pdz = n.parent.pos.getZ() - n.pos.getZ() + 0.125f;

            float x1 = 0.5f;
            float y1 = 0.5f;
            float z1 = 0.5f;
            float x2 = pdx / 0.25f;
            float y2 = pdy / 0.25f;
            float z2 = pdz / 0.25f;

            // Transform vertices using matrix
            float v1x = matrix.m00() * x1 + matrix.m01() * y1 + matrix.m02() * z1 + matrix.m03();
            float v1y = matrix.m10() * x1 + matrix.m11() * y1 + matrix.m12() * z1 + matrix.m13();
            float v1z = matrix.m20() * x1 + matrix.m21() * y1 + matrix.m22() * z1 + matrix.m23();
            
            float v2x = matrix.m00() * x2 + matrix.m01() * y2 + matrix.m02() * z2 + matrix.m03();
            float v2y = matrix.m10() * x2 + matrix.m11() * y2 + matrix.m12() * z2 + matrix.m13();
            float v2z = matrix.m20() * x2 + matrix.m21() * y2 + matrix.m22() * z2 + matrix.m23();

            buffer.addVertex(v1x, v1y, v1z)
                .setColor(0.75F, 0.75F, 0.75F, 1.0F)
                .setNormal(0, 1, 0);

            buffer.addVertex(v2x, v2y, v2z)
                .setColor(0.75F, 0.75F, 0.75F, 1.0F)
                .setNormal(0, 1, 0);
        }

        ctx.poseStack.popPose();
    }

    private static void renderDebugText(@NotNull final MNode n, final WorldEventContext ctx) {
        final Font fontrenderer = Minecraft.getInstance().font;

        final String s1 = String.format("F: %.3f [%d]", n.getCost(), n.getCounterAdded());
        final String s2 = String.format("G: %.3f [%d]", n.getScore(), n.getCounterVisited());
        final int i = Math.max(fontrenderer.width(s1), fontrenderer.width(s2)) / 2;

        ctx.poseStack.pushPose();
        ctx.poseStack.translate(0.0F, 0.75F, 0.0F);

        ctx.poseStack.mulPose(Minecraft.getInstance().getEntityRenderDispatcher().cameraOrientation());
        ctx.poseStack.scale(-0.014F, -0.014F, 0.014F);
        ctx.poseStack.translate(0.0F, 18F, 0.0F);
        final Matrix4f mat = ctx.poseStack.last().pose();

        WorldRenderMacros.renderFillRectangle(ctx.bufferSource, ctx.poseStack, -i - 1, -5, 0, 2 * i + 2, 17, 0x7f000000);

        ctx.poseStack.translate(0.0F, -5F, -0.1F);
        fontrenderer.drawInBatch(s1, -fontrenderer.width(s1) / 2.0f, 1, 0xFFFFFFFF, false, mat, ctx.bufferSource, Font.DisplayMode.NORMAL, 0, 15728880);
        ctx.poseStack.translate(0.0F, 8F, -0.1F);
        fontrenderer.drawInBatch(s2, -fontrenderer.width(s2) / 2.0f, 1, 0xFFFFFFFF, false, mat, ctx.bufferSource, Font.DisplayMode.NORMAL, 0, 15728880);

        ctx.poseStack.popPose();
    }


}