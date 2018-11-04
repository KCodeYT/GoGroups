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
import io.gomint.plugin.injection.InjectPlugin;

import java.util.Map;

@Name("setgroup")
@Description("Sets a group to a player")
@Permission("gogroups.cmd.setgroup")
@Overload({
        @Parameter(name = "target", validator = TargetValidator.class),
        @Parameter(name = "group", validator = StringValidator.class, arguments = {".*"})
})
public class SetGroupCommand extends Command {

    @InjectPlugin
    private GoGroups goGroups;

    @Override
    public CommandOutput execute(CommandSender commandSender, String s, Map<String, Object> argsMap) {
        CommandOutput commandOutput = new CommandOutput();
        EntityPlayer target = (EntityPlayer) argsMap.get("target");
        String groupName = (String) argsMap.get("group");

        if(target == null || groupName == null || groupName.equals(""))
            return commandOutput.fail("Usage: /setgroup <target> <group>");

        if(!this.goGroups.getPlayerManager().playerExists(target.getName()))
            return commandOutput.fail(this.goGroups.getFailPrefix() + " Player " + target.getName() + " does not exists.");

        PlayerConfig playerConfig = this.goGroups.getPlayerManager().getPlayerConfig(target.getName());

        if(!this.goGroups.getGroupManager().groupExists(groupName))
            return commandOutput.fail(this.goGroups.getFailPrefix() + " Group " + groupName + " does not exists.");

        Group currentGroup = this.goGroups.getGroupManager().getGroups().get(playerConfig.getGroup());
        target.getPermissionManager().removeGroup(currentGroup.getPermissionGroup());

        playerConfig.setGroup(groupName);

        Group group = this.goGroups.getGroupManager().getGroups().get(groupName);
        String nameTag = group.getNameTag().
                replace("%name%", target.getName()).
                replace("&", "§");
        String listName = group.getListName().
                replace("%name%", target.getName()).
                replace("&", "§");

        target.setNameTag(nameTag);
        target.setPlayerListName(listName);
        target.getPermissionManager().addGroup(group.getPermissionGroup());

        this.goGroups.getScheduler().executeAsync(() -> {
            try {
                playerConfig.save();
            } catch(InvalidConfigurationException e) {
                e.printStackTrace();
            }
        });

        return commandOutput.success(this.goGroups.getSuccessPrefix() + " Group " + group + " successfully set to player " + target.getName() + ".");
    }

}
