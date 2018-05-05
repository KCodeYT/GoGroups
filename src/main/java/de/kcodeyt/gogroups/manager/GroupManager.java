package de.kcodeyt.gogroups.manager;

import de.kcodeyt.gogroups.GoGroups;
import de.kcodeyt.gogroups.config.GroupConfig;
import de.kcodeyt.gogroups.config.GroupsConfig;
import de.kcodeyt.gogroups.misc.Group;
import io.gomint.config.InvalidConfigurationException;
import lombok.Getter;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GroupManager {

    private GoGroups goGroups;

    @Getter
    private GroupsConfig groupsConfig;

    @Getter
    private Map<String, Group> groups;

    private File configFile;

    public GroupManager(GoGroups goGroups) {
        this.goGroups = goGroups;
        this.groupsConfig = new GroupsConfig();
        this.groups = new HashMap<>();

        this.configFile = new File(this.goGroups.getDataFolder(), "groups.yml");

        if(!this.configFile.exists()) {
            this.groupsConfig.getGroups().add(new GroupConfig());

            this.save();
        }

        try {
            this.groupsConfig.init(this.configFile);
        } catch(InvalidConfigurationException e) {
            e.printStackTrace();
        }

        for(GroupConfig groupConfig : this.groupsConfig.getGroups()) {
            String groupName = groupConfig.getName();
            String chatFormat = groupConfig.getChatFormat();
            String nameTag = groupConfig.getNameTag();
            List<String> permissions = groupConfig.getPermissions();

            this.createGroup(groupName, chatFormat, nameTag, permissions);
        }
    }

    public boolean groupExists(String groupName) {
        return this.getGroups().containsKey(groupName);
    }

    public void createGroup(String groupName, String chatFormat, String nameTag, List<String> permissions) {
        if(!this.groupExists(groupName)) {
            if(this.getGroupsConfig().getGroupConfig(groupName) == null) {
                GroupConfig groupConfig = new GroupConfig();
                groupConfig.setName(groupName);

                {
                    if(chatFormat == null)
                        groupConfig.setChatFormat(groupConfig.getChatFormat().replace("Guest", groupName));
                    else
                        groupConfig.setChatFormat(chatFormat);

                    if(nameTag == null)
                        groupConfig.setNameTag(groupConfig.getNameTag().replace("Guest", groupName));
                    else
                        groupConfig.setNameTag(nameTag);
                }

                groupConfig.setPermissions(permissions);

                this.getGroupsConfig().getGroups().add(groupConfig);

                this.save();
            }

            io.gomint.permission.Group permissionGroup = this.goGroups.getServer().getGroupManager().getOrCreateGroup(groupName);

            for(String permission : permissions)
                permissionGroup.setPermission(permission, true);

            this.getGroups().put(groupName, new Group(groupName, chatFormat, nameTag, permissionGroup));
        }
    }

    public void save() {
        this.goGroups.getScheduler().executeAsync(() -> {
            try {
                this.getGroupsConfig().save(this.configFile);
            } catch(InvalidConfigurationException e) {
                e.printStackTrace();
            }
        });
    }

    public void removeGroup(String groupName) {
        if(this.groupExists(groupName)) {
            this.getGroups().remove(groupName);

            this.getGroupsConfig().getGroups().remove(this.getGroupsConfig().getGroupConfig(groupName));

            this.save();
        }
    }

}
