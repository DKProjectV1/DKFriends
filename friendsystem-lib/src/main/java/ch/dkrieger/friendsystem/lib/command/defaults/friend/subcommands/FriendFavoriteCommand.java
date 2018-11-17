package ch.dkrieger.friendsystem.lib.command.defaults.friend.subcommands;

import ch.dkrieger.friendsystem.lib.FriendSystem;
import ch.dkrieger.friendsystem.lib.Messages;
import ch.dkrieger.friendsystem.lib.command.FriendCommandSender;
import ch.dkrieger.friendsystem.lib.command.SubFriendCommand;
import ch.dkrieger.friendsystem.lib.player.FriendPlayer;

import java.util.List;

/*
 *
 *  * Copyright (c) 2018 Davide Wietlisbach on 17.11.18 18:17
 *
 */

public class FriendFavoriteCommand extends SubFriendCommand {

    public FriendFavoriteCommand() {
        super("favorite","",null,"","favourite","darling","fav","honey","ducky");
    }
    @Override
    public void onExecute(FriendCommandSender sender, String[] args) {
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
            if(player.isFavorite(friend)){
                player.setFavorite(friend,false);
                sender.sendMessage(Messages.PLAYER_FAVORITE_REMOVE
                        .replace("[prefix]",getPrefix())
                        .replace("[player]",friend.getColoredName()));
            }else{
                player.setFavorite(friend,true);
                sender.sendMessage(Messages.PLAYER_FAVORITE_ADD
                        .replace("[prefix]",getPrefix())
                        .replace("[player]",friend.getColoredName()));
            }
        }
    }
    @Override
    public List<String> onTabComplete(FriendCommandSender sender, String[] args) {
        return null;
    }
}
