package ch.dkrieger.friendsystem.lib.command.defaults.friend;

import ch.dkrieger.friendsystem.lib.Messages;
import ch.dkrieger.friendsystem.lib.command.FriendCommandSender;
import ch.dkrieger.friendsystem.lib.command.defaults.friend.subcommands.*;
import ch.dkrieger.friendsystem.lib.command.defaults.friend.subcommands.FriendMessageCommand;
import ch.dkrieger.friendsystem.lib.config.Config;

import java.util.List;

/*
 *
 *  * Copyright (c) 2018 Davide Wietlisbach on 16.11.18 20:45
 *
 */

public class FriendCommand extends ch.dkrieger.friendsystem.lib.command.FriendCommand {

    public FriendCommand(Config config) {
        super(config.getStringValue("command.friend.name"),
                config.getStringValue("command.friend.description"),
                config.getStringValue("command.friend.permission"),
                config.getStringValue("command.friend.usage"),
                config.getStringListValue("command.friend.aliases"));
        setPrefix(Messages.PREFIX_FRIEND);
        if(config.getBooleanValue("command.friend.list.enabled")) registerSubCommand(new FriendListCommand());
        if(config.getBooleanValue("command.friend.requestlist.enabled")) registerSubCommand(new FriendRequestListCommand());
        if(config.getBooleanValue("command.friend.add.enabled")) registerSubCommand(new FriendAddCommand());
        if(config.getBooleanValue("command.friend.accept.enabled")) registerSubCommand(new FriendAcceptCommand());
        if(config.getBooleanValue("command.friend.deny.enabled")) registerSubCommand(new FriendDenyCommand());
        if(config.getBooleanValue("command.friend.acceptall.enabled")) registerSubCommand(new FriendAcceptAllCommand());
        if(config.getBooleanValue("command.friend.denyall.enabled")) registerSubCommand(new FriendDenyAllCommand());
        if(config.getBooleanValue("command.friend.favorite.enabled")) registerSubCommand(new FriendFavoriteCommand());
        if(config.getBooleanValue("command.friend.jump.enabled")) registerSubCommand(new FriendJumpCommand());
        if(config.getBooleanValue("command.friend.message.enabled")) registerSubCommand(new FriendMessageCommand());
        if(config.getBooleanValue("command.friend.respond.enabled")) registerSubCommand(new FriendRespondCommand());
        if(config.getBooleanValue("command.friend.remove.enabled")) registerSubCommand(new FriendRemoveCommand());
        if(config.getBooleanValue("command.friend.clear.enabled")) registerSubCommand(new FriendClearCommand());
        if(config.getBooleanValue("command.friend.toggle.enabled")) registerSubCommand(new FriendToggleCommand());
    }
    @Override
    public void onExecute(FriendCommandSender sender, String[] args) {
        sendHelp(sender,args);
    }

    @Override
    public List<String> onTabComplete(FriendCommandSender sender, String[] args) {
        return null;
    }
}
