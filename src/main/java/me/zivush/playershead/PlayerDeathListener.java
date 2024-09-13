package me.zivush.playershead;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class PlayerDeathListener implements Listener {

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        Player killer = player.getKiller();
        Random random = new Random();

        double chance = PlayersHead.getInstance().getConfig().getDouble("drop-chance");
        if (random.nextDouble() > chance) {
            return;
        }

        ItemStack head = new ItemStack(Material.PLAYER_HEAD, 1);
        SkullMeta meta = (SkullMeta) head.getItemMeta();
        meta.setOwningPlayer(player);

        String displayName = PlayersHead.getInstance().getConfig().getString("head-name");
        displayName = displayName.replace("%player%", player.getName());
        meta.setDisplayName(ColorUtils.translateColorCodes(displayName));

        List<String> lore = PlayersHead.getInstance().getConfig().getStringList("head-lore");
        List<String> coloredLore = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentDate = dateFormat.format(new Date());
        String killerName = (killer != null) ? killer.getName() : "Unknown";

        for (String line : lore) {
            line = line.replace("%player%", player.getName())
                    .replace("%date%", currentDate)
                    .replace("%killer%", killerName);
            coloredLore.add(ColorUtils.translateColorCodes(line));
        }
        meta.setLore(coloredLore);

        head.setItemMeta(meta);
        player.getWorld().dropItemNaturally(player.getLocation(), head);

        String deathMessage = PlayersHead.getInstance().getConfig().getString("death-message");
        deathMessage = deathMessage.replace("%player%", player.getName());
        player.sendMessage(ColorUtils.translateColorCodes(deathMessage));

        String killedByMessage = PlayersHead.getInstance().getConfig().getString("killed-by-message");
        if (killedByMessage != null && !killedByMessage.isEmpty()) {
            killedByMessage = killedByMessage.replace("%killer%", killerName);
            player.sendMessage(ColorUtils.translateColorCodes(killedByMessage));
        }
    }
}

