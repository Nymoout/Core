package de.mj.BattleBuild.core.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

public class Title {

    private final Plugin plugin;

    public Title(Plugin plugin) {
        this.plugin = plugin;
    }

    public String getVersion() {
        return Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
    }

    public Class<?> getNMSClass(String name) {
        try {
            return Class.forName("net.minecraft.server." + getVersion() + "." + name);
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public void sendPacket(Player player, Object packet) {
        try {
            Object handle = player.getClass().getMethod("getHandle").invoke(player);
            Object playerConnection = handle.getClass().getField("playerConnection").get(handle);
            playerConnection.getClass().getMethod("sendPacket", getNMSClass("Packet"))
                    .invoke(playerConnection, packet);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @SuppressWarnings("rawtypes")
    public void sendTitle(Player p, Integer fadeIn, Integer stay, Integer fadeOut, String title,
                          String subtitle) {
        try {
            if (title != null) {
                title = ChatColor.translateAlternateColorCodes('&', title);

                Object e = getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("TIMES").get(null);
                Object chatTitle = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0]
                        .getMethod("a", String.class)
                        .invoke(null, "{\"text\":\"" + title + "\"}");
                Constructor subtitleConstructor = getNMSClass("PacketPlayOutTitle")
                        .getConstructor(getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0],
                                getNMSClass("IChatBaseComponent"), Integer.TYPE, Integer.TYPE, Integer.TYPE);
                Object titlePacket = subtitleConstructor
                        .newInstance(e, chatTitle, fadeIn, stay, fadeOut);
                sendPacket(p, titlePacket);

                e = getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("TITLE").get(null);
                chatTitle = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0]
                        .getMethod("a", String.class)
                        .invoke(null, "{\"text\":\"" + title + "\"}");
                subtitleConstructor = getNMSClass("PacketPlayOutTitle").getConstructor(getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0], getNMSClass("IChatBaseComponent"));
                titlePacket = subtitleConstructor.newInstance(e, chatTitle);
                sendPacket(p, titlePacket);
            }
            if (subtitle != null) {
                subtitle = ChatColor.translateAlternateColorCodes('&', subtitle);
                Object e = getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("TIMES").get(null);
                Object chatSubtitle = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0]
                        .getMethod("a", String.class)
                        .invoke(null, "{\"text\":\"" + title + "\"}");
                Constructor subtitleConstructor = getNMSClass("PacketPlayOutTitle")
                        .getConstructor(getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0],
                                getNMSClass("IChatBaseComponent"), Integer.TYPE, Integer.TYPE, Integer.TYPE);
                Object subtitlePacket = subtitleConstructor
                        .newInstance(e, chatSubtitle, fadeIn, stay, fadeOut);
                sendPacket(p, subtitlePacket);

                e = getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("SUBTITLE").get(null);
                chatSubtitle = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0]
                        .getMethod("a", String.class)
                        .invoke(null, "{\"text\":\"" + subtitle + "\"}");
                subtitleConstructor = getNMSClass("PacketPlayOutTitle")
                        .getConstructor(getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0],
                                getNMSClass("IChatBaseComponent"), Integer.TYPE, Integer.TYPE, Integer.TYPE);
                subtitlePacket = subtitleConstructor
                        .newInstance(e, chatSubtitle, fadeIn, stay, fadeOut);
                sendPacket(p, subtitlePacket);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void sendTabTitle(Player player, String header, String footer) {
        if (header == null)
            header = "";
        if (footer == null)
            footer = "";
        header = ChatColor.translateAlternateColorCodes('&', header);
        footer = ChatColor.translateAlternateColorCodes('&', footer);
        try {
            Object headertext = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0]
                    .getMethod("a", String.class)
                    .invoke(null, "{\"text\":\"" + header + "\"}");
            Object footertext = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0]
                    .getMethod("a", String.class)
                    .invoke(null, "{\"text\":\"" + footer + "\"}");
            Object packet = getNMSClass("PacketPlayOutPlayerListHeaderFooter")
                    .getConstructor(new Class[]{getNMSClass("IChatBaseComponent")})
                    .newInstance(headertext);
            Field f = packet.getClass().getDeclaredField("b");
            f.setAccessible(true);
            f.set(packet, footertext);
            sendPacket(player, packet);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    public void sendActionBar(Player p, String msg) {
        try {
            Class c_craftplayer = Class.forName("org.bukkit.craftbukkit." + getVersion() + ".entity.CraftPlayer");
            Object cp = c_craftplayer.cast(p);
            Object packet;
            String ver = getVersion();
            if (((ver.equalsIgnoreCase("v1_8_R1")) || (!ver.startsWith("v1_8_"))) && (!ver.startsWith("v1_9_"))) {
                Object comp = getNMSClass("IChatBaseComponent")
                        .cast(getNMSClass("ChatSerializer").getDeclaredMethod("a", String.class)
                                .invoke(getNMSClass("ChatSerializer"), "{\"text\": \"" + msg + "\"}"));
                packet = getNMSClass("PacketPlayOutChat")
                        .getConstructor(new Class[]{getNMSClass("IChatBaseComponent"), Byte.TYPE})
                        .newInstance(comp, Byte.valueOf((byte) 2));
            } else {
                Object o = getNMSClass("ChatComponentText").getConstructor(new Class[]{String.class})
                        .newInstance(msg);
                packet = getNMSClass("PacketPlayOutChat")
                        .getConstructor(new Class[]{getNMSClass("IChatBaseComponent"), Byte.TYPE})
                        .newInstance(o, Byte.valueOf((byte) 2));
            }
            Object handle = c_craftplayer.getDeclaredMethod("getHandle").invoke(cp);
            Object pc = handle.getClass().getDeclaredField("playerConnection").get(handle);
            pc.getClass().getDeclaredMethod("sendPacket", getNMSClass("Packet")).invoke(pc,
                    packet);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}

