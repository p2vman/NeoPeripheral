package io.p2vman.neoperipheral.mixin.addon.cbc;

import com.simibubi.create.content.contraptions.AssemblyException;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;
import rbasamoyai.createbigcannons.cannon_control.fixed_cannon_mount.FixedCannonMountBlockEntity;

@Mixin(FixedCannonMountBlockEntity.class)
public interface FixedCannonMountBlockEntityAccessor {
    @Accessor("cannonYaw")
    float getYaw();
    @Accessor("cannonPitch")
    float getPitch();
    @Accessor
    boolean isRunning();

    @Accessor("cannonYaw")
    void setYaw(float value);
    @Accessor("cannonPitch")
    void setPitch(float value);

    @Invoker("assemble")
    void Assemble() throws AssemblyException;


}
