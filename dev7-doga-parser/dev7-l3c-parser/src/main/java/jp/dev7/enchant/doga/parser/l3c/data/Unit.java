package jp.dev7.enchant.doga.parser.l3c.data;

import java.util.Arrays;
import java.util.List;

import com.google.common.collect.Lists;

public class Unit {

	private final List<Unit> childUnits = Lists.newArrayList();

	private String name;

	private String l3pFileName;
	private double[] mov = { 0, 0, 0 };
	private double rotz;
	private double roty;
	private double rotx;

	private double[] unitScal = { 1, 1, 1 };
	private double[] unitMov = { 0, 0, 0 };

	private int posePointer;

	public List<Unit> getChildUnits() {
		return childUnits;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getL3pFileName() {
		return l3pFileName;
	}

	public void setL3pFileName(String l3pFileName) {
		this.l3pFileName = l3pFileName;
	}

	public double[] getMov() {
		return mov;
	}

	public void setMov(double[] mov) {
		this.mov = mov;
	}

	public double getRotz() {
		return rotz;
	}

	public void setRotz(double rotz) {
		this.rotz = rotz;
	}

	public double getRoty() {
		return roty;
	}

	public void setRoty(double roty) {
		this.roty = roty;
	}

	public double getRotx() {
		return rotx;
	}

	public void setRotx(double rotx) {
		this.rotx = rotx;
	}

	public double[] getUnitScal() {
		return unitScal;
	}

	public void setUnitScal(double[] unitScal) {
		this.unitScal = unitScal;
	}

	public double[] getUnitMov() {
		return unitMov;
	}

	public void setUnitMov(double[] unitMov) {
		this.unitMov = unitMov;
	}

	public int getPosePointer() {
		return posePointer;
	}

	public void setPosePointer(int posePointer) {
		this.posePointer = posePointer;
	}

	@Override
	public String toString() {
		return "Unit [l3pFileName=" + l3pFileName + ", mov="
				+ Arrays.toString(mov) + ", rotz=" + rotz + ", roty=" + roty
				+ ", rotx=" + rotx + ", unitScal=" + Arrays.toString(unitScal)
				+ ", unitMov=" + Arrays.toString(unitMov) + "]";
	}

}
