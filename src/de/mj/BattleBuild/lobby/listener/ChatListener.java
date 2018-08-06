/*
 * @author MJ
 * Created in 25.08.2018
 * Copyright (c) 2017 - 2018 by MJ. All rights reserved.
 *
 */

package de.mj.BattleBuild.lobby.listener;

import de.mj.BattleBuild.lobby.Lobby;
import me.BukkitPVP.VIPHide.VIPHide;
import me.lucko.luckperms.LuckPerms;
import me.lucko.luckperms.api.Contexts;
import me.lucko.luckperms.api.User;
import me.lucko.luckperms.api.caching.MetaData;
import me.lucko.luckperms.api.context.ContextManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {

    private final Lobby lobby;

    public ChatListener(Lobby lobby) {
        this.lobby = lobby;
        lobby.setListener(this);
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent playerChatEvent) {
        Player player = playerChatEvent.getPlayer();
        User user = LuckPerms.getApi().getUser(player.getUniqueId());
        ContextManager cm = LuckPerms.getApi().getContextManager();
        Contexts contexts = cm.lookupApplicableContexts(user).orElse(cm.getStaticContexts());
        MetaData md = user.getCachedData().getMetaData(contexts);
        String msg = playerChatEvent.getMessage().replace("%", "Prozent");
        String prefix = "";
        if (md.getPrefix() != null) {
            prefix = md.getPrefix();
        }
        String suffix = "";
        if (md.getSuffix() != null) {
            suffix = md.getSuffix();
        }
        if (!VIPHide.instance.isDisguised(player)) {
            if (player.hasPermission("chat.color")) {
                playerChatEvent.setFormat(prefix.replace("&", "§") + player.getName() + suffix.replace("&", "§") + msg.replace("&", "§"));
            } else {
                playerChatEvent.setFormat(prefix.replace("&", "§") + player.getName() + suffix.replace("&", "§") + msg);
            }
        }
    }
}
