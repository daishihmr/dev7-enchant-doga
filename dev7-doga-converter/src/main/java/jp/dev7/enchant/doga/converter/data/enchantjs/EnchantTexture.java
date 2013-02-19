package jp.dev7.enchant.doga.converter.data.enchantjs;

import java.io.File;
import java.util.List;

import jp.dev7.enchant.doga.converter.JsonHintFormat;
import net.arnx.jsonic.JSONHint;

import com.google.common.collect.Lists;

public class EnchantTexture {

    public String name = "default";
    public final List<Double> ambient = Lists.newArrayList();
    public final List<Double> diffuse = Lists.newArrayList();
    public final List<Double> emission = Lists.newArrayList();
    public double shininess;
    public final List<Double> specular = Lists.newArrayList();
    /** テクスチャ画像の名前 */
    public String src;
    /** テクスチャ画像ファイル */
    @JSONHint(ignore = true)
    public File srcFile;

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
        return "EnchantTexture [name=" + name + ", src=" + src + "]";
    }

    public EnchantTexture clone() {
        final EnchantTexture result = new EnchantTexture();
        result.ambient.addAll(ambient);
        result.diffuse.addAll(diffuse);
        result.emission.addAll(emission);
        result.shininess = shininess;
        result.specular.addAll(specular);
        result.src = src;
        result.srcFile = srcFile;
        return result;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((ambient == null) ? 0 : ambient.hashCode());
        result = prime * result + ((diffuse == null) ? 0 : diffuse.hashCode());
        result = prime * result
                + ((emission == null) ? 0 : emission.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        long temp;
        temp = Double.doubleToLongBits(shininess);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        result = prime * result
                + ((specular == null) ? 0 : specular.hashCode());
        result = prime * result + ((src == null) ? 0 : src.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        EnchantTexture other = (EnchantTexture) obj;
        if (ambient == null) {
            if (other.ambient != null)
                return false;
        } else if (!ambient.equals(other.ambient))
            return false;
        if (diffuse == null) {
            if (other.diffuse != null)
                return false;
        } else if (!diffuse.equals(other.diffuse))
            return false;
        if (emission == null) {
            if (other.emission != null)
                return false;
        } else if (!emission.equals(other.emission))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (Double.doubleToLongBits(shininess) != Double
                .doubleToLongBits(other.shininess))
            return false;
        if (specular == null) {
            if (other.specular != null)
                return false;
        } else if (!specular.equals(other.specular))
            return false;
        if (src == null) {
            if (other.src != null)
                return false;
        } else if (!src.equals(other.src))
            return false;
        return true;
    }

}
