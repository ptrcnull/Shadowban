package ml.bjorn.shadowban.commands;

import ml.bjorn.shadowban.Apology;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;

public class Unjail extends Apology {
    public void handle(CommandSender sender, String[] args) {
        if(!handleDefault(sender, args, "jail")){
            return;
        }
        try {
            Location spawn = plugin.getServer().getWorlds().get(0).getSpawnLocation();
            plugin.getServer().getPlayerExact(args[0]).teleport(spawn);
        } catch (Throwable e) {
            sender.sendMessage(lang("cannot-teleport-player"));
        }
    }
}
