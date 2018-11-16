package ch.dkrieger.friendsystem.lib;

import ch.dkrieger.friendsystem.lib.command.FriendCommandManager;
import ch.dkrieger.friendsystem.lib.storage.FriendStorage;

/*
 *
 *  * Copyright (c) 2018 Davide Wietlisbach on 16.11.18 17:47
 *
 */

public class FriendSystem {

    private static FriendSystem instance;
    private final FriendCommandManager commandManager;
    private FriendStorage playerStorage;

    public FriendCommandManager getCommandManager() {
        return this.commandManager;
    }
    public static FriendSystem getInstance() {
        return instance;
    }
}
