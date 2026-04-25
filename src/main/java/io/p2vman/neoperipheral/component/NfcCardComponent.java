package io.p2vman.neoperipheral.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.p2vman.neoperipheral.util.Codecs;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;

public record NfcCardComponent(int b4pin, byte[] data) {
    public static final Codec<NfcCardComponent> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.INT.fieldOf("b4pin").forGetter(NfcCardComponent::b4pin),
                    Codecs.BYTE_ARRAY_CODEC.fieldOf("data")
                            .flatXmap(
                                    arr -> arr.length > 1024
                                            ? DataResult.error(() -> "NFC data too large: " + arr.length)
                                            : DataResult.success(arr),

                                    DataResult::success
                            )
                            .forGetter(NfcCardComponent::data)
            ).apply(instance, NfcCardComponent::new)
    );

    public static final StreamCodec<FriendlyByteBuf, NfcCardComponent> STREAM_CODEC =
            StreamCodec.of(
                    (buf, value) -> {
                        byte[] data = value.data();
                        int len = Math.min(data.length, 1024);

                        buf.writeInt(value.b4pin());
                        buf.writeVarInt(len);
                        buf.writeBytes(data, 0, len);
                    },

                    (buf) -> {
                        int b4pin = buf.readInt();
                        int len = buf.readVarInt();

                        if (len > 1024) {
                            len = 1024;
                        }

                        byte[] data = new byte[len];
                        buf.readBytes(data);

                        return new NfcCardComponent(b4pin, data);
                    }
            );
}
