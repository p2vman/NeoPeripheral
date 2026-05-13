package io.p2vman.neoperipheral.integration.ctc.peripheral;

import dan200.computercraft.api.peripheral.IComputerAccess;
import io.p2vman.neoperipheral.IPrefSource;
import io.p2vman.neoperipheral.integration.create.block.entity.LinkedControllerHubBlockEntity;
import io.p2vman.neoperipheral.peripheral.BasePeripheral;
import io.p2vman.neoperipheral.peripheral.IPeripheralAttacher;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

import java.util.Collection;
import java.util.Set;

public class TweakedLinkedControllerHubPeripheral extends BasePeripheral implements IPeripheralAttacher {
    public ObjectList<IComputerAccess> computers = new ObjectArrayList<>();
    public TweakedLinkedControllerHubPeripheral(IPrefSource source) {
        super(source);
    }

    @Override
    public String getType() {
        return "tweaked_linked_controller_hub";
    }

    @Override
    public Set<String> getAdditionalTypes() {
        return Set.of("controller_hub");
    }

    @Override
    public ObjectList<IComputerAccess> getAttachedComputers() {
        return computers;
    }

    public static void receivePressed(Level level, BlockPos pos, Collection<Integer> buttons, boolean pressed) {
        if (!(level.getBlockEntity(pos) instanceof LinkedControllerHubBlockEntity entity)) return;
        var peripheral = entity.peripheral.get();

    }
}
