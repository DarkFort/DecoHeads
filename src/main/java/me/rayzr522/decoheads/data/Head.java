/**
 *
 */
package me.rayzr522.decoheads.data;

import me.rayzr522.decoheads.Category;
import me.rayzr522.decoheads.DecoHeads;
import me.rayzr522.decoheads.util.CustomHead;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author Rayzr
 */
public class Head {
    private String name;
    private Category category;

    private String texture;
    private String uuid;
    private double cost;

    private Head(String name, Category category, ConfigurationSection config) {
        if (!config.isString("texture")) {
            throw new IllegalArgumentException("config for head '" + name + "' is missing 'texture' key!");
        }

        this.name = name;
        this.category = category;

        this.texture = config.getString("texture");
        this.uuid = config.getString("uuid", UUID.randomUUID().toString());
        this.cost = config.getDouble("cost", -1);
    }

    public static Head load(String name, Category category, ConfigurationSection config) {
        try {
            return new Head(name, category, config);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Map<String, Object> serialize() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("texture", texture);
        map.put("uuid", uuid);
        map.put("cost", cost);
        return map;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getTexture() {
        return texture;
    }

    public String getUUID() {
        return uuid;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public double computeCost() {
        return cost != -1 ? cost : DecoHeads.getInstance().getSettings().getDefaultHeadCost();
    }

    public boolean hasCost() {
        return computeCost() > 0.0;
    }

    @Override
    public String toString() {
        return "Head{" +
                "name='" + name + '\'' +
                ", category=" + category +
                ", texture='" + texture + '\'' +
                ", uuid='" + uuid + '\'' +
                ", cost=" + cost +
                '}';
    }

    public ItemStack getItem() {
        return CustomHead.getHead(texture, uuid, name);
    }
}