package io.p2vman.neoperipheral.mixin.addon.synaxis;

import com.verr1.synaxis.content.blocks.anchor.AnchorBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(AnchorBlockEntity.class)
public interface AnchorBlockEntityAccessor {
    @Invoker("applyAirResistance")
    void setAirResistance(double airResistance);

    @Invoker("applyExtraGravity")
    void setExtraGravity(double gravity);

    @Invoker("applyRotationalResistance")
    void setRotationalResistance(double rotationalResistance);
}
