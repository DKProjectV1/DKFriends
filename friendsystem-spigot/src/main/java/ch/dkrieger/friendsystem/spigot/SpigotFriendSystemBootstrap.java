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
import ch.dkrieger.friendsystem.spigot.inventory.Inventory;
import ch.dkrieger.friendsystem.spigot.inventory.InventoryConfig;
import ch.dkrieger.friendsystem.spigot.listener.InventoryOpenListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class SpigotFriendSystemBootstrap extends JavaPlugin implements DKFriendsPlatform {

    private static SpigotFriendSystemBootstrap instance;
    private SpigotCommandManager commandManager;
    private Document inventoryConfig;

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

    public Document getInventoryConfig() {
        return inventoryConfig;
    }

    public Inventory getInventory(String inventory) {
        return getInventoryConfig().getObject("inventories", InventoryConfig.class).getInventory(inventory);
    }

    private void registerListener() {
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new InventoryOpenListener(), this);
    }

    private void loadInventoryConfig() {
        File file = new File(getFolder(), "inventories.json");
        if(file.exists() && file.isFile()) this.inventoryConfig = Document.loadData(file);
        else this.inventoryConfig = new Document();
        InventoryConfig inventoryConfig = new InventoryConfig();

        this.inventoryConfig.appendDefault("inventories", inventoryConfig);
        if(!(file.exists() && file.isFile())){
            file.delete();
            this.inventoryConfig.saveData(file);
        }
    }

    public static SpigotFriendSystemBootstrap getInstance() {
        return instance;
    }
}