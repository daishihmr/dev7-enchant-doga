package jp.dev7.enchant.doga.parser.data;

/**
 * 多関節物体ファイル(*.l2c, *.l3c)に相当.
 */
public class Connection {

    private ConnectionObj rootUnit;

    public ConnectionObj getRootUnit() {
        return rootUnit;
    }

    public void setRootUnit(ConnectionObj rootUnit) {
        this.rootUnit = rootUnit;
    }
}
