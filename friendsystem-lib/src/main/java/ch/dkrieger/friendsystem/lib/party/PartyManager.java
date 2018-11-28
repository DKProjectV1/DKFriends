package ch.dkrieger.friendsystem.lib.party;

/*
 *
 *  * Copyright (c) 2018 Davide Wietlisbach on 18.11.18 11:35
 *
 */

import ch.dkrieger.friendsystem.lib.player.FriendPlayer;
import ch.dkrieger.friendsystem.lib.player.OnlineFriendPlayer;
import ch.dkrieger.friendsystem.lib.utils.GeneralUtil;

import java.util.*;

public abstract class PartyManager {

    private Map<UUID,Party> parties;

    public PartyManager() {
        this.parties = new HashMap<>();
    }
    public Collection<Party> getParties() {
        return parties.values();
    }
    public Map<UUID,Party> getMapedParties(){
        return this.parties;
    }
    public List<Party> getPublicParties(){
        List<Party> parties = new ArrayList<>();
        Iterator<Party> iterator = new ArrayList<>(this.parties.values()).iterator();
        Party party = null;
        while(iterator.hasNext() && (party = iterator.next()) != null) if(party.isPublic()) parties.add(party);
        return parties;
    }

    public Party getParty(PartyMember partyMember) {
        return getParty(partyMember.getUUID());
    }

    public Party getParty(FriendPlayer player){
        return getParty(player.getUUID());
    }

    public Party getParty(OnlineFriendPlayer player){
        return getParty(player.getUUID());
    }

    public Party getParty(UUID player){
        Iterator<Party> iterator = new ArrayList<>(this.parties.values()).iterator();
        Party party = null;
        while(iterator.hasNext() && (party = iterator.next()) != null) if(party.isMember(player)) return party;
        return null;
    }

    public Party getRandomPublicParty(){
        List<Party> parties = getPublicParties();
        return parties.get(GeneralUtil.RANDOM.nextInt(parties.size()));
    }

    public boolean isInParty(FriendPlayer player){
        return isInParty(player.getUUID());
    }

    public boolean isInParty(OnlineFriendPlayer player){
        return isInParty(player.getUUID());
    }

    public boolean isInParty(UUID player){
        return getParty(player) != null;
    }

    public Party createParty(OnlineFriendPlayer player){
        return createParty(player.getUUID());
    }

    public Party createParty(FriendPlayer player){
        return createParty(player.getUUID());
    }

    public void deleteParty(Party party){
        this.parties.remove(party.getUUID());
    }

    public Party createParty(UUID player){
        UUID uuid = UUID.randomUUID();
        while(this.parties.containsKey(uuid)) uuid = UUID.randomUUID();
        Party party = new Party(uuid,player);
        this.parties.put(uuid,party);
        return party;
    }

    public abstract void update(Party party);
    public void replaceParty(Party party){
        this.parties.put(party.getUUID(),party);
    }
}
