package ml.bjorn.shadowban;

import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class JailHandler {
    public static void handle (Player player, Main plugin) {
        FileConfiguration config = plugin.getConfig();
        Server server = plugin.getServer();
        if (
            config.contains("jail") &&
            config.contains("jail.x") &&
            config.contains("jail.y") &&
            config.contains("jail.z") &&
            config.isInt("jail.x") &&
            config.isInt("jail.y") &&
            config.isInt("jail.z")
        ) {
            World world;
            if (config.contains("jail.world") && config.isString("jail.world")) {
                world = server.getWorld(config.getString("jail.world"));
            } else {
                world = server.getWorlds().get(0);
            }
            Double x = config.getDouble("jail.x");
            Double y = config.getDouble("jail.y");
            Double z = config.getDouble("jail.z");
            Location location = new Location(world, x, y, z);
            player.teleport(location);
        } else {
            plugin.getLogger().log(plugin.getLogger().getLevel(), "Nie znaleziono poprawnego wiezienia w konfiguracji, przenosze gracza na spawn");
            player.teleport(server.getWorlds().get(0).getSpawnLocation());
        }
    }
}
