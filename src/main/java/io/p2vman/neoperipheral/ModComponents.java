package io.p2vman.neoperipheral;

import com.mojang.serialization.Codec;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModComponents {
    public static final DeferredRegister.DataComponents REGISTRAR = DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, Neoperipheral.MODID);


    public static final Supplier<DataComponentType<Integer>> COLOR_COMPONENT = REGISTRAR.registerComponentType(
            "color",
            builder -> builder
                    .persistent(Codec.INT)
    );


}
