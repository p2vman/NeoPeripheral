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

    private static final ModConfigSpec.IntValue ENTITY_RADAR_RANGE_LIMIT;
    private static final ModConfigSpec.IntValue PLAYER_RADAR_RANGE_LIMIT;
    private static final ModConfigSpec.BooleanValue ENTITY_RADAR_SHOW_PLAYER_NAMES;

    private static final ModConfigSpec.BooleanValue SABLE_ENGINE_ENABLED;
    private static final ModConfigSpec.IntValue SABLE_ENGINE_MAX_RATE;
    private static final ModConfigSpec.IntValue SABLE_ENGINE_RATE_WINDOW;

    static final ModConfigSpec SPEC;


    static {
        RADAR_RANGE_LIMIT = BUILDER.defineInRange("radar_range_limit", 2048, 16, Integer.MAX_VALUE);
        RADAR_DEFAULT_RANGE = BUILDER.defineInRange("radar_default_range", 1024, 16, Integer.MAX_VALUE);

        BUILDER.push("cbc");
        BUILDER.push("cheat_cannon");

        CHEAT_CANNON_MOUNT = BUILDER.define("enable", true);
        CHEAT_CANNON_MAX_FIRE_RATE = BUILDER.defineInRange("max_fire_rate", 14, 1, Integer.MAX_VALUE);
        CHEAT_CANNON_RATE_WINDOW = BUILDER.defineInRange("rate_window", 20, 1, Integer.MAX_VALUE);

        BUILDER.pop();

        BUILDER.pop();

        BUILDER.push("entity_radar");

        ENTITY_RADAR_RANGE_LIMIT = BUILDER.defineInRange("entity_range_limit", 2048, 16, Integer.MAX_VALUE);
        PLAYER_RADAR_RANGE_LIMIT = BUILDER.defineInRange("player_range_limit", 1024, 16, Integer.MAX_VALUE);

        ENTITY_RADAR_SHOW_PLAYER_NAMES = BUILDER.define("show_player_names", true);

        BUILDER.pop();

        BUILDER.push("sable_engine");

        SABLE_ENGINE_ENABLED = BUILDER.define("enable", true);

        SABLE_ENGINE_MAX_RATE = BUILDER.defineInRange("max_rate", 19, 1, Integer.MAX_VALUE);
        SABLE_ENGINE_RATE_WINDOW = BUILDER.defineInRange("rate_window", 20, 1, Integer.MAX_VALUE);

        BUILDER.pop();


        SPEC = BUILDER.build();
    }

    public static int _RADAR_RANGE_LIMIT;
    public static int _RADAR_DEFAULT_RANGE;
    public static boolean _CHEAT_CANNON_MOUNT;

    public static int _CHEAT_CANNON_MAX_FIRE_RATE;
    public static int _CHEAT_CANNON_RATE_WINDOW;

    public static int _ENTITY_RADAR_RANGE_LIMIT;
    public static int _PLAYER_RADAR_RANGE_LIMIT;
    public static boolean _ENTITY_RADAR_SHOW_PLAYER_NAMES;

    public static boolean _SABLE_ENGINE_ENABLED;
    public static int _SABLE_ENGINE_MAX_RATE;
    public static int _SABLE_ENGINE_RATE_WINDOW;

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {
        _RADAR_RANGE_LIMIT = RADAR_RANGE_LIMIT.get();
        _RADAR_DEFAULT_RANGE = RADAR_DEFAULT_RANGE.get();
        _CHEAT_CANNON_MOUNT = CHEAT_CANNON_MOUNT.get();

        _CHEAT_CANNON_MAX_FIRE_RATE = CHEAT_CANNON_MAX_FIRE_RATE.get();
        _CHEAT_CANNON_RATE_WINDOW = CHEAT_CANNON_RATE_WINDOW.get();

        _ENTITY_RADAR_RANGE_LIMIT = ENTITY_RADAR_RANGE_LIMIT.get();
        _PLAYER_RADAR_RANGE_LIMIT = PLAYER_RADAR_RANGE_LIMIT.get();
        _ENTITY_RADAR_SHOW_PLAYER_NAMES = ENTITY_RADAR_SHOW_PLAYER_NAMES.get();

        _SABLE_ENGINE_ENABLED = SABLE_ENGINE_ENABLED.get();
        _SABLE_ENGINE_MAX_RATE = SABLE_ENGINE_MAX_RATE.get();
        _SABLE_ENGINE_RATE_WINDOW = SABLE_ENGINE_RATE_WINDOW.get();
    }
}
