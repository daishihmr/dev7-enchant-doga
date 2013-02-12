package jp.dev7.enchant.doga.converter.l3c;

import javax.vecmath.Quat4d;

import junit.framework.TestCase;

public class QuatTest extends TestCase {

    public void testQuat() throws Exception {
        Quat4d q = createQuat(1, 2, 3);
        System.out.println(q.x + " " + q.y + " " + q.z + " " + q.w);
    }

    private Quat4d newQuat(double x, double y, double z, double rad) {
        double l = Math.sqrt(x * x + y * y + z * z);
        if (l != 0.0 && l != -0.0) {
            x /= l;
            y /= l;
            z /= l;
        }
        double s = Math.sin(rad / 2);
        double c = Math.cos(rad / 2);
        return new Quat4d(-x * s, -y * s, -z * s, c);
    }

    private Quat4d createQuat(double rotX, double rotY, double rotZ) {
        Quat4d q = newQuat(0, 0, 1, rotZ);
        q.mul(newQuat(1, 0, 0, rotX));
        q.mul(newQuat(0, 1, 0, rotY));
        return q;
    }

}
