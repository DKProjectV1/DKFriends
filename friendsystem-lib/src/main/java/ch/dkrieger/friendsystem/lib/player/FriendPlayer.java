package ch.dkrieger.friendsystem.lib.player;

import ch.dkrieger.friendsystem.lib.FriendSystem;
import ch.dkrieger.friendsystem.lib.Messages;
import ch.dkrieger.friendsystem.lib.utils.Document;
import net.md_5.bungee.api.chat.TextComponent;

import java.util.*;
import java.util.List;

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

    public String getColoredName(){
        return this.color+this.name;
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
    public List<Friend> getSortedFriends(){
        List<Friend> online = new ArrayList<>();
        List<Friend> online_favorite = new ArrayList<>();
        List<Friend> offline = new ArrayList<>();
        List<Friend> offline_favorite = new ArrayList<>();

        Iterator<Friend> iterator = this.friends.iterator();
        Friend friend = null;
        while(iterator.hasNext() && (friend= iterator.next()) != null){
            if(friend.isOnline()){
                if(friend.isFavorite()) online_favorite.add(friend);
                else online.add(friend);
            }else{
                if(friend.isFavorite()) offline_favorite.add(friend);
                else offline.add(friend);
            }
        }
        online_favorite.addAll(online);
        online_favorite.addAll(offline_favorite);
        online_favorite.addAll(offline);

        return online_favorite;
    }
    public List<Friend> getOnlineFriends(){
        List<Friend> online = new ArrayList<>();
        Iterator<Friend> iterator = this.friends.iterator();
        Friend friend = null;
        while(iterator.hasNext() && (friend= iterator.next()) != null) if(friend.isOnline()) online.add(friend);
        return online;
    }
    public List<Friend> getRequests() {
        return requests;
    }

    public Document getProperties() {
        return properties;
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

    public OnlineFriendPlayer getOnlinePlayer(){
        return FriendSystem.getInstance().getPlayerManager().getOnlinePlayer(this.uuid);
    }
    public void sendMessage(String message){
        OnlineFriendPlayer online = getOnlinePlayer();
        if(online != null) getOnlinePlayer().sendMessage(message);
    }
    public void sendMessage(TextComponent component){
        OnlineFriendPlayer online = getOnlinePlayer();
        if(online != null) getOnlinePlayer().sendMessage(component);
    }


    public Friend getRequest(FriendPlayer player){
        return getRequest(player.getUUID());
    }
    public Friend getRequest(UUID uuid){
        Iterator<Friend> iterator = this.requests.iterator();
        Friend request = null;
        while(iterator.hasNext() && (request= iterator.next()) != null) if(request.getUUID().equals(uuid)) return request;
        return null;
    }
    public void addRequest(FriendPlayer player){
        this.requests.add(new Friend(player.getUUID(),System.currentTimeMillis(),false));
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
        Friend request = null;
        while(iterator.hasNext() && (request= iterator.next()) != null) if(request.getUUID().equals(uuid)) return request;
        return null;
    }
    public void addFriend(FriendPlayer player){
        addFriend(player,false);
    }
    public void addFriend(FriendPlayer player, boolean noRepeat){
        Friend friend = getRequest(player);
        if(friend == null) friend = new Friend(player.getUUID(),System.currentTimeMillis(),false);
        else friend.setTimeStamp(System.currentTimeMillis());
        this.friends.add(friend);
        this.requests.remove(friend);
        if(!noRepeat) player.addFriend(this,true);
        FriendSystem.getInstance().getStorage().saveFriendsAndRequests(this.uuid,this.friends,this.requests);
    }
    public void removeFriend(FriendPlayer player){
        removeFriend(player,false);
    }
    public void removeFriend(FriendPlayer player, boolean noRepeat){
        this.friends.remove(getFriend(player.getUUID()));
        if(!noRepeat) player.removeFriend(this,true);
        FriendSystem.getInstance().getStorage().saveFriends(this.uuid,this.friends);
    }
    public boolean isFriend(FriendPlayer player){
        return isFriend(player.getUUID());
    }
    public boolean isFriend(UUID uuid){
        return getFriend(uuid) != null;
    }
    public boolean isFavorite(FriendPlayer player){
        return isFavorite(player.getUUID());
    }
    public boolean isFavorite(UUID uuid){
        Friend friend = getFriend(uuid);
        return friend != null && friend.isFavorite();
    }
    public void setFavorite(FriendPlayer player, boolean favorite){
        Friend friend = getFriend(player.getUUID());
        if(friend != null) friend.setFavorite(favorite);
        FriendSystem.getInstance().getStorage().saveFriends(this.uuid,this.friends);
    }
    public void acceptAll(){
        Iterator<Friend> iterator = new ArrayList<>(this.requests).iterator();
        this.requests.clear();
        Friend request = null;
        while(iterator.hasNext() && (request= iterator.next()) != null){
            this.friends.add(new Friend(request.getUUID(),System.currentTimeMillis(),false));
            request.getFriendPlayer().addFriend(this,true);
        }
        FriendSystem.getInstance().getStorage().saveFriendsAndRequests(this.uuid,this.friends,this.requests);
    }
    public void denyAll(){
        this.requests.clear();
        FriendSystem.getInstance().getStorage().saveRequests(this.uuid,this.requests);
    }
    public void clearFriends(){
        Iterator<Friend> iterator = new ArrayList<>(this.friends).iterator();
        this.friends.clear();
        Friend friend = null;
        while(iterator.hasNext() && (friend= iterator.next()) != null) friend.getFriendPlayer().removeFriend(this,true);
        FriendSystem.getInstance().getStorage().saveFriends(this.uuid,this.friends);
    }
    public void updateInfos(String name,String color,String gameProfile){
        updateInfos(name,color,this.gameProfile);
    }
    public void updateInfos(String name,String color){
        this.name = name;
        this.color = color;
        this.lastLogin = System.currentTimeMillis();
        //save infos to storage  and gameProfile  (name/color(lastLogin/gameProfile)
    }
    public static class Settings {

        private Map<String,Boolean> friendRequests, partyRequests, playerHider;
        private boolean jumpEnabled, notifyEnabled, messageEnabled, partyTagsEnabled, clanTagsEnabled;
        private short design;

        public Settings() {
            this.friendRequests = new LinkedHashMap<>();
            this.partyRequests = new LinkedHashMap<>();
            this.playerHider = new LinkedHashMap<>();
            this.jumpEnabled = true;
            this.notifyEnabled = true;
            this.messageEnabled = true;
            this.partyTagsEnabled = true;
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

        public boolean isPartyTagsEnabled() {
            return partyTagsEnabled;
        }

        public boolean isClanTagsEnabled() {
            return clanTagsEnabled;
        }

        public short getDesign() {
            return design;
        }


        public void setJumpEnabled(boolean jumpEnabled) {
            this.jumpEnabled = jumpEnabled;
        }

        public void setNotifyEnabled(boolean notifyEnabled) {
            this.notifyEnabled = notifyEnabled;
        }

        public void setMessageEnabled(boolean messageEnabled) {
            this.messageEnabled = messageEnabled;
        }

        public void setPartyTagsEnabled(boolean partyTagsEnabled) {
            this.partyTagsEnabled = partyTagsEnabled;
        }

        public void setClanTagsEnabled(boolean clanTagsEnabled) {
            this.clanTagsEnabled = clanTagsEnabled;
        }

        public void setDesign(short design) {
            this.design = design;
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