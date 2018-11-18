package ch.dkrieger.friendsystem.lib;

import ch.dkrieger.friendsystem.lib.command.FriendCommandManager;
import ch.dkrieger.friendsystem.lib.command.defaults.friend.FriendCommand;
import ch.dkrieger.friendsystem.lib.command.defaults.party.PartyCommand;
import ch.dkrieger.friendsystem.lib.config.Config;
import ch.dkrieger.friendsystem.lib.config.MessageConfig;
import ch.dkrieger.friendsystem.lib.party.PartyManager;
import ch.dkrieger.friendsystem.lib.player.FriendPlayerManager;
import ch.dkrieger.friendsystem.lib.storage.FriendStorage;
import ch.dkrieger.friendsystem.lib.storage.StorageType;
import ch.dkrieger.friendsystem.lib.storage.json.JsonFriendStorage;
import ch.dkrieger.friendsystem.lib.storage.mongodb.MongoDBFriendStorage;
import ch.dkrieger.friendsystem.lib.storage.sql.mysql.MySQLFriendStorage;
import ch.dkrieger.friendsystem.lib.storage.sql.sqlite.SQLiteFriendStorage;

/*
 *
 *  * Copyright (c) 2018 Davide Wietlisbach on 16.11.18 17:47
 *
 */

public class FriendSystem {

    private static FriendSystem instance;
    private final String version;
    private final DKFriendsPlatform platform;

    private FriendPlayerManager playerManager;
    private PartyManager partyManager;
    private FriendStorage storage;
    private Config config;
    private MessageConfig messageConfig;

    public FriendSystem(DKFriendsPlatform platform, FriendPlayerManager playerManager) {
        if(instance != null) throw new IllegalArgumentException("DKFriends is already running!");
        instance = this;
        this.version = "1.0.0";
        this.platform = platform;
        new Messages("DKFriends");

        System.out.println(Messages.SYSTEM_PREFIX+"plugin is starting");
        System.out.println(Messages.SYSTEM_PREFIX+"FriendSystem "+this.version+" by Davide Wietlisbach & Philipp Elvin Friedhoff");

        this.playerManager = playerManager;
        this.partyManager = new PartyManager();

        systemBootstrap();

        System.out.println(Messages.SYSTEM_PREFIX+"plugin successfully started");
    }
    private void systemBootstrap() {
        this.platform.getFolder().mkdirs();

        this.config = new Config(this.platform);
        this.messageConfig = new MessageConfig(this.platform);
        this.config.loadConfig();
        this.messageConfig.loadConfig();

        setupStorage();


        registerCommands();
    }
    private void setupStorage(){
        if(this.config.getStorageType() == StorageType.MYSQL) this.storage = new MySQLFriendStorage(this.config);
        else if(this.config.getStorageType() == StorageType.SQLITE) this.storage = new SQLiteFriendStorage(this.config);
        else if(this.config.getStorageType() == StorageType.MONGODB) this.storage = new MongoDBFriendStorage(this.config);

        if(this.storage != null && this.storage.connect()) return;

        this.storage = new JsonFriendStorage(this.config);
    }
    public void shutdown(){
        if(this.storage != null) this.storage.disconnect();
    }
    public String getVersion() {
        return this.version;
    }
    public DKFriendsPlatform getPlatform() {
        return this.platform;
    }
    public FriendCommandManager getCommandManager() {
        return this.platform.getCommandManager();
    }
    public FriendPlayerManager getPlayerManager() {
        return this.playerManager;
    }
    public PartyManager getPartyManager() {
        return partyManager;
    }
    public FriendStorage getStorage() {
        return storage;
    }

    public void registerCommands(){
        if(this.config.isCommandFriendEnabled()){
            this.platform.getCommandManager().registerCommand(new FriendCommand());
        }
        this.platform.getCommandManager().registerCommand(new PartyCommand());
    }

    public static FriendSystem getInstance() {
        return instance;
    }
}