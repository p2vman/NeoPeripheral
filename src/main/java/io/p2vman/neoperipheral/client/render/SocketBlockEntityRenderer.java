package io.p2vman.neoperipheral.client.render;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexMultiConsumer;
import io.p2vman.neoperipheral.ModRegistry;
import io.p2vman.neoperipheral.block.entity.SocketBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.ModelBlockRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public final class SocketBlockEntityRenderer implements BlockEntityRenderer<SocketBlockEntity> {
    public SocketBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(SocketBlockEntity be, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay) {
        var level = be.getLevel();
        if (level == null) return;

        var itemRenderer = Minecraft.getInstance().getItemRenderer();
        var blockRenderer = Minecraft.getInstance().getBlockRenderer();
        var modelRenderer = blockRenderer.getModelRenderer();

        final int worldLight = LevelRenderer.getLightColor(level, be.getBlockPos().above());

        VertexConsumer vertexConsumer = VertexMultiConsumer.create(buffer.getBuffer(RenderType.solid()));

        BlockState blockState = ModRegistry.SOCKET_BLOCK.get().defaultBlockState();
        BakedModel model = blockRenderer.getBlockModel(blockState);


        modelRenderer.tesselateBlock(level, model, blockState, be.getBlockPos(), poseStack, vertexConsumer, false, level.getRandom(), 0, packedOverlay);

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
            itemRenderer.renderStatic(stack, ItemDisplayContext.FIXED, worldLight, packedOverlay, poseStack, buffer, level, seed);
            poseStack.popPose();
        }
    }
}
