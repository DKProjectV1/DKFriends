package ch.dkrieger.friendsystem.spigot.inventories;

import ch.dkrieger.friendsystem.spigot.inventories.FriendPage;
import org.bukkit.entity.Player;
import java.util.LinkedHashMap;
import java.util.Map;

/*
 *
 *  * Copyright (c) 2018 Philipp Elvin Friedhoff on 21.11.18 19:42
 *
 */

public class Profile {

    private Player player;
    private FriendPage friendPage;
    private PartyPage partyPage;
    private SettingsPage settingsPage;
    private Map<String, Object> properties;

    public Profile(Player player) {
        this.player = player;
        this.friendPage = new FriendPage(player);
        this.partyPage = new PartyPage(player);
        this.settingsPage = new SettingsPage(player);
        this.properties = new LinkedHashMap<>();
    }

    public Player getPlayer() {
        return player;
    }

    public FriendPage getFriendPage() {
        return friendPage;
    }

    public PartyPage getPartyPage() {
        return partyPage;
    }

    public SettingsPage getSettingsPage() {
        return settingsPage;
    }

    public Map<String, Object> getProperties() {
        return properties;
    }

    public Object getProperty(String key) {
        return this.properties.get(key);
    }

    public boolean hasProperty(String key) {
        return this.properties.containsKey(key);
    }

    public void addProperty(String key, Object value) {
        this.properties.put(key, value);
    }
}