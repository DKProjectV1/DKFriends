package ch.dkrieger.friendsystem.lib.command.defaults.party.subcommands;

import ch.dkrieger.friendsystem.lib.FriendSystem;
import ch.dkrieger.friendsystem.lib.Messages;
import ch.dkrieger.friendsystem.lib.command.FriendCommandSender;
import ch.dkrieger.friendsystem.lib.command.SubFriendCommand;
import ch.dkrieger.friendsystem.lib.party.Party;
import ch.dkrieger.friendsystem.lib.player.FriendPlayer;
import ch.dkrieger.friendsystem.lib.utils.GeneralUtil;

import java.util.List;

/*
 *
 *  * Copyright (c) 2018 Davide Wietlisbach on 18.11.18 14:52
 *
 */

public class PartyPromoteCommand extends SubFriendCommand {

    public PartyPromoteCommand() {
        super(FriendSystem.getInstance().getConfig().getStringValue("command.party.promote.name"),
                FriendSystem.getInstance().getConfig().getStringValue("command.party.promote.description"),
                FriendSystem.getInstance().getConfig().getStringValue("command.party.promote.permission"),
                FriendSystem.getInstance().getConfig().getStringValue("command.party.promote.usage"),
                FriendSystem.getInstance().getConfig().getStringListValue("command.party.promote.aliases"));
    }
    @Override
    public void onExecute(FriendCommandSender sender, String[] args) {
        if(args.length <= 0){
            getMainCommand().sendHelp(sender);
            return;
        }
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
                party.setLeader(friend);
                party.sendMessage(Messages.PLAYER_PARTY_NEW_LEADER
                        .replace("[prefix]",getPrefix())
                        .replace("[player]",friend.getColoredName()));
            }else{
                party.setModerator(friend,true);
                party.sendMessage(Messages.PLAYER_PARTY_MODERATOR_PROMOTED
                        .replace("[prefix]",getPrefix())
                        .replace("[player]",friend.getColoredName()));
            }
        }
    }
    @Override
    public List<String> onTabComplete(FriendCommandSender sender, String[] args) {
        FriendPlayer player = sender.getAsFriendPlayer();
        if(player == null) return null;
        Party party = player.getParty();
        if(party == null || !(party.isLeader(player))) return null;
        String search = "";
        if(args.length >= 1) search = args[0].toLowerCase();
        return GeneralUtil.startsWith(search,party.getMemberNames());
    }
}
