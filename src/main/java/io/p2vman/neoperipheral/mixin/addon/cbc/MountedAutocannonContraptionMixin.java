package io.p2vman.neoperipheral.mixin.addon.cbc;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import io.p2vman.neoperipheral.integration.cbc.MountedAutocannonContraptionProxy;
import net.minecraft.server.level.ServerLevel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import rbasamoyai.createbigcannons.cannon_control.contraption.MountedAutocannonContraption;
import rbasamoyai.createbigcannons.cannon_control.contraption.PitchOrientedContraptionEntity;

@Mixin(MountedAutocannonContraption.class)
public class MountedAutocannonContraptionMixin implements MountedAutocannonContraptionProxy {
    @Unique
    public boolean neoperipheral$cc_call = false;

    @ModifyExpressionValue(
            method = "fireShot",
            at = @At(
                    value = "INVOKE",
                    target = "Lrbasamoyai/createbigcannons/cannons/autocannon/breech/AbstractAutocannonBreechBlockEntity;canFire()Z"
            )
    )
    public boolean checkIsCalledByComputer(boolean original) {
        return original || this.neoperipheral$cc_call;
    }

    @Inject(
            method = "fireShot",
            at = @At("RETURN")
    )
    public void reset(ServerLevel level, PitchOrientedContraptionEntity entity, CallbackInfo ci) {
        this.neoperipheral$cc_call = false;
    }


    @Override
    public void setNeoperipheral$cc_call(boolean contraption$cc_call) {
        this.neoperipheral$cc_call = contraption$cc_call;
    }

    @Override
    public boolean getNeoperipheral$cc_call() {
        return this.neoperipheral$cc_call;
    }
}
