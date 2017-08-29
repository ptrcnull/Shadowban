package ml.bjorn.shadowban;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.util.Vector;

import java.time.Instant;

public class EventListener implements Listener {
    private Main plugin = Main.plugin;
    private FileConfiguration config = plugin.getConfig();

    @EventHandler (priority = EventPriority.HIGHEST)
    public void onAsyncPlayerChatEvent(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        if (config.contains("mute." + player.getName())) {
            long muteEnd = config.getLong("mute." + player.getName() + ".end");
            if (muteEnd > Instant.now().toEpochMilli() || muteEnd == 0L) {
                if(config.getBoolean("mute." + player.getName() + ".silent")) {
                    String message = player.getDisplayName();
                    message += "§f: ";
                    message += event.getMessage();
                    player.sendMessage(message);
                }
                event.setCancelled(true);
            } else {
                config.set("mute." + player.getName(), null);
            }
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (config.contains("ban." + player.getName())) {
            long banEnd = config.getLong("ban." + player.getName() + ".end");
            if (banEnd > Instant.now().toEpochMilli() || banEnd == 0L) {
                double x = event.getFrom().getX();
                double z = event.getFrom().getZ();
                if (x > 99){
                    player.setVelocity(new Vector(-1, 0.2, 0));
                } else if (z > 99){
                    player.setVelocity(new Vector(0, 0.2, -1));
                } else if (x < -99) {
                    player.setVelocity(new Vector(1, 0.2, 0));
                } else if (z < -99) {
                    player.setVelocity(new Vector(0, 0.2, 1));
                }
            } else {
                config.set("ban." + player.getName(), null);
            }
        }
		if (config.contains("jail." + player.getName())) {
			long jailEnd = config.getLong("jail." + player.getName() + ".end");
            if (jailEnd > Instant.now().toEpochMilli() || jailEnd == 0L) {
				event.setCancelled(true);
			} else {
				config.set("jail." + player.getName(), null);
			}
		}
    }
	
	@EventHandler (priority = EventPriority.HIGHEST)
	public void onPlayerTeleport(PlayerTeleportEvent event) {
		Player player = event.getPlayer();
		if (config.contains("ban." + player.getName())) {
			long banEnd = config.getLong("ban." + player.getName() + ".end");
			if (banEnd > Instant.now().toEpochMilli() || banEnd == 0L) {
                double x = event.getTo().getX();
                double z = event.getTo().getZ();
				if (x > 99 || x < -99 || z > 99 || z < -99) {
					player.sendMessage("Nie mozesz tego zrobic, masz bana!");
					event.setCancelled(true);
				}
			} else {
				config.set("ban." + player.getName(), null);
			}
		}
		if (config.contains("jail." + player.getName())) {
			long jailEnd = config.getLong("jail." + player.getName() + ".end");
			if (jailEnd > Instant.now().toEpochMilli() || jailEnd == 0L) {
                double x = event.getTo().getX();
                double z = event.getTo().getZ();
				if (x > 99 || x < -99 || z > 99 || z < -99) {
					player.sendMessage("Nie mozesz tego zrobic, jestes w wiezieniu!");
					event.setCancelled(true);
				}
			} else {
				config.set("jail." + player.getName(), null);
			}
		}
	}
}
