/*
 * @author MJ
 * Created in 25.08.2018
 * Copyright (c) 2017 - 2018 by MJ. All rights reserved.
 *
 */

package main.de.mj.bb.core.listener;

import main.de.mj.bb.core.CoreSpigot;
import me.lucko.luckperms.api.Contexts;
import me.lucko.luckperms.api.User;
import me.lucko.luckperms.api.caching.MetaData;
import me.lucko.luckperms.api.context.ContextManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.jetbrains.annotations.NotNull;

public class ChatListener implements Listener {

    private final CoreSpigot coreSpigot;

    public ChatListener(@NotNull CoreSpigot coreSpigot) {
        this.coreSpigot = coreSpigot;
        coreSpigot.setListener(this);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onChat(AsyncPlayerChatEvent playerChatEvent) {
        Player player = playerChatEvent.getPlayer();
        User user = coreSpigot.getHookManager().getLuckPermsApi().getUser(player.getUniqueId());
        ContextManager cm = coreSpigot.getHookManager().getLuckPermsApi().getContextManager();
        Contexts contexts = cm.lookupApplicableContexts(user).orElse(cm.getStaticContexts());
        MetaData md = user.getCachedData().getMetaData(contexts);
        String msg = playerChatEvent.getMessage();
        String pmsg = msg;
        if (msg.contains("@")) {
            for (Player all : Bukkit.getOnlinePlayers()) {
                String name = all.getName();
                pmsg = msg.replace("@" + name, "§e@" + name + "§f");
            }
        }
        String prefix = "";
        if (md.getPrefix() != null) {
            prefix = md.getPrefix();
        }
        String suffix = "";
        if (md.getSuffix() != null) {
            suffix = md.getSuffix();
        }
        if (pmsg.contains("%")) pmsg = pmsg.replace("%", "Prozent");
        if (!coreSpigot.getModuleManager().getNickManager().isDisguised(player)) {
            if (player.hasPermission("chat.color")) {
                playerChatEvent.setFormat(prefix.replace("&", "§") + player.getName() + suffix.replace("&", "§") + pmsg.replace("&", "§").replace("<3", "\u2764").replace(":3", "\u2764"));
            } else {
                playerChatEvent.setFormat(prefix.replace("&", "§") + player.getName() + suffix.replace("&", "§") + pmsg.replace("<3", "\u2764").replace(":3", "\u2764"));
            }
        } else {
            playerChatEvent.setFormat("§7Spieler§8 | §7" + coreSpigot.getNickManager().getPlayerName().get(player) + "§8 » §7" + pmsg.replace("<3", "\u2764").replace(":3", "\u2764"));
        }
    }
}
