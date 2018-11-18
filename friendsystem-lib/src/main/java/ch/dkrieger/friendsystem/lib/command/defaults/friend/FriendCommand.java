package ch.dkrieger.friendsystem.lib.command.defaults.friend;

import ch.dkrieger.friendsystem.lib.FriendSystem;
import ch.dkrieger.friendsystem.lib.Messages;
import ch.dkrieger.friendsystem.lib.command.FriendCommandSender;
import ch.dkrieger.friendsystem.lib.command.defaults.friend.subcommands.*;
import ch.dkrieger.friendsystem.lib.config.Config;

import java.util.List;

/*
 *
 *  * Copyright (c) 2018 Davide Wietlisbach on 16.11.18 20:45
 *
 */

public class FriendCommand extends ch.dkrieger.friendsystem.lib.command.FriendCommand {

    public FriendCommand() {
        super(FriendSystem.getInstance().getConfig().getStringValue("command.friend.name"),
                FriendSystem.getInstance().getConfig().getStringValue("command.friend.description"),
                FriendSystem.getInstance().getConfig().getStringValue("command.friend.permission"),
                FriendSystem.getInstance().getConfig().getStringValue("command.friend.usage"),
                FriendSystem.getInstance().getConfig().getStringListValue("command.friend.aliases"));
        setPrefix(Messages.PREFIX_FRIEND);
        Config config = FriendSystem.getInstance().getConfig();
        if(config.getBooleanValue("command.friend.acceptall.enabled")) registerSubCommand(new FriendAcceptAllCommand(config.getStringValue("command.friend.acceptall.name")));
        if(config.getBooleanValue("command.friend.accept.enabled")) registerSubCommand(new FriendAcceptCommand(config.getStringValue("command.friend.accept.name")));
        if(config.getBooleanValue("command.friend.add.enabled")) registerSubCommand(new FriendAddCommand(config.getStringValue("command.friend.add.name")));
        if(config.getBooleanValue("command.friend.clear.enabled")) registerSubCommand(new FriendClearCommand(config.getStringValue("command.friend.clear.name")));
        if(config.getBooleanValue("command.friend.denyall.enabled")) registerSubCommand(new FriendDenyAllCommand(config.getStringValue("command.friend.denyall.name")));
        if(config.getBooleanValue("command.friend.deny.enabled")) registerSubCommand(new FriendDenyCommand(config.getStringValue("command.friend.deny.name")));
        if(config.getBooleanValue("command.friend.favorite.enabled")) registerSubCommand(new FriendFavoriteCommand(config.getStringValue("command.friend.favorite.name")));
        if(config.getBooleanValue("command.friend.jump.enabled")) registerSubCommand(new FriendJumpCommand(config.getStringValue("command.friend.jump.name")));
        if(config.getBooleanValue("command.friend.list.enabled")) registerSubCommand(new FriendListCommand(config.getStringValue("command.friend.list.name")));
        if(config.getBooleanValue("command.friend.message.enabled")) registerSubCommand(new FriendMessageCommand(config.getStringValue("command.friend.message.name")));
        if(config.getBooleanValue("command.friend.remove.enabled")) registerSubCommand(new FriendRemoveCommand(config.getStringValue("command.friend.remove.name")));
        if(config.getBooleanValue("command.friend.requestlist.enabled")) registerSubCommand(new FriendRequestListCommand(config.getStringValue("command.friend.requestlist.name")));
        if(config.getBooleanValue("command.friend.toggle.enabled")) registerSubCommand(new FriendToggleCommand(config.getStringValue("command.friend.toggle.name")));
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
