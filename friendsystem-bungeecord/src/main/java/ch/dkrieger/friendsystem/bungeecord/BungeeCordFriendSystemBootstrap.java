package ch.dkrieger.friendsystem.bungeecord;

/*
 *
 *  * Copyright (c) 2018 Davide Wietlisbach on 16.11.18 08:41
 *
 */

import net.md_5.bungee.api.plugin.Plugin;

public class BungeeCordFriendSystemBootstrap extends Plugin {

    private static BungeeCordFriendSystemBootstrap instance;

    public static BungeeCordFriendSystemBootstrap getInstance() {
        return instance;
    }
}
