package de.mj.BattleBuild.lobby.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class Title {

    private Plugin plugin;

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
            Object handle = player.getClass().getMethod("getHandle", new Class[0]).invoke(player, new Object[0]);
            Object playerConnection = handle.getClass().getField("playerConnection").get(handle);
            playerConnection.getClass().getMethod("sendPacket", new Class[] { getNMSClass("Packet") })
                    .invoke(playerConnection, new Object[] { packet });
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
                        .getMethod("a", new Class[] { String.class })
                        .invoke(null, new Object[] { "{\"text\":\"" + title + "\"}" });
                Constructor subtitleConstructor = getNMSClass("PacketPlayOutTitle")
                        .getConstructor(new Class[] { getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0],
                                getNMSClass("IChatBaseComponent"), Integer.TYPE, Integer.TYPE, Integer.TYPE });
                Object titlePacket = subtitleConstructor
                        .newInstance(new Object[] { e, chatTitle, fadeIn, stay, fadeOut });
                sendPacket(p, titlePacket);

                e = getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("TITLE").get(null);
                chatTitle = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0]
                        .getMethod("a", new Class[] { String.class })
                        .invoke(null, new Object[] { "{\"text\":\"" + title + "\"}" });
                subtitleConstructor = getNMSClass("PacketPlayOutTitle").getConstructor(new Class[] {
                        getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0], getNMSClass("IChatBaseComponent") });
                titlePacket = subtitleConstructor.newInstance(new Object[] { e, chatTitle });
                sendPacket(p, titlePacket);
            }
            if (subtitle != null) {
                subtitle = ChatColor.translateAlternateColorCodes('&', subtitle);
                Object e = getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("TIMES").get(null);
                Object chatSubtitle = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0]
                        .getMethod("a", new Class[] { String.class })
                        .invoke(null, new Object[] { "{\"text\":\"" + title + "\"}" });
                Constructor subtitleConstructor = getNMSClass("PacketPlayOutTitle")
                        .getConstructor(new Class[] { getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0],
                                getNMSClass("IChatBaseComponent"), Integer.TYPE, Integer.TYPE, Integer.TYPE });
                Object subtitlePacket = subtitleConstructor
                        .newInstance(new Object[] { e, chatSubtitle, fadeIn, stay, fadeOut });
                sendPacket(p, subtitlePacket);

                e = getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("SUBTITLE").get(null);
                chatSubtitle = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0]
                        .getMethod("a", new Class[] { String.class })
                        .invoke(null, new Object[] { "{\"text\":\"" + subtitle + "\"}" });
                subtitleConstructor = getNMSClass("PacketPlayOutTitle")
                        .getConstructor(new Class[] { getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0],
                                getNMSClass("IChatBaseComponent"), Integer.TYPE, Integer.TYPE, Integer.TYPE });
                subtitlePacket = subtitleConstructor
                        .newInstance(new Object[] { e, chatSubtitle, fadeIn, stay, fadeOut });
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
                    .getMethod("a", new Class[] { String.class })
                    .invoke(null, new Object[] { "{\"text\":\"" + header + "\"}" });
            Object footertext = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0]
                    .getMethod("a", new Class[] { String.class })
                    .invoke(null, new Object[] { "{\"text\":\"" + footer + "\"}" });
            Object packet = getNMSClass("PacketPlayOutPlayerListHeaderFooter")
                    .getConstructor(new Class[] { getNMSClass("IChatBaseComponent") })
                    .newInstance(new Object[] { headertext });
            Field f = packet.getClass().getDeclaredField("b");
            f.setAccessible(true);
            f.set(packet, footertext);
            sendPacket(player, packet);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void sendActionBar(Player p, String msg) {
        try {
            Class c_craftplayer = Class.forName("org.bukkit.craftbukkit." + getVersion() + ".entity.CraftPlayer");
            Object cp = c_craftplayer.cast(p);
            Object packet;
            String ver = getVersion();
            if (((ver.equalsIgnoreCase("v1_8_R1")) || (!ver.startsWith("v1_8_"))) && (!ver.startsWith("v1_9_"))) {
                Object comp = getNMSClass("IChatBaseComponent")
                        .cast(getNMSClass("ChatSerializer").getDeclaredMethod("a", new Class[] { String.class })
                                .invoke(getNMSClass("ChatSerializer"), new Object[] { "{\"text\": \"" + msg + "\"}" }));
                packet = getNMSClass("PacketPlayOutChat")
                        .getConstructor(new Class[] { getNMSClass("IChatBaseComponent"), Byte.TYPE })
                        .newInstance(new Object[] { comp, Byte.valueOf((byte) 2) });
            } else {
                Object o = getNMSClass("ChatComponentText").getConstructor(new Class[] { String.class })
                        .newInstance(new Object[] { msg });
                packet = getNMSClass("PacketPlayOutChat")
                        .getConstructor(new Class[] { getNMSClass("IChatBaseComponent"), Byte.TYPE })
                        .newInstance(new Object[] { o, Byte.valueOf((byte) 2) });
            }
            Object handle = c_craftplayer.getDeclaredMethod("getHandle", new Class[0]).invoke(cp, new Object[0]);
            Object pc = handle.getClass().getDeclaredField("playerConnection").get(handle);
            pc.getClass().getDeclaredMethod("sendPacket", new Class[] { getNMSClass("Packet") }).invoke(pc,
                    new Object[] { packet });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}

