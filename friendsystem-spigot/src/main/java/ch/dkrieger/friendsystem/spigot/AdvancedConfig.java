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

    private int skullFirstSlot, skullLastSlot, friendSwitchPageInventorySlot1, friendSwitchPageInventorySlot2;
    private Map<String, ItemStack> items;
    private Map<String, ConfigInventory> inventories;

    public AdvancedConfig() {
        this.skullFirstSlot = 1;
        this.skullLastSlot = 36;
        this.friendSwitchPageInventorySlot1 = 44;
        this.friendSwitchPageInventorySlot2 = 43;

        this.items = new LinkedHashMap<>();

        items.put("onlinePlayerSkull", new ItemStack("397:3").addLore("§aOnline auf [server]"));
        items.put("offlinePlayerSkull", new ItemStack("397:0").addLore("§cZuletzt online: [lastonline]"));
        items.put("friendRequests", new ItemStack("friendRequests","358:0").setDisplayName("§6Friend Requests").setInventorySlot(50));
        items.put("nextPage", new ItemStack("nextPage", "262:0").setDisplayName("§aNext Page").addListener(new Listener(Listener.DefaultEvent.CLICK, "nextFriendPage")));
        items.put("previousPage", new ItemStack("previousPage", "262:0").setDisplayName("§cPrevious Page").addListener(new Listener(Listener.DefaultEvent.CLICK, "previousFriendPage")));

        this.inventories = new LinkedHashMap<>();

        ConfigInventory friendInventory = new ConfigInventory("§eFriends", 54);

        friendInventory.setItem(46, new ItemStack("friends","314:0").setDisplayName("§eFriends").addListener(new Listener(Listener.DefaultEvent.CLICK, "openFriendPage")));
        friendInventory.setItem(47, new ItemStack("parties", "401:0").setDisplayName("§5Party").addListener(new Listener(Listener.DefaultEvent.CLICK, "openPartyPage")));
        friendInventory.setItem(48, new ItemStack("settings", "356:0").setDisplayName("§cSettings").addListener(new Listener(Listener.DefaultEvent.CLICK, "openSettingsPage")));

        /*ConditionInventory friendRequests = new ConditionInventory("friends", friendInventory, "friendRequests");
        friendRequests.setItem(50, new ItemStack("friendRequests","358:0").setDisplayName("§6Friend Requests"));
        friendInventory.addConditionInventory(friendRequests);

        ConditionInventory nextPage = new ConditionInventory("friends", friendInventory, "nextFriendPage");
        nextPage.setItem(44, new ItemStack("nextPage", "262:0").setDisplayName("§aNext Page").addListener(new Listener(Listener.DefaultEvent.CLICK, "nextFriendPage")));
        friendInventory.addConditionInventory(nextPage);

        ConditionInventory previousPage = new ConditionInventory("friends", friendInventory, "previousFriendPage");
        previousPage.setItem(43, new ItemStack("previousPage", "262:0").setDisplayName("§cPrevious Page").addListener(new Listener(Listener.DefaultEvent.CLICK, "previousFriendPage")));
        friendInventory.addConditionInventory(previousPage);*/

        ConfigInventory partyInventory = new ConfigInventory("§5Party", 54);


        ConfigInventory settingsInventory = new ConfigInventory("§cSettings", 54);

        inventories.put("friends", friendInventory);
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

    public Map<String, ConfigInventory> getInventories() {
        return inventories;
    }

    public Map<String, ItemStack> getItems() {
        return items;
    }

    public ItemStack getItem(String key) {
        return getItems().get(key);
    }
}