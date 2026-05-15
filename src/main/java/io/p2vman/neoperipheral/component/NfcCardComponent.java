package io.p2vman.neoperipheral.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.p2vman.neoperipheral.util.Codecs;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;

public record NfcCardComponent(int b4pin, byte[] data, int level) {
    static final int[] POW2 = {
            1,    // 2^0
            2,    // 2^1
            4,    // 2^2
            8,    // 2^3
            16,   // 2^4
            32,   // 2^5
            64,   // 2^6
            128,  // 2^7
            256,  // 2^8
            512,  // 2^9
            1024, // 2^10
            2048, // 2^11
            4096  // 2^12
    };
    public static final int MaxLevel = 12;
    public static final int MaxLen = POW2[MaxLevel];
    public static final Codec<NfcCardComponent> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.INT.fieldOf("b4pin").forGetter(NfcCardComponent::b4pin),
                    Codecs.BYTE_ARRAY_CODEC.fieldOf("data")
                            .flatXmap(
                                    arr -> arr.length > MaxLen
                                            ? DataResult.error(() -> "NFC data too large: " + arr.length)
                                            : DataResult.success(arr),

                                    DataResult::success
                            )
                            .forGetter(NfcCardComponent::data),
                    Codec.INT.fieldOf("level").forGetter(NfcCardComponent::level)
            ).apply(instance, NfcCardComponent::new)
    );

    public static final StreamCodec<FriendlyByteBuf, NfcCardComponent> STREAM_CODEC =
            StreamCodec.of(
                    (buf, value) -> {
                        byte[] data = value.data();
                        int len = Math.min(data.length, POW2[value.level]);

                        buf.writeInt(value.b4pin());
                        buf.writeVarInt(value.level());
                        buf.writeVarInt(len);
                        buf.writeBytes(data, 0, len);
                    },

                    (buf) -> {
                        int b4pin = buf.readInt();
                        int level = buf.readVarInt();
                        var mlen = POW2[level];

                        int len = buf.readVarInt();
                        if (len > mlen) {
                            len = mlen;
                        }

                        byte[] data = new byte[len];
                        buf.readBytes(data, 0, len);

                        return new NfcCardComponent(b4pin, data, level);
                    }
            );
}
