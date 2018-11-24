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
import ch.dkrieger.friendsystem.spigot.adapter.Adapter;
import ch.dkrieger.friendsystem.spigot.adapter.FriendAdapter;
import ch.dkrieger.friendsystem.spigot.adapter.friends.NextPageFriendAdapter;
import ch.dkrieger.friendsystem.spigot.adapter.friends.OpenFriendPageAdapter;
import ch.dkrieger.friendsystem.spigot.adapter.friends.PreviousPageFriendAdapter;
import ch.dkrieger.friendsystem.spigot.adapter.inventory.OpenInventoryAdapter;
import ch.dkrieger.friendsystem.spigot.adapter.party.OpenPartyPageAdapter;
import ch.dkrieger.friendsystem.spigot.adapter.settings.OpenSettingsPageAdapter;
import ch.dkrieger.friendsystem.spigot.api.inventory.item.ItemStorage;
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
import java.util.LinkedList;
import java.util.List;

public class SpigotFriendSystemBootstrap extends JavaPlugin implements DKFriendsPlatform {

    private static SpigotFriendSystemBootstrap instance;
    private SpigotCommandManager commandManager;
    private BungeeCordConnection bungeeCordConnection;
    private InventoryManager inventoryManager;
    private List<Adapter> adapters;
    private Document document;

    @Override
    public void onLoad() {
        instance = this;
        this.commandManager = new SpigotCommandManager();
        this.bungeeCordConnection = new BungeeCordConnection();
        new FriendSystem(this, new SpigotFriendPlayerManager(), new SpigotPartyManager());
        this.adapters = new LinkedList<>();
        registerAdapters();
        loadAdvancedConfig();
        this.inventoryManager = new InventoryManager(getAdvancedConfig().getInventories());
    }

    @Override
    public void onEnable() {
        registerListener();
        Bukkit.getScheduler().runTaskLater(this,()->{
            if(isCloudNet()){
                FriendSystem.getInstance().setPlayerManager(new SpigotCloudNetPlayerManager());
                FriendSystem.getInstance().setPartyManager(new CloudNetPartyManager());
            }else if(isCloudNet()){
                Bukkit.getPluginManager().registerEvents(new CloudNetMessageChannelListener(),this);
                FriendSystem.getInstance().setPlayerManager(new SpigotCloudNetPlayerManager());
                FriendSystem.getInstance().setPartyManager(new CloudNetPartyManager());
            }else if(FriendSystem.getInstance().getConfig().isBungeeCord()){
                Bukkit.getMessenger().registerOutgoingPluginChannel(this,"DKFriends");
                Bukkit.getMessenger().registerIncomingPluginChannel(this,"DKFriends",this.bungeeCordConnection);
                FriendSystem.getInstance().setPlayerManager(new SpigotBungeeCordPlayerManager());
                FriendSystem.getInstance().setPartyManager(new SpigotBungeeCordPartyManager());
            }
        },10L);
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

    public BungeeCordConnection getBungeeCordConnection() {
        return this.bungeeCordConnection;
    }

    public AdvancedConfig getAdvancedConfig() {
        return document.getObject("config", AdvancedConfig.class);
    }

    public InventoryManager getInventoryManager() {
        return inventoryManager;
    }

    public Adapter getAdapter(String name) {
        for(Adapter adapter : this.adapters) if(adapter.getName().startsWith(name))return adapter;
        return null;
    }

    public FriendAdapter getFriendAdapter(String name) {
        return (FriendAdapter)getAdapter(name);
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

    private void registerListener() {
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new PlayerListener(),this);
        pluginManager.registerEvents(new InventoryOpenListener(), this);
        pluginManager.registerEvents(new InventoryClickListener(), this);
        pluginManager.registerEvents(new InventoryCloseListener(), this);
        pluginManager.registerEvents(new PlayerJoinListener(), this);
        pluginManager.registerEvents(new PlayerLeaveListener(), this);
    }

    private void loadAdvancedConfig() {
        File file = new File(getFolder(), "advancedspigotconfig.json");
        if(file.exists() && file.isFile()) this.document = Document.loadData(file);
        else this.document = new Document();
        this.document.appendDefault("config", new AdvancedConfig());
        if(!(file.exists() && file.isFile())) {
            file.delete();
            this.document.saveData(file);
        }
    }

    public void registerAdapter(Adapter adapter) {
        this.adapters.add(adapter);
    }

    private void registerAdapters() {
        registerAdapter(new NextPageFriendAdapter());
        registerAdapter(new PreviousPageFriendAdapter());
        registerAdapter(new OpenFriendPageAdapter());
        registerAdapter(new OpenInventoryAdapter());
        registerAdapter(new OpenPartyPageAdapter());
        registerAdapter(new OpenSettingsPageAdapter());
    }

    public static SpigotFriendSystemBootstrap getInstance() {
        return instance;
    }
}
