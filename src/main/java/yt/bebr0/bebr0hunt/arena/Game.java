package yt.bebr0.bebr0hunt.arena;

import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;
import yt.bebr0.bebr0hunt.Plugin;
import yt.bebr0.bebr0hunt.arena.classes.Class;
import yt.bebr0.bebr0hunt.util.ItemUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/*
    Made by BeBr0
    Check out my youtube - https://www.youtube.com/c/BeBr0 
*/
public class Game {

    private static List<String> prioritizedTargets = new ArrayList<>();
    public static final ItemStack compass = ItemUtil.createItemStack(ChatColor.GREEN + "ПКМ - Указать на цель", List.of("Нажимай ПКМ, чтобы обновить"), Material.COMPASS);

    private static final List<Material> forbiddenBlocks = List.of(
            Material.LAVA,
            Material.MAGMA_BLOCK
    );

    public static void setPrioritizedTargets(List<String> list) {
        prioritizedTargets = list;
    }

    private final List<GamePlayer> gamePlayers = new ArrayList<>();

    private final Arena arena;

    private Location targetLocation;

    public Game(Arena arena){
        this.arena = arena;
    }

    public void start(){
        arena.setOpen(false);
        randomizeRoles();
        teleportPlayers();
        randomizeClasses();
    }
    public void teleportPlayers(){

        Random random = new Random();
        while (true) {
            int x = random.nextInt(arena.getPos1().getBlockX(), arena.getPos2().getBlockX());
            int z = random.nextInt(arena.getPos1().getBlockZ(), arena.getPos2().getBlockZ());

            Location location = new Location(arena.getAttackersSpawn().getWorld(), x, -60, z);
            if (location.distance(arena.getAttackersSpawn()) >= 50 && arena.getAttackersSpawn().getBlockX() > arena.getPos1().getBlockX() && arena.getAttackersSpawn().getBlockZ() > arena.getPos1().getBlockZ() &&
                    arena.getAttackersSpawn().getBlockX() < arena.getPos2().getBlockX() && arena.getAttackersSpawn().getBlockZ() < arena.getPos2().getBlockZ()){
                boolean isFound = false;
                for (int y = 319; y >= -60; y--){
                    location.setY(y);
                    if (location.getBlock().getType() != Material.AIR && location.getBlock().getType() != Material.VOID_AIR){
                        if (!forbiddenBlocks.contains(location.getBlock().getType())) {
                            isFound = true;
                            break;
                        }

                        break;
                    }
                }

                if (isFound) {
                    targetLocation = location;
                    break;
                }
            }
        }

        for (GamePlayer gamePlayer: gamePlayers){
            if (gamePlayer.getRole() == Role.ATTACKER) {
                gamePlayer.getPlayer().teleport(arena.getAttackersSpawn());

                gamePlayer.getPlayer().setBedSpawnLocation(arena.getAttackersSpawn());
            }

            else{
                gamePlayer.getPlayer().teleport(targetLocation);
                gamePlayer.getPlayer().setBedSpawnLocation(targetLocation);
            }

            preparePlayer(gamePlayer.getPlayer());
        }
    }

    private void preparePlayer(Player player){
        player.getInventory().clear();
        for (PotionEffect potionEffect: player.getActivePotionEffects()){
            player.removePotionEffect(potionEffect.getType());
        }

        player.setGameMode(GameMode.SURVIVAL);
        player.getInventory().addItem(compass);
    }

    private void randomizeClasses(){
        Random random = new Random();
        for (Player player: arena.getPlayers()){
            Class randomClass = Class.values()[random.nextInt(Class.values().length)];
            randomClass.grantAbilities(player);

            player.sendMessage(ChatColor.translateAlternateColorCodes('&', randomClass.getName()));
        }
    }

