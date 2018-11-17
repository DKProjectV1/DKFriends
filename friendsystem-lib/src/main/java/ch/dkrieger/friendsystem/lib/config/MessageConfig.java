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

        Messages.SERVER_NOTFOUND = get("server.notfound");
        Messages.SERVER_ALREADY = get("server.already");

        Messages.WORLD_NOTFOUND = get("world.notfound");
        Messages.WORLD_ALREADY = get("world.already");

        Messages.PLAYER_NOT_FOUND = get("player.not.found");
        Messages.PLAYER_NOT_ONLINE = get("player.not.online");
        Messages.PLAYER_NOT_REQUEST = get("player.not.request");
        Messages.PLAYER_NOT_FRIENDS = get("player.not.friends");

        Messages.PLAYER_NO_FRIENDS = get("player.no.friends");
        Messages.PLAYER_NO_REQUESTS = get("player.no.requests");

        Messages.PLAYER_ALREADY_FRIENDS = get("player.already.friends");
        Messages.PLAYER_ALREADY_REQUEST = get("player.already.request");

        Messages.PLAYER_REACHED_MAX_FRIENDS_SELF = get("player.reached.max.friends.self");
        Messages.PLAYER_REACHED_MAX_FRIENDS_OTHER = get("player.reached.max.friends.other");

        Messages.PLAYER_SELF_INTEGRATION = get("player.self.integration");

        Messages.PLAYER_REQUEST_SENDED = get("player.request.sended");
        Messages.PLAYER_REQUEST_RECEIVED_MESSAGE = get("player.request.received.message");
        Messages.PLAYER_REQUEST_RECEIVED_ACCEPT= get("player.request.received.accept");
        Messages.PLAYER_REQUEST_RECEIVED_DENY = get("player.request.received.deny");

        Messages.PLAYER_REQUEST_DENIED_SELF = get("player.request.denied.self");
        Messages.PLAYER_REQUEST_DENIED_OTHER = get("player.request.denied.other");

        Messages.PLAYER_REQUEST_ACCEPTED_SELF = get("player.request.accepted.self");
        Messages.PLAYER_REQUEST_ACCEPTED_OTHER = get("player.request.accepted.other");

        Messages.PLAYER_REQUEST_LIST_HEADER = get("player.request.list.header");
        Messages.PLAYER_REQUEST_LIST_LIST = get("player.request.list.list");

        Messages.PLAYER_REMOVED_SELF = get("player.removed.self");
        Messages.PLAYER_REMOVED_OTHER = get("player.removed.other");

        Messages.PLAYER_REQUEST_OPEN_MESSAGE = get("player.request.open.message");
        Messages.PLAYER_REQUEST_OPEN_SINGULAR = get("player.request.open.singular");
        Messages.PLAYER_REQUEST_OPEN_PLURAL = get("player.request.open.plural");

        Messages.PLAYER_LIST_HEADER = get("player.list.header");
        Messages.PLAYER_LIST_ONLINE = get("player.list.online");
        Messages.PLAYER_LIST_OFFLINE = get("player.list.offline");
        Messages.PLAYER_LIST_SYMBOL_NORMAL = get("player.list.symbol.normal");
        Messages.PLAYER_LIST_SYMBOL_FAVORITE = get("player.list.symbol.favorite");
        
        Messages.PLAYER_FAVORITE_ADD = get("player.favorite.add");
        Messages.PLAYER_FAVORITE_REMOVE = get("player.favorite.remove");

        Messages.PLAYER_JUMP = get("player.jump");

        Messages.PLAYER_MESSAGE_FORMAT = get("player.message.format");
        Messages.PLAYER_MESSAGE_COLOR = get("player.message.color");

        Messages.PLAYER_NOTIFY_ONLINE = get("player.notify.online");
        Messages.PLAYER_NOTIFY_OFFLINE = get("player.notify.offline");

        Messages.PLAYER_ONLINE_ONE = get("player.online.one");
        Messages.PLAYER_ONLINE_TWO = get("player.online.two");
        Messages.PLAYER_ONLINE_THREE = get("player.online.three");
        Messages.PLAYER_ONLINE_MORE = get("player.online.more");
    }
    @Override
    public void registerDefaults() {
        addValue("prefix.friend", "&8» &9DKFriends &8| &f");
        addValue("prefix.party", "&8» &9DKParties &8| &f");

        addValue("error", "[prefix]&cAn error occurred, please contact a network administrator");
        addValue("nopermissions", "[prefix]&4You don't have permission to execute this command.");

        addValue("help.header.one", "[prefix]&7Page [page]/[maxPage]");
        addValue("help.header.more", "[prefix]&7Page [page]/[maxPage] &8| &7More help with &e/[command] [nexPage]");

        addValue("server.notfound", "[prefix]&7cThe server was not found.");
        addValue("server.already", "[prefix]&7c You are already connected to this server.");

        addValue("world.notfound", "[prefix]&7cThe world was not found.");
        addValue("world.already", "[prefix]&7c You are already in this world.");

        addValue("player.not.found", "[prefix]&cThe player &e[player] was not found.");
        addValue("prefix.not.online", "[prefix]&cThe player &e[player] is not online.");
        addValue("player.not.request", "[prefix]&cYou don't have a request from &e[player].");
        addValue("player.not.friends", "[prefix]&e[player] &cis not your friend.");

        addValue("player.no.friends", "[prefix]&cYou do not have any friends.");
        addValue("player.no.requests", "[prefix]&cYou do not have any requests.");

        addValue("player.already.friends", "[prefix]&e[player] &cis already your friend.");
        addValue("player.already.request", "[prefix]&e[prefix]&cYou have already sent a request to &e[player].");

        addValue("player.reached.max.friends.self","[prefix]&cYou reached the maximum amount of &e[max] &cfriends.");
        addValue("player.reached.max.friends.other","[prefix]&e[player] &creached the maximum amount of friends.");

        addValue("player.self.integration", "[prefix]&cYou can't integrate with your self.");

        addValue("player.request.sended", "[prefix]&7You send a friend request to &e[player].");
        addValue("player.request.received.message", "[prefix]&7You received a request from &e[player].\n[prefix]&7Click here: [accept] [deny]");
        addValue("player.request.received.accept", "&8[&aAccept&8]");
        addValue("player.request.received.deny", "&8[&cDeny&8]");

        addValue("player.request.denied.self", "[prefix]&7You denied the friend request from &e[player].");
        addValue("player.request.denied.other", "[prefix]&e[player] &7denied your friend request.");

        addValue("player.request.accepted.self", "[prefix]&aYou accepted the friend request from &e[player].");
        addValue("player.request.accepted.other", "[prefix]&e[player] &aaccepted your friend request.");

        addValue("player.request.list.header", "[prefix]&7Request list page [page]/[maxPages]");
        addValue("player.request.list.list", " &7- [player] [accept] [deny]");

        addValue("player.request.open.message", "[prefix]&7You have [amount] open [design]");
        addValue("player.request.open.singular", "friend request");
        addValue("player.request.open.plural", "friend requests");

        addValue("player.removed.self", "[prefix]&e[player] &7is no longer your friend.");
        addValue("player.removed.other", "[prefix]&e[player] &7is no longer your friend.");

        addValue("player.list.header", "[prefix]&7Friend list page [page]/[maxPages]");
        addValue("player.list.online", " [symbol] &e[player] &aOnline &7on [server]");
        addValue("player.list.offline", " [symbol] &e[player] &cOffline &7since [lastOnline]");
        addValue("player.list.symbol.normal", "&7-");
        addValue("player.list.symbol.favorite", "&6✦");

        addValue("player.favorite.add", "[prefix]&7You marked &e[player] &7as favorite.");
        addValue("player.favorite.remove", "[prefix]&7You removed &e[player] &7as favorite.");

        addValue("player.jump","[prefix]&7You jumped to server from &e[player]&7.");

        addValue("player.message.format","[prefix][sender] §7§l»§f [receiver]§7: §e[message]");
        addValue("player.message.color","&e");

        addValue("player.notify.online","[prefix]&e[player] &ais now online.");
        addValue("player.notify.offline","[prefix]&e[player] &cis now offline.");

        addValue("player.online.one","[prefix]&7Currently is only &e[player-1] &7online.");
        addValue("player.online.two","[prefix]&7Currently is &e[player-1] &7and &e[player-3] &7online.");
        addValue("player.online.three","[prefix]&7Currently is &e[player-1]&7, &e[player-2] &7and &e[player-3] &7online.");
        addValue("player.online.more","[prefix]&7Currently is &e[player-1]&7, &e[player-2] &7and &e[more] &7more online.");

    }
    public String get(String path){
        return getMessageValue(path);
    }
}