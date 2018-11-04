package de.kcodeyt.gogroups.command;

import de.kcodeyt.gogroups.GoGroups;
import de.kcodeyt.gogroups.config.GroupConfig;
import io.gomint.command.Command;
import io.gomint.command.CommandOutput;
import io.gomint.command.CommandSender;
import io.gomint.command.annotation.*;
import io.gomint.command.validator.StringValidator;
import io.gomint.command.validator.TextValidator;
import io.gomint.plugin.injection.InjectPlugin;

import java.util.Map;

@Name("setgroupnametag")
@Description("Sets a name tag for a group")
@Permission("gogroups.cmd.setgroupnametag")
@Overload({
        @Parameter(name = "group", validator = StringValidator.class, arguments = {".*"}),
        @Parameter(name = "nameTag", validator = TextValidator.class, arguments = {".*"})
})
public class SetGroupNametagCommand extends Command {

    @InjectPlugin
    private GoGroups goGroups;

    @Override
    public CommandOutput execute(CommandSender commandSender, String s, Map<String, Object> argsMap) {
        CommandOutput commandOutput = new CommandOutput();
        String group = (String) argsMap.get("group");
        String nameTag = (String) argsMap.get("nameTag");

        if(group == null || group.equals("") || nameTag == null || nameTag.equals(""))
            return commandOutput.fail("Usage: /setgroupnametag <group> <nameTag>");

        if(!this.goGroups.getGroupManager().groupExists(group))
            return commandOutput.fail(this.goGroups.getFailPrefix() + " Group " + group + " does not exists.");

        GroupConfig groupConfig = this.goGroups.getGroupManager().getGroupsConfig().getGroupConfig(group);
        groupConfig.setNameTag(nameTag);

        this.goGroups.getGroupManager().save();

        return commandOutput.success(this.goGroups.getSuccessPrefix() + " Name tag " + nameTag + " successfully set to group " + group + ".");
    }

}
