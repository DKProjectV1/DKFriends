package ch.dkrieger.friendsystem.lib;

/*
 *
 *  * Copyright (c) 2018 Davide Wietlisbach on 16.11.18 19:26
 *
 */

public class Messages {

    public Messages(String systemname) {
        SYSTEM_NAME = systemname;
        SYSTEM_PREFIX = "["+systemname+"] ";
    }
    public static String SYSTEM_NAME;
    public static String SYSTEM_PREFIX;

    public static String PREFIX_FRIEND;
    public static String PREFIX_PARTY;

    public static String ERROR;
    public static String NOPERMISSIONS;

    public static String HELP_HEADER_ONE;
    public static String HELP_HEADER_MORE;

    public static String PLAYER_NOT_FOUND;
    public static String PLAYER_NOT_ONLINE;

    public static String PLAYER_ALREADY_REQUEST;
    public static String PLAYER_ALREADY_FRIENDS;

    public static String PLAYER_REACHED_MAX_FRIENDS_SELF;
    public static String PLAYER_REACHED_MAX_FRIENDS_OTHER;

    public static String PLAYER_DEFAULT_STATUS;

    public static String PLAYER_SELF_INTEGRATION;


    public static String PLAYER_DEFAULT_COLOR;

    public static String PLAYER_REQUEST_SENDED;
    public static String PLAYER_REQUEST_RECEIVED_MESSAGE;
    public static String PLAYER_REQUEST_RECEIVED_ACCEPT;
    public static String PLAYER_REQUEST_RECEIVED_DENY;


}
