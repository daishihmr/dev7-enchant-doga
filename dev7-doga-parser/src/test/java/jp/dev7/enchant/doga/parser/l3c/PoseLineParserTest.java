package jp.dev7.enchant.doga.parser.l3c;

import jp.dev7.enchant.doga.parser.l3c.data.Pose;
import junit.framework.TestCase;

public class PoseLineParserTest extends TestCase {

	public void testParse() throws Exception {
		String line = "0: Pose1 ( labelled 24 ( HIP move (    0    0    0 ) rot (    0    0    0 ) ) ( LTHIGH move (    0    0    0 ) rot (   15  -10   10 ) ) ( LSHIN move (   -0   -0   -0 ) rot (   -0   10   -2 ) ) ( LFOOT move (    0   -0   -0 ) rot (  -14    4   14 ) ) ( LTOE move (    0    0   -0 ) rot (    0   -0    0 ) ) ( WAIST move (    0    0    0 ) rot (    0    0    0 ) ) ( BREAST move (    0    0    0 ) rot (    0    0    0 ) ) ( LSHOULDER move (    0    0    0 ) rot (    0    0    0 ) ) ( LUPPERARM move (    0    0    0 ) rot (   20    0    0 ) ) ( LFOREARM move (    0    0    0 ) rot (    0  -45    0 ) ) ( LHAND move (   -0   -0   -0 ) rot (    1  -17   -2 ) ) ( NECK move (    0    0    0 ) rot (    0    0    0 ) ) ( HEAD move (    0    0    0 ) rot (    0    0    0 ) ) ( RSHOULDER move (    0    0    0 ) rot (    0    0    0 ) ) ( RUPPERARM move (    0    0    0 ) rot (  -20    0    0 ) ) ( RFOREARM move (    0    0    0 ) rot (    0  -45    0 ) ) ( RHAND move (    0    0    0 ) rot (    2   -9   -0 ) ) ( unlabelled move (    0    0    0 ) rot (    0   40  -90 ) ) ( unlabelled move (    0    0    0 ) rot (  -90  -50  180 ) ) ( unlabelled move (    0    0    0 ) rot (    0    0    0 ) ) ( RTHIGH move (    0   -0    0 ) rot (  -15  -10  -10 ) ) ( RSHIN move (    0    0   -0 ) rot (    0   10    2 ) ) ( RFOOT move (    0    0   -0 ) rot (   14    4  -14 ) ) ( RTOE move (    0    0    0 ) rot (    0    0    0 ) ) )";
		Pose pose = PoseLineParser.parse(line);

		assertEquals(pose.getName(), "Pose1");
		assertEquals(pose.getLabels().get(1), "LTHIGH");
		assertEquals(pose.getUnitPose().get(2)[0], -0.0);
		assertEquals(pose.getUnitPose().get(2)[1], -0.0);
		assertEquals(pose.getUnitPose().get(2)[2], -0.0);
		assertEquals(pose.getUnitPose().get(1)[3], 15.0);
		assertEquals(pose.getUnitPose().get(1)[4], -10.0);
		assertEquals(pose.getUnitPose().get(1)[5], 10.0);
	}

}
