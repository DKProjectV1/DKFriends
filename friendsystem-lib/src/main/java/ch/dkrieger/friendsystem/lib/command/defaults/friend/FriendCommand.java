package ch.dkrieger.friendsystem.lib.command.defaults.friend;

import ch.dkrieger.friendsystem.lib.Messages;
import ch.dkrieger.friendsystem.lib.command.FriendCommandSender;
import ch.dkrieger.friendsystem.lib.command.defaults.friend.subcommands.FriendAddCommand;

import java.util.List;

/*
 *
 *  * Copyright (c) 2018 Davide Wietlisbach on 16.11.18 20:45
 *
 */

public class FriendCommand extends ch.dkrieger.friendsystem.lib.command.FriendCommand {

    public FriendCommand() {
        super("friend","Friend command",null,"/friend ","freund","f");
        setPrefix(Messages.PREFIX_FRIEND);
        registerSubCommand(new FriendAddCommand());
    }

    @Override
    public void onExecute(FriendCommandSender sender, String[] args) {
        sendHelp(sender);
    }

    @Override
    public List<String> onTabComplete(FriendCommandSender sender, String[] args) {
        return null;
    }
}
