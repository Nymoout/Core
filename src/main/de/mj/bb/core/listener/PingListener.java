package main.de.mj.bb.core.listener;

import main.de.mj.bb.core.CoreBungee;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;

import java.util.Arrays;
import java.util.List;

public class PingListener implements Listener {

    private final CoreBungee coreBungee;


    public PingListener(CoreBungee coreBungee) {
        this.coreBungee = coreBungee;
        coreBungee.registerListener(this);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPing(ProxyPingEvent pingEvent) {
        if (coreBungee.getModuleManager().getBungeeAPI().isMaintenance()) {
            pingEvent.getResponse().setDescriptionComponent(new TextComponent("§6§lBattleBuild §8§l✘ §5§l☣§a Dein Netzwerk §5§l☣ §8§l✘ §f[§b1.8§8-§b1.13.§b§kn§r§f]\n§8§l➥ §c§lWARTUNG §8§l➜ §7§lEröffnung am §301.01.2019 | 16 Uhr"));
            List<String> lines = Arrays.asList(
                    "§e§l☷☷☷☷☷☷☷☷☷☷ §8§l✘ §6§lBattleBuild §8§l✘ §e§l☷☷☷☷☷☷☷☷☷☷",
                    "§8§l➽ §c§lWARTUNGSARBEITEN",
                    "§8§l➽ §7Komm auf unseren TS: §9battlebuild.net",
                    "§8§l➽ §7Komm auf unseren Discord: §2discord.battlebuild.net",
                    "§8§l➽ §7Folge uns auf Twitter: §5@battlebuildNET",
                    "§8§l➽ §7Besuche unser Forum: §4forum.battlebuild.net",
                    "§e§l☷☷☷☷☷☷☷☷☷☷☷☷☷☷☷☷☷☷☷☷☷☷☷☷☷☷☷☷☷☷☷"
            );
            ServerPing.PlayerInfo[] sample = new ServerPing.PlayerInfo[lines.size()];
            for (int i = 0; i < sample.length; i++) {

                sample[i] = new ServerPing.PlayerInfo(lines.get(i), "");
            }
            pingEvent.getResponse().getPlayers().setSample(sample);
            pingEvent.getResponse().setVersion(new ServerPing.Protocol("§8§l➽ §c§lWartungsmodus", 404));
        } else {
            pingEvent.getResponse().setDescriptionComponent(new TextComponent("§6§lBattleBuild §8§l✘ §5§l☣§a Dein Netzwerk §5§l☣ §8§l✘ §f[§b1.8§8-§b1.13.§b§kn§r§f]\n§8§l➥ §c§lOPEN BETA §8§l➜ §7§lEröffnung am §301.01.2019 | 16 Uhr"));
            List<String> lines = Arrays.asList(
                    "§e§l☷☷☷☷☷☷☷☷☷☷ §8§l✘ §6§lBattleBuild §8§l✘ §e§l☷☷☷☷☷☷☷☷☷☷",
                    "§8§l➽ §7Komm auf unseren TS: §9battlebuild.net",
                    "§8§l➽ §7Komm auf unseren Discord: §2discord.battlebuild.net",
                    "§8§l➽ §7Folge uns auf Twitter: §5@battlebuildNET",
                    "§8§l➽ §7Besuche unser Forum: §4forum.battlebuild.net",
                    "§e§l☷☷☷☷☷☷☷☷☷☷☷☷☷☷☷☷☷☷☷☷☷☷☷☷☷☷☷☷☷☷☷"
            );
            ServerPing.PlayerInfo[] sample = new ServerPing.PlayerInfo[lines.size()];
            for (int i = 0; i < sample.length; i++) {

                sample[i] = new ServerPing.PlayerInfo(lines.get(i), "");
            }
            pingEvent.getResponse().getPlayers().setSample(sample);
        }
    }
}
