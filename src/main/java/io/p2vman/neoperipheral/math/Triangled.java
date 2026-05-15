package io.p2vman.neoperipheral.math;

import dev.ryanhcode.sable.companion.math.BoundingBox3d;
import dev.ryanhcode.sable.companion.math.BoundingBox3dc;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.joml.Matrix3dc;
import org.joml.Vector3d;

public class Triangled implements Triangledc {
    public double hx;
    public double hy;
    public double hz;

    public double x1;
    public double y1;
    public double z1;

    public double x2;
    public double y2;
    public double z2;

    public double x3;
    public double y3;
    public double z3;

    public double x4;
    public double y4;
    public double z4;

    public Triangled(
            final double hx, final double hy, final double hz,
            final double x1, final double y1, final double z1,
            final double x2, final double y2, final double z2,
            final double x3, final double y3, final double z3,
            final double x4, final double y4, final double z4
            ) {
        this.hx = hx;
        this.hy = hy;
        this.hz = hz;

        this.x1 = x1;
        this.y1 = y1;
        this.z1 = z1;

        this.x2 = x2;
        this.y2 = y2;
        this.z2 = z2;

        this.x3 = x3;
        this.y3 = y3;
        this.z3 = z3;

        this.x4 = x4;
        this.y4 = y4;
        this.z4 = z4;
    }

    @Override
    public double hx() {
        return hx;
    }

    @Override
    public double hy() {
        return hy;
    }

    @Override
    public double hz() {
        return hz;
    }

    @Override
    public double x1() {
        return x1;
    }

    @Override
    public double y1() {
        return y1;
    }

    @Override
    public double z1() {
        return z1;
    }

    @Override
    public double x2() {
        return x2;
    }

    @Override
    public double y2() {
        return y2;
    }

    @Override
    public double z2() {
        return z2;
    }

    @Override
    public double x3() {
        return x3;
    }

    @Override
    public double y3() {
        return y3;
    }

    @Override
    public double z3() {
        return z3;
    }

    @Override
    public double x4() {
        return x4;
    }

    @Override
    public double y4() {
        return y4;
    }

    @Override
    public double z4() {
        return z4;
    }

    public void translate(final double x, final double y, final double z) {
        this.hx += x;
        this.hy += y;
        this.hz += z;

        this.x1 += x;
        this.y1 += y;
        this.z1 += z;

        this.x2 += x;
        this.y2 += y;
        this.z2 += z;

        this.x3 += x;
        this.y3 += y;
        this.z3 += z;

        this.x4 += x;
        this.y4 += y;
        this.z4 += z;
    }

    public void transform(Matrix3dc matrix) {
        this.transform(matrix, new Vector3d());
    }

    public void transform(Matrix3dc matrix3dc, Vector3d vec) {
        matrix3dc.transform(this.hx, this.hy, this.hz, vec);
        this.hx = vec.x;
        this.hy = vec.y;
        this.hz = vec.z;

        matrix3dc.transform(this.x1, this.y1, this.z1, vec);
        this.x1 = vec.x;
        this.y1 = vec.y;
        this.z1 = vec.z;

        matrix3dc.transform(this.x2, this.y2, this.z2, vec);
        this.x2 = vec.x;
        this.y2 = vec.y;
        this.z2 = vec.z;

        matrix3dc.transform(this.x3, this.y3, this.z3, vec);
        this.x3 = vec.x;
        this.y3 = vec.y;
        this.z3 = vec.z;

        matrix3dc.transform(this.x4, this.y4, this.z4, vec);
        this.x4 = vec.x;
        this.y4 = vec.y;
        this.z4 = vec.z;
    }

