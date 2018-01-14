package ml.bjorn.shadowban;

import org.bukkit.ChatColor;
import org.bukkit.World;
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
        String muteSel = "players." + player.getName() + ".mute";
        if (config.contains(muteSel)) {
            long muteEnd = config.getLong(muteSel + ".end");
            if (muteEnd > Instant.now().toEpochMilli() || muteEnd == 0L) {
                if(config.getBoolean(muteSel + ".silent")) {
                    String message = player.getDisplayName();
                    message += "§f: ";
                    message += event.getMessage();
                    player.sendMessage(message);
                }
                event.setCancelled(true);
            } else {
                config.set(muteSel, null);
            }
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        String banSel = "players." + player.getName() + ".ban";
        if (config.contains(banSel)) {
            long banEnd = config.getLong(banSel + ".end");
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
                config.set(banSel, null);
            }
        }
        String jailSel = "players." + player.getName() + ".jail";
		if (config.contains(jailSel)) {
			long jailEnd = config.getLong(jailSel + ".end");
            if (jailEnd > Instant.now().toEpochMilli() || jailEnd == 0L) {
				event.setCancelled(true);
			} else {
				config.set(jailSel, null);
			}
		}
    }
	
	@EventHandler (priority = EventPriority.HIGHEST)
	public void onPlayerTeleport(PlayerTeleportEvent event) {
		Player player = event.getPlayer();
        String banSel = "players." + player.getName() + ".ban";
        World defaultWorld = plugin.getServer().getWorlds().get(0);
		if (config.contains(banSel)) {
			long banEnd = config.getLong(banSel + ".end");
			if (banEnd > Instant.now().toEpochMilli() || banEnd == 0L) {
                double x = event.getTo().getX();
                double z = event.getTo().getZ();
				if ((x > 99 || x < -99 || z > 99 || z < -99) || event.getTo().getWorld() != defaultWorld) {
					player.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.lang.getString("cannot-teleport")));
					event.setCancelled(true);
				}
			} else {
				config.set(banSel, null);
			}
		}
        String jailSel = "players." + player.getName() + ".jail";
		if (config.contains(jailSel)) {
			long jailEnd = config.getLong(jailSel + ".end");
			if (jailEnd > Instant.now().toEpochMilli() || jailEnd == 0L) {
                double x = event.getTo().getX();
                double z = event.getTo().getZ();
				if ((x > 99 || x < -99 || z > 99 || z < -99) || event.getTo().getWorld() != defaultWorld) {
					player.sendMessage("Nie mozesz tego zrobic, jestes w wiezieniu!");
					event.setCancelled(true);
				}
			} else {
				config.set(jailSel, null);
			}
		}
	}
}
