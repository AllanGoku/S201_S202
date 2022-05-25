package application;

import java.util.Optional;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

@SuppressWarnings("rawtypes")
public class EventWorld implements EventHandler{

	// Variables de classes utiles pour les changements dans WorldGUI
	private WorldGUI world;
	private Scene game;
	private HBox lines;
	private VBox infos;
	private VBox startPage;
	
	// Constructeur
	public EventWorld(WorldGUI w, Scene s, HBox h, VBox v, VBox start) {
		world = w;
		game = s;
		lines = h;
		infos = v;
		startPage = start;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void handle(Event event) {
		// Gestion des events clavier
		if(event instanceof KeyEvent) {
			// Gestion des déplacements
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
			// Gestion du changement de robot
			else if(((KeyEvent) event).getCode() == KeyCode.SPACE) {
				world.changeRobot();
				world.changeDisplay();
			}
			// Gestion de la touche d'action
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
				else if(world.selected.getSonSecteur().haveEntrepot()) {
					try {
						world.contenuDebut(false);
						world.selected.deposer();
						world.contenuFin(false);
						world.refreshCapacityRobot();
						world.changeRobot();
						// Vérification de l'état de la partie au dépôt des ressources
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
		// Gestion des boutons des menus précédents le jeu
		} else if(event instanceof MouseEvent && event.getEventType() == MouseEvent.MOUSE_CLICKED && event.getSource().toString().contains("Jouer")) {
			game.setRoot(lines);
			game.addEventFilter(KeyEvent.KEY_PRESSED, this);
		} else if(event instanceof MouseEvent && event.getEventType() == MouseEvent.MOUSE_CLICKED && event.getSource().toString().contains("Comment jouer ?")) {
			game.setRoot(infos);
		} else if(event instanceof MouseEvent && event.getEventType() == MouseEvent.MOUSE_CLICKED && event.getSource().toString().contains("< Retour")) {
			game.setRoot(startPage);
		}
	}
}
