package ch.dkrieger.friendsystem.lib.command.defaults.friend.subcommands;

import ch.dkrieger.friendsystem.lib.FriendSystem;
import ch.dkrieger.friendsystem.lib.Messages;
import ch.dkrieger.friendsystem.lib.command.FriendCommandSender;
import ch.dkrieger.friendsystem.lib.command.SubFriendCommand;
import ch.dkrieger.friendsystem.lib.player.FriendPlayer;
import ch.dkrieger.friendsystem.lib.utils.GeneralUtil;

import java.util.List;

/*
 *
 *  * Copyright (c) 2018 Davide Wietlisbach on 17.11.18 18:02
 *
 */

public class FriendRemoveCommand extends SubFriendCommand {

    public FriendRemoveCommand() {
        super(FriendSystem.getInstance().getConfig().getStringValue("command.friend.remove.name"),
                FriendSystem.getInstance().getConfig().getStringValue("command.friend.remove.description"),
                FriendSystem.getInstance().getConfig().getStringValue("command.friend.remove.permission"),
                "<player>",
                FriendSystem.getInstance().getConfig().getStringListValue("command.friend.remove.aliases"));
    }
    @Override
    public void onExecute(FriendCommandSender sender, String[] args) {
        if(args.length <= 0){
            getMainCommand().sendHelp(sender);
            return;
        }
        FriendPlayer player = sender.getAsFriendPlayer();
        if(player != null){
            FriendPlayer friend = FriendSystem.getInstance().getPlayerManager().getPlayer(args[0]);
            if(friend == null){
                sender.sendMessage(Messages.PLAYER_NOT_FOUND
                        .replace("[prefix]",getPrefix())
                        .replace("[player]",args[0]));
                return;
            }
            if(!player.isFriend(friend)){
                sender.sendMessage(Messages.PLAYER_NOT_FRIENDS
                        .replace("[prefix]",getPrefix())
                        .replace("[player]",friend.getColoredName()));
                return;
            }
            player.removeFriend(friend);
            sender.sendMessage(Messages.PLAYER_REMOVED_SELF
                    .replace("[prefix]",getPrefix())
                    .replace("[player]",friend.getColoredName()));
            friend.sendMessage(Messages.PLAYER_REMOVED_OTHER
                    .replace("[prefix]",getPrefix())
                    .replace("[player]",player.getColoredName()));
        }
    }
    @Override
    public List<String> onTabComplete(FriendCommandSender sender, String[] args) {
        FriendPlayer player = sender.getAsFriendPlayer();
        if(player == null) return null;
        String search = "";
        if(args.length >= 1) search = args[0].toLowerCase();
        return GeneralUtil.startsWith(search,player.getFriendNames());
    }
}
