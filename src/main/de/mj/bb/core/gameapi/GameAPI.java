package main.de.mj.bb.core.gameapi;

import lombok.Getter;
import main.de.mj.bb.core.CoreSpigot;
import org.jetbrains.annotations.NotNull;

@Getter
public class GameAPI {

    private final CoreSpigot coreSpigot;
    private MySQL mySQL;

    public GameAPI(@NotNull CoreSpigot coreSpigot) {
        this.coreSpigot = coreSpigot;
        this.mySQL = new MySQL(coreSpigot);
    }

}
