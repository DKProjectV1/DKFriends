package ch.dkrieger.friendsystem.spigot.api.inventory.inventory;

import ch.dkrieger.friendsystem.spigot.api.inventory.itemstack.ItemStack;
import org.bukkit.Bukkit;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/*
 *
 *  * Copyright (c) 2018 Philipp Elvin Friedhoff on 18.11.18 21:59
 *
 */

public class Inventory {

    private String name;
    private int size;
    private Map<Integer, ItemStack> items;
    private Map<String, ConditionInventory> conditionInventories;

    public Inventory(String name, int size) {
        this.name = name;
        this.size = size;
        this.items = new LinkedHashMap<>();
        this.conditionInventories = new LinkedHashMap<>();
    }

    public String getName() {
        return name;
    }

    public int getSize() {
        return size;
    }

    public ItemStack getItem(int slot) {
        return items.get(slot);
    }

    public ItemStack getItem(org.bukkit.inventory.ItemStack item) {
        for(ItemStack itemStack : this.items.values()) {
            if(itemStack.toBukkitItemStack().equals(item)) {
                return itemStack;
            }
        }
        return null;
    }

    public Map<Integer, ItemStack> getItems() {
        return items;
    }

    public org.bukkit.inventory.ItemStack[] getContentAsArray() {
        org.bukkit.inventory.ItemStack[] itemStacks = new org.bukkit.inventory.ItemStack[this.items.size()-1];
        for(int i = 0; i < size; i++) itemStacks[i] = getItem(i).toBukkitItemStack();
        return itemStacks;
    }

    public Map<String, ConditionInventory> getConditionInventories() {
        return conditionInventories;
    }

    public ConditionInventory getConditionInventory(String condition) {
        return getConditionInventories().get(condition);
    }

    public Inventory setConditionInventories(Map<String, ConditionInventory> conditionInventories) {
        this.conditionInventories = conditionInventories;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void addConditionInventory(ConditionInventory inventory) {
        this.conditionInventories.put(inventory.getCondition(), inventory);
    }

    public Inventory setItems(Map<Integer, ItemStack> items) {
        this.items = items;
        return this;
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
            inventory.setItem(entry.getKey()-1, entry.getValue().toBukkitItemStack());
        }
        return inventory;
    }

    @Override
    public Inventory clone() {
        return new Inventory(this.name, this.size).setItems(this.items).setConditionInventories(this.conditionInventories);
    }
}