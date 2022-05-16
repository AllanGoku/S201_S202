package application;

import java.util.ArrayList;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class WorldGUI extends Application {
	private VBox rows;
	private Monde monde;
	
	private ArrayList<Mine> mines;
	private ArrayList<Entrepot> entrepots;
	private ArrayList<Robot> robots;

	private int sizeMines;
	private int sizeEntrepots;
	private int sizeRobots;
	
	private VBox stats;
	
	public int num;
	
	// selected robot
	public Robot selected;
	
	@Override
	public void start(Stage stage) {
		try {
			// Creation of the VBox containing the rows
			this.rows = new VBox();
			rows.setStyle("-fx-border-color: black;");
			HBox numX = new HBox();
			numX.setMaxWidth(525);
			numX.getChildren().add(new StackPane());
			((StackPane) numX.getChildren().get(0)).setPrefSize(25, 20);
			((StackPane) numX.getChildren().get(0)).setMaxWidth(50);
			((StackPane) numX.getChildren().get(0)).setStyle("-fx-border-color: grey; -fx-border-with:1px;");
			for(int p=1; p<11;p++) {
				numX.getChildren().add(new StackPane());
				((StackPane) numX.getChildren().get(p)).setPrefSize(50, 20);
				((StackPane) numX.getChildren().get(p)).setMaxWidth(50);
				((StackPane) numX.getChildren().get(p)).setMaxHeight(20);
				((StackPane) numX.getChildren().get(p)).setStyle("-fx-border-color: grey; -fx-border-with:1px;");
				((StackPane) numX.getChildren().get(p)).getChildren().add(new Text((""+(p-1))));
			}
			rows.getChildren().add(numX);
			// Rows creation (loop of 10 because world is 10x10 sectors)
			for(int k=1; k<11 ; k++) {
				rows.getChildren().add(new HBox());
				((HBox) rows.getChildren().get(k)).getChildren().add(new StackPane());
				((HBox) rows.getChildren().get(k)).setMaxWidth(525);
				((StackPane) ((HBox) rows.getChildren().get(k)).getChildren().get(0)).setPrefSize(25, 50);
				((StackPane) ((HBox) rows.getChildren().get(k)).getChildren().get(0)).setStyle("-fx-border-color: grey; -fx-border-with:1px;");
				((StackPane) ((HBox) rows.getChildren().get(k)).getChildren().get(0)).getChildren().add(new Text((""+(k-1))));
				((StackPane) ((HBox) rows.getChildren().get(k)).getChildren().get(0)).setMaxWidth(25);
				((StackPane) ((HBox) rows.getChildren().get(k)).getChildren().get(0)).setMaxHeight(50);
				for (int i = 1;i < 11;i++){
					// 1 secteur -> 50 pixels
					((HBox) rows.getChildren().get(k)).getChildren().add(new VBox());
					((VBox) ((HBox) rows.getChildren().get(k)).getChildren().get(i)).setStyle("-fx-border-color: black; -fx-border-with:1px;");
					((VBox) ((HBox) rows.getChildren().get(k)).getChildren().get(i)).setMaxWidth(50);
					((VBox) ((HBox) rows.getChildren().get(k)).getChildren().get(i)).setMaxHeight(50);
					((VBox) ((HBox) rows.getChildren().get(k)).getChildren().get(i)).getChildren().add(new HBox());
					((VBox) ((HBox) rows.getChildren().get(k)).getChildren().get(i)).getChildren().add(new HBox());
					
					for(int u=0; u<2;u++) {
						
						((HBox) ((VBox) ((HBox) rows.getChildren().get(k)).getChildren().get(i)).getChildren().get(u)).getChildren().add(new StackPane());
						((HBox) ((VBox) ((HBox) rows.getChildren().get(k)).getChildren().get(i)).getChildren().get(u)).getChildren().add(new StackPane());
						// 1 case -> 25 pixels
						((StackPane) ((HBox) ((VBox) ((HBox) rows.getChildren().get(k)).getChildren().get(i)).getChildren().get(u)).getChildren().get(0)).setPrefSize(25, 25);
						((StackPane) ((HBox) ((VBox) ((HBox) rows.getChildren().get(k)).getChildren().get(i)).getChildren().get(u)).getChildren().get(0)).setStyle("-fx-border-color: grey; -fx-border-with:1px;");
						
						((StackPane) ((HBox) ((VBox) ((HBox) rows.getChildren().get(k)).getChildren().get(i)).getChildren().get(u)).getChildren().get(1)).setPrefSize(25, 25);
						((StackPane) ((HBox) ((VBox) ((HBox) rows.getChildren().get(k)).getChildren().get(i)).getChildren().get(u)).getChildren().get(1)).setStyle("-fx-border-color: grey; -fx-border-with:1px;");
					}
				}
			}
			
			// Numbers :
			// First is the number of the row       -> Sector
			// Second is the number of the column   -> Sector
			// Third is the number of the row       -> StackPane
			// Fourth is the number of the column   -> StackPane

			num=1;
			creation();
			addStats();
			affichageHelp();
			rows.setAlignment(Pos.CENTER);
			((HBox) rows.getChildren().get(11)).setAlignment(Pos.CENTER);
			Scene scene = new Scene(rows);
			EventWorld e = new EventWorld(this);
			selected = monde.getRobots().get(0);
			scene.addEventFilter(KeyEvent.KEY_PRESSED, e);
			stage.setScene(scene);
			stage.sizeToScene();
			stage.setTitle("Jeu");
			stage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void creation() {
		monde = new Monde();
		monde.createWorld();

		mines = monde.getMines();
		entrepots = monde.getEntrepots();
		robots = monde.getRobots();
		
		sizeMines = mines.size(); 
		sizeEntrepots = entrepots.size();
		sizeRobots = robots.size();
		
		Secteur[][] lesSecteurs = monde.getSecteurs(); 
		for(int i=1;i<11;i++) {
			Secteur[] row = lesSecteurs[i-1];
			for(int j=1;j<11;j++) {
				Secteur s = row[j-1];
				if(s.getEau()) {
					((StackPane) ((HBox) ((VBox) ((HBox) rows.getChildren().get(i)).getChildren().get(j)).getChildren().get(0)).getChildren().get(0)).getChildren().add(new Text("X"));
					((StackPane) ((HBox) ((VBox) ((HBox) rows.getChildren().get(i)).getChildren().get(j)).getChildren().get(0)).getChildren().get(1)).getChildren().add(new Text("X"));
					((StackPane) ((HBox) ((VBox) ((HBox) rows.getChildren().get(i)).getChildren().get(j)).getChildren().get(1)).getChildren().get(0)).getChildren().add(new Text("X"));
					((StackPane) ((HBox) ((VBox) ((HBox) rows.getChildren().get(i)).getChildren().get(j)).getChildren().get(1)).getChildren().get(1)).getChildren().add(new Text("X"));
				}
				else if(s.haveEntrepot()) {
					Entrepot ent = s.getEntrepot();
					((StackPane) ((HBox) ((VBox) ((HBox) rows.getChildren().get(i)).getChildren().get(j)).getChildren().get(0)).getChildren().get(0)).getChildren().add(new Text("E"));
					((StackPane) ((HBox) ((VBox) ((HBox) rows.getChildren().get(i)).getChildren().get(j)).getChildren().get(0)).getChildren().get(1)).getChildren().add(new Text(""+ent.getNumero()));
				}
				else if(s.haveMine()) {
					Mine mi = s.getMine();
					((StackPane) ((HBox) ((VBox) ((HBox) rows.getChildren().get(i)).getChildren().get(j)).getChildren().get(0)).getChildren().get(0)).getChildren().add(new Text("M"));
					((StackPane) ((HBox) ((VBox) ((HBox) rows.getChildren().get(i)).getChildren().get(j)).getChildren().get(0)).getChildren().get(1)).getChildren().add(new Text(""+mi.getNumero()));
				}
				if(s.haveRobot()) {
					Robot ro = s.getRobot();
					((StackPane) ((HBox) ((VBox) ((HBox) rows.getChildren().get(i)).getChildren().get(j)).getChildren().get(1)).getChildren().get(0)).getChildren().add(new Text("R"));
					((StackPane) ((HBox) ((VBox) ((HBox) rows.getChildren().get(i)).getChildren().get(j)).getChildren().get(1)).getChildren().get(1)).getChildren().add(new Text(""+ro.getNumero()));
				}
			}
		}
	}
	
	public void addStats() {
		
		// round display
		rows.getChildren().add(new HBox());		
		((HBox) rows.getChildren().get(11)).getChildren().add(new Text("Tour "));
		((HBox) rows.getChildren().get(11)).getChildren().add(new Text(""+num));
		((Text) ((HBox) rows.getChildren().get(11)).getChildren().get(0)).setFont(Font.font("Helvetica", FontWeight.BOLD, 15));
		((Text) ((HBox) rows.getChildren().get(11)).getChildren().get(1)).setFont(Font.font("Helvetica", FontWeight.BOLD, 15));
		((HBox) rows.getChildren().get(11)).setStyle("-fx-border-color: black; -fx-border-with:5px;");
		((HBox) rows.getChildren().get(11)).setMaxWidth(525);
		
		// vbox for the stats
		stats = new VBox();
		// display of the stats 
		for(int i=0;i<sizeMines;i++) {
			stats.getChildren().add(new HBox());
			((HBox) stats.getChildren().get(i)).getChildren().add(new Text("M"+(i+1)+" "));
			((HBox) stats.getChildren().get(i)).getChildren().add(new Text("\t|\t" + mines.get(i).getSonSecteur().getX()));
			((HBox) stats.getChildren().get(i)).getChildren().add(new Text(" ; " + mines.get(i).getSonSecteur().getY()));	
			((HBox) stats.getChildren().get(i)).getChildren().add(new Text("\t|\t " + mines.get(i).getTypeMinerai()));		
			((HBox) stats.getChildren().get(i)).getChildren().add(new Text("\t|\t " + mines.get(i).getCapacite()));				
			((HBox) stats.getChildren().get(i)).getChildren().add(new Text("/" + mines.get(i).getCapaciteStockageMax()));		
			((HBox) stats.getChildren().get(i)).setAlignment(Pos.CENTER);
		}
		for(int j=0;j<sizeEntrepots;j++) {
			stats.getChildren().add(new HBox());
			((HBox) stats.getChildren().get(sizeMines + j)).getChildren().add(new Text("E"+(j+1)+" "));
			((HBox) stats.getChildren().get(sizeMines +j)).getChildren().add(new Text("\t|\t" + entrepots.get(j).getSonSecteur().getX()));
			((HBox) stats.getChildren().get(sizeMines +j)).getChildren().add(new Text(" ; " + entrepots.get(j).getSonSecteur().getY()));	
			((HBox) stats.getChildren().get(sizeMines +j)).getChildren().add(new Text("\t|\t " + entrepots.get(j).getTypeMinerai()));		
			((HBox) stats.getChildren().get(sizeMines +j)).getChildren().add(new Text("\t|\t " + entrepots.get(j).getCapacite()));			
			((HBox) stats.getChildren().get(sizeMines + j)).setAlignment(Pos.CENTER);
		}
		for(int k=0;k<sizeRobots;k++) {
			stats.getChildren().add(new HBox());
			((HBox) stats.getChildren().get(sizeMines + sizeEntrepots + k)).getChildren().add(new Text("R"+(k+1)+" "));
			((HBox) stats.getChildren().get(sizeMines + sizeEntrepots + k)).getChildren().add(new Text("\t|\t" + robots.get(k).getSonSecteur().getX()));
			((HBox) stats.getChildren().get(sizeMines + sizeEntrepots + k)).getChildren().add(new Text(" ; " + robots.get(k).getSonSecteur().getY()));	
			((HBox) stats.getChildren().get(sizeMines + sizeEntrepots + k)).getChildren().add(new Text("\t|\t " + robots.get(k).getTypeMinerai()));		
			((HBox) stats.getChildren().get(sizeMines + sizeEntrepots + k)).getChildren().add(new Text("\t|\t " + robots.get(k).getCapacite()));				
			((HBox) stats.getChildren().get(sizeMines + sizeEntrepots + k)).getChildren().add(new Text("/" + robots.get(k).getCapaciteStockageMax()));	
			((HBox) stats.getChildren().get(sizeMines + sizeEntrepots + k)).setAlignment(Pos.CENTER);
		}
		
		stats.setStyle("-fx-border-color: black; -fx-border-with:5px;");
		stats.setMaxWidth(525);
		rows.getChildren().add(stats);
	}
	
	public void addTour() {
		((HBox) rows.getChildren().get(11)).getChildren().remove(1);
		((HBox) rows.getChildren().get(11)).getChildren().add(new Text(""+num));
		((Text) ((HBox) rows.getChildren().get(11)).getChildren().get(1)).setFont(Font.font("Helvetica", FontWeight.BOLD, 15));
	}
	
	public void changeRobot() {
		if(this.selected.getNumero()==this.robots.size()) {
			this.selected = robots.get(0);
		}else {
			this.selected = robots.get(this.selected.getNumero());
		}
	}
	
	public void moveRobot(int beforeX, int beforeY, int afterX, int afterY) {
		beforeX+=1;
		beforeY+=1;
		afterX+=1;
		afterY+=1;	
		addTour();		
		((StackPane) ((HBox) ((VBox) ((HBox) rows.getChildren().get(beforeX)).getChildren().get(beforeY)).getChildren().get(1)).getChildren().get(0)).getChildren().remove(0);
		((StackPane) ((HBox) ((VBox) ((HBox) rows.getChildren().get(beforeX)).getChildren().get(beforeY)).getChildren().get(1)).getChildren().get(1)).getChildren().remove(0);
		((StackPane) ((HBox) ((VBox) ((HBox) rows.getChildren().get(afterX)).getChildren().get(afterY)).getChildren().get(1)).getChildren().get(0)).getChildren().add(new Text("R"));
		((StackPane) ((HBox) ((VBox) ((HBox) rows.getChildren().get(afterX)).getChildren().get(afterY)).getChildren().get(1)).getChildren().get(1)).getChildren().add(new Text(""+this.selected.getNumero()));
		((HBox) stats.getChildren().get(sizeMines + sizeEntrepots + this.selected.getNumero()-1)).getChildren().set(1, new Text("\t|\t" + afterX));
		((HBox) stats.getChildren().get(sizeMines + sizeEntrepots + this.selected.getNumero()-1)).getChildren().set(2, new Text(" ; " + afterY));	
	}
	
	public void refreshCapacityRobot() {	
		if(this.selected.getSonSecteur().haveMine()) {
			int numMine = this.selected.getSonSecteur().getMine().getNumero();
			((HBox) stats.getChildren().get(numMine-1)).getChildren().set(4,new Text("\t|\t " + mines.get(numMine-1).getCapacite()));		
		}
		else if(this.selected.getSonSecteur().haveEntrepot()) {
			int numEnt = this.selected.getSonSecteur().getEntrepot().getNumero();
			((HBox) stats.getChildren().get(sizeMines +numEnt-1)).getChildren().set(4,new Text("\t|\t " + entrepots.get(numEnt-1).getCapacite()));	
		}
		((HBox) stats.getChildren().get(sizeMines + sizeEntrepots + this.selected.getNumero()-1)).getChildren().set(4, new Text("\t|\t " + this.selected.getCapacite()));
		((HBox) stats.getChildren().get(sizeMines + sizeEntrepots + this.selected.getNumero()-1)).getChildren().set(5, new Text("/" + this.selected.getCapaciteStockageMax()));	
	}
	
	public void changeText(Ressource r) {
		r.getSonSecteur().getCoord();
		if(r instanceof Robot) {
			Robot ro = ((Robot) r);
			r.getNumero();
			
		}
		if(r instanceof Entrepot) {
			Entrepot en = ((Entrepot) r);
		}
		if(r instanceof Mine) {
			Mine mi = ((Mine) r);
		}
	}
	
	public void affichageHelp() {
		rows.getChildren().add(new VBox());	
		
		((VBox) rows.getChildren().get(13)).getChildren().add(new Text("Aide"));
		((Text) ((VBox) rows.getChildren().get(13)).getChildren().get(0)).setUnderline(true);
		((Text) ((VBox) rows.getChildren().get(13)).getChildren().get(0)).setFont(Font.font("Helvetica", FontWeight.BOLD, 15));

		rows.getChildren().add(new HBox());	
		((VBox) rows.getChildren().get(13)).setAlignment(Pos.CENTER);
		((VBox) rows.getChildren().get(13)).getChildren().add(new Text("Flèches pour se déplacer"));
		((VBox) rows.getChildren().get(13)).getChildren().add(new Text("Espace pour changer de robot"));
		((VBox) rows.getChildren().get(13)).getChildren().add(new Text("Entrée pour miner/déposer"));
	}
	
	public static void main(String args[])
	{
		launch(args);
	}
}