    public BoundingBox3dc computeAABB() {
        double minX = hx, maxX = hx, minY = hy, maxY = hy, minZ = hz, maxZ = hz;
        if (x1 < minX) minX = x1; else if (x1 > maxX) maxX = x1;
        if (y1 < minY) minY = y1; else if (y1 > maxY) maxY = y1;
        if (z1 < minZ) minZ = z1; else if (z1 > maxZ) maxZ = z1;
        if (x2 < minX) minX = x2; else if (x2 > maxX) maxX = x2;
        if (y2 < minY) minY = y2; else if (y2 > maxY) maxY = y2;
        if (z2 < minZ) minZ = z2; else if (z2 > maxZ) maxZ = z2;
        if (x3 < minX) minX = x3; else if (x3 > maxX) maxX = x3;
        if (y3 < minY) minY = y3; else if (y3 > maxY) maxY = y3;
        if (z3 < minZ) minZ = z3; else if (z3 > maxZ) maxZ = z3;
        if (x4 < minX) minX = x4; else if (x4 > maxX) maxX = x4;
        if (y4 < minY) minY = y4; else if (y4 > maxY) maxY = y4;
        if (z4 < minZ) minZ = z4; else if (z4 > maxZ) maxZ = z4;
        return new BoundingBox3d(minX, minY, minZ, maxX, maxY, maxZ);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    public boolean containsPoint(double px, double py, double pz) {
        double v0x = x1, v0y = y1, v0z = z1;
        double v1x = x2, v1y = y2, v1z = z2;
        double v2x = x3, v2y = y3, v2z = z3;
        double v3x = x4, v3y = y4, v3z = z4;
        double a1x = v1x - v0x, a1y = v1y - v0y, a1z = v1z - v0z;
        double a2x = v2x - v0x, a2y = v2y - v0y, a2z = v2z - v0z;
        double a3x = v3x - v0x, a3y = v3y - v0y, a3z = v3z - v0z;
        double apx = px - v0x, apy = py - v0y, apz = pz - v0z;
        double c2x = a2y * a3z - a2z * a3y;
        double c2y = a2z * a3x - a2x * a3z;
        double c2z = a2x * a3y - a2y * a3x;
        double v0 = a1x * c2x + a1y * c2y + a1z * c2z;
        double vp = apx * c2x + apy * c2y + apz * c2z;
        double cpx = apy * a3z - apz * a3y;
        double cpy = apz * a3x - apx * a3z;
        double cpz = apx * a3y - apy * a3x;
        double v1 = a1x * cpx + a1y * cpy + a1z * cpz;
        double cqy = a2z * apx - a2x * apz;
        double cqz = a2x * apy - a2y * apx;
        double v2 = a1x * (a2y * apz - a2z * apy) + a1y * cqy + a1z * cqz;
        double v3 = v0 - vp - v1 - v2;
        if (v0 > 0) return vp >= 0 && v1 >= 0 && v2 >= 0 && v3 >= 0;
        return vp <= 0 && v1 <= 0 && v2 <= 0 && v3 <= 0;
    }

    public boolean intersectsAABB(BoundingBox3dc box) {
        double minX = hx, maxX = hx;
        if (x1 < minX) minX = x1; else if (x1 > maxX) maxX = x1;
        if (x2 < minX) minX = x2; else if (x2 > maxX) maxX = x2;
        if (x3 < minX) minX = x3; else if (x3 > maxX) maxX = x3;
        if (x4 < minX) minX = x4; else if (x4 > maxX) maxX = x4;
        if (maxX < box.minX() || minX > box.maxX()) return false;

        double minY = hy, maxY = hy;
        if (y1 < minY) minY = y1; else if (y1 > maxY) maxY = y1;
        if (y2 < minY) minY = y2; else if (y2 > maxY) maxY = y2;
        if (y3 < minY) minY = y3; else if (y3 > maxY) maxY = y3;
        if (y4 < minY) minY = y4; else if (y4 > maxY) maxY = y4;
        if (maxY < box.minY() || minY > box.maxY()) return false;

        double minZ = hz, maxZ = hz;
        if (z1 < minZ) minZ = z1; else if (z1 > maxZ) maxZ = z1;
        if (z2 < minZ) minZ = z2; else if (z2 > maxZ) maxZ = z2;
        if (z3 < minZ) minZ = z3; else if (z3 > maxZ) maxZ = z3;
        if (z4 < minZ) minZ = z4; else if (z4 > maxZ) maxZ = z4;
        if (maxZ < box.minZ() || minZ > box.maxZ()) return false;

        double[][] v = {{x1,y1,z1},{x2,y2,z2},{x3,y3,z3},{x4,y4,z4}};
        double bminX = box.minX(), bminY = box.minY(), bminZ = box.minZ();
        double bmaxX = box.maxX(), bmaxY = box.maxY(), bmaxZ = box.maxZ();

        if (!axisTest(v, 1, 0, 0, bminX, bmaxX, bminY, bmaxY, bminZ, bmaxZ)) return false;
        if (!axisTest(v, 0, 1, 0, bminX, bmaxX, bminY, bmaxY, bminZ, bmaxZ)) return false;
        if (!axisTest(v, 0, 0, 1, bminX, bmaxX, bminY, bmaxY, bminZ, bmaxZ)) return false;

        double nx = (v[1][1]-v[0][1])*(v[2][2]-v[0][2]) - (v[1][2]-v[0][2])*(v[2][1]-v[0][1]);
        double ny = (v[1][2]-v[0][2])*(v[2][0]-v[0][0]) - (v[1][0]-v[0][0])*(v[2][2]-v[0][2]);
        double nz = (v[1][0]-v[0][0])*(v[2][1]-v[0][1]) - (v[1][1]-v[0][1])*(v[2][0]-v[0][0]);
        if (nx != 0 || ny != 0 || nz != 0) {
            double len = Math.sqrt(nx*nx + ny*ny + nz*nz);
            if (!axisTest(v, nx/len, ny/len, nz/len, bminX, bmaxX, bminY, bmaxY, bminZ, bmaxZ)) return false;
        }

        nx = (v[1][1]-v[0][1])*(v[3][2]-v[0][2]) - (v[1][2]-v[0][2])*(v[3][1]-v[0][1]);
        ny = (v[1][2]-v[0][2])*(v[3][0]-v[0][0]) - (v[1][0]-v[0][0])*(v[3][2]-v[0][2]);
        nz = (v[1][0]-v[0][0])*(v[3][1]-v[0][1]) - (v[1][1]-v[0][1])*(v[3][0]-v[0][0]);
        if (nx != 0 || ny != 0 || nz != 0) {
            double len = Math.sqrt(nx*nx + ny*ny + nz*nz);
            if (!axisTest(v, nx/len, ny/len, nz/len, bminX, bmaxX, bminY, bmaxY, bminZ, bmaxZ)) return false;
        }

        nx = (v[2][1]-v[0][1])*(v[3][2]-v[0][2]) - (v[2][2]-v[0][2])*(v[3][1]-v[0][1]);
        ny = (v[2][2]-v[0][2])*(v[3][0]-v[0][0]) - (v[2][0]-v[0][0])*(v[3][2]-v[0][2]);
        nz = (v[2][0]-v[0][0])*(v[3][1]-v[0][1]) - (v[2][1]-v[0][1])*(v[3][0]-v[0][0]);
        if (nx != 0 || ny != 0 || nz != 0) {
            double len = Math.sqrt(nx*nx + ny*ny + nz*nz);
            if (!axisTest(v, nx/len, ny/len, nz/len, bminX, bmaxX, bminY, bmaxY, bminZ, bmaxZ)) return false;
        }

        nx = (v[2][1]-v[1][1])*(v[3][2]-v[1][2]) - (v[2][2]-v[1][2])*(v[3][1]-v[1][1]);
        ny = (v[2][2]-v[1][2])*(v[3][0]-v[1][0]) - (v[2][0]-v[1][0])*(v[3][2]-v[1][2]);
        nz = (v[2][0]-v[1][0])*(v[3][1]-v[1][1]) - (v[2][1]-v[1][1])*(v[3][0]-v[1][0]);
        if (nx != 0 || ny != 0 || nz != 0) {
            double len = Math.sqrt(nx*nx + ny*ny + nz*nz);
            if (!axisTest(v, nx/len, ny/len, nz/len, bminX, bmaxX, bminY, bmaxY, bminZ, bmaxZ)) return false;
        }

        return true;
    }

    private boolean axisTest(double[][] v, double ax, double ay, double az,
                             double bminX, double bmaxX, double bminY, double bmaxY, double bminZ, double bmaxZ) {
        double minT = v[0][0]*ax + v[0][1]*ay + v[0][2]*az;
        double maxT = minT;
        for (int i = 1; i < 4; i++) {
            double p = v[i][0]*ax + v[i][1]*ay + v[i][2]*az;
            if (p < minT) minT = p; else if (p > maxT) maxT = p;
        }
        double p000 = bminX*ax + bminY*ay + bminZ*az;
        double p001 = bminX*ax + bminY*ay + bmaxZ*az;
        double p010 = bminX*ax + bmaxY*ay + bminZ*az;
        double p011 = bminX*ax + bmaxY*ay + bmaxZ*az;
        double p100 = bmaxX*ax + bminY*ay + bminZ*az;
        double p101 = bmaxX*ax + bminY*ay + bmaxZ*az;
        double p110 = bmaxX*ax + bmaxY*ay + bminZ*az;
        double p111 = bmaxX*ax + bmaxY*ay + bmaxZ*az;

        double minA = p000;
        if (p001 < minA) minA = p001;
        if (p010 < minA) minA = p010;
        if (p011 < minA) minA = p011;
        if (p100 < minA) minA = p100;
        if (p101 < minA) minA = p101;
        if (p110 < minA) minA = p110;
        if (p111 < minA) minA = p111;

        double maxA = p000;
        if (p001 > maxA) maxA = p001;
        if (p010 > maxA) maxA = p010;
        if (p011 > maxA) maxA = p011;
        if (p100 > maxA) maxA = p100;
        if (p101 > maxA) maxA = p101;
        if (p110 > maxA) maxA = p110;
        if (p111 > maxA) maxA = p111;

        return maxT >= minA && maxA >= minT;
    }
}
