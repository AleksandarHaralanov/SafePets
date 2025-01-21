package io.github.aleksandarharalanov.safepets.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityListener;
import org.bukkit.event.entity.EntityTameEvent;

import java.util.ArrayList;
import java.util.List;

import static io.github.aleksandarharalanov.safepets.SafePets.getAlivePets;

public class EntityTameListener extends EntityListener {

    @Override
    public void onEntityTame(EntityTameEvent event) {
        String ownerName = ((Player) event.getOwner()).getName();
        List<String> ownerPets = getAlivePets().getStringList(ownerName, new ArrayList<>());

        String petUniqueId = event.getEntity().getUniqueId().toString();
        ownerPets.add(petUniqueId);

        getAlivePets().setProperty(ownerName, ownerPets);
        getAlivePets().save();
    }
}
