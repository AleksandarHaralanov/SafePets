package io.github.aleksandarharalanov.petx;

import io.github.aleksandarharalanov.petx.listener.*;
import io.github.aleksandarharalanov.petx.util.ConfigUtil;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event.Type;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import static io.github.aleksandarharalanov.petx.util.LoggerUtil.logInfo;
import static io.github.aleksandarharalanov.petx.util.UpdateUtil.checkForUpdates;

public class PetX extends JavaPlugin {
    private static ConfigUtil config;
    private static ConfigUtil pets;
    private static ConfigUtil deadPets;
    private static PluginDescriptionFile pdf;

    @Override
    public void onEnable() {
        pdf = getDescription();

        checkForUpdates(this, "https://api.github.com/repos/AleksandarHaralanov/PetX/releases/latest");

        config = new ConfigUtil(this, "config.yml");
        config.loadConfig();

        pets = new ConfigUtil(this, "pets/pets.yml");
        pets.loadConfig();

        deadPets = new ConfigUtil(this, "pets/dead-pets.yml");
        deadPets.loadConfig();

        PluginManager pluginManager = getServer().getPluginManager();
        final EntityDamageListener entityDamageListener = new EntityDamageListener();
        final EntityDeathListener entityDeathListener = new EntityDeathListener();
        final EntityTameListener entityTameListener = new EntityTameListener();
        final PlayerInteractEntityListener playerInteractEntityListener = new PlayerInteractEntityListener();
        final PlayerJoinListener playerJoinListener = new PlayerJoinListener();
        pluginManager.registerEvent(Type.ENTITY_DAMAGE, entityDamageListener, Priority.Normal, this);
        pluginManager.registerEvent(Type.ENTITY_DEATH, entityDeathListener, Priority.Normal, this);
        pluginManager.registerEvent(Type.ENTITY_TAME, entityTameListener, Priority.Normal, this);
        pluginManager.registerEvent(Type.PLAYER_INTERACT_ENTITY, playerInteractEntityListener, Priority.Normal, this);
        pluginManager.registerEvent(Type.PLAYER_JOIN, playerJoinListener, Priority.Normal, this);

        logInfo(String.format("[%s] v%s Enabled.", pdf.getName(), pdf.getVersion()));
    }

    @Override
    public void onDisable() {
        logInfo(String.format("[%s] v%s Disabled.", pdf.getName(), pdf.getVersion()));
    }

    public static ConfigUtil getConfig() {
        return config;
    }

    public static ConfigUtil getPets() {
        return pets;
    }

    public static ConfigUtil getDeadPets() {
        return deadPets;
    }
}