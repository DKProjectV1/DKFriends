package ch.dkrieger.friendsystem.lib.command.defaults.friend.subcommands;

import ch.dkrieger.friendsystem.lib.FriendSystem;
import ch.dkrieger.friendsystem.lib.Messages;
import ch.dkrieger.friendsystem.lib.command.FriendCommandSender;
import ch.dkrieger.friendsystem.lib.command.SubFriendCommand;
import ch.dkrieger.friendsystem.lib.player.FriendPlayer;

import java.util.List;

/*
 *
 *  * Copyright (c) 2018 Davide Wietlisbach on 17.11.18 23:19
 *
 */

public class FriendToggleCommand extends SubFriendCommand {

    public FriendToggleCommand() {
        super(FriendSystem.getInstance().getConfig().getStringValue("command.friend.toggle.name"),
                FriendSystem.getInstance().getConfig().getStringValue("command.friend.toggle.description"),
                FriendSystem.getInstance().getConfig().getStringValue("command.friend.toggle.permission"),
                FriendSystem.getInstance().getConfig().getStringValue("command.friend.toggle.usage"),
                FriendSystem.getInstance().getConfig().getStringListValue("command.friend.toggle.aliases"));
    }
    @Override
    public void onExecute(FriendCommandSender sender, String[] args) {
        FriendPlayer player = sender.getAsFriendPlayer();
        if(player != null){
            if(args[0].equalsIgnoreCase("jump")){
                player.getSettings().setJumpEnabled(!player.getSettings().isJumpEnabled());
                if(player.getSettings().isJumpEnabled()) sender.sendMessage(Messages.PLAYER_SETTING_JUMP_ENABLE.replace("[prefix]",getPrefix()));
                else sender.sendMessage(Messages.PLAYER_SETTING_JUMP_DISABLE.replace("[prefix]",getPrefix()));
            }else if(args[0].equalsIgnoreCase("message")){
                player.getSettings().setMessageEnabled(!player.getSettings().isMessageEnabled());
                if(player.getSettings().isMessageEnabled()) sender.sendMessage(Messages.PLAYER_SETTING_MESSAGE_ENABLE.replace("[prefix]",getPrefix()));
                else sender.sendMessage(Messages.PLAYER_SETTING_MESSAGE_DISABLE.replace("[prefix]",getPrefix()));
            }else if(args[0].equalsIgnoreCase("notify")){
                player.getSettings().setNotifyEnabled(!player.getSettings().isNotifyEnabled());
                if(player.getSettings().isJumpEnabled()) sender.sendMessage(Messages.PLAYER_SETTING_NOTIFY_ENABLE.replace("[prefix]",getPrefix()));
                else sender.sendMessage(Messages.PLAYER_SETTING_NOTIFY_DISABLE.replace("[prefix]",getPrefix()));
            }else sender.sendMessage(Messages.PLAYER_SETTING_HELP.replace("[prefix]",getPrefix()));
        }
    }
    @Override
    public List<String> onTabComplete(FriendCommandSender sender, String[] args) {
        return null;
    }
}
