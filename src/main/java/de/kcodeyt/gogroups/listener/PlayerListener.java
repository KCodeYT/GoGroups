package de.kcodeyt.gogroups.listener;

import de.kcodeyt.gogroups.GoGroups;
import de.kcodeyt.gogroups.config.PlayerConfig;
import de.kcodeyt.gogroups.misc.Group;
import io.gomint.entity.EntityPlayer;
import io.gomint.event.EventHandler;
import io.gomint.event.EventListener;
import io.gomint.event.player.PlayerChatEvent;
import io.gomint.event.player.PlayerJoinEvent;
import io.gomint.event.player.PlayerQuitEvent;

import java.util.List;

public class PlayerListener implements EventListener {

    private GoGroups goGroups;

    public PlayerListener(GoGroups goGroups) {
        this.goGroups = goGroups;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        EntityPlayer player = event.getPlayer();

        this.goGroups.getPlayerManager().loadPlayer(player, playerConfig -> {
            String groupName = playerConfig.getGroup();
            List<String> permissions = playerConfig.getPermissions();

            for(String permission : permissions)
                player.getPermissionManager().setPermission(permission, true);

            if(this.goGroups.getGroupManager().groupExists(groupName)) {
                Group group = this.goGroups.getGroupManager().getGroups().get(groupName);
                String nameTag = group.getNameTag().
                        replace("%name%", player.getName()).
                        replace("&", "§");

                player.setNameTag(nameTag);
                player.setPlayerListName(nameTag);
                player.getPermissionManager().addGroup(group.getPermissionGroup());
            }

            if(player.hasPermission("*"))
                player.setOp(true);
        });
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        this.goGroups.getPlayerManager().updatePlayer(event.getPlayer(), null, null);
    }

    @EventHandler
    public void onChat(PlayerChatEvent event) {
        EntityPlayer player = event.getPlayer();
        PlayerConfig playerConfig = this.goGroups.getPlayerManager().getPlayerConfig(player.getName());

        String groupName = playerConfig.getGroup();

        if(this.goGroups.getGroupManager().groupExists(groupName)) {
            event.setCancelled(true);

            Group group = this.goGroups.getGroupManager().getGroups().get(groupName);
            String chatFormat = group.getChatFormat().
                    replace("%name%", player.getName()).
                    replace("%msg%", event.getText()).
                    replace("&", "§");

            event.getRecipients().forEach(player1 -> player1.sendMessage(chatFormat));
        }
    }

}
