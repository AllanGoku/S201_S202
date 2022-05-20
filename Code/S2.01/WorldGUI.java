package application;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
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
	
	private HBox line;
	private VBox elements;
	private VBox sidebar;
	private VBox middleRow;
	
	public int num;
	
	// selected robot
	public Robot selected;
	
	@Override
	public void start(Stage stage) {
		try {
			this.line = new HBox();
			this.elements = new VBox();
			this.middleRow = new VBox();
			// Creation of the VBox containing the rows
			this.rows = new VBox();
			middleRow.getChildren().add(rows);
			elements.getChildren().add(middleRow);
			rows.setMaxWidth(525);
			rows.setMaxHeight(510);
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
			selected = robots.get(0);
			affichageHelp();
			line.getChildren().add(elements);
			sideBar();
			setMiddleRow();
			
			// Alignement de la matrice
			rows.setAlignment(Pos.CENTER);
						
			Scene scene = new Scene(line);
			EventWorld e = new EventWorld(this);
			int[] coord = selected.getCoord();
			((StackPane) ((HBox) ((VBox) ((HBox) rows.getChildren().get(coord[0]+1)).getChildren().get(coord[1]+1)).getChildren().get(1)).getChildren().get(0)).setBackground(new Background(new BackgroundFill(Color.MEDIUMVIOLETRED, CornerRadii.EMPTY, Insets.EMPTY)));
			((StackPane) ((HBox) ((VBox) ((HBox) rows.getChildren().get(coord[0]+1)).getChildren().get(coord[1]+1)).getChildren().get(1)).getChildren().get(1)).setBackground(new Background(new BackgroundFill(Color.MEDIUMVIOLETRED, CornerRadii.EMPTY, Insets.EMPTY)));
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
					((StackPane) ((HBox) ((VBox) ((HBox) rows.getChildren().get(i)).getChildren().get(j)).getChildren().get(0)).getChildren().get(0)).setBackground(new Background(new BackgroundFill(Color.SKYBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
					((StackPane) ((HBox) ((VBox) ((HBox) rows.getChildren().get(i)).getChildren().get(j)).getChildren().get(0)).getChildren().get(1)).setBackground(new Background(new BackgroundFill(Color.SKYBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
					((StackPane) ((HBox) ((VBox) ((HBox) rows.getChildren().get(i)).getChildren().get(j)).getChildren().get(1)).getChildren().get(0)).setBackground(new Background(new BackgroundFill(Color.SKYBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
					((StackPane) ((HBox) ((VBox) ((HBox) rows.getChildren().get(i)).getChildren().get(j)).getChildren().get(1)).getChildren().get(1)).setBackground(new Background(new BackgroundFill(Color.SKYBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
				}
				else if(s.haveEntrepot()) {
					Entrepot ent = s.getEntrepot();
					((StackPane) ((HBox) ((VBox) ((HBox) rows.getChildren().get(i)).getChildren().get(j)).getChildren().get(0)).getChildren().get(0)).getChildren().add(new Text("E"));
					((StackPane) ((HBox) ((VBox) ((HBox) rows.getChildren().get(i)).getChildren().get(j)).getChildren().get(0)).getChildren().get(1)).getChildren().add(new Text(""+ent.getNumero()));
					if(ent.getTypeMinerai()=="NI") {
						((StackPane) ((HBox) ((VBox) ((HBox) rows.getChildren().get(i)).getChildren().get(j)).getChildren().get(0)).getChildren().get(0)).setBackground(new Background(new BackgroundFill(Color.SILVER, CornerRadii.EMPTY, Insets.EMPTY)));
						((StackPane) ((HBox) ((VBox) ((HBox) rows.getChildren().get(i)).getChildren().get(j)).getChildren().get(0)).getChildren().get(1)).setBackground(new Background(new BackgroundFill(Color.SILVER, CornerRadii.EMPTY, Insets.EMPTY)));
					} else if(ent.getTypeMinerai()=="OR") {
						((StackPane) ((HBox) ((VBox) ((HBox) rows.getChildren().get(i)).getChildren().get(j)).getChildren().get(0)).getChildren().get(0)).setBackground(new Background(new BackgroundFill(Color.GOLD, CornerRadii.EMPTY, Insets.EMPTY)));
						((StackPane) ((HBox) ((VBox) ((HBox) rows.getChildren().get(i)).getChildren().get(j)).getChildren().get(0)).getChildren().get(1)).setBackground(new Background(new BackgroundFill(Color.GOLD, CornerRadii.EMPTY, Insets.EMPTY)));
					}
					
					((StackPane) ((HBox) ((VBox) ((HBox) rows.getChildren().get(i)).getChildren().get(j)).getChildren().get(1)).getChildren().get(0)).setBackground(new Background(new BackgroundFill(Color.PALEGREEN, CornerRadii.EMPTY, Insets.EMPTY)));
					((StackPane) ((HBox) ((VBox) ((HBox) rows.getChildren().get(i)).getChildren().get(j)).getChildren().get(1)).getChildren().get(1)).setBackground(new Background(new BackgroundFill(Color.PALEGREEN, CornerRadii.EMPTY, Insets.EMPTY)));
				}
				else if(s.haveMine()) {
					Mine mi = s.getMine();
					((StackPane) ((HBox) ((VBox) ((HBox) rows.getChildren().get(i)).getChildren().get(j)).getChildren().get(0)).getChildren().get(0)).getChildren().add(new Text("M"));
					((StackPane) ((HBox) ((VBox) ((HBox) rows.getChildren().get(i)).getChildren().get(j)).getChildren().get(0)).getChildren().get(1)).getChildren().add(new Text(""+mi.getNumero()));
					((StackPane) ((HBox) ((VBox) ((HBox) rows.getChildren().get(i)).getChildren().get(j)).getChildren().get(0)).getChildren().get(0)).setBackground(new Background(new BackgroundFill(Color.PALEGREEN, CornerRadii.EMPTY, Insets.EMPTY)));
					((StackPane) ((HBox) ((VBox) ((HBox) rows.getChildren().get(i)).getChildren().get(j)).getChildren().get(0)).getChildren().get(1)).setBackground(new Background(new BackgroundFill(Color.PALEGREEN, CornerRadii.EMPTY, Insets.EMPTY)));
					((StackPane) ((HBox) ((VBox) ((HBox) rows.getChildren().get(i)).getChildren().get(j)).getChildren().get(1)).getChildren().get(0)).setBackground(new Background(new BackgroundFill(Color.PALEGREEN, CornerRadii.EMPTY, Insets.EMPTY)));
					((StackPane) ((HBox) ((VBox) ((HBox) rows.getChildren().get(i)).getChildren().get(j)).getChildren().get(1)).getChildren().get(1)).setBackground(new Background(new BackgroundFill(Color.PALEGREEN, CornerRadii.EMPTY, Insets.EMPTY)));
					if(mi.getTypeMinerai()=="NI") {
						((StackPane) ((HBox) ((VBox) ((HBox) rows.getChildren().get(i)).getChildren().get(j)).getChildren().get(0)).getChildren().get(0)).setBackground(new Background(new BackgroundFill(Color.SILVER, CornerRadii.EMPTY, Insets.EMPTY)));
						((StackPane) ((HBox) ((VBox) ((HBox) rows.getChildren().get(i)).getChildren().get(j)).getChildren().get(0)).getChildren().get(1)).setBackground(new Background(new BackgroundFill(Color.SILVER, CornerRadii.EMPTY, Insets.EMPTY)));
					} else if(mi.getTypeMinerai()=="OR") {
						((StackPane) ((HBox) ((VBox) ((HBox) rows.getChildren().get(i)).getChildren().get(j)).getChildren().get(0)).getChildren().get(0)).setBackground(new Background(new BackgroundFill(Color.GOLD, CornerRadii.EMPTY, Insets.EMPTY)));
						((StackPane) ((HBox) ((VBox) ((HBox) rows.getChildren().get(i)).getChildren().get(j)).getChildren().get(0)).getChildren().get(1)).setBackground(new Background(new BackgroundFill(Color.GOLD, CornerRadii.EMPTY, Insets.EMPTY)));
					}
				}
				if(s.haveRobot()) {
					Robot ro = s.getRobot();
					((StackPane) ((HBox) ((VBox) ((HBox) rows.getChildren().get(i)).getChildren().get(j)).getChildren().get(1)).getChildren().get(0)).getChildren().add(new Text("R"));
					((StackPane) ((HBox) ((VBox) ((HBox) rows.getChildren().get(i)).getChildren().get(j)).getChildren().get(1)).getChildren().get(1)).getChildren().add(new Text(""+ro.getNumero()));
					((StackPane) ((HBox) ((VBox) ((HBox) rows.getChildren().get(i)).getChildren().get(j)).getChildren().get(0)).getChildren().get(0)).setBackground(new Background(new BackgroundFill(Color.PALEGREEN, CornerRadii.EMPTY, Insets.EMPTY)));
					((StackPane) ((HBox) ((VBox) ((HBox) rows.getChildren().get(i)).getChildren().get(j)).getChildren().get(0)).getChildren().get(1)).setBackground(new Background(new BackgroundFill(Color.PALEGREEN, CornerRadii.EMPTY, Insets.EMPTY)));
					((StackPane) ((HBox) ((VBox) ((HBox) rows.getChildren().get(i)).getChildren().get(j)).getChildren().get(1)).getChildren().get(0)).setBackground(new Background(new BackgroundFill(Color.PALEVIOLETRED, CornerRadii.EMPTY, Insets.EMPTY)));
					((StackPane) ((HBox) ((VBox) ((HBox) rows.getChildren().get(i)).getChildren().get(j)).getChildren().get(1)).getChildren().get(1)).setBackground(new Background(new BackgroundFill(Color.PALEVIOLETRED, CornerRadii.EMPTY, Insets.EMPTY)));
					if(ro.getTypeMinerai()=="NI") {
						((Text) ((StackPane) ((HBox) ((VBox) ((HBox) rows.getChildren().get(i)).getChildren().get(j)).getChildren().get(1)).getChildren().get(0)).getChildren().get(0)).setFill(Color.GREY);
						((Text) ((StackPane) ((HBox) ((VBox) ((HBox) rows.getChildren().get(i)).getChildren().get(j)).getChildren().get(1)).getChildren().get(1)).getChildren().get(0)).setFill(Color.GREY);
						((Text) ((StackPane) ((HBox) ((VBox) ((HBox) rows.getChildren().get(i)).getChildren().get(j)).getChildren().get(1)).getChildren().get(0)).getChildren().get(0)).setFont(Font.font("Helvetica", FontWeight.BOLD, 15));
						((Text) ((StackPane) ((HBox) ((VBox) ((HBox) rows.getChildren().get(i)).getChildren().get(j)).getChildren().get(1)).getChildren().get(1)).getChildren().get(0)).setFont(Font.font("Helvetica", FontWeight.BOLD, 15));
					} else if(ro.getTypeMinerai()=="OR") {
						((Text) ((StackPane) ((HBox) ((VBox) ((HBox) rows.getChildren().get(i)).getChildren().get(j)).getChildren().get(1)).getChildren().get(0)).getChildren().get(0)).setFill(Color.GOLD);
						((Text) ((StackPane) ((HBox) ((VBox) ((HBox) rows.getChildren().get(i)).getChildren().get(j)).getChildren().get(1)).getChildren().get(1)).getChildren().get(0)).setFill(Color.GOLD);
						((Text) ((StackPane) ((HBox) ((VBox) ((HBox) rows.getChildren().get(i)).getChildren().get(j)).getChildren().get(1)).getChildren().get(0)).getChildren().get(0)).setFont(Font.font("Helvetica", FontWeight.BOLD, 15));
						((Text) ((StackPane) ((HBox) ((VBox) ((HBox) rows.getChildren().get(i)).getChildren().get(j)).getChildren().get(1)).getChildren().get(1)).getChildren().get(0)).setFont(Font.font("Helvetica", FontWeight.BOLD, 15));
					}
				}
				else if(!(s.getEau()) && !(s.haveEntrepot()) && !(s.haveMine())) {
					((StackPane) ((HBox) ((VBox) ((HBox) rows.getChildren().get(i)).getChildren().get(j)).getChildren().get(0)).getChildren().get(0)).setBackground(new Background(new BackgroundFill(Color.PALEGREEN, CornerRadii.EMPTY, Insets.EMPTY)));
					((StackPane) ((HBox) ((VBox) ((HBox) rows.getChildren().get(i)).getChildren().get(j)).getChildren().get(0)).getChildren().get(1)).setBackground(new Background(new BackgroundFill(Color.PALEGREEN, CornerRadii.EMPTY, Insets.EMPTY)));
					((StackPane) ((HBox) ((VBox) ((HBox) rows.getChildren().get(i)).getChildren().get(j)).getChildren().get(1)).getChildren().get(0)).setBackground(new Background(new BackgroundFill(Color.PALEGREEN, CornerRadii.EMPTY, Insets.EMPTY)));
					((StackPane) ((HBox) ((VBox) ((HBox) rows.getChildren().get(i)).getChildren().get(j)).getChildren().get(1)).getChildren().get(1)).setBackground(new Background(new BackgroundFill(Color.PALEGREEN, CornerRadii.EMPTY, Insets.EMPTY)));
				}
			}
		}
		rows.getChildren().add(new Text("Tour 1"));
		((Text) rows.getChildren().get(11)).setTextAlignment(TextAlignment.CENTER);
		((Text) rows.getChildren().get(11)).setFont(Font.font("Helvetica", FontWeight.BOLD, 20));
	}

	public void changeRobot() {
		if(selected.getNumero()==robots.size()){
			num+=1;
			((Text) rows.getChildren().get(11)).setText("Tour "+num);
		}
		// Ancient coord
		int[] coord = new int[2];
		coord[0] = this.selected.getSonSecteur().getX();
		coord[1] = this.selected.getSonSecteur().getY();
		
		((StackPane) ((HBox) ((VBox) ((HBox) rows.getChildren().get(coord[0]+1)).getChildren().get(coord[1]+1)).getChildren().get(1)).getChildren().get(0)).setBackground(new Background(new BackgroundFill(Color.PALEVIOLETRED, CornerRadii.EMPTY, Insets.EMPTY)));
		((StackPane) ((HBox) ((VBox) ((HBox) rows.getChildren().get(coord[0]+1)).getChildren().get(coord[1]+1)).getChildren().get(1)).getChildren().get(1)).setBackground(new Background(new BackgroundFill(Color.PALEVIOLETRED	, CornerRadii.EMPTY, Insets.EMPTY)));
		
		if(this.selected.getNumero()==this.robots.size()) {
			this.selected = robots.get(0);
		}else {
			this.selected = robots.get(this.selected.getNumero());
		}
		
		// New coord
		coord[0] = this.selected.getSonSecteur().getX();
		coord[1] = this.selected.getSonSecteur().getY();

		((StackPane) ((HBox) ((VBox) ((HBox) rows.getChildren().get(coord[0]+1)).getChildren().get(coord[1]+1)).getChildren().get(1)).getChildren().get(0)).setBackground(new Background(new BackgroundFill(Color.MEDIUMVIOLETRED, CornerRadii.EMPTY, Insets.EMPTY)));
		((StackPane) ((HBox) ((VBox) ((HBox) rows.getChildren().get(coord[0]+1)).getChildren().get(coord[1]+1)).getChildren().get(1)).getChildren().get(1)).setBackground(new Background(new BackgroundFill(Color.MEDIUMVIOLETRED, CornerRadii.EMPTY, Insets.EMPTY)));
		refreshRobotDisplay();
		}
	
	public void moveRobot(int beforeX, int beforeY, int afterX, int afterY) {

		beforeX+=1;
		beforeY+=1;
		afterX+=1;
		afterY+=1;	

		((StackPane) ((HBox) ((VBox) ((HBox) rows.getChildren().get(beforeX)).getChildren().get(beforeY)).getChildren().get(1)).getChildren().get(0)).setBackground(new Background(new BackgroundFill(Color.PALEGREEN, CornerRadii.EMPTY, Insets.EMPTY)));
		((StackPane) ((HBox) ((VBox) ((HBox) rows.getChildren().get(beforeX)).getChildren().get(beforeY)).getChildren().get(1)).getChildren().get(1)).setBackground(new Background(new BackgroundFill(Color.PALEGREEN, CornerRadii.EMPTY, Insets.EMPTY)));
		((StackPane) ((HBox) ((VBox) ((HBox) rows.getChildren().get(afterX)).getChildren().get(afterY)).getChildren().get(1)).getChildren().get(0)).setBackground(new Background(new BackgroundFill(Color.MEDIUMVIOLETRED, CornerRadii.EMPTY, Insets.EMPTY)));
		((StackPane) ((HBox) ((VBox) ((HBox) rows.getChildren().get(afterX)).getChildren().get(afterY)).getChildren().get(1)).getChildren().get(1)).setBackground(new Background(new BackgroundFill(Color.MEDIUMVIOLETRED, CornerRadii.EMPTY, Insets.EMPTY)));

		((StackPane) ((HBox) ((VBox) ((HBox) rows.getChildren().get(beforeX)).getChildren().get(beforeY)).getChildren().get(1)).getChildren().get(0)).getChildren().remove(0);
		((StackPane) ((HBox) ((VBox) ((HBox) rows.getChildren().get(beforeX)).getChildren().get(beforeY)).getChildren().get(1)).getChildren().get(1)).getChildren().remove(0);
		((StackPane) ((HBox) ((VBox) ((HBox) rows.getChildren().get(afterX)).getChildren().get(afterY)).getChildren().get(1)).getChildren().get(0)).getChildren().add(new Text("R"));
		((StackPane) ((HBox) ((VBox) ((HBox) rows.getChildren().get(afterX)).getChildren().get(afterY)).getChildren().get(1)).getChildren().get(1)).getChildren().add(new Text(""+this.selected.getNumero()));
		
		if(selected.getNumero()-1<3)
			((VBox) ((HBox) ((VBox) sidebar.getChildren().get(2)).getChildren().get(1)).getChildren().get(selected.getNumero()-1)).getChildren().set(1, new Text("Coordonnees : ("+(afterX-1)+";"+(afterY-1)+")"));	
		else
			((VBox) ((HBox) ((VBox) sidebar.getChildren().get(2)).getChildren().get(1)).getChildren().get(selected.getNumero()-4)).getChildren().set(5, new Text("Coordonnees : ("+(afterX-1)+";"+(afterY-1)+")"));	

		if(selected.getTypeMinerai()=="NI") {
			((Text) ((StackPane) ((HBox) ((VBox) ((HBox) rows.getChildren().get(afterX)).getChildren().get(afterY)).getChildren().get(1)).getChildren().get(0)).getChildren().get(0)).setFill(Color.GREY);
			((Text) ((StackPane) ((HBox) ((VBox) ((HBox) rows.getChildren().get(afterX)).getChildren().get(afterY)).getChildren().get(1)).getChildren().get(1)).getChildren().get(0)).setFill(Color.GREY);
			((Text) ((StackPane) ((HBox) ((VBox) ((HBox) rows.getChildren().get(afterX)).getChildren().get(afterY)).getChildren().get(1)).getChildren().get(0)).getChildren().get(0)).setFont(Font.font("Helvetica", FontWeight.BOLD, 15));
			((Text) ((StackPane) ((HBox) ((VBox) ((HBox) rows.getChildren().get(afterX)).getChildren().get(afterY)).getChildren().get(1)).getChildren().get(1)).getChildren().get(0)).setFont(Font.font("Helvetica", FontWeight.BOLD, 15));
		} else if(selected.getTypeMinerai()=="OR") {
			((Text) ((StackPane) ((HBox) ((VBox) ((HBox) rows.getChildren().get(afterX)).getChildren().get(afterY)).getChildren().get(1)).getChildren().get(0)).getChildren().get(0)).setFill(Color.GOLD);
			((Text) ((StackPane) ((HBox) ((VBox) ((HBox) rows.getChildren().get(afterX)).getChildren().get(afterY)).getChildren().get(1)).getChildren().get(1)).getChildren().get(0)).setFill(Color.GOLD);
			((Text) ((StackPane) ((HBox) ((VBox) ((HBox) rows.getChildren().get(afterX)).getChildren().get(afterY)).getChildren().get(1)).getChildren().get(0)).getChildren().get(0)).setFont(Font.font("Helvetica", FontWeight.BOLD, 15));
			((Text) ((StackPane) ((HBox) ((VBox) ((HBox) rows.getChildren().get(afterX)).getChildren().get(afterY)).getChildren().get(1)).getChildren().get(1)).getChildren().get(0)).setFont(Font.font("Helvetica", FontWeight.BOLD, 15));
		}
		changeRobot();
	}
	
	public void refreshCapacityRobot() {	
		if(this.selected.getSonSecteur().haveMine()) {
			int numMine = this.selected.getSonSecteur().getMine().getNumero();
			numMine-=1;
			((VBox) ((HBox) ((VBox) sidebar.getChildren().get(0)).getChildren().get(1)).getChildren().get(numMine)).getChildren().set(3, new Text("Capacite : " +
					mines.get(numMine).getCapacite()+ '/' +mines.get(numMine).getCapaciteStockageMax()));	
		}
		else if(this.selected.getSonSecteur().haveEntrepot()) {
			int numEnt = this.selected.getSonSecteur().getEntrepot().getNumero();
			numEnt-=1;
			((VBox) ((HBox) ((VBox) sidebar.getChildren().get(1)).getChildren().get(1)).getChildren().get(numEnt)).getChildren().set(3, new Text("Contient " +
					entrepots.get(numEnt).getCapacite()+" minerais"));
		}

		if(selected.getNumero()-1<3)
			((VBox) ((HBox) ((VBox) sidebar.getChildren().get(2)).getChildren().get(1)).getChildren().get(selected.getNumero()-1)).getChildren().set(3, new Text("Capacite : " +
					robots.get(selected.getNumero()-1).getCapacite()+ '/' +robots.get(selected.getNumero()-1).getCapaciteStockageMax()));
		else
			((VBox) ((HBox) ((VBox) sidebar.getChildren().get(2)).getChildren().get(1)).getChildren().get(selected.getNumero()-4)).getChildren().set(3, new Text("Capacite : " +
					robots.get(selected.getNumero()-1).getCapacite()+ '/' +robots.get(selected.getNumero()-1).getCapaciteStockageMax()));
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
		VBox help = new VBox();		
		help.setMaxHeight(482);
		HBox.setMargin(help, new Insets(25,25,25,25));
		help.getChildren().add(new Text("Aide"));
		((Text) help.getChildren().get(0)).setUnderline(true);
		((Text) help.getChildren().get(0)).setFont(Font.font("Helvetica", FontWeight.BOLD, 30));

		help.getChildren().add(new VBox());	
		
		((VBox) help.getChildren().get(1)).setAlignment(Pos.CENTER);
		((VBox) help.getChildren().get(1)).getChildren().add(new Text("Flèches pour se déplacer"));
		((VBox) help.getChildren().get(1)).getChildren().add(new Text("Espace pour passer le tour du robot selectionné"));
		((VBox) help.getChildren().get(1)).getChildren().add(new Text("Entrée pour miner/déposer"));
		((Text) ((VBox) help.getChildren().get(1)).getChildren().get(0)).setFont(Font.font("Helvetica", FontWeight.NORMAL, 15));
		((Text) ((VBox) help.getChildren().get(1)).getChildren().get(1)).setFont(Font.font("Helvetica", FontWeight.NORMAL, 15));
		((Text) ((VBox) help.getChildren().get(1)).getChildren().get(2)).setFont(Font.font("Helvetica", FontWeight.NORMAL, 15));
		help.setAlignment(Pos.CENTER);
		line.getChildren().add(help);
	}
	
	public boolean verifWin() {
		boolean win = true;
		for(Mine m:mines) {
			if(m.getCapacite()!=0) {
				win=false;
			}
		}
		for(Robot r:robots) {
			if(r.getCapacite()!=0) {
				win=false;
			}
		}
		return win; 
	}
	
	
	public void setMiddleRow() {
		VBox action = new VBox();
		VBox infRobot = new VBox();
		middleRow.getChildren().add(action);
		middleRow.getChildren().add(infRobot);
		middleRow.setAlignment(Pos.CENTER);
		middleRow.setSpacing(25);
		VBox.setMargin(middleRow, new Insets(0,0,25,25));
		// Current Action infos
		action.getChildren().add(new Text("Activité en cours"));
		((Text) action.getChildren().get(0)).setFont(Font.font("Helvetica", FontWeight.BOLD, 25));
		action.getChildren().add(new Text("En attente d'une décision"));
		action.getChildren().add(new Text(""));
		action.setAlignment(Pos.CENTER);
		// Currently used Robot infos
		infRobot.getChildren().add(new Text("Informations robot actuel"));
		((Text) infRobot.getChildren().get(0)).setFont(Font.font("Helvetica", FontWeight.BOLD, 20));
		infRobot.getChildren().add(new Text(""));
		infRobot.getChildren().add(new Text(""));
		infRobot.getChildren().add(new Text(""));
		infRobot.getChildren().add(new Text(""));
		infRobot.getChildren().add(new Text(""));
		infRobot.setSpacing(5);
		infRobot.setAlignment(Pos.CENTER);
		refreshRobotDisplay();
	}
	
	public void sideBar() {
		sidebar = new VBox();
		sidebar.setAlignment(Pos.CENTER);
		sidebar.setSpacing(100);
		HBox.setMargin(sidebar, new Insets(25,25,25,25));
		
		VBox catMines = new VBox();
		HBox infMines = new HBox();
		catMines.getChildren().add(new Text("Informations des mines"));
		((Text) catMines.getChildren().get(0)).setFont(Font.font("Helvetica", FontWeight.BOLD, 20));
		sidebar.getChildren().add(catMines);
		catMines.getChildren().add(infMines);
		catMines.setAlignment(Pos.CENTER);
		infMines.setSpacing(25);
		catMines.setSpacing(10);

		VBox catEnt = new VBox();
		HBox infEnt = new HBox();
		catEnt.getChildren().add(new Text("Informations des Entrepots"));
		((Text) catEnt.getChildren().get(0)).setFont(Font.font("Helvetica", FontWeight.BOLD, 20));
		sidebar.getChildren().add(catEnt);
		catEnt.getChildren().add(infEnt);
		catEnt.setAlignment(Pos.CENTER);
		infEnt.setSpacing(25);
		catEnt.setSpacing(10);

		VBox catRob = new VBox();
		HBox infRob = new HBox();
		catRob.getChildren().add(new Text("Informations des Robots")); 
		((Text) catRob.getChildren().get(0)).setFont(Font.font("Helvetica", FontWeight.BOLD, 20));
		sidebar.getChildren().add(catRob);
		catRob.getChildren().add(infRob);
		catRob.setAlignment(Pos.CENTER);
		infRob.setSpacing(25);
		catRob.setSpacing(10);
		
		// display of the stats
		for(int i=0;i<sizeMines;i++) {
			infMines.getChildren().add(new VBox());
			((VBox) infMines.getChildren().get(i)).getChildren().add(new Text("Mine n°"+(i+1)));
			((Text) ((VBox) infMines.getChildren().get(i)).getChildren().get(0)).setFont(Font.font("Helvetica", FontWeight.BOLD, 15));
			((VBox) infMines.getChildren().get(i)).getChildren().add(new Text("Coordonnees : (" + 
			mines.get(i).getSonSecteur().getX()+";"+mines.get(i).getSonSecteur().getY()+")"));
			((VBox) infMines.getChildren().get(i)).getChildren().add(new Text("Type minerai : " + mines.get(i).getTypeMinerai()));		
			((VBox) infMines.getChildren().get(i)).getChildren().add(new Text("Capacite : " +
			mines.get(i).getCapacite()+ '/' +mines.get(i).getCapaciteStockageMax()));		
			((VBox) infMines.getChildren().get(i)).setAlignment(Pos.CENTER);
			infMines.setAlignment(Pos.CENTER);
		}
		
		for(int i=0;i<sizeEntrepots;i++) {
			infEnt.getChildren().add(new VBox());
			((VBox) infEnt.getChildren().get(i)).getChildren().add(new Text("Entrepot n°"+(i+1)));
			((Text) ((VBox) infEnt.getChildren().get(i)).getChildren().get(0)).setFont(Font.font("Helvetica", FontWeight.BOLD, 15));
			((VBox) infEnt.getChildren().get(i)).getChildren().add(new Text("Coordonnees : (" + 
					entrepots.get(i).getSonSecteur().getX()+";"+mines.get(i).getSonSecteur().getY()+")"));
			((VBox) infEnt.getChildren().get(i)).getChildren().add(new Text("Type minerai : " + entrepots.get(i).getTypeMinerai()));		
			((VBox) infEnt.getChildren().get(i)).getChildren().add(new Text("Contient " +
					entrepots.get(i).getCapacite()+" minerais"));		
			((VBox) infEnt.getChildren().get(i)).setAlignment(Pos.CENTER);
			infEnt.setAlignment(Pos.CENTER);
		}
		for(int i=0;i<sizeRobots;i++) {
			if(i<3) {
				infRob.getChildren().add(new VBox());
				((VBox) infRob.getChildren().get(i)).getChildren().add(new Text("Robot n°"+(i+1)));
				((Text) ((VBox) infRob.getChildren().get(i)).getChildren().get(0)).setFont(Font.font("Helvetica", FontWeight.BOLD, 15));
				((VBox) infRob.getChildren().get(i)).getChildren().add(new Text("Coordonnees : (" + 
						robots.get(i).getSonSecteur().getX()+";"+robots.get(i).getSonSecteur().getY()+")"));
				((VBox) infRob.getChildren().get(i)).getChildren().add(new Text("Type minerai : " + robots.get(i).getTypeMinerai()));		
				((VBox) infRob.getChildren().get(i)).getChildren().add(new Text("Capactie : " +
						robots.get(i).getCapacite()+"/"+robots.get(i).getCapaciteStockageMax()));
				((VBox) infRob.getChildren().get(i)).setAlignment(Pos.CENTER);
				infRob.setAlignment(Pos.CENTER);
			} else {
				((VBox) infRob.getChildren().get(i-3)).getChildren().add(new Text("Robot n°"+(i+1)));
				((Text) ((VBox) infRob.getChildren().get(i-3)).getChildren().get(4)).setFont(Font.font("Helvetica", FontWeight.BOLD, 15));
				((VBox) infRob.getChildren().get(i-3)).getChildren().add(new Text("Coordonnees : (" + 
						robots.get(i).getSonSecteur().getX()+";"+robots.get(i).getSonSecteur().getY()+")"));
				((VBox) infRob.getChildren().get(i-3)).getChildren().add(new Text("Type minerai : " + robots.get(i).getTypeMinerai()));		
				((VBox) infRob.getChildren().get(i-3)).getChildren().add(new Text("Capactie : " +
						robots.get(i).getCapacite()+"/"+robots.get(i).getCapaciteStockageMax()));
				((VBox) infRob.getChildren().get(i-3)).setAlignment(Pos.CENTER);
				infRob.setAlignment(Pos.CENTER);
			}
			
		}
		line.getChildren().add(sidebar);
	}
	
	public void move(int n, int x, int y) {
		((Text) ((VBox) middleRow.getChildren().get(1)).getChildren().get(1)).setText("Le robot n°"+n+" a ete deplace en ("+x+";"+y+")");
		((Text) ((VBox) middleRow.getChildren().get(1)).getChildren().get(2)).setText("");
	}
	public void errorMove(){
		((Text) ((VBox) middleRow.getChildren().get(1)).getChildren().get(1)).setText("Cette direction n'est pas atteignable!");
		((Text) ((VBox) middleRow.getChildren().get(1)).getChildren().get(2)).setText("");
	}
	
	public void contenuDebut(boolean mine) {
		if(mine) {
			((Text) ((VBox) middleRow.getChildren().get(1)).getChildren().get(1)).setText("Minage...");			
			((Text) ((VBox) middleRow.getChildren().get(1)).getChildren().get(2)).setText("");
		}else {
			((Text) ((VBox) middleRow.getChildren().get(1)).getChildren().get(1)).setText("Depot des ressources...");
			((Text) ((VBox) middleRow.getChildren().get(1)).getChildren().get(2)).setText("");
		}
	}
	public void contenuFin(boolean mine) {
		if(mine) {
			((Text) ((VBox) middleRow.getChildren().get(1)).getChildren().get(2)).setText("Minage effectue avec succes");
		}else {
			((Text) ((VBox) middleRow.getChildren().get(1)).getChildren().get(2)).setText("Depot effectue avec succes");
		}
	}
	
	public void errorContenu(String str) {
		((Text) ((VBox) middleRow.getChildren().get(1)).getChildren().get(1)).setText(str);
		((Text) ((VBox) middleRow.getChildren().get(1)).getChildren().get(2)).setText("");
	}
	
	public void changeDisplay() {
		((Text) ((VBox) middleRow.getChildren().get(1)).getChildren().get(1)).setText("Changement de robot effectue avec succes");
		((Text) ((VBox) middleRow.getChildren().get(1)).getChildren().get(2)).setText("");
		refreshRobotDisplay();
	}
	public void refreshRobotDisplay() {
		((Text)((VBox) middleRow.getChildren().get(2)).getChildren().get(1)).setText("Numero : "+selected.getNumero());
		((Text)((VBox) middleRow.getChildren().get(2)).getChildren().get(2)).setText("Nombre de minerais : "+selected.getCapacite());
		((Text)((VBox) middleRow.getChildren().get(2)).getChildren().get(3)).setText("Capacite max : "+selected.getCapaciteStockageMax());
		((Text)((VBox) middleRow.getChildren().get(2)).getChildren().get(4)).setText("Vitesse de minage : "+selected.getCapaciteMinage());
		((Text)((VBox) middleRow.getChildren().get(2)).getChildren().get(5)).setText("Coordonnees : ("+selected.getSonSecteur().getX()+";"+selected.getSonSecteur().getY()+")");
	}
	public static void main(String args[])
	{
		launch(args);
	}
}
