package ch.dkrieger.friendsystem.lib;

import ch.dkrieger.friendsystem.lib.command.FriendCommandManager;
import ch.dkrieger.friendsystem.lib.player.FriendPlayer;

import java.io.File;

/*
 *
 *  * Copyright (c) 2018 Davide Wietlisbach on 17.11.18 11:19
 *
 */

public interface DKFriendsPlatform {

    public String getPlatformName();

    public String getServerVersion();

    public File getFolder();

    public FriendCommandManager getCommandManager();

    public String getColor(FriendPlayer player);

    //public PermissionTaskManager getTaskManager();

}
