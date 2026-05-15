package io.p2vman.neoperipheral.peripheral;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;

public interface IPeripheralRenderer {
    void render(float v, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, int i1);
}
