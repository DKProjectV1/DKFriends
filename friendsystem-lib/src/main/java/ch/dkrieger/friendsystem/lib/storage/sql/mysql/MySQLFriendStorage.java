package ch.dkrieger.friendsystem.lib.storage.sql.mysql;

import ch.dkrieger.friendsystem.lib.Messages;
import ch.dkrieger.friendsystem.lib.config.Config;
import ch.dkrieger.friendsystem.lib.player.Friend;
import ch.dkrieger.friendsystem.lib.player.FriendPlayer;
import ch.dkrieger.friendsystem.lib.storage.FriendStorage;
import ch.dkrieger.friendsystem.lib.storage.sql.SQLFriendStorage;
import ch.dkrieger.friendsystem.lib.storage.sql.query.SelectQuery;
import ch.dkrieger.friendsystem.lib.storage.sql.table.Table;
import ch.dkrieger.friendsystem.lib.utils.Document;
import ch.dkrieger.friendsystem.lib.utils.GeneralUtil;
import com.google.gson.reflect.TypeToken;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

/*
 *
 *  * Copyright (c) 2018 Philipp Elvin Friedhoff on 17.11.18 20:23
 *
 */

public class MySQLFriendStorage extends SQLFriendStorage implements Runnable {

	public MySQLFriendStorage(Config config) {
        super(config);
	}

    @Override
    public void loadDriver() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch(ClassNotFoundException exception) {
			System.out.println(Messages.SYSTEM_PREFIX +"Could not load MySQLFriendStorage driver.");
		}
	}

    @Override
    public void connect(Config config) throws SQLException {
	    setConnection(DriverManager.getConnection("jdbc:mysql://"+config.getHost()+":"+config.getPort()+"/"+config.getDatabase()+
                "?autoReconnect=true&allowMultiQueries=true&reconnectAtTxEnd=true",config.getUser(), config.getPassword()));
    }

	public void reconnect() {
		disconnect();
		connect();
	}

	@Override
	public void run() {
		reconnect();
	}
}