package ch.dkrieger.friendsystem.lib.storage.sql.table;

/*
 *
 *  * Copyright (c) 2018 Davide Wietlisbach on 16.11.18 20:57
 *
 */

import ch.dkrieger.friendsystem.lib.storage.sql.MySQLFriendStorage;
import ch.dkrieger.friendsystem.lib.storage.sql.query.*;

public class Table {

    private String name;
    private MySQLFriendStorage mysql;

    public Table(MySQLFriendStorage mysql, String name){
        this.mysql = mysql;
        this.name = name;
    }
    public String getName(){
        return this.name;
    }
    public CreateQuery create(){
        return new CreateQuery(mysql.getConnection(),"CREATE TABLE IF NOT EXISTS `"+this.name+"` (");
    }
    public InsertQuery insert(){
        return new InsertQuery(mysql.getConnection(),"INSERT INTO `"+this.name+"` (");
    }
    public UpdateQuery update(){
        return new UpdateQuery(mysql.getConnection(),"UPDATE `"+this.name+"` SET");
    }
    public SelectQuery select(){
        return select("*");
    }
    public SelectQuery select(String selection){
        return new SelectQuery(mysql.getConnection(), "SELECT "+selection+" FROM `"+this.name+"`");
    }
    public DeleteQuery delete(){
        return new DeleteQuery(mysql.getConnection(), "DELETE FROM `"+this.name+"`");
    }
    public CustomQuery query(){
        return new CustomQuery(mysql.getConnection());
    }
}
