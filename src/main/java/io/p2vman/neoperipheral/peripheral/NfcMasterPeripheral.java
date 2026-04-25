package io.p2vman.neoperipheral.peripheral;

import dan200.computercraft.api.lua.*;
import dan200.computercraft.api.peripheral.IComputerAccess;
import io.p2vman.neoperipheral.IPrefSource;
import io.p2vman.neoperipheral.ModComponents;
import io.p2vman.neoperipheral.block.entity.NfcMasterBlockEntity;
import io.p2vman.neoperipheral.component.NfcCardComponent;
import io.p2vman.neoperipheral.lua.Table;
import io.p2vman.neoperipheral.util.ArgumentsExtension;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;

import java.nio.charset.StandardCharsets;

public class NfcMasterPeripheral extends BasePeripheral implements IPeripheralAttacher {
    private final NfcMasterBlockEntity entity;
    private final ObjectList<IComputerAccess> computers = new ObjectArrayList<>();
    public NfcMasterPeripheral(Direction direction, IPrefSource source) {
        super(source);
        this.entity = (NfcMasterBlockEntity) source;
    }

    @Override
    public String getType() {
        return "neo_nfc_master";
    }

    @LuaFunction(mainThread = true, value = {"hashCard", "HashCard"})
    public boolean hashCard() {
        return !entity.isEmpty();
    }

    @LuaFunction(mainThread = true)
    public String getLabel() {
        return entity.getLabel();
    }

    @LuaFunction(mainThread = true)
    public void setLabel(String label) {
        entity.setLabel(label);
    }

    public void onCardChanged(ItemStack stack) {
        var label = this.entity.getLabel();
        if (stack.isEmpty()) {
            this.queueEvent("nfc_master_card_enject", label);
        } else  {
            this.queueEvent("nfc_master_card_inject", label);
        }
    }

    @Override
    public ObjectList<IComputerAccess> getAttachedComputers() {
        return computers;
    }

    public enum FlashMode {
        DATA,
        B4PIN
    }

    @LuaFunction(mainThread = true, value = {"Flash", "flash"})
    public boolean flash(IArguments args) throws LuaException {
        if (entity.isEmpty()) return false;
        var card = entity.getCard();

        var data = card.get(ModComponents.NFC_DATA);
        if (data != null) {
            if (data.b4pin() != 0) {
                var pi = args.optInt(2);
                if (pi.isEmpty() || data.b4pin() != pi.get()) {
                    throw new LuaException("Invalid b4pin value");
                }
            }
        }

        var mode = args.getEnum(0, FlashMode.class);
        switch (mode) {
            case DATA:
                var tableOpt = ArgumentsExtension.optTable(args, 1);
                if (tableOpt.isPresent()) {
                    var table = tableOpt.get();

                    int max = 0;
                    for (Object k : table.keySet()) {
                        if (k instanceof Number n) {
                            int i = n.intValue();
                            if (i > max) max = i;
                        }
                    }

                    if (max > 1024) throw new LuaException("Max length n<=1024");

                    byte[] out = new byte[max];

                    for (int i = 1; i <= max; i++) {
                        Object v = table.get(i);
                        if (v == null) v = table.get((double) i);

                        if (v instanceof Number n) {
                            int val = n.intValue();
                            if (val < 0 || val > 255)
                                throw new LuaException("Byte out of range");

                            out[i - 1] = (byte) val;
                        }
                    }

                    if (data == null) {
                        card.set(ModComponents.NFC_DATA, new NfcCardComponent(0, out));
                        return true;
                    }
                    card.set(ModComponents.NFC_DATA, new NfcCardComponent(data.b4pin(), out));
                    return true;
                }

                var strOpt = args.optString(1);
                if (strOpt.isPresent()) {
                    byte[] out = strOpt.get().getBytes(StandardCharsets.UTF_8);

                    if (out.length > 1024)
                        throw new LuaException("Max length n<=1024");

                    if (data == null) {
                        card.set(ModComponents.NFC_DATA, new NfcCardComponent(0, out));
                        return true;
                    }
                    card.set(ModComponents.NFC_DATA, new NfcCardComponent(data.b4pin(), out));
                    return true;
                }
                break;
            case B4PIN:
                var pin = args.getInt(1);
                if (data == null) {
                    card.set(ModComponents.NFC_DATA, new NfcCardComponent(pin, new byte[] {}));
                    return true;
                }
                card.set(ModComponents.NFC_DATA, new NfcCardComponent(pin, data.data()));
                return true;
        }
        return false;
    }

    @LuaFunction(mainThread = true, value = {"Read", "read"})
    public MethodResult read() throws LuaException {
        if (entity.isEmpty()) return MethodResult.of(false);
        var card = entity.getCard();
        var data = card.get(ModComponents.NFC_DATA);
        if (data == null) return MethodResult.of(false);
        Table _data = new Table();

        byte[] arr = data.data();
        for (int i = 0; i < arr.length; i++) {
            _data.put(i + 1, arr[i] & 0xFF);
        }
        return MethodResult.of(_data);
    }
}
