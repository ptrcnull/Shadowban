package ml.bjorn.shadowban.commands;

import ml.bjorn.shadowban.Punishment;
import org.bukkit.command.CommandSender;

public class Ban extends Punishment {
    public void handle(CommandSender sender, String[] args) {
        if(!handleDefault(sender, args, "ban")) {
            return;
        }
        plugin.getServer().getPlayerExact(args[0]).teleport(plugin.getServer().getWorlds().get(0).getSpawnLocation());
    }
}
