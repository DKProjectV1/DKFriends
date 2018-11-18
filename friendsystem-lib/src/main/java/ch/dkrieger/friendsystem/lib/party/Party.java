package ch.dkrieger.friendsystem.lib.party;

/*
 *
 *  * Copyright (c) 2018 Davide Wietlisbach on 18.11.18 11:32
 *
 */

import ch.dkrieger.friendsystem.lib.player.FriendPlayer;
import ch.dkrieger.friendsystem.lib.player.OnlineFriendPlayer;

import java.util.*;

public class Party {

    private boolean public0;
    private long timeOut;
    private List<PartyPlayer> members;
    private List<UUID> requests;

    public Party(OnlineFriendPlayer leader) {
        this.members = new ArrayList<>();
        this.requests = new ArrayList<>();
        this.members.add(new PartyPlayer(leader.getUUID(),true));
    }
    public List<PartyPlayer> getMembers() {
        return members;
    }
    public List<UUID> getRequests() {
        return requests;
    }
    public long getTimeOut() {
        return timeOut;
    }
    public PartyPlayer getLeader(){
        Iterator<PartyPlayer> iterator = new ArrayList<>(this.members).iterator();
        PartyPlayer player = null;
        while(iterator.hasNext() && (player= iterator.next()) != null) if(player.isLeader()) return player;
        return null;
    }
    public List<PartyPlayer> getModerators(){
        List<PartyPlayer> players = new ArrayList<>();
        Iterator<PartyPlayer> iterator = new ArrayList<>(this.members).iterator();
        PartyPlayer player = null;
        while(iterator.hasNext() && (player= iterator.next()) != null) if(!(player.isLeader()) && player.isModerator()) players.add(player);
        return players;
    }
    public List<PartyPlayer> getNormalMembers(){
        List<PartyPlayer> players = new ArrayList<>();
        Iterator<PartyPlayer> iterator = new ArrayList<>(this.members).iterator();
        PartyPlayer player = null;
        while(iterator.hasNext() && (player= iterator.next()) != null) if(!(player.isLeader()) && !(player.isModerator())) players.add(player);
        return players;
    }
    public PartyPlayer getPlayer(FriendPlayer player){
        return getPlayer(player.getUUID());
    }
    public PartyPlayer getPlayer(OnlineFriendPlayer player){
        return getPlayer(player.getUUID());
    }
    public PartyPlayer getPlayer(UUID uuid){
        Iterator<PartyPlayer> iterator = new ArrayList<>(this.members).iterator();
        PartyPlayer player = null;
        while(iterator.hasNext() && (player= iterator.next()) != null) if(player.getUUID().equals(uuid)) return player;
        return null;
    }
    public boolean isPublic() {
        return public0;
    }
    public boolean isMember(FriendPlayer player) {
        return isMember(player.getUUID());
    }
    public boolean isMember(OnlineFriendPlayer player) {
        return  isMember(player.getUUID());
    }
    public boolean isMember(UUID uuid) {
        return getPlayer(uuid) != null;
    }
    public boolean isLeader(FriendPlayer player) {
        return isLeader(player.getUUID());
    }
    public boolean isLeader(OnlineFriendPlayer player) {
        return isLeader(player.getUUID());
    }
    public boolean isLeader(UUID uuid) {
        return getPlayer(uuid) != null;
    }
    public boolean hasRequest(FriendPlayer player) {
        return hasRequest(player.getUUID());
    }
    public boolean hasRequest(OnlineFriendPlayer player) {
        return hasRequest(player.getUUID());
    }
    public boolean hasRequest(UUID uuid) {
        return this.requests.contains(uuid);
    }
    public void setPublic(boolean public0) {
        this.public0 = public0;
    }
    public void setTimeOut(long timeOut) {
        this.timeOut = timeOut;
    }
    public void sendMessage(String message){

    }
    public void connect(String server){

    }
}
