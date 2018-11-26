package ch.dkrieger.friendsystem.spigot;

/*
 *
 *  * Copyright (c) 2018 Philipp Elvin Friedhoff on 23.11.18 14:24
 *
 */

import ch.dkrieger.friendsystem.spigot.api.inventory.Listener;
import ch.dkrieger.friendsystem.spigot.api.inventory.inventory.ConfigInventory;
import ch.dkrieger.friendsystem.spigot.api.inventory.item.ItemStack;
import java.util.LinkedHashMap;
import java.util.Map;

public class AdvancedConfig {

    private int skullFirstSlot, skullLastSlot, friendSwitchPageInventorySlot1, friendSwitchPageInventorySlot2, partySwitchPageInventorySlot1, partySwitchPageInventorySlot2, friendRequestsInventorySlot;
    private Map<String, ItemStack> items;
    private Map<String, ConfigInventory> inventories;
    private Map<Integer, ItemStack> defaultInventoryItems;

    public AdvancedConfig() {
        this.skullFirstSlot = 1;
        this.skullLastSlot = 36;
        this.friendSwitchPageInventorySlot1 = 44;
        this.friendSwitchPageInventorySlot2 = 43;
        this.partySwitchPageInventorySlot1 = 44;
        this.partySwitchPageInventorySlot2 = 43;
        this.friendRequestsInventorySlot = 50;
        this.items = new LinkedHashMap<>();

        items.put("onlinePlayerSkull", new ItemStack("397:3").addLore("§aOnline auf [server]").addListener(Listener.DefaultEvent.CLICK, "openFriendOptionsPage"));
        items.put("offlinePlayerSkull", new ItemStack("397:0").addLore("§cZuletzt online: [lastonline]").addListener(Listener.DefaultEvent.CLICK, "openFriendOptionsPage"));
        items.put("friendRequests", new ItemStack("358:0").setDisplayName("§6Friend Requests"));
        items.put("nextFriendPage", new ItemStack("262:0").setDisplayName("§aNext Page").addListener(new Listener(Listener.DefaultEvent.CLICK, "nextFriendPage")));
        items.put("previousFriendPage", new ItemStack("262:0").setDisplayName("§cPrevious Page").addListener(new Listener(Listener.DefaultEvent.CLICK, "previousFriendPage")));
        items.put("partyPlayerSkull", new ItemStack("393:3"));
        items.put("nextPartyPage", new ItemStack("262:0").setDisplayName("§aNext Page").addListener(new Listener(Listener.DefaultEvent.CLICK, "nextPartyPage")));
        items.put("previousPartyPage", new ItemStack("262:0").setDisplayName("§cPrevious Page").addListener(new Listener(Listener.DefaultEvent.CLICK, "previousPartyPage")));


        this.defaultInventoryItems = new LinkedHashMap<>();

        defaultInventoryItems.put(45, new ItemStack("314:0").setDisplayName("§eFriends").addListener(new Listener(Listener.DefaultEvent.CLICK, "openFriendPage")));
        defaultInventoryItems.put(46, new ItemStack("401:0").setDisplayName("§5Party").addListener(new Listener(Listener.DefaultEvent.CLICK, "openPartyPage")));
        defaultInventoryItems.put(47, new ItemStack("356:0").setDisplayName("§cSettings").addListener(new Listener(Listener.DefaultEvent.CLICK, "openSettingsPage")));


        this.inventories = new LinkedHashMap<>();


        ConfigInventory friendInventory = new ConfigInventory("§eFriends", 54);

        ConfigInventory partyInventory = new ConfigInventory("§5Party", 54);

        ConfigInventory settingsInventory = new ConfigInventory("§cSettings", 54);

        ConfigInventory friendOptions = new ConfigInventory("[friend]", 27);
        friendOptions.setItem("friendOnline", 12, new ItemStack("354:0").setDisplayName("").addListener(Listener.DefaultEvent.CLICK, "invitePlayerToParty"));
        friendOptions.setItem("friendOnline", 14, new ItemStack("368:0").addListener(Listener.DefaultEvent.CLICK, "jumpToPlayer"));
        friendOptions.setItem(15, new ItemStack("166:0").addListener(Listener.DefaultEvent.CLICK, "removeFriend"));

        friendOptions.setItem("friendOnline", 11, new ItemStack("397:3").addLore("§aOnline auf [server]"));
        friendOptions.setItem("friendOffline", 11, new ItemStack("397:0"));


        inventories.put("friends", friendInventory);
        inventories.put("friendOptions", friendOptions);
        inventories.put("parties", partyInventory);
        inventories.put("settings", settingsInventory);
    }

    public int getSkullFirstSlot() {
        return skullFirstSlot-1;
    }

    public int getSkullLastSlot() {
        return skullLastSlot-1;
    }

    public int getFriendSwitchPageInventorySlot1() {
        return friendSwitchPageInventorySlot1;
    }

    public int getFriendSwitchPageInventorySlot2() {
        return friendSwitchPageInventorySlot2;
    }

    public int getPartySwitchPageInventorySlot1() {
        return partySwitchPageInventorySlot1;
    }

    public int getPartySwitchPageInventorySlot2() {
        return partySwitchPageInventorySlot2;
    }

    public Map<String, ConfigInventory> getInventories() {
        return inventories;
    }

    public Map<String, ItemStack> getItems() {
        return items;
    }

    public Map<Integer, ItemStack> getDefaultInventoryItems() {
        return defaultInventoryItems;
    }

    public ItemStack getItem(String key) {
        return (ItemStack) getItems().get(key);
    }
}