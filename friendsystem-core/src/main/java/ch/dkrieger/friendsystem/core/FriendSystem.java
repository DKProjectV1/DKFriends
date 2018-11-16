package ch.dkrieger.friendsystem.core;

import ch.dkrieger.friendsystem.core.command.FriendCommand;
import ch.dkrieger.friendsystem.core.command.FriendCommandManager;

/*
 *
 *  * Copyright (c) 2018 Davide Wietlisbach on 16.11.18 08:35
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
