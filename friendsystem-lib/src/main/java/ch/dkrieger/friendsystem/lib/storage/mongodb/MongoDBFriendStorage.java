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

    private MongoClient mongoClient;
    private MongoDatabase database;
    private MongoCollection friendPlayerCollection;

    @Override
    public void connect(Config config) {
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
    public void saveFriends(UUID uuid, List<Friend> friends) {
        MongoDBUtil.updateOne(friendPlayerCollection, new org.bson.Document("uuid", uuid), new org.bson.Document("$set", new org.bson.Document("friends", friends)));
    }

    @Override
    public void saveRequests(UUID uuid, List<Friend> requests) {

    }

    @Override
    public void saveSettings(UUID uuid, FriendPlayer.Settings settings) {

    }

    @Override
    public void saveProperties(UUID uuid, Document properties) {

    }

    @Override
    public void saveStatus(UUID uuid, FriendPlayer.Status status) {

    }

    @Override
    public void saveHiderStatus(UUID uuid, FriendPlayer.HiderStatus hiderStatus) {

    }
}