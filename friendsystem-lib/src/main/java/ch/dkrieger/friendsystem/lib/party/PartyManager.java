package ch.dkrieger.friendsystem.lib.party;

/*
 *
 *  * Copyright (c) 2018 Davide Wietlisbach on 18.11.18 11:35
 *
 */

import ch.dkrieger.friendsystem.lib.player.FriendPlayer;
import ch.dkrieger.friendsystem.lib.player.OnlineFriendPlayer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

public class PartyManager {

    private List<Party> parties;

    public PartyManager() {
        this.parties = new ArrayList<>();
    }
    public List<Party> getParties() {
        return parties;
    }
    public List<Party> getPublicParties(){
        List<Party> parties = new ArrayList<>();
        Iterator<Party> iterator = new ArrayList<>(this.parties).iterator();
        Party party = null;
        while(iterator.hasNext() && (party = iterator.next()) != null) if(party.isPublic()) parties.add(party);
        return parties;
    }
    public Party getParty(FriendPlayer player){
        return getParty(player.getUUID());
    }
    public Party getParty(OnlineFriendPlayer player){
        return getParty(player.getUUID());
    }
    public Party getParty(UUID uuid){
        Iterator<Party> iterator = new ArrayList<>(this.parties).iterator();
        Party party = null;
        while(iterator.hasNext() && (party = iterator.next()) != null) if(party.isMember(uuid)) return party;
        return null;
    }
    public boolean isInParty(OnlineFriendPlayer player){
        return getParty(player) != null;
    }
    public Party createParty(OnlineFriendPlayer player){
        return createParty(player.getUUID());
    }
    public Party createParty(FriendPlayer player){
        return createParty(player.getUUID());
    }
    public Party createParty(UUID uuid){
        Party party = new Party(uuid);
        this.parties.add(party);
        return party;
    }
}
