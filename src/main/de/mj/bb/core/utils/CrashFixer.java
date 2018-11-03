package main.de.mj.bb.core.utils;

import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.utility.StreamSerializer;
import com.comphenix.protocol.wrappers.nbt.NbtCompound;
import com.comphenix.protocol.wrappers.nbt.NbtFactory;
import com.comphenix.protocol.wrappers.nbt.NbtList;
import io.netty.buffer.ByteBuf;
import main.de.mj.bb.core.CoreSpigot;
import org.apache.commons.io.Charsets;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CrashFixer {

    private final Map<Player, Long> PACKET_USAGE = new ConcurrentHashMap<>();
    private final String kickMessage = "Du sendest zu viele Packete!";
    private final String dispatchCommand = "dispatch";
    private final CoreSpigot coreSpigot;

    public CrashFixer(CoreSpigot coreSpigot) {
        this.coreSpigot = coreSpigot;
    }

    public void checkPacket(PacketEvent event) {
        Player player = event.getPlayer();
        long lastPacket = PACKET_USAGE.getOrDefault(player, -1L);
        if (lastPacket == -2L) {
            event.setCancelled(true);
            return;
        }
        String name = event.getPacket().getStrings().readSafely(0);
        if (!("MC|BSign".equals(name) || "MC|BEdit".equals(name) || "REGISTER".equals(name))) {
            return;
        }
        try {
            if ("REGISTER".equals(name)) {
                this.checkChannels(event);
            } else {
                if (!this.elapsed(lastPacket, 100L)) {
                    throw new IOException("Packet flood");
                }
                PACKET_USAGE.put(player, System.currentTimeMillis());
                this.checkNbtTags(event);
            }
        } catch (Throwable ex) {
            PACKET_USAGE.put(player, -2L);
            Bukkit.getScheduler().runTask(coreSpigot, () -> {
                player.kickPlayer(this.kickMessage);
                if (this.dispatchCommand != null) {
                    coreSpigot.getServer().dispatchCommand(Bukkit.getConsoleSender(), this.dispatchCommand.replace("%name%", player.getName()));
                }
            });
            coreSpigot.getLogger().warning(String.valueOf(player.getName()) + " tried to exploit CustomPayload: " + ex.getMessage());
            event.setCancelled(true);
        }
    }

    private void checkNbtTags(PacketEvent event) throws IOException {
        PacketContainer container = event.getPacket();
        ByteBuf buffer = container.getSpecificModifier(ByteBuf.class).read(0).copy();
        byte[] bytes = new byte[buffer.readableBytes()];
        buffer.readBytes(bytes);
        try {
            Throwable throwable = null;
            Object var6_7 = null;
            try {
                DataInputStream inputStream = new DataInputStream(new ByteArrayInputStream(bytes));
                try {
                    ItemStack itemStack = StreamSerializer.getDefault().deserializeItemStack(inputStream);
                    if (itemStack == null) {
                        throw new IOException("Unable to deserialize ItemStack");
                    }
                    NbtCompound root = (NbtCompound) NbtFactory.fromItemTag(itemStack);
                    if (root == null) {
                        throw new IOException("No NBT tag?!");
                    }
                    if (!root.containsKey("pages")) {
                        throw new IOException("No 'pages' NBT compound was found");
                    }
                    NbtList pages = root.getList("pages");
                    if (pages.size() > 50) {
                        throw new IOException("Too much pages");
                    }
                } finally {
                    if (inputStream != null) {
                        inputStream.close();
                    }
                }
            } catch (Throwable throwable2) {
                if (throwable == null) {
                    throwable = throwable2;
                } else if (throwable != throwable2) {
                    throwable.addSuppressed(throwable2);
                }
                throw throwable2;
            }
        } finally {
            buffer.release();
        }
    }

    private void checkChannels(PacketEvent event) throws Exception {
        int channelsSize = event.getPlayer().getListeningPluginChannels().size();
        PacketContainer container = event.getPacket();
        ByteBuf buffer = container.getSpecificModifier(ByteBuf.class).read(0).copy();
        try {
            int i = 0;
            while (i < buffer.toString(Charsets.UTF_8).split("\u0000").length) {
                if (++channelsSize > 124) {
                    throw new IOException("Too much channels");
                }
                ++i;
            }
        } finally {
            buffer.release();
        }
    }

    private boolean elapsed(long from, long required) {
        return from == -1L || System.currentTimeMillis() - from > required;
    }

    public Map<Player, Long> getPACKET_USAGE() {
        return this.PACKET_USAGE;
    }

    public String getKickMessage() {
        return this.kickMessage;
    }

    public String getDispatchCommand() {
        return this.dispatchCommand;
    }

    public CoreSpigot getCoreSpigot() {
        return this.coreSpigot;
    }
}
