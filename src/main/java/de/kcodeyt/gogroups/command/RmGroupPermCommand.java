package de.kcodeyt.gogroups.command;

import io.gomint.command.Command;
import io.gomint.command.CommandOutput;
import io.gomint.command.annotation.Description;
import io.gomint.command.annotation.Name;
import io.gomint.command.annotation.Overload;
import io.gomint.command.annotation.Permission;
import io.gomint.entity.EntityPlayer;

import java.util.Map;

@Name("rmgroupperm")
@Description("Removes a permission from a group")
@Permission("gogroups.cmd.rmgroupperm")
@Overload()
public class RmGroupPermCommand extends Command {

    @Override
    public CommandOutput execute(EntityPlayer player, String s, Map<String, Object> argsMap) {
        return null;
    }

}
