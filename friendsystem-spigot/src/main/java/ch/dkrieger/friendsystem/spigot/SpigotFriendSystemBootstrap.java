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
import ch.dkrieger.friendsystem.spigot.api.inventory.inventory.ConditionInventory;
import ch.dkrieger.friendsystem.spigot.api.inventory.inventory.MainInventory;
import ch.dkrieger.friendsystem.spigot.api.inventory.itemstack.ItemStack;
import ch.dkrieger.friendsystem.spigot.api.inventory.Listener;
import ch.dkrieger.friendsystem.spigot.listener.InventoryClickListener;
import ch.dkrieger.friendsystem.spigot.listener.InventoryCloseListener;
import ch.dkrieger.friendsystem.spigot.listener.InventoryOpenListener;
import ch.dkrieger.friendsystem.spigot.listener.PlayerListener;
import com.google.gson.reflect.TypeToken;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Collection;
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
        pluginManager.registerEvents(new PlayerListener(), this);
    }

    private void loadInventoryConfig() {
        File file = new File(getFolder(), "advancedspigotconfig.json");
        if(file.exists() && file.isFile()) this.advancedConfig = Document.loadData(file);
        else this.advancedConfig = new Document();

        Map<String, MainInventory> inventories = new LinkedHashMap<>();
        /*MainInventory test1 = new MainInventory("test1", 27);

        test1.addItem(new ItemStack("1:0").addListener(new Listener(Listener.DefaultEvent.CLICK, "me ist der beste", Listener.CommandRunner.PLAYER)));
        test1.addItem(new ItemStack("2:0"));

        ConditionInventory ctest1 = new ConditionInventory(test1, "ctest1");
        ctest1.addItem(new ItemStack("3:0"));
        ctest1.addItem(new ItemStack("4:0"));
        ctest1.addItem(new ItemStack("5:0"));
        ctest1.addItem(new ItemStack("7:0").addListener(new Listener(Listener.DefaultEvent.CLICK, "say hello from server", Listener.CommandRunner.CONSOLE)));

        test1.addConditionInventory(ctest1);
        test1.addListener(new Listener(Listener.DefaultEvent.INVENTORY_OPEN, "say inventory open", Listener.CommandRunner.CONSOLE));
        test1.addListener(new Listener(Listener.DefaultEvent.INVENTORY_CLOSE, "me closed inventory", Listener.CommandRunner.PLAYER));

        inventories.put("test1", test1);*/


        this.advancedConfig.appendDefault("inventories", inventories);
        if(!(file.exists() && file.isFile())){
            file.delete();
            this.advancedConfig.saveData(file);
        }
    }

    public static SpigotFriendSystemBootstrap getInstance() {
        return instance;
    }
}