package me.zivush.playershead;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class PlayersHeadCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
            if (sender.hasPermission("playershead.reload")) {
                PlayersHead.getInstance().reloadConfig();
                sender.sendMessage(ColorUtils.translateColorCodes("&aPlayersHead configuration reloaded."));
            } else {
                sender.sendMessage(ColorUtils.translateColorCodes("&cYou don't have permission to use this command."));
            }
            return true;
        }
        return false;
    }
}
