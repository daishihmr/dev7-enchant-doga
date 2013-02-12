package jp.dev7.enchant.doga.parser.l3p;

import java.util.List;

import jp.dev7.enchant.doga.parser.l3p.data.Part;

import com.google.common.collect.Lists;

abstract class _Func {

    protected final List<Double> args = Lists.newArrayList();

    static class Mov extends _Func {

        @Override
        public void apply(Part l3pObj) {
            l3pObj.setMov(new double[] { args.get(0), args.get(1), args.get(2) });
        }
    }

    static class RotX extends _Func {

        @Override
        public void apply(Part l3pObj) {
            l3pObj.setRotx(args.get(0));
        }
    }

    static class RotY extends _Func {

        @Override
        public void apply(Part l3pObj) {
            l3pObj.setRoty(args.get(0));
        }
    }

    static class RotZ extends _Func {

        @Override
        public void apply(Part l3pObj) {
            l3pObj.setRotz(args.get(0));
        }

    }

    static class Scal extends _Func {

        @Override
        public void apply(Part l3pObj) {
            l3pObj.setScal(new double[] { args.get(0), args.get(1), args.get(2) });
        }

    }

    public void addArg(double arg) {
        args.add(arg);
    }

    public double getArg(int index) {
        return args.get(index);
    }

    public abstract void apply(Part l3pObj);

    @Override
    public String toString() {
        return getClass().getSimpleName() + " " + args;
    }

}
