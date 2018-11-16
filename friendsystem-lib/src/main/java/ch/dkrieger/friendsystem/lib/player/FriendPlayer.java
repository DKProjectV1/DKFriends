package ch.dkrieger.friendsystem.lib.player;

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
        this(uuid,name,color,gameProfile,System.currentTimeMillis(),System.currentTimeMillis(),HiderStatus.NOBODY,new Status(),new Settings(),new LinkedList<>(),new LinkedList<>(),new Document());
    }
    public FriendPlayer(UUID uuid, String name, String color, String gameProfile, long firstLogin, long lastLogin, HiderStatus hiderStatus, Status status, Settings settings, List<Friend> friends, List<Friend> requests, Document properties) {
        this.uuid = uuid;
        this.name = name;
        this.color = color;
        this.gameProfile = gameProfile;
        this.firstLogin = firstLogin;
        this.lastLogin = lastLogin;
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
