package jp.dev7.enchant.doga.parser.atr;

import java.util.List;

import jp.dev7.enchant.doga.parser.atr.data.Atr;
import jp.dev7.enchant.doga.parser.atr.data.Color;

import com.google.common.collect.Lists;

abstract class _Func implements _Value {

	static class IgnoreFunction extends _Func {
		@Override
		public void apply(Atr atr) {
			// no op
		}
	}

	static class Col extends _Func {
		@Override
		public void apply(Atr atr) {
			if (args.get(0) instanceof Rgb) {
				((Rgb) (args.get(0))).apply(atr);
			} else if (args.get(0) instanceof _Num) {
				Color color = new Color();
				color.red = ((_Num) args.get(0)).getValue();
				color.green = ((_Num) args.get(0)).getValue();
				color.blue = ((_Num) args.get(0)).getValue();
				atr.setCol(color);
			}
		}
	}

	static class Rgb extends _Func {
		@Override
		public void apply(Atr atr) {
			Color color = new Color();
			color.red = ((_Num) args.get(0)).getValue();
			color.green = ((_Num) args.get(1)).getValue();
			color.blue = ((_Num) args.get(2)).getValue();
			atr.setCol(color);
		}
	}

	static class Amb extends _Func {
		@Override
		public void apply(Atr atr) {
			atr.setAmb(((_Num) args.get(0)).getValue());
		}
	}

	static class Dif extends _Func {
		@Override
		public void apply(Atr atr) {
			atr.setDif(((_Num) args.get(0)).getValue());
		}
	}

	static class Spc extends _Func {
		@Override
		public void apply(Atr atr) {
			double f = ((_Num) args.get(0)).getValue();
			double s = ((_Num) args.get(1)).getValue();
			double c = ((_Num) args.get(2)).getValue();
			atr.setSpc(new double[] { f, s, c });
		}
	}

	static class Tra extends _Func {
		@Override
		public void apply(Atr atr) {
			atr.setTra(((_Num) args.get(0)).getValue());
		}
	}

	static class ColorMap extends _Func {
		@Override
		public void apply(Atr atr) {
			if (atr.getColorMap1() == null) {
				atr.setColorMap1(((_FilePath) args.get(0)).getValue());
			} else {
				atr.setColorMap2(((_FilePath) args.get(0)).getValue());
			}
		}
	}

	static class MapSize extends _Func {
		@Override
		public void apply(Atr atr) {
			double umin = ((_Num) args.get(0)).getValue();
			double vmin = ((_Num) args.get(1)).getValue();
			double umax = ((_Num) args.get(2)).getValue();
			double vmax = ((_Num) args.get(3)).getValue();
			atr.setMapSize(new double[] { umin, vmin, umax, vmax });
		}
	}

	static class Emittion extends _Func {
		@Override
		public void apply(Atr atr) {
			double p1 = ((_Num) args.get(0)).getValue();
			double p2 = ((_Num) args.get(1)).getValue();
			atr.setOptEmittion(new double[] { p1, p2 });
		}
	}

	private String funcName;
	protected final List<_Value> args = Lists.newArrayList();

	public String getFuncName() {
		return funcName;
	}

	public void setFuncName(String funcName) {
		this.funcName = funcName;
	}

	public List<_Value> getArgs() {
		return args;
	}

	public abstract void apply(Atr atr);

	public static _Func create(String funcName) {
		_Func result;
		if (funcName == null) {
			result = new IgnoreFunction();
		} else if (funcName.equals("col")) {
			result = new Col();
		} else if (funcName.equals("rgb")) {
			result = new Rgb();
		} else if (funcName.equals("amb")) {
			result = new Amb();
		} else if (funcName.equals("dif")) {
			result = new Dif();
		} else if (funcName.equals("spc")) {
			result = new Spc();
		} else if (funcName.equals("tra")) {
			result = new Tra();
		} else if (funcName.equals("colormap")) {
			result = new ColorMap();
		} else if (funcName.equals("mapsize")) {
			result = new MapSize();
		} else if (funcName.equals("emittion")) {
			result = new Emittion();
		} else {
			result = new IgnoreFunction();
		}

		result.setFuncName(funcName);
		return result;
	}
}
