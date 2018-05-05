package de.kcodeyt.gogroups.config;

import io.gomint.config.YamlConfig;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class GroupConfig extends YamlConfig {

    private String name;

    private String chatFormat;

    private String nameTag;

    private List<String> permissions;

}
