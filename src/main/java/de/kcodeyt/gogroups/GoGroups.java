package de.kcodeyt.gogroups;

import de.kcodeyt.gogroups.listener.PlayerListener;
import de.kcodeyt.gogroups.manager.GroupManager;
import de.kcodeyt.gogroups.manager.PlayerManager;
import io.gomint.plugin.Plugin;
import io.gomint.plugin.PluginName;
import io.gomint.plugin.Version;
import lombok.Getter;

@PluginName("GoGroups")
@Version(major = 1, minor = 0)
public class GoGroups extends Plugin {

    @Getter
    private static GoGroups goGroupsInstance;

    @Getter
    private GroupManager groupManager;

    @Getter
    private PlayerManager playerManager;

    @Getter
    private String successPrefix;

    @Getter
    private String failPrefix;

    @Override
    public void onInstall() {
        this.init();
    }

    private void init() {
        GoGroups.goGroupsInstance = this;

        if(!this.getDataFolder().exists())
            this.getDataFolder().mkdirs();

        this.groupManager = new GroupManager(this);
        this.playerManager = new PlayerManager(this);

        this.successPrefix = "§6GoGroups §8§l»§r§a ";
        this.failPrefix = "§6GoGroups §8§l»§r§c ";

        this.registerListener(new PlayerListener(this));
    }

    @Override
    public void onUninstall() {

    }

}
