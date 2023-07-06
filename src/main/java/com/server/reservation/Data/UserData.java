package com.server.reservation.Data;

import com.server.reservation.Main;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class UserData {

    public static YamlConfiguration config = new YamlConfiguration();
    public static File playerFile = new File(Main.getPlugin().getUuidFolder(), "plugins/Reservation/UUIDs/");

    public static YamlConfiguration getPlayerConfig(OfflinePlayer player) {
        playerFile = new File(Main.getPlugin().getUuidFolder(), "plugins/Reservation/UUIDs/" + player.getUniqueId().toString() + ".yml");

        if (!playerFile.exists()) {
            Main.getPlugin().createPlayerDefaults();
        }

        config = YamlConfiguration.loadConfiguration(playerFile);
        return config;
    }
}
