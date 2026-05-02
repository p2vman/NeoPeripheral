package io.p2vman.neoperipheral.mixin.addon.cbc;

import com.simibubi.create.content.contraptions.AssemblyException;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;
import rbasamoyai.createbigcannons.cannon_control.cannon_mount.CannonMountBlockEntity;

@Mixin(CannonMountBlockEntity.class)
public interface CannonMountBlockEntityAccessor {
    @Accessor("cannonYaw")
    float getYaw();

    @Accessor("cannonPitch")
    float getPitch();

    @Invoker("assemble")
    void Assemble() throws AssemblyException;
}
