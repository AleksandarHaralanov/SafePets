package io.github.aleksandarharalanov.petx.listener;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityListener;

import java.util.ArrayList;
import java.util.List;

import static io.github.aleksandarharalanov.petx.PetX.getDeadPets;
import static io.github.aleksandarharalanov.petx.PetX.getPets;

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
            List<String> ownerPets = getPets().getStringList(owner.getName(), new ArrayList<>());
            if (ownerPets.remove(petUniqueId)) {
                getPets().setProperty(owner.getName(), ownerPets);
                getPets().save();
            }
        } else {
            List<String> deadPets = getDeadPets().getStringList("dead-pets", new ArrayList<>());
            deadPets.add(petUniqueId);
            getDeadPets().setProperty("dead-pets", deadPets);
            getDeadPets().save();
        }
    }
}
