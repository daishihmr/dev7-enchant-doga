package jp.dev7.enchant.doga.old.v4.converter;

import java.util.List;

import net.arnx.jsonic.JSONHint;

import com.google.common.collect.Lists;

public class EnchantMesh implements Cloneable {
	private String name;
	public final List<Double> vertices = Lists.newArrayList();
	public final List<Double> normals = Lists.newArrayList();
	public final List<Double> texCoords = Lists.newArrayList();
	// public final List<Double> colors = Lists.newArrayList();
	public final List<Integer> indices = Lists.newArrayList();
	public EnchantTexture texture = new EnchantTexture();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@JSONHint(format = JsonHintFormat.N)
	public List<Double> getVertices() {
		return vertices;
	}

	@JSONHint(format = JsonHintFormat.N)
	public List<Double> getNormals() {
		return normals;
	}

	@JSONHint(format = JsonHintFormat.N)
	public List<Double> getTexCoords() {
		return texCoords;
	}

	// public List<Double> getColors() {
	// return colors;
	// }

	public List<Integer> getIndices() {
		return indices;
	}

	public EnchantTexture getTexture() {
		return texture;
	}

	public void setTexture(EnchantTexture texture) {
		this.texture = texture;
	}

	@Override
	public String toString() {
		return "EnchantMesh [texture=" + texture + ", vertexSize="
				+ indices.size() + "]";
	}

	public EnchantMesh clone() {
		final EnchantMesh result = new EnchantMesh();
		result.setName(name);
		result.vertices.addAll(vertices);
		result.normals.addAll(normals);
		result.texCoords.addAll(texCoords);
		result.indices.addAll(indices);
		result.texture = texture.clone();
		return result;
	}
}
