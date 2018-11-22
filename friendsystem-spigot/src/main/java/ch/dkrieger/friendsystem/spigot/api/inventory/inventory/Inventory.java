package ch.dkrieger.friendsystem.spigot.api.inventory.inventory;

/*
 *
 *  * Copyright (c) 2018 Philipp Elvin Friedhoff on 20.11.18 19:41
 *
 */

import ch.dkrieger.friendsystem.lib.player.FriendPlayer;
import ch.dkrieger.friendsystem.spigot.api.inventory.item.ItemStack;
import org.bukkit.Bukkit;
import org.bukkit.inventory.InventoryHolder;
import java.util.LinkedHashMap;
import java.util.Map;

public class Inventory {

    private String title;
    private int size;
    private Map<Integer, ItemStack> items;

    public Inventory(String title, int size) {
        this.title = title;
        this.size = size;
        this.items = new LinkedHashMap<>();
    }

    public String getTitle() {
        return title;
    }

    public int getSize() {
        return size;
    }

    public Map<Integer, ItemStack> getItems() {
        return items;
    }

    public org.bukkit.inventory.ItemStack[] getContent() {
        org.bukkit.inventory.ItemStack[] itemStacks = new org.bukkit.inventory.ItemStack[getSize()];
        for(int i = 1; i < this.size; i++) itemStacks[i-1] = getItem(i).toBukkitItemStack();
        return itemStacks;
    }

    public ItemStack getItem(String defaultKey) {
        for(ItemStack itemStack : this.items.values()) {
            if(itemStack.getKey() != null && itemStack.getKey().equalsIgnoreCase(defaultKey)) return itemStack;
        }
        return null;
    }



    public ItemStack getItem(int slot) {
        return items.get(slot);
    }

    public ItemStack getItem(org.bukkit.inventory.ItemStack item) {
        for(ItemStack itemStack : this.items.values()) {
            if(itemStack.getDisplayName() != null && item.getItemMeta().hasDisplayName() && item.getItemMeta().getDisplayName().equalsIgnoreCase(itemStack.getDisplayName()))return itemStack;
            if(itemStack.toBukkitItemStack().equals(item)) {
                return itemStack;
            }
        }
        return null;
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

    public org.bukkit.inventory.Inventory toBukkitInventory(InventoryHolder inventoryHolder, FriendPlayer player) {
        org.bukkit.inventory.Inventory inventory = Bukkit.createInventory(inventoryHolder, this.size, this.title);
        for(Map.Entry<Integer, ItemStack> entry : this.items.entrySet()) {
            inventory.setItem(entry.getKey()-1, entry.getValue().toBukkitItemStack(player));
        }
        return inventory;
    }

    public org.bukkit.inventory.Inventory toBukkitInventory(InventoryHolder inventoryHolder) {
        return toBukkitInventory(inventoryHolder);
    }

    public org.bukkit.inventory.Inventory toBukkitInventory() {
        return toBukkitInventory(null, null);
    }

    @Override
    public Inventory clone() {
        return new Inventory(this.title, this.size).setItems(this.items);
    }

}