package ch.dkrieger.friendsystem.spigot.api.inventory.item;

import ch.dkrieger.friendsystem.lib.player.FriendPlayer;
import ch.dkrieger.friendsystem.spigot.api.inventory.Listener;
import ch.dkrieger.friendsystem.spigot.api.inventory.Color;
import de.tr7zw.itemnbtapi.NBTItem;

import java.util.LinkedList;
import java.util.List;

/*
 *
 *  * Copyright (c) 2018 Philipp Elvin Friedhoff on 20.11.18 20:21
 *
 */

public class ItemStack {

    private ItemStackType type;
    private String key, itemId, displayName, skullName;
    private int amount, durability;
    private boolean glow;
    private Color color;
    private List<String> lore;
    private List<Listener> listeners;

    public ItemStack(ItemStackType type) {
        this(type, null);
    }

    public ItemStack(String key, String itemId) {
        this(key, ItemStackType.NORMAL, itemId);
    }

    public ItemStack(String key, ItemStackType type, String itemId) {
        this(type, key, itemId, 1, -1, null, null, false, null, new LinkedList<>());
    }

    public ItemStack(String itemId) {
        this(ItemStackType.NORMAL, itemId);
    }

    public ItemStack(ItemStackType type, String itemId) {
        this(null, type, itemId);
    }

    public ItemStack(ItemStackType type, String key, String itemId, int amount, int durability, String displayName, String skullName, boolean glow, Color color, List<String> lore) {
        this.type = type;
        this.key = key;
        this.itemId = itemId;
        this.amount = amount;
        this.durability = durability;
        this.displayName = displayName;
        this.skullName = skullName;
        this.glow = glow;
        this.color = color;
        this.lore = lore;
        this.listeners = new LinkedList<>();
    }

    public String getKey() {
        return key;
    }

    public String getItemId() {
        return itemId;
    }

    public int getAmount() {
        return amount;
    }

    public int getDurability() {
        return durability;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getSkullName() {
        return skullName;
    }

    public boolean isGlow() {
        return glow;
    }

    public Color getColor() {
        return color;
    }

    public List<String> getLore() {
        return lore;
    }

    public List<Listener> getListeners() {
        return listeners;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setDurability(int durability) {
        this.durability = durability;
    }

    public ItemStack setDisplayName(String displayName) {
        this.displayName = displayName;
        return this;
    }

    public void setSkullName(String skullName) {
        this.skullName = skullName;
    }

    public void setGlow(boolean glow) {
        this.glow = glow;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setLore(List<String> lore) {
        this.lore = lore;
    }

    public org.bukkit.inventory.ItemStack toBukkitItemStack() {
        if(this.type == ItemStackType.PLACEHOLDER)return new ItemBuilder("160:15").build(); //Default placeholder
        if(this.type == ItemStackType.EMPTY)return new ItemBuilder("0").build();
        ItemBuilder itemBuilder = new ItemBuilder(this.itemId);
        if(this.displayName != null)itemBuilder.setDisplayName(this.displayName);
        if(this.skullName != null)itemBuilder.setSkullOwner(this.skullName);
        itemBuilder.setAmount(this.amount);
        if(this.durability != -1)itemBuilder.setDurability(this.durability);
        itemBuilder.setGlowing(this.glow);
        if(this.color != null)itemBuilder.setLeatherColor(org.bukkit.Color.fromBGR(this.color.getBlue(), this.color.getGreen(), this.color.getRed()));
        if(!this.lore.isEmpty())itemBuilder.setLore(this.lore);

        if(this.key == null)return itemBuilder.build();
        NBTItem nbtItem = new NBTItem(itemBuilder.build());
        nbtItem.setString("defaultitem", this.key);
        return nbtItem.getItem();
    }

    public org.bukkit.inventory.ItemStack toBukkitItemStack(FriendPlayer player) {
        if(player == null && this.type == ItemStackType.PLACEHOLDER)return new ItemBuilder(160).setDurability(15).build();
        if(player != null && this.type == ItemStackType.PLACEHOLDER)return new ItemBuilder(160).setDurability(player.getSettings().getDesign()).build();
        return toBukkitItemStack();
    }

    public ItemStack addListener(Listener listener) {
        this.listeners.add(listener);
        return this;
    }

    @Override
    protected ItemStack clone() {
        return new ItemStack(this.type, this.key, this.itemId, this.amount, this.durability, this.displayName, this.skullName, this.glow, this.color, this.lore);
    }
}