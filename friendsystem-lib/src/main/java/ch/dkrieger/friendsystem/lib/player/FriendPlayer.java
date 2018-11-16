package ch.dkrieger.friendsystem.lib.player;

import ch.dkrieger.friendsystem.lib.utils.Document;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/*
 *
 *  * Copyright (c) 2018 Davide Wietlisbach on 16.11.18 17:47
 *
 */

public class FriendPlayer {

    private UUID uuid;
    private String name, color, gameProfile;
    private long firstLogin, lastLogin;
    private HiderStatus hiderStatus;
    private Status status;
    private Settings settings;
    private List<Friend> friends, requests;
    private Document properties;

    /*


     */

    public class Settings {

        private Map<String,Boolean> friendRequests, partyRequests, playerHider;
        private boolean jumpEnabled, notifyEnabled, messageEnabled, partyTagsEnalbed, clanTagsEnabled;
        private short design;

    }
    public class Status {

        private String message;
        private long lastUpdate;



    }
    public enum HiderStatus {

        ALL(),VIP(),NOBODY();

    }
}
