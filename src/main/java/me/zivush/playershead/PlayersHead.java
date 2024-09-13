package me.zivush.playershead;

import org.bukkit.plugin.java.JavaPlugin;

public class PlayersHead extends JavaPlugin {

    private static PlayersHead instance;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        getServer().getPluginManager().registerEvents(new PlayerDeathListener(), this);
        getCommand("playershead").setExecutor(new PlayersHeadCommand());
    }

    public static PlayersHead getInstance() {
        return instance;
    }
}
