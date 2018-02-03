package ml.bjorn.shadowban.commands;

import ml.bjorn.shadowban.Apology;
import org.bukkit.command.CommandSender;

public class Unmute extends Apology {
    public void handle(CommandSender sender, String[] args) {
        handleDefault(sender, args, "mute");
    }
}
