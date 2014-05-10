package com.untamedears.nastynether;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Configuration {

	private NastyNether plugin;
	private Map<String, WorldConfig> worldConfigs;
	private int explosionTickInterval;
	private int effectTickInterval;

	public Configuration(NastyNether nastyNether) {
		this.plugin = nastyNether;
		this.worldConfigs = new HashMap<String, WorldConfig>();
		
		nastyNether.saveDefaultConfig();
		ConfigurationSection root = nastyNether.getConfig().getRoot();
		
		explosionTickInterval = root.getInt("explosion_tick_interval", 40);
		effectTickInterval = root.getInt("effect_tick_interval", 20);
		
		ConfigurationSection worlds = root.getConfigurationSection("worlds");
		for (String worldName : worlds.getKeys(false)) {
			WorldConfig worldConfig = new WorldConfig(worlds.getConfigurationSection(worldName));
			worldConfigs.put(worldName, worldConfig);
		}
	}
	
	public int getExplosionTickInterval() {
		return explosionTickInterval;
	}
	
	public int getEffectTickInterval() {
		return effectTickInterval;
	}

	public Set<String> getWorldNames() {
		return worldConfigs.keySet();
	}
	
	public WorldConfig getWorldConfig(String name) {
		return worldConfigs.get(name);
	}
	
	public class WorldConfig {
		private List<PotionEffect> potionEffects;
		private double explosionMaxDistance;
		private double explosionInterval;
		private double explosionMinPower;
		private double explosionMaxPower;
		private double explosionFireChance;
		
		public List<PotionEffect> getPotionEffects() {
			return potionEffects;
		}

		public double getExplosionMinPower() {
			return explosionMinPower;
		}

		public double getExplosionMaxPower() {
			return explosionMaxPower;
		}

		
		public WorldConfig(ConfigurationSection config) {
			this.potionEffects = getPotionEffects(config.getConfigurationSection("effects"));
			
			explosionMaxDistance = config.getDouble("explosion_max_distance", 16);
			explosionInterval = config.getDouble("explosion_interval", 180);
			explosionMinPower = config.getDouble("explosion_min_power", 3);
			explosionMaxPower = config.getDouble("explosion_max_power", 6);
			explosionFireChance = config.getDouble("explosion_fire_chance", 5) / 100;
		}

		private List<PotionEffect> getPotionEffects(
				ConfigurationSection configurationSection) {
			List<PotionEffect> potionEffects = new ArrayList<PotionEffect>();
			if(configurationSection!=null)
			{
				Iterator<String> names=configurationSection.getKeys(false).iterator();
				while (names.hasNext())
				{
					String name=names.next();
					ConfigurationSection configEffect=configurationSection.getConfigurationSection(name);
					String type=configEffect.getString("type");
					if (type!=null)
					{
						PotionEffectType effect = PotionEffectType.getByName(type);
						if (effect != null) {
							int duration=configEffect.getInt("duration",200);
							int amplifier=configEffect.getInt("amplifier",0);
							potionEffects.add(new PotionEffect(effect, duration, amplifier));
						}
					}
				}
			}
			return potionEffects;
		}
		
		public double getExplosionChance(double seconds) {
			return seconds / explosionInterval;
		}

		public double getExplosionMaxDistance() {
			return explosionMaxDistance;
		}

		public double getExplosionFireChance() {
			return explosionFireChance;
		}
	}

}
