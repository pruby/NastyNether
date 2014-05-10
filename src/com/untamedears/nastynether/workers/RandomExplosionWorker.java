package com.untamedears.nastynether.workers;

import java.util.Random;

import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.entity.Player;

import com.untamedears.nastynether.Configuration;
import com.untamedears.nastynether.Configuration.WorldConfig;
import com.untamedears.nastynether.NastyNether;

public class RandomExplosionWorker implements Runnable {

	private NastyNether plugin;
	private Random random;
	
	public RandomExplosionWorker(NastyNether nastyNether) {
		this.plugin = nastyNether;
		this.random = new Random();
	}

	@Override
	public void run() {
		Configuration config = plugin.getConfiguration();
		for (String worldName : config.getWorldNames()) {
			WorldConfig worldConfig = config.getWorldConfig(worldName);
			World world = plugin.getServer().getWorld(worldName);
			for (Player player : world.getPlayers()) {
				// Skip creative players
				if (player.getGameMode().equals(GameMode.CREATIVE))
					continue;
				
				if (random.nextDouble() < worldConfig.getExplosionChance(config.getExplosionTickInterval() / 20.0)) {
					// Boom now please
					double r = random.nextDouble();
					double distance = (1.0 - (r * r)) * worldConfig.getExplosionMaxDistance();
					double radialAngle = random.nextDouble() * 2 * Math.PI;
					double verticalAngle = random.nextDouble() * 0.5 * Math.PI;
					
					double dy = Math.sin(verticalAngle) * distance;
					double dx = Math.sin(radialAngle) * Math.cos(verticalAngle) * distance;
					double dz = Math.cos(radialAngle) * Math.cos(verticalAngle) * distance;
					
					float explosionPower = (float) (worldConfig.getExplosionMinPower() + (random.nextDouble() * (worldConfig.getExplosionMaxPower() - worldConfig.getExplosionMinPower())));
					boolean isFiery = random.nextDouble() < worldConfig.getExplosionFireChance();

					plugin.getLogger().info("Explosion at - " + dx + "," + dy + "," + dz + ", power " + explosionPower + ", " + (isFiery ? "fiery" : "normal"));

					double x = player.getLocation().getX() + dx;
					double y = player.getLocation().getY() + dy;
					double z = player.getLocation().getZ() + dz;
					
					world.createExplosion(x, y, z, explosionPower, isFiery);
				}
			}
		}
	}

}
