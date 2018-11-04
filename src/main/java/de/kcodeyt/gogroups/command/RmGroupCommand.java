package de.kcodeyt.gogroups.command;

import de.kcodeyt.gogroups.GoGroups;
import io.gomint.command.Command;
import io.gomint.command.CommandOutput;
import io.gomint.command.CommandSender;
import io.gomint.command.annotation.*;
import io.gomint.command.validator.StringValidator;
import io.gomint.plugin.injection.InjectPlugin;

import java.util.Map;

@Name("rmgroup")
@Description("Removes a group")
@Permission("gogroups.cmd.rmgroup")
@Overload({
        @Parameter(name = "group", validator = StringValidator.class, arguments = {".*"})
})
public class RmGroupCommand extends Command {

    @InjectPlugin
    private GoGroups goGroups;

    @Override
    public CommandOutput execute(CommandSender commandSender, String s, Map<String, Object> argsMap) {
        CommandOutput commandOutput = new CommandOutput();
        String group = (String) argsMap.get("group");

        if(group == null || group.equals(""))
            return commandOutput.fail("Usage: /rmgroup <group>");

        if(!this.goGroups.getGroupManager().groupExists(group))
            return commandOutput.fail(this.goGroups.getFailPrefix() + " Group " + group + " does not exists.");

        this.goGroups.getGroupManager().removeGroup(group);

        return commandOutput.success(this.goGroups.getSuccessPrefix() + " Group " + group + " successfully removed.");
    }

}
