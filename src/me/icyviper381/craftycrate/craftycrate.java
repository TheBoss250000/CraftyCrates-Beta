package me.icyviper381.craftycrate;
 
import java.util.Map.Entry;
import java.util.Random;
 
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.plugin.java.JavaPlugin;
 
public class craftycrate extends JavaPlugin implements Listener {
    
    private Random random;
    private Material[] items;
	private boolean addRecipe;
    
    @Override
    public void onEnable() {
        addRecipe = Bukkit.getServer().addRecipe(
                new ShapelessRecipe(new ItemStack(Material.DIRT))
                .addIngredient(Material.TRIPWIRE_HOOK)
                .addIngredient(Material.CHEST)
        );
        
        getConfig().options().copyDefaults(true);
        saveConfig();
        
        this.random = new Random();
        this.items = new Material[100];
        
        int index = 0;
        for (Entry<String, Object> entry : getConfig().getConfigurationSection("items").getValues(false).entrySet()) {
            for (int i = 0; i < Integer.valueOf(entry.getValue().toString()); i++) {
                items[index++] = Material.valueOf(entry.getKey().toUpperCase());
            }
        }
        
        Bukkit.getServer().getPluginManager().registerEvents(this, this);
    }
    
    @EventHandler
    public void onItemCraft(CraftItemEvent e) {
        // Use the ItemMeta of the dirt.
        if (e.getRecipe().getResult().getType() == Material.DIRT) {
            e.getInventory().setResult(new ItemStack(items[random.nextInt(items.length)]));
        }
    }
}