package ch.dkrieger.friendsystem.lib.command.defaults.friend;

/*
 *
 *  * Copyright (c) 2018 Davide Wietlisbach on 16.11.18 18:53
 *
 */

import ch.dkrieger.friendsystem.lib.command.FriendCommand;
import ch.dkrieger.friendsystem.lib.command.FriendCommandSender;

import java.util.List;

public class CommandFriend extends FriendCommand {

    public CommandFriend() {
        super("friend","Friend command",null,"/friend ","freund","f");
    }

    @Override
    public void onExecute(FriendCommandSender sender, String[] args) {

    }

    @Override
    public List<String> onTabComplete(FriendCommandSender sender, String[] args) {
        return null;
    }
}
