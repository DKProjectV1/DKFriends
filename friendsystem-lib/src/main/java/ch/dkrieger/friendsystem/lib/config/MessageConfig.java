package ch.dkrieger.friendsystem.lib.config;

import ch.dkrieger.friendsystem.lib.DKFriendsPlatform;
import ch.dkrieger.friendsystem.lib.Messages;
import ch.dkrieger.friendsystem.lib.storage.StorageType;

import java.io.File;

/*
 *
 *  * Copyright (c) 2018 Davide Wietlisbach on 16.11.18 22:30
 *
 */

public class MessageConfig extends SimpleConfig {

    private final DKFriendsPlatform platform;

    public MessageConfig(DKFriendsPlatform platform) {
        super(new File(platform.getFolder(),"messages.yml"));
        this.platform = platform;
    }

    @Override
    public void onLoad() {
        Messages.PREFIX_FRIEND = get("prefix.friend");
        Messages.ERROR = get("error");
        Messages.NOPERMISSIONS = get("nopermissions");

        Messages.HELP_HEADER_ONE = get("help.header.one");
        Messages.HELP_HEADER_MORE = get("help.header.more");

        Messages.PLAYER_NOT_FOUND = get("player.not.found");
        Messages.PLAYER_NOT_ONLINE = get("player.not.online");

        Messages.PLAYER_ALREADY_FRIENDS = get("player.already.friends");
        Messages.PLAYER_ALREADY_REQUEST = get("player.already.request");

        Messages.PLAYER_REACHED_MAX_FRIENDS_SELF = get("player.reached.max.friends.self");
        Messages.PLAYER_REACHED_MAX_FRIENDS_OTHER = get("player.reached.max.friends.other");

        Messages.PLAYER_SELF_INTEGRATION = get("player.self.integration");

        Messages.PLAYER_REQUEST_SENDED = get("player.request.sended");
        Messages.PLAYER_REQUEST_RECEIVED_MESSAGE = get("player.request.received.message");
        Messages.PLAYER_REQUEST_RECEIVED_ACCEPT= get("player.request.received.accept");
        Messages.PLAYER_REQUEST_RECEIVED_DENY = get("player.request.received.deny");
    }
    @Override
    public void registerDefaults() {
        addValue("prefix.friend", "&8» &9DKFriends &8| &f");
        addValue("prefix.party", "&8» &9DKParties &8| &f");

        addValue("error", "[prefix]&cAn error occurred, please contact a network administrator");
        addValue("nopermissions", "[prefix]&4You don't have permission to execute this command.");

        addValue("help.header.one", "[prefix]&7Page [page]/[maxPage]");
        addValue("help.header.more", "[prefix]&7Page [page]/[maxPage] &8| &7More help with &e/[command] [nexPage]");

        addValue("player.not.found", "[prefix]&cThe player &e[player] was not found.");
        addValue("prefix.not.online", "[prefix]&cThe player &e[player] is not online.");

        addValue("player.already.friends", "&e[player] is already your friend.");
        addValue("player.already.request", "[prefix]&cYou have already sent a request to &e[player].");

        addValue("player.reached.max.friends.self","[prefix]&cYou reached the maximum amount of &e[max] &cfriends.");
        addValue("player.reached.max.friends.other","[prefix]&e[player] &creached the maximum amount of friends.");

        addValue("player.self.integration", "[prefix]&cYou can't integrate with your self.");

        addValue("player.request.sended", "[prefix]&7You send a friend request to &e[player].");
        addValue("player.request.received.message", "[prefix]&7You received a request from &e[player].\n[prefix]&7Click here: [accept] [deny]");
        addValue("player.request.received.accept", "&8[&aAccept&8]");
        addValue("player.request.received.deny", "&8[&cDeny&8]");
    }
    public String get(String path){
        return getMessageValue(path);
    }
}