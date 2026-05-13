package io.p2vman.neoperipheral.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.p2vman.neoperipheral.ModComponents;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;

import java.util.Optional;

public record HubLinkComponent(int x, int y, int z) {
    public static final Codec<HubLinkComponent> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.INT.fieldOf("x").forGetter(HubLinkComponent::x),
                    Codec.INT.fieldOf("y").forGetter(HubLinkComponent::y),
                    Codec.INT.fieldOf("z").forGetter(HubLinkComponent::z)
            ).apply(instance, HubLinkComponent::new)
    );

    public static final StreamCodec<FriendlyByteBuf, HubLinkComponent> STREAM_CODEC =
            StreamCodec.of(
                    (buf, value) -> {
                        buf.writeVarInt(value.x);
                        buf.writeVarInt(value.y);
                        buf.writeVarInt(value.z);
                    },

                    (buf) -> new HubLinkComponent(buf.readVarInt(), buf.readVarInt(), buf.readVarInt())
            );


    public static Optional<BlockPos> getHubPosOptional(ItemStack stack) {
        if (!stack.has(ModComponents.HUB_LINK)) return Optional.empty();
        var component = stack.get(ModComponents.HUB_LINK);
        if (component == null) return Optional.empty();
        return Optional.of(new BlockPos(component.x, component.y, component.z));
    }
}
