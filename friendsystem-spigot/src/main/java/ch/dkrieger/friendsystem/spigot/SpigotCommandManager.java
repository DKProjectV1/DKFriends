package ch.dkrieger.friendsystem.spigot;

/*
 *
 *  * Copyright (c) 2018 Philipp Elvin Friedhoff on 17.11.18 13:23
 *
 */

import ch.dkrieger.friendsystem.lib.command.FriendCommand;
import ch.dkrieger.friendsystem.lib.command.FriendCommandManager;
import ch.dkrieger.friendsystem.lib.command.FriendCommandSender;
import ch.dkrieger.friendsystem.lib.player.FriendPlayer;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class SpigotCommandManager implements FriendCommandManager {

    private Collection<FriendCommand> commands;

    @Override
    public Collection<FriendCommand> getCommands() {
        return this.commands;
    }

    @Override
    public FriendCommand getCommand(String name) {
        for(FriendCommand command : this.commands) if(command.getName().equalsIgnoreCase(name)) return command;
        return null;
    }

    @Override
    public void registerCommand(FriendCommand command) {

    }

    public abstract class SpigotFriendCommand extends Command implements TabExecutor {

        private FriendCommand command;

        public SpigotFriendCommand(FriendCommand command) {
            super(command.getName(), command.getDescription(), command.getUsage(), command.getAliases());
            this.command = command;
        }

        @Override
        public boolean execute(CommandSender commandSender, String s, String[] args) {
            if(command.getPermission() == null || commandSender.hasPermission(command.getPermission())){
                Bukkit.getScheduler().runTaskAsynchronously(SpigotFriendSystemBootstrap.getInstance(), ()->{
                    command.execute(new SpigotFriendCommandSender(commandSender), args);
                });
            }else commandSender.sendMessage("No Perms");
            return false;
        }

        @Override
        public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] args) {
            return this.command.tabComplete(new SpigotFriendCommandSender(commandSender),args);
        }
    }

    private class SpigotFriendCommandSender implements FriendCommandSender {

        private final CommandSender commandSender;

        public SpigotFriendCommandSender(CommandSender commandSender) {
            this.commandSender = commandSender;
        }

        @Override
        public String getName() {
            return commandSender.getName();
        }

        @Override
        public UUID getUUID() {
            if(commandSender instanceof Player)return ((Player)commandSender).getUniqueId();
            return null;
        }

        @Override
        public FriendPlayer getAsFriendPlayer() {
            return null;
        }

        @Override
        public boolean hasPermission(String permission) {
            return commandSender.hasPermission(permission);
        }

        @Override
        public void sendMessage(String message) {
            commandSender.sendMessage(message);
        }

        @Override
        public void sendMessage(TextComponent component) {

        }
    }
}