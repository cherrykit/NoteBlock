package me.cherrykit;

import org.bukkit.Material;
import org.bukkit.Note;
import org.bukkit.block.NoteBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class NoteBlocks extends JavaPlugin implements Listener {

	//Saves the note that is copied over
	Note note;
	
	//Registers the listener
	@Override
	public void onEnable() {
		this.getServer().getPluginManager().registerEvents(this, this);
	}
	
	@Override
	public void onDisable() {}
	
	//Registers left and right clicks
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e){
		Player p = e.getPlayer();
		
		//If player interacts with note block with stick in hand
		if (e.getClickedBlock().getType() == Material.NOTE_BLOCK 
				&& p.getInventory().getItemInMainHand().getType() == Material.STICK) {
			
			//Cancels default action and gets the state of the note block
			e.setCancelled(true);
			NoteBlock b = (NoteBlock) e.getClickedBlock().getState();
			
			//Saves the pitch if block was right clicked
			if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
				note = b.getNote();
			}
			
			//Changes note block to the saved pitch if left clicked
			if (e.getAction() == Action.LEFT_CLICK_BLOCK) {
				try {
					b.setNote(note);
					b.update(true);
				} catch (Exception ex) {
					System.out.println("No saved config: " + ex);
				}
			}
		}
		
	}
	
}
