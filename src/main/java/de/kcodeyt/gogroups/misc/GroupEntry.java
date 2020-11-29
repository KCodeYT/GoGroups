package de.kcodeyt.gogroups.misc;

import de.kcodeyt.gogroups.config.GroupConfig;
import io.gomint.permission.Group;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class GroupEntry {

    private final GroupConfig config;
    private final Group group;

    public String getName() {
        return this.config.getName();
    }

    public String getListName() {
        return this.config.getListName();
    }

    public String getNameTag() {
        return this.config.getNameTag();
    }

    public String getChatFormat() {
        return this.config.getChatFormat();
    }

    public List<String> getPermissions() {
        return this.config.getPermissions();
    }

}
