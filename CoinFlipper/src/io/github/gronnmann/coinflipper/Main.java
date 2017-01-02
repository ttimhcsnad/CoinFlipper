package me.gronnmann.coinflipper;

import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import me.gronnmann.coinflipper.bets.BettingTimer;
import me.gronnmann.coinflipper.stats.StatsManager;
import net.milkbowl.vault.economy.Economy;


public class Main extends JavaPlugin{
	
	private static Economy economy;
	
	
	public void onEnable(){
		
		if (!enableEconomy()){Bukkit.getPluginManager().disablePlugin(this);}
		
		ConfigManager.getManager().setup(this);
		GUI.getInstance().setup(this);
		StatsManager.getManager().load();
		
		this.getCommand("coinflipper").setExecutor(new CommandsManager());
		
		
		BettingTimer task = new BettingTimer();
		task.runTaskTimerAsynchronously(this, 0, 60*20);
		
		
		Bukkit.getPluginManager().registerEvents(GUI.getInstance(), this);
		Bukkit.getPluginManager().registerEvents(StatsManager.getManager(), this);
	}
	
	public void onDisable(){
		StatsManager.getManager().save();
	}
	
	public boolean enableEconomy(){
		RegisteredServiceProvider<Economy> rsp = this.getServer().getServicesManager().getRegistration(Economy.class);
		if (rsp == null){
			return false;
		}
		economy = rsp.getProvider();
		return economy != null;
	}
	
	
	public static Economy getEcomony(){
		return economy;
	}
}