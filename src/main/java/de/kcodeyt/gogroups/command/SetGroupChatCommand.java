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

@Name("setgroupchat")
@Description("Sets a chat format for a group")
@Permission("gogroups.cmd.setgroupchat")
@Overload({
        @Parameter(name = "group", validator = StringValidator.class, arguments = {".*"}),
        @Parameter(name = "chatFormat", validator = TextValidator.class, arguments = {".*"})
})
public class SetGroupChatCommand extends Command {

    @InjectPlugin
    private GoGroups goGroups;

    @Override
    public CommandOutput execute(CommandSender commandSender, String s, Map<String, Object> argsMap) {
        CommandOutput commandOutput = new CommandOutput();
        String group = (String) argsMap.get("group");
        String chatFormat = (String) argsMap.get("chatFormat");

        if(group == null || group.equals("") || chatFormat == null || chatFormat.equals(""))
            return commandOutput.fail("Usage: /setgroupchat <group> <chatFormat>");

        if(!this.goGroups.getGroupManager().groupExists(group))
            return commandOutput.fail(this.goGroups.getFailPrefix() + " Group " + group + " does not exists.");

        GroupConfig groupConfig = this.goGroups.getGroupManager().getGroupsConfig().getGroupConfig(group);
        groupConfig.setChatFormat(chatFormat);

        this.goGroups.getGroupManager().save();

        return commandOutput.success(this.goGroups.getSuccessPrefix() + " Chat format " + chatFormat + " successfully set to group " + group + ".");
    }

}
