/*
 * @author MJ
 * Created in 25.08.2018
 * Copyright (c) 2017 - 2018 by MJ. All rights reserved.
 *
 */

package de.mj.BattleBuild.core.listener;

import de.mj.BattleBuild.core.Core;
import me.lucko.luckperms.api.Contexts;
import me.lucko.luckperms.api.User;
import me.lucko.luckperms.api.caching.MetaData;
import me.lucko.luckperms.api.context.ContextManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {

    private final Core core;

    public ChatListener(Core core) {
        this.core = core;
        core.setListener(this);
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent playerChatEvent) {
        Player player = playerChatEvent.getPlayer();
        User user = core.getHookManager().getLuckPermsApi().getUser(player.getUniqueId());
        ContextManager cm = core.getHookManager().getLuckPermsApi().getContextManager();
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
        if (!core.getHookManager().getVipHide().isDisguised(player)) {
            if (player.hasPermission("chat.color")) {
                playerChatEvent.setFormat(prefix.replace("&", "§") + player.getName() + suffix.replace("&", "§") + msg.replace("&", "§"));
            } else {
                playerChatEvent.setFormat(prefix.replace("&", "§") + player.getName() + suffix.replace("&", "§") + msg);
            }
        }
    }
}
