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
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

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
    public boolean connect() {

        String uri = "mongodb"+(config.hasMongoDbSrv()?"+srv":"")+"://";
        if(config.hasMongoDbAuthentication()) uri += config.getUser()+":"+config.getPassword()+"@";
        uri += config.getHost()+"/";
        if(config.hasMongoDbAuthentication()) uri += config.getMongoDbAuthenticationDatabase();
        uri += "?retryWrites=true&connectTimeoutMS=500&socketTimeoutMS=500";
        //(login.hasSSL()) uri+= "&ssl=true";

        this.mongoClient = new MongoClient(new MongoClientURI(uri));
        System.out.println(uri);
        this.database = this.mongoClient.getDatabase(config.getDatabase());
        this.friendPlayerCollection = database.getCollection("DKFriends_players");
        return true;
    }

    @Override
    public void disconnect() {

    }
    @Override
    public boolean isConnected() {
        return false;
    }

    @Override
    public FriendPlayer getPlayer(UUID uuid) {
        return MongoDBUtil.findFirst(friendPlayerCollection, Filters.eq("uuid",uuid.toString()), FriendPlayer.class);
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
        MongoDBUtil.replaceOne(friendPlayerCollection, Filters.eq("uuid", player.getUUID().toString()), player);
    }

    @Override
    public void saveName(UUID uuid, String name) {
        MongoDBUtil.updateOne(friendPlayerCollection, "uuid", uuid.toString(), "name", name);
    }

    @Override
    public void saveColor(UUID uuid, String color) {
        MongoDBUtil.updateOne(friendPlayerCollection, "uuid", uuid.toString(), "color", color);
    }

    @Override
    public void saveGameProfile(UUID uuid, String gameProfile) {
        MongoDBUtil.updateOne(friendPlayerCollection, "uuid", uuid.toString(), "gameProfile", gameProfile);
    }

    @Override
    public void saveLastLogin(UUID uuid, long lastLogin) {
        MongoDBUtil.updateOne(friendPlayerCollection, "uuid", uuid.toString(), "lastLogin", lastLogin);
    }

    @Override
    public void saveMaxFriends(UUID uuid, int maxFriends) {
        MongoDBUtil.updateOne(friendPlayerCollection, "uuid", uuid.toString(), "maxFriends", maxFriends);
    }

    @Override
    public void saveMaxPartyPlayers(UUID uuid, int maxPartyPlayers) {
        MongoDBUtil.updateOne(friendPlayerCollection, "uuid", uuid.toString(), "maxPartyPlayers", maxPartyPlayers);
    }

    @Override
    public void saveMaxClanPlayers(UUID uuid, int maxClanPlayers) {
        MongoDBUtil.updateOne(friendPlayerCollection, "uuid", uuid.toString(), "maxClanPlayers", maxClanPlayers);
    }

    @Override
    public void saveFriends(UUID uuid, List<Friend> friends) {
        MongoDBUtil.updateOne(friendPlayerCollection, "uuid", uuid.toString(), "friends", friends);
    }

    @Override
    public void saveRequests(UUID uuid, List<Friend> requests) {
        MongoDBUtil.updateOne(friendPlayerCollection, "uuid", uuid.toString(), "requests", requests);
    }

    @Override
    public void saveFriendsAndRequests(UUID uuid, List<Friend> friends, List<Friend> requests) {
        MongoDBUtil.updateOne(friendPlayerCollection, "uuid", uuid.toString(), "friends", friends);
        MongoDBUtil.updateOne(friendPlayerCollection, "uuid", uuid.toString(), "requests", requests);
    }

    @Override
    public void saveSettings(UUID uuid, FriendPlayer.Settings settings) {
        MongoDBUtil.updateOne(friendPlayerCollection, "uuid", uuid.toString(), "settings", settings);
    }

    @Override
    public void saveProperties(UUID uuid, Document properties) {
        MongoDBUtil.updateOne(friendPlayerCollection, "uuid", uuid.toString(), "properties", properties);
    }

    @Override
    public void saveStatus(UUID uuid, FriendPlayer.Status status) {
        MongoDBUtil.updateOne(friendPlayerCollection, "uuid", uuid.toString(), "status", status);
    }

    @Override
    public void saveHiderStatus(UUID uuid, FriendPlayer.HiderStatus hiderStatus) {
        MongoDBUtil.updateOne(friendPlayerCollection, "uuid", uuid.toString(), "hiderStatus", hiderStatus);
    }
}