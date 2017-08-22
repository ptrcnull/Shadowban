package ml.bjorn.shadowban;

import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

import java.time.Instant;

public class ShowSubCommand implements SubCommand {
    private Main plugin = Main.plugin;
    private FileConfiguration config = plugin.getConfig();

    @Override
    public int getMinArgs() {
        return 0;
    }

    @Override
    public boolean handle(CommandSender sender, String[] args) {
        String person = args.length < 2 ? sender.getName() : args[1];
        boolean found = false;
        if(config.contains("mute." + person)){
            long muteEnd = config.getLong("mute." + person + ".end");
            if(muteEnd > Instant.now().getEpochSecond()){
                sender.sendMessage("§dMute za: §f" + config.getString("mute." + person + ".reason"));
                sender.sendMessage("§dNadany przez: §f" + config.getString("mute." + person + ".by"));
                sender.sendMessage("§dKoniec: §f" + config.getLong("mute." + person + ".end"));
                found = true;
            } else {
                config.set("mute." + person, null);
                plugin.saveConfig();
            }
        }
        if(config.contains("ban." + person)){
            long banEnd = config.getLong("ban." + person + ".end");
            if(banEnd > Instant.now().getEpochSecond()){
                sender.sendMessage("§dBan za: §f" + config.getString("ban." + person + ".reason"));
                sender.sendMessage("§dNadany przez: §f" + config.getString("ban." + person + ".by"));
                sender.sendMessage("§dKoniec: §f" + config.getLong("ban." + person + ".end"));
                found = true;
            } else {
                config.set("ban." + person, null);
                plugin.saveConfig();
            }
        }
        if(config.contains("jail." + person)){
            long jailEnd = config.getLong("jail." + person + ".end");
            if(jailEnd > Instant.now().getEpochSecond()){
                sender.sendMessage("§dJail za: §f" + config.getString("jail." + person + ".reason"));
                sender.sendMessage("§dNadany przez: §f" + config.getString("jail." + person + ".by"));
                sender.sendMessage("§dKoniec: §f" + config.getLong("jail." + person + ".end"));
                found = true;
            } else {
                config.set("jail." + person, null);
                plugin.saveConfig();
            }
        }
        if(!found){
            if(person.equals(sender.getName())){
                sender.sendMessage("§aBrak kar na twoim koncie");
            } else {
                sender.sendMessage("§aBrak kar na koncie gracza " + person);
            }
        }
        return true;
    }
}
