/*
 * Bukkit plugin which moves the mobs closer to the players.
 * Copyright (C) 2016 Jakub "Co0sh" Sapalski
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package pl.betoncraft.hordes;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.LivingEntity;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Despawns mobs in unwanted places.
 * 
 * @author Jakub Sapalski
 */
public class Despawner extends BukkitRunnable {
	
	private Hordes plugin;

	/**
	 * Starts the despawner.
	 * 
	 * @param plugin
	 *            instance of the plugin
	 */
	public Despawner(Hordes plugin) {
		this.plugin = plugin;
		int interval = plugin.getConfig().getInt(
				"global-settings.despawn-interval", 10);
		runTaskTimer(plugin, interval* 20L, interval* 20L);
	}

	@Override
	public void run() {
		for (World world : Bukkit.getWorlds()) {
			WorldSettings settings = plugin.getWorlds().get(world.getName());
			if (settings == null) continue;
			// copy the array so it does not get modified while iterating
			ArrayList<LivingEntity> entities = new ArrayList<>(world.getLivingEntities());
			for (LivingEntity entity : entities) {
				if (!settings.shouldExist(entity)){
					entity.remove();
				}
			}
		}
	}

}
