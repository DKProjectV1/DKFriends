package ch.dkrieger.friendsystem.spigot.util;

import ch.dkrieger.friendsystem.spigot.api.Reflection;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.chat.ComponentSerializer;
import org.apache.commons.codec.binary.Base64;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;

import java.lang.reflect.Constructor;
import java.util.LinkedList;
import java.util.List;
import static ch.dkrieger.friendsystem.spigot.api.Reflection.*;

/*
 *
 *  * Copyright (c) 2018 Philipp Elvin Friedhoff on 17.11.18 13:17
 *
 */

public class SpigotUtil {

    public static Base64 BASE64 = new Base64();

    public static List<ItemStack> getInventoryItems(Player player){
        List<ItemStack> items = new LinkedList<>();
        for(int i = 0;i < player.getInventory().getSize();i++){
            if(player.getInventory().getItem(i) != null) items.add(player.getInventory().getItem(i));
        }
        if(player.getInventory().getHelmet() != null) items.add(player.getInventory().getHelmet());
        if(player.getInventory().getChestplate() != null) items.add(player.getInventory().getChestplate());
        if(player.getInventory().getLeggings() != null) items.add(player.getInventory().getLeggings());
        if(player.getInventory().getBoots() != null) items.add(player.getInventory().getBoots());
        return items;
    }
    public static int getInventoryLines(Integer size){
        return (int) Math.ceil(size.doubleValue()/9);
    }
    public static int getInventoryAmount(Integer size){
        return (int) Math.ceil(size.doubleValue()/54);
    }
    public static BlockFace getPlayerDirection(Player player) {
        double rotation = (player.getLocation().getYaw() - 90) % 360;
        if (rotation < 0)  rotation += 360.0;
        if (0 <= rotation && rotation < 22.5) {
            return BlockFace.WEST;
        } else if (22.5 <= rotation && rotation < 67.5) {
            return BlockFace.NORTH_WEST;
        } else if (67.5 <= rotation && rotation < 112.5) {
            return BlockFace.NORTH;
        } else if (112.5 <= rotation && rotation < 157.5) {
            return BlockFace.NORTH_EAST;
        } else if (157.5 <= rotation && rotation < 202.5) {
            return BlockFace.EAST;
        } else if (202.5 <= rotation && rotation < 247.5) {
            return BlockFace.SOUTH_EAST;
        } else if (247.5 <= rotation && rotation < 292.5) {
            return BlockFace.SOUTH;
        }else if (292.5 <= rotation && rotation < 337.5) {
            return BlockFace.SOUTH_WEST;
        }else if (337.5 <= rotation && rotation < 360.0) {
            return BlockFace.WEST;
        }else {
            return null;
        }
    }

    public static int getFreeInventoryPlaces(Inventory inventory) {
        int freePlaces = 0;
        for(ItemStack itemStack : inventory.getContents()) if(itemStack == null || itemStack.getType() == Material.AIR) freePlaces++;
        return freePlaces;
    }

    public static double getArmorPoints(Player player) {
        PlayerInventory inventory = player.getInventory();
        ItemStack boots = inventory.getBoots();
        ItemStack helmet = inventory.getHelmet();
        ItemStack chestplate = inventory.getChestplate();
        ItemStack leggings = inventory.getLeggings();
        double armorpoints = 0.0;

        if(helmet != null) {
            if (helmet.getType() == Material.LEATHER_HELMET) armorpoints = armorpoints + 0.5;
            else if (helmet.getType() == Material.GOLD_HELMET) armorpoints = armorpoints + 1;
            else if (helmet.getType() == Material.CHAINMAIL_HELMET) armorpoints = armorpoints + 1;
            else if (helmet.getType() == Material.IRON_HELMET) armorpoints = armorpoints + 1;
            else if (helmet.getType() == Material.DIAMOND_HELMET) armorpoints = armorpoints + 1.5;
        }
        if(boots != null) {
            if (boots.getType() == Material.LEATHER_BOOTS) armorpoints = armorpoints + 0.5;
            else if (boots.getType() == Material.GOLD_BOOTS) armorpoints = armorpoints + 0.5;
            else if (boots.getType() == Material.CHAINMAIL_BOOTS) armorpoints = armorpoints + 0.5;
            else if (boots.getType() == Material.IRON_BOOTS) armorpoints = armorpoints + 1;
            else if (boots.getType() == Material.DIAMOND_BOOTS) armorpoints = armorpoints + 1.5;
        }
        if(leggings != null) {
            if (leggings.getType() == Material.LEATHER_LEGGINGS) armorpoints = armorpoints + 1;
            else if (leggings.getType() == Material.GOLD_LEGGINGS) armorpoints = armorpoints + 1.5;
            else if (leggings.getType() == Material.CHAINMAIL_LEGGINGS) armorpoints = armorpoints + 5;
            else if (leggings.getType() == Material.IRON_LEGGINGS) armorpoints = armorpoints + 2.5;
            else if (leggings.getType() == Material.DIAMOND_LEGGINGS) armorpoints = armorpoints + 3;
        }
        if(chestplate != null) {
            if (chestplate.getType() == Material.LEATHER_CHESTPLATE) armorpoints = armorpoints + 1.5;
            else if (chestplate.getType() == Material.GOLD_CHESTPLATE) armorpoints = armorpoints + 2.5;
            else if (chestplate.getType() == Material.CHAINMAIL_CHESTPLATE) armorpoints = armorpoints + 2.5;
            else if (chestplate.getType() == Material.IRON_CHESTPLATE) armorpoints = armorpoints + 3;
            else if (chestplate.getType() == Material.DIAMOND_CHESTPLATE) armorpoints = armorpoints + 4;
        }
        return armorpoints;
    }
    public static String getDurationString(int seconds) {
        int minutes = (seconds % 3600) / 60;
        seconds = seconds % 60;
        return "§e"+twoDigitString(minutes) + "§8:§e" + twoDigitString(seconds);
    }
    public static String twoDigitString(int number) {
        if (number == 0) return "00";
        else if (number / 10 == 0) return "0" + number;
        return String.valueOf(number);
    }
    public static void setSpeed(Player player, SpeedType type, int speedlevel){
        if(type == SpeedType.CHECK) type = player.isFlying() ? SpeedType.FLY : SpeedType.WALK;
        if(speedlevel < 0) speedlevel = 0;
        if(type == SpeedType.FLY && speedlevel > 10) speedlevel = 10;
        else if(type == SpeedType.WALK &&  speedlevel > 5) speedlevel = 5;
        if(type == SpeedType.WALK) player.setWalkSpeed(0.2f*speedlevel);
        else player.setFlySpeed(0.1f*speedlevel);
    }
    public static void clearInventory(Player player){
        player.getInventory().clear();
        player.getInventory().setArmorContents(null);
    }
    public static void clearEffects(Player player){
        for(PotionEffect effect : player.getActivePotionEffects()) player.removePotionEffect(effect.getType());
    }
    public static void dropInventory(Player player){
        dropInventory(player,player.getLocation());
    }
    public static void dropInventory(Player player, Location location){
        dropItems(getInventoryItems(player),location);
    }
    public static void dropItems(List<ItemStack> items, Location location){
        for(ItemStack item : items) location.getWorld().dropItemNaturally(location,item);
    }

