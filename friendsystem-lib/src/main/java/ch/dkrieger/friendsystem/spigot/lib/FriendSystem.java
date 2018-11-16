package ch.dkrieger.friendsystem.spigot.lib;

import ch.dkrieger.friendsystem.spigot.lib.command.FriendCommandManager;

/*
 *
 *  * Copyright (c) 2018 Davide Wietlisbach on 16.11.18 17:46
 *
 */

public class FriendSystem {

    private static FriendSystem instance;
    private final FriendCommandManager commandManager;

    public FriendCommandManager getCommandManager() {
        return this.commandManager;
    }
    public static FriendSystem getInstance() {
        return instance;
    }
}
