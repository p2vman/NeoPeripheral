package io.p2vman.neoperipheral.upgrade.turtle;

import dan200.computercraft.api.peripheral.IPeripheral;
import dan200.computercraft.api.turtle.AbstractTurtleUpgrade;
import dan200.computercraft.api.turtle.ITurtleAccess;
import dan200.computercraft.api.turtle.TurtleSide;
import dan200.computercraft.api.turtle.TurtleUpgradeType;
import dan200.computercraft.api.upgrades.UpgradeType;
import io.p2vman.neoperipheral.ModRegistry;
import io.p2vman.neoperipheral.Neoperipheral;
import io.p2vman.neoperipheral.peripheral.RadarPeripheral;
import io.p2vman.neoperipheral.util.AccessWrapper;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;

public class RadarTurtleUpgrade extends AbstractTurtleUpgrade {
    public static final UpgradeType<RadarTurtleUpgrade> TYPE =
            UpgradeType.simpleWithCustomItem(RadarTurtleUpgrade::new);

    public RadarTurtleUpgrade(ItemStack stack) {
        super(TurtleUpgradeType.PERIPHERAL, String.format("upgrade.%s.radar_upgrade", Neoperipheral.MODID), stack);
    }

    @Override
    public UpgradeType<? extends RadarTurtleUpgrade> getType() {
        return ModRegistry.RADAR_TURTLE_UPGRADE_TYPE.get();
    }

    @Override
    public @Nullable IPeripheral createPeripheral(ITurtleAccess turtle, TurtleSide side) {
        return new RadarPeripheral(AccessWrapper.create(turtle), false);
    }
}
