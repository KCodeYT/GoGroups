package de.kcodeyt.gogroups;

import de.kcodeyt.gogroups.manager.GroupManager;
import io.gomint.plugin.Plugin;
import io.gomint.plugin.PluginName;
import io.gomint.plugin.Version;
import lombok.Getter;

@PluginName("GoGroups")
@Version(major = 1, minor = 0)
public class GoGroups extends Plugin {

    @Getter
    private GroupManager groupManager;

    @Override
    public void onInstall() {
        if(!this.getDataFolder().exists())
            this.getDataFolder().mkdirs();

        this.groupManager = new GroupManager(this);
    }

    @Override
    public void onUninstall() {

    }

}
