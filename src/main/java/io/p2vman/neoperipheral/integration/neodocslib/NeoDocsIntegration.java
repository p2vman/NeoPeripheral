package io.p2vman.neoperipheral.integration.neodocslib;

import io.p2vman.neodocslib.api.DocumentationCategory;
import io.p2vman.neodocslib.api.DocumentationPage;
import io.p2vman.neodocslib.api.DocumentationPageMeta;
import io.p2vman.neodocslib.api.DocumentationRegistry;
import io.p2vman.neodocslib.core.builtin.ExampleSymbolResolver;
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

        var catRoot = ResourceLocation.fromNamespaceAndPath(Neoperipheral.MODID, "core");
        var catArch = ResourceLocation.fromNamespaceAndPath(Neoperipheral.MODID, "core/architecture");
        DocumentationRegistry.registerCategory(new DocumentationCategory(catRoot, "Core", null, 0));
        DocumentationRegistry.registerCategory(new DocumentationCategory(catArch, "Architecture", catRoot, 10));

        DocumentationRegistry.register(new DocumentationPage(HOME, "NeoDocsLib", source), new DocumentationPageMeta(catRoot, 0));
        DocumentationRegistry.register(new DocumentationPage(ResourceLocation.fromNamespaceAndPath(Neoperipheral.MODID, "architecture"), "Architecture", source), new DocumentationPageMeta(catArch, 0));
        DocumentationRegistry.register(new DocumentationPage(ResourceLocation.fromNamespaceAndPath(Neoperipheral.MODID, "resource_packs"), "Resource Packs", source), new DocumentationPageMeta(catRoot, 10));
        DocumentationRegistry.register(new DocumentationPage(ResourceLocation.fromNamespaceAndPath(Neoperipheral.MODID, "symbols"), "Symbols", source), new DocumentationPageMeta(catRoot, 20));
        DocumentationRegistry.register(new DocumentationPage(ResourceLocation.fromNamespaceAndPath(Neoperipheral.MODID, "examples"), "Examples", source), new DocumentationPageMeta(catRoot, 30));

        DocumentationRegistry.registerSymbolResolver(new ExampleSymbolResolver());

        // Export some example symbols so [[EventBus]] can resolve even without a custom resolver.
        //DocumentationRegistry.exportSymbol("NeoDocsLib", HOME);
        //DocumentationRegistry.exportSymbol("EventBus", ResourceLocation.fromNamespaceAndPath(Neoperipheral.MODID, "symbols"));
        //DocumentationRegistry.exportSymbol("TaskLoopExecutor", ResourceLocation.fromNamespaceAndPath(Neoperipheral.MODID, "symbols"));
    }
}
