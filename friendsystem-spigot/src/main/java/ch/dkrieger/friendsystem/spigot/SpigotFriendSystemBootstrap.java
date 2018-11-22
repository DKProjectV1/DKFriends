package ch.dkrieger.friendsystem.spigot;

/*
 *
 *  * Copyright (c) 2018 Philipp Elvin Friedhoff on 17.11.18 13:17
 *
 */

import ch.dkrieger.friendsystem.lib.DKFriendsPlatform;
import ch.dkrieger.friendsystem.lib.FriendSystem;
import ch.dkrieger.friendsystem.lib.command.FriendCommandManager;
import ch.dkrieger.friendsystem.lib.utils.Document;
import ch.dkrieger.friendsystem.spigot.api.inventory.Listener;
import ch.dkrieger.friendsystem.spigot.api.inventory.inventory.ConditionInventory;
import ch.dkrieger.friendsystem.spigot.api.inventory.inventory.MainInventory;
import ch.dkrieger.friendsystem.spigot.api.inventory.item.ItemStack;
import ch.dkrieger.friendsystem.spigot.api.inventory.item.ItemStackType;
import ch.dkrieger.friendsystem.spigot.listener.*;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;

public class SpigotFriendSystemBootstrap extends JavaPlugin implements DKFriendsPlatform {

    private static SpigotFriendSystemBootstrap instance;
    private SpigotCommandManager commandManager;
    private InventoryManager inventoryManager;
    private Document advancedConfig;

    @Override
    public void onLoad() {
        instance = this;
        loadInventoryConfig();
        this.inventoryManager = new InventoryManager();
        this.commandManager = new SpigotCommandManager();
        new FriendSystem(this, new SpigotFriendPlayerManager());
    }

    @Override
    public void onEnable() {
        registerListener();
    }

    @Override
    public void onDisable() {

    }

    @Override
    public String getPlatformName() {
        return "Spigot";
    }

    @Override
    public String getServerVersion() {
        return Bukkit.getVersion();
    }

    @Override
    public File getFolder() {
        return new File("plugins/DKFriends/");
    }

    @Override
    public FriendCommandManager getCommandManager() {
        return this.commandManager;
    }

    public Document getAdvancedConfig() {
        return advancedConfig;
    }

    public InventoryManager getInventoryManager() {
        return inventoryManager;
    }

    private void registerListener() {
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new InventoryOpenListener(), this);
        pluginManager.registerEvents(new InventoryClickListener(), this);
        pluginManager.registerEvents(new InventoryCloseListener(), this);
        pluginManager.registerEvents(new PlayerJoinListener(), this);
        pluginManager.registerEvents(new PlayerLeaveListener(), this);
    }

    private void loadInventoryConfig() {
        File file = new File(getFolder(), "advancedspigotconfig.json");
        if(file.exists() && file.isFile()) this.advancedConfig = Document.loadData(file);
        else this.advancedConfig = new Document();

        Map<String, MainInventory> inventories = new LinkedHashMap<>();

        MainInventory friendInventory = new MainInventory("§eFriends", 54);

        for(int i = 37; i < 46; i++) friendInventory.setItem(i, new ItemStack(ItemStackType.PLACEHOLDER));
        for(int i = 49; i <= 54; i++) friendInventory.setItem(i, new ItemStack(ItemStackType.PLACEHOLDER));
        friendInventory.setItem(46, new ItemStack("friends","314:0").setDisplayName("§eFriends"));
        friendInventory.setItem(47, new ItemStack("parties", "401:0").setDisplayName("§5Party"));
        friendInventory.setItem(48, new ItemStack("settings", "356:0").setDisplayName("§cSettings"));

        ConditionInventory friendRequests = new ConditionInventory("friends", friendInventory, "friendRequests");
        friendRequests.setItem(51, new ItemStack("friendRequests","358:0").setDisplayName("§6Friend Requests"));
        friendInventory.addConditionInventory(friendRequests);

        ConditionInventory nextPage = new ConditionInventory("friends", friendInventory, "nextFriendPage");
        nextPage.setItem(45, new ItemStack("nextPage", "262:0").setDisplayName("§aNext Page"));
        friendInventory.addConditionInventory(nextPage);

        ConditionInventory previousPage = new ConditionInventory("friends", friendInventory, "previousFriendPage");
        previousPage.setItem(44, new ItemStack("previousPage", "262:0").setDisplayName("§cPrevious Page"));
        friendInventory.addConditionInventory(previousPage);

        MainInventory partyInventory = new MainInventory("§5Party", 54);


        MainInventory settingsInventory = new MainInventory("§cSettings", 54);

        inventories.put("friends", friendInventory);
        inventories.put("parties", partyInventory);
        inventories.put("settings", settingsInventory);
        this.advancedConfig.appendDefault("inventories", inventories);

        if(!(file.exists() && file.isFile())) {
            file.delete();
            this.advancedConfig.saveData(file);
        }
    }

    public static SpigotFriendSystemBootstrap getInstance() {
        return instance;
    }
}