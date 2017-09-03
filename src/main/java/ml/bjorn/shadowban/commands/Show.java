package ml.bjorn.shadowban.commands;

import ml.bjorn.shadowban.SubCommand;

import org.bukkit.command.CommandSender;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.Objects;
import java.util.TimeZone;

public class Show extends SubCommand {

    public int getMinArgs() { return 0; }

    public void handle(CommandSender sender, String[] args) {
        String person = args.length < 1 ? sender.getName() : args[0];
        if (!Objects.equals(person, sender.getName()) && !sender.hasPermission("shadowban.see.others")) {
            sender.sendMessage(lang("no-permissions"));
            return;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        TimeZone tz = TimeZone.getTimeZone("Europe/Warsaw");
        sdf.setTimeZone(tz);
        boolean found = false;
        String[] punishments = new String[] {"mute", "ban", "jail"};
        for (String type : punishments) {
            String selector = "players." + person + "." + type;
            if (config.contains(selector)) {
               long end = config.getLong(selector + ".end");
                if(end > Instant.now().toEpochMilli() || end == 0L){
                    sender.sendMessage(langf("reason", lang(type), config.getString(selector + ".reason")));
                    sender.sendMessage(langf("by", config.getString(selector + ".by")));
                    sender.sendMessage(langf("end", end == 0L ? lang("permanent") : sdf.format(new Date(end))));
                    found = true;
                } else {
                    config.set(selector, null);
                    plugin.saveConfig();
                }
            }
        }
        if(!found){
            if(person.equals(sender.getName())){
                sender.sendMessage(lang("no-punishments"));
            } else {
                sender.sendMessage(langf("no-punishments-other", person));
            }
        }
    }
}
