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
import ch.dkrieger.friendsystem.lib.player.FriendPlayerManager;

import java.util.List;

public class AddCommand extends SubFriendCommand {

    public AddCommand(String name) {
        super(name);
    }
    public AddCommand(String name, String description) {
        super(name, description);
    }
    public AddCommand(String name, String description, String permission) {
        super(name, description, permission);
    }
    public AddCommand(String name, String description, String permission, String usage, String... aliases) {
        super(name, description, permission, usage, aliases);
    }
    @Override
    public void onExecute(FriendCommandSender sender, String[] args) {
        FriendPlayer player = sender.getAsFriendPlayer();
        if(player != null){
            if(player.getFriends().size() >= player.getMaxFriends()){
                sender.sendMessage(Messages.PLAYER_MAX_FRIENDS_REACHED.replace("[max]",player.getm));
                return;
            }
        }
    }
    @Override
    public List<String> onTabComplete(FriendCommandSender sender, String[] args) {
        return null;
    }
}
