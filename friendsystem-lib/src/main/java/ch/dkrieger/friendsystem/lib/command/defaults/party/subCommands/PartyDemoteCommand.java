package ch.dkrieger.friendsystem.lib.command.defaults.party.subCommands;

import ch.dkrieger.friendsystem.lib.FriendSystem;
import ch.dkrieger.friendsystem.lib.Messages;
import ch.dkrieger.friendsystem.lib.command.FriendCommandSender;
import ch.dkrieger.friendsystem.lib.command.SubFriendCommand;
import ch.dkrieger.friendsystem.lib.party.Party;
import ch.dkrieger.friendsystem.lib.player.FriendPlayer;

import java.util.List;

/*
 *
 *  * Copyright (c) 2018 Davide Wietlisbach on 18.11.18 15:26
 *
 */

public class PartyDemoteCommand extends SubFriendCommand {

    public PartyDemoteCommand() {
        super("demote");
    }
    @Override
    public void onExecute(FriendCommandSender sender, String[] args) {
        FriendPlayer player = sender.getAsFriendPlayer();
        if(player != null){
            Party party = player.getParty();
            if(party == null){
                sender.sendMessage(Messages.PLAYER_PARTY_NO_PARTY_OTHER
                        .replace("[prefix]",getPrefix())
                        .replace("[player]",player.getColoredName()));
                return;
            }
            if(!party.isLeader(player)){
                sender.sendMessage(Messages.PLAYER_PARTY_NOT_LEADER.replace("[prefix]",getPrefix()));
                return;
            }
            FriendPlayer friend = FriendSystem.getInstance().getPlayerManager().getPlayer(args[0]);
            if(friend == null){
                sender.sendMessage(Messages.PLAYER_NOT_FOUND
                        .replace("[prefix]",getPrefix())
                        .replace("[player]",args[0]));
                return;
            }
            if(!party.isMember(friend)){
                sender.sendMessage(Messages.PLAYER_PARTY_NO_PARTY_OTHER
                        .replace("[prefix]",getPrefix())
                        .replace("[player]",friend.getColoredName()));
                return;
            }
            if(party.isModerator(friend)){
                party.setModerator(friend,false);
                party.sendMessage(Messages.PLAYER_PARTY_MODERATOR_DEMOTED
                        .replace("[prefix]",getPrefix())
                        .replace("[player]",friend.getColoredName()));
            }else{
                sender.sendMessage(Messages.PLAYER_PARTY_MODERATOR_NOT
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