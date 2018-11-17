package ch.dkrieger.friendsystem.spigot.api.inventory.item;

/*
 *
 *  * Copyright (c) 2018 Philipp Elvin Friedhoff on 17.11.18 13:13
 *
 */

import org.bukkit.inventory.ItemStack;

import java.util.LinkedHashMap;
import java.util.Map;

public class ItemStorage {

    private static Map<String, ItemStack> itemStorage;

    static {
        itemStorage = new LinkedHashMap<>();
    }

    public static boolean contains(String key) {
        return itemStorage.containsKey(key);
    }

    public static ItemStack get(String key) {
        return itemStorage.get(key);
    }

    public static ItemStack put(String key, ItemStack itemStack) {
        return itemStorage.put(key, itemStack);
    }

    public static ItemStack put(String key, ItemBuilder itemBuilder) {
        return put(key, itemBuilder.build());
    }
}