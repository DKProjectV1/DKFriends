package ch.dkrieger.friendsystem.spigot.api.inventory.inventory;

import ch.dkrieger.friendsystem.spigot.api.inventory.Listener;
import ch.dkrieger.friendsystem.spigot.api.inventory.item.ItemStack;
import java.util.LinkedList;
import java.util.List;

/*
 *
 *  * Copyright (c) 2018 Philipp Elvin Friedhoff on 18.11.18 21:59
 *
 */

public class MainInventory extends Inventory {

    private List<ConditionInventory> conditionInventories;
    private List<Listener> listeners;

    public MainInventory(String title, int size) {
        super(title, size);
        this.conditionInventories = new LinkedList<>();
        this.listeners = new LinkedList<>();
    }

    public List<ConditionInventory> getConditionInventories() {
        return conditionInventories;
    }

    public ConditionInventory getConditionInventory(String condition) {
        for(ConditionInventory conditionInventory : getConditionInventories()) {
            if(conditionInventory.getCondition().equalsIgnoreCase(condition)) return conditionInventory;
        }
        return null;
    }

    public ItemStack getItem(String defaultKey, boolean withConditionInventories) {
        for(ItemStack itemStack : getItems().values())
            if(itemStack.getKey() != null && itemStack.getKey().equalsIgnoreCase(defaultKey))
                return itemStack;
        if(withConditionInventories)
            for(ConditionInventory conditionInventory : getConditionInventories())
                for(ItemStack itemStack : conditionInventory.getItems().values())
                    if(itemStack.getKey() != null && itemStack.getKey().equalsIgnoreCase(defaultKey)) return itemStack;
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

    public void setContent(ConditionInventory inventory) {

    }

    @Override
    public MainInventory clone() {
        return (MainInventory) new MainInventory(getTitle(), getSize()).setConditionInventories(this.conditionInventories).setListeners(this.listeners).setItems(getItems());
    }
}