package de.kcodeyt.gogroups.config;

import io.gomint.config.YamlConfig;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class GroupConfig extends YamlConfig {

    public GroupConfig() {
        this.name = "Guest";
        this.chatFormat = "&8[&7Guest&8] &7%name% &8> &f%msg%";
        this.nameTag = "&8[&7Guest&8] &7%name%";
        this.permissions = new ArrayList<>();
    }

    private String name;

    private String chatFormat;

    private String nameTag;

    private List<String> permissions;

}
