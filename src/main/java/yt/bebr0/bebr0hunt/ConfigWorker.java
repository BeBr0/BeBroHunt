package yt.bebr0.bebr0hunt;

import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import yt.bebr0.bebr0hunt.arena.Arena;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

/*
    Made by BeBr0
    Check out my youtube - https://www.youtube.com/c/BeBr0 
*/
public class ConfigWorker {

    private static final String path = "plugins/" + Plugin.getInstance().getName() + "/arenas/";

    public static void loadFromConfig() throws IOException {
        File folder = new File(path);
        if (!folder.exists())
            folder.mkdirs();

        for (File file : folder.listFiles()) {
            Arena arena = Arena.add(file.getName().split("\\.")[0]);
            YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(file);
            arena.setPos1(yamlConfiguration.getLocation("pos1"));
            arena.setPos2(yamlConfiguration.getLocation("pos2"));
            arena.setAttackersSpawn(yamlConfiguration.getLocation("attackersSpawn"));
            arena.setLobby(yamlConfiguration.getLocation("lobby"));
            arena.setMinPlayers(yamlConfiguration.getInt("minPlayers"));
            arena.setTimeToStart(yamlConfiguration.getInt("timeToStart"));
            arena.launch();
        }
    }

    public static void writeToConfig(){
        for (Arena arena: Arena.getArenas()){
            File file = new File(path + arena.getName() + ".yml");
            if (!file.exists()) {
                try {
                    file.createNewFile();
                }
                catch (IOException ignored){}
            }

            YamlConfiguration configuration = new YamlConfiguration();

            configuration.set("pos1", arena.getPos1());
            configuration.set("pos2", arena.getPos2());
            configuration.set("attackersSpawn", arena.getAttackersSpawn());
            configuration.set("lobby", arena.getLobby());
            configuration.set("minPlayers", arena.getMinPlayers());
            configuration.set("timeToStart", arena.getTimeToStart());

            try {
                configuration.save(file);
            }
            catch (IOException ignored){}
        }
    }

}
