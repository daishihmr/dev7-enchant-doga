package jp.dev7.enchant.doga.old.v4.parser.atr;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.List;

import jp.dev7.enchant.doga.old.v4.parser.atr.data.Atr;

import com.google.common.collect.Lists;

/**
 * DoGA ATRファイルをパースする.
 * 
 * @author daishi_hmr
 */
public class AtrFileParser {
	private AtrFileParser() {
	}

	public static List<Atr> parse(File file) throws ParseException, IOException {
		final Reader in = new InputStreamReader(new FileInputStream(file),
				"Shift_JIS");
		return parse(in);
	}

	public static List<Atr> parseLine(String line) throws ParseException,
			IOException {
		return parse(new StringReader(line));
	}

	public static List<Atr> parse(Reader in) throws ParseException, IOException {
		try {
			final AtrParser parser = new AtrParser(in);
			final ASTStart start = parser.Start();

			final List<Atr> atrs = Lists.newArrayList();

			final AtrParserVisitor visitor = new AtrParserVisitor() {
				private Atr currentAtr;

				@Override
				public Object visit(ASTStart node, Object data) {
					return node.childrenAccept(this, data);
				}

				@Override
				public Object visit(ASTAtr node, Object data) {
					final Atr atr = new Atr();
					currentAtr = atr;
					node.childrenAccept(this, atr);
					currentAtr = null;
					atrs.add(atr);
					return data;
				}

				@Override
				public Object visit(ASTAtrName node, Object data) {
					if (data instanceof Atr) {
						Atr atr = (Atr) data;
						atr.setName(node.nodeValue);
					}
					return node.childrenAccept(this, data);
				}

				@Override
				public Object visit(ASTFunc node, Object data) {
					final _Func _this = _Func.create(node.nodeValue);

					node.childrenAccept(this, _this);

					if (data instanceof _Func) {
						final _Func parent = (_Func) data;
						parent.getArgs().add(_this);
					} else if (data instanceof Atr) {
						_this.apply((Atr) data);
					}

					return data;
				}

				@Override
				public Object visit(ASTEmittion node, Object data) {
					final _Func _this = _Func.create("emittion");
					node.childrenAccept(this, _this);
					if (data instanceof Atr) {
						_this.apply((Atr) data);
					} else if (currentAtr != null) {
						_this.apply(currentAtr);
					}
					return data;
				}

				@Override
				public Object visit(ASTNum node, Object data) {
					final _Num _this = new _Num();
					_this.setValue(Double.parseDouble(node.nodeValue));

					if (data instanceof _Func) {
						_Func parent = (_Func) data;
						parent.getArgs().add(_this);
					}
					return data;
				}

				@Override
				public Object visit(ASTFilePath node, Object data) {
					final _FilePath _this = new _FilePath();
					_this.setValue(node.nodeValue);
					if (data instanceof _Func) {
						_Func parent = (_Func) data;
						parent.getArgs().add(_this);
					}
					return data;
				}

				@Override
				public Object visit(SimpleNode node, Object data) {
					return null;
				}

			};

			start.jjtAccept(visitor, null);

			return atrs;
		} finally {
			try {
				in.close();
			} catch (IOException e) {
			}
		}
	}
}
