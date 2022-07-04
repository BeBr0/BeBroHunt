package yt.bebr0.bebr0hunt;

import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import yt.bebr0.bebr0hunt.arena.Arena;

import java.io.File;
import java.io.IOException;

/*
    Made by BeBr0
    Check out my youtube - https://www.youtube.com/c/BeBr0 
*/
public class ConfigWorker {

    private static final String path = "plugins/BeBroHunt/";

    public static void loadFromConfig(){
        File folder = new File(path);
        for (File file: folder.listFiles()){
            Arena arena = new Arena(file.getName());
            YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(file);
            arena.setPos1((Location) yamlConfiguration.get("pos1"));
            arena.setPos2((Location) yamlConfiguration.get("pos2"));
            arena.setAttackersSpawn((Location) yamlConfiguration.get("attackersSpawn"));
            arena.setLobby((Location) yamlConfiguration.get("lobby"));
            arena.setMinPlayers((Integer) yamlConfiguration.get("minPlayers"));
            arena.setTimeToStart((Integer) yamlConfiguration.get("timeToStart"));
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
