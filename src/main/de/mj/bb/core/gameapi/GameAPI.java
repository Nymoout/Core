package main.de.mj.bb.core.gameapi;

import main.de.mj.bb.core.CoreSpigot;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class GameAPI {

    private final CoreSpigot coreSpigot;
    private final MySQL mySQL;

    public GameAPI(@NotNull CoreSpigot coreSpigot) {
        this.coreSpigot = coreSpigot;
        this.mySQL = new MySQL(coreSpigot);
    }

    public CoreSpigot getCoreSpigot() {
        return coreSpigot;
    }

    public MySQL getMySQL() {
        return mySQL;
    }

    @Contract(value = "null -> false", pure = true)
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof GameAPI)) return false;
        final GameAPI other = (GameAPI) o;
        if (!other.canEqual(this)) return false;
        final Object this$coreSpigot = this.getCoreSpigot();
        final Object other$coreSpigot = other.getCoreSpigot();
        if (this$coreSpigot == null ? other$coreSpigot != null : !this$coreSpigot.equals(other$coreSpigot))
            return false;
        final Object this$mySQL = this.getMySQL();
        final Object other$mySQL = other.getMySQL();
        return this$mySQL == null ? other$mySQL == null : this$mySQL.equals(other$mySQL);
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $coreSpigot = this.getCoreSpigot();
        result = result * PRIME + ($coreSpigot == null ? 43 : $coreSpigot.hashCode());
        final Object $mySQL = this.getMySQL();
        result = result * PRIME + ($mySQL == null ? 43 : $mySQL.hashCode());
        return result;
    }

    protected boolean canEqual(Object other) {
        return other instanceof GameAPI;
    }

    public String toString() {
        return "GameAPI(coreSpigot=" + this.getCoreSpigot() + ", mySQL=" + this.getMySQL() + ")";
    }
}
