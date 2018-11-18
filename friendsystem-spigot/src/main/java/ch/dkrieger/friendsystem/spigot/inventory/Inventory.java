package ch.dkrieger.friendsystem.spigot.inventory;

/*
 *
 *  * Copyright (c) 2018 Philipp Elvin Friedhoff on 18.11.18 19:22
 *
 */

import org.bukkit.Bukkit;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Inventory {

    private String name;
    private int size;
    private Map<Integer, ItemStack> items;
    private List<ConditionInventory> conditionInventories;

    public Inventory(String name, int size) {
        this.name = name;
        this.size = size;
        this.items = new LinkedHashMap<>();
        this.conditionInventories = new LinkedList<>();
    }

    public String getName() {
        return name;
    }

    public int getSize() {
        return size;
    }

    public Map<Integer, ItemStack> getItems() {
        return items;
    }

    public List<ConditionInventory> getConditionInventories() {
        return conditionInventories;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void addConditionInventory(ConditionInventory inventory) {
        this.conditionInventories.add(inventory);
    }

    public void setItem(int slot, ItemStack itemStack) {
        this.items.put(slot, itemStack);
    }

    public void addItem(ItemStack itemStack) {
        for(int i = 1; i <= this.size; i++) {
            if(!this.items.containsKey(i)) {
                this.items.put(i, itemStack);
                return;
            }
        }
    }

    public org.bukkit.inventory.Inventory toBukkitInventory() {
        org.bukkit.inventory.Inventory inventory = Bukkit.createInventory(null, this.size, this.name);
        for(Map.Entry<Integer, ItemStack> entry : this.items.entrySet()) {
            inventory.setItem(entry.getKey(), entry.getValue().toBukkitItemStack());
        }
        return inventory;
    }
}