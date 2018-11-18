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
 *  * Copyright (c) 2018 Davide Wietlisbach on 18.11.18 13:56
 *
 */

public class PartyDenyCommand extends SubFriendCommand {

    public PartyDenyCommand() {
        super("deny");
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
            Party party = FriendSystem.getInstance().getPartyManager().getParty(friend);
            if(party == null){
                sender.sendMessage(Messages.PLAYER_PARTY_NOT_PARTY
                        .replace("[prefix]",getPrefix())
                        .replace("[player]",friend.getColoredName()));
                return;
            }
            if(!party.hasRequest(player)){
                sender.sendMessage(Messages.PLAYER_PARTY_REQUEST_NOT
                        .replace("[prefix]",getPrefix())
                        .replace("[player]",friend.getColoredName()));
                return;
            }
            party.removeRequest(player);
            sender.sendMessage(Messages.PLAYER_PARTY_DENIED_SELF
                    .replace("[prefix]",getPrefix())
                    .replace("[player]",friend.getColoredName()));
            party.sendMessage(Messages.PLAYER_PARTY_DENIED_OTHER
                    .replace("[prefix]",getPrefix())
                    .replace("[player]",player.getColoredName()));
        }
    }
    @Override
    public List<String> onTabComplete(FriendCommandSender sender, String[] args) {
        return null;
    }
}
