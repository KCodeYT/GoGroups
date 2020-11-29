package de.kcodeyt.gogroups.config;

import io.gomint.config.YamlConfig;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class GroupsConfig extends YamlConfig {

    private String defaultGroup;
    private List<GroupConfig> groups;

    public GroupsConfig() {
        this.defaultGroup = "Guest";
        this.groups = new ArrayList<>();
    }

    public GroupConfig getGroupConfig(String groupName) {
        return this.groups.stream().filter(groupConfig -> groupConfig.getName().equalsIgnoreCase(groupName)).findAny().orElse(null);
    }

}
