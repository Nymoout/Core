package main.de.mj.bb.core.gameapi;

import lombok.Getter;
import main.de.mj.bb.core.CoreSpigot;

@Getter
public class GameAPI {

    private MySQL mySQL;

    public GameAPI(CoreSpigot coreSpigot) {
        this.mySQL = new MySQL(coreSpigot);
    }

}
