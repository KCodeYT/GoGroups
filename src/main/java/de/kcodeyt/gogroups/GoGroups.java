package de.kcodeyt.gogroups;

import de.kcodeyt.gogroups.listener.PlayerListener;
import de.kcodeyt.gogroups.manager.GroupManager;
import de.kcodeyt.gogroups.manager.PlayerManager;
import io.gomint.plugin.Plugin;
import io.gomint.plugin.PluginName;
import io.gomint.plugin.Version;
import lombok.Getter;

@Getter
@PluginName("GoGroups")
@Version(major = 1, minor = 3)
public class GoGroups extends Plugin {

    private GroupManager groupManager;
    private PlayerManager playerManager;
    private String successPrefix;
    private String failPrefix;

    @Override
    public void onInstall() {
        this.getLogger().info("Initialize GoGroups.");

        Exception exception = this.init();
        if(exception != null)
            this.getLogger().error("Error whilst installing GoGroups:", exception);
        else
            this.getLogger().info("Successfully installed GoGroups!");
    }

    private Exception init() {
        try {
            if(!this.getDataFolder().exists() && !this.getDataFolder().mkdirs())
                return new Exception("Could not create plugin folder");

            this.groupManager = new GroupManager(this);
            this.playerManager = new PlayerManager(this);
            this.successPrefix = "§6GoGroups §8§l»§r§a ";
            this.failPrefix = "§6GoGroups §8§l»§r§c ";

            this.registerListener(new PlayerListener(this));
        } catch(Exception e) {
            return e;
        }

        return null;
    }

    @Override
    public void onUninstall() {
        this.getLogger().info("Uninstalled GoGroups.");
    }

}
