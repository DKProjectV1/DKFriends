package ch.dkrieger.friendsystem.spigot;

/*
 *
 *  * Copyright (c) 2018 Philipp Elvin Friedhoff on 17.11.18 13:23
 *
 */

import ch.dkrieger.friendsystem.lib.FriendSystem;
import ch.dkrieger.friendsystem.lib.Messages;
import ch.dkrieger.friendsystem.lib.command.FriendCommand;
import ch.dkrieger.friendsystem.lib.command.FriendCommandManager;
import ch.dkrieger.friendsystem.lib.command.FriendCommandSender;
import ch.dkrieger.friendsystem.lib.player.FriendPlayer;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.util.*;

public class SpigotCommandManager implements FriendCommandManager {

    private Collection<FriendCommand> commands;

    public SpigotCommandManager() {
        this.commands = new LinkedList<>();
    }
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
        this.commands.add(command);
        CommandMap cmap = null;
        try{
            final Field field = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            field.setAccessible(true);
            cmap = (CommandMap)field.get(Bukkit.getServer());
            cmap.register("",new SpigotFriendCommand(command));
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public class SpigotFriendCommand extends Command implements TabCompleter {

        private FriendCommand command;

        public SpigotFriendCommand(FriendCommand command) {
            super(command.getName(), command.getDescription(), command.getUsage(), command.getAliases());
            this.command = command;
        }
        @Override
        public boolean execute(CommandSender sender, String s, String[] args) {
            if(command.getPermission() == null || sender.hasPermission(command.getPermission())){
                Bukkit.getScheduler().runTaskAsynchronously(SpigotFriendSystemBootstrap.getInstance(), ()->{
                    command.execute(new SpigotFriendCommandSender(sender), args);
                });
            }else{
                sender.sendMessage(Messages.NOPERMISSIONS
                        .replace("[prefix]",(command.getPrefix() != null?command.getPrefix():Messages.PREFIX_FRIEND)));
            }
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
            if(this.commandSender instanceof Player)return ((Player)this.commandSender).getUniqueId();
            return null;
        }

        @Override
        public FriendPlayer getAsFriendPlayer() {
            return FriendSystem.getInstance().getPlayerManager().getPlayer(getUUID());
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
        @Override
        public void executeCommand(String command) {

        }
    }
}