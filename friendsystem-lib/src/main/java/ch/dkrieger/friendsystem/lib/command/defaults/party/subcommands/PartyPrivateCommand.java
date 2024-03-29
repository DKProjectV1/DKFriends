package ch.dkrieger.friendsystem.lib.command.defaults.party.subcommands;

import ch.dkrieger.friendsystem.lib.FriendSystem;
import ch.dkrieger.friendsystem.lib.Messages;
import ch.dkrieger.friendsystem.lib.command.FriendCommandSender;
import ch.dkrieger.friendsystem.lib.command.SubFriendCommand;
import ch.dkrieger.friendsystem.lib.party.Party;
import ch.dkrieger.friendsystem.lib.player.FriendPlayer;

import java.util.List;

/*
 *
 *  * Copyright (c) 2018 Davide Wietlisbach on 18.11.18 16:00
 *
 */

public class PartyPrivateCommand extends SubFriendCommand {

    public PartyPrivateCommand() {
        super(FriendSystem.getInstance().getConfig().getStringValue("command.party.private.name"),
                FriendSystem.getInstance().getConfig().getStringValue("command.party.private.description"),
                FriendSystem.getInstance().getConfig().getStringValue("command.party.private.permission"),
                FriendSystem.getInstance().getConfig().getStringValue("command.party.private.usage"),
                FriendSystem.getInstance().getConfig().getStringListValue("command.party.private.aliases"));
    }
    @Override
    public void onExecute(FriendCommandSender sender, String[] args) {
        FriendPlayer player = sender.getAsFriendPlayer();
        if(player != null){
            Party party = FriendSystem.getInstance().getPartyManager().getParty(player);
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
            if(!party.isPublic()) sender.sendMessage(Messages.PLAYER_PARTY_PRIVATE_ALREADY.replace("[prefix]",getPrefix()));
            else{
                party.setPublic(false);
                party.sendMessage(Messages.PLAYER_PARTY_PRIVATE_NOW.replace("[prefix]",getPrefix()));;
            }
        }
    }
    @Override
    public List<String> onTabComplete(FriendCommandSender sender, String[] args) {
        return null;
    }
}
