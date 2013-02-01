package ch.jamiete.fwv;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.kitteh.vanish.event.VanishStatusChangeEvent;

public class FlyWhileVanished extends JavaPlugin implements Listener {
    public void broadcast(final String message, final String permission) {
        for (final Player player : this.getServer().getOnlinePlayers()) {
            if (player.hasPermission(permission)) {
                player.sendMessage(message);
            }
        }
        this.getLogger().info(message);
    }

    @Override
    public void onEnable() {
        if (this.getServer().getPluginManager().getPlugin("VanishNoPacket") == null) {
            this.getLogger().severe("Failed to find VanishNoPacket. Shutting down.");
            this.getServer().getPluginManager().disablePlugin(this);
            return;
        }
        this.getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onVanishChange(final VanishStatusChangeEvent event) {
        if (!event.getPlayer().hasPermission("flywhilevanished.allow")) {
            return;
        }
        event.getPlayer().setFlying(event.isVanishing());
        event.getPlayer().sendMessage(ChatColor.AQUA + "You can " + (event.isVanishing() ? "now fly" : "no longer fly"));
        if (event.getPlayer().hasPermission("flywhilevanished.announce")) {
            this.broadcast(ChatColor.AQUA + event.getName() + " can " + (event.isVanishing() ? "now fly" : "no longer fly") + " due to Vanish No Packet.", "flywhilevanished.receive");
        }
    }
}
