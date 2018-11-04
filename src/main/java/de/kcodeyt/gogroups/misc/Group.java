package de.kcodeyt.gogroups.misc;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Group {

    private String name;

    private String chatFormat;

    private String nameTag;

    private String listName;

    private io.gomint.permission.Group permissionGroup;

}
