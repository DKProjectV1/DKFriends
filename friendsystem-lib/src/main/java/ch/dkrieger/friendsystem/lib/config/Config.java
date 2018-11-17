package ch.dkrieger.friendsystem.lib.config;

/*
 *
 *  * Copyright (c) 2018 Philipp Elvin Friedhoff on 16.11.18 19:36
 *
 */

import ch.dkrieger.friendsystem.lib.DKFriendsPlatform;
import ch.dkrieger.friendsystem.lib.storage.StorageType;

import java.io.File;
import java.util.Arrays;

public class Config extends SimpleConfig {

    private final DKFriendsPlatform platform;
    private StorageType storageType;
    private String host, port, user, password, database, mongoDbAuthenticationDatabase, dataFolder;
    private boolean mongoDbSrv, mongoDbAuthentication, commandFriendEnabled;

    public Config(DKFriendsPlatform platform) {
        super(new File(platform.getFolder(),"config.yml"));
        this.platform = platform;
    }

    public StorageType getStorageType() {
        return storageType;
    }

    public String getHost() {
        return host;
    }

    public String getPort() {
        return port;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public String getDatabase() {
        return database;
    }

    public String getMongoDbAuthenticationDatabase() {
        return mongoDbAuthenticationDatabase;
    }
    public boolean hasMongoDbAuthentication() {
        return mongoDbAuthentication;
    }
    public boolean hasMongoDbSrv() {
        return mongoDbSrv;
    }
    public boolean isCommandFriendEnabled() {
        return commandFriendEnabled;
    }
    public String getDataFolder() {
        return dataFolder;
    }

    @Override
    public void onLoad() {
        this.dataFolder = getStringValue("storage.folder");
        this.storageType = StorageType.parse(getStringValue("storage.type"));
        this.host = getStringValue("storage.host");
        this.port = getStringValue("storage.port");
        this.user = getStringValue("storage.user");
        this.password = getStringValue("storage.password");
        this.database = getStringValue("storage.database");
        this.mongoDbAuthentication = getBooleanValue("storage.mongodb.mongodbauthentication");
        this.mongoDbAuthenticationDatabase = getStringValue("storage.mongodb.authenticationDatabase");
        this.mongoDbSrv = getBooleanValue("storage.mongodb.srv");

        this.commandFriendEnabled = getBooleanValue("command.friend.enabled");
    }
    @Override
    public void registerDefaults() {
        addValue("storage.folder",this.platform.getFolder()+"/data/");
        addValue("storage.type", StorageType.MONGODB);//change to json
        addValue("storage.host", "127.0.0.1");
        addValue("storage.port", "3306"); //mongo 27018
        addValue("storage.user", "root");
        addValue("storage.password", "password");
        addValue("storage.database", "DKFriends");
        addValue("storage.mongodb.mongodbauthentication",true);
        addValue("storage.mongodb.authenticationDatabase", "admin");
        addValue("storage.mongodb.srv", false);

        addValue("command.friend.enabled",true);
        addValue("command.friend.name","friend");
        addValue("command.friend.aliases", Arrays.asList("f","freund"));
        addValue("command.party.enabled",true);
        addValue("command.party.name","party");
        addValue("command.party.aliases", Arrays.asList("p"));
    }
}