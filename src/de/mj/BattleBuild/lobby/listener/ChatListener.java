package de.mj.BattleBuild.lobby.listener;

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
    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        User user = LuckPerms.getApi().getUser(p.getUniqueId());
        ContextManager cm = LuckPerms.getApi().getContextManager();
        Contexts contexts = cm.lookupApplicableContexts(user).orElse(cm.getStaticContexts());
        MetaData md = user.getCachedData().getMetaData(contexts);
        String msg = e.getMessage().replace("%", "Prozent");
        String prefix = "";
        if (md.getPrefix() != null) {
            prefix = md.getPrefix();
        }
        String suffix = "";
        if (md.getSuffix() != null) {
            suffix = md.getSuffix();
        }
        if (!VIPHide.instance.isDisguised(p)) {
            if (p.hasPermission("chat.color")) {
                e.setFormat(prefix.replace("&", "§") + p.getName() + suffix.replace("&", "§") + msg.replace("&", "§"));
            } else {
                e.setFormat(prefix.replace("&", "§") + p.getName() + suffix.replace("&", "§") + msg);
            }
        }
    }
}
