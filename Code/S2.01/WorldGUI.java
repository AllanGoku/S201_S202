package application;

import java.util.ArrayList;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
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
	
	@Override
	public void start(Stage stage) {
		try {
			// Creation of the VBox containing the rows
			this.rows = new VBox();
			rows.setStyle("-fx-border-color: black;");
			HBox numX = new HBox();
			numX.setMaxWidth(520);
			numX.getChildren().add(new StackPane());
			((StackPane) numX.getChildren().get(0)).setPrefSize(25, 20);
			((StackPane) numX.getChildren().get(0)).setStyle("-fx-border-color: grey; -fx-border-with:1px;");
			for(int p=1; p<11;p++) {
				numX.getChildren().add(new StackPane());
				((StackPane) numX.getChildren().get(p)).setPrefSize(50, 20);
				((StackPane) numX.getChildren().get(p)).setMaxWidth(50);
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
				for (int i = 1;i < 11;i++){
					// 1 secteur -> 52 pixels
					((HBox) rows.getChildren().get(k)).getChildren().add(new VBox());
					((VBox) ((HBox) rows.getChildren().get(k)).getChildren().get(i)).setStyle("-fx-border-color: black; -fx-border-with:1px;");
					((VBox) ((HBox) rows.getChildren().get(k)).getChildren().get(i)).setMaxWidth(50);
					((VBox) ((HBox) rows.getChildren().get(k)).getChildren().get(i)).getChildren().add(new HBox());
					((VBox) ((HBox) rows.getChildren().get(k)).getChildren().get(i)).getChildren().add(new HBox());
					
					for(int u=0; u<2;u++) {
						
						((HBox) ((VBox) ((HBox) rows.getChildren().get(k)).getChildren().get(i)).getChildren().get(u)).getChildren().add(new StackPane());
						((HBox) ((VBox) ((HBox) rows.getChildren().get(k)).getChildren().get(i)).getChildren().get(u)).getChildren().add(new StackPane());
						// 1 case -> 25 pixels
						((StackPane) ((HBox) ((VBox) ((HBox) rows.getChildren().get(k)).getChildren().get(i)).getChildren().get(u)).getChildren().get(0)).setPrefSize(24, 24);
						((StackPane) ((HBox) ((VBox) ((HBox) rows.getChildren().get(k)).getChildren().get(i)).getChildren().get(u)).getChildren().get(0)).setStyle("-fx-border-color: grey; -fx-border-with:1px;");
						
						((StackPane) ((HBox) ((VBox) ((HBox) rows.getChildren().get(k)).getChildren().get(i)).getChildren().get(u)).getChildren().get(1)).setPrefSize(24, 24);
						((StackPane) ((HBox) ((VBox) ((HBox) rows.getChildren().get(k)).getChildren().get(i)).getChildren().get(u)).getChildren().get(1)).setStyle("-fx-border-color: grey; -fx-border-with:1px;");
					}
				}
			}
			
			// Numbers :
			// First is the number of the row       -> Sector
			// Second is the number of the column   -> Sector
			// Third is the number of the row       -> StackPane
			// Fourth is the number of the column   -> StackPane
			
			creation();
			addStats();
			Scene scene = new Scene(rows);
			rows.setAlignment(Pos.BASELINE_CENTER);
			stage.sizeToScene();
			stage.setTitle("Jeu");
			stage.setScene(scene);
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
		int size = lesSecteurs.length;
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
					((StackPane) ((HBox) ((VBox) ((HBox) rows.getChildren().get(i)).getChildren().get(j)).getChildren().get(0)).getChildren().get(0)).getChildren().add(new Text("E"));
					((StackPane) ((HBox) ((VBox) ((HBox) rows.getChildren().get(i)).getChildren().get(j)).getChildren().get(0)).getChildren().get(1)).getChildren().add(new Text("1"));
				}
				else if(s.haveMine()) {
					((StackPane) ((HBox) ((VBox) ((HBox) rows.getChildren().get(i)).getChildren().get(j)).getChildren().get(0)).getChildren().get(0)).getChildren().add(new Text("M"));
					((StackPane) ((HBox) ((VBox) ((HBox) rows.getChildren().get(i)).getChildren().get(j)).getChildren().get(0)).getChildren().get(1)).getChildren().add(new Text("1"));
				}
				if(s.haveRobot()) {
					((StackPane) ((HBox) ((VBox) ((HBox) rows.getChildren().get(i)).getChildren().get(j)).getChildren().get(1)).getChildren().get(0)).getChildren().add(new Text("R"));
					((StackPane) ((HBox) ((VBox) ((HBox) rows.getChildren().get(i)).getChildren().get(j)).getChildren().get(1)).getChildren().get(1)).getChildren().add(new Text("1"));
				}
			}
		}
	}
	
	public void addStats() {
		
		rows.getChildren().add(new HBox());
		
		// number of rounds 
		((HBox) rows.getChildren().get(11)).getChildren().add(new Text("\tTour "));
		((HBox) rows.getChildren().get(11)).getChildren().add(new Text("1"));
		
		// border top
		rows.getChildren().add(new HBox());
		((HBox) rows.getChildren().get(12)).setStyle("-fx-border-color: black; -fx-border-with:5px;");
		
		// display of the stats 
		for(int i=0;i<sizeMines;i++) {
			rows.getChildren().add(new HBox());
			((HBox) rows.getChildren().get(13+i)).getChildren().add(new Text("\tM"+i+" "));
		}
		for(int j=0;j<sizeEntrepots;j++) {
			rows.getChildren().add(new HBox());
			((HBox) rows.getChildren().get(13+sizeMines+j)).getChildren().add(new Text("\tE"+j+" "));
		}
		for(int k=0;k<sizeRobots;k++) {
			rows.getChildren().add(new HBox());
			((HBox) rows.getChildren().get(13+sizeMines+sizeEntrepots+k)).getChildren().add(new Text("\tR"+k+" "));
		}
		
		// border bottom
		rows.getChildren().add(new HBox());
		((HBox) rows.getChildren().get(13+sizeMines+sizeEntrepots+sizeRobots)).setStyle("-fx-border-color: black; -fx-border-with:5px;");
	}
	
	public void addTour() {
		
	}
	
	public void moveRobot(int beforeX, int beforeY, int afterX, int afterY) {
		beforeX+=1;
		beforeY+=1;
		afterX+=1;
		afterY+=1;
		((StackPane) ((HBox) ((VBox) ((HBox) rows.getChildren().get(beforeX)).getChildren().get(beforeY)).getChildren().get(1)).getChildren().get(0)).getChildren().remove(0);
		((StackPane) ((HBox) ((VBox) ((HBox) rows.getChildren().get(beforeX)).getChildren().get(beforeY)).getChildren().get(1)).getChildren().get(1)).getChildren().remove(0);
		((StackPane) ((HBox) ((VBox) ((HBox) rows.getChildren().get(afterX)).getChildren().get(afterY)).getChildren().get(1)).getChildren().get(0)).getChildren().add(new Text("R"));
		((StackPane) ((HBox) ((VBox) ((HBox) rows.getChildren().get(afterX)).getChildren().get(afterY)).getChildren().get(1)).getChildren().get(1)).getChildren().add(new Text("1"));
	}
	
	public static void main(String args[])
	{
		launch(args);
	}
}
