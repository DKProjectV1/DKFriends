package ch.dkrieger.friendsystem.lib.storage.json;

/*
 *
 *  * Copyright (c) 2018 Philipp Elvin Friedhoff on 17.11.18 14:50
 *
 */

import ch.dkrieger.friendsystem.lib.config.Config;
import ch.dkrieger.friendsystem.lib.player.Friend;
import ch.dkrieger.friendsystem.lib.player.FriendPlayer;
import ch.dkrieger.friendsystem.lib.storage.FriendStorage;
import ch.dkrieger.friendsystem.lib.utils.Document;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.util.*;

public class JsonFriendStorage implements FriendStorage {

    private File file;
    private Document data;

    public JsonFriendStorage(Config config) {
        this.file = new File(config.getDataFolder(), "dkfriends_friendplayers.json");
        if(file.exists() && file.isFile()) this.data = Document.loadData(file);
        else this.data = new Document();
        this.data.appendDefault("friendplayers", new ArrayList<>());
        if(!(file.exists() && file.isFile())){
            file.delete();
            this.data.saveData(file);
        }
    }

    @Override
    public boolean connect() {
        return false;
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
        List<FriendPlayer> friendPlayers = data.getObject("friendplayers", new TypeToken<List<FriendPlayer>>(){}.getType());
        Iterator<FriendPlayer> iterator = friendPlayers.iterator();
        FriendPlayer player;
        while((player = iterator.next()) != null) if(player.getUUID().equals(uuid)) return player;
        this.data.saveData(file);
        return null;
    }

    @Override
    public FriendPlayer getPlayer(String name) {
        List<FriendPlayer> friendPlayers = data.getObject("friendplayers", new TypeToken<List<FriendPlayer>>(){}.getType());
        Iterator<FriendPlayer> iterator = friendPlayers.iterator();
        FriendPlayer player;
        while((player = iterator.next()) != null) if(player.getName().equalsIgnoreCase(name)) return player;
        this.data.saveData(file);
        return null;
    }

    @Override
    public void createPlayer(FriendPlayer player) {
        List<FriendPlayer> friendPlayers = data.getObject("friendplayers", new TypeToken<List<FriendPlayer>>(){}.getType());
        friendPlayers.add(player);
        this.data.saveData(file);
    }

    @Override
    public void savePlayer(FriendPlayer friendPlayer) {
        List<FriendPlayer> friendPlayers = data.getObject("friendplayers", new TypeToken<List<FriendPlayer>>(){}.getType());
        Iterator<FriendPlayer> iterator = friendPlayers.iterator();
        FriendPlayer player;
        while((player = iterator.next()) != null) {
            if(player.getUUID().equals(friendPlayer.getUUID())) {
                friendPlayers.remove(player);
                friendPlayers.add(friendPlayer);
            }
        }
        this.data.saveData(file);
    }

    @Override
    public void saveName(UUID uuid, String name) {
        FriendPlayer player = getPlayer(uuid);
        player.setName(name);
        savePlayer(player);
    }

    @Override
    public void saveColor(UUID uuid, String color) {
        FriendPlayer player = getPlayer(uuid);
        player.setColor(color);
        savePlayer(player);
    }

    @Override
    public void saveGameProfile(UUID uuid, String gameProfile) {
        FriendPlayer player = getPlayer(uuid);
        player.setGameProfile(gameProfile);
        savePlayer(player);
    }

    @Override
    public void saveLastLogin(UUID uuid, long lastLogin) {
        FriendPlayer player = getPlayer(uuid);
        player.setLastLogin(lastLogin);
        savePlayer(player);
    }

    @Override
    public void saveMaxFriends(UUID uuid, int maxFriends) {
        FriendPlayer player = getPlayer(uuid);
        player.setMaxFriends(maxFriends);
        savePlayer(player);
    }

    @Override
    public void saveMaxPartyPlayers(UUID uuid, int maxPartyPlayers) {
        FriendPlayer player = getPlayer(uuid);
        player.setMaxPartyPlayers(maxPartyPlayers);
        savePlayer(player);
    }

    @Override
    public void saveMaxClanPlayers(UUID uuid, int maxClanPlayers) {
        FriendPlayer player = getPlayer(uuid);
        player.setMaxClanPlayers(maxClanPlayers);
        savePlayer(player);
    }

    @Override
    public void saveHiderStatus(UUID uuid, FriendPlayer.HiderStatus hiderStatus) {
        FriendPlayer player = getPlayer(uuid);
        player.setHiderStatus(hiderStatus);
        savePlayer(player);
    }

    @Override
    public void saveStatus(UUID uuid, FriendPlayer.Status status) {
        FriendPlayer player = getPlayer(uuid);
        player.setStatus(status);
        savePlayer(player);
    }

    @Override
    public void saveSettings(UUID uuid, FriendPlayer.Settings settings) {
        FriendPlayer player = getPlayer(uuid);
        player.setSettings(settings);
        savePlayer(player);
    }

    @Override
    public void saveFriends(UUID uuid, List<Friend> friends) {
        FriendPlayer player = getPlayer(uuid);
        player.setFriends(friends);
        savePlayer(player);
    }

    @Override
    public void saveRequests(UUID uuid, List<Friend> requests) {
        FriendPlayer player = getPlayer(uuid);
        player.setRequests(requests);
        savePlayer(player);
    }

    @Override
    public void saveFriendsAndRequests(UUID uuid, List<Friend> friends, List<Friend> requests) {
        FriendPlayer player = getPlayer(uuid);
        player.setFriends(friends);
        player.setRequests(requests);
        savePlayer(player);
    }

    @Override
    public void saveProperties(UUID uuid, Document properties) {
        FriendPlayer player = getPlayer(uuid);
        player.setProperties(properties);
        savePlayer(player);
    }
}