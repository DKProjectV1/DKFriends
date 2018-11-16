package ch.dkrieger.friendsystem.lib.storage.mongodb;

/*
 *
 *  * Copyright (c) 2018 Philipp Elvin Friedhoff on 16.11.18 19:49
 *
 */

import ch.dkrieger.friendsystem.lib.config.Config;
import ch.dkrieger.friendsystem.lib.player.Friend;
import ch.dkrieger.friendsystem.lib.player.FriendPlayer;
import ch.dkrieger.friendsystem.lib.storage.FriendStorage;
import ch.dkrieger.friendsystem.lib.utils.Document;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class MongoDBFriendStorage implements FriendStorage {

    private Config config;
    private MongoClient mongoClient;
    private MongoDatabase database;
    private MongoCollection friendPlayerCollection;

    public MongoDBFriendStorage(Config config) {
        this.config = config;
    }

    @Override
    public void connect() {
        this.mongoClient = new MongoClient(new MongoClientURI("mongodb"+(config.isMongoDbSrv() ? "+srv" : "")+"://"+config.getUser()+":"
                +config.getPassword()+"@"+config.getHost()+":"+config.getPort()+"/" +config.getMongoDbAuthenticationDatabase()+"?retryWrites=true"));
        this.database = this.mongoClient.getDatabase(config.getDatabase());
        this.friendPlayerCollection = database.getCollection("dkfriends_friendplayers");
    }

    @Override
    public void disconnect() {

    }

    @Override
    public FriendPlayer getPlayer(UUID uuid) {
        return MongoDBUtil.findFirst(friendPlayerCollection, Filters.eq("uuid", uuid), FriendPlayer.class);
    }

    @Override
    public FriendPlayer getPlayer(String name) {
        return MongoDBUtil.findFirst(friendPlayerCollection, Filters.eq("name", name), FriendPlayer.class);
    }

    @Override
    public void createPlayer(FriendPlayer player) {
        MongoDBUtil.insertOne(friendPlayerCollection, player);
    }

    @Override
    public void savePlayer(FriendPlayer player) {
        MongoDBUtil.replaceOne(friendPlayerCollection, Filters.eq("uuid", player.getUUID()), player);
    }

    @Override
    public void saveName(UUID uuid, String name) {
        MongoDBUtil.updateOne(friendPlayerCollection, "uuid", uuid, "name", name);
    }

    @Override
    public void saveColor(UUID uuid, String color) {
        MongoDBUtil.updateOne(friendPlayerCollection, "uuid", uuid, "color", color);
    }

    @Override
    public void saveGameProfile(UUID uuid, String gameProfile) {
        MongoDBUtil.updateOne(friendPlayerCollection, "uuid", uuid, "gameProfile", gameProfile);
    }

    @Override
    public void saveLastLogin(UUID uuid, long lastLogin) {
        MongoDBUtil.updateOne(friendPlayerCollection, "uuid", uuid, "lastLogin", lastLogin);
    }

    @Override
    public void saveMaxFriends(UUID uuid, int maxFriends) {
        MongoDBUtil.updateOne(friendPlayerCollection, "uuid", uuid, "maxFriends", maxFriends);
    }

    @Override
    public void saveMaxPartyPlayers(UUID uuid, int maxPartyPlayers) {
        MongoDBUtil.updateOne(friendPlayerCollection, "uuid", uuid, "maxPartyPlayers", maxPartyPlayers);
    }

    @Override
    public void saveMaxClanPlayers(UUID uuid, int maxClanPlayers) {
        MongoDBUtil.updateOne(friendPlayerCollection, "uuid", uuid, "maxClanPlayers", maxClanPlayers);
    }

    @Override
    public void saveFriends(UUID uuid, List<Friend> friends) {
        MongoDBUtil.updateOne(friendPlayerCollection, "uuid", uuid, "friends", friends);
    }

    @Override
    public void saveRequests(UUID uuid, List<Friend> requests) {
        MongoDBUtil.updateOne(friendPlayerCollection, "uuid", uuid, "requests", requests);
    }

    @Override
    public void saveSettings(UUID uuid, FriendPlayer.Settings settings) {
        MongoDBUtil.updateOne(friendPlayerCollection, "uuid", uuid, "settings", settings);
    }

    @Override
    public void saveProperties(UUID uuid, Document properties) {
        MongoDBUtil.updateOne(friendPlayerCollection, "uuid", uuid, "properties", properties);
    }

    @Override
    public void saveStatus(UUID uuid, FriendPlayer.Status status) {
        MongoDBUtil.updateOne(friendPlayerCollection, "uuid", uuid, "status", status);
    }

    @Override
    public void saveHiderStatus(UUID uuid, FriendPlayer.HiderStatus hiderStatus) {
        MongoDBUtil.updateOne(friendPlayerCollection, "uuid", uuid, "hiderStatus", hiderStatus);
    }
}