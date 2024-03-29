package ch.dkrieger.friendsystem.lib.command.defaults.friend.subcommands;

/*
 *
 *  * Copyright (c) 2018 Davide Wietlisbach on 16.11.18 20:41
 *
 */

import ch.dkrieger.friendsystem.lib.FriendSystem;
import ch.dkrieger.friendsystem.lib.Messages;
import ch.dkrieger.friendsystem.lib.command.FriendCommandSender;
import ch.dkrieger.friendsystem.lib.command.SubFriendCommand;
import ch.dkrieger.friendsystem.lib.player.FriendPlayer;
import ch.dkrieger.friendsystem.lib.player.OnlineFriendPlayer;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;

import java.util.List;

public class FriendAddCommand extends SubFriendCommand {

    public FriendAddCommand() {
        super(FriendSystem.getInstance().getConfig().getStringValue("command.friend.add.name"),
                FriendSystem.getInstance().getConfig().getStringValue("command.friend.add.description"),
                FriendSystem.getInstance().getConfig().getStringValue("command.friend.add.permission"),
                "<player>",
                FriendSystem.getInstance().getConfig().getStringListValue("command.friend.add.aliases"));
    }
    @Override
    public void onExecute(FriendCommandSender sender, String[] args) {
        if(args.length <= 0){
            getMainCommand().sendHelp(sender);
            return;
        }
        FriendPlayer player = sender.getAsFriendPlayer();
        if(player != null){
            if(player.getFriends().size() >= player.getMaxFriends()){
                sender.sendMessage(Messages.PLAYER_REACHED_MAX_FRIENDS_SELF
                        .replace("[prefix]",getPrefix())
                        .replace("[max]",""+player.getMaxFriends()));
                return;
            }
            FriendPlayer friend = FriendSystem.getInstance().getPlayerManager().getPlayer(args[0]);
            if(friend == null){
                sender.sendMessage(Messages.PLAYER_NOT_FOUND
                        .replace("[prefix]",getPrefix())
                        .replace("[player]",args[0]));
                return;
            }
            //check can add
            if(friend.getFriends().size() >= friend.getMaxFriends()){
                sender.sendMessage(Messages.PLAYER_REACHED_MAX_FRIENDS_OTHER
                        .replace("[prefix]",getPrefix())
                        .replace("[player]",friend.getColoredName()));
                return;
            }
            if(friend.hasRequest(player)){
                sender.sendMessage(Messages.PLAYER_ALREADY_REQUEST
                        .replace("[prefix]",getPrefix())
                        .replace("[player]",friend.getColoredName()));
                return;
            }
            if(player.hasRequest(friend)){
                sender.executeCommand(getMainCommand().getName()+" accept "+friend.getName());
                return;
            }
            if(player.isFriend(friend)){
                sender.sendMessage(Messages.PLAYER_ALREADY_FRIENDS
                        .replace("[prefix]",getPrefix())
                        .replace("[player]",friend.getColoredName()));
                return;
            }
            friend.addRequest(player);
            sender.sendMessage(Messages.PLAYER_REQUEST_SENDED
                    .replace("[prefix]",getPrefix())
                    .replace("[player]",friend.getColoredName()));
            friend.sendMessage(Messages.replaceAcceptDeny(
                    Messages.PLAYER_REQUEST_RECEIVED_MESSAGE
                            .replace("[prefix]",getPrefix())
                            .replace("[player]",player.getColoredName())
                    ,"/"+getMainCommand().getName()+" accept "+player.getName()
                    ,"/"+getMainCommand().getName()+" deny "+player.getName()));
        }
    }
    @Override
    public List<String> onTabComplete(FriendCommandSender sender, String[] args) {
        return null;
    }
}
