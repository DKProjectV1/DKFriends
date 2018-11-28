package ch.dkrieger.friendsystem.spigot.api.inventory.inventory;

/*
 *
 *  * Copyright (c) 2018 Philipp Elvin Friedhoff on 20.11.18 19:41
 *
 */

import ch.dkrieger.friendsystem.lib.party.PartyMember;
import ch.dkrieger.friendsystem.lib.player.Friend;
import ch.dkrieger.friendsystem.lib.player.FriendPlayer;
import ch.dkrieger.friendsystem.spigot.api.inventory.Listener;
import ch.dkrieger.friendsystem.spigot.api.inventory.item.ItemStack;
import net.minecraft.server.v1_8_R3.Item;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ConfigInventory {

    private String title;
    private int size;
    private Map<Integer, ItemStack> items;
    private List<Listener> listeners;
    private Map<String, Map<Integer, ItemStack>> conditionItems;

    public ConfigInventory(String title, int size) {
        this.title = title;
        this.size = size;
        this.items = new LinkedHashMap<>();
        this.listeners = new LinkedList<>();
        this.conditionItems = new LinkedHashMap<>();
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
        for(int i = 0; i < this.size; i++) itemStacks[i] = getItem(i).toBukkitItemStack();
        return itemStacks;
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

    public List<Listener> getListeners() {
        return listeners;
    }

    public List<Listener> getListeners(Listener.DefaultEvent event) {
        List<Listener> listeners = new LinkedList<>();
        for(Listener listener : getListeners()) if(listener.getEvent().equalsIgnoreCase(event.getName())) listeners.add(listener);
        return listeners;
    }

    public Map<Integer, ItemStack> getConditionItems(String condition) {
        return this.conditionItems.get(condition);
    }

    public ConfigInventory setListeners(List<Listener> listeners) {
        this.listeners = listeners;
        return this;
    }

    public ConfigInventory addListener(Listener listener) {
        this.listeners.add(listener);
        return this;
    }

    public ConfigInventory setItems(Map<Integer, ItemStack> items) {
        this.items = items;
        return this;
    }

    public ConfigInventory setConditionItems(Map<String, Map<Integer, ItemStack>> conditionItems) {
        this.conditionItems = conditionItems;
        return this;
    }

    public void setItem(int slot, ItemStack itemStack) {
        this.items.put(slot, itemStack);
    }

    public void setItem(String condition, int slot, ItemStack itemStack) {
        if(!this.conditionItems.containsKey(condition)) {
            System.out.println("setItem not contains: " + condition);
            this.conditionItems.put(condition, new LinkedHashMap<>());
        }
        this.conditionItems.get(condition).put(slot, itemStack);
        System.out.println("setItem: " + this.conditionItems);
    }

    public void addItem(ItemStack itemStack) {
        for(int i = 0; i <= this.size; i++) {
            if(!this.items.containsKey(i)) {
                this.items.put(i, itemStack);
                return;
            }
        }
    }

    public void addItem(String condition, ItemStack itemStack) {
        if(!this.conditionItems.containsKey(condition))conditionItems.put(condition, new LinkedHashMap<>());
        for(int i = 0; i <= this.size; i++) {
            if(!this.conditionItems.get(condition).containsKey(i)) {
                this.conditionItems.get(condition).put(i, itemStack);
                return;
            }
        }
    }

    public ConfigInventory setTitle(String title) {
        this.title = title;
        return this;
    }

    public Inventory toBukkitInventory(InventoryHolder inventoryHolder, Friend friend) {
        return toBukkitInventory(inventoryHolder, friend, null);
    }

    public Inventory toBukkitInventory(InventoryHolder inventoryHolder, Friend friend, String... conditions) {
        Inventory inventory = Bukkit.createInventory(inventoryHolder, this.size, this.title);
        for(Map.Entry<Integer, ItemStack> entry : this.items.entrySet()) {
            inventory.setItem(entry.getKey(), entry.getValue().toBukkitItemStack(friend));
        }
        for(String condition : conditions) {
            if(condition != null) {
                if(!this.conditionItems.containsKey(condition)) {
                    conditionItems.put(condition, new LinkedHashMap<>());
                }
                for(Map.Entry<Integer, ItemStack> entry : this.conditionItems.get(condition).entrySet()) {
                    inventory.setItem(entry.getKey(), entry.getValue().toBukkitItemStack(friend));
                }
            }
        }
        return inventory;
    }

    public Inventory toBukkitInventory(InventoryHolder inventoryHolder, PartyMember partyMember, String... conditions) {
        Inventory inventory = Bukkit.createInventory(inventoryHolder, this.size, this.title);
        for(Map.Entry<Integer, ItemStack> entry : this.items.entrySet()) {
            inventory.setItem(entry.getKey(), entry.getValue().toBukkitItemStack(partyMember));
        }
        for(String condition : conditions) {
            if(condition != null) {
                if(!this.conditionItems.containsKey(condition)) {
                    conditionItems.put(condition, new LinkedHashMap<>());
                }
                for(Map.Entry<Integer, ItemStack> entry : this.conditionItems.get(condition).entrySet()) {
                    inventory.setItem(entry.getKey(), entry.getValue().toBukkitItemStack(partyMember));
                }
            }
        }
        return inventory;
    }

    public org.bukkit.inventory.Inventory toBukkitInventory(InventoryHolder inventoryHolder, FriendPlayer player) {
        org.bukkit.inventory.Inventory inventory = Bukkit.createInventory(inventoryHolder, this.size, this.title);
        for(Map.Entry<Integer, ItemStack> entry : this.items.entrySet()) {
            inventory.setItem(entry.getKey(), entry.getValue().toBukkitItemStack(player));
        }
        return inventory;
    }

    public org.bukkit.inventory.Inventory toBukkitInventory(InventoryHolder inventoryHolder) {
        org.bukkit.inventory.Inventory inventory = Bukkit.createInventory(inventoryHolder, this.size, this.title);
        for(Map.Entry<Integer, ItemStack> entry : this.items.entrySet()) {
            inventory.setItem(entry.getKey(), entry.getValue().toBukkitItemStack());
        }
        return inventory;
    }

    public org.bukkit.inventory.Inventory toBukkitInventory() {
        return toBukkitInventory(null);
    }

    @Override
    public ConfigInventory clone() {
        return new ConfigInventory(this.title, this.size).setItems(this.items).setListeners(this.listeners).setConditionItems(this.conditionItems);
    }
}