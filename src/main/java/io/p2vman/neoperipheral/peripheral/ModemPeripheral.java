package io.p2vman.neoperipheral.peripheral;

import dan200.computercraft.api.lua.LuaException;
import dan200.computercraft.api.peripheral.IComputerAccess;
import io.p2vman.neoperipheral.IPrefSource;
import io.p2vman.neoperipheral.network.INetwork;
import io.p2vman.neoperipheral.network.IPacketReceiver;
import io.p2vman.neoperipheral.network.IPacketSender;
import io.p2vman.neoperipheral.network.NetworkPacket;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.atomic.AtomicBoolean;

public class ModemPeripheral extends BasePeripheral implements IPacketSender, IPacketReceiver, IPeripheralAttacher {
    private INetwork network;
    private final ModemState state;
    private final ObjectList<IComputerAccess> computers = new ObjectArrayList<>();

    public ModemPeripheral(Direction direction, IPrefSource source) {
        super(source);
        this.state = new ModemState();
    }

    public ModemState getModemState() {
        return this.state;
    }

    @Override
    public String getType() {
        return "neo_modem";
    }

    @Override
    public void receive(@NotNull NetworkPacket var1) {

    }

    @Override
    public @NotNull Level getLevel() {
        return getLevel();
    }

    @Override
    public @NotNull Vec3 getPosition() {
        return null;
    }

    @Override
    public @NotNull String getSenderID() {
        return "";
    }

    @Override
    public ObjectList<IComputerAccess> getAttachedComputers() {
        return computers;
    }

    public static class ModemState {
        private final Runnable onChanged;
        private final AtomicBoolean changed = new AtomicBoolean(true);
        private boolean open = false;
        private final IntSet channels = new IntOpenHashSet();

        public ModemState() {
            this.onChanged = null;
        }

        public ModemState(Runnable onChanged) {
            this.onChanged = onChanged;
        }

        private void setOpen(boolean open) {
            if (this.open != open) {
                this.open = open;
                if (!this.changed.getAndSet(true) && this.onChanged != null) {
                    this.onChanged.run();
                }

            }
        }

        public boolean pollChanged() {
            return this.changed.getAndSet(false);
        }

        public boolean isOpen(int channel) {
            synchronized(this.channels) {
                return this.channels.contains(channel);
            }
        }

        public boolean isOpen() {
            return this.open;
        }

        public void open(int channel) throws LuaException {
            synchronized(this.channels) {
                if (!this.channels.contains(channel)) {
                    if (this.channels.size() >= 128) {
                        throw new LuaException("Too many open channels");
                    }

                    this.channels.add(channel);
                    this.setOpen(true);
                }

            }
        }

        public void close(int channel) {
            synchronized(this.channels) {
                this.channels.remove(channel);
                if (this.channels.isEmpty()) {
                    this.setOpen(false);
                }

            }
        }

        public void closeAll() {
            synchronized(this.channels) {
                this.channels.clear();
                this.setOpen(false);
            }
        }
    }

}