    public void sendMessage(Player player, ChatMessageType position,BaseComponent... components){
        /*ComponentSerializer.toString(components)


        IChatBaseComponent component = IChatBaseComponent.ChatSerializer.a(ComponentSerializer.toString(components));
        PacketPlayOutChat packet = new PacketPlayOutChat(component, (byte) position.ordinal());
        ((Class.forName("")) player).1117getHandle().playerConnection.sendPacket(packet);

        Object enumSubTitle = getNMSClass("PacketPlayOutTitle.EnumTitleAction").getField("SUBTITLE").get(null);
        Class<?> IComponent = getMinecraftClass("IChatBaseComponent.PacketPlayOutChat");
        IComponent.getConstructor(getMinecraftClass(".IChatBaseComponent.PacketPlayOutChat"))





        try {
            Constructor<?> chatConstructor = getMinecraftClass("PacketPlayOutChat").getConstructor(int[].class);
            Object chat = chatConstructor.newInstance(new int[]{(int)this.player.getClass().getMethod("getEntityId").invoke(this.player)});
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        /*

        IChatBaseComponent component = IChatBaseComponent.ChatSerializer.a(ComponentSerializer.toString(components));
        PacketPlayOutChat packet = new PacketPlayOutChat(component, (byte) position.ordinal());
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);


public void sendMessage(Player player, ChatMessageType position, BaseComponent... components) {
        if (player == null) {
            return;
        }
        IChatBaseComponent component = IChatBaseComponent.ChatSerializer.a(ComponentSerializer.toString(components));
        PacketPlayOutChat packet = new PacketPlayOutChat(component, (byte) position.ordinal());
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
    }



        import net.minecraft.server.v1_11_R1.IChatBaseComponent;
import net.minecraft.server.v1_11_R1.PacketPlayOutChat;

        private static ChatComponentPacket getPacket() {
        String packageName = Bukkit.getServer().getClass().getPackage().getName();
        String version = packageName.substring(packageName.lastIndexOf('.') + 1);
        String path = ComponentSender.class.getPackage().getName() + ".nms." + version;

        try {
            final Class<?> clazz = Class.forName(path + ".ChatComponentPacketHandler");
            if (ChatComponentPacket.class.isAssignableFrom(clazz)) {
                return (ChatComponentPacket) clazz.getConstructor().newInstance();
            }
        } catch (Exception ignore) {
            Bukkit.getLogger().info("[ERROR] This plugin is not compatible with this server version (" + version + ").");
            Bukkit.getLogger().info("[ERROR] Could not send chat packet!");
            ignore.printStackTrace();
        }
        return null;

        IChatBaseComponent component = IChatBaseComponent.ChatSerializer.a(ComponentSerializer.toString(components));
        PacketPlayOutChat packet = new PacketPlayOutChat(component, (byte) position.ordinal());
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
    }
         */
    }

    public enum SpeedType {
        WALK("Wal Speed"),
        FLY("Fly Speed"),
        CHECK("Check");

        private String name;

        SpeedType(String name){
            this.name = name;
        }
        public String getName() {
            return name;
        }
        public String getName(Player player) {
            if(this == CHECK) return player.isFlying() ? FLY.getName() : WALK.getName();
            else return getName();
        }
    }
}