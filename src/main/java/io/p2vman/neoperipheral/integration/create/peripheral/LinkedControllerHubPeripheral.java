package io.p2vman.neoperipheral.integration.create.peripheral;

import dan200.computercraft.api.lua.LuaFunction;
import dan200.computercraft.api.lua.LuaTable;
import dan200.computercraft.api.peripheral.IComputerAccess;
import io.p2vman.neoperipheral.IPrefSource;
import io.p2vman.neoperipheral.peripheral.BasePeripheral;
import io.p2vman.neoperipheral.peripheral.IPeripheralAttacher;
import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import it.unimi.dsi.fastutil.objects.Object2BooleanOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;

import java.util.Collection;
import java.util.Set;

public class LinkedControllerHubPeripheral extends BasePeripheral implements IPeripheralAttacher {
    public static final String[] KEY_TO_CHANNEL = new String[]{"keyUp", "keyDown", "keyLeft", "keyRight", "keyJump", "keyShift"};
    public Object2BooleanMap<String> prees = new Object2BooleanOpenHashMap<>(KEY_TO_CHANNEL.length);

    public ObjectList<IComputerAccess> computers = new ObjectArrayList<>();
    public LinkedControllerHubPeripheral(IPrefSource source) {
        super(source);
    }

    @Override
    public String getType() {
        return "linked_controller_hub";
    }

    @Override
    public Set<String> getAdditionalTypes() {
        return Set.of("controller_hub");
    }

    @Override
    public ObjectList<IComputerAccess> getAttachedComputers() {
        return computers;
    }

    @LuaFunction
    public boolean getButton(String key) {
        return prees.getBoolean(key);
    }

    @LuaFunction
    public void getButtons(LuaTable<String, Boolean> table) {
        table.replaceAll((s, v) -> prees.getBoolean(s));
    }

    public void receivePressed(Collection<Integer> buttons, boolean pressed) {
        for (Integer button : buttons) {
            prees.put(KEY_TO_CHANNEL[button], pressed);
        }
    }

    @LuaFunction
    public String[] getKeys() {
        return KEY_TO_CHANNEL;
    }
}
