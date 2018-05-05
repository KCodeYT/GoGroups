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
    private GroupManager groupManager;

    @Getter
    private PlayerManager playerManager;

    @Override
    public void onInstall() {
        this.init();
    }

    private void init() {
        if(!this.getDataFolder().exists())
            this.getDataFolder().mkdirs();

        this.groupManager = new GroupManager(this);
        this.playerManager = new PlayerManager(this);

        this.registerListener(new PlayerListener(this));
    }

    @Override
    public void onUninstall() {

    }

}
