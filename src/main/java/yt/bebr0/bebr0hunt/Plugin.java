package yt.bebr0.bebr0hunt;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import yt.bebr0.bebr0hunt.arena.Game;
import yt.bebr0.bebr0hunt.cmds.GameSettingCMDs;
import yt.bebr0.bebr0hunt.cmds.PlayerCMDs;
import yt.bebr0.bebr0hunt.events.ArenaEvents;

import java.io.IOException;
import java.util.List;

/*
    Made by BeBr0
    Check out my youtube - https://www.youtube.com/c/BeBr0 
*/

public final class Plugin extends JavaPlugin {

    private static Plugin instance;

    @Override
    public void onEnable() {
        instance = this;
        Game.setPrioritizedTargets((List<String>) getConfig().getList("prioritized_targets"));

        saveDefaultConfig();

        Bukkit.getPluginManager().registerEvents(new ArenaEvents(), this);

        new GameSettingCMDs(this);
        new PlayerCMDs(this);


        try {
            ConfigWorker.loadFromConfig();
        } catch (IOException e) {
            System.out.println("[Bebr0Hunt]: Unable to load cfg");
            e.printStackTrace();
        }
    }

    @Override
    public void onDisable() {
        ConfigWorker.writeToConfig();
    }

    public static Plugin getInstance(){
        return instance;
    }
}

