/**
 * 
 */
package com.rayzr522.decoheads;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import com.rayzr522.decoheads.util.CustomHead;

/**
 * @author Rayzr
 *
 */
public class Head {

    private String   name;
    private String   uuid;
    private String   texture;
    private Category category;

    public Head(String name, String uuid, String texture, Category category) {
        this.name = name;
        this.uuid = uuid;
        this.texture = texture;
        this.category = category;
    }

    /**
     * @param name the name of the head
     * @param data the head data
     */
    public static Head deserialize(String name, Category category, ConfigurationSection data) {
        if (!data.isString("uuid") || !data.isString("texture")) {
            return null;
        }
        return new Head(name, data.getString("uuid"), data.getString("texture"), category);
    }

    public Map<String, Object> serialize() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("uuid", uuid);
        map.put("texture", texture);
        return map;
    }

    /**
     * @return the uuid
     */
    public String getUuid() {
        return uuid;
    }

    /**
     * @return the texture
     */
    public String getTexture() {
        return texture;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    void setName(String name) {
        this.name = name;
    }

    /**
     * @return The category of this head
     */
    public Category getCategory() {
        return category;
    }

    public ItemStack getItem() {
        return CustomHead.getHead(texture, uuid, name);
    }

    @Override
    public String toString() {
        return "Head [name=" + name + ", uuid=" + uuid + ", texture=" + texture + "]";
    }

}
