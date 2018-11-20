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
 *  * Copyright (c) 2018 Davide Wietlisbach on 18.11.18 15:33
 *
 */

public class PartyMessageCommand extends SubFriendCommand {

    public PartyMessageCommand() {
        super(FriendSystem.getInstance().getConfig().getStringValue("command.party.message.name"),
                FriendSystem.getInstance().getConfig().getStringValue("command.party.message.description"),
                FriendSystem.getInstance().getConfig().getStringValue("command.party.message.permission"),
                "<message>",
                FriendSystem.getInstance().getConfig().getStringListValue("command.party.message.aliases"));
    }
    @Override
    public void onExecute(FriendCommandSender sender, String[] args) {
        FriendPlayer player = sender.getAsFriendPlayer();
        if(player != null){
            if(args.length <= 0){
                sender.sendMessage(Messages.PLAYER_MESSAGE_NOT_ENABLED
                        .replace("[prefix]",getPrefix()));
                return;
            }
            Party party = player.getParty();
            if(party == null){
                sender.sendMessage(Messages.PLAYER_PARTY_NO_PARTY_SELF.replace("[prefix]",getPrefix()));
                return;
            }
            String message = "";
            for(int i = 1; i< args.length;i++) message = message +" "+Messages.PLAYER_PARTY_MESSAGE_COLOR + args[i];

            party.sendMessage(Messages.PLAYER_PARTY_MESSAGE_FORMAT
                    .replace("[prefix]",getPrefix())
                    .replace("[message]",message)
                    .replace("[player]",player.getColoredName()));
        }
    }
    @Override
    public List<String> onTabComplete(FriendCommandSender sender, String[] args) {
        return null;
    }
}
