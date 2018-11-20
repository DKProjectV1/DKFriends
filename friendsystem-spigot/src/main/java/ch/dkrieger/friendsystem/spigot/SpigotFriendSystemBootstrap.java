package ch.dkrieger.friendsystem.spigot;

/*
 *
 *  * Copyright (c) 2018 Philipp Elvin Friedhoff on 17.11.18 13:17
 *
 */

import ch.dkrieger.friendsystem.lib.DKFriendsPlatform;
import ch.dkrieger.friendsystem.lib.FriendSystem;
import ch.dkrieger.friendsystem.lib.command.FriendCommandManager;
import ch.dkrieger.friendsystem.lib.player.Friend;
import ch.dkrieger.friendsystem.lib.utils.Document;
import ch.dkrieger.friendsystem.spigot.api.inventory.inventory.ConditionInventory;
import ch.dkrieger.friendsystem.spigot.api.inventory.inventory.Inventory;
import ch.dkrieger.friendsystem.spigot.api.inventory.itemstack.ItemStack;
import ch.dkrieger.friendsystem.spigot.api.inventory.itemstack.ItemStackListener;
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
import java.util.List;
import java.util.Map;

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
        Map<String, Inventory> inventoryMap = getAdvancedConfig().getObject("inventories", new TypeToken<Map<String, Inventory>>(){}.getType());
        return inventoryMap.get(inventory);
    }

    public Collection<Inventory> getInventories() {
        Map<String, Inventory> inventoryMap = getAdvancedConfig().getObject("inventories", new TypeToken<Map<String, Inventory>>(){}.getType());
        return inventoryMap.values();
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

        Map<String, Inventory> inventories = new LinkedHashMap<>();
        Inventory test1 = new Inventory("test1", 27);

        test1.addItem(new ItemStack("1:0").addListener("click", new ItemStackListener("me ist cool", ItemStackListener.ConsoleSender.PLAYER)));
        test1.addItem(new ItemStack("2:0"));

        ConditionInventory ctest1 = new ConditionInventory(test1, "ctest1");
        ctest1.addItem(new ItemStack("3:0"));
        ctest1.addItem(new ItemStack("4:0"));
        ctest1.addItem(new ItemStack("5:0"));
        ctest1.addItem(new ItemStack("7:0").addListener("click", new ItemStackListener("say hallo test", ItemStackListener.ConsoleSender.CONSOLE)));


        test1.addConditionInventory(ctest1);

        inventories.put("test1", test1);

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