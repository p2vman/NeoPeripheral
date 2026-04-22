package io.p2vman.neoperipheral.client;

import io.p2vman.neoperipheral.ModRegistry;
import io.p2vman.neoperipheral.Neoperipheral;
import io.p2vman.neoperipheral.client.render.SocketBlockEntityRenderer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@EventBusSubscriber(modid = Neoperipheral.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class ClientModEvents {
    private ClientModEvents() {}

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(ModRegistry.SOCKET_BLOCK_ENTITY.get(), SocketBlockEntityRenderer::new);
    }
}

