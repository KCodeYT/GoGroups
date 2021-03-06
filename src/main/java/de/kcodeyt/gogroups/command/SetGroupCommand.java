package de.kcodeyt.gogroups.command;

import de.kcodeyt.gogroups.GoGroups;
import de.kcodeyt.gogroups.config.PlayerConfig;
import de.kcodeyt.gogroups.misc.GroupEntry;
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
        final EntityPlayer target = (EntityPlayer) argsMap.get("target");
        final String groupName = (String) argsMap.get("group");
        if(target == null || groupName == null || groupName.equals(""))
            return CommandOutput.failure("Usage: /setgroup <target> <group>");
        if(!this.goGroups.getPlayerManager().playerExists(target.getName()))
            return CommandOutput.failure(this.goGroups.getFailPrefix() + " Player " + target.getName() + " does not exists.");
        final PlayerConfig playerConfig = this.goGroups.getPlayerManager().getPlayerConfig(target.getName());
        if(!this.goGroups.getGroupManager().groupExists(groupName))
            return CommandOutput.failure(this.goGroups.getFailPrefix() + " Group " + groupName + " does not exists.");
        final GroupEntry currentGroup = this.goGroups.getGroupManager().getGroup(playerConfig.getGroup());
        target.getPermissionManager().removeGroup(currentGroup.getGroup());
        playerConfig.setGroup(groupName);
        final GroupEntry group = this.goGroups.getGroupManager().getGroup(groupName);
        target.setNameTag(group.getNameTag().
                replace("%name%", target.getName()).
                replace("&", "§"));
        target.setPlayerListName(group.getListName().
                replace("%name%", target.getName()).
                replace("&", "§"));
        target.getPermissionManager().addGroup(group.getGroup());
        this.goGroups.getScheduler().executeAsync(() -> {
            try {
                playerConfig.save();
            } catch(InvalidConfigurationException e) {
                this.goGroups.getLogger().error("Error whilst initialising the file from " + target.getName() + ": ", e);
            }
        });
        return CommandOutput.successful(this.goGroups.getSuccessPrefix() + " Group " + group.getName() + " successfully set to player " + target.getName() + ".");
    }

}
