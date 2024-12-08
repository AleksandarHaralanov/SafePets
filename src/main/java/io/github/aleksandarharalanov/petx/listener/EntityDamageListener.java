package io.github.aleksandarharalanov.petx.listener;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityListener;

import java.util.List;

import static io.github.aleksandarharalanov.petx.PetX.getPets;
import static io.github.aleksandarharalanov.petx.util.ColorUtil.translate;

public class EntityDamageListener extends EntityListener {
    @Override
    public void onEntityDamage(EntityDamageEvent event) {
        Entity entity = event.getEntity();
        if (!(entity instanceof Wolf)) return;

        Wolf wolf = (Wolf) entity;
        if (!wolf.isTamed()) return;

        Player attacker = getAttacker(event);
        if (attacker == null) return;

        String petUniqueId = wolf.getUniqueId().toString();
        List<String> attackerPets = getPets().getStringList(attacker.getName(), null);
        if (attackerPets == null || !attackerPets.contains(petUniqueId)) {
            event.setCancelled(true);
            attacker.sendMessage(translate("&cYou can't harm this pet; it doesn't belong to you."));
        }
    }

    private static Player getAttacker(EntityDamageEvent event) {
        Player attacker = null;
        if (event instanceof EntityDamageByEntityEvent) {
            EntityDamageByEntityEvent damageByEntityEvent = (EntityDamageByEntityEvent) event;
            Entity damager = damageByEntityEvent.getDamager();
            if (damager instanceof Player) attacker = (Player) damager;
            else if (damager instanceof Arrow) {
                Entity shooter = ((Arrow) damager).getShooter();
                if (shooter instanceof Player) attacker = (Player) shooter;
            }
        }
        return attacker;
    }
}
