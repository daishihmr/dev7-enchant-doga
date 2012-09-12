package jp.dev7.enchant.doga.parser.suf.data;

import java.util.List;
import java.util.Map;

import jp.dev7.enchant.doga.parser.atr.data.Atr;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class Suf {

	private final List<Obj> objects = Lists.newArrayList();
	private final Map<String, Atr> atrMap = Maps.newHashMap();

	public List<Obj> getObjects() {
		return objects;
	}

	public Map<String, Atr> getAtrMap() {
		return atrMap;
	}
}
