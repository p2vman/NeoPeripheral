package io.p2vman.neoperipheral.peripheral;

import dan200.computercraft.api.peripheral.IComputerAccess;
import io.p2vman.neoperipheral.IPrefSource;
import io.p2vman.neoperipheral.ModComponents;
import io.p2vman.neoperipheral.lua.Table;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;

public class NfcReaderPeripheral extends BasePeripheral implements IPeripheralAttacher {
    private final ObjectList<IComputerAccess> computers = new ObjectArrayList<>();
    public NfcReaderPeripheral(Direction direction, IPrefSource source) {
        super(source);
    }

    @Override
    public ObjectList<IComputerAccess> getAttachedComputers() {
        return computers;
    }

    @Override
    public String getType() {
        return "neo_nfc_reader";
    }

    public void read(ItemStack itemStack) {
        var component = itemStack.get(ModComponents.NFC_DATA);

        if (component == null) {
            this.queueEvent("neo_nfc_read", this.source.getLabel());
        } else {
            Table data = new Table();

            byte[] arr = component.data();
            for (int i = 0; i < arr.length; i++) {
                data.put(i + 1, arr[i] & 0xFF);
            }

            this.queueEvent("neo_nfc_read", this.source.getLabel(), data);
        }
    }
}
