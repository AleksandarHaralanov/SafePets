package io.github.aleksandarharalanov.petx.listener;

import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerListener;

import java.util.ArrayList;
import java.util.List;

import static io.github.aleksandarharalanov.petx.PetX.getDeadPets;
import static io.github.aleksandarharalanov.petx.PetX.getPets;

public class PlayerJoinListener extends PlayerListener {
    @Override
    public void onPlayerJoin(PlayerJoinEvent event) {
        List<String> ownerPets = getPets().getStringList(event.getPlayer().getName(), new ArrayList<>());
        List<String> deadPets = getDeadPets().getStringList("dead-pets", new ArrayList<>());

        List<String> matchedPets = new ArrayList<>(ownerPets);
        matchedPets.retainAll(deadPets);

        if (!matchedPets.isEmpty()) {
            ownerPets.removeAll(matchedPets);
            deadPets.removeAll(matchedPets);

            getPets().setProperty(event.getPlayer().getName(), ownerPets);
            getPets().save();

            getDeadPets().setProperty("dead-pets", deadPets);
            getDeadPets().save();
        }
    }
}
