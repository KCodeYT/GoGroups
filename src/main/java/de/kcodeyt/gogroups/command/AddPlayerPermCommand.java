package de.kcodeyt.gogroups.command;

import de.kcodeyt.gogroups.GoGroups;
import de.kcodeyt.gogroups.config.PlayerConfig;
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

@Name("addplayerperm")
@Description("Adds a permission to a player")
@Permission("gogroups.cmd.addplayerperm")
@Overload({
        @Parameter(name = "target", validator = TargetValidator.class),
        @Parameter(name = "permission", validator = StringValidator.class, arguments = {".*"})
})
public class AddPlayerPermCommand extends Command {

    @InjectPlugin
    private GoGroups goGroups;

    @Override
    public CommandOutput execute(CommandSender commandSender, String s, Map<String, Object> argsMap) {
        CommandOutput commandOutput = new CommandOutput();
        EntityPlayer target = (EntityPlayer) argsMap.get("target");
        String permission = (String) argsMap.get("permission");

        if(target == null || permission == null || permission.equals(""))
            return commandOutput.fail("Usage: /addplayerperm <target> <permission>");

        if(!this.goGroups.getPlayerManager().playerExists(target.getName()))
            return commandOutput.fail(this.goGroups.getFailPrefix() + " Player " + target.getName() + " does not exists.");

        PlayerConfig playerConfig = this.goGroups.getPlayerManager().getPlayerConfig(target.getName());

        if(playerConfig.getPermissions().contains(permission))
            return commandOutput.fail(this.goGroups.getFailPrefix() + " Player " + target.getName() + " already has the permission " + permission + ".");

        playerConfig.getPermissions().add(permission);

        this.goGroups.getScheduler().executeAsync(() -> {
            try {
                playerConfig.save();
            } catch(InvalidConfigurationException e) {
                this.goGroups.getLogger().error("Error whilst initialising the file from " + target.getName() + ": ", e);
            }
        });

        return commandOutput.success(this.goGroups.getSuccessPrefix() + " Permission " + permission + " successfully added to player " + target.getName() + ".");
    }

}
