package de.kcodeyt.gogroups.command;

import de.kcodeyt.gogroups.GoGroups;
import de.kcodeyt.gogroups.config.PlayerConfig;
import de.kcodeyt.gogroups.misc.Group;
import io.gomint.command.Command;
import io.gomint.command.CommandOutput;
import io.gomint.command.CommandSender;
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
    public CommandOutput execute(CommandSender commandSender, String s, Map<String, Object> argsMap) {
        CommandOutput commandOutput = new CommandOutput();
        GoGroups goGroups = GoGroups.getGoGroupsInstance();
        EntityPlayer target = (EntityPlayer) argsMap.get("target");
        String groupName = (String) argsMap.get("group");

        if(target == null || groupName == null || groupName.equals(""))
            return commandOutput.fail("Usage: /setgroup <target> <group>");

        if(!goGroups.getPlayerManager().playerExists(target.getName()))
            return commandOutput.fail(goGroups.getFailPrefix() + " Player " + target.getName() + " does not exists.");

        PlayerConfig playerConfig = goGroups.getPlayerManager().getPlayerConfig(target.getName());

        if(!goGroups.getGroupManager().groupExists(groupName))
            return commandOutput.fail(goGroups.getFailPrefix() + " Group " + groupName + " does not exists.");

        Group currentGroup = goGroups.getGroupManager().getGroups().get(playerConfig.getGroup());
        target.getPermissionManager().removeGroup(currentGroup.getPermissionGroup());

        playerConfig.setGroup(groupName);

        Group group = goGroups.getGroupManager().getGroups().get(groupName);
        String nameTag = group.getNameTag().
                replace("%name%", target.getName()).
                replace("&", "§");

        target.setNameTag(nameTag);
        target.setPlayerListName(nameTag);
        target.getPermissionManager().addGroup(group.getPermissionGroup());

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
