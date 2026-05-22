package io.p2vman.neoperipheral.network;

public record NetworkPacket(int channel, int replyChannel, Object payload, IPacketSender sender) {
}
