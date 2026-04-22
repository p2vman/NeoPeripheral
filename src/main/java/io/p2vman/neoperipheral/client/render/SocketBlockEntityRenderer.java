package io.p2vman.neoperipheral.client.render;

import io.p2vman.neoperipheral.block.entity.SocketBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public final class SocketBlockEntityRenderer implements BlockEntityRenderer<SocketBlockEntity> {
    public SocketBlockEntityRenderer(BlockEntityRendererProvider.Context context) {

    }

    @Override
    public void render(SocketBlockEntity be, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay) {
        var level = be.getLevel();
        if (level == null) return;

        var itemRenderer = Minecraft.getInstance().getItemRenderer();

        for (int i = 0; i < 4; i++) {
            ItemStack stack = be.getModuleItem(i);
            if (stack.isEmpty()) continue;

            int gx = i & 1;
            int gz = (i >> 1) & 1;

            float x = (gx * 0.435f)+0.5f;
            float z = (gz * 0.435f)+0.5f;
            float y = 0.55f;

            poseStack.pushPose();
            poseStack.translate(x, y, z);
            poseStack.mulPose(Axis.XP.rotationDegrees(90.0f));

            int seed = be.getBlockPos().hashCode() * 31 + i;
            itemRenderer.renderStatic(stack, ItemDisplayContext.FIXED, packedLight, packedOverlay, poseStack, buffer, level, seed);
            poseStack.popPose();
        }
    }
}
