package jp.dev7.enchant.doga.parser;

import java.io.File;

import jp.dev7.enchant.doga.parser.L3cFileParser;
import jp.dev7.enchant.doga.parser.data.Connection;
import jp.dev7.enchant.doga.parser.data.ConnectionObj;
import jp.dev7.enchant.doga.parser.util.Props;
import junit.framework.TestCase;

public class L3cFileParserTest extends TestCase {

    public void test43() throws Exception {
        L3cFileParser parser = new L3cFileParser();
        parser.parse(new File(Props.dataDir(),
                "l3_samp/connection/human/man/darkhero.l3c"));
    }

    public void testParse() throws Exception {

        L3cFileParser parser = new L3cFileParser();
        Connection l3c = parser.parse(new File(Props.dataDir(),
                "l3_samp/connection/human/robot/heavyms.l3c"));

        assertNotNull(l3c);
        assertNotNull(l3c.getRootUnit());

        assertEquals(l3c.getRootUnit().getName(), "MSHip");
        assertEquals(l3c.getRootUnit().getUnitFileName(), "HeavyMS\\MSHip.l3p");
        assertEquals(l3c.getRootUnit().getMov()[0], 0.0);
        assertEquals(l3c.getRootUnit().getMov()[1], 0.0);
        assertEquals(l3c.getRootUnit().getMov()[2], 1020.0);
        assertEquals(l3c.getRootUnit().getUnitScal()[0], 1.0);
        assertEquals(l3c.getRootUnit().getUnitScal()[1], 1.0);
        assertEquals(l3c.getRootUnit().getUnitScal()[2], 1.0);
        assertEquals(l3c.getRootUnit().getPosePointer(), 0);

        assertEquals(l3c.getRootUnit().getChildUnits().size(), 3);
        ConnectionObj thigh = l3c.getRootUnit().getChildUnits().get(2);
        assertEquals(thigh.getMov()[1], -103.0);
        assertEquals(thigh.getUnitScal()[1], -1.0);

        ConnectionObj waist = l3c.getRootUnit().getChildUnits().get(1);
        assertEquals(waist.getName(), "MSWaist");

        ConnectionObj bust = waist.getChildUnits().get(0);
        assertEquals(bust.getName(), "MSBust");

        ConnectionObj joint = bust.getChildUnits().get(3);
        assertEquals(joint.getName(), "MSJoint");
        assertEquals(joint.getUnitMov()[0], 907.0);
        assertEquals(joint.getUnitMov()[1], -13.0);
        assertEquals(joint.getUnitMov()[2], 0.0);

        ConnectionObj gun = joint.getChildUnits().get(0);
        assertEquals(gun.getName(), "MSGun");
        assertEquals(gun.getRotz(), 180.0);
        assertEquals(gun.getRoty(), -50.0);
        assertEquals(gun.getRotx(), -90.0);
    }

    public void testParse2() throws Exception {

        L3cFileParser parser = new L3cFileParser();
        Connection l3c = parser.parse(new File(Props.dataDir(),
                "l3_samp/connection/human/robot/ms_kage.l3c"));

        ConnectionObj kgleg = l3c.getRootUnit().getChildUnits().get(0).getChildUnits()
                .get(0);
        assertEquals(kgleg.getName(), "kgleg");
        assertEquals(kgleg.getPosePointer(), 12);
    }

    public void testParse3() throws Exception {

        L3cFileParser parser = new L3cFileParser();
        Connection l3c = parser.parse(new File(Props.dataDir(),
                "l3_samp/connection/human/woman/idol.l3c"));

        assertNotNull(l3c);
        assertNotNull(l3c.getRootUnit());
    }

}
