package de.kcodeyt.gogroups.command;

import de.kcodeyt.gogroups.GoGroups;
import de.kcodeyt.gogroups.config.GroupConfig;
import io.gomint.command.Command;
import io.gomint.command.CommandOutput;
import io.gomint.command.CommandSender;
import io.gomint.command.annotation.*;
import io.gomint.command.validator.StringValidator;

import java.util.Map;

@Name("addgroupperm")
@Description("Adds a permission to a group")
@Permission("gogroups.cmd.addgroupperm")
@Overload({
        @Parameter(name = "group", validator = StringValidator.class, arguments = {".*"}),
        @Parameter(name = "permission", validator = StringValidator.class, arguments = {".*"})
})
public class AddGroupPermCommand extends Command {

    @Override
    public CommandOutput execute(CommandSender commandSender, String s, Map<String, Object> argsMap) {
        CommandOutput commandOutput = new CommandOutput();
        GoGroups goGroups = GoGroups.getGoGroupsInstance();
        String group = (String) argsMap.get("group");
        String permission = (String) argsMap.get("permission");

        if(group == null || group.equals("") || permission == null || permission.equals(""))
            return commandOutput.fail("Usage: /addgroupperm <group> <permission>");

        if(!goGroups.getGroupManager().groupExists(group))
            return commandOutput.fail(goGroups.getFailPrefix() + " Group " + group + " does not exists.");

        GroupConfig groupConfig = goGroups.getGroupManager().getGroupsConfig().getGroupConfig(group);

        if(groupConfig.getPermissions().contains(permission))
            return commandOutput.fail(goGroups.getFailPrefix() + " Group " + group + " already has the permission " + permission + ".");

        groupConfig.getPermissions().add(permission);

        goGroups.getGroupManager().save();

        return commandOutput.success(goGroups.getSuccessPrefix() + " Permission " + permission + " successfully added to group " + group + ".");
    }

}
