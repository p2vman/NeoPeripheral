package io.p2vman.neoperipheral.util;

import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;

public class EntityTypeCache {
    private final Object2ObjectMap<EntityType<?>, ResourceLocation> types = new Object2ObjectOpenHashMap<>();
    public EntityTypeCache() {

    }

    public ResourceLocation get(Entity entity) {
        return this.get(entity.getType());
    }

    public ResourceLocation get(EntityType<?> entityType) {
        var type = types.get(entityType);
        if (type == null) {
            type = EntityType.getKey(entityType);
            types.put(entityType, type);
        }
        return type;
    }
}
