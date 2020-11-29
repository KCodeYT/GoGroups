package de.kcodeyt.gogroups.manager;

import de.kcodeyt.gogroups.GoGroups;
import de.kcodeyt.gogroups.config.GroupConfig;
import de.kcodeyt.gogroups.config.GroupsConfig;
import de.kcodeyt.gogroups.misc.GroupEntry;
import io.gomint.config.InvalidConfigurationException;
import io.gomint.permission.Group;
import lombok.Getter;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

public class GroupManager {

    private final GoGroups goGroups;
    @Getter
    private final GroupsConfig groupsConfig;
    private final Map<GroupConfig, Group> groups;
    private final File configFile;

    public GroupManager(GoGroups goGroups) throws InvalidConfigurationException {
        this.goGroups = goGroups;
        this.groupsConfig = new GroupsConfig();
        this.groups = new HashMap<>();
        this.configFile = new File(this.goGroups.getDataFolder(), "groups.yml");

        if(!this.configFile.exists()) {
            this.groupsConfig.getGroups().add(new GroupConfig());
            this.save();
        }

        this.groupsConfig.init(this.configFile);
        this.groupsConfig.getGroups().forEach(this::createOfConfig);
    }

    private void createOfConfig(GroupConfig groupConfig) {
        final String groupName = groupConfig.getName();
        final String chatFormat = groupConfig.getChatFormat();
        final String nameTag = groupConfig.getNameTag();
        final String listName = groupConfig.getListName();
        final List<String> permissions = groupConfig.getPermissions();
        this.createGroup(groupName, chatFormat, nameTag, listName, permissions);
    }

    private Group createPermOfConfig(GroupConfig groupConfig) {
        final Group permissionGroup = this.goGroups.getServer().getGroupManager().getOrCreateGroup(groupConfig.getName());
        for(final String permission : groupConfig.getPermissions())
            permissionGroup.setPermission(permission, true);
        return permissionGroup;
    }

    public boolean groupExists(String groupName) {
        return this.getGroup(groupName) != null;
    }

    public void createGroup(String groupName, String chatFormat, String nameTag, String listName, List<String> permissions) {
        if(!this.groupExists(groupName)) {
            if(this.groupsConfig.getGroupConfig(groupName) == null) {
                final GroupConfig groupConfig = new GroupConfig();
                groupConfig.setName(groupName);
                groupConfig.setChatFormat(Objects.requireNonNullElseGet(chatFormat, () -> groupConfig.getChatFormat().replace("Guest", groupName)));
                groupConfig.setNameTag(Objects.requireNonNullElseGet(nameTag, () -> groupConfig.getNameTag().replace("Guest", groupName)));
                groupConfig.setListName(Objects.requireNonNullElseGet(listName, () -> groupConfig.getListName().replace("Guest", groupName)));
                groupConfig.setPermissions(permissions);

                this.groupsConfig.getGroups().add(groupConfig);
                this.save();
            }

            final GroupConfig groupConfig = this.groupsConfig.getGroupConfig(groupName);
            this.groups.put(groupConfig, this.createPermOfConfig(groupConfig));
        }
    }

    /** @noinspection WeakerAccess */
    public List<GroupEntry> getGroups() {
        return this.groups.entrySet().stream().map(entry -> new GroupEntry(this.groupsConfig.getGroupConfig(entry.getKey().getName()), entry.getValue())).collect(Collectors.toUnmodifiableList());
    }

    public GroupEntry getGroup(String groupName) {
        return this.getGroups().stream().filter(groupEntry -> groupEntry.getName().equalsIgnoreCase(groupName)).findAny().orElse(null);
    }

    public void save() {
        this.goGroups.getScheduler().executeAsync(() -> {
            try {
                this.groupsConfig.save(this.configFile);
            } catch(InvalidConfigurationException e) {
                this.goGroups.getLogger().error("Error whilst saving the groups file: ", e);
            }
        });
    }

    public void removeGroup(String groupName) {
        if(this.groupExists(groupName)) {
            final GroupConfig groupConfig = this.groupsConfig.getGroupConfig(groupName);
            this.groups.remove(groupConfig);
            this.groupsConfig.getGroups().remove(groupConfig);
            this.save();
        }
    }

}
