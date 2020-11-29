package de.kcodeyt.gogroups.config;

import io.gomint.config.YamlConfig;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class PlayerConfig extends YamlConfig {

    private String name;
    private String group;
    private List<String> permissions;

    public PlayerConfig(String name, String group, List<String> permissions) {
        this.name = name;
        this.group = group;
        this.permissions = permissions;
    }

}
