package ch.dkrieger.friendsystem.lib.command;

import java.util.Collection;

/*
 *
 *  * Copyright (c) 2018 Davide Wietlisbach on 16.11.18 17:47
 *
 */

public interface FriendCommandManager {

    public Collection<FriendCommand> getCommands();

    public FriendCommand getCommand(String name);

    public void registerCommand(FriendCommand command);

}
