package ml.bjorn.shadowban;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

public class CommandHandler implements CommandExecutor {
    private Main plugin;

    CommandHandler(Main instance) {
        plugin = instance;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandString, String[] arguments) {
        if (arguments.length == 0) {
            help(sender);
            return true;
        }
        switch(arguments[0]){
            case "help":
                help(sender);
                break;
            case "silent":
                silent(sender);
                break;
            case "reload":
                reload();
                break;
            default:
                help(sender);
                break;
        }
        return true;
    }

    private void help(CommandSender sender) {
        sender.sendMessage("§aPomoc dla pluginu Shadowban");
        sender.sendMessage("§a help - wyswietla ta wiadomosc");
        if(sender.hasPermission("shadowban.see.others")) {
            sender.sendMessage("§a show [gracz] - pokazuje aktywne kary na koncie");
        } else {
            sender.sendMessage("§a show - pokazuje aktywne kary na twoim koncie");
        }
        if(sender.hasPermission("shadowban.mute")) {
            sender.sendMessage("§a mute <gracz> [czas] [powod] - wycisza gracza");
            sender.sendMessage("§a unmute <gracz> - odwycisza gracza");
        }
        if(sender.hasPermission("shadowban.ban")){
            sender.sendMessage("§a ban <gracz> [czas] [powod] - banuje gracza");
        }

        if(sender.hasPermission("shadowban.silent")) {
            sender.sendMessage("§a silent - przelacza tryb cichy");
        }
    }
    private void silent(CommandSender sender) {
        FileConfiguration config = plugin.getConfig();
        String path = "silent." + sender.getName();
        if(config.isSet(path) && config.getBoolean(path)) {
            config.set(path, false);
            sender.sendMessage("§aTeraz twoje kary §4nie sa §awyciszone");
        } else {
            config.set(path, true);
            sender.sendMessage("§aTeraz twoje kary §4sa §awyciszone");
        }
        plugin.saveConfig();
    }
    private void reload() {
        plugin.reloadConfig();
    }
}