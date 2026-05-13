package io.p2vman.neoperipheral.peripheral;

import dan200.computercraft.api.lua.LuaException;
import dan200.computercraft.api.lua.LuaFunction;
import dan200.computercraft.api.lua.MethodResult;
import dan200.computercraft.api.peripheral.IComputerAccess;
import dev.ryanhcode.sable.Sable;
import dev.ryanhcode.sable.api.physics.handle.RigidBodyHandle;
import dev.ryanhcode.sable.api.physics.mass.MassData;
import dev.ryanhcode.sable.sublevel.ServerSubLevel;
import dev.ryanhcode.sable.sublevel.SubLevel;
import io.p2vman.neoperipheral.Config;
import io.p2vman.neoperipheral.IPrefSource;
import io.p2vman.neoperipheral.util.CallLimiter;
import it.unimi.dsi.fastutil.objects.Object2DoubleMap;
import it.unimi.dsi.fastutil.objects.Object2DoubleOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import org.joml.Quaterniondc;
import org.joml.Vector3d;
import org.joml.Vector3dc;

import javax.annotation.Nullable;
import java.util.Map;

public class SableEnginePeripheral extends BasePeripheral implements IPeripheralAttacher {
    private SubLevel subLevel;
    private CallLimiter callLimiter;
    private final ObjectList<IComputerAccess> computers = new ObjectArrayList<>();
    public SableEnginePeripheral(Direction direction, IPrefSource source) {
        super(source);
        this.callLimiter = new CallLimiter.RingCallLimiter(Config._SABLE_ENGINE_MAX_RATE, Config._SABLE_ENGINE_RATE_WINDOW);
    }

    @Override
    public String getType() {
        return "neo_sable_engine";
    }

    @LuaFunction
    public final @Nullable String getUUID() {
        ServerSubLevel sl = getSubLevel();
        return sl != null ? sl.getUniqueId().toString() : null;
    }

    private int tick = 0;
    public void tick() {
        tick++;
        queueEvent("engine_tick");
    }

    private @Nullable ServerSubLevel getSubLevel() {
        if (source.getLevel() == null || source.getLevel().isClientSide()) {
            return null;
        }

        if (subLevel != null) {
            if (subLevel.isRemoved()) subLevel = null;
        }
        else {
            subLevel = Sable.HELPER.getContaining(source.getLevel(), source.getPos());
        }

        return (ServerSubLevel) subLevel;
    }

    @LuaFunction
    public final boolean isOnSubLevel() {
        return getSubLevel() != null;
    }

    @LuaFunction
    public final @Nullable String getName() {
        ServerSubLevel sl = getSubLevel();
        return sl != null ? sl.getName() : null;
    }

    @LuaFunction
    public final double getMass() {
        ServerSubLevel sl = getSubLevel();
        if (sl == null) {return Double.NaN;}
        MassData massTracker = sl.getMassTracker();
        return massTracker != null ? massTracker.getMass() : 0.0;
    }

    @LuaFunction
    public final Map<String, Double> getPosition() throws LuaException {
        ServerSubLevel sl = getSubLevel();
        if (sl == null) {return null;}
        Vector3dc pos = sl.logicalPose().position();
        Object2DoubleMap<String> result = new Object2DoubleOpenHashMap<>();
        result.put("x", pos.x());
        result.put("y", pos.y());
        result.put("z", pos.z());
        return result;
    }

    @LuaFunction
    public final Map<String, Double> getOrientation() throws LuaException {
        ServerSubLevel sl = getSubLevel();
        if (sl == null) {return null;}
        Quaterniondc q = sl.logicalPose().orientation();
        Object2DoubleMap<String> result = new Object2DoubleOpenHashMap<>();
        result.put("x", q.x());
        result.put("y", q.y());
        result.put("z", q.z());
        result.put("w", q.w());
        return result;
    }

