package de.kcodeyt.gogroups.config;

import io.gomint.config.YamlConfig;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class GroupsConfig extends YamlConfig {

    public GroupsConfig() {
        this.defaultGroup = "Guest";
        this.groups = new ArrayList<>();
    }

    private String defaultGroup;

    private List<GroupConfig> groups;

    public GroupConfig getGroupConfig(String groupName) {
        for(GroupConfig groupConfig : this.getGroups())
            if(groupConfig.getName().equals(groupName))
                return groupConfig;
        return null;
    }

}
