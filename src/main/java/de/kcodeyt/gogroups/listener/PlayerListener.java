package de.kcodeyt.gogroups.listener;

import de.kcodeyt.gogroups.GoGroups;
import io.gomint.entity.EntityPlayer;
import io.gomint.event.EventHandler;
import io.gomint.event.EventListener;
import io.gomint.event.player.PlayerChatEvent;
import io.gomint.event.player.PlayerJoinEvent;
import io.gomint.event.player.PlayerQuitEvent;

public class PlayerListener implements EventListener {

    private GoGroups goGroups;

    public PlayerListener(GoGroups goGroups) {
        this.goGroups = goGroups;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        EntityPlayer player = event.getPlayer();

    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        EntityPlayer player = event.getPlayer();

    }

    @EventHandler
    public void onChat(PlayerChatEvent event) {
        EntityPlayer player = event.getPlayer();

    }

}
