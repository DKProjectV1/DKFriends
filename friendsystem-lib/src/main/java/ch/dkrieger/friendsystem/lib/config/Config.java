package ch.dkrieger.friendsystem.lib.config;

/*
 *
 *  * Copyright (c) 2018 Philipp Elvin Friedhoff on 16.11.18 19:36
 *
 */

import ch.dkrieger.friendsystem.lib.DKFriendsPlatform;
import ch.dkrieger.friendsystem.lib.storage.StorageType;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Config extends SimpleConfig {

    private final DKFriendsPlatform platform;
    private StorageType storageType;
    private String host, port, user, password, database, mongoDbAuthenticationDatabase, dataFolder;
    private boolean mongoDbSrv, mongoDbAuthentication, commandFriendEnabled, bungeeCord;
    private SimpleDateFormat dateFormat;

    public Config(DKFriendsPlatform platform) {
        super(new File(platform.getFolder(),"config.yml"));
        this.platform = platform;
    }

    public StorageType getStorageType() {
        return storageType;
    }

    public String getHost() {
        return host;
    }

    public String getPort() {
        return port;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public String getDatabase() {
        return database;
    }
    public SimpleDateFormat getDateFormat(){
        return this.dateFormat;
    }

    public String getMongoDbAuthenticationDatabase() {
        return mongoDbAuthenticationDatabase;
    }
    public boolean hasMongoDbAuthentication() {
        return mongoDbAuthentication;
    }
    public boolean hasMongoDbSrv() {
        return mongoDbSrv;
    }
    public boolean isCommandFriendEnabled() {
        return commandFriendEnabled;
    }

    public boolean isBungeeCord() {
        return bungeeCord;
    }

    public String getDataFolder() {
        return dataFolder;
    }

    @Override
    public void onLoad() {
        this.dataFolder = getStringValue("storage.folder");
        this.storageType = StorageType.parse(getStringValue("storage.type"));
        this.host = getStringValue("storage.host");
        this.port = getStringValue("storage.port");
        this.user = getStringValue("storage.user");
        this.password = getStringValue("storage.password");
        this.database = getStringValue("storage.database");
        this.mongoDbAuthentication = getBooleanValue("storage.mongodb.mongodbauthentication");
        this.mongoDbAuthenticationDatabase = getStringValue("storage.mongodb.authenticationDatabase");
        this.mongoDbSrv = getBooleanValue("storage.mongodb.srv");

        this.commandFriendEnabled = getBooleanValue("command.friend.enabled");

        this.dateFormat = new SimpleDateFormat(getStringValue("date.format"));
        this.bungeeCord = getBooleanValue("bungeecord");
    }
    @Override
    public void registerDefaults() {
        addValue("bungeecord",false);
        addValue("storage.folder",this.platform.getFolder()+"/data/");
        addValue("storage.type", StorageType.MONGODB.toString().toUpperCase());//change to json
        addValue("storage.host", "127.0.0.1");
        addValue("storage.port", "3306"); //mongo 27018
        addValue("storage.user", "root");
        addValue("storage.password", "password");
        addValue("storage.database", "DKFriends");
        addValue("storage.mongodb.mongodbauthentication",true);
        addValue("storage.mongodb.authenticationDatabase", "admin");
        addValue("storage.mongodb.srv", false);

        addValue("date.format","yyyy.MM.dd HH:mm");

        addValue("color.liveupdate",false);
        addValue("color.default","&8");
        addValue("color.console","&4");
        List<String> colors = new LinkedList<>();
        colors.add("dkbans.color.admin:&4");
        colors.add("dkbans.color.developer:&b");
        colors.add("dkbans.color.mod:&c");
        colors.add("dkbans.color.supporter:&9");
        colors.add("dkbans.color.builder:&3");
        colors.add("dkbans.color.youtuber:&5");
        colors.add("dkbans.color.premium:&6");
        addValue("color.colors",colors);

        //Friend commands
        addValue("command.friend.enabled",true);
        addValue("command.friend.name","friend");
        addValue("command.friend.description", "Manage your friends");
        addValue("command.friend.permission", "none");
        addValue("command.friend.aliases", Arrays.asList("f","freund"));

        //Subcommands

        addValue("command.friend.acceptall.enabled",true);
        addValue("command.friend.acceptall.name","acceptall");
        addValue("command.friend.acceptall.description", "Accept all friend requests");
        addValue("command.friend.acceptall.permission", "none");
        addValue("command.friend.acceptall.aliases", Arrays.asList());

        addValue("command.friend.accept.enabled",true);
        addValue("command.friend.accept.name","accept");
        addValue("command.friend.accept.description", "Accept a friend request");
        addValue("command.friend.accept.permission", "none");
        addValue("command.friend.accept.aliases", Arrays.asList());

        addValue("command.friend.add.enabled",true);
        addValue("command.friend.add.name","add");
        addValue("command.friend.add.description", "Add a friend");
        addValue("command.friend.add.permission", "none");
        addValue("command.friend.add.aliases", Arrays.asList());

        addValue("command.friend.clear.enabled",true);
        addValue("command.friend.clear.name","clear");
        addValue("command.friend.clear.description", "clear your friend list");
        addValue("command.friend.clear.permission", "none");
        addValue("command.friend.clear.aliases", Arrays.asList());

        addValue("command.friend.denyall.enabled",true);
        addValue("command.friend.denyall.name","denyall");
        addValue("command.friend.denyall.description", "Deny all friend requests");
        addValue("command.friend.denyall.permission", "none");
        addValue("command.friend.denyall.aliases", Arrays.asList());

        addValue("command.friend.deny.enabled",true);
        addValue("command.friend.deny.name","deny");
        addValue("command.friend.deny.description", "Deny a friend requests");
        addValue("command.friend.deny.permission", "none");
        addValue("command.friend.deny.aliases", Arrays.asList());

        addValue("command.friend.favorite.enabled",true);
        addValue("command.friend.favorite.name","favorite");
        addValue("command.friend.favorite.description", "Add a friend as favorite");
        addValue("command.friend.favorite.permission", "none");
        addValue("command.friend.favorite.aliases", Arrays.asList("favorite","favourite","darling","fav","honey","ducky"));

        addValue("command.friend.jump.enabled",true);
        addValue("command.friend.jump.name","jump");
        addValue("command.friend.jump.description", "Jump to a friend");
        addValue("command.friend.jump.permission", "none");
        addValue("command.friend.jump.aliases", Arrays.asList());

        addValue("command.friend.list.enabled",true);
        addValue("command.friend.list.name","list");
        addValue("command.friend.list.description", "List all friends");
        addValue("command.friend.list.permission", "none");
        addValue("command.friend.list.aliases", Arrays.asList());

        addValue("command.friend.message.enabled",true);
        addValue("command.friend.message.name","message");
        addValue("command.friend.message.description", "Send a message");
        addValue("command.friend.message.permission", "none");
        addValue("command.friend.message.aliases", Arrays.asList("msg","nachricht"));

        addValue("command.friend.respond.enabled",true);
        addValue("command.friend.respond.name","respond");
        addValue("command.friend.respond.description", "Respond to a message");
        addValue("command.friend.respond.permission", "none");
        addValue("command.friend.respond.aliases",Arrays.asList("r"));

        addValue("command.friend.remove.enabled",true);
        addValue("command.friend.remove.name","remove");
        addValue("command.friend.remove.description", "Remove a friend");
        addValue("command.friend.remove.permission", "none");
        addValue("command.friend.remove.aliases", Arrays.asList("delete"));

        addValue("command.friend.requestlist.enabled",true);
        addValue("command.friend.requestlist.name","requestlist");
        addValue("command.friend.requestlist.description", "List all requests");
        addValue("command.friend.requestlist.permission", "none");
        addValue("command.friend.requestlist.aliases", Arrays.asList());

        addValue("command.friend.toggle.enabled",true);
        addValue("command.friend.toggle.name","toggle");
        addValue("command.friend.toggle.description", "Toggle settings");
        addValue("command.friend.toggle.permission", "none");
        addValue("command.friend.toggle.aliases", Arrays.asList());

        addValue("command.friendmessage.enabled",true);
        addValue("command.friendmessage.name","message");
        addValue("command.friendmessage.description", "Send a message to a friend");
        addValue("command.friendmessage.permission", "none");
        addValue("command.friendmessage.aliases", Arrays.asList("msg","nachricht"));

        addValue("command.friendrespond.enabled",true);
        addValue("command.friendrespond.name","respond");
        addValue("command.friendrespond.description", "Respond to a message");
        addValue("command.friendrespond.permission", "none");
        addValue("command.friendrespond.aliases",Arrays.asList("r"));

        //Party commands
        addValue("command.party.enabled",true);
        addValue("command.party.name","party");
        addValue("command.party.description", "Manage your party");
        addValue("command.party.permission", "none");
        addValue("command.party.aliases", Arrays.asList("p"));

        addValue("command.party.accept.enabled",true);
        addValue("command.party.accept.name","accept");
        addValue("command.party.accept.description", "Accept a party invite");
        addValue("command.party.accept.permission", "none");
        addValue("command.party.accept.aliases", Arrays.asList());

        addValue("command.party.demote.enabled",true);
        addValue("command.party.demote.name","demote");
        addValue("command.party.demote.description", "Demote a party member");
        addValue("command.party.demote.permission", "none");
        addValue("command.party.demote.aliases", Arrays.asList());

        addValue("command.party.deny.enabled",true);
        addValue("command.party.deny.name","deny");
        addValue("command.party.deny.description", "Deny a party invite");
        addValue("command.party.deny.permission", "none");
        addValue("command.party.deny.aliases", Arrays.asList());

        addValue("command.party.info.enabled",true);
        addValue("command.party.info.name","info");
        addValue("command.party.info.description", "Get informations about your party");
        addValue("command.party.info.permission", "none");
        addValue("command.party.info.aliases", Arrays.asList("list"));

        addValue("command.party.invite.enabled",true);
        addValue("command.party.invite.name","invite");
        addValue("command.party.invite.description", "Invite a player to your party");
        addValue("command.party.invite.permission", "none");
        addValue("command.party.invite.aliases", Arrays.asList());

        addValue("command.party.jump.enabled",true);
        addValue("command.party.jump.name","jump");
        addValue("command.party.jump.description", "Jump to your party leader");
        addValue("command.party.jump.permission", "none");
        addValue("command.party.jump.aliases", Arrays.asList());

        addValue("command.party.kick.enabled",true);
        addValue("command.party.kick.name","kick");
        addValue("command.party.kick.description", "Kick a party member");
        addValue("command.party.kick.permission", "none");
        addValue("command.party.kick.aliases", Arrays.asList());

        addValue("command.party.leave.enabled",true);
        addValue("command.party.leave.name","leave");
        addValue("command.party.leave.description", "Leave your party");
        addValue("command.party.leave.permission", "none");
        addValue("command.party.leave.aliases", Arrays.asList());

        addValue("command.party.message.enabled",true);
        addValue("command.party.message.name","message");
        addValue("command.party.message.description", "Send a message ");
        addValue("command.party.message.permission", "none");
        addValue("command.party.message.aliases", Arrays.asList("msg","nachricht","chat"));

        addValue("command.party.join.enabled",true);
        addValue("command.party.join.name","join");
        addValue("command.party.join.description", "Join a public party");
        addValue("command.party.join.permission", "none");
        addValue("command.party.join.aliases", Arrays.asList());

        addValue("command.party.publiclist.enabled",true);
        addValue("command.party.publiclist.name","publiclist");
        addValue("command.party.publiclist.description", "List all public parties");
        addValue("command.party.publiclist.permission", "none");
        addValue("command.party.publiclist.aliases", Arrays.asList());

        addValue("command.party.random.enabled",true);
        addValue("command.party.random.name","random");
        addValue("command.party.random.description", "Join a random party");
        addValue("command.party.random.permission", "none");
        addValue("command.party.random.aliases", Arrays.asList());

        addValue("command.party.promote.enabled",true);
        addValue("command.party.promote.name","promote");
        addValue("command.party.promote.description", "Promote a party member");
        addValue("command.party.promote.permission", "none");
        addValue("command.party.promote.aliases", Arrays.asList());

        addValue("command.party.public.enabled",true);
        addValue("command.party.public.name","public");
        addValue("command.party.public.description", "Make a party public");
        addValue("command.party.public.permission", "none");
        addValue("command.party.public.aliases", Arrays.asList());

        addValue("command.party.private.enabled",true);
        addValue("command.party.private.name","private");
        addValue("command.party.private.description", "Kick a party private");
        addValue("command.party.private.permission", "none");
        addValue("command.party.private.aliases", Arrays.asList());

        addValue("command.party.ban.enabled",true);
        addValue("command.party.ban.name","ban");
        addValue("command.party.ban.description", "Ban a member");
        addValue("command.party.ban.permission", "none");
        addValue("command.party.ban.aliases", Arrays.asList());

        addValue("command.party.unban.enabled",true);
        addValue("command.party.unban.name","unban");
        addValue("command.party.unban.description", "Unban a member");
        addValue("command.party.unban.permission", "none");
        addValue("command.party.unban.aliases", Arrays.asList());

        addValue("command.partymessage.enabled",true);
        addValue("command.partymessage.name","pmessage");
        addValue("command.partymessage.description", "Send a message to your party members");
        addValue("command.partymessage.permission", "none");
        addValue("command.partymessage.aliases", Arrays.asList("pmsg","pnachricht","pchat","partychat"));

        addValue("join.requestinfo.enabled", true);
        addValue("join.friendinfo.enabled", true);
    }
}