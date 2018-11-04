package de.kcodeyt.gogroups.command;

import de.kcodeyt.gogroups.GoGroups;
import de.kcodeyt.gogroups.config.GroupConfig;
import io.gomint.command.Command;
import io.gomint.command.CommandOutput;
import io.gomint.command.CommandSender;
import io.gomint.command.annotation.*;
import io.gomint.command.validator.StringValidator;
import io.gomint.plugin.injection.InjectPlugin;

import java.util.Map;

@Name("addgroupperm")
@Description("Adds a permission to a group")
@Permission("gogroups.cmd.addgroupperm")
@Overload({
        @Parameter(name = "group", validator = StringValidator.class, arguments = {".*"}),
        @Parameter(name = "permission", validator = StringValidator.class, arguments = {".*"})
})
public class AddGroupPermCommand extends Command {

    @InjectPlugin
    private GoGroups goGroups;

    @Override
    public CommandOutput execute(CommandSender commandSender, String s, Map<String, Object> argsMap) {
        CommandOutput commandOutput = new CommandOutput();
        String group = (String) argsMap.get("group");
        String permission = (String) argsMap.get("permission");

        if(group == null || group.equals("") || permission == null || permission.equals(""))
            return commandOutput.fail("Usage: /addgroupperm <group> <permission>");

        if(!this.goGroups.getGroupManager().groupExists(group))
            return commandOutput.fail(this.goGroups.getFailPrefix() + " Group " + group + " does not exists.");

        GroupConfig groupConfig = this.goGroups.getGroupManager().getGroupsConfig().getGroupConfig(group);

        if(groupConfig.getPermissions().contains(permission))
            return commandOutput.fail(this.goGroups.getFailPrefix() + " Group " + group + " already has the permission " + permission + ".");

        groupConfig.getPermissions().add(permission);

        this.goGroups.getGroupManager().save();

        return commandOutput.success(this.goGroups.getSuccessPrefix() + " Permission " + permission + " successfully added to group " + group + ".");
    }

}
