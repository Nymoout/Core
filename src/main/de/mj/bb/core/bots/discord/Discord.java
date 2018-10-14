package main.de.mj.bb.core.bots.discord;

import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.events.Event;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.hooks.EventListener;

import javax.security.auth.login.LoginException;

public class Discord implements EventListener {

    private JDA jda;

    public void startBot() throws LoginException, InterruptedException {
        jda = new JDABuilder("NDc2Nzg1NzQ2OTk3NjczOTk0.Dkyo7Q.DTkxRL_lojVj3elcgDnb9OtdH6A").addEventListener(new Discord()).build();
        jda.awaitReady();
        jda.setAutoReconnect(true);
    }

    @Override
    public void onEvent(Event event) {
        if (event instanceof ReadyEvent) {
            System.out.println("Discord Bot hochgefahren!");
            jda.getGuildsByName("BattleBuild", true).get(0).getTextChannelById("494964092860104704").sendMessage("Der BattleBuildBot ist nun online :) ... Lasst uns feiern!");
        }
    }
}
