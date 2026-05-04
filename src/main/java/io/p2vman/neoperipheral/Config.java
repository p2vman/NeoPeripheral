package io.p2vman.neoperipheral;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

// An example config class. This is not required, but it's a good idea to have one to keep your config organized.
// Demonstrates how to use Neo's config APIs
@EventBusSubscriber(modid = Neoperipheral.MODID, bus = EventBusSubscriber.Bus.MOD)
public class Config {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    private static final ModConfigSpec.IntValue RADAR_RANGE_LIMIT;
    private static final ModConfigSpec.IntValue RADAR_DEFAULT_RANGE;

    private static final ModConfigSpec.BooleanValue CHEAT_CANNON_MOUNT;

    private static final ModConfigSpec.IntValue CHEAT_CANNON_MAX_FIRE_RATE;
    private static final ModConfigSpec.IntValue CHEAT_CANNON_RATE_WINDOW;

    static final ModConfigSpec SPEC;


    static {
        RADAR_RANGE_LIMIT = BUILDER.defineInRange("radar_range_limit", 2048, 16, Integer.MAX_VALUE);
        RADAR_DEFAULT_RANGE = BUILDER.defineInRange("radar_default_range", 1024, 16, Integer.MAX_VALUE);

        BUILDER.push("cbc");
        BUILDER.push("cheat_cannon");

        CHEAT_CANNON_MOUNT = BUILDER.define("enable", true);
        CHEAT_CANNON_MAX_FIRE_RATE = BUILDER.defineInRange("max_fire_rate", 8, 1, Integer.MAX_VALUE);
        CHEAT_CANNON_RATE_WINDOW = BUILDER.defineInRange("rate_window", 20, 1, Integer.MAX_VALUE);

        BUILDER.pop();

        BUILDER.pop();

        SPEC = BUILDER.build();
    }

    public static int _RADAR_RANGE_LIMIT;
    public static int _RADAR_DEFAULT_RANGE;
    public static boolean _CHEAT_CANNON_MOUNT;

    public static int _CHEAT_CANNON_MAX_FIRE_RATE;
    public static int _CHEAT_CANNON_RATE_WINDOW;

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {
        _RADAR_RANGE_LIMIT = RADAR_RANGE_LIMIT.get();
        _RADAR_DEFAULT_RANGE = RADAR_DEFAULT_RANGE.get();
        _CHEAT_CANNON_MOUNT = CHEAT_CANNON_MOUNT.get();

        _CHEAT_CANNON_MAX_FIRE_RATE = CHEAT_CANNON_MAX_FIRE_RATE.get();
        _CHEAT_CANNON_RATE_WINDOW = CHEAT_CANNON_RATE_WINDOW.get();
    }
}
