package ml.bjorn.shadowban.commands;

import ml.bjorn.shadowban.Punishment;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Jail extends Punishment {
    public void handle(CommandSender sender, String[] args) {
        if(!handleDefault(sender, args, "jail")) {
            return;
        }
        Server server = plugin.getServer();
        Player player = server.getPlayerExact(args[0]);
        if (config.contains("jail") && config.contains("jail.x") && config.contains("jail.y") && config.contains("jail.z") && config.isInt("jail.x") && config.isInt("jail.y") && config.isInt("jail.z")) {
            Boolean hasWorld = config.contains("jail.world") && config.isString("jail.world");
            World world = hasWorld ? server.getWorld(config.getString("jail.world")) : server.getWorlds().get(0);
            Double x = config.getDouble("jail.x");
            Double y = config.getDouble("jail.y");
            Double z = config.getDouble("jail.z");
            Location location = new Location(world, x, y, z);
            player.teleport(location);
        } else {
            World world = server.getWorlds().get(0);
            plugin.getLogger().log(plugin.getLogger().getLevel(), langf("jail-not-found", world.getName()));
            player.teleport(world.getSpawnLocation());
        }
    }
}
