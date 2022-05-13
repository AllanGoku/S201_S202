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
				System.out.println(world.selected.seDeplacer("S")[0]);
				System.out.println(world.selected.seDeplacer("S")[1]);
				System.out.println(world.selected.seDeplacer("S")[2]);
				System.out.println(world.selected.seDeplacer("S")[3]);		
				world.moveRobot(world.selected.seDeplacer("S")[0], world.selected.seDeplacer("S")[1],
						world.selected.seDeplacer("S")[2], world.selected.seDeplacer("S")[3]);
			}
		}
	}

}
