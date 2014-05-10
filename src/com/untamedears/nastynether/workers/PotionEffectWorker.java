package com.untamedears.nastynether.workers;

import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

import com.untamedears.nastynether.Configuration;
import com.untamedears.nastynether.NastyNether;
import com.untamedears.nastynether.Configuration.WorldConfig;

public class PotionEffectWorker implements Runnable {
	private NastyNether plugin;

	public PotionEffectWorker(NastyNether plugin) {
		super();
		this.plugin = plugin;
	}

	@Override
	public void run() {
		
		Configuration config = plugin.getConfiguration();
		for (String worldName : config.getWorldNames()) {
			WorldConfig worldConfig = config.getWorldConfig(worldName);
			World world = plugin.getServer().getWorld(worldName);
			for (Player player : world.getPlayers()) {
				for (PotionEffect effect : worldConfig.getPotionEffects()) {
					effect.apply(player);
				}
			}
		}
	}
}
