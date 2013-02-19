package jp.dev7.enchant.doga.old.v4.parser.l3p;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jp.dev7.enchant.doga.old.v4.parser.atr.data.Atr;
import jp.dev7.enchant.doga.old.v4.parser.l3p.data.L3p;
import jp.dev7.enchant.doga.old.v4.parser.l3p.data.Part;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;

public class L3pFileParser {

	private L3p resultL3p;

	private final Logger logger = LoggerFactory.getLogger(L3pFileParser.class);

	public L3p parse(File l3pFile) throws Exception {
		final L3p l3pData = new L3p();

		// obj部分のコメント解析
		final Map<Integer, String> objSufMap = Maps.newHashMap();
		final Map<Integer, String> objAtrMap = Maps.newHashMap();
		{
			final Pattern pattern = Pattern
					.compile(".*obj \\\"?\\w+\\\"? /\\* \\\"?([^\"]+)\\\"? atr \\\"?([^\"]+)\\\"? \\*/.*");
			final BufferedReader reader = new BufferedReader(
					new InputStreamReader(new FileInputStream(l3pFile),
							"Shift_JIS"));
			String l;
			Integer i = 0;
			while ((l = reader.readLine()) != null) {
				Matcher m = pattern.matcher(l.trim());
				if (m.matches()) {
					String sufFile;
					String atrName;
					sufFile = m.group(1);
					atrName = m.group(2);

					objSufMap.put(i, sufFile);
					objAtrMap.put(i, atrName);
					i++;
				}
			}
			reader.close();
		}

		// パレット部分解析
		final Map<String, Atr> palette = Maps.newHashMap();
		{
			final BufferedReader reader = new BufferedReader(
					new InputStreamReader(new FileInputStream(l3pFile),
							"Shift_JIS"));
			String l;
			while ((l = reader.readLine()) != null && !l.startsWith("Palette:")) {
				// Palette行まで読み飛ばす
			}
			while ((l = reader.readLine()) != null) {
				l = l.trim();
				if (l.equals("*/")) {
					break;
				}
				Atr atr = AtrLineParser.parse(l);
				palette.put(atr.getName(), atr);
			}
		}

		final InputStreamReader in = new InputStreamReader(new FileInputStream(
				l3pFile), "Shift_JIS");
		try {
			final L3pParser l3pParser = new L3pParser(in);
			ASTStart start;
			try {
				start = l3pParser.Start();
			} catch (Throwable e) {
				logger.error("L3Pファイルパース中にエラー!! " + l3pFile, e);
				throw (Exception) e;
			}

			final L3pParserVisitor visitor = new L3pParserVisitor() {

				int i = 0;

				public Object visit(ASTStart node, Object data) {
					return node.childrenAccept(this, data);
				}

				public Object visit(ASTFram node, Object data) {
					return node.childrenAccept(this, data);
				}

				public Object visit(ASTObjData node, Object data) {
					L3p l3pData = (L3p) data;

					if (node.nodeValue != null) {
						Part l3pObj = new Part();

						l3pObj.setName(node.nodeValue);
						l3pObj.setSufFileName(objSufMap.get(i));
						l3pObj.setPaletteName(objAtrMap.get(i));
						i++;
						node.childrenAccept(this, l3pObj);

						l3pData.getObjects().add(l3pObj);
					}
					return null;
				}

				public Object visit(ASTFunc node, Object data) {
					if (data instanceof Part) {
						Part l3pObj = (Part) data;

						// funcName
						_Func f = (_Func) node.jjtGetChild(0).jjtAccept(this,
								null);
						if (f == null) {
							return null;
						}

						// args
						if (node.jjtGetNumChildren() > 0) {
							for (int i = 1; i < node.jjtGetNumChildren(); i++) {
								node.jjtGetChild(i).jjtAccept(this, f);
							}
						}

						f.apply(l3pObj);

						return data;
					} else {
						return null;
					}
				}

				public Object visit(ASTFuncName node, Object data) {
					if (node.nodeValue == null) {
						return null;
					} else if (node.nodeValue.equals("mov")) {
						return new _Func.Mov();
					} else if (node.nodeValue.equals("rotx")) {
						return new _Func.RotX();
					} else if (node.nodeValue.equals("roty")) {
						return new _Func.RotY();
					} else if (node.nodeValue.equals("rotz")) {
						return new _Func.RotZ();
					} else if (node.nodeValue.equals("scal")) {
						return new _Func.Scal();
					} else {
						return null;
					}
				}

				public Object visit(ASTNum node, Object data) {
					_Func f = (_Func) data;
					if (f == null) {
						return null;
					} else {
						f.addArg(Double.parseDouble(node.nodeValue));
					}
					return f;
				}

				public Object visit(SimpleNode node, Object data) {
					return null;
				}

			};

			try {
				start.jjtAccept(visitor, l3pData);
			} catch (Throwable e) {
				logger.error("L3Pファイルパース中にエラー!! " + l3pFile, e);
				throw (Exception) e;
			}

			resultL3p = l3pData;
			resultL3p.getPalette().putAll(palette);

			return resultL3p;

		} finally {
			try {
				in.close();
			} catch (IOException e) {
			}
		}
	}

	public L3p getResultL3p() {
		return resultL3p;
	}
}
