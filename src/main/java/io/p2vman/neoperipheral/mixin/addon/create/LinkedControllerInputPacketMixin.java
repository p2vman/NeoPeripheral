package io.p2vman.neoperipheral.mixin.addon.create;

import com.simibubi.create.content.redstone.link.controller.LinkedControllerInputPacket;
import io.p2vman.neoperipheral.component.HubLinkComponent;
import io.p2vman.neoperipheral.integration.create.block.entity.LinkedControllerHubBlockEntity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(LinkedControllerInputPacket.class)
public class LinkedControllerInputPacketMixin {
    @Shadow
    @Final
    private boolean press;

    @Shadow
    @Final
    private List<Integer> activatedButtons;

    @Inject(
            method = {"handleItem"},
            at = {@At("RETURN")},
            remap = false
    )
    private void neoperipheral$handleItem(ServerPlayer player, ItemStack heldItem, CallbackInfo ci) {
        HubLinkComponent.getHubPosOptional(heldItem).ifPresent(pos -> {
            if (!(player.level().getBlockEntity(pos) instanceof LinkedControllerHubBlockEntity entity)) return;
            entity.peripheral.get().receivePressed(this.activatedButtons, this.press);
        });
    }
}
