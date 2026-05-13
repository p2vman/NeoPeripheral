package io.p2vman.neoperipheral;

import io.p2vman.neoperipheral.component.HubLinkComponent;
import io.p2vman.neoperipheral.component.NfcCardComponent;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.StreamCodec;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModComponents {
    public static final DeferredRegister.DataComponents REGISTRAR = DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, Neoperipheral.MODID);

    public static final Supplier<DataComponentType<NfcCardComponent>> NFC_DATA = REGISTRAR.register("nfc_data", () -> DataComponentType.<NfcCardComponent>builder()
            .persistent(NfcCardComponent.CODEC)
            .networkSynchronized(NfcCardComponent.STREAM_CODEC)
            .build());

    public static final Supplier<DataComponentType<HubLinkComponent>> HUB_LINK = REGISTRAR.register("hub_link", () -> DataComponentType.<HubLinkComponent>builder()
            .persistent(HubLinkComponent.CODEC)
            .networkSynchronized(HubLinkComponent.STREAM_CODEC)
            .build());
}