package io.p2vman.neoperipheral.upgrade.pocket;

import dan200.computercraft.api.peripheral.IPeripheral;
import dan200.computercraft.api.pocket.AbstractPocketUpgrade;
import dan200.computercraft.api.pocket.IPocketAccess;
import dan200.computercraft.api.upgrades.UpgradeType;
import io.p2vman.neoperipheral.ModRegistry;
import io.p2vman.neoperipheral.Neoperipheral;
import io.p2vman.neoperipheral.peripheral.RadarPeripheral;
import io.p2vman.neoperipheral.util.AccessWrapper;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;

public class RadarPocketUpgrade extends AbstractPocketUpgrade {
    public static final UpgradeType<RadarPocketUpgrade> TYPE =
            UpgradeType.simpleWithCustomItem(RadarPocketUpgrade::new);

    public RadarPocketUpgrade(ItemStack item) {
        super(String.format("upgrade.%s.radar_upgrade", Neoperipheral.MODID), item);
    }

    @Override
    public UpgradeType<? extends RadarPocketUpgrade> getType() {
        return ModRegistry.RADAR_UPGRADE_TYPE.get();
    }

    @Override
    public @Nullable IPeripheral createPeripheral(IPocketAccess access) {
        return new RadarPeripheral(AccessWrapper.create(access), false);
    }


    @Override
    public void update(IPocketAccess access, @Nullable IPeripheral peripheral) {
    }
}
