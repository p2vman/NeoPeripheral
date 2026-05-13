package io.p2vman.neoperipheral.peripheral;

import dan200.computercraft.api.lua.IArguments;
import dan200.computercraft.api.lua.LuaException;
import dan200.computercraft.api.lua.LuaFunction;
import dev.ryanhcode.sable.Sable;
import io.p2vman.neoperipheral.Config;
import io.p2vman.neoperipheral.IPrefSource;
import io.p2vman.neoperipheral.lua.Table;
import io.p2vman.neoperipheral.lua.TableArray;
import io.p2vman.neoperipheral.util.EntityTypeCache;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;

public class EntityRadarPeripheral extends BasePeripheral {
    private final EntityTypeCache cache;
    public EntityRadarPeripheral(Direction direction, IPrefSource source) {
        super(source);
        this.cache = new EntityTypeCache();
    }

    @Override
    public String getType() {
        return "neo_entity_radar";
    }

    @LuaFunction(mainThread = true)
    public TableArray scanForEntities(IArguments arguments) throws LuaException {
        var array = new TableArray();
        var increment = 1;
        var radius = Math.max(16, Math.min(Config._ENTITY_RADAR_RANGE_LIMIT, arguments.optInt(0, 16)));

        var pos = this.source.getPos().getCenter();
        var level = this.source.getLevel();
        var _pos = Sable.HELPER.projectOutOfSubLevel(level, pos);


        for (LivingEntity entity : level.getEntitiesOfClass(LivingEntity.class, new AABB(_pos.x - radius, _pos.y - radius, _pos.z - radius, _pos.x + radius, _pos.y + radius, _pos.z + radius))) {
            var table = new Table();
            table.put("type", cache.get(entity).toString());
            table.put("id", entity.getId());
            table.put("x", entity.getX());
            table.put("y", entity.getY());
            table.put("z", entity.getZ());
            table.put("xRot", entity.getXRot());
            table.put("yRot", entity.getYRot());
            table.put("health", entity.getHealth());
            table.put("maxHealth", entity.getMaxHealth());

            array.put(increment++, table);
        }

        return array;
    }

    @LuaFunction(mainThread = true)
    public TableArray scanForPlayers(IArguments arguments) throws LuaException {
        var array = new TableArray();
        var increment = 1;
        var radius = Math.max(16, Math.min(Config._PLAYER_RADAR_RANGE_LIMIT, arguments.optInt(0, 16)));

        var pos = this.source.getPos().getCenter();
        var level = this.source.getLevel();
        var _pos = Sable.HELPER.projectOutOfSubLevel(level, pos);


        for (Player player : level.getEntitiesOfClass(Player.class, new AABB(_pos.x - radius, _pos.y - radius, _pos.z - radius, _pos.x + radius, _pos.y + radius, _pos.z + radius))) {
            var table = new Table();
            if (Config._ENTITY_RADAR_SHOW_PLAYER_NAMES) table.put("username", player.getGameProfile().getName());
            table.put("x", player.getX());
            table.put("y", player.getY());
            table.put("z", player.getZ());
            table.put("xRot", player.getXRot());
            table.put("yRot", player.getYRot());
            table.put("health", player.getHealth());
            table.put("maxHealth", player.getMaxHealth());

            array.put(increment++, table);
        }

        return array;
    }
}
