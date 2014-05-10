package com.untamedears.nastynether;

import org.bukkit.World;
import org.bukkit.entity.*;
import org.bukkit.plugin.java.JavaPlugin;

import com.untamedears.nastynether.workers.PotionEffectWorker;
import com.untamedears.nastynether.workers.RandomExplosionWorker;

import java.util.*;

/**
 * This is the main class for the MusterCull Bukkit plug-in.
 * @author Celdecea
 *
 */
public class NastyNether extends JavaPlugin {
	
	private Configuration config;

	/**
	 * Called when the plug-in is enabled by Bukkit.
	 */
	public void onEnable() {
		this.config = new Configuration(this);

		getServer().getScheduler().scheduleSyncRepeatingTask(this, new PotionEffectWorker(this), config.getEffectTickInterval(), config.getEffectTickInterval());
		getServer().getScheduler().scheduleSyncRepeatingTask(this, new RandomExplosionWorker(this), config.getExplosionTickInterval(), config.getExplosionTickInterval());
    }
	
	/**
	 * Called when the plug-in is disabled by Bukkit.
	 */
    public void onDisable() { 
    	
    }
    
    public Configuration getConfiguration() {
    	return config;
    }
}
