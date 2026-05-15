package io.p2vman.neoperipheral.peripheral;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dan200.computercraft.api.lua.LuaFunction;
import dan200.computercraft.api.lua.MethodResult;
import dev.ryanhcode.sable.Sable;
import dev.ryanhcode.sable.companion.math.BoundingBox3d;
import dev.ryanhcode.sable.sublevel.ServerSubLevel;
import dev.ryanhcode.sable.sublevel.SubLevel;
import io.p2vman.neoperipheral.Config;
import io.p2vman.neoperipheral.IPrefSource;
import io.p2vman.neoperipheral.lua.Table;
import io.p2vman.neoperipheral.lua.TableArray;
import io.p2vman.neoperipheral.lua.UUIDTable;
import io.p2vman.neoperipheral.math.Triangled;
import io.p2vman.neoperipheral.util.CallLimiter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.ParticleStatus;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix3d;
import org.joml.Matrix4d;
import org.joml.Matrix4f;
import org.joml.Vector3d;

import java.util.Random;

public class TriangleRadarPeripheral extends BasePeripheral implements IPeripheralRenderer {
    private final Random random = new Random();
    private CallLimiter callLimiter;
    public TriangleRadarPeripheral(Direction direction, IPrefSource source) {
        super(source);
        this.callLimiter = new CallLimiter.RingCallLimiter(8, 20);
    }

    @Override
    public String getType() {
        return "neo_triangle_radar";
    }

    @LuaFunction(mainThread = true)
    public MethodResult scanForSubLevels() {
        var level = this.source.getLevel();
        var server =  level.getServer();
        if (!this.callLimiter.tryCall(server.getTickCount())) return MethodResult.of(false);
        ServerSubLevel subLevel = (ServerSubLevel) Sable.HELPER.getContaining(level, this.source.getPos().getCenter());
        var _pos = Sable.HELPER.projectOutOfSubLevel(this.source.getLevel(), this.source.getPos().getCenter());

        var radius = 15;

        Triangled tr = new Triangled(
                0, 0, 0,
                radius * (float)Math.cos(0),              radius, radius * (float)Math.sin(0),
                radius * (float)Math.cos(Math.PI / 2),    radius, radius * (float)Math.sin(Math.PI / 2),
                radius * (float)Math.cos(Math.PI),        radius, radius * (float)Math.sin(Math.PI),
                radius * (float)Math.cos(3 * Math.PI / 2),radius, radius * (float)Math.sin(3 * Math.PI / 2)
        );

        Matrix3d matrix = new Matrix3d();
        matrix.rotate(subLevel.lastPose().orientation());
        tr.transform(matrix);
        tr.translate(_pos.x, _pos.y, _pos.z);


        var sub_levels = new TableArray();
        var increment = 1;
        var it = Sable.HELPER.getAllIntersecting(this.source.getLevel(), tr.computeAABB()).iterator();
        System.out.println(tr);
        while (it.hasNext()) {
            var subLevel2 = (ServerSubLevel) it.next();

            if (tr.intersectsAABB(subLevel2.boundingBox())) {
                var sub = new Table();
                sub.put("id", new UUIDTable(subLevel2.getUniqueId()));
                var pose = subLevel2.logicalPose();
                var pos = pose.position();
                var dx = _pos.x - pos.x;
                var dy = _pos.y - pos.y;
                var dz = _pos.z - pos.z;
                var dist = dx * dx + dy * dy + dz * dz;
                var err = dist/(256);
                sub.put("distance", dist + (random.nextDouble() * 2 - 1) * err);
                sub.put("x", (_pos.x - pos.x) + (random.nextDouble() * 2 - 1) * err);
                sub.put("y", (_pos.y - pos.y) + (random.nextDouble() * 2 - 1) * err);
                sub.put("z", (_pos.z - pos.z) + (random.nextDouble() * 2 - 1) * err);
                var quat = pose.orientation();
                sub.put("q_x", quat.x);
                sub.put("q_y", quat.y);
                sub.put("q_z", quat.z);
                sub.put("q_w", quat.w);

                sub_levels.put(increment++, sub);
            }
        }
        return MethodResult.of(sub_levels);
    }

    @Override
    public void render(float partialTick,
                       PoseStack poseStack,
                       MultiBufferSource buffer,
                       int packedLight,
                       int packedOverlay) {

    }
}
