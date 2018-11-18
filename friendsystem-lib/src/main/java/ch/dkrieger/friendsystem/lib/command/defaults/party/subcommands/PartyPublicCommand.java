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

public class PartyPublicCommand extends SubFriendCommand {

    public PartyPublicCommand() {
        super(FriendSystem.getInstance().getConfig().getStringValue("command.party.public.name"),
                FriendSystem.getInstance().getConfig().getStringValue("command.party.public.description"),
                FriendSystem.getInstance().getConfig().getStringValue("command.party.public.permission"),
                FriendSystem.getInstance().getConfig().getStringValue("command.party.public.usage"),
                FriendSystem.getInstance().getConfig().getStringListValue("command.party.public.aliases"));
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
            if(party.isPublic()) sender.sendMessage(Messages.PLAYER_PARTY_PUBLIC_ALREADY.replace("[prefix]",getPrefix()));
            else{
                party.setPublic(true);
                party.sendMessage(Messages.PLAYER_PARTY_PUBLIC_NOW.replace("[prefix]",getPrefix()));;
            }
        }
    }
    @Override
    public List<String> onTabComplete(FriendCommandSender sender, String[] args) {
        return null;
    }
}
