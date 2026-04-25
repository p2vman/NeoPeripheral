package io.p2vman.neoperipheral.util;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.codecs.PrimitiveCodec;

import java.nio.ByteBuffer;

public class Codecs {
    public static final Codec<byte[]> BYTE_ARRAY_CODEC = new PrimitiveCodec<>() {

        @Override
        public <T> DataResult<byte[]> read(DynamicOps<T> ops, T input) {
            return ops.getByteBuffer(input)
                    .map(buf -> {
                        byte[] arr = new byte[buf.remaining()];
                        buf.get(arr);
                        return arr;
                    });
        }

        @Override
        public <T> T write(DynamicOps<T> ops, byte[] value) {
            return ops.createByteList(ByteBuffer.wrap(value));
        }
    };
}
