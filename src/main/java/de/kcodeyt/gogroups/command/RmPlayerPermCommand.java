package de.kcodeyt.gogroups.command;

import io.gomint.command.Command;
import io.gomint.command.CommandOutput;
import io.gomint.command.annotation.Description;
import io.gomint.command.annotation.Name;
import io.gomint.command.annotation.Overload;
import io.gomint.command.annotation.Permission;
import io.gomint.entity.EntityPlayer;

import java.util.Map;

@Name("rmplayerperm")
@Description("Removes a permission from a player")
@Permission("gogroups.cmd.rmplayerperm")
@Overload()
public class RmPlayerPermCommand extends Command {

    @Override
    public CommandOutput execute(EntityPlayer player, String s, Map<String, Object> argsMap) {
        return null;
    }

}
