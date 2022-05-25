package application;

// Importations
import java.util.ArrayList;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
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

// Classe
public class WorldGUI extends Application {
	// Variables de classe
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
	private VBox help;
	private VBox middleRow;
	
	public int num;
	
	// Robot utilisé actuellement
	public Robot selected;
	
	@SuppressWarnings("unchecked")
	@Override
	public void start(Stage stage) {
		// Inclusion dans bloc try pour pouvoir tracer les erreurs avec 'printStackTrace()'
		try {
			// Création des éléments qui seront dans la fenêtre de jeu principal
			this.line = new HBox();
			this.elements = new VBox();
			this.middleRow = new VBox();
			this.rows = new VBox();
			middleRow.getChildren().add(rows);
			elements.getChildren().add(middleRow);
			// Définition des dimensions de la grille de jeu
			rows.setMaxWidth(525);
			rows.setMaxHeight(510);
			// Bordures de la grille de jeu
			rows.setStyle("-fx-border-color: black;");
			// Numérotation des secteurs
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
			// Création des secteurs graphiques (dimensions 10x10)
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
			
			// Lancement de fonctions tiers
			num=1;
			creation();
			selected = robots.get(0);
			affichageHelp();
			line.getChildren().add(elements);
			sideBar();
			setMiddleRow();
			
			// Alignement de la matrice
			rows.setAlignment(Pos.CENTER);
			// Ajout de margin
			VBox.setMargin(rows, new Insets(25,25,0,0));
			// Création des éléments utilisés dans la scene de lancement du jeu
			VBox infos = new VBox();
			VBox displayLaunch = new VBox();
			// Attribution de la scene de base
			Scene game = new Scene(displayLaunch);
			// Création du gestionnaire d'evenement 
			EventWorld e = new EventWorld(this, game, line, infos, displayLaunch);
			// Mise en évidence du Robot 1 pour le premier tour
			int[] coord = selected.getCoord();
			((StackPane) ((HBox) ((VBox) ((HBox) rows.getChildren().get(coord[0]+1)).getChildren().get(coord[1]+1)).getChildren().get(1)).getChildren().get(0)).setBackground(new Background(new BackgroundFill(Color.MEDIUMVIOLETRED, CornerRadii.EMPTY, Insets.EMPTY)));
			((StackPane) ((HBox) ((VBox) ((HBox) rows.getChildren().get(coord[0]+1)).getChildren().get(coord[1]+1)).getChildren().get(1)).getChildren().get(1)).setBackground(new Background(new BackgroundFill(Color.MEDIUMVIOLETRED, CornerRadii.EMPTY, Insets.EMPTY)));
			// Mise en plein ecran du stage
			stage.setMaximized(true);
			// Ajout du titre de la fenêtre
			stage.setTitle("Robot Mineur - Le jeu");
			// Réglage de l'alignement et espacement de la fenêtre de jeu principal
			line.setAlignment(Pos.CENTER);
			line.setSpacing(50);
			
			// Scene de base avec bouton play 'Jouer' 'Comment jouer ?'
			VBox text = new VBox();
			Text titre = new Text("Robot Mineur");
			Text desc = new Text("Jeu réalisé dans le cadre d'un projet scolaire\n"
					+ "Université Polytechnique des Hauts de France");	
			titre.setFont(Font.font("Helvetica", FontWeight.BOLD, 150));
			desc.setFont(Font.font("Helvetica", FontWeight.NORMAL, 50));
			// Gestion des deux boutons de la fenêtre
			VBox buttons = new VBox();
			Button play = new Button();
			Button infosPlay = new Button();
			play.addEventHandler(MouseEvent.MOUSE_CLICKED, e);
			infosPlay.addEventFilter(MouseEvent.MOUSE_CLICKED, e);
			play.setText("Jouer");
			infosPlay.setText("Comment jouer ?");
			play.setStyle("-fx-font-size:60; -fx-padding: 0 50 0 50;");
			infosPlay.setStyle("-fx-font-size:40; -fx-padding: 3 30 3 30;");
			text.getChildren().add(titre);
			text.getChildren().add(desc);
			buttons.getChildren().add(play);
			buttons.getChildren().add(infosPlay);
			buttons.setSpacing(10);
			buttons.setAlignment(Pos.CENTER);
			desc.setTextAlignment(TextAlignment.CENTER);
			text.setAlignment(Pos.CENTER);
			
			displayLaunch.setSpacing(100);
			displayLaunch.setAlignment(Pos.CENTER);
			
			displayLaunch.getChildren().add(text);
			displayLaunch.getChildren().add(buttons);
			
			// Page "Comment jouer ?"
			infos.setAlignment(Pos.CENTER);
			infos.setSpacing(50);
			// Titre
			infos.getChildren().add(new Text("Comment jouer ?"));
			((Text) infos.getChildren().get(0)).setFont(Font.font("Helvetica", FontWeight.BOLD, 70));
			
			// Explication du jeu
			String explication = "Le but du jeu est de déplacer vos robots pour miner des minerais dans les mines"
					+ " correspondantes au type de votre robot puis d'emmener ces minerais dans les entrepôts, tout"
					+ " ça avec le moins d'actions requises jusqu'à épuisement des mines.\n\n"
					+ "Les mines sont marqués par un 'M', les robots par un 'R', les entrepôts par un 'E', "
					+ "tous ont une couleur attitrée qui montre le type de minerai, soit gris, soit jaune."
					+ " Les numéros situés à droite des lettres sont leurs numéros.";
			Text expl = new Text(explication);
			expl.setFont(Font.font("Helvetica", FontWeight.NORMAL, 30));
			expl.setWrappingWidth(900);
			expl.setTextAlignment(TextAlignment.CENTER);
			infos.getChildren().add(expl);
			
			
			// Bouton retour dans infos du jeu
			infos.getChildren().add(new Button("< Retour"));
			((Button) infos.getChildren().get(2)).addEventHandler(MouseEvent.MOUSE_CLICKED, e);
			((Button) infos.getChildren().get(2)).setStyle("-fx-font-size:40; -fx-padding: 3 30 3 30;");
			
			
			stage.setScene(game);
			
			// Affichage de la fenêtre
			stage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void creation() {
		// Création du monde
		monde = new Monde();
		monde.createWorld();
		
		// Récupération des vvariables les plus utiles
		mines = monde.getMines();
		entrepots = monde.getEntrepots();
		robots = monde.getRobots();
		
		sizeMines = mines.size(); 
		sizeEntrepots = entrepots.size();
		sizeRobots = robots.size();
		
		Secteur[][] lesSecteurs = monde.getSecteurs(); 
		// Premier coloriage de toute les cellules en vert
		for(int i=1;i<11;i++) {
			for(int j=1;j<11;j++) {
				((StackPane) ((HBox) ((VBox) ((HBox) rows.getChildren().get(i)).getChildren().get(j)).getChildren().get(0)).getChildren().get(0)).setBackground(new Background(new BackgroundFill(Color.PALEGREEN, CornerRadii.EMPTY, Insets.EMPTY)));
				((StackPane) ((HBox) ((VBox) ((HBox) rows.getChildren().get(i)).getChildren().get(j)).getChildren().get(0)).getChildren().get(1)).setBackground(new Background(new BackgroundFill(Color.PALEGREEN, CornerRadii.EMPTY, Insets.EMPTY)));
				((StackPane) ((HBox) ((VBox) ((HBox) rows.getChildren().get(i)).getChildren().get(j)).getChildren().get(1)).getChildren().get(0)).setBackground(new Background(new BackgroundFill(Color.PALEGREEN, CornerRadii.EMPTY, Insets.EMPTY)));
				((StackPane) ((HBox) ((VBox) ((HBox) rows.getChildren().get(i)).getChildren().get(j)).getChildren().get(1)).getChildren().get(1)).setBackground(new Background(new BackgroundFill(Color.PALEGREEN, CornerRadii.EMPTY, Insets.EMPTY)));			
			}
		}
		// Coloriage des autres cellules avec les couleurs correspondantes
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
				}
				else if(s.haveMine()) {
					Mine mi = s.getMine();
					((StackPane) ((HBox) ((VBox) ((HBox) rows.getChildren().get(i)).getChildren().get(j)).getChildren().get(0)).getChildren().get(0)).getChildren().add(new Text("M"));
					((StackPane) ((HBox) ((VBox) ((HBox) rows.getChildren().get(i)).getChildren().get(j)).getChildren().get(0)).getChildren().get(1)).getChildren().add(new Text(""+mi.getNumero()));
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
			}
		}
		// Ajout du text des tours
		rows.getChildren().add(new Text("Tour 1"));
		((Text) rows.getChildren().get(11)).setTextAlignment(TextAlignment.CENTER);
		((Text) rows.getChildren().get(11)).setFont(Font.font("Helvetica", FontWeight.BOLD, 20));
	}