    @LuaFunction
    public final Map<String, Double> getLinearVelocity() throws LuaException {
        ServerSubLevel sl = getSubLevel();
        if (sl == null) {return null;}
        RigidBodyHandle handle = RigidBodyHandle.of(sl);
        if (handle == null) {return null;}
        Vector3d linVel = handle.getLinearVelocity(new Vector3d());
        Object2DoubleMap<String> result = new Object2DoubleOpenHashMap<>();
        result.put("x", linVel.x);
        result.put("y", linVel.y);
        result.put("z", linVel.z);
        return result;
    }

    @LuaFunction
    public final Map<String, Double> getAngularVelocity() throws LuaException {
        ServerSubLevel sl = getSubLevel();
        if (sl == null) {return null;}
        RigidBodyHandle handle = RigidBodyHandle.of(sl);
        if (handle == null) {return null;}
        Vector3d angVel = handle.getAngularVelocity(new Vector3d());
        Object2DoubleMap<String> result = new Object2DoubleOpenHashMap<>();
        result.put("x", angVel.x);
        result.put("y", angVel.y);
        result.put("z", angVel.z);
        return result;
    }

    @LuaFunction
    public final @Nullable Map<String, Double> getCenterOfMass() throws LuaException {
        ServerSubLevel sl = getSubLevel();
        if (sl == null) {return null;}
        MassData massTracker = sl.getMassTracker();
        if (massTracker == null) return null;
        Vector3dc com = massTracker.getCenterOfMass();
        if (com == null) return null;
        Object2DoubleMap<String> result = new Object2DoubleOpenHashMap<>();
        result.put("x", com.x());
        result.put("y", com.y());
        result.put("z", com.z());
        return result;
    }

    @LuaFunction
    public final MethodResult applyLinearImpulse(double x, double y, double z) throws LuaException {
        if (!callLimiter.tryCall(tick)) return null;
        ServerSubLevel sl = getSubLevel();
        if (sl == null) {return null;}
        RigidBodyHandle handle = RigidBodyHandle.of(sl);
        if (handle == null) {return null;}
        handle.applyLinearImpulse(new Vector3d(x, y, z));
        return MethodResult.of(true);
    }

    @LuaFunction
    public final MethodResult applyAngularImpulse(double x, double y, double z) throws LuaException {
        if (!callLimiter.tryCall(tick)) return null;
        ServerSubLevel sl = getSubLevel();
        if (sl == null) {return null;}
        RigidBodyHandle handle = RigidBodyHandle.of(sl);
        if (handle == null) {return null;}
        handle.applyAngularImpulse(new Vector3d(x, y, z));
        return MethodResult.of(true);
    }

    @LuaFunction
    public final MethodResult applyImpulseAtPoint(double px, double py, double pz, double fx, double fy, double fz) throws LuaException {
        if (!callLimiter.tryCall(tick)) return null;
        ServerSubLevel sl = getSubLevel();
        if (sl == null) {return null;}
        RigidBodyHandle handle = RigidBodyHandle.of(sl);
        if (handle == null) {return null;}
        handle.applyImpulseAtPoint(new Vector3d(px, py, pz), new Vector3d(fx, fy, fz));
        return MethodResult.of(true);
    }

    @LuaFunction
    public final MethodResult applyImpulseAtRelativePoint(double rx, double ry, double rz, double fx, double fy, double fz) throws LuaException {
        if (!callLimiter.tryCall(tick)) return null;
        ServerSubLevel sl = getSubLevel();
        if (sl == null) {return null;}
        RigidBodyHandle handle = RigidBodyHandle.of(sl);
        if (handle == null) {return null;}
        BlockPos blockPos = source.getPos();
        handle.applyImpulseAtPoint(new Vector3d(blockPos.getX() + rx, blockPos.getY() + ry, blockPos.getZ() + rz), new Vector3d(fx, fy, fz));
        return MethodResult.of(true);
    }


    @Override
    public ObjectList<IComputerAccess> getAttachedComputers() {
        return computers;
    }
}
