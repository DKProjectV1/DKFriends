package ch.dkrieger.friendsystem.spigot.api.inventory.inventory;

import ch.dkrieger.friendsystem.spigot.api.inventory.Listener;
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

public class MainInventory extends Inventory {

    private List<ConditionInventory> conditionInventories;
    private List<Listener> listeners;

    public MainInventory(String name, int size) {
        super(name, size);
        this.conditionInventories = new LinkedList<>();
        this.listeners = new LinkedList<>();
    }

    public List<ConditionInventory> getConditionInventories() {
        return conditionInventories;
    }

    public ConditionInventory getConditionInventory(String condition) {
        for(ConditionInventory conditionInventory : getConditionInventories())if(conditionInventory.getCondition().equals(condition))return conditionInventory;
        return null;
    }

    public List<Listener> getListeners() {
        return listeners;
    }

    public MainInventory setConditionInventories(List<ConditionInventory> conditionInventories) {
        this.conditionInventories = conditionInventories;
        return this;
    }

    public void addConditionInventory(ConditionInventory inventory) {
        this.conditionInventories.add(inventory);
    }

    public MainInventory setListeners(List<Listener> listeners) {
        this.listeners = listeners;
        return this;
    }

    public MainInventory addListener(Listener listener) {
        this.listeners.add(listener);
        return this;
    }

    public org.bukkit.inventory.Inventory toBukkitInventory() {
        org.bukkit.inventory.Inventory inventory = Bukkit.createInventory(null, getSize(), getName());
        for(Map.Entry<Integer, ItemStack> entry : getItems().entrySet()) {
            inventory.setItem(entry.getKey()-1, entry.getValue().toBukkitItemStack());
        }
        return inventory;
    }

    @Override
    public MainInventory clone() {
        return (MainInventory) new MainInventory(getName(), getSize()).setConditionInventories(this.conditionInventories).setListeners(this.listeners).setItems(getItems());
    }
}