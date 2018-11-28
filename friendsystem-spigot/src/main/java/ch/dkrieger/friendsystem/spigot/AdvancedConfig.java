package ch.dkrieger.friendsystem.spigot;

/*
 *
 *  * Copyright (c) 2018 Philipp Elvin Friedhoff on 23.11.18 14:24
 *
 */

import ch.dkrieger.friendsystem.lib.config.Config;
import ch.dkrieger.friendsystem.spigot.api.inventory.Listener;
import ch.dkrieger.friendsystem.spigot.api.inventory.inventory.ConfigInventory;
import ch.dkrieger.friendsystem.spigot.api.inventory.item.ItemStack;
import java.util.LinkedHashMap;
import java.util.Map;

public class AdvancedConfig {

    private Map<String, Object> settings;
    private Map<String, ItemStack> items;
    private Map<String, ConfigInventory> inventories;
    private Map<Integer, ItemStack> defaultInventoryItems;

    public AdvancedConfig() {
        this.settings = new LinkedHashMap<>();
        addSettingAsInt("friendPageSkullFirstSlot", 0);
        addSettingAsInt("friendPageSkullLastSlot", 35);

        addSettingAsInt("friendRequestsPageSkullFirstSlot", 0);
        addSettingAsInt("friendRequestsPageSkullLastSlot", 35);

        addSettingAsInt("partyPageSkullFirstSlot", 0);
        addSettingAsInt("partyPageSkullLastSlot", 35);

        addSettingAsInt("friendSwitchPageInventorySlot1", 44);
        addSettingAsInt("friendSwitchPageInventorySlot2", 43);

        addSettingAsInt("friendRequestsSwitchPageInventorySlot1", 44);
        addSettingAsInt("friendRequestsSwitchPageInventorySlot2", 43);

        addSettingAsInt("partySwitchPageInventorySlot1", 44);
        addSettingAsInt("partySwitchPageInventorySlot2", 43);

        this.items = new LinkedHashMap<>();

        items.put("onlinePlayerSkull", new ItemStack("397:3").addLore("§aOnline auf [server]").addListener(Listener.DefaultEvent.CLICK, "openFriendOptionsPage"));
        items.put("offlinePlayerSkull", new ItemStack("397:0").addLore("§cZuletzt online: [lastonline]").addListener(Listener.DefaultEvent.CLICK, "openFriendOptionsPage"));
        items.put("nextFriendPage", new ItemStack("262:0").setDisplayName("§aNext Page").addListener(new Listener(Listener.DefaultEvent.CLICK, "nextFriendPage")));
        items.put("previousFriendPage", new ItemStack("262:0").setDisplayName("§cPrevious Page").addListener(new Listener(Listener.DefaultEvent.CLICK, "previousFriendPage")));
        items.put("partyPlayerSkull", new ItemStack("393:3").addListener(Listener.DefaultEvent.CLICK, "openPartyPlayerPage"));
        items.put("nextPartyPage", new ItemStack("262:0").setDisplayName("§aNext Page").addListener(new Listener(Listener.DefaultEvent.CLICK, "nextPartyPage")));
        items.put("previousPartyPage", new ItemStack("262:0").setDisplayName("§cPrevious Page").addListener(new Listener(Listener.DefaultEvent.CLICK, "previousPartyPage")));
        items.put("requestItem", new ItemStack("339:0").setDisplayName("[requester]").addListener(new Listener(Listener.DefaultEvent.CLICK, "acceptFriendRequest")));
        items.put("nextFriendRequestsPage", new ItemStack("262:0").setDisplayName("§aNext Page").addListener(new Listener(Listener.DefaultEvent.CLICK, "nextFriendRequestsPage")));
        items.put("previousFriendRequestsPage", new ItemStack("262:0").setDisplayName("§cPrevious Page").addListener(new Listener(Listener.DefaultEvent.CLICK, "previousFriendRequestsPage")));

        this.defaultInventoryItems = new LinkedHashMap<>();

        defaultInventoryItems.put(45, new ItemStack("314:0").setDisplayName("§eFriends").addListener(new Listener(Listener.DefaultEvent.CLICK, "openFriendPage")));
        defaultInventoryItems.put(46, new ItemStack("401:0").setDisplayName("§5Party").addListener(new Listener(Listener.DefaultEvent.CLICK, "openPartyPage")));
        defaultInventoryItems.put(47, new ItemStack("356:0").setDisplayName("§cSettings").addListener(new Listener(Listener.DefaultEvent.CLICK, "openSettingsPage")));

        this.inventories = new LinkedHashMap<>();

        ConfigInventory friendInventory = new ConfigInventory("§eFriends", 54);
        friendInventory.setItem("hasRequests", 50, new ItemStack("358:0").setDisplayName("§6Friend Requests").addListener(Listener.DefaultEvent.CLICK, "openFriendRequestsPage"));

        ConfigInventory friendRequestsInventory = new ConfigInventory("§cRequests", 54);

        ConfigInventory partyInventory = new ConfigInventory("§5Party", 54);
        partyInventory.setItem("partyPublic", 53, new ItemStack("147:0").setDisplayName("§cUnPublish").addListener(Listener.DefaultEvent.CLICK, "unPublishParty"));
        partyInventory.setItem("partyPrivate", 53, new ItemStack("70:0").setDisplayName("§aPublish").addListener(Listener.DefaultEvent.CLICK, "publishParty"));
        partyInventory.setItem("isInParty", 52, new ItemStack("341").setDisplayName("§cLeave").addListener(Listener.DefaultEvent.CLICK, "leaveParty"));

        ConfigInventory partyPlayerInventory = new ConfigInventory("[partymember]", 27);
        partyPlayerInventory.setItem("canIntegrate", 1, new ItemStack("166:0").addListener(Listener.DefaultEvent.CLICK, "banPlayerFromParty"));
        partyPlayerInventory.setItem("canIntegrate", 2, new ItemStack("2:0").addListener(Listener.DefaultEvent.CLICK, "kickPlayerFromParty"));
        partyPlayerInventory.setItem("canPromote", 3, new ItemStack("3:0").addListener(Listener.DefaultEvent.CLICK, "promotePartyPlayer"));
        partyInventory.setItem("canDemote", 4, new ItemStack("4:0").addListener(Listener.DefaultEvent.CLICK, "demotePartyPlayer"));

        ConfigInventory settingsInventory = new ConfigInventory("§cSettings", 54);

        ConfigInventory friendOptionsInventory = new ConfigInventory("[friend]", 27);
        friendOptionsInventory.setItem("friendOnline", 12, new ItemStack("354:0").setDisplayName("").addListener(Listener.DefaultEvent.CLICK, "invitePlayerToParty"));
        friendOptionsInventory.setItem("friendOnline", 14, new ItemStack("368:0").addListener(Listener.DefaultEvent.CLICK, "jumpToPlayer"));
        friendOptionsInventory.setItem(15, new ItemStack("166:0").addListener(Listener.DefaultEvent.CLICK, "removeFriend"));

        friendOptionsInventory.setItem("friendOnline", 11, new ItemStack("397:3").addLore("§aOnline auf [server]"));
        friendOptionsInventory.setItem("friendOffline", 11, new ItemStack("397:0"));

        ConfigInventory friendRequestDecisionInventory = new ConfigInventory("[friend]", 27);
        friendRequestDecisionInventory.addItem(new ItemStack("159:13").addListener(Listener.DefaultEvent.CLICK, "acceptFriendRequest"));
        friendRequestDecisionInventory.addItem(new ItemStack("159:14").addListener(Listener.DefaultEvent.CLICK, "denyFriendRequest"));

        inventories.put("friends", friendInventory);
        inventories.put("friendRequests", friendRequestsInventory);
        inventories.put("friendOptions", friendOptionsInventory);
        inventories.put("friendRequestDecision", friendRequestDecisionInventory);
        inventories.put("parties", partyInventory);
        inventories.put("partyPlayer", partyPlayerInventory);
        inventories.put("settings", settingsInventory);
    }

    public Map<String, Object> getSettings() {
        return settings;
    }

    public Object getSetting(String setting) {
        return this.settings.get(setting);
    }

    public int getSettingAsInt(String setting) {
        System.out.println(setting + ":" + getSetting(setting));
        return (int) Math.round((Double) getSetting(setting));
    }

    public String getSettingAsString(String setting) {
        return (String) getSetting(setting);
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
        return getItems().get(key);
    }

    public void addSetting(String setting, Object value) {
        this.settings.put(setting, value);
    }

    public void addSettingAsInt(String setting, int value) {
        addSetting(setting, (int)value);
    }
}