package jp.dev7.enchant.doga.converter;

import java.util.List;

import net.arnx.jsonic.JSONHint;

import com.google.common.collect.Lists;

public class EnchantTexture {

	public String name = "default";
	public final List<Double> ambient = Lists.newArrayList();
	public final List<Double> diffuse = Lists.newArrayList();
	public final List<Double> emission = Lists.newArrayList();
	public double shininess;
	public final List<Double> specular = Lists.newArrayList();
	public String src;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@JSONHint(format = JsonHintFormat.N)
	public double getShininess() {
		return shininess;
	}

	public void setShininess(double shininess) {
		this.shininess = shininess;
	}

	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}

	@JSONHint(format = JsonHintFormat.N)
	public List<Double> getAmbient() {
		return ambient;
	}

	@JSONHint(format = JsonHintFormat.N)
	public List<Double> getDiffuse() {
		return diffuse;
	}

	@JSONHint(format = JsonHintFormat.N)
	public List<Double> getEmission() {
		return emission;
	}

	@JSONHint(format = JsonHintFormat.N)
	public List<Double> getSpecular() {
		return specular;
	}

	@Override
	public String toString() {
		return "EnchantTexture [name=" + name + "]";
	}

	public EnchantTexture clone() {
		final EnchantTexture result = new EnchantTexture();
		result.ambient.addAll(ambient);
		result.diffuse.addAll(diffuse);
		result.emission.addAll(emission);
		result.shininess = shininess;
		result.specular.addAll(specular);
		result.src = src;
		return result;
	}

}
