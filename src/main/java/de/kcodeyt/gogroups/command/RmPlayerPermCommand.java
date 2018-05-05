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

@Name("rmplayerperm")
@Description("Removes a permission from a player")
@Permission("gogroups.cmd.rmplayerperm")
@Overload({
        @Parameter(name = "target", validator = TargetValidator.class),
        @Parameter(name = "permission", validator = StringValidator.class, arguments = {".*"})
})
public class RmPlayerPermCommand extends Command {

    @Override
    public CommandOutput execute(EntityPlayer player, String s, Map<String, Object> argsMap) {
        CommandOutput commandOutput = new CommandOutput();
        GoGroups goGroups = GoGroups.getGoGroupsInstance();
        EntityPlayer target = (EntityPlayer) argsMap.get("target");
        String permission = (String) argsMap.get("permission");

        if(target == null || permission == null || permission.equals(""))
            return commandOutput.fail("Usage: /rmplayerperm <target> <permission>");

        if(!goGroups.getPlayerManager().playerExists(target.getName()))
            return commandOutput.fail(goGroups.getFailPrefix() + " Player " + target.getName() + " does not exists.");

        PlayerConfig playerConfig = goGroups.getPlayerManager().getPlayerConfig(target.getName());

        if(!playerConfig.getPermissions().contains(permission))
            return commandOutput.fail(goGroups.getFailPrefix() + " Player " + target.getName() + " does not has the permission " + permission + ".");

        playerConfig.getPermissions().remove(permission);

        goGroups.getScheduler().executeAsync(() -> {
            try {
                playerConfig.save();
            } catch(InvalidConfigurationException e) {
                e.printStackTrace();
            }
        });

        return commandOutput.success(goGroups.getSuccessPrefix() + " Permission " + permission + " successfully removed from player " + target.getName() + ".");
    }

}
