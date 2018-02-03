package ml.bjorn.shadowban.commands;

import ml.bjorn.shadowban.Punishment;
import org.bukkit.command.CommandSender;

public class Mute extends Punishment {
    public void handle(CommandSender sender, String[] args) {
        handleDefault(sender, args, "mute");
    }
}
