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
import ch.dkrieger.friendsystem.spigot.api.inventory.inventory.Inventory;
import ch.dkrieger.friendsystem.spigot.api.inventory.InventoryConfig;
import ch.dkrieger.friendsystem.spigot.listener.InventoryClickListener;
import ch.dkrieger.friendsystem.spigot.listener.InventoryCloseListener;
import ch.dkrieger.friendsystem.spigot.listener.InventoryOpenListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class SpigotFriendSystemBootstrap extends JavaPlugin implements DKFriendsPlatform {

    private static SpigotFriendSystemBootstrap instance;
    private SpigotCommandManager commandManager;
    private Document advancedConfig;

    @Override
    public void onLoad() {
        instance = this;
        loadInventoryConfig();
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

    public Inventory getInventory(String inventory) {
        return getAdvancedConfig().getObject("inventories", InventoryConfig.class).getInventory(inventory);
    }

    private void registerListener() {
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new InventoryOpenListener(), this);
        pluginManager.registerEvents(new InventoryClickListener(), this);
        pluginManager.registerEvents(new InventoryCloseListener(), this);
    }

    private void loadInventoryConfig() {
        File file = new File(getFolder(), "advancedspigotconfig.json");
        if(file.exists() && file.isFile()) this.advancedConfig = Document.loadData(file);
        else this.advancedConfig = new Document();

        InventoryConfig inventoryConfig = new InventoryConfig();

        this.advancedConfig.appendDefault("inventories", inventoryConfig);
        if(!(file.exists() && file.isFile())){
            file.delete();
            this.advancedConfig.saveData(file);
        }
    }

    public static SpigotFriendSystemBootstrap getInstance() {
        return instance;
    }
}