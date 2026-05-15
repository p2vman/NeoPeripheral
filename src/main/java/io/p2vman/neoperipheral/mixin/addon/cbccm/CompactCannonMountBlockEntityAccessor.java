package io.p2vman.neoperipheral.mixin.addon.cbccm;

import com.cubester.cbc_compact_mount.content.CompactCannonMountBlockEntity;
import com.simibubi.create.content.contraptions.AssemblyException;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(CompactCannonMountBlockEntity.class)
public interface CompactCannonMountBlockEntityAccessor {
    @Accessor("cannonPitch")
    float getPitch();

    @Accessor("cannonPitch")
    void setPitch(float value);

    @Invoker("assemble")
    void Assemble() throws AssemblyException;
}
