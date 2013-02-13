package jp.dev7.enchant.doga.parser.fsc;

import java.util.List;

import jp.dev7.enchant.doga.parser.data.UnitObj;

import com.google.common.collect.Lists;

abstract public class _Func {

    protected final List<Double> args = Lists.newArrayList();

    public static class Mov extends _Func {

        @Override
        public void apply(UnitObj part) {
            part.setMov(new double[] { args.get(0), args.get(1), args.get(2) });
        }
    }

    public static class RotX extends _Func {

        @Override
        public void apply(UnitObj part) {
            part.setRotx(args.get(0));
        }
    }

    public static class RotY extends _Func {

        @Override
        public void apply(UnitObj part) {
            part.setRoty(args.get(0));
        }
    }

    public static class RotZ extends _Func {

        @Override
        public void apply(UnitObj part) {
            part.setRotz(args.get(0));
        }

    }

    public static class Scal extends _Func {

        @Override
        public void apply(UnitObj part) {
            part.setScal(new double[] { args.get(0), args.get(1), args.get(2) });
        }

    }

    public void addArg(double arg) {
        args.add(arg);
    }

    public double getArg(int index) {
        return args.get(index);
    }

    public abstract void apply(UnitObj part);

    @Override
    public String toString() {
        return getClass().getSimpleName() + " " + args;
    }

}
