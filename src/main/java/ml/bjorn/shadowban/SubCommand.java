package ml.bjorn.shadowban;

import org.bukkit.command.CommandSender;

public interface SubCommand {
    int getMinArgs();

    boolean handle(CommandSender sender, String[] args);
}
