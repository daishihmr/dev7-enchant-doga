package jp.dev7.enchant.doga.old.v4.parser.suf.data;

public class Vertex implements Comparable<Vertex> {

	public double x;
	public double y;
	public double z;
	public double normalX;
	public double normalY;
	public double normalZ;
	public double u;
	public double v;

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getZ() {
		return z;
	}

	public void setZ(double z) {
		this.z = z;
	}

	public double getNormalX() {
		return normalX;
	}

	public void setNormalX(double normalX) {
		this.normalX = normalX;
	}

	public double getNormalY() {
		return normalY;
	}

	public void setNormalY(double normalY) {
		this.normalY = normalY;
	}

	public double getNormalZ() {
		return normalZ;
	}

	public void setNormalZ(double normalZ) {
		this.normalZ = normalZ;
	}

	public double getU() {
		return u;
	}

	public void setU(double u) {
		this.u = u;
	}

	public double getV() {
		return v;
	}

	public void setV(double v) {
		this.v = v;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(normalX);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(normalY);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(normalZ);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(u);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(v);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(x);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(y);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(z);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vertex other = (Vertex) obj;
		if (Double.doubleToLongBits(normalX) != Double
				.doubleToLongBits(other.normalX))
			return false;
		if (Double.doubleToLongBits(normalY) != Double
				.doubleToLongBits(other.normalY))
			return false;
		if (Double.doubleToLongBits(normalZ) != Double
				.doubleToLongBits(other.normalZ))
			return false;
		if (Double.doubleToLongBits(u) != Double.doubleToLongBits(other.u))
			return false;
		if (Double.doubleToLongBits(v) != Double.doubleToLongBits(other.v))
			return false;
		if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x))
			return false;
		if (Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y))
			return false;
		if (Double.doubleToLongBits(z) != Double.doubleToLongBits(other.z))
			return false;
		return true;
	}

	@Override
	public int compareTo(Vertex o) {
		int c = Double.compare(x, o.x);
		if (c != 0)
			return c;
		c = Double.compare(y, o.y);
		if (c != 0)
			return c;
		c = Double.compare(z, o.z);
		if (c != 0)
			return c;
		c = Double.compare(normalX, o.normalX);
		if (c != 0)
			return c;
		c = Double.compare(normalY, o.normalY);
		if (c != 0)
			return c;
		c = Double.compare(normalZ, o.normalZ);
		if (c != 0)
			return c;
		c = Double.compare(u, o.u);
		if (c != 0)
			return c;
		c = Double.compare(v, o.v);
		return c;
	}

	@Override
	public String toString() {
		return "Vertex [x=" + x + ", y=" + y + ", z=" + z + ", normalX="
				+ normalX + ", normalY=" + normalY + ", normalZ=" + normalZ
				+ ", u=" + u + ", v=" + v + "]";
	}

}
