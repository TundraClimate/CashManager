package com.tundra.github.cashmanager;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class DetectLogin implements Listener {
    public DetectLogin(JavaPlugin plugin){
        plugin.getServer().getPluginManager().registerEvents(this,plugin);
    }
    @EventHandler
    public void LoginDetect(PlayerJoinEvent e){
        DBManager.addPlayer(e.getPlayer());
    }
}
