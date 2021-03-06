package jp.dev7.enchant.doga.old.v4.parser.l3c;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import jp.dev7.enchant.doga.old.v4.parser.l3c.data.L3c;
import jp.dev7.enchant.doga.old.v4.parser.l3c.data.Unit;

public class L3cFileParser {

	public L3c parse(File file) throws Exception {
		final L3c result = new L3c();

		final InputStreamReader in = new InputStreamReader(new FileInputStream(
				file), "Shift_JIS");

		final L3cParser parser = new L3cParser(in);
		final ASTStart start = parser.Start();

		final L3cParserVisitor visitor = new L3cParserVisitor() {

			@Override
			public Object visit(ASTStart node, Object data) {
				return node.childrenAccept(this, data);
			}

			@Override
			public Object visit(ASTUnit node, Object data) {
				final Unit unit = new Unit();
				node.childrenAccept(this, unit);

				if (data instanceof L3c) {
					final L3c parent = (L3c) data;
					parent.setRootUnit(unit);
				} else if (data instanceof Unit) {
					final Unit parent = (Unit) data;
					parent.getChildUnits().add(unit);
				}

				return data;
			}

			@Override
			public Object visit(ASTObj node, Object data) {
				if (data instanceof Unit) {
					Unit unit = (Unit) data;
					unit.setName(node.nodeValue);
				}
				return node.childrenAccept(this, data);
			}

			@Override
			public Object visit(ASTFileName node, Object data) {
				if (data instanceof Unit) {
					Unit unit = (Unit) data;
					unit.setL3pFileName(node.nodeValue);
				}
				return node.childrenAccept(this, data);
			}

			@Override
			public Object visit(ASTMov node, Object data) {
				if (data instanceof Unit) {
					Unit unit = (Unit) data;
					Double x = (Double) node.jjtGetChild(0).jjtAccept(this,
							null);
					Double y = (Double) node.jjtGetChild(1).jjtAccept(this,
							null);
					Double z = (Double) node.jjtGetChild(2).jjtAccept(this,
							null);
					unit.setMov(new double[] { x, y, z });

					if (3 < node.jjtGetNumChildren()) {
						Double p = (Double) node.jjtGetChild(4).jjtAccept(this,
								null);
						unit.setPosePointer(p.intValue());
					}
				}
				return node.childrenAccept(this, data);
			}

			@Override
			public Object visit(ASTUnitMov node, Object data) {
				if (data instanceof Unit) {
					Unit unit = (Unit) data;
					Double x = (Double) node.jjtGetChild(0).jjtAccept(this,
							null);
					Double y = (Double) node.jjtGetChild(1).jjtAccept(this,
							null);
					Double z = (Double) node.jjtGetChild(2).jjtAccept(this,
							null);
					unit.setUnitMov(new double[] { x, y, z });
				}
				return node.childrenAccept(this, data);
			}

			@Override
			public Object visit(ASTScal node, Object data) {
				if (data instanceof Unit) {
					Unit unit = (Unit) data;
					Double x = (Double) node.jjtGetChild(0).jjtAccept(this,
							null);
					Double y = (Double) node.jjtGetChild(1).jjtAccept(this,
							null);
					Double z = (Double) node.jjtGetChild(2).jjtAccept(this,
							null);
					unit.setUnitScal(new double[] { x, y, z });
				}
				return node.childrenAccept(this, data);
			}

			@Override
			public Object visit(ASTRotx node, Object data) {
				if (data instanceof Unit) {
					Unit unit = (Unit) data;
					Double value = (Double) node.jjtGetChild(0).jjtAccept(this,
							null);
					unit.setRotx(value);
				}
				return node.childrenAccept(this, data);
			}

			@Override
			public Object visit(ASTRoty node, Object data) {
				if (data instanceof Unit) {
					Unit unit = (Unit) data;
					Double value = (Double) node.jjtGetChild(0).jjtAccept(this,
							null);
					unit.setRoty(value);
				}
				return node.childrenAccept(this, data);
			}

			@Override
			public Object visit(ASTRotz node, Object data) {
				if (data instanceof Unit) {
					Unit unit = (Unit) data;
					Double value = (Double) node.jjtGetChild(0).jjtAccept(this,
							null);
					unit.setRotz(value);
				}
				return node.childrenAccept(this, data);
			}

			@Override
			public Object visit(ASTText node, Object data) {
				return node.nodeValue;
			}

			@Override
			public Object visit(ASTNum node, Object data) {
				return Double.parseDouble(node.nodeValue);
			}

			@Override
			public Object visit(ASTFunc node, Object data) {
				return null;
			}

			@Override
			public Object visit(SimpleNode node, Object data) {
				return null;
			}
		};

		start.jjtAccept(visitor, result);

		return result;
	}
}
