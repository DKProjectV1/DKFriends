package ch.dkrieger.friendsystem.lib.player;

import ch.dkrieger.friendsystem.lib.FriendSystem;
import ch.dkrieger.friendsystem.lib.Messages;
import ch.dkrieger.friendsystem.lib.utils.Document;

import java.util.*;

/*
 *
 *  * Copyright (c) 2018 Davide Wietlisbach on 16.11.18 17:47
 *
 */

public class FriendPlayer {

    private UUID uuid;
    private String name, color, gameProfile;
    private long firstLogin, lastLogin;
    private int maxFriends, maxPartyPlayers, maxClanPlayers;
    private HiderStatus hiderStatus;
    private Status status;
    private Settings settings;
    private List<Friend> friends, requests;
    private Document properties;

    public FriendPlayer(UUID uuid, String name, String color, String gameProfile) {
        this(uuid,name,color,gameProfile,System.currentTimeMillis(),System.currentTimeMillis(),50,10,10,HiderStatus.NOBODY,new Status(),new Settings(),new ArrayList<>(),new ArrayList<>(),new Document());
    }
    public FriendPlayer(UUID uuid, String name, String color, String gameProfile, long firstLogin, long lastLogin, int maxFriends, int maxPartyPlayers, int maxClanPlayers, HiderStatus hiderStatus, Status status, Settings settings, List<Friend> friends, List<Friend> requests, Document properties) {
        this.uuid = uuid;
        this.name = name;
        this.color = color;
        this.gameProfile = gameProfile;
        this.firstLogin = firstLogin;
        this.lastLogin = lastLogin;
        this.maxFriends = maxFriends;
        this.maxPartyPlayers = maxPartyPlayers;
        this.maxClanPlayers = maxClanPlayers;
        this.hiderStatus = hiderStatus;
        this.status = status;
        this.settings = settings;
        this.friends = friends;
        this.requests = requests;
        this.properties = properties;
    }

    public UUID getUUID() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public String getGameProfile() {
        return gameProfile;
    }

    public long getFirstLogin() {
        return firstLogin;
    }

    public long getLastLogin() {
        return lastLogin;
    }

    public int getMaxFriends() {
        return maxFriends;
    }

    public int getMaxPartyPlayers() {
        return maxPartyPlayers;
    }

    public int getMaxClanPlayers() {
        return maxClanPlayers;
    }

    public HiderStatus getHiderStatus() {
        return hiderStatus;
    }

    public Status getStatus() {
        return status;
    }

    public Settings getSettings() {
        return settings;
    }

    public List<Friend> getFriends() {
        return friends;
    }

    public List<Friend> getRequests() {
        return requests;
    }

    public Document getProperties() {
        return properties;
    }

