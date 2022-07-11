package yt.bebr0.bebr0hunt.arena.classes;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.scheduler.BukkitRunnable;
import yt.bebr0.bebr0hunt.Plugin;

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
}
