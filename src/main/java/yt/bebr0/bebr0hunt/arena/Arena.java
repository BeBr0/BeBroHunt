package yt.bebr0.bebr0hunt.arena;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;
import yt.bebr0.bebr0hunt.Plugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
    Made by BeBr0
    Check out my youtube - https://www.youtube.com/c/BeBr0 
*/
public class Arena {

    private static final List<Arena> arenaList = new ArrayList<>();

    public static Arena add(String name){
        Arena arena = new Arena(name);
        arenaList.add(arena);
        return arena;
    }

    public static Arena get(Player player) {
        for (Arena arena: arenaList){
            if (arena.getPlayers().contains(player)){
                return arena;
            }
        }

        return null;
    }

    public static Arena get(String name) {
        for (Arena arena: arenaList){
            if (arena.getName().equalsIgnoreCase(name)){
                return arena;
            }
        }

        return null;
    }

    private final String name;
    private Location pos1, pos2;
    private Location attackersSpawn;
    private Location lobby;
    private int minPlayers = 4;
    private int timeToStart = 30;

    private final List<Player> players = new ArrayList<>();
    private final Map<Player, Location> onJoinLocation = new HashMap<>();
    private final Map<Player, Location> onJoinSpawn = new HashMap<>();

    private boolean isOpen = false;
    private boolean isStarting = false;

    private Game game;

    public Arena(String name) {
        this.name = name;

        game = new Game(this);
    }

    public void reset(){
        players.clear();
        game = new Game(this);

        for (Player player: onJoinLocation.keySet()){
            player.teleport(onJoinLocation.get(player));
            player.getInventory().clear();

            for (PotionEffect potionEffect: player.getActivePotionEffects()){
                player.removePotionEffect(potionEffect.getType());
            }
        }

        for (Player player: onJoinSpawn.keySet()){
            player.setBedSpawnLocation(onJoinLocation.get(player));
        }
    }

    public boolean join(Player player){
        if (get(player) != null){
            return false;
        }

        if (isOpen) {
            players.add(player);
            onJoinSpawn.put(player, player.getBedSpawnLocation());
            onJoinLocation.put(player, player.getLocation());
            player.teleport(lobby);
            sendArenaMessage(player.getDisplayName() + " присоединился!");

            if (players.size() >= minPlayers && !isStarting){
                startTimer();
            }

            return true;
        }

        return false;
    }

    public boolean leave(Player player){
        if (players.contains(player)) {
            sendArenaMessage(player.getDisplayName() + " вышел!");
            players.remove(player);
            player.teleport(onJoinLocation.get(player));

            onJoinLocation.remove(player);
            return true;
        }

        return false;
    }

    private void startTimer(){
        isStarting = true;

        new BukkitRunnable(){
            int ctr = timeToStart;

            public void run(){
                sendArenaTitle("До начала осталось - " + ctr + "!", "Приготовься!");
                playArenaSound(Sound.BLOCK_NOTE_BLOCK_PLING);

                ctr--;
                if (players.size() < minPlayers){
                    sendArenaMessage("Недостаточно игроков!");
                    playArenaSound(Sound.ENTITY_VILLAGER_NO);
                    cancel();
                }
                if (ctr == 0){
                    game.start();
                    cancel();
                }
            }
        }.runTaskTimer(Plugin.getInstance(), 0L, 20L);
    }

    public void sendArenaMessage(String msg){
        for (Player player: players){
            player.sendMessage(ChatColor.GOLD + "[" + name + "]: " + ChatColor.AQUA + msg);
        }
    }

    public void sendArenaTitle(String msg, String subMsg){
        for (Player player: players){
            player.sendTitle(msg, subMsg, 10, 40, 10);
        }
    }

    public void playArenaSound(Sound sound){
        for (Player player: players){
            player.playSound(player.getLocation(), sound, 1, 1);
        }
    }

    public boolean launch(){
        if (pos1 != null && pos2 != null && attackersSpawn != null && pos1.distance(pos2) >= 100) {
            isOpen = true;
            return true;
        }
        else{
            return false;
        }
    }

    public Location getPos1() {
        return pos1;
    }

    public void setPos1(Location pos1) {
        this.pos1 = pos1;
    }

    public Location getPos2() {
        return pos2;
    }

    public void setPos2(Location pos2) {
        if (pos1 != null){
            if (pos1.getBlockX() > pos2.getBlockX()){
                int t = pos1.getBlockX();
                pos1.setX(pos2.getX());
                pos2.setX(t);
            }

            if (pos1.getBlockZ() > pos2.getBlockZ()){
                int t = pos1.getBlockZ();
                pos1.setZ(pos2.getZ());
                pos2.setZ(t);
            }
        }

        this.pos2 = pos2;
    }

    public String getName() {
        return name;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public Location getAttackersSpawn() {
        return attackersSpawn;
    }

    public void setAttackersSpawn(Location attackersSpawn) {
        this.attackersSpawn = attackersSpawn;
    }

    public Game getGame() {
        return game;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setLobby(Location lobby) {
        this.lobby = lobby;
    }

    public static List<Arena> getArenas(){
        return arenaList;
    }

    public Location getLobby() {
        return lobby;
    }

    public int getMinPlayers() {
        return minPlayers;
    }

    public int getTimeToStart() {
        return timeToStart;
    }

    public void setMinPlayers(int minPlayers) {
        this.minPlayers = minPlayers;
    }

    public void setTimeToStart(int timeToStart) {
        this.timeToStart = timeToStart;
    }

    public void setOpen(boolean b) {
        isOpen = b;
    }
}
