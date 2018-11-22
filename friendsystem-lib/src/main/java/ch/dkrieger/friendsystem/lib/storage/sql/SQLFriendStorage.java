package ch.dkrieger.friendsystem.lib.storage.sql;

/*
 *
 *  * Copyright (c) 2018 Philipp Elvin Friedhoff on 17.11.18 20:24
 *
 */

import ch.dkrieger.friendsystem.lib.Messages;
import ch.dkrieger.friendsystem.lib.config.Config;
import ch.dkrieger.friendsystem.lib.player.Friend;
import ch.dkrieger.friendsystem.lib.player.FriendPlayer;
import ch.dkrieger.friendsystem.lib.storage.FriendStorage;
import ch.dkrieger.friendsystem.lib.storage.sql.query.SelectQuery;
import ch.dkrieger.friendsystem.lib.storage.sql.table.Table;
import ch.dkrieger.friendsystem.lib.utils.Document;
import ch.dkrieger.friendsystem.lib.utils.GeneralUtil;
import com.google.gson.reflect.TypeToken;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public abstract class SQLFriendStorage implements FriendStorage {

    private Config config;
    private Connection connection;
    private Table friendPlayerTable;

    public SQLFriendStorage(Config config) {
        this.config = config;
    }
    @Override
    public boolean isConnected() {
        return connection != null;
    }

    public Connection getConnection() {
        return connection;
    }

    public Table getFriendPlayerTable() {
        return friendPlayerTable;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean connect() {
        if(!isConnected()){
            loadDriver();
            System.out.println(Messages.SYSTEM_PREFIX+"connecting to SQL server at "+config.getHost()+":"+config.getPort());
            try {
                connect(config);
                this.friendPlayerTable = new Table(this, "DKFriends_players");
                friendPlayerTable.create()
                        .create("`uuid` varchar(32) NOT NULL")
                        .create("`name` varchar(32) NOT NULL")
                        .create("`color` TEXT NOT NULL")
                        .create("`gameProfile` LONGTEXT")
                        .create("`firstLogin` varchar(20) NOT NULL")
                        .create("`lastLogin` varchar(20) NOT NULL")
                        .create("`maxFriends` int NOT NULL")
                        .create("`maxPartyPlayers` int NOT NULL")
                        .create("`maxClanPlayers` int maxClanPlayers NOT NULL")
                        .create("`hiderStatus` LONGTEXT NOT NULL")
                        .create("`status` LONGTEXT NOT NULL")
                        .create("`settings` LONGTEXT NOT NULL")
                        .create("`friends` LONGTEXT NOT NULL")
                        .create("`requests` LONGTEXT NOT NULL")
                        .create("`properties` LONGTEXT NOT NULL")
                        .execute();
                System.out.println(Messages.SYSTEM_PREFIX+"successful connected to SQL server at "+config.getHost()+":"+config.getPort());
            }catch (SQLException exception) {
                System.out.println(Messages.SYSTEM_PREFIX+"Could not connect to SQL server at "+config.getHost()+":"+config.getPort());
                System.out.println(Messages.SYSTEM_PREFIX+"Error: "+exception.getMessage());
                System.out.println(Messages.SYSTEM_PREFIX+"Check your login data in the config.");
                connection = null;
                return false;
            }
        }
        return true;
    }
    @Override
    public void disconnect() {
        if(isConnected()){
            try {
                connection.close();
                connection = null;
                System.out.println(Messages.SYSTEM_PREFIX+"successful disconnected from MySQLFriendStorage server at "+this.config.getHost()+":"+this.config.getPort());
            }catch (SQLException exception) {
                connection = null;
            }
        }
    }

    @Override
    public FriendPlayer getPlayer(UUID uuid) {
        return getPlayer("uuid", uuid.toString());
    }

    @Override
    public FriendPlayer getPlayer(String name) {
        return getPlayer("name", name);
    }

    private FriendPlayer getPlayer(String identifier, Object identifierObject) {
        try {
            SelectQuery query = this.friendPlayerTable.select().where(identifier,identifierObject);
            ResultSet result = query.execute();
            if(result == null) return null;
            try {
                while (result.next()) {
                    return new FriendPlayer(UUID.fromString(result.getString("uuid")),
                            result.getString("name"),
                            result.getString("color"),
                            result.getString("gameProfile"),
                            result.getLong("firstLogin"),
                            result.getLong("lastLogin"),
                            result.getInt("maxFriends"),
                            result.getInt("maxPartyPlayers"),
                            result.getInt("maxClanPlayers"),
                            GeneralUtil.GSON_NOT_PRETTY.fromJson(result.getString("hiderStatus"), FriendPlayer.HiderStatus.class),
                            GeneralUtil.GSON_NOT_PRETTY.fromJson(result.getString("status"), FriendPlayer.Status.class),
                            GeneralUtil.GSON_NOT_PRETTY.fromJson(result.getString("settings"), FriendPlayer.Settings.class),
                            GeneralUtil.GSON_NOT_PRETTY.fromJson(result.getString("friends"), new TypeToken<List<Friend>>(){}.getType()),
                            GeneralUtil.GSON_NOT_PRETTY.fromJson(result.getString("requests"), new TypeToken<List<Friend>>(){}.getType()),
                            GeneralUtil.GSON_NOT_PRETTY.fromJson(result.getString("properties"), Document.class)
                    );
                }
            }finally {
                query.close();
                result.close();
            }
        }catch (SQLException exception){
            exception.printStackTrace();
        }
        return null;
    }

    @Override
    public void createPlayer(FriendPlayer player) {
        this.friendPlayerTable.insert()
                .insert("uuid")
                .insert("name")
                .insert("color")
                .insert("gameProfile")
                .insert("firstLogin")
                .insert("lastLogin")
                .insert("maxFriends")
                .insert("maxPartyPlayers")
                .insert("maxClanPlayers")
                .insert("hiderStatus")
                .insert("status")
                .insert("settings")
                .insert("friends")
                .insert("requests")
                .insert("properties")
                .value(player.getUUID())
                .value(player.getName())
                .value(player.getColor())
                .value("test")
                .value(player.getFirstLogin())
                .value(player.getLastLogin())
                .value(player.getMaxFriends())
                .value(player.getMaxPartyPlayers())
                .value(player.getMaxClanPlayers())
                .value(player.getHiderStatus())
                .value(GeneralUtil.GSON_NOT_PRETTY.toJson(player.getStatus()))
                .value(GeneralUtil.GSON_NOT_PRETTY.toJson(player.getSettings()))
                .value(GeneralUtil.GSON_NOT_PRETTY.toJson(player.getFriends(), new TypeToken<List<Friend>>(){}.getType()))
                .value(GeneralUtil.GSON_NOT_PRETTY.toJson(player.getRequests(), new TypeToken<List<Friend>>(){}.getType()))
                .value(player.getProperties().toJson())
                .execute();
    }

    @Override
    public void savePlayer(FriendPlayer player) {
        this.friendPlayerTable.update()
                .set("name", player.getName())
                .set("color", player.getColor())
                .set("gameProfile", player.getGameProfile())
                .set("lastLogin", player.getLastLogin())
                .set("maxFriends", player.getMaxFriends())
                .set("maxPartyPlayers", player.getMaxPartyPlayers())
                .set("maxClanPlayers", player.getMaxClanPlayers())
                .set("hiderStatus", GeneralUtil.GSON_NOT_PRETTY.toJson(player.getHiderStatus()))
                .set("status", GeneralUtil.GSON_NOT_PRETTY.toJson(player.getStatus()))
                .set("settings", GeneralUtil.GSON_NOT_PRETTY.toJson(player.getSettings()))
                .set("friends", GeneralUtil.GSON_NOT_PRETTY.toJson(player.getFriends(), new TypeToken<List<Friend>>(){}.getType()))
                .set("requests", GeneralUtil.GSON_NOT_PRETTY.toJson(player.getRequests(), new TypeToken<List<Friend>>(){}.getType()))
                .set("properties", GeneralUtil.GSON_NOT_PRETTY.toJson(player.getProperties()))
                .where("uuid", player.getUUID().toString()).execute();

    }

    @Override
    public void saveName(UUID uuid, String name) {
        updateSettings(uuid, "name", name);
    }

    @Override
    public void saveColor(UUID uuid, String color) {
        updateSettings(uuid, "color", color);
    }

    @Override
    public void saveGameProfile(UUID uuid, String gameProfile) {
        if(gameProfile == null)return;
        updateSettings(uuid, "gameProfile", gameProfile);
    }

    @Override
    public void saveLastLogin(UUID uuid, long lastLogin) {
        updateSettings(uuid, "lastLogin", lastLogin);
    }

    @Override
    public void saveMaxFriends(UUID uuid, int maxFriends) {
        updateSettings(uuid, "maxFriends", maxFriends);
    }

    @Override
    public void saveMaxPartyPlayers(UUID uuid, int maxPartyPlayers) {
        updateSettings(uuid, "maxPartyPlayers", maxPartyPlayers);
    }

    @Override
    public void saveMaxClanPlayers(UUID uuid, int maxClanPlayers) {
        updateSettings(uuid, "maxClanPlayers", maxClanPlayers);
    }

    @Override
    public void saveFriends(UUID uuid, List<Friend> friends) {
        updateSettings(uuid, "friends", GeneralUtil.GSON_NOT_PRETTY.toJson(friends, new TypeToken<List<Friend>>(){}.getType()));
    }

    @Override
    public void saveRequests(UUID uuid, List<Friend> requests) {
        updateSettings(uuid, "requests", GeneralUtil.GSON_NOT_PRETTY.toJson(requests, new TypeToken<List<Friend>>(){}.getType()));
    }

    @Override
    public void saveFriendsAndRequests(UUID uuid, List<Friend> friends, List<Friend> requests) {
        this.friendPlayerTable.update()
                .set("friends", GeneralUtil.GSON_NOT_PRETTY.toJson(friends, new TypeToken<List<Friend>>(){}.getType()))
                .set("requests", GeneralUtil.GSON_NOT_PRETTY.toJson(requests, new TypeToken<List<Friend>>(){}.getType()))
                .where("uuid", uuid.toString()).execute();
    }

    @Override
    public void saveSettings(UUID uuid, FriendPlayer.Settings settings) {
        updateSettings(uuid, "settings", GeneralUtil.GSON_NOT_PRETTY.toJson(settings));
    }

    @Override
    public void saveProperties(UUID uuid, Document properties) {
        updateSettings(uuid, "properties", GeneralUtil.GSON_NOT_PRETTY.toJson(properties));
    }

    @Override
    public void saveStatus(UUID uuid, FriendPlayer.Status status) {
        updateSettings(uuid, "status", GeneralUtil.GSON_NOT_PRETTY.toJson(status));
    }

    @Override
    public void saveHiderStatus(UUID uuid, FriendPlayer.HiderStatus hiderStatus) {
        updateSettings(uuid, "hiderStatus", GeneralUtil.GSON_NOT_PRETTY.toJson(hiderStatus));
    }

    @Override
    public void saveInformations(UUID uuid, String color, long lastLogin, String gameProfile) {
        this.friendPlayerTable.update()
                .set("color", color)
                .set("lastLogin", lastLogin)
                .set("gameProfile", gameProfile)
                .where("uuid", uuid.toString()).execute();
    }

    private void updateSettings(UUID uuid, String identifier, Object value) {
        this.friendPlayerTable.update().set(identifier, value).where("uuid", uuid.toString()).execute();
    }

    public abstract void connect(Config config) throws SQLException;
    public abstract void loadDriver();
}