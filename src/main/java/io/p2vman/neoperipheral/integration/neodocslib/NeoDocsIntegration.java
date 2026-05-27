package io.p2vman.neoperipheral.integration.neodocslib;

import io.p2vman.neodocslib.core.source.DevOverlayDocumentationSource;
import io.p2vman.neoperipheral.Neoperipheral;
import io.p2vman.neoperipheral.integration.Integration;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;

@Integration(modid = "neodocslib")
public class NeoDocsIntegration {
    public static final ResourceLocation HOME = ResourceLocation.fromNamespaceAndPath(Neoperipheral.MODID, "home");
    public NeoDocsIntegration(IEventBus modBus) {
        var source = new DevOverlayDocumentationSource();

    }
}
