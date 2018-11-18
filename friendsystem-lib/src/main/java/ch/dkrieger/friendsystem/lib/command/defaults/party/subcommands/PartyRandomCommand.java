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
 *  * Copyright (c) 2018 Davide Wietlisbach on 18.11.18 16:02
 *
 */

public class PartyRandomCommand extends SubFriendCommand {

    public PartyRandomCommand() {
        super(FriendSystem.getInstance().getConfig().getStringValue("command.party.random.name"),
                FriendSystem.getInstance().getConfig().getStringValue("command.party.random.description"),
                FriendSystem.getInstance().getConfig().getStringValue("command.party.random.permission"),
                FriendSystem.getInstance().getConfig().getStringValue("command.party.random.usage"),
                FriendSystem.getInstance().getConfig().getStringListValue("command.party.random.aliases"));
    }
    @Override
    public void onExecute(FriendCommandSender sender, String[] args) {
        FriendPlayer player = sender.getAsFriendPlayer();
        if(player != null){
            Party party = FriendSystem.getInstance().getPartyManager().getRandomPublicParty();
            if(party == null){
                sender.sendMessage(Messages.PLAYER_PARTY_PUBLIC_NO
                        .replace("[prefix]",getPrefix()));
                return;
            }
            sender.executeCommand(getMainCommand().getName()+" join "+party.getLeader().getPlayer().getName());
        }
    }
    @Override
    public List<String> onTabComplete(FriendCommandSender sender, String[] args) {
        return null;
    }
}
