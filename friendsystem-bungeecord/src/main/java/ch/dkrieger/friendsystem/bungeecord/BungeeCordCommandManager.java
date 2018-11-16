package ch.dkrieger.friendsystem.bungeecord;

import ch.dkrieger.friendsystem.lib.command.FriendCommand;
import ch.dkrieger.friendsystem.lib.command.FriendCommandManager;
import ch.dkrieger.friendsystem.lib.command.FriendCommandSender;
import ch.dkrieger.friendsystem.lib.player.FriendPlayer;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.UUID;

/*
 *
 *  * Copyright (c) 2018 Davide Wietlisbach on 16.11.18 08:47
 *
 */

public class BungeeCordCommandManager implements FriendCommandManager {

    private Collection<FriendCommand> commands;

    public BungeeCordCommandManager() {
        this.commands = new LinkedHashSet<>();
    }
    public Collection<FriendCommand> getCommands() {
        return this.commands;
    }
    public FriendCommand getCommand(String name) {
        for(FriendCommand command : this.commands) if(command.getName().equalsIgnoreCase(name)) return command;
        return null;
    }
    public void registerCommand(final FriendCommand command) {
        BungeeCord.getInstance().getPluginManager().registerCommand(BungeeCordFriendSystemBootstrap.getInstance()
                ,new BungeeCordFriendCommand(command));
    }
    private class BungeeCordFriendCommand extends Command implements TabExecutor {

        private FriendCommand command;

        public BungeeCordFriendCommand(FriendCommand command) {
            super(command.getName(),command.getPermission(),command.getAliases().toArray(new String[command.getAliases().size()]));
            this.command = command;
        }
        @Override
        public void execute(CommandSender sender, String[] args) {
            if(command.getPermission() == null || sender.hasPermission(command.getPermission())){
                BungeeCord.getInstance().getScheduler().runAsync(BungeeCordFriendSystemBootstrap.getInstance(),()->{
                    command.execute(new BungeeCordFriendCommandSender(sender),args);
                });
                return;
            }
            sender.sendMessage("No Perms");
            //send no perm message
            //sender.sendMessage(new TextComponent(Messages.PREFIX+Messages.NOPERMISSIONS));
            return;
        }
        @Override
        public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
            return this.command.tabComplete(new BungeeCordFriendCommandSender(sender),args);
        }
    }
    private class BungeeCordFriendCommandSender implements FriendCommandSender {

        private CommandSender sender;

        public BungeeCordFriendCommandSender(CommandSender sender) {
            this.sender = sender;
        }
        @Override
        public String getName() {
            return sender.getName();
        }
        @Override
        public UUID getUUID() {
            if(sender instanceof ProxiedPlayer) return ((ProxiedPlayer) sender).getUniqueId();
            else return null;
        }
        @Override
        public FriendPlayer getASFriendPlayer() {
            return null;
        }
        @Override
        public Boolean hasPermission(String permission) {
            return sender.hasPermission(permission);
        }
        @Override
        public void sendMessage(String message) {
            sender.sendMessage(message);
        }
        @Override
        public void sendMessage(TextComponent component) {
            sender.sendMessage(component);
        }
    }
}
