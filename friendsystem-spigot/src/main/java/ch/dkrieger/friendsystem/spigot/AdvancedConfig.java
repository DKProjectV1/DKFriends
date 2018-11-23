package ch.dkrieger.friendsystem.spigot;

/*
 *
 *  * Copyright (c) 2018 Philipp Elvin Friedhoff on 23.11.18 14:24
 *
 */

import ch.dkrieger.friendsystem.spigot.api.inventory.inventory.ConditionInventory;
import ch.dkrieger.friendsystem.spigot.api.inventory.inventory.MainInventory;
import ch.dkrieger.friendsystem.spigot.api.inventory.item.ItemStack;
import java.util.LinkedHashMap;
import java.util.Map;

public class AdvancedConfig {

    private int skullFirstSlot, skullLastSlot;
    private Map<String, MainInventory> inventories;

    public AdvancedConfig() {
        this.skullFirstSlot = 1;
        this.skullLastSlot = 36;
        this.inventories = new LinkedHashMap<>();

        MainInventory friendInventory = new MainInventory("§eFriends", 54);


        friendInventory.setItem(45, new ItemStack("friends","314:0").setDisplayName("§eFriends"));
        friendInventory.setItem(46, new ItemStack("parties", "401:0").setDisplayName("§5Party"));
        friendInventory.setItem(47, new ItemStack("settings", "356:0").setDisplayName("§cSettings"));

        ConditionInventory friendRequests = new ConditionInventory("friends", friendInventory, "friendRequests");
        friendRequests.setItem(50, new ItemStack("friendRequests","358:0").setDisplayName("§6Friend Requests"));
        friendInventory.addConditionInventory(friendRequests);

        ConditionInventory nextPage = new ConditionInventory("friends", friendInventory, "nextFriendPage");
        nextPage.setItem(44, new ItemStack("nextPage", "262:0").setDisplayName("§aNext Page"));
        friendInventory.addConditionInventory(nextPage);

        ConditionInventory previousPage = new ConditionInventory("friends", friendInventory, "previousFriendPage");
        previousPage.setItem(43, new ItemStack("previousPage", "262:0").setDisplayName("§cPrevious Page"));
        friendInventory.addConditionInventory(previousPage);

        MainInventory partyInventory = new MainInventory("§5Party", 54);


        MainInventory settingsInventory = new MainInventory("§cSettings", 54);

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

    public Map<String, MainInventory> getInventories() {
        return inventories;
    }
}