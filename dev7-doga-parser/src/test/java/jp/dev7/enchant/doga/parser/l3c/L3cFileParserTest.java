package jp.dev7.enchant.doga.parser.l3c;

import java.io.File;

import jp.dev7.enchant.doga.parser.Props;
import jp.dev7.enchant.doga.parser.l3c.data.L3c;
import jp.dev7.enchant.doga.parser.l3c.data.L3cObj;
import junit.framework.TestCase;

public class L3cFileParserTest extends TestCase {

    public void test43() throws Exception {
        L3cFileParser parser = new L3cFileParser();
        parser.parse(new File(Props.dataDir(),
                "l3_samp/connection/human/man/darkhero.l3c"));
    }

    public void testParse() throws Exception {

        L3cFileParser parser = new L3cFileParser();
        L3c l3c = parser.parse(new File(Props.dataDir(),
                "l3_samp/connection/human/robot/heavyms.l3c"));

        assertNotNull(l3c);
        assertNotNull(l3c.getRootUnit());

        assertEquals(l3c.getRootUnit().getName(), "MSHip");
        assertEquals(l3c.getRootUnit().getL3pFileName(), "HeavyMS\\MSHip.l3p");
        assertEquals(l3c.getRootUnit().getMov()[0], 0.0);
        assertEquals(l3c.getRootUnit().getMov()[1], 0.0);
        assertEquals(l3c.getRootUnit().getMov()[2], 1020.0);
        assertEquals(l3c.getRootUnit().getUnitScal()[0], 1.0);
        assertEquals(l3c.getRootUnit().getUnitScal()[1], 1.0);
        assertEquals(l3c.getRootUnit().getUnitScal()[2], 1.0);
        assertEquals(l3c.getRootUnit().getPosePointer(), 0);

        assertEquals(l3c.getRootUnit().getChildUnits().size(), 3);
        L3cObj thigh = l3c.getRootUnit().getChildUnits().get(2);
        assertEquals(thigh.getMov()[1], -103.0);
        assertEquals(thigh.getUnitScal()[1], -1.0);

        L3cObj waist = l3c.getRootUnit().getChildUnits().get(1);
        assertEquals(waist.getName(), "MSWaist");

        L3cObj bust = waist.getChildUnits().get(0);
        assertEquals(bust.getName(), "MSBust");

        L3cObj joint = bust.getChildUnits().get(3);
        assertEquals(joint.getName(), "MSJoint");
        assertEquals(joint.getUnitMov()[0], 907.0);
        assertEquals(joint.getUnitMov()[1], -13.0);
        assertEquals(joint.getUnitMov()[2], 0.0);

        L3cObj gun = joint.getChildUnits().get(0);
        assertEquals(gun.getName(), "MSGun");
        assertEquals(gun.getRotz(), 180.0);
        assertEquals(gun.getRoty(), -50.0);
        assertEquals(gun.getRotx(), -90.0);
    }

    public void testParse2() throws Exception {

        L3cFileParser parser = new L3cFileParser();
        L3c l3c = parser.parse(new File(Props.dataDir(),
                "l3_samp/connection/human/robot/ms_kage.l3c"));

        L3cObj kgleg = l3c.getRootUnit().getChildUnits().get(0).getChildUnits()
                .get(0);
        assertEquals(kgleg.getName(), "kgleg");
        assertEquals(kgleg.getPosePointer(), 12);
    }

    public void testParse3() throws Exception {

        L3cFileParser parser = new L3cFileParser();
        L3c l3c = parser.parse(new File(Props.dataDir(),
                "l3_samp/connection/human/woman/idol.l3c"));

        assertNotNull(l3c);
        assertNotNull(l3c.getRootUnit());
    }

}
