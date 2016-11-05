
package com.rayzr522.decoheads;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.rayzr522.decoheads.type.ClassType;
import com.rayzr522.decoheads.type.HandleType;
import com.rayzr522.decoheads.type.PacketType;

public class Reflector {

    private static String  version = "";

    public static Class<?> CRAFT_PLAYER;
    public static Class<?> CRAFT_ENTITY;
    public static Class<?> CRAFT_SERVER;
    public static Class<?> CRAFT_WORLD;
    public static Class<?> CRAFT_ITEM_STACK;

    private static Method  PLAYER_HANDLE;
    private static Method  ENTITY_HANDLE;
    private static Method  SERVER_HANDLE;
    private static Method  WORLD_HANDLE;

    static {

        try {

            version = Bukkit.getServer().getClass().getName().split("\\.")[3];

            CRAFT_PLAYER = getClass(ClassType.CRAFTBUKKIT, "entity.CraftPlayer");
            CRAFT_ENTITY = getClass(ClassType.CRAFTBUKKIT, "entity.CraftEntity");
            CRAFT_SERVER = getClass(ClassType.CRAFTBUKKIT, "CraftServer");
            CRAFT_WORLD = getClass(ClassType.CRAFTBUKKIT, "CraftWorld");
            CRAFT_ITEM_STACK = getClass(ClassType.CRAFTBUKKIT, "inventory.CraftItemStack");

            PLAYER_HANDLE = getMethod(CRAFT_PLAYER, "getHandle");
            ENTITY_HANDLE = getMethod(CRAFT_ENTITY, "getHandle");
            SERVER_HANDLE = getMethod(CRAFT_SERVER, "getHandle");
            WORLD_HANDLE = getMethod(CRAFT_WORLD, "getHandle");

        } catch (Exception e) {

            e.printStackTrace();
            DecoHeads.INSTANCE.err("Failed to load Reflector", true);

        }

    }

    public static String getVersion() {

        return version;

    }

    public static Class<?> getClass(ClassType type, String name) {

        try {
            return Class.forName(type.getPackage() + "." + version + "." + name);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }

    }

    public static Method getMethod(Class<?> clazz, String name) {

        for (Method m : clazz.getMethods()) {

            if (m.getName().equals(name)) {

                return m;

            }

        }

        return null;

    }

    public static Method getMethod(Class<?> clazz, String name, Class<?>... params) {

        for (Method m : clazz.getMethods()) {

            if (m.getName().equalsIgnoreCase("name")) {

                if (m.getParameterTypes() == params) {

                    return m;

                }

            }

        }

        return null;

    }

    public static Field getField(Class<?> clazz, String fieldName) throws Exception {

        return clazz.getDeclaredField(fieldName);

    }

    public static Object getFieldValue(Object object, String fieldName) throws Exception {

        Field f = getField(object.getClass(), fieldName);
        f.setAccessible(true);
        return f.get(object);

    }

    public static void setFieldValue(Object object, String fieldName, Object value) throws Exception {

        Field f = getField(object.getClass(), fieldName);
        f.setAccessible(true);
        f.set(object, value);

    }

    public static Constructor<?> getConstructor(Class<?> clazz, int numParams) {

        for (Constructor<?> constr : clazz.getConstructors()) {

            if (constr.getParameterTypes().length == numParams) {

                return constr;

            }

        }

        return null;

    }

    public static Object getHandle(HandleType type, Object object) {

        try {

            switch (type) {

                case ENTITY:
                    return ENTITY_HANDLE.invoke(object, new Object[] {});
                case PLAYER:
                    return PLAYER_HANDLE.invoke(object, new Object[] {});
                case SERVER:
                    return SERVER_HANDLE.invoke(object, new Object[] {});
                case WORLD:
                    return WORLD_HANDLE.invoke(object, new Object[] {});
                default:
                    return null;

            }

        } catch (Exception e) {

            return null;

        }

    }

    public static Object getNMSItem(ItemStack item) {

        try {

            return getFieldValue(item, "handle");

        } catch (Exception e) {

            return null;

        }

    }

    public static class Packets {

        public static Object getPlayerConnection(Player p) {

            try {

                return getFieldValue(p, "playerConnection");

            } catch (Exception e) {

                return null;

            }

        }

        public static Class<?> getPacket(PacketType type, String name) {

            return Reflector.getClass(ClassType.NMS, "Packet" + type.prefix + name);

        }

    }

}
