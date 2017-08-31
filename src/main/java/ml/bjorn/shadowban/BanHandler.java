package ml.bjorn.shadowban;

import org.bukkit.entity.Player;

public class BanHandler {
    public static void handle(Player player, Main plugin) {
        player.teleport(plugin.getServer().getWorlds().get(0).getSpawnLocation());
    }
}