    public Friend getRequest(FriendPlayer player){
        return getRequest(player.getUUID());
    }
    public Friend getRequest(UUID uuid){
        Iterator<Friend> iterator = this.requests.iterator();
        Friend request = null;
        while((request= iterator.next()) != null) if(request.getUUID().equals(uuid)) return request;
        return null;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setGameProfile(String gameProfile) {
        this.gameProfile = gameProfile;
    }

    public void setLastLogin(long lastLogin) {
        this.lastLogin = lastLogin;
    }

    public void setMaxFriends(int maxFriends) {
        this.maxFriends = maxFriends;
    }

    public void setMaxPartyPlayers(int maxPartyPlayers) {
        this.maxPartyPlayers = maxPartyPlayers;
    }

    public void setMaxClanPlayers(int maxClanPlayers) {
        this.maxClanPlayers = maxClanPlayers;
    }

    public void setHiderStatus(HiderStatus hiderStatus) {
        this.hiderStatus = hiderStatus;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setSettings(Settings settings) {
        this.settings = settings;
    }

    public void setFriends(List<Friend> friends) {
        this.friends = friends;
    }

    public void setRequests(List<Friend> requests) {
        this.requests = requests;
    }

    public void setProperties(Document properties) {
        this.properties = properties;
    }

    public void addRequest(FriendPlayer player){
        addRequest(player,false);
    }
    public void addRequest(FriendPlayer player, boolean noRepeat){
        this.requests.add(new Friend(player.getUUID(),System.currentTimeMillis(),false));
        if(!noRepeat) player.addRequest(this,true);
        FriendSystem.getInstance().getStorage().saveRequests(this.uuid,this.requests);
    }
    public void removeRequest(FriendPlayer player){
        removeRequest(player.getUUID());
    }
    public void removeRequest(UUID uuid){
        this.requests.remove(getRequest(uuid));
        FriendSystem.getInstance().getStorage().saveRequests(this.uuid,this.requests);
    }
    public boolean hasRequest(FriendPlayer player){
        return hasRequest(player.getUUID());
    }
    public boolean hasRequest(UUID uuid){
        return getRequest(uuid) != null;
    }
    public Friend getFriend(FriendPlayer player){
        return getFriend(player.getUUID());
    }
    public Friend getFriend(UUID uuid){
        Iterator<Friend> iterator = this.friends.iterator();
        Friend friend = null;
        while((friend = iterator.next()) != null) if(friend.getUUID().equals(uuid)) return friend;
        return null;
    }
    public void addFriend(FriendPlayer player){
        addFriend(player,false);
    }
    public void addFriend(FriendPlayer player, boolean noRepeat){
        Friend friend = getRequest(player);
        if(friend == null) friend = new Friend(player.getUUID(),System.currentTimeMillis(),false);
        else this.friends.add(friend);
        this.requests.remove(friend);
        if(!noRepeat) player.addFriend(this,true);
        FriendSystem.getInstance().getStorage().saveFriendsAndRequests(this.uuid,this.friends,this.requests);
    }
    public boolean isFriend(FriendPlayer player){
        return isFriend(player.getUUID());
    }
    public boolean isFriend(UUID uuid){
        return getFriend(uuid) != null;
    }
    public static class Settings {

        private Map<String,Boolean> friendRequests, partyRequests, playerHider;
        private boolean jumpEnabled, notifyEnabled, messageEnabled, partyTagsEnalbed, clanTagsEnabled;
        private short design;

        public Settings() {
            this.friendRequests = new LinkedHashMap<>();
            this.partyRequests = new LinkedHashMap<>();
            this.playerHider = new LinkedHashMap<>();
            this.jumpEnabled = true;
            this.notifyEnabled = true;
            this.messageEnabled = true;
            this.partyTagsEnalbed = true;
            this.clanTagsEnabled = true;
            this.design = -1;
        }
        public Map<String, Boolean> getFriendRequests() {
            return friendRequests;
        }

        public Map<String, Boolean> getPartyRequests() {
            return partyRequests;
        }

        public Map<String, Boolean> getPlayerHider() {
            return playerHider;
        }

        public boolean isJumpEnabled() {
            return jumpEnabled;
        }

        public boolean isNotifyEnabled() {
            return notifyEnabled;
        }

        public boolean isMessageEnabled() {
            return messageEnabled;
        }

        public boolean isPartyTagsEnalbed() {
            return partyTagsEnalbed;
        }

        public boolean isClanTagsEnabled() {
            return clanTagsEnabled;
        }

        public short getDesign() {
            return design;
        }
    }
    public static class Status {

        private String message;
        private long lastUpdate;

        public Status(){
            this(Messages.PLAYER_DEFAULT_STATUS,System.currentTimeMillis());
        }
        public Status(String message, long lastUpdate) {
            this.message = message;
            this.lastUpdate = lastUpdate;
        }
        public String getMessage() {
            return message;
        }
        public long getLastUpdate() {
            return lastUpdate;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public void setLastUpdate(long lastUpdate) {
            this.lastUpdate = lastUpdate;
        }
    }
    public enum HiderStatus {

        ALL(),VIP(),NOBODY();

    }
}
