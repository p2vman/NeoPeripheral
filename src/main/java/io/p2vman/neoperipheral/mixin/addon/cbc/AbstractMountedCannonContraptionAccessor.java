package io.p2vman.neoperipheral.mixin.addon.cbc;

import net.minecraft.server.level.ServerLevel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;
import rbasamoyai.createbigcannons.cannon_control.contraption.AbstractMountedCannonContraption;
import rbasamoyai.createbigcannons.cannon_control.contraption.PitchOrientedContraptionEntity;

@Mixin(AbstractMountedCannonContraption.class)
public interface AbstractMountedCannonContraptionAccessor {
    @Invoker("fireShot")
    void FireShot(ServerLevel var1, PitchOrientedContraptionEntity var2);
}
