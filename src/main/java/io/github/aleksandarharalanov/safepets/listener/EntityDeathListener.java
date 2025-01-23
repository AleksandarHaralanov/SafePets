package io.github.aleksandarharalanov.safepets.listener;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityListener;

import java.util.ArrayList;
import java.util.List;

import static io.github.aleksandarharalanov.safepets.SafePets.getAlivePets;
import static io.github.aleksandarharalanov.safepets.SafePets.getDeadPets;

public class EntityDeathListener extends EntityListener {

    @Override
    public void onEntityDeath(EntityDeathEvent event) {
        Entity entity = event.getEntity();
        if (!(entity instanceof Wolf)) return;

        Wolf wolf = (Wolf) entity;
        if (!wolf.isTamed()) return;

        String petUniqueId = wolf.getUniqueId().toString();
        Player owner = (Player) wolf.getOwner();
        if (owner != null) {
            List<String> ownerPets = getAlivePets().getStringList(owner.getName(), new ArrayList<>());
            if (ownerPets.remove(petUniqueId)) {
                getAlivePets().setProperty(owner.getName(), ownerPets);
                getAlivePets().save();
            }

            if (ownerPets.isEmpty() && getAlivePets().getProperty(owner.getName()) != null) {
                getAlivePets().removeProperty(owner.getName());
                getAlivePets().save();
            }
        } else {
            List<String> deadPets = getDeadPets().getStringList("dead-pets", new ArrayList<>());
            deadPets.add(petUniqueId);
            getDeadPets().setProperty("dead-pets", deadPets);
            getDeadPets().save();
        }
    }
}
