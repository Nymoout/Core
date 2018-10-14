package main.de.mj.bb.core;

import lombok.Getter;
import main.de.mj.bb.core.bots.discord.Discord;
import net.md_5.bungee.api.plugin.Plugin;

import javax.security.auth.login.LoginException;
import java.util.logging.Logger;

@Getter
public class CoreBungee extends Plugin {

    private Logger logger = this.getLogger();

    public void onEnable() {
        Discord discord = new Discord();
        try {
            discord.startBot();
        } catch (LoginException logex) {
            logex.printStackTrace();
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }
    }
}
