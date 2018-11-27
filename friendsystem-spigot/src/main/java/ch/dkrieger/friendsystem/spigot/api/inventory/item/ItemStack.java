package ch.dkrieger.friendsystem.spigot.api.inventory.item;

import ch.dkrieger.friendsystem.lib.FriendSystem;
import ch.dkrieger.friendsystem.lib.Messages;
import ch.dkrieger.friendsystem.lib.party.PartyMember;
import ch.dkrieger.friendsystem.lib.player.Friend;
import ch.dkrieger.friendsystem.lib.player.FriendPlayer;
import ch.dkrieger.friendsystem.lib.utils.GeneralUtil;
import ch.dkrieger.friendsystem.spigot.api.inventory.Listener;
import ch.dkrieger.friendsystem.spigot.util.SpigotUtil;
import com.google.gson.reflect.TypeToken;
import de.tr7zw.itemnbtapi.NBTItem;
import org.bukkit.Bukkit;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/*
 *
 *  * Copyright (c) 2018 Philipp Elvin Friedhoff on 20.11.18 20:21
 *
 */

public class ItemStack {

    private ItemStackType type;
    private String itemId, displayName, skullName;
    private int amount, durability, inventorySlot;
    private boolean glow;
    private Color color;
    private List<String> lore;
    private List<Listener> listeners;
    private Map<String, Object> properties;

    public ItemStack(ItemStackType type) {
        this(type, null);
    }

    public ItemStack(String itemId) {
        this(ItemStackType.NORMAL, itemId);
    }

    public ItemStack(ItemStackType type, String itemId) {
        this(type, itemId, 1, -1, null, null, false, null, new LinkedList<>());
    }

    public ItemStack(ItemStackType type, String itemId, int amount, int durability, String displayName, String skullName, boolean glow, Color color, List<String> lore) {
        this.type = type;
        this.itemId = itemId;
        this.amount = amount;
        this.durability = durability;
        this.displayName = displayName;
        this.skullName = skullName;
        this.glow = glow;
        this.color = color;
        this.lore = lore;
        this.listeners = new LinkedList<>();
        this.properties = new LinkedHashMap<>();
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

    public List<Listener> getListeners(Listener.DefaultEvent event) {
        List<Listener> listeners = new LinkedList<>();
        for(Listener listener : getListeners()) if(listener.getEvent().equalsIgnoreCase(event.getName())) listeners.add(listener);
        return listeners;
    }

    public Map<String, Object> getProperties() {
        return properties;
    }

    public Object getProperty(String property) {
        return getProperties().get(property);
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

    public ItemStack setInventorySlot(int inventorySlot) {
        this.inventorySlot = inventorySlot;
        return this;
    }

    public ItemStack addLore(String lore) {
        this.lore.add(lore);
        return this;
    }

    public ItemStack setProperties(Map<String, Object> properties) {
        this.properties = properties;
        return this;
    }

    public ItemStack setListeners(List<Listener> listeners) {
        this.listeners = listeners;
        return this;
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
        if(!this.lore.isEmpty()) {
            for(String lore : this.lore) {
                itemBuilder.addLore(lore);
            }
        }
        NBTItem nbtItem = new NBTItem(itemBuilder.build());
        nbtItem.setString("listeners", GeneralUtil.GSON_NOT_PRETTY.toJson(this.listeners, new TypeToken<List<Listener>>(){}.getType()));
        return nbtItem.getItem();
    }

    public org.bukkit.inventory.ItemStack toBukkitItemStack(FriendPlayer player) {
        if(player == null && this.type == ItemStackType.PLACEHOLDER)return new ItemBuilder(160).setDurability(15).build();
        if(player != null && this.type == ItemStackType.PLACEHOLDER)return new ItemBuilder(160).setDurability(player.getSettings().getDesign()).build();
        org.bukkit.inventory.ItemStack itemStack = toBukkitItemStack();
        NBTItem nbtItem = new NBTItem(itemStack);
        nbtItem.setString("friendPlayer", player.getUUID().toString());
        return nbtItem.getItem();
    }

    @SuppressWarnings("Only to build a friend skull or to add friend uuid as NBT data")
    public org.bukkit.inventory.ItemStack toBukkitItemStack(Friend friend) {
        org.bukkit.inventory.ItemStack itemStack;
        if(this.itemId.startsWith("397") && friend != null) {
            ItemBuilder itemBuilder;
            if (friend.isOnline()) {
                itemBuilder = new ItemBuilder(SpigotUtil.getGameProfile(Bukkit.getPlayer(friend.getUUID())),
                        (friend.isFavorite() ? Messages.PLAYER_LIST_SYMBOL_FAVORITE : Messages.PLAYER_LIST_SYMBOL_NORMAL) + friend.getFriendPlayer().getColoredName());
            } else {
                itemBuilder = new ItemBuilder(getItemId())
                        .setDisplayName((friend.isFavorite() ? Messages.PLAYER_LIST_SYMBOL_FAVORITE : Messages.PLAYER_LIST_SYMBOL_NORMAL) + friend.getFriendPlayer().getColoredName());
            }
            for (String lore : this.lore) {
                lore = lore.replace("[lastonline]", String.valueOf(friend.getTimeStamp()));
                if (friend.isOnline()) lore = lore.replace("[server]", friend.getOnlineFriendPlayer().getServer());
                itemBuilder.addLore(lore);
            }
            itemStack = itemBuilder.setDisplayName(this.displayName.replace("[friend]", friend.getFriendPlayer().getColoredName()).replace("[requester]", friend.getFriendPlayer().getColoredName())).build();
        }else itemStack = toBukkitItemStack();

        NBTItem nbtItem = new NBTItem(itemStack);
        if(friend != null)nbtItem.setString("friend", friend.getFriendPlayer().getName());
        nbtItem.setString("listeners", GeneralUtil.GSON_NOT_PRETTY.toJson(this.listeners, new TypeToken<List<Listener>>(){}.getType()));
        return nbtItem.getItem();
    }

    @SuppressWarnings("Only to build a party member skull")
    public org.bukkit.inventory.ItemStack toBukkitItemStack(PartyMember partyMember) {
        return new ItemBuilder(SpigotUtil.getGameProfile(Bukkit.getPlayer(partyMember.getUUID())), partyMember.getPlayer().getColoredName()).build();
    }

    public ItemStack addListener(Listener listener) {
        this.listeners.add(listener);
        return this;
    }

    public ItemStack addListener(Listener.DefaultEvent event, String adapter) {
        return addListener(new Listener(event, adapter));
    }

    @Override
    protected ItemStack clone() {
        return new ItemStack(this.type, this.itemId, this.amount, this.durability, this.displayName, this.skullName, this.glow, this.color, this.lore).setProperties(this.properties).setListeners(this.listeners);
    }
}