package ml.bjorn.shadowban;

import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.TimeZone;

public class ShowSubCommand implements SubCommand {
    private Main plugin = Main.plugin;
    private FileConfiguration config = plugin.getConfig();

    @Override
    public int getMinArgs() {
        return 0;
    }

    @Override
    public boolean handle(CommandSender sender, String[] args) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        TimeZone tz = TimeZone.getTimeZone("Europe/Warsaw");
        sdf.setTimeZone(tz);
        String person = args.length < 1 ? sender.getName() : args[0];
        boolean found = false;
        if(config.contains("mute." + person)){
            long muteEnd = config.getLong("mute." + person + ".end");
            if(muteEnd > Instant.now().toEpochMilli() || muteEnd == 0L){
                String end;
                if(muteEnd == 0L){
                    end = "permanentny";
                } else {
                    end = sdf.format(new Date(muteEnd));
                }
                sender.sendMessage("§dMute za: §f" + config.getString("mute." + person + ".reason"));
                sender.sendMessage("§dNadany przez: §f" + config.getString("mute." + person + ".by"));
                sender.sendMessage("§dKoniec: §f" + end);
                found = true;
            } else {
                config.set("mute." + person, null);
                plugin.saveConfig();
            }
        }
        if(config.contains("ban." + person)){
            long banEnd = config.getLong("ban." + person + ".end");
            if(banEnd > Instant.now().toEpochMilli() || banEnd == 0L){
                String end;
                if(banEnd == 0L){
                    end = "permanentny";
                } else {
                    end = sdf.format(new Date(banEnd));
                }
                sender.sendMessage("§dBan za: §f" + config.getString("ban." + person + ".reason"));
                sender.sendMessage("§dNadany przez: §f" + config.getString("ban." + person + ".by"));
                sender.sendMessage("§dKoniec: §f" + end);
                found = true;
            } else {
                config.set("ban." + person, null);
                plugin.saveConfig();
            }
        }
        if(config.contains("jail." + person)){
            long jailEnd = config.getLong("jail." + person + ".end");
            if(jailEnd > Instant.now().toEpochMilli() || jailEnd == 0L){
                String end;
                if(jailEnd == 0L){
                    end = "permanentny";
                } else {
                    end = sdf.format(new Date(jailEnd));
                }
                sender.sendMessage("§dJail za: §f" + config.getString("jail." + person + ".reason"));
                sender.sendMessage("§dNadany przez: §f" + config.getString("jail." + person + ".by"));
                sender.sendMessage("§dKoniec: §f" + end);
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
