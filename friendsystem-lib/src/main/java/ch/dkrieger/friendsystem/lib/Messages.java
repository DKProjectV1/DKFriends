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

    public static String ERROR;

    public static String PLAYER_NOT_FOUND;

    public static String PLAYER_HAS_ALREADY_REQUEST;

    public static String PLAYER_ALREADY_FRIENDS;

    public static String PLAYER_DEFAULT_STATUS;

    public static String PLAYER_REQUEST_SENDED;
    public static String PLAYER_REQUEST_RECEIVED;

    public static String PLAYER_DEFAULT_COLOR;

    public static String PLAYER_MAX_FRIENDS_REACHED;
    public static String PLAYER_MAX_FRIENDS_REACHED_FRIEND;


}
