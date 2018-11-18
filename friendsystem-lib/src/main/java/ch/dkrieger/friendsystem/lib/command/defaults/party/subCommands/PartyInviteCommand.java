package ch.dkrieger.friendsystem.lib.command.defaults.party.subCommands;

import ch.dkrieger.friendsystem.lib.FriendSystem;
import ch.dkrieger.friendsystem.lib.Messages;
import ch.dkrieger.friendsystem.lib.command.FriendCommandSender;
import ch.dkrieger.friendsystem.lib.command.SubFriendCommand;
import ch.dkrieger.friendsystem.lib.party.Party;
import ch.dkrieger.friendsystem.lib.player.FriendPlayer;
import ch.dkrieger.friendsystem.lib.player.OnlineFriendPlayer;

import java.util.List;

/*
 *
 *  * Copyright (c) 2018 Davide Wietlisbach on 18.11.18 11:47
 *
 */

public class PartyInviteCommand extends SubFriendCommand {

    public PartyInviteCommand() {
        super("invite");
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
            /*
            check can invite
             */
            Party party = FriendSystem.getInstance().getPartyManager().getParty(player);
            if(party == null) party = FriendSystem.getInstance().getPartyManager().createParty(player);
            else if(!party.isLeader(player)){
                sender.sendMessage(Messages.PLAYER_PARTY_NOT_LEADER
                        .replace("[prefix]",getPrefix()));
                return;
            }
            if(party.isMember(friend)){
                sender.sendMessage(Messages.PLAYER_ALREADY_PARTY_OWN
                        .replace("[prefix]",getPrefix())
                        .replace("[player]",friend.getColoredName()));
                return;
            }
            if(party.hasRequest(friend)){
                sender.sendMessage(Messages.PLAYER_ALREADY_PARTY_REQUEST
                        .replace("[prefix]",getPrefix())
                        .replace("[player]",friend.getColoredName()));
                return;
            }
            if(FriendSystem.getInstance().getPartyManager().getParty(friend) != null){
                sender.sendMessage(Messages.PLAYER_ALREADY_PARTY_OTHER
                        .replace("[prefix]",getPrefix())
                        .replace("[player]",friend.getColoredName()));
                return;
            }
            
        }
    }
    @Override
    public List<String> onTabComplete(FriendCommandSender sender, String[] args) {
        return null;
    }
}
