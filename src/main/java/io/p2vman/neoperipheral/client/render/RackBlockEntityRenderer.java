package io.p2vman.neoperipheral.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import io.p2vman.neoperipheral.block.entity.RackBlockEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;

public class RackBlockEntityRenderer implements BlockEntityRenderer<RackBlockEntity> {
    public RackBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(RackBlockEntity rackBlockEntity, float v, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, int i1) {

    }
}
