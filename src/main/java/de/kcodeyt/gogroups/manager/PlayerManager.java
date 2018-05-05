package de.kcodeyt.gogroups.manager;

import de.kcodeyt.gogroups.GoGroups;
import de.kcodeyt.gogroups.config.PlayerConfig;
import io.gomint.config.InvalidConfigurationException;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerManager {

    private GoGroups goGroups;

    private Map<String, PlayerConfig> playerConfigs;

    private File playerDirectory;

    public PlayerManager(GoGroups goGroups) {
        this.goGroups = goGroups;
        this.playerConfigs = new HashMap<>();

        this.playerDirectory = new File(this.goGroups.getDataFolder(), "players");
    }

    public boolean playerExists(String playerName) {
        return this.playerConfigs.containsKey(playerName);
    }

    public void loadPlayer(String playerName) {
        PlayerConfig playerConfig;
        if(this.playerExists(playerName)) {
            playerConfig = this.playerConfigs.get(playerName);
        } else {
            playerConfig = new PlayerConfig();
            playerConfig.setName("name");
            playerConfig.setGroup("group");
            playerConfig.setPermissions(new ArrayList<>());
        }

        this.goGroups.getScheduler().executeAsync(() -> {
            try {
                playerConfig.load(new File(this.playerDirectory, playerName));
            } catch(InvalidConfigurationException e) {
                e.printStackTrace();
            }
        });
    }

    public void updatePlayer(String playerName, String groupName, List<String> permissions) {
        if(this.playerExists(playerName)) {
            PlayerConfig playerConfig = this.playerConfigs.get(playerName);

            if(groupName != null)
                playerConfig.setGroup(groupName);

            if(permissions != null)
                for(String permission : permissions)
                    playerConfig.getPermissions().add(permission);

            this.goGroups.getScheduler().executeAsync(() -> {
                try {
                    playerConfig.save(new File(this.playerDirectory, playerName));
                } catch(InvalidConfigurationException e) {
                    e.printStackTrace();
                }
            });
        }
    }

}
