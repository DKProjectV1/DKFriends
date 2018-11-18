package ch.dkrieger.friendsystem.spigot.inventory;

/*
 *
 *  * Copyright (c) 2018 Philipp Elvin Friedhoff on 18.11.18 19:22
 *
 */

import ch.dkrieger.friendsystem.spigot.api.inventory.item.ItemBuilder;
import java.util.LinkedList;
import java.util.List;

public class ItemStack {

    private String itemId, displayName, skullName;
    private int amount, durability;
    private boolean glow;
    private Color color;
    private List<String> lore;

    public ItemStack(String itemId) {
        this(itemId, 1, -1, null, null, false, null, new LinkedList<>());
    }

    public ItemStack(String itemId, int amount, int durability, String displayName, String skullName, boolean glow, Color color, List<String> lore) {
        this.itemId = itemId;
        this.amount = amount;
        this.durability = durability;
        this.displayName = displayName;
        this.skullName = skullName;
        this.glow = glow;
        this.color = color;
        this.lore = lore;
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

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setDurability(int durability) {
        this.durability = durability;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
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
        ItemBuilder itemBuilder = new ItemBuilder(this.itemId);
        if(this.displayName != null)itemBuilder.setDisplayName(this.displayName);
        if(this.skullName != null)itemBuilder.setSkullOwner(this.skullName);
        itemBuilder.setAmount(this.amount);
        if(this.durability != -1)itemBuilder.setDurability(this.durability);
        itemBuilder.setGlowing(this.glow);
        if(this.color != null)itemBuilder.setLeatherColor(org.bukkit.Color.fromBGR(this.color.getBlue(), this.color.getGreen(), this.color.getRed()));
        if(!this.lore.isEmpty())itemBuilder.setLore(this.lore);
        return itemBuilder.build();
    }

    @Override
    protected ItemStack clone() {
        return new ItemStack(this.itemId, this.amount, this.durability, this.displayName, this.skullName, this.glow, this.color, this.lore);
    }
}