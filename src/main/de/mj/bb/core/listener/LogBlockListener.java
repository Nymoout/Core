package main.de.mj.bb.core.listener;

import main.de.mj.bb.core.CoreSpigot;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class LogBlockListener implements Listener {

    private final CoreSpigot coreSpigot;

    public LogBlockListener(CoreSpigot coreSpigot) {
        this.coreSpigot = coreSpigot;
        coreSpigot.setListener(this);
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent placeEvent) {
        Player player = placeEvent.getPlayer();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH,mm,ss");
        String date = "[" + calendar.get(Calendar.DAY_OF_MONTH) + "-" + calendar.get(Calendar.MONTH) + "-" + calendar.get(Calendar.YEAR) + " " + dateFormat.format(calendar.getTime()) + "]";
        coreSpigot.getModuleManager().getFileManager().setBlockConfig(date, player.getName(), placeEvent.getBlock().getType().toString(), placeEvent.getBlock().getX(), placeEvent.getBlock().getY(), placeEvent.getBlock().getZ(), true);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent breakEvent) {
        Player player = breakEvent.getPlayer();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH,mm,ss");
        String date = "[" + calendar.get(Calendar.DAY_OF_MONTH) + "-" + calendar.get(Calendar.MONTH) + "-" + calendar.get(Calendar.YEAR) + ", " + dateFormat.format(calendar.getTime()) + "]";
        coreSpigot.getModuleManager().getFileManager().setBlockConfig(date, player.getName(), breakEvent.getBlock().getType().toString(), breakEvent.getBlock().getX(), breakEvent.getBlock().getY(), breakEvent.getBlock().getZ(), false);
    }
}
