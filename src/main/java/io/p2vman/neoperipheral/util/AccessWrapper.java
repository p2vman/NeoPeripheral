package io.p2vman.neoperipheral.util;

import dan200.computercraft.api.pocket.IPocketAccess;
import dan200.computercraft.api.turtle.ITurtleAccess;
import io.p2vman.neoperipheral.IPrefSource;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

public abstract class AccessWrapper implements IPrefSource {
    public static AccessWrapper create(IPocketAccess access) {
        return new PocketAccessWrapper(access);
    }

    public static AccessWrapper create(ITurtleAccess access) {
        return new TurtleAccessWrapper(access);
    }

    public static class PocketAccessWrapper extends AccessWrapper {
        private final IPocketAccess access;
        public PocketAccessWrapper(IPocketAccess access) {
            this.access = access;
        }

        @Override
        public BlockPos getPos() {
            var pos = this.access.getPosition();
            return new BlockPos(Mth.floor(pos.x), Mth.floor(pos.y), Mth.floor(pos.z));
        }

        @Override
        public Level getLevel() {
            return this.access.getLevel();
        }

        @Override
        public BlockEntity getBlockEntity() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setLabel(String label) {

        }

        @Override
        public String getLabel() {
            return "";
        }

        @Override
        public boolean hasLabel() {
            return false;
        }
    }

    public static class TurtleAccessWrapper extends AccessWrapper {
        private final ITurtleAccess access;

        public TurtleAccessWrapper(ITurtleAccess access) {
            this.access = access;
        }

        @Override
        public BlockPos getPos() {
            return this.access.getPosition();
        }

        @Override
        public Level getLevel() {
            return this.access.getLevel();
        }

        @Override
        public BlockEntity getBlockEntity() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setLabel(String label) {

        }

        @Override
        public String getLabel() {
            return "";
        }

        @Override
        public boolean hasLabel() {
            return false;
        }
    }
}
