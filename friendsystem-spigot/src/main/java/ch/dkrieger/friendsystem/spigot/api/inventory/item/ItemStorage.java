package ch.dkrieger.friendsystem.spigot.api.inventory.item;

/*
 *
 *  * Copyright (c) 2018 Philipp Elvin Friedhoff on 17.11.18 13:13
 *
 */

import java.util.LinkedHashMap;
import java.util.Map;

public class ItemStorage {

    private Map<String, org.bukkit.inventory.ItemStack> itemStorage;
    private Map<String, ItemStack> configItemStackStorage;

    public ItemStorage(Map<String, ItemStack> configItemStackStorage) {
        this.itemStorage = new LinkedHashMap<>();
        this.configItemStackStorage = configItemStackStorage;
    }


    public boolean containsConfigItemStack(String key) {
        return configItemStackStorage.containsKey(key);
    }

    public boolean containsItemStack(String key) {
        return itemStorage.containsKey(key);
    }

    public ItemStack get(String key) {
        return configItemStackStorage.get(key);
    }

    public ItemStack put(String key, ItemStack itemStack) {
        return configItemStackStorage.put(key, itemStack);
    }
}