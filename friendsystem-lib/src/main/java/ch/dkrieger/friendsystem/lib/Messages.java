package ch.dkrieger.friendsystem.lib;

/*
 *
 *  * Copyright (c) 2018 Davide Wietlisbach on 16.11.18 19:26
 *
 */

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;

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
    public static String PAGENOTFOUND;

    public static String HELP_HEADER_ONE;
    public static String HELP_HEADER_MORE;
    public static String HELP_HEADER_LINE;

    public static String SERVER_ALREADY;
    public static String SERVER_NOTFOUND;

    public static String WORLD_ALREADY;
    public static String WORLD_NOTFOUND;

    public static String PLAYER_NOT_FOUND;
    public static String PLAYER_NOT_ONLINE;
    public static String PLAYER_NOT_FRIENDS;
    public static String PLAYER_NOT_REQUEST;

    public static String PLAYER_NO_FRIENDS;
    public static String PLAYER_NO_REQUESTS;

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
    public static String PLAYER_REQUEST_LIST_HEADER;
    public static String PLAYER_REQUEST_LIST_LIST;

    public static String PLAYER_REQUEST_DENIED_SELF;
    public static String PLAYER_REQUEST_DENIED_OTHER;

    public static String PLAYER_REQUEST_ACCEPTED_SELF;
    public static String PLAYER_REQUEST_ACCEPTED_OTHER;

    public static String PLAYER_REQUEST_OPEN_MESSAGE;
    public static String PLAYER_REQUEST_OPEN_SINGULAR;
    public static String PLAYER_REQUEST_OPEN_PLURAL;

    public static String PLAYER_REMOVED_SELF;
    public static String PLAYER_REMOVED_OTHER;

    public static String PLAYER_LIST_HEADER;
    public static String PLAYER_LIST_ONLINE;
    public static String PLAYER_LIST_OFFLINE;
    public static String PLAYER_LIST_SYMBOL_NORMAL;
    public static String PLAYER_LIST_SYMBOL_FAVORITE;

    public static String PLAYER_FAVORITE_ADD;
    public static String PLAYER_FAVORITE_REMOVE;

    public static String PLAYER_MESSAGE_FORMAT;
    public static String PLAYER_MESSAGE_COLOR;
    public static String PLAYER_MESSAGE_NOT_ALLOWED;
    public static String PLAYER_MESSAGE_NOT_ENABLED;
    public static String PLAYER_MESSAGE_NO_RESPOND;
    public static String PLAYER_MESSAGE_NO;

    public static String PLAYER_NOTIFY_ONLINE;
    public static String PLAYER_NOTIFY_OFFLINE;

    public static String PLAYER_ONLINE_ONE;
    public static String PLAYER_ONLINE_TWO;
    public static String PLAYER_ONLINE_THREE;
    public static String PLAYER_ONLINE_MORE;

    public static String PLAYER_JUMP;
    public static String PLAYER_JUMP_NOTALLOWED;

    public static String PLAYER_SETTING_JUMP_ENABLE;
    public static String PLAYER_SETTING_JUMP_DISABLE;

    public static String PLAYER_SETTING_MESSAGE_ENABLE;
    public static String PLAYER_SETTING_MESSAGE_DISABLE;

    public static String PLAYER_SETTING_NOTIFY_ENABLE;
    public static String PLAYER_SETTING_NOTIFY_DISABLE;
    public static String PLAYER_SETTING_HELP;

    public static String PLAYER_PARTY_NOT_LEADER;
    public static String PLAYER_PARTY_NOT_PARTY;
    public static String PLAYER_PARTY_NOT_ALLOWED_INVITE;
    public static String PLAYER_PARTY_NOT_ALLOWED_KICK;
    public static String PLAYER_PARTY_NOT_ALLOWED_BAN;
    public static String PLAYER_PARTY_NOT_ALLOWED_UNBAN;
    public static String PLAYER_PARTY_NOT_ALLOWED_JOIN;
    public static String PLAYER_PARTY_NO_PARTY_SELF;
    public static String PLAYER_PARTY_NO_PARTY_OTHER;

    public static String PLAYER_PARTY_REQUEST_SENDED;
    public static String PLAYER_PARTY_REQUEST_RECEIVED;
    public static String PLAYER_PARTY_REQUEST_ALREADY;
    public static String PLAYER_PARTY_REQUEST_NOT;

    public static String PLAYER_PARTY_ALREADY_INVITED;

    public static String PLAYER_PARTY_ALREADY_OWN;
    public static String PLAYER_PARTY_ALREADY_MY;
    public static String PLAYER_PARTY_ALREADY_OTHER;

    public static String PLAYER_PARTY_ALREADY_BANNED;
    public static String PLAYER_PARTY_ALREADY_UNBANED;

    public static String PLAYER_PARTY_JOINED;
    public static String PLAYER_PARTY_DENIED_SELF;
    public static String PLAYER_PARTY_DENIED_OTHER;
    public static String PLAYER_PARTY_LEAVED;
    public static String PLAYER_PARTY_JUMPED;
    public static String PLAYER_PARTY_KICKED;
    public static String PLAYER_PARTY_BANNED;
    public static String PLAYER_PARTY_UNBANNED;
    public static String PLAYER_PARTY_DELETED;
    public static String PLAYER_PARTY_CONNECTED;

    public static String PLAYER_PARTY_INFO_HEADER;
    public static String PLAYER_PARTY_INFO_CREATED;
    public static String PLAYER_PARTY_INFO_PUBLIC_TEXT;
    public static String PLAYER_PARTY_INFO_PUBLIC_ENABLED;
    public static String PLAYER_PARTY_INFO_PUBLIC_DISABLED;
    public static String PLAYER_PARTY_INFO_MEMBER_HEADER;
    public static String PLAYER_PARTY_INFO_MEMBER_LIST;
    public static String PLAYER_PARTY_INFO_MEMBER_SYMBOL_NORMAL;
    public static String PLAYER_PARTY_INFO_MEMBER_SYMBOL_LEADER;
    public static String PLAYER_PARTY_INFO_MEMBER_SYMBOL_MODERATOR;

    public static String PLAYER_PARTY_NEW_LEADER;

    public static String PLAYER_PARTY_MODERATOR_PROMOTED;
    public static String PLAYER_PARTY_MODERATOR_DEMOTED;
    public static String PLAYER_PARTY_MODERATOR_NOT;

    public static String PLAYER_PARTY_MESSAGE_FORMAT;
    public static String PLAYER_PARTY_MESSAGE_COLOR;

    public static String PLAYER_PARTY_PUBLIC_NOW;
    public static String PLAYER_PARTY_PUBLIC_ALREADY;
    public static String PLAYER_PARTY_PUBLIC_NO;
    public static String PLAYER_PARTY_PUBLIC_NOT;
    public static String PLAYER_PARTY_PUBLIC_LIST_HEADER;
    public static String PLAYER_PARTY_PUBLIC_LIST_LIST;

    public static String PLAYER_PARTY_PRIVATE_NOW;
    public static String PLAYER_PARTY_PRIVATE_ALREADY;

    public static TextComponent replaceAcceptDeny(String text,String acceptCommand,String denyCommand){
        TextComponent message = new TextComponent();
        if(text.contains("[accept]")){
            int index = text.lastIndexOf("[accept]");
            TextComponent component = new TextComponent(Messages.PLAYER_REQUEST_RECEIVED_ACCEPT);
            component.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,acceptCommand));
            message.addExtra(new TextComponent(text.substring(0,index)));
            message.addExtra(component);
            text = text.substring(index).replace("[accept]","");
        }
        if(text.contains("[deny]")){
            int index = text.lastIndexOf("[deny]");
            TextComponent component = new TextComponent(Messages.PLAYER_REQUEST_RECEIVED_DENY);
            component.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,denyCommand));
            message.addExtra(new TextComponent(text.substring(0,index)));
            message.addExtra(component);
            text = text.substring(index).replace("[deny]","");
        }
        if(text.length() > 0) message.addExtra(text);
        return message;
    }
}
