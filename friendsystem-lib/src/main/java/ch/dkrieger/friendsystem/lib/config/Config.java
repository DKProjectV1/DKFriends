package ch.dkrieger.friendsystem.lib.config;

/*
 *
 *  * Copyright (c) 2018 Philipp Elvin Friedhoff on 16.11.18 19:36
 *
 */

import ch.dkrieger.friendsystem.lib.storage.StorageType;

import java.io.File;

public class Config extends SimpleConfig {

    private StorageType storageType;
    private String host, port, user, password, database, mongoDbAuthenticationDatabase, jsonFolder;
    private boolean mongoDbSrv;

    public Config(File file) {
        super(file);
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

    public boolean isMongoDbSrv() {
        return mongoDbSrv;
    }

    public String getJsonFolder() {
        return jsonFolder;
    }

    @Override
    public void onLoad() {
        this.storageType = (StorageType) getValue("storage.type");
        this.host = getStringValue("storage.host");
        this.port = getStringValue("storage.port");
        this.user = getStringValue("storage.user");
        this.password = getStringValue("storage.password");
        this.database = getStringValue("storage.database");
        this.mongoDbAuthenticationDatabase = getStringValue("storage.mongodb.authenticationDatabase");
        this.mongoDbSrv = getBooleanValue("storage.mongodb.srv");
        this.jsonFolder = getStringValue("storage.json.folder");
    }

    @Override
    public void registerDefaults() {
        addValue("storage.type", StorageType.MONGODB);
        addValue("storage.host", "127.0.0.1");
        addValue("storage.port", "27018");
        addValue("storage.user", "root");
        addValue("storage.password", "1234");
        addValue("storage.database", "dkfriends");
        addValue("storage.mongodb.authenticationDatabase", "admin");
        addValue("storage.mongodb.srv", false);
        addValue("storage.json.folder", "json");
    }
}