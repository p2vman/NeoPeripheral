package io.p2vman.neoperipheral.mixin.addon.synaxis;

import com.verr1.synaxis.content.blocks.camera.CameraBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(CameraBlockEntity.class)
public interface CameraBlockEntityAccessor {
    @Invoker("applyRayEnabled")
    void setRayEnabled(boolean enabled);

    @Invoker("applyRayRange")
    void setRayRange(double range);
}
