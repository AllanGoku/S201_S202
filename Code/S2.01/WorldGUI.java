package application;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class WorldGUI extends Application {
	
	@Override
	public void start(Stage stage) {
		try {
			// Creation of the VBox containing the rows
			VBox rows = new VBox();
			rows.setStyle("-fx-border-color: black;");
			
			// Rows creation (loop of 10 because world is 10x10 sectors)
			for(int k=0; k<10 ; k++) {
				rows.getChildren().add(new HBox());
				for (int i = 0;i < 10;i++){
					((HBox) rows.getChildren().get(k)).getChildren().add(new VBox());
					((VBox) ((HBox) rows.getChildren().get(k)).getChildren().get(i)).setStyle("-fx-border-color: black;");

					((VBox) ((HBox) rows.getChildren().get(k)).getChildren().get(i)).getChildren().add(new HBox());
					((VBox) ((HBox) rows.getChildren().get(k)).getChildren().get(i)).getChildren().add(new HBox());
					
					for(int u=0; u<2;u++) {
						
						((HBox) ((VBox) ((HBox) rows.getChildren().get(k)).getChildren().get(i)).getChildren().get(u)).getChildren().add(new StackPane());
						((HBox) ((VBox) ((HBox) rows.getChildren().get(k)).getChildren().get(i)).getChildren().get(u)).getChildren().add(new StackPane());
						
						((StackPane) ((HBox) ((VBox) ((HBox) rows.getChildren().get(k)).getChildren().get(i)).getChildren().get(u)).getChildren().get(0)).setPrefSize(25, 25);
						((StackPane) ((HBox) ((VBox) ((HBox) rows.getChildren().get(k)).getChildren().get(i)).getChildren().get(u)).getChildren().get(0)).setStyle("-fx-border-color: grey;");
						
						((StackPane) ((HBox) ((VBox) ((HBox) rows.getChildren().get(k)).getChildren().get(i)).getChildren().get(u)).getChildren().get(1)).setPrefSize(25, 25);
						((StackPane) ((HBox) ((VBox) ((HBox) rows.getChildren().get(k)).getChildren().get(i)).getChildren().get(u)).getChildren().get(1)).setStyle("-fx-border-color: grey;");
					}
				}
			}
			// Numbers :
			// First is the number of the row       -> Sector
			// Second is the number of the column   -> Sector
			// Third is the number of the row       -> StackPane
			// Fourth is the number of the column   -> StackPane
			((StackPane) ((HBox) ((VBox) ((HBox) rows.getChildren().get(0)).getChildren().get(0)).getChildren().get(0)).getChildren().get(0)).getChildren().add(new Text("A"));
			((StackPane) ((HBox) ((VBox) ((HBox) rows.getChildren().get(0)).getChildren().get(0)).getChildren().get(0)).getChildren().get(0)).getChildren().remove(0);
			((StackPane) ((HBox) ((VBox) ((HBox) rows.getChildren().get(0)).getChildren().get(0)).getChildren().get(0)).getChildren().get(1)).getChildren().add(new Text("B"));
			
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
	
	public void creation(VBox rows) {
		Monde monde = new Monde();
		int size = monde.lesSecteurs.length;
		for(int i=0;i<size;i++) {
			Secteur[] row = monde.lesSecteurs[i];
			for(Secteur j:row) {
				if(j.getEau())
					((StackPane) ((HBox) ((VBox) ((HBox) rows.getChildren().get(0)).getChildren().get(0)).getChildren().get(0)).getChildren().get(1)).getChildren().add(new Text("B"));
			}
		}
		
	}
	
	public static void main(String args[])
	{
		Monde mondeJeu = new Monde().createWorld();
		launch(args);
	}
}
