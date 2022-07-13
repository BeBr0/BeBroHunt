package yt.bebr0.bebr0hunt.arena.classes;

import io.papermc.paper.event.entity.EntityLoadCrossbowEvent;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.LingeringPotionSplashEvent;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.scheduler.BukkitRunnable;
import yt.bebr0.bebr0hunt.Plugin;
import yt.bebr0.bebr0hunt.arena.Arena;
import yt.bebr0.bebr0hunt.util.ItemUtil;

import java.util.ArrayList;

public class ClassEvent implements Listener {

    @EventHandler(priority = EventPriority.NORMAL)
    public void onGrenadeHit(ProjectileHitEvent event){
        if (event.getEntity().getType() == EntityType.SNOWBALL){
            if (event.getEntity().getShooter() instanceof Player player){
                if (Class.getClass(player) == Class.DEMOLISHIONEER){
                    event.getEntity().getLocation().createExplosion(Grenade.getInstance().getGrenadePower(), false, false);

                    new BukkitRunnable(){
                        @Override
                        public void run() {
                            player.getInventory().addItem(Grenade.getInstance().getGrenadeItem());
                        }
                    }.runTaskLater(Plugin.getInstance(), Grenade.getInstance().getGrenadeCoolDown() * 20L);
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPotionExplode(PotionSplashEvent event){
        if (event.getEntity().getShooter() instanceof Player player){
            if (Class.getClass(player) == Class.BREWER){
                new BukkitRunnable(){
                    @Override
                    public void run() {
                        player.getInventory().addItem(event.getPotion().getItem());
                    }
                }.runTaskLater(Plugin.getInstance(), Grenade.getInstance().getGrenadeCoolDown() * 20L);
            }
        }
    }

    @EventHandler
    public void onLingeringExplode(LingeringPotionSplashEvent event){
        if (event.getEntity().getShooter() instanceof Player player){
            if (Class.getClass(player) == Class.BREWER){
                new BukkitRunnable(){
                    @Override
                    public void run() {
                        player.getInventory().addItem(event.getEntity().getItem());
                    }
                }.runTaskLater(Plugin.getInstance(), Grenade.getInstance().getGrenadeCoolDown() * 20L);
            }
        }
    }

    @EventHandler
    public void onShootCrossbow(EntityLoadCrossbowEvent event){
        if (event.getEntity() instanceof Player player){
            if (Arena.get(player) != null)
                player.getInventory().addItem(ItemUtil.createItemStack("&aБолт", new ArrayList<>(), Material.ARROW));
        }
    }
}
