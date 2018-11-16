package ch.dkrieger.friendsystem.lib;

import ch.dkrieger.friendsystem.lib.command.FriendCommandManager;
import ch.dkrieger.friendsystem.lib.player.FriendPlayerManager;
import ch.dkrieger.friendsystem.lib.storage.FriendStorage;

/*
 *
 *  * Copyright (c) 2018 Davide Wietlisbach on 16.11.18 17:47
 *
 */

public class FriendSystem {

    private static FriendSystem instance;
    private final FriendCommandManager commandManager;
    private FriendPlayerManager playerManager;
    private FriendStorage storage;

    public FriendCommandManager getCommandManager() {
        return this.commandManager;
    }
    public FriendPlayerManager getPlayerManager() {
        return this.playerManager;
    }
    public FriendStorage getStorage() {
        return storage;
    }

    public void registerCommands(){
        
    }



    public static FriendSystem getInstance() {
        return instance;
    }
}
