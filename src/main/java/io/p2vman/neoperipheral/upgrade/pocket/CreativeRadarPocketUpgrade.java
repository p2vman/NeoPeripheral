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

public class CreativeRadarPocketUpgrade extends AbstractPocketUpgrade {
    public static final UpgradeType<CreativeRadarPocketUpgrade> TYPE =
            UpgradeType.simpleWithCustomItem(CreativeRadarPocketUpgrade::new);
    public CreativeRadarPocketUpgrade(ItemStack item) {
        super(String.format("upgrade.%s.creative_radar_upgrade", Neoperipheral.MODID), item);
    }

    @Override
    public UpgradeType<? extends CreativeRadarPocketUpgrade> getType() {
        return ModRegistry.CREATIVE_RADAR_UPGRADE_TYPE.get();
    }

    @Override
    public @Nullable IPeripheral createPeripheral(IPocketAccess access) {
        return new RadarPeripheral(AccessWrapper.create(access), true);
    }


    @Override
    public void update(IPocketAccess access, @Nullable IPeripheral peripheral) {
    }
}
