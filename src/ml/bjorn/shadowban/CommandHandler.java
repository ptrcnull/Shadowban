package ml.bjorn.shadowban;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

import java.time.Instant;

public class CommandHandler implements CommandExecutor {
    private Main plugin;
    private FileConfiguration config;
    CommandHandler(Main instance) {
        plugin = instance;
        config = plugin.getConfig();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandString, String[] arguments) {
        if (arguments.length == 0) {
            help(sender);
            return true;
        }
        switch(arguments[0]){
            case "help":    help(sender); break;
            case "silent":  silent(sender); break;
            case "reload":  plugin.reloadConfig(); break;
            case "version": version(sender); break;
            case "show":    show(sender, arguments); break;
            default:        help(sender); break;
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
    private void version(CommandSender sender) {
        sender.sendMessage("§bShadowban v0.1.0");
    }
    private void show(CommandSender sender, String[] args) {
        String person = args.length < 2 ? sender.getName() : args[1];
        boolean found = false;
        if(config.contains("mute." + person)){
            long muteEnd = config.getLong("mute." + person + ".end");
            if(muteEnd > Instant.now().getEpochSecond()){
                sender.sendMessage("§dMute za: §f" + config.getString("mute." + person + ".reason"));
                sender.sendMessage("§dNadany przez: §f" + config.getString("mute." + person + ".by"));
                sender.sendMessage("§dKoniec: §f" + config.getString("mute." + person + ".end"));
            } else {
                config.set("mute." + person, null);
            }
            found = true;
        }
        if(config.contains("ban." + person)){
            long banEnd = config.getLong("ban." + person + ".end");
            if(banEnd > Instant.now().getEpochSecond()){
                sender.sendMessage("§dBan za: §f" + config.getString("ban." + person + ".reason"));
                sender.sendMessage("§dNadany przez: §f" + config.getString("ban." + person + ".by"));
                sender.sendMessage("§dKoniec: §f" + config.getString("ban." + person + ".end"));
            } else {
                config.set("ban." + person, null);
            }
            found = true;
        }
        if(config.contains("jail." + person)){
            long jailEnd = config.getLong("jail." + person + ".end");
            if(jailEnd > Instant.now().getEpochSecond()){
                sender.sendMessage("§dJail za: §f" + config.getString("jail." + person + ".reason"));
                sender.sendMessage("§dNadany przez: §f" + config.getString("jail." + person + ".by"));
                sender.sendMessage("§dKoniec: §f" + config.getString("jail." + person + ".end"));
            } else {
                config.set("jail." + person, null);
            }
            found = true;
        }
        if(!found){
            if(person.equals(sender.getName())){
                sender.sendMessage("§aBrak kar na twoim koncie");
            } else {
                sender.sendMessage("§aBrak kar na koncie gracza " + person);
            }
        }

    }
}