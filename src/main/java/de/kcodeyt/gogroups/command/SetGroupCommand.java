package de.kcodeyt.gogroups.command;

import de.kcodeyt.gogroups.GoGroups;
import de.kcodeyt.gogroups.config.PlayerConfig;
import io.gomint.command.Command;
import io.gomint.command.CommandOutput;
import io.gomint.command.annotation.*;
import io.gomint.command.validator.StringValidator;
import io.gomint.command.validator.TargetValidator;
import io.gomint.config.InvalidConfigurationException;
import io.gomint.entity.EntityPlayer;

import java.util.Map;

@Name("setgroup")
@Description("Sets a group to a player")
@Permission("gogroups.cmd.setgroup")
@Overload({
        @Parameter(name = "target", validator = TargetValidator.class),
        @Parameter(name = "group", validator = StringValidator.class, arguments = {".*"})
})
public class SetGroupCommand extends Command {

    @Override
    public CommandOutput execute(EntityPlayer player, String s, Map<String, Object> argsMap) {
        CommandOutput commandOutput = new CommandOutput();
        GoGroups goGroups = GoGroups.getGoGroupsInstance();
        EntityPlayer target = (EntityPlayer) argsMap.get("target");
        String group = (String) argsMap.get("group");

        if(target == null || group == null || group.equals(""))
            return commandOutput.fail("Usage: /setgroup <target> <group>");

        if(!goGroups.getPlayerManager().playerExists(target.getName()))
            return commandOutput.fail(goGroups.getFailPrefix() + " Player " + target.getName() + " does not exists.");

        PlayerConfig playerConfig = goGroups.getPlayerManager().getPlayerConfig(target.getName());

        if(!goGroups.getGroupManager().groupExists(group))
            return commandOutput.fail(goGroups.getFailPrefix() + " Group " + group + " does not exists.");

        playerConfig.setGroup(group);

        goGroups.getScheduler().executeAsync(() -> {
            try {
                playerConfig.save();
            } catch(InvalidConfigurationException e) {
                e.printStackTrace();
            }
        });

        return commandOutput.success(goGroups.getSuccessPrefix() + " Group " + group + " successfully set to player " + target.getName() + ".");
    }

}
