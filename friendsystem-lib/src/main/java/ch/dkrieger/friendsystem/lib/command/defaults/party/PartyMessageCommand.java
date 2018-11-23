package ch.dkrieger.friendsystem.lib.command.defaults.party;

import ch.dkrieger.friendsystem.lib.FriendSystem;
import ch.dkrieger.friendsystem.lib.Messages;
import ch.dkrieger.friendsystem.lib.command.FriendCommand;
import ch.dkrieger.friendsystem.lib.command.FriendCommandSender;
import ch.dkrieger.friendsystem.lib.command.defaults.party.subcommands.*;

import java.util.List;

/*
 *
 *  * Copyright (c) 2018 Davide Wietlisbach on 20.11.18 18:19
 *
 */

public class PartyMessageCommand extends FriendCommand {

    public PartyMessageCommand() {
        super(FriendSystem.getInstance().getConfig().getStringValue("command.partymessage.name"),
                FriendSystem.getInstance().getConfig().getStringValue("command.partymessage.name"),
                FriendSystem.getInstance().getConfig().getStringValue("command.partymessage.permission"),
                "<message>",
                FriendSystem.getInstance().getConfig().getStringListValue("command.partymessage.aliases"));
        setPrefix(Messages.PREFIX_PARTY);
    }
    @Override
    public void onExecute(FriendCommandSender sender, String[] args) {
        if(args.length <= 0){
            sender.sendMessage(Messages.PLAYER_MESSAGE_NOT_ENABLED
                    .replace("[prefix]",getPrefix()));
            return;
        }
        String message = "";
        for(int i = 0; i< args.length;i++) message += args[i]+" "+Messages.PLAYER_PARTY_MESSAGE_COLOR;
        sender.executeCommand(FriendSystem.getInstance().getConfig().getStringValue("command.party.name")
                +" "+FriendSystem.getInstance().getConfig().getStringValue("command.party.message.name")
                +" "+message);
    }
    @Override
    public List<String> onTabComplete(FriendCommandSender sender, String[] args) {
        return null;
    }
}
