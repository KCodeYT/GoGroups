package de.kcodeyt.gogroups.listener;

import de.kcodeyt.gogroups.GoGroups;
import de.kcodeyt.gogroups.config.PlayerConfig;
import de.kcodeyt.gogroups.misc.GroupEntry;
import io.gomint.entity.EntityPlayer;
import io.gomint.event.EventHandler;
import io.gomint.event.EventListener;
import io.gomint.event.player.PlayerChatEvent;
import io.gomint.event.player.PlayerJoinEvent;
import io.gomint.event.player.PlayerQuitEvent;

import java.util.List;

/** @noinspection unused*/
public class PlayerListener implements EventListener {

    private final GoGroups goGroups;

    public PlayerListener(GoGroups goGroups) {
        this.goGroups = goGroups;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        final EntityPlayer player = event.getPlayer();
        this.goGroups.getPlayerManager().loadPlayer(player, playerConfig -> {
            final String groupName = playerConfig.getGroup();
            final List<String> permissions = playerConfig.getPermissions();
            for(final String permission : permissions)
                player.getPermissionManager().setPermission(permission, true);
            if(this.goGroups.getGroupManager().groupExists(groupName)) {
                final GroupEntry group = this.goGroups.getGroupManager().getGroup(groupName);
                player.setNameTag(group.getNameTag().
                        replace("%name%", player.getName()).
                        replace("&", "§"));
                player.setPlayerListName(group.getListName().
                        replace("%name%", player.getName()).
                        replace("&", "§"));
                player.getPermissionManager().addGroup(group.getGroup());
            }

            if(player.getPermissionManager().hasPermission("*"))
                player.setOp(true);
        });
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        this.goGroups.getPlayerManager().updatePlayer(event.getPlayer(), null, null);
    }

    @EventHandler
    public void onChat(PlayerChatEvent event) {
        final EntityPlayer player = event.getPlayer();
        final PlayerConfig playerConfig = this.goGroups.getPlayerManager().getPlayerConfig(player.getName());
        final String groupName = playerConfig.getGroup();
        if(this.goGroups.getGroupManager().groupExists(groupName)) {
            event.setCancelled(true);
            final GroupEntry group = this.goGroups.getGroupManager().getGroup(groupName);
            final String chatFormat = group.getChatFormat().
                    replace("%name%", player.getName()).
                    replace("%msg%", event.getText()).
                    replace("&", "§");
            event.getRecipients().forEach(recipient -> recipient.sendMessage(chatFormat));
        }
    }

}
