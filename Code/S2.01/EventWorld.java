package application;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class EventWorld implements EventHandler{

	private WorldGUI world;
	
	public EventWorld(WorldGUI w) {
		world = w;
	}
	
	@Override
	public void handle(Event event) {
		if(event instanceof KeyEvent) {
			if(((KeyEvent) event).getCode() == KeyCode.DOWN) {
				int[] coord = world.selected.seDeplacer("S");
				if(coord == null) {
					System.out.println("Mouvement impossible");
				}else {		
					world.moveRobot(coord[0], coord[1],
							coord[2],coord[3]);
				}
			}
			if(((KeyEvent) event).getCode() == KeyCode.LEFT) {
				int[] coord = world.selected.seDeplacer("O");
				if(coord == null) {
					System.out.println("Mouvement impossible");
				}else {		
					world.moveRobot(coord[0], coord[1],
							coord[2],coord[3]);
				}
			}
			if(((KeyEvent) event).getCode() == KeyCode.RIGHT) {
				int[] coord = world.selected.seDeplacer("E");
				if(coord == null) {
					System.out.println("Mouvement impossible");
				}else {		
					world.moveRobot(coord[0], coord[1],
							coord[2],coord[3]);
				}
			}
			if(((KeyEvent) event).getCode() == KeyCode.UP) {
				int[] coord = world.selected.seDeplacer("N");
				if(coord == null) {
					System.out.println("Mouvement impossible");
				}else {		
					world.moveRobot(coord[0], coord[1],
							coord[2],coord[3]);
				}
			}
			if(((KeyEvent) event).getCode() == KeyCode.SPACE) {
				world.changeRobot();
			}
		}
	}
}

