package de.kcodeyt.gogroups.command;

import io.gomint.command.Command;
import io.gomint.command.CommandOutput;
import io.gomint.command.annotation.*;
import io.gomint.entity.EntityPlayer;

import java.util.Map;

@Name("addgroup")
@Description("Adds a group")
@Permission("gogroups.cmd.addgroup")
@Overload()
public class AddGroupCommand extends Command {

    @Override
    public CommandOutput execute(EntityPlayer player, String s, Map<String, Object> argsMap) {
        return null;
    }

}