    private void randomizeRoles(){
        boolean isTargetFound = false;

        for (Player player: arena.getPlayers()){
            for (String target: prioritizedTargets){
                if (player.displayName().equals(Component.text(target))){
                    gamePlayers.add(new GamePlayer(player, Role.TARGET));
                    isTargetFound = true;
                    break;
                }
            }

            if (isTargetFound){
                break;
            }
        }

        Random random = new Random();

        if (!isTargetFound){
            int randomPlayerNum = random.nextInt(0, arena.getPlayers().size());

            gamePlayers.add(new GamePlayer(arena.getPlayers().get(randomPlayerNum), Role.TARGET));
        }

        int guardsNum = arena.getPlayers().size() / 4;
        int currGuardsNum = 0;

        for (int i = 0; i < guardsNum; i++){
            if (currGuardsNum >= guardsNum)
                break;
            while (true) {
                int randomPlayerNum = random.nextInt(0, arena.getPlayers().size());

                Player player = arena.getPlayers().get(randomPlayerNum);
                if (getRole(player) == null){
                    gamePlayers.add(new GamePlayer(player, Role.GUARD));
                    currGuardsNum += 1;
                    break;
                }
            }
        }

        for (Player player: arena.getPlayers()){
            if (getRole(player) == null){
                gamePlayers.add(new GamePlayer(player, Role.ATTACKER));
            }
        }
    }

    public void onTargetDeath(Player killer){
        int maxDamage = 0;
        getTarget().getPlayer().setGameMode(GameMode.SPECTATOR);
        GamePlayer secondWinner = null;
        for (GamePlayer gamePlayer: gamePlayers){
            if (gamePlayer.getTargetDamage() > maxDamage){
                secondWinner = gamePlayer;
            }
        }

        endGame(killer, secondWinner);
    }

    public void onDeath(Player player, Player killer){
        player.setGameMode(GameMode.SPECTATOR);

        new BukkitRunnable(){

            int ctr = 8;
            @Override
            public void run() {
                player.sendTitle(ChatColor.translateAlternateColorCodes('&', "Вас убил - &4" + killer.getDisplayName()),
                        ChatColor.translateAlternateColorCodes('&', "&1Ты возродишься через " + ctr + " секунд!"), 0, 40, 10);

                if (ctr-- <= 0){
                    revive(player);
                    cancel();

                }
            }
        }.runTaskTimer(Plugin.getInstance(), 0L, 20L);

    }

    public void revive(Player player){
        player.setGameMode(GameMode.SURVIVAL);
        player.setHealth(20);
        player.setFoodLevel(20);

        if (getRole(player) == Role.ATTACKER){
            player.teleport(arena.getAttackersSpawn());
        }
        else{
            player.teleport(targetLocation);
        }

        for (PotionEffect potionEffect: player.getActivePotionEffects()){
            player.removePotionEffect(potionEffect.getType());
        }
    }

    public void endGame(Player winner, GamePlayer secondWinner){
        if (secondWinner != null && !secondWinner.getPlayer().equals(winner)) {
            arena.sendArenaTitle("Игра окончена! Победитель - " + winner.getDisplayName(),
                    "Второе место - " + secondWinner.getPlayer().getDisplayName() + " c " + secondWinner.getTargetDamage() + " урона по цели!");
        }
        else {
            arena.sendArenaTitle("Игра окончена! Победитель - " + winner.getDisplayName(),
                    "");
        }
        new BukkitRunnable(){
            public void run(){
                arena.reset();
            }
        }.runTaskLater(Plugin.getInstance(), 10 * 20L);
    }

    public GamePlayer getTarget(){
        for (GamePlayer gamePlayer: gamePlayers){
            if (gamePlayer.getRole() == Role.TARGET)
                return gamePlayer;
        }

        return null;
    }

    public Role getRole(Player player){
        for (GamePlayer gamePlayer: gamePlayers){
            if (gamePlayer.getPlayer() == player){
                return gamePlayer.getRole();
            }
        }

        return null;
    }
}
