package com.github.jselby.ej;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;

public class JetpackListener implements Listener {
	@EventHandler
	public void onPlayerToggleCrouchEvent(PlayerToggleSneakEvent evt) {
		if (evt.isSneaking()) {
			JetpackEvent event = new JetpackEvent(evt.getPlayer(),
					FlightTypes.CROUCH);
			JetpackManager.getInstance().onJetpackEvent(event);
			evt.setCancelled(event.isCancelled());
		}
	}

	@EventHandler
	public void onFlightToggleEvent(PlayerToggleFlightEvent evt) {
		if (evt.getPlayer().getGameMode() != GameMode.CREATIVE
				&& EasyJetpack.getInstance().haveAllowedFlying(evt.getPlayer())) {
			if (!evt.isFlying()) {
				JetpackEvent event = new JetpackEvent(evt.getPlayer(),
						FlightTypes.CROUCH);
				JetpackManager.getInstance().onJetpackEvent(event);
				evt.setCancelled(event.isCancelled());
			}
		}
	}

	@EventHandler
	public void onPlayerDamage(EntityDamageEvent evt) {
		if (evt.getEntity() instanceof Player) {
			if (evt.getCause() == DamageCause.FALL) {
				JetpackEvent event = new JetpackEvent(
						((Player) evt.getEntity()), FlightTypes.FALLING);
				JetpackManager.getInstance().onJetpackEvent(event);
				evt.setCancelled(event.isCancelled());
			}
			if (evt.getCause() == DamageCause.DROWNING) {
				JetpackEvent event = new JetpackEvent(
						((Player) evt.getEntity()), FlightTypes.DROWNING);
				JetpackManager.getInstance().onJetpackEvent(event);
				evt.setCancelled(event.isCancelled());
			}
		}
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent evt) {
		JetpackEvent event = new JetpackEvent(evt.getPlayer(),
				FlightTypes.INTERACT);
		JetpackManager.getInstance().onJetpackEvent(event);
		evt.setCancelled(event.isCancelled());
	}
	
	@EventHandler
	public void onPlayerAttacked(EntityDamageByEntityEvent evt) {
		if (evt.getEntity() instanceof Player) {
			if (evt.getDamager() instanceof Player) {
				JetpackEvent event = new JetpackEvent((Player) evt.getEntity(),
						FlightTypes.DAMAGED_BY_PLAYER);
				JetpackManager.getInstance().onJetpackEvent(event);
				evt.setCancelled(event.isCancelled());
			} else {
				JetpackEvent event = new JetpackEvent((Player) evt.getEntity(),
						FlightTypes.DAMAGED_BY_MOB);
				JetpackManager.getInstance().onJetpackEvent(event);
				evt.setCancelled(event.isCancelled());
			}
		}
	}
}
