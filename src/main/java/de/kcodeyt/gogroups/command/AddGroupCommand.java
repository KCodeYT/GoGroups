package de.kcodeyt.gogroups.command;

import de.kcodeyt.gogroups.GoGroups;
import io.gomint.command.Command;
import io.gomint.command.CommandOutput;
import io.gomint.command.annotation.*;
import io.gomint.command.validator.StringValidator;
import io.gomint.entity.EntityPlayer;

import java.util.ArrayList;
import java.util.Map;

@Name("addgroup")
@Description("Adds a group")
@Permission("gogroups.cmd.addgroup")
@Overload({
        @Parameter(name = "group", validator = StringValidator.class, arguments = {".*"})
})
public class AddGroupCommand extends Command {

    @Override
    public CommandOutput execute(EntityPlayer player, String s, Map<String, Object> argsMap) {
        CommandOutput commandOutput = new CommandOutput();
        GoGroups goGroups = GoGroups.getGoGroupsInstance();
        String group = (String) argsMap.get("group");

        if(group == null || group.equals(""))
            return commandOutput.fail("Usage: /addgroup <group>");

        if(goGroups.getGroupManager().groupExists(group))
            return commandOutput.fail(goGroups.getFailPrefix() + " Group " + group + " already exists.");

        goGroups.getGroupManager().createGroup(group, null, null, new ArrayList<>());

        return commandOutput.success(goGroups.getSuccessPrefix() + " Group " + group + " successfully created.");
    }

}
