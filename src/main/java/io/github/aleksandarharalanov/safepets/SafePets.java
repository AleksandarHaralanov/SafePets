package io.github.aleksandarharalanov.safepets;

import io.github.aleksandarharalanov.safepets.listener.*;
import io.github.aleksandarharalanov.safepets.util.ConfigUtil;
import org.bukkit.event.Event;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import static io.github.aleksandarharalanov.safepets.util.LoggerUtil.logInfo;
import static io.github.aleksandarharalanov.safepets.util.UpdateUtil.checkForUpdates;

public class SafePets extends JavaPlugin {

    private static ConfigUtil alivePets;
    private static ConfigUtil deadPets;

    @Override
    public void onEnable() {
        checkForUpdates(this, "https://api.github.com/repos/AleksandarHaralanov/SafePets/releases/latest");

        alivePets = new ConfigUtil(this, "pets/alive-pets.yml");
        alivePets.loadConfig();

        deadPets = new ConfigUtil(this, "pets/dead-pets.yml");
        deadPets.loadConfig();

        PluginManager pluginManager = getServer().getPluginManager();
        final EntityDamageListener entityDamageListener = new EntityDamageListener();
        final EntityDeathListener entityDeathListener = new EntityDeathListener();
        final EntityTameListener entityTameListener = new EntityTameListener();
        final PlayerInteractEntityListener playerInteractEntityListener = new PlayerInteractEntityListener();
        final PlayerJoinListener playerJoinListener = new PlayerJoinListener();
        pluginManager.registerEvent(Event.Type.ENTITY_DAMAGE, entityDamageListener, Event.Priority.Normal, this);
        pluginManager.registerEvent(Event.Type.ENTITY_DEATH, entityDeathListener, Event.Priority.Normal, this);
        pluginManager.registerEvent(Event.Type.ENTITY_TAME, entityTameListener, Event.Priority.Normal, this);
        pluginManager.registerEvent(Event.Type.PLAYER_INTERACT_ENTITY, playerInteractEntityListener, Event.Priority.Normal, this);
        pluginManager.registerEvent(Event.Type.PLAYER_JOIN, playerJoinListener, Event.Priority.Normal, this);

        logInfo(String.format("[%s] v%s Enabled.", getDescription().getName(), getDescription().getVersion()));
    }

    @Override
    public void onDisable() {
        logInfo(String.format("[%s] v%s Disabled.", getDescription().getName(), getDescription().getVersion()));
    }

    public static ConfigUtil getAlivePets() {
        return alivePets;
    }

    public static ConfigUtil getDeadPets() {
        return deadPets;
    }
}