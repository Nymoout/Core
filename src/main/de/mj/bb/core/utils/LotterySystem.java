package main.de.mj.bb.core.utils;

import main.de.mj.bb.core.CoreSpigot;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

public class LotterySystem {

    /* TODO */
    private final CoreSpigot coreSpigot;
    private int timer = 10;
    private Set<Player> player = new HashSet<>();

    public LotterySystem(CoreSpigot coreSpigot) {
        this.coreSpigot = coreSpigot;
    }

}
