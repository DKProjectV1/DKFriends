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
    private List<PartyMember> members;
    private List<UUID> requests;

    public Party(FriendPlayer leader) {
        this(leader.getUUID());
    }
    public Party(OnlineFriendPlayer leader) {
        this(leader.getUUID());
    }
    public Party(UUID uuid) {
        this.members = new ArrayList<>();
        this.requests = new ArrayList<>();
        this.members.add(new PartyMember(uuid,true));
    }
    public List<PartyMember> getMembers() {
        return members;
    }
    public List<PartyMember> getSortedMembers() {
        List<PartyMember> members = new ArrayList<>();
        members.add(getLeader());
        members.addAll(getModerators());
        members.addAll(getNormalMembers());
        return members;
    }
    public List<UUID> getRequests() {
        return requests;
    }
    public long getTimeOut() {
        return timeOut;
    }
    public PartyMember getLeader(){
        Iterator<PartyMember> iterator = new ArrayList<>(this.members).iterator();
        PartyMember player = null;
        while(iterator.hasNext() && (player= iterator.next()) != null) if(player.isLeader()) return player;
        return null;
    }
    public List<PartyMember> getModerators(){
        List<PartyMember> players = new ArrayList<>();
        Iterator<PartyMember> iterator = new ArrayList<>(this.members).iterator();
        PartyMember player = null;
        while(iterator.hasNext() && (player= iterator.next()) != null) if(!(player.isLeader()) && player.isModerator()) players.add(player);
        return players;
    }
    public List<PartyMember> getNormalMembers(){
        List<PartyMember> players = new ArrayList<>();
        Iterator<PartyMember> iterator = new ArrayList<>(this.members).iterator();
        PartyMember player = null;
        while(iterator.hasNext() && (player= iterator.next()) != null) if(!(player.isLeader()) && !(player.isModerator())) players.add(player);
        return players;
    }
    public PartyMember getMember(FriendPlayer player){
        return getMember(player.getUUID());
    }
    public PartyMember getMember(OnlineFriendPlayer player){
        return getMember(player.getUUID());
    }
    public PartyMember getMember(UUID uuid){
        Iterator<PartyMember> iterator = new ArrayList<>(this.members).iterator();
        PartyMember player = null;
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
        return getMember(uuid) != null;
    }
    public boolean isLeader(FriendPlayer player) {
        return isLeader(player.getUUID());
    }
    public boolean isLeader(OnlineFriendPlayer player) {
        return isLeader(player.getUUID());
    }
    public boolean isLeader(UUID uuid) {
        return getMember(uuid) != null;
    }
    public boolean isModerator(FriendPlayer player) {
        return isModerator(player.getUUID());
    }
    public boolean isModerator(OnlineFriendPlayer player) {
        return isModerator(player.getUUID());
    }
    public boolean isModerator(UUID uuid) {
        PartyMember member =  getMember(uuid);
        return member != null && member.isModerator();
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
    public void addRequest(FriendPlayer player){
        addRequest(player.getUUID());
    }
    public void addRequest(OnlineFriendPlayer player) {
        addRequest(player.getUUID());
    }
    public void addRequest(UUID uuid){
        this.requests.add(uuid);
    }
    public void removeRequest(FriendPlayer player){
        removeRequest(player.getUUID());
    }
    public void removeRequest(OnlineFriendPlayer player){
        removeRequest(player.getUUID());
    }
    public void removeRequest(UUID uuid){
        this.requests.remove(uuid);
    }
    public void addMember(FriendPlayer player){
        addMember(player.getUUID());
    }
    public void addMember(OnlineFriendPlayer player) {
        addMember(player.getUUID());
    }
    public void addMember(UUID uuid){
        this.requests.remove(uuid);
        this.members.add(new PartyMember(uuid));
    }
    public void removeMember(FriendPlayer player){
        removeMember(player.getUUID());
    }
    public void removeMember(OnlineFriendPlayer player){
        removeMember(player.getUUID());
    }
    public void removeMember(UUID uuid){
        this.members.remove(getMember(uuid));
    }
    public void setModerator(FriendPlayer player, boolean moderator){
        setModerator(player.getUUID(),moderator);
    }
    public void setModerator(OnlineFriendPlayer player, boolean moderator){
        setModerator(player.getUUID(),moderator);
    }
    public void setModerator(UUID uuid, boolean moderator){
        PartyMember member = getMember(uuid);
        if(member != null) member.setModerator(moderator);
    }
    public void setLeader(FriendPlayer player){
        setLeader(player.getUUID());
    }
    public void setLeader(OnlineFriendPlayer player){
        setLeader(player.getUUID());
    }
    public void setLeader(UUID uuid){
        getLeader().setLeader(false);
        PartyMember member = getMember(uuid);
        if(member != null) member.setModerator(true);
    }
    public void sendMessage(String message){
        for(PartyMember member : this.members){
            OnlineFriendPlayer online = member.getOnlinePlayer();
            if(online != null) online.sendMessage(message);
        }
    }
    public void connect(String server){

    }
}
