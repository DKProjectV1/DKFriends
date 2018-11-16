package ch.dkrieger.friendsystem.lib.database;

/*
 *
 *  * Copyright (c) 2018 Philipp Elvin Friedhoff on 16.11.18 17:04
 *
 */

public interface Database {

    void connect(DatabaseConfig config);
    void disconnect();

}