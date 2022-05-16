package application;

import java.util.Optional;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
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
			else if(((KeyEvent) event).getCode() == KeyCode.LEFT) {
				int[] coord = world.selected.seDeplacer("O");
				if(coord == null) {
					System.out.println("Mouvement impossible");
				}else {		
					world.moveRobot(coord[0], coord[1],
							coord[2],coord[3]);
				}
			}
			else if(((KeyEvent) event).getCode() == KeyCode.RIGHT) {
				int[] coord = world.selected.seDeplacer("E");
				if(coord == null) {
					System.out.println("Mouvement impossible");
				}else {		
					world.moveRobot(coord[0], coord[1],
							coord[2],coord[3]);
				}
			}
			else if(((KeyEvent) event).getCode() == KeyCode.UP) {
				int[] coord = world.selected.seDeplacer("N");
				if(coord == null) {
					System.out.println("Mouvement impossible");
				}else {		
					world.moveRobot(coord[0], coord[1],
							coord[2],coord[3]);
				}
			}
			else if(((KeyEvent) event).getCode() == KeyCode.SPACE) {
				world.changeRobot();
			}
			else if(((KeyEvent) event).getCode() == KeyCode.ENTER) {
				if(world.selected.getSonSecteur().haveMine()) {
					if(world.selected.getCapacite()==world.selected.getCapaciteStockageMax()) {
						System.out.println("Le robot est plein!");
					} else if(world.selected.getSonSecteur().getMine().getCapacite()==0) {
						System.out.println("La mine est vide!");
					} else {
						world.selected.extract_minerais();
						world.refreshCapacityRobot();
						System.out.println("Minage...");
						world.changeRobot();
					}
				}
				if(world.selected.getSonSecteur().haveEntrepot()) {
					if(world.selected.getCapacite()==0) {
						System.out.println("Le robot est vide!");
					} else {
					world.selected.deposer();
					if(world.verifWin()) {
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("Victoire");
						alert.setHeaderText("Vous avez gagné la partie en "+world.num+" tours !");
						Optional<ButtonType> result = alert.showAndWait();
						if (result.get() == ButtonType.OK)
							Platform.exit();
					}
					world.num+=1;
					world.refreshCapacityRobot();
					System.out.println("Déposage...");
					world.changeRobot();
					}
				}
				
			}
		}
	}
}

