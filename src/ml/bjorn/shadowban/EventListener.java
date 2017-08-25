package ml.bjorn.shadowban;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.time.Instant;

public class EventListener implements Listener {
    private Main plugin = Main.plugin;
    private FileConfiguration config = plugin.getConfig();

    @EventHandler (priority = EventPriority.HIGHEST)
    public void onAsyncPlayerChatEvent(AsyncPlayerChatEvent event){
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
}
