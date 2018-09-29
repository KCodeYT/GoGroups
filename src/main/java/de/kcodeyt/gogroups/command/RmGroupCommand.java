package de.kcodeyt.gogroups.command;

import de.kcodeyt.gogroups.GoGroups;
import io.gomint.command.Command;
import io.gomint.command.CommandOutput;
import io.gomint.command.CommandSender;
import io.gomint.command.annotation.*;
import io.gomint.command.validator.StringValidator;

import java.util.Map;

@Name("rmgroup")
@Description("Removes a group")
@Permission("gogroups.cmd.rmgroup")
@Overload({
        @Parameter(name = "group", validator = StringValidator.class, arguments = {".*"})
})
public class RmGroupCommand extends Command {

    @Override
    public CommandOutput execute(CommandSender commandSender, String s, Map<String, Object> argsMap) {
        CommandOutput commandOutput = new CommandOutput();
        GoGroups goGroups = GoGroups.getGoGroupsInstance();
        String group = (String) argsMap.get("group");

        if(group == null || group.equals(""))
            return commandOutput.fail("Usage: /rmgroup <group>");

        if(!goGroups.getGroupManager().groupExists(group))
            return commandOutput.fail(goGroups.getFailPrefix() + " Group " + group + " does not exists.");

        goGroups.getGroupManager().removeGroup(group);

        return commandOutput.success(goGroups.getSuccessPrefix() + " Group " + group + " successfully removed.");
    }

}
