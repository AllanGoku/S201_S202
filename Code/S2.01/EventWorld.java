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
				int[] coord;
				try {
					coord = world.selected.seDeplacer("S");		
					world.move(world.selected.getNumero(), coord[2],coord[3]);
					world.moveRobot(coord[0], coord[1],coord[2],coord[3]);
				} catch (Exception e) {
					world.errorMove();
				}
			}
			else if(((KeyEvent) event).getCode() == KeyCode.LEFT) {
				int[] coord;
				try {
					coord = world.selected.seDeplacer("O");
					world.move(world.selected.getNumero(), coord[2],coord[3]);
					world.moveRobot(coord[0], coord[1],coord[2],coord[3]);
				} catch (Exception e) {
					world.errorMove();
				}
			}
			else if(((KeyEvent) event).getCode() == KeyCode.RIGHT) {
				int[] coord;
				try {
					coord = world.selected.seDeplacer("E");
					world.move(world.selected.getNumero(), coord[2],coord[3]);
					world.moveRobot(coord[0], coord[1],coord[2],coord[3]);
				} catch (Exception e) {
					world.errorMove();
				}
			}
			else if(((KeyEvent) event).getCode() == KeyCode.UP) {
				int[] coord;
				try {
					coord = world.selected.seDeplacer("N");
					world.move(world.selected.getNumero(), coord[2],coord[3]);
					world.moveRobot(coord[0], coord[1],coord[2],coord[3]);
				} catch (Exception e) {
					world.errorMove();
				}
			}
			else if(((KeyEvent) event).getCode() == KeyCode.SPACE) {
				world.changeRobot();
				world.changeDisplay();
			}
			else if(((KeyEvent) event).getCode() == KeyCode.ENTER) {
				if(world.selected.getSonSecteur().haveMine()) {
					try {
						world.contenuDebut(true);
						world.selected.extract_minerais();
						world.contenuFin(true);
						world.refreshCapacityRobot();
						world.changeRobot();
					} catch (ExceptionContenu e) {
						world.errorContenu(e.getMessage());
					}
					
				}
				if(world.selected.getSonSecteur().haveEntrepot()) {
					try {
						world.contenuDebut(false);
						world.selected.deposer();
						world.contenuFin(false);
						world.refreshCapacityRobot();
						world.changeRobot();
						if(world.verifWin()) {
							Alert alert = new Alert(AlertType.INFORMATION);
							alert.setTitle("Victoire");
							alert.setHeaderText("Vous avez gagné la partie en "+world.num+" tours !");
							Optional<ButtonType> result = alert.showAndWait();
							if (result.get() == ButtonType.OK)
								Platform.exit();
						}
					} catch (ExceptionContenu e) {
						world.errorContenu(e.getMessage());
					}
				}
				
			}
		}
	}
}

