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

public class PartyKickCommand extends SubFriendCommand {

    public PartyKickCommand() {
        super(FriendSystem.getInstance().getConfig().getStringValue("command.party.kick.name"),
                FriendSystem.getInstance().getConfig().getStringValue("command.party.kick.description"),
                FriendSystem.getInstance().getConfig().getStringValue("command.party.kick.permission"),
                "<player>",
                FriendSystem.getInstance().getConfig().getStringListValue("command.party.kick.aliases"));
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
                sender.sendMessage(Messages.PLAYER_PARTY_NO_PARTY_SELF.replace("[prefix]",getPrefix()));
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
                        .replace("[player]",player.getColoredName()));
                return;
            }
            if(!party.canIntegrate(player,friend)){
                sender.sendMessage(Messages.PLAYER_PARTY_NOT_ALLOWED_KICK
                        .replace("[prefix]",getPrefix())
                        .replace("[player]",friend.getColoredName()));
                return;
            }
            party.sendMessage(Messages.PLAYER_PARTY_KICKED
                    .replace("[prefix]",getPrefix())
                    .replace("[player]",friend.getColoredName())
                    .replace("[kicker]",player.getColoredName()));
            party.removeMember(friend);
        }
    }
    @Override
    public List<String> onTabComplete(FriendCommandSender sender, String[] args) {
        FriendPlayer player = sender.getAsFriendPlayer();
        if(player == null) return null;
        Party party = player.getParty();
        if(party == null || !(party.isLeader(player)) || !(party.isModerator(player))) return null;
        String search = "";
        if(args.length >= 1) search = args[0].toLowerCase();
        return GeneralUtil.startsWith(search,party.getMemberNames());
    }
}