	public void changeRobot() {
		// Changement du robot selectionné
		if(selected.getNumero()==robots.size()){
			num+=1;
			((Text) rows.getChildren().get(11)).setText("Tour "+num);
		}
		// Ancienne coordonnées
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
		
		// Nouvelles coordonnées 
		coord[0] = this.selected.getSonSecteur().getX();
		coord[1] = this.selected.getSonSecteur().getY();

		((StackPane) ((HBox) ((VBox) ((HBox) rows.getChildren().get(coord[0]+1)).getChildren().get(coord[1]+1)).getChildren().get(1)).getChildren().get(0)).setBackground(new Background(new BackgroundFill(Color.MEDIUMVIOLETRED, CornerRadii.EMPTY, Insets.EMPTY)));
		((StackPane) ((HBox) ((VBox) ((HBox) rows.getChildren().get(coord[0]+1)).getChildren().get(coord[1]+1)).getChildren().get(1)).getChildren().get(1)).setBackground(new Background(new BackgroundFill(Color.MEDIUMVIOLETRED, CornerRadii.EMPTY, Insets.EMPTY)));
		refreshRobotDisplay();
		}

	// Changement des cases colorés et du texte au changement de robot
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
	
	// Actualisation des statistiques
	public void refreshCapacityRobot() {
		if(this.selected.getSonSecteur().haveMine()) {
			int numMine = this.selected.getSonSecteur().getMine().getNumero()-1;
			if(numMine<2)
				((VBox) ((HBox) ((VBox) sidebar.getChildren().get(0)).getChildren().get(1)).getChildren().get(numMine)).getChildren().set(3, new Text("Capacite : " +
					mines.get(numMine).getCapacite()+ '/' +mines.get(numMine).getCapaciteStockageMax()));	
			else
				((VBox) ((HBox) ((VBox) sidebar.getChildren().get(0)).getChildren().get(1)).getChildren().get(numMine-2)).getChildren().set(7
						, new Text("Capacite : " +
						mines.get(numMine).getCapacite()+ '/' +mines.get(numMine).getCapaciteStockageMax()));	
		
		}
		else if(this.selected.getSonSecteur().haveEntrepot()) {
			int numEnt = this.selected.getSonSecteur().getEntrepot().getNumero()-1;
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
	
	// Création du panneaux de gauche (aide)
	public void affichageHelp() {
		help = new VBox();		
		help.setMaxHeight(482);
		help.setSpacing(75);
		HBox.setMargin(help, new Insets(25,25,25,25));

		VBox touches = new VBox();
		
		touches.getChildren().add(new Text("Aide"));	
		((Text) touches.getChildren().get(0)).setUnderline(true);
		((Text) touches.getChildren().get(0)).setFont(Font.font("Helvetica", FontWeight.BOLD, 30));
		
		touches.setAlignment(Pos.CENTER);
		touches.getChildren().add(new Text("Flèches pour se déplacer"));
		touches.getChildren().add(new Text("Espace pour passer le tour du robot selectionné"));
		touches.getChildren().add(new Text("Entrée pour miner/déposer"));
		((Text) touches.getChildren().get(1)).setFont(Font.font("Helvetica", FontWeight.NORMAL, 15));
		((Text) touches.getChildren().get(2)).setFont(Font.font("Helvetica", FontWeight.NORMAL, 15));
		((Text) touches.getChildren().get(3)).setFont(Font.font("Helvetica", FontWeight.NORMAL, 15));
		help.setAlignment(Pos.CENTER);
		line.getChildren().add(help);
		VBox infRobot = new VBox();
		// Currently used Robot infos
		infRobot.getChildren().add(new Text("Informations robot actuel"));
		((Text) infRobot.getChildren().get(0)).setFont(Font.font("Helvetica", FontWeight.BOLD, 20));
		infRobot.getChildren().add(new Text(""));
		((Text) infRobot.getChildren().get(1)).setFont(Font.font("Helvetica", FontWeight.NORMAL, 15));
		infRobot.getChildren().add(new Text(""));
		((Text) infRobot.getChildren().get(2)).setFont(Font.font("Helvetica", FontWeight.NORMAL, 15));
		infRobot.getChildren().add(new Text(""));
		((Text) infRobot.getChildren().get(3)).setFont(Font.font("Helvetica", FontWeight.NORMAL, 15));
		infRobot.getChildren().add(new Text(""));
		((Text) infRobot.getChildren().get(4)).setFont(Font.font("Helvetica", FontWeight.NORMAL, 15));
		infRobot.getChildren().add(new Text(""));
		((Text) infRobot.getChildren().get(5)).setFont(Font.font("Helvetica", FontWeight.NORMAL, 15));
		infRobot.setSpacing(5);
		infRobot.setAlignment(Pos.CENTER);
		help.getChildren().add(touches);
		help.getChildren().add(infRobot);
		refreshRobotDisplay();
	}
	
	// Verification de l'état actuelle de la partie (victoire ou non)
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
	
	// Création de la colonne du milieu 
	public void setMiddleRow() {
		VBox action = new VBox();
		middleRow.getChildren().add(action);
		middleRow.setAlignment(Pos.CENTER);
		middleRow.setSpacing(25);
		VBox.setMargin(middleRow, new Insets(0,0,25,25));
		// Current Action infos
		action.getChildren().add(new Text("Activité en cours"));
		((Text) action.getChildren().get(0)).setFont(Font.font("Helvetica", FontWeight.BOLD, 25));
		action.getChildren().add(new Text("En attente d'une décision"));
		action.getChildren().add(new Text(""));
		action.setAlignment(Pos.CENTER);
	}
	
	// Création de la colonne de droite (infos sur la partie)
	public void sideBar() {
		sidebar = new VBox();
		sidebar.setAlignment(Pos.CENTER);
		sidebar.setSpacing(100);
		HBox.setMargin(sidebar, new Insets(25,25,25,25));

		// création catégorie mines
		VBox catMines = new VBox();
		HBox infMines = new HBox();
		catMines.getChildren().add(new Text("Informations des mines"));
		((Text) catMines.getChildren().get(0)).setFont(Font.font("Helvetica", FontWeight.BOLD, 20));
		sidebar.getChildren().add(catMines);
		catMines.getChildren().add(infMines);
		catMines.setAlignment(Pos.CENTER);
		infMines.setSpacing(25);
		catMines.setSpacing(10);

		// création catégorie entrepots
		VBox catEnt = new VBox();
		HBox infEnt = new HBox();
		catEnt.getChildren().add(new Text("Informations des Entrepots"));
		((Text) catEnt.getChildren().get(0)).setFont(Font.font("Helvetica", FontWeight.BOLD, 20));
		sidebar.getChildren().add(catEnt);
		catEnt.getChildren().add(infEnt);
		catEnt.setAlignment(Pos.CENTER);
		infEnt.setSpacing(25);
		catEnt.setSpacing(10);

		// création catégorie robots
		VBox catRob = new VBox();
		HBox infRob = new HBox();
		catRob.getChildren().add(new Text("Informations des Robots")); 
		((Text) catRob.getChildren().get(0)).setFont(Font.font("Helvetica", FontWeight.BOLD, 20));
		sidebar.getChildren().add(catRob);
		catRob.getChildren().add(infRob);
		catRob.setAlignment(Pos.CENTER);
		infRob.setSpacing(25);
		catRob.setSpacing(10);
		
		// affichage des mines
		for(int i=0;i<sizeMines;i++) {
			if(i<2) {
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
			} else {
				((VBox) infMines.getChildren().get(i-2)).getChildren().add(new Text("Mine n°"+(i+1)));
				((Text) ((VBox) infMines.getChildren().get(i-2)).getChildren().get(4)).setFont(Font.font("Helvetica", FontWeight.BOLD, 15));
				((VBox) infMines.getChildren().get(i-2)).getChildren().add(new Text("Coordonnees : (" + 
				mines.get(i).getSonSecteur().getX()+";"+mines.get(i).getSonSecteur().getY()+")"));
				((VBox) infMines.getChildren().get(i-2)).getChildren().add(new Text("Type minerai : " + mines.get(i).getTypeMinerai()));		
				((VBox) infMines.getChildren().get(i-2)).getChildren().add(new Text("Capacite : " +
				mines.get(i).getCapacite()+ '/' +mines.get(i).getCapaciteStockageMax()));		
				((VBox) infMines.getChildren().get(i-2)).setAlignment(Pos.CENTER);
				infMines.setAlignment(Pos.CENTER);
			}
		}

		// affichage des entrepots
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
		
		// affichage des robots
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
	
	// Fonction de changement d'affichage de l'activité en cours 
	
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
	
	// Fonction qui change les stats du robot sélectionné quand nous changeons de sélection
	public void refreshRobotDisplay() {
		((Text)((VBox) help.getChildren().get(1)).getChildren().get(1)).setText("Numero : "+selected.getNumero());
		((Text)((VBox) help.getChildren().get(1)).getChildren().get(2)).setText("Nombre de minerais : "+selected.getCapacite());
		((Text)((VBox) help.getChildren().get(1)).getChildren().get(3)).setText("Capacite max : "+selected.getCapaciteStockageMax());
		((Text)((VBox) help.getChildren().get(1)).getChildren().get(4)).setText("Vitesse de minage : "+selected.getCapaciteMinage());
		((Text)((VBox) help.getChildren().get(1)).getChildren().get(5)).setText("Coordonnees : ("+selected.getSonSecteur().getX()+";"+selected.getSonSecteur().getY()+")");
	}
	
	// Lancement du programme
	public static void main(String args[])
	{
		launch(args);
	}
}



