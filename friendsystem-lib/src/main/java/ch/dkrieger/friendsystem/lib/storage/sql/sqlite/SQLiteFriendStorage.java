package ch.dkrieger.friendsystem.lib.storage.sql.sqlite;

/*
 *
 *  * Copyright (c) 2018 Philipp Elvin Friedhoff on 17.11.18 20:38
 *
 */

import ch.dkrieger.friendsystem.lib.Messages;
import ch.dkrieger.friendsystem.lib.config.Config;
import ch.dkrieger.friendsystem.lib.storage.sql.SQLFriendStorage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLiteFriendStorage extends SQLFriendStorage {

    public SQLiteFriendStorage(Config config) {
        super(config);
    }
    @Override
    public void connect(Config config) throws SQLException {
        new File(config.getDataFolder()).mkdirs();
        try {
            new File(config.getDataFolder(),"players.db").createNewFile();
        } catch (IOException exception) {
            System.err.println("Could not create SQLite database file ("+exception.getMessage()+").");
        }
        setConnection(DriverManager.getConnection("jdbc:sqlite:"+config.getDataFolder()+"players.db"));
    }
    @Override
    public void loadDriver() {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.out.println(Messages.SYSTEM_PREFIX +"Could not load SQLiteFriendStorage driver.");
        }
    }
}