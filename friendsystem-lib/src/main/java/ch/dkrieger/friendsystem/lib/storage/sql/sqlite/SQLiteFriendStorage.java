package ch.dkrieger.friendsystem.lib.storage.sql.sqlite;

/*
 *
 *  * Copyright (c) 2018 Philipp Elvin Friedhoff on 17.11.18 20:38
 *
 */

import ch.dkrieger.friendsystem.lib.config.Config;
import ch.dkrieger.friendsystem.lib.storage.sql.SQLFriendStorage;

import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLiteFriendStorage extends SQLFriendStorage {

    public SQLiteFriendStorage(Config config) {
        super(config);
    }

    @Override
    public void connect(Config config) throws SQLException {
        setConnection(DriverManager.getConnection("jdbc:sqlite:players.db"));

    }

    @Override
    public void loadDriver() {

    }
}