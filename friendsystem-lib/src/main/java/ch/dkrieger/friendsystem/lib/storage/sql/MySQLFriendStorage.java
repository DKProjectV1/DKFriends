package ch.dkrieger.friendsystem.lib.storage.sql;

import ch.dkrieger.friendsystem.lib.Messages;
import ch.dkrieger.friendsystem.lib.config.Config;
import ch.dkrieger.friendsystem.lib.player.Friend;
import ch.dkrieger.friendsystem.lib.player.FriendPlayer;
import ch.dkrieger.friendsystem.lib.storage.FriendStorage;
import ch.dkrieger.friendsystem.lib.storage.sql.query.SelectQuery;
import ch.dkrieger.friendsystem.lib.storage.sql.table.Table;
import ch.dkrieger.friendsystem.lib.utils.Document;
import ch.dkrieger.friendsystem.lib.utils.GeneralUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

/*
 *
 *  * Copyright (c) 2018 Davide Wietlisbach on 16.11.18 20:57
 *
 */

public class MySQLFriendStorage implements Runnable, FriendStorage {

    private Config config;
	private Connection connection;
	private Table friendPlayerTable;

	public MySQLFriendStorage(Config config) {
	    this.config = config;
	}

    public boolean isConnected() {
        return connection != null;
    }

    public Connection getConnection() {
        return connection;
    }

    public Table getFriendPlayerTable() {
        return friendPlayerTable;
    }

    public void loadDriver() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch(ClassNotFoundException exception) {
			System.out.println(Messages.SYSTEM_PREFIX +"Could not load MySQLFriendStorage driver.");
		}
	}

	public void reconnect() {
		disconnect();
		connect();
	}

	@Override
	public void run() {
		reconnect();
	}

	@Override
	public void connect() {
        if(!isConnected()){
            loadDriver();
            System.out.println(Messages.SYSTEM_PREFIX+"connecting to MySQLFriendStorage server at "+config.getHost()+":"+config.getPort());
            try {
                this.connection = DriverManager.getConnection("jdbc:mysql://"+config.getHost()+":"+config.getPort()+"/"+config.getDatabase()+
                        "?autoReconnect=true&allowMultiQueries=true&reconnectAtTxEnd=true",config.getUser(), config.getPassword());
                this.friendPlayerTable = new Table(this, "dkfriends_friendplayers");
                friendPlayerTable.create()
                        .create("`id` int NOT NULL AUTO_INCREMENT")
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
                System.out.println(Messages.SYSTEM_PREFIX+"successful connected to MySQLFriendStorage server at "+config.getHost()+":"+config.getPort());

            }catch (SQLException exception) {
                System.out.println(Messages.SYSTEM_PREFIX+"Could not connect to MySQLFriendStorage server at "+config.getHost()+":"+config.getPort());
                System.out.println(Messages.SYSTEM_PREFIX+"Error: "+exception.getMessage());
                System.out.println(Messages.SYSTEM_PREFIX+"Check your login datas in the config.");
                connection = null;
            }
        }
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
                            GeneralUtil.GSON.fromJson(result.getString("hiderStatus"), FriendPlayer.HiderStatus.class),
                            GeneralUtil.GSON.fromJson(result.getString("status"), FriendPlayer.Status.class),
                            GeneralUtil.GSON.fromJson(result.getString("settings"), FriendPlayer.Settings.class),
                            GeneralUtil.GSON.fromJson(result.getString("friends"), List.class),
                            GeneralUtil.GSON.fromJson(result.getString("requests"), List.class),
                            GeneralUtil.GSON.fromJson(result.getString("properties"), Document.class)
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
        this.friendPlayerTable.insert().insert("uuid").insert("name").insert("friendplayer").value(player.getUUID().toString()).value(player.getName()).value(GeneralUtil.GSON_NOT_PRETTY.toJson(player)).execute();
	}

	@Override
	public void savePlayer(FriendPlayer player) {
	    this.friendPlayerTable.update().set("friendplayer", player).where("uuid", player.getUUID().toString()).execute();
	}

	@Override
	public void saveFriends(UUID uuid, List<Friend> friends) {
        //this.friendPlayerTable.update().set("friendplayer", player).where("uuid", player.getUUID().toString()).execute();
	}

	@Override
	public void saveRequests(UUID uuid, List<Friend> requests) {

	}

	@Override
	public void saveSettings(UUID uuid, FriendPlayer.Settings settings) {

	}

	@Override
	public void saveProperties(UUID uuid, Document properties) {

	}

	@Override
	public void saveStatus(UUID uuid, FriendPlayer.Status status) {

	}

	@Override
	public void saveHiderStatus(UUID uuid, FriendPlayer.HiderStatus hiderStatus) {

	}
}
