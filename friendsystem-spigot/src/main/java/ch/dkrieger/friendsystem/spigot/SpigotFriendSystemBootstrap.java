package ch.dkrieger.friendsystem.spigot;

/*
 *
 *  * Copyright (c) 2018 Philipp Elvin Friedhoff on 17.11.18 13:17
 *
 */

import ch.dkrieger.friendsystem.lib.DKFriendsPlatform;
import ch.dkrieger.friendsystem.lib.FriendSystem;
import ch.dkrieger.friendsystem.lib.Messages;
import ch.dkrieger.friendsystem.lib.cloudnet.CloudNetPartyManager;
import ch.dkrieger.friendsystem.lib.command.FriendCommandManager;
import ch.dkrieger.friendsystem.lib.utils.Document;
import ch.dkrieger.friendsystem.spigot.api.inventory.inventory.ConditionInventory;
import ch.dkrieger.friendsystem.spigot.api.inventory.inventory.MainInventory;
import ch.dkrieger.friendsystem.spigot.api.inventory.item.ItemStack;
import ch.dkrieger.friendsystem.spigot.api.inventory.item.ItemStackType;
import ch.dkrieger.friendsystem.spigot.listener.*;
import ch.dkrieger.friendsystem.spigot.party.SpigotBungeeCordPartyManager;
import ch.dkrieger.friendsystem.spigot.party.SpigotPartyManager;
import ch.dkrieger.friendsystem.spigot.player.SpigotCloudNetPlayerManager;
import ch.dkrieger.friendsystem.spigot.player.bungeecord.SpigotBungeeCordPlayerManager;
import ch.dkrieger.friendsystem.spigot.player.local.SpigotFriendPlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;

public class SpigotFriendSystemBootstrap extends JavaPlugin implements DKFriendsPlatform {

    private static SpigotFriendSystemBootstrap instance;
    private SpigotCommandManager commandManager;

    private BungeeCordConnection bungeeCordConnection;

    private InventoryManager inventoryManager;
    private Document advancedConfig;

    @Override
    public void onLoad() {
        instance = this;
        loadInventoryConfig();
        this.inventoryManager = new InventoryManager();
        this.commandManager = new SpigotCommandManager();
        this.bungeeCordConnection = new BungeeCordConnection();
        new FriendSystem(this, new SpigotFriendPlayerManager(),new SpigotPartyManager());
    }

    public BungeeCordConnection getBungeeCordConnection() {
        return this.bungeeCordConnection;
    }

    @Override
    public void onEnable() {
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new InventoryOpenListener(), this);
        pluginManager.registerEvents(new InventoryClickListener(), this);
        pluginManager.registerEvents(new InventoryCloseListener(), this);
        pluginManager.registerEvents(new PlayerJoinListener(), this);
        pluginManager.registerEvents(new PlayerLeaveListener(), this);

        Bukkit.getScheduler().runTaskLater(this,()->{
            if(isCloudNet()){
                FriendSystem.getInstance().setPlayerManager(new SpigotCloudNetPlayerManager());
                FriendSystem.getInstance().setPartyManager(new CloudNetPartyManager());
            }else{
                Bukkit.getMessenger().registerOutgoingPluginChannel(this,"DKFriends");
                Bukkit.getMessenger().registerIncomingPluginChannel(this,"DKFriends",this.bungeeCordConnection);
                FriendSystem.getInstance().setPlayerManager(new SpigotBungeeCordPlayerManager());
                FriendSystem.getInstance().setPartyManager(new SpigotBungeeCordPartyManager());
            }
        },20L);
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
    private boolean isCloudNet(){
        Plugin cloudnet = getServer().getPluginManager().getPlugin("CloudNetAPI");
        if(cloudnet != null){
            if(cloudnet.getDescription() != null){
                System.out.println(Messages.SYSTEM_PREFIX+"CloudNet found");
                return true;
            }
        }
        return false;
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