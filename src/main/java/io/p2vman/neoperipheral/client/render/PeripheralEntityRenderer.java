package io.p2vman.neoperipheral.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import io.p2vman.neoperipheral.block.entity.BasePeripheralBlockEntity;
import io.p2vman.neoperipheral.peripheral.IPeripheralRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;

public class PeripheralEntityRenderer implements BlockEntityRenderer<BasePeripheralBlockEntity> {
    public PeripheralEntityRenderer(BlockEntityRendererProvider.Context context) {

    }

    @Override
    public void render(BasePeripheralBlockEntity basePeripheralBlockEntity, float v, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, int i1) {
        var pref = basePeripheralBlockEntity.getPeripheral(Direction.DOWN);
        if (pref != null && pref instanceof IPeripheralRenderer renderer) {
            renderer.render(v, poseStack, multiBufferSource, i, i1);
        }
    }
}
