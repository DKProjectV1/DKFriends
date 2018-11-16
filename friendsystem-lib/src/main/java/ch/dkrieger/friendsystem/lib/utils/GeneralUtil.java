package ch.dkrieger.friendsystem.lib.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;
import java.util.Random;

/*
 *
 *  * Copyright (c) 2018 Davide Wietlisbach on 16.11.18 08:35
 *
 */

public class GeneralUtil {

    public static final Random RANDOM = new Random();
    public static final GsonBuilder GSON_BUILDER = new GsonBuilder().setPrettyPrinting();
    public static Gson GSON = GSON_BUILDER.create();
    public static final JsonParser PARSER = new JsonParser();

    public static void createGSON(){
        GSON = GSON_BUILDER.create();
    }
    public static String getRandomString(final int size){
        char data = ' ';
        String dat = "";
        for(int i=0;i<=size;i++) {
            data = (char)(RANDOM.nextInt(25)+97);
            dat = data+dat;
        }
        return dat;
    }
    public static <U> U getHighestKey(final Map<U, Integer> map) {
        return map.entrySet().stream().sorted(Map.Entry.<U,Integer>comparingByValue().reversed()).limit(1).map(Map.Entry::getKey).findFirst().orElse(null);
    }
    public static int getMaxPages(int pagesize, List<?> list) {
        int max = pagesize;
        int i = list.size();
        if (i % max == 0) return i/max;
        double j = i / pagesize;
        int h = (int) Math.floor(j*100)/100;
        return h+1;
    }
    public static boolean isNumber(String value){
        try{
            Long.parseLong(value);
            return true;
        }catch(NumberFormatException exception){
            return false;
        }
    }
    public static String rotateString(String string){
        String newstring = "";
        char[] chararray = string.toCharArray();
        for(int i = chararray.length-1;i > -1;i--) newstring += chararray[i];
        return newstring;
    }
    public static Boolean equalsOne(String string, String... values){
        for(String value : values) if(value.equalsIgnoreCase(string)) return true;
        return false;
    }
    public static Boolean equalsALL(String string, String... values){
        for(String value : values) if(!value.equalsIgnoreCase(string)) return false;
        return true;
    }
    public static String encode(String password){
        return encode(password.getBytes());
    }
    public static String encode(byte[] bytes) {
        MessageDigest digest = getMessageDigest("MD5");
        byte[] hash = digest.digest(bytes);
        StringBuilder builder = new StringBuilder();
        for(int val : hash) builder.append(Integer.toHexString(val&0xff));
        return builder.toString();
    }
    public static String completEncode(String password){
        return completEncode(password.getBytes());
    }
    public static String completEncode(byte[] bytes){
        String d = encode(encode(bytes));
        String complethash = "";
        for(char c : d.toCharArray()) complethash+=encode(""+c);
        return complethash;
    }
    public static MessageDigest getMessageDigest(String name) {
        try {
            return MessageDigest.getInstance(name);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
