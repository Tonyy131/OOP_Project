package views;

import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.util.ConcurrentModificationException;
import javafx.scene.input.KeyCombination;
import engine.Game;
import exceptions.InvalidTargetException;
import exceptions.MovementException;
import exceptions.NoAvailableResourcesException;
import exceptions.NotEnoughActionsException;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.Lighting;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.TilePane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import model.characters.*;
import model.characters.Character;
import model.world.*;
import model.collectibles.*;

public class Main extends Application {
	public static GridPane maps = new GridPane();
	public static ToggleButton[][] buttons = new ToggleButton[15][15];
	public static Group g = new Group();
	public static Rectangle healthBar;
	public static ImageView charView;
	public static Hero usedHero;
	public static boolean target;
	public static Character selected;
	public static TextFlow attributes;
	public static File gameOver = new File("over.mp3");
	static Media gameOverSound = new Media(gameOver.toURI().toString());
	public static MediaPlayer gameOverSoundPlayer = new MediaPlayer (gameOverSound);
	public static File win = new File("win.mp4");
	static Media winSound = new Media(win.toURI().toString());
	public static MediaPlayer winSoundPlayer = new MediaPlayer(winSound);
	public static void main(String[] args) {
		launch(args);
	}

	public static void fadeIn(Group r) {
		FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), r);
		fadeTransition.setFromValue(0.5);
		fadeTransition.setToValue(1);
		fadeTransition.play();
	}

	public void fadeOut(Group r) {
		FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), r);
		fadeTransition.setFromValue(1);
		fadeTransition.setToValue(0.5);
		fadeTransition.play();
	}

	public void start(Stage stage) throws Exception {
		Group root = new Group();
		stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
		Scene startPage = new Scene(root);
		startPage.setFill(Color.BLACK);
		stage.setMaximized(true);
		stage.setScene(startPage);
		stage.setTitle("The Last of Us");
		Image icon = new Image("icon.png");
		//
		// scene 2
		//Group root2 = new Group();
		//Scene chooseNoPlayers = new Scene(root2);
		//chooseNoPlayers.setFill(Color.BLACK);
		stage.initStyle(StageStyle.UNDECORATED);
		// scene 3
		Group root3 = new Group();
		Scene ChoosePlayer = new Scene(root3);
		ChoosePlayer.setFill(Color.BLACK);
		// scene credit
		Group cr = new Group();
		Scene creditss = new Scene(cr);
		// Game scene

		Scene Game = new Scene(g);
		Game.setFill(Color.BLACK);
		// Music
		File file=new File("Sound.mp3");
		Media music=new Media(file.toURI().toString());
		MediaPlayer mediaPlayer = new MediaPlayer(music);
		mediaPlayer.setAutoPlay(true);
		mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
		File attack = new File("attack.mp3");
		Media attackSound = new Media(attack.toURI().toString());
		MediaPlayer attackSoundPlayer = new MediaPlayer(attackSound);
		File move = new File("move.mp3");
		Media moveSound = new Media(move.toURI().toString());
		MediaPlayer moveSoundPlayer = new MediaPlayer(moveSound);
		
		File use = new File("supply.mp3");
		Media useSound = new Media(use.toURI().toString());
		MediaPlayer useSoundPlayer = new MediaPlayer(useSound);
		File end = new File("end.mp3");
		Media endSound = new Media(end.toURI().toString());
		MediaPlayer endSoundPlayer = new MediaPlayer(endSound);
		File vaccine = new File("vaccine.mp4");
		Media vaccineSound = new Media(vaccine.toURI().toString());
		MediaPlayer vaccineSoundPlayer = new MediaPlayer(vaccineSound);
		File trap = new File("trap.mp4");
		Media trapSound = new Media(trap.toURI().toString());
		MediaPlayer trapSoundPlayer = new MediaPlayer(trapSound);
		// start background
		Image back = new Image("background.jpg");
		ImageView backG = new ImageView(back);
		backG.setFitWidth(Screen.getPrimary().getVisualBounds().getWidth());
		backG.setFitHeight(Screen.getPrimary().getVisualBounds().getHeight() + 50);
		root.getChildren().add(backG);
		stage.setFullScreenExitHint("");
		// Start buttom begin
		Image startButtom = new Image("Start Game.png");
		ImageView startButtomView = new ImageView(startButtom);
		startButtomView.setFitHeight(80);
		Button startGame = new Button();
		startGame.setLayoutX(Screen.getPrimary().getVisualBounds().getWidth()-1086);
		startGame.setLayoutY(Screen.getPrimary().getVisualBounds().getHeight()-616);
		startGame.setGraphic(startButtomView);
		startGame.setBackground(null);
		startGame.setOnAction(e -> {
			fadeOut(root);
			try {
				engine.Game.loadHeroes("Heroes.csv");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			stage.setScene(ChoosePlayer);
			//stage.setFullScreen(true);
			fadeIn(root3);
		});

		root.getChildren().add(startGame);
		// Game background
		Image back4 = new Image("last.png");
		ImageView backG4 = new ImageView(back4);
		backG4.setFitHeight(500);
		backG4.setFitWidth(500);
		// backG4.setFitWidth(Screen.getPrimary().getVisualBounds().getWidth());
		// backG4.setFitHeight(Screen.getPrimary().getVisualBounds().getHeight()+50);
		Game.setFill(Color.BLACK);
		backG4.setLayoutX(Screen.getPrimary().getVisualBounds().getWidth()-1686);
		backG4.setLayoutY(Screen.getPrimary().getVisualBounds().getHeight()-666);
		g.getChildren().add(backG4);
		// Start button end
		// help button start
		Font font = Font.font("Algerian", FontWeight.BOLD, 25);
		Text text = new Text(30, 110,
				"\n                                                                                              Welcome to The last of US! \n"
						+ "• The game is a single player survival game set in a zombie apocalyptic world.\r\n"
						+ "• To start the game, you must choose one of the heroes.\r\n"
						+ "• There are several types of heroes available in the game, each one provides different assets for the player to win the game.\r\n"
						+ "• You will be controlling only one hero, but you can gain additional heroes by curing zombies.\r\n"
						+ "• There are zombies in the game that threaten the player during the game. Zombies could only be cured or attacked. Each time a zombie is killed another zombie will spawn somewhere on the map. In addition to extra zombies spawning every time the player ends a turn. Whenever a zombie is cured an extra hero will take its place and be available for the player to use in future turns.\r\n"
						+ "• Any character has an initial amount of health that decreases whenever they are attacked. If the character’s health ever reaches 0, they are killed and removed from the game.\r\n"
						+ "• There are collectibles that are scattered across the map that can help the player survive and advance in the game. Each collectible is only usable once, and after being discarded from the hero’s inventory it cannot be reused.\r\n"
						+ "• There are 2 types of collectibles that the player can use:\r\n"
						+ "1. Vaccines that are the only mean through which players can cure zombies and recruit new heroes.\r\n"
						+ "2. Supplies that allow the heroes to use their special actions.\r\n"
						+ "• The game starts off with just one hero and 10 zombies in the map. The player can only see the directly adjacent cells next to their pool of heroes. The player then keeps taking his turn trying to collect vaccines, and cure or kill zombies. The game ends when the player has collected and used all vaccines or when all heroes have been overwhelmed and defeated by the zombies. The player only wins if he has successfully collected and used all vaccines and has 5 or more heroes alive. \n\n\n\n\n");
		text.setFont(font);
		text.setFill(Color.DARKRED);
		TextFlow textFlow = new TextFlow(text);
		textFlow.setPrefWidth(Screen.getPrimary().getVisualBounds().getWidth());
		Group h = new Group(textFlow);
		Scene helper = new Scene(h, Color.BLACK);
		Image helpButtom = new Image("Help.png");
		ImageView helpButtomView = new ImageView(helpButtom);
		helpButtomView.setFitHeight(80);
		Button help = new Button();
		help.setLayoutX(Screen.getPrimary().getVisualBounds().getWidth()-916);
		help.setLayoutY(Screen.getPrimary().getVisualBounds().getHeight()-416);

		help.setGraphic(helpButtomView);
		help.setBackground(null);
		help.setOnAction(e -> {
			fadeOut(root);
			stage.setScene(helper);
			//stage.setFullScreen(true);
			fadeIn(h);
		});
		root.getChildren().add(help);
		// back button 3
		Image backButtom3 = new Image("back.jpg");
		ImageView backButtomView3 = new ImageView(backButtom3);
		backButtomView3.setFitHeight(50);
		backButtomView3.setFitWidth(50);
		backButtomView3.setFitHeight(80);
		Button back13 = new Button();
		back13.setLayoutX(0);
		back13.setLayoutY(0);
		back13.setGraphic(backButtomView3);
		back13.setBackground(null);
		back13.setOnAction(e -> {
			fadeOut(h);
			stage.setScene(startPage);
			//stage.setFullScreen(true);
			fadeIn(root);
		});
		h.getChildren().add(back13);
		// help button end
		// credits button start
		Image credits = new Image("Credits.png");
		ImageView creditsView = new ImageView(credits);
		creditsView.setFitHeight(80);
		Button Credits = new Button();
		Credits.setLayoutX(Screen.getPrimary().getVisualBounds().getWidth()-986);
		Credits.setLayoutY(Screen.getPrimary().getVisualBounds().getHeight()-216);

		Credits.setGraphic(creditsView);
		Credits.setBackground(null);
		Credits.setOnAction(e -> {
			fadeOut(root);
			stage.setScene(creditss);
			//stage.setFullScreen(true);
			fadeIn(cr);
		});
		root.getChildren().add(Credits);

		Image cre = new Image("credits-1.png");
		ImageView creV = new ImageView(cre);
		creV.setFitHeight(Screen.getPrimary().getVisualBounds().getHeight()+50);
		cr.getChildren().add(creV);
		// credits button end
		//credits backButton
		Image backcr = new Image("back.jpg");
		ImageView backcrView3 = new ImageView(backcr);
		backcrView3.setFitHeight(50);
		backcrView3.setFitWidth(50);
		backcrView3.setFitHeight(80);
		Button backcr13 = new Button();
		backcr13.setLayoutX(0);
		backcr13.setLayoutY(0);
		backcr13.setGraphic(backcrView3);
		backcr13.setBackground(null);
		backcr13.setOnAction(e -> {
			fadeOut(cr);
			stage.setScene(startPage);
			//stage.setFullScreen(true);
			fadeIn(root);
		});
		cr.getChildren().add(backcr13);
		
		stage.getIcons().add(icon);

		// background2
		/*Image back2 = new Image("background2.jpg");
		ImageView backG2 = new ImageView(back2);
		backG2.setFitWidth(Screen.getPrimary().getVisualBounds().getWidth());
		backG2.setFitHeight(Screen.getPrimary().getVisualBounds().getHeight() + 50);
		root2.getChildren().add(backG2);*/
		//
		// backbuttom
		/*Image backButtom = new Image("back.jpg");
		ImageView backButtomView = new ImageView(backButtom);
		backButtomView.setFitHeight(50);
		backButtomView.setFitWidth(50);

		Button back1 = new Button();
		back1.setLayoutX(0);
		back1.setLayoutY(0);
		back1.setGraphic(backButtomView);
		back1.setBackground(null);
		back1.setOnAction(e -> {
			fadeOut(root2);
			stage.setScene(startPage);
			fadeIn(root);
			stage.setFullScreen(true);
		});
		root2.getChildren().add(back1);*/
		// single buttom
		/*Image single = new Image("single.png");
		ImageView singleView = new ImageView(single);
		singleView.setFitHeight(80);
		Button Single = new Button();
		Single.setLayoutX(450);
		Single.setLayoutY(300);

		Single.setGraphic(singleView);
		Single.setBackground(null);
		Single.setOnAction(e -> {
			fadeOut(root2);
			stage.setScene(ChoosePlayer);
			stage.setFullScreen(true);
			fadeIn(root3);
			try {
				engine.Game.loadHeroes("Heroes.csv");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});

		root2.getChildren().add(Single);*/
		// multi buttom
		/*Image multi = new Image("multi.png");
		ImageView multiView = new ImageView(multi);
		multiView.setFitHeight(80);
		Button Multi = new Button();
		Multi.setLayoutX(480);
		Multi.setLayoutY(500);

		Multi.setGraphic(multiView);
		Multi.setBackground(null);
		Multi.setOnAction(e -> System.out.println("x"));
		root2.getChildren().add(Multi);*/
		// background 3
		Image back3 = new Image("background3.jpg");
		ImageView backG3 = new ImageView(back3);
		backG3.setFitWidth(Screen.getPrimary().getVisualBounds().getWidth());
		backG3.setFitHeight(Screen.getPrimary().getVisualBounds().getHeight() + 50);
		root3.getChildren().add(backG3);
		// back button 2
		Image backButtom2 = new Image("back.jpg");
		ImageView backButtomView2 = new ImageView(backButtom2);
		backButtomView2.setFitHeight(50);
		backButtomView2.setFitWidth(50);
		backButtomView2.setFitHeight(80);
		Button back12 = new Button();
		back12.setLayoutX(0);
		back12.setLayoutY(0);
		back12.setGraphic(backButtomView2);
		back12.setBackground(null);
		back12.setOnAction(e -> {
			fadeOut(root3);
			stage.setScene(startPage);
			//stage.setFullScreen(true);
			fadeIn(root);
		});
		root3.getChildren().add(back12);
		// layout

		TilePane layout = new TilePane();
		layout.setPrefColumns(4);
		// Joel button
		Image joelAtt = new Image("Joel att.png");
		ImageView jAttView = new ImageView(joelAtt);
		Image joel = new Image("Joel.png");
		ImageView joelView = new ImageView(joel);
		joelView.setFitHeight(250);
		joelView.setFitWidth(250);
		jAttView.setFitHeight(250);
		jAttView.setFitWidth(250);
		Button Joel = new Button();

		Joel.graphicProperty().bind(Bindings.when(Joel.hoverProperty()).then(jAttView).otherwise(joelView));
		Joel.setBackground(null);
		Joel.setOnAction(e -> {
			fadeOut(root3);
			stage.setScene(Game);
			mediaPlayer.stop();
			stage.setFullScreen(true);
			fadeIn(g);
			engine.Game.startGame(engine.Game.availableHeroes.get(0));
			makeMaps();
		});

		layout.getChildren().add(Joel);
		// Ellie button
		Image ellieAtt = new Image("Ellie att.png");
		ImageView eAttView = new ImageView(ellieAtt);
		Image ellie = new Image("Ellie.png");
		ImageView ellieView = new ImageView(ellie);
		ellieView.setFitHeight(250);
		ellieView.setFitWidth(250);
		eAttView.setFitHeight(250);
		eAttView.setFitWidth(250);
		Button Ellie = new Button();

		Ellie.setLayoutX(Screen.getPrimary().getVisualBounds().getWidth()-1036);
		Ellie.setLayoutY(Screen.getPrimary().getVisualBounds().getHeight()-416);

		Ellie.graphicProperty().bind(Bindings.when(Ellie.hoverProperty()).then(eAttView).otherwise(ellieView));
		Ellie.setBackground(null);
		Ellie.setOnAction(e -> {
			fadeOut(root3);
			stage.setScene(Game);
			mediaPlayer.stop();
			stage.setFullScreen(true);
			fadeIn(g);
			engine.Game.startGame(engine.Game.availableHeroes.get(1));
			makeMaps();
		});
		layout.getChildren().add(Ellie);
		// Bill button
		Image billAtt = new Image("Bill att.png");
		ImageView bAttView = new ImageView(billAtt);
		Image bill = new Image("Bill.png");
		ImageView billView = new ImageView(bill);
		billView.setFitHeight(250);
		billView.setFitWidth(250);
		bAttView.setFitHeight(250);
		bAttView.setFitWidth(250);
		Button Bill = new Button();

		Bill.setLayoutX(Screen.getPrimary().getVisualBounds().getWidth()-1036);
		Bill.setLayoutY(Screen.getPrimary().getVisualBounds().getHeight()-116);

		Bill.graphicProperty().bind(Bindings.when(Bill.hoverProperty()).then(bAttView).otherwise(billView));
		Bill.setBackground(null);
		Bill.setOnAction(e -> {
			fadeOut(root3);
			stage.setScene(Game);
			mediaPlayer.stop();
			stage.setFullScreen(true);
			fadeIn(g);
			engine.Game.startGame(engine.Game.availableHeroes.get(5));
			makeMaps();
		});
		layout.getChildren().add(Bill);
		// David button
		Image davidAtt = new Image("David att.png");
		ImageView dAttView = new ImageView(davidAtt);
		Image david = new Image("David.png");
		ImageView davidView = new ImageView(david);
		davidView.setFitHeight(250);
		davidView.setFitWidth(250);
		dAttView.setFitHeight(250);
		dAttView.setFitWidth(250);
		Button David = new Button();

		David.graphicProperty().bind(Bindings.when(David.hoverProperty()).then(dAttView).otherwise(davidView));
		David.setBackground(null);
		David.setOnAction(e -> {
			fadeOut(root3);
			stage.setScene(Game);
			mediaPlayer.stop();
			stage.setFullScreen(true);
			fadeIn(g);
			engine.Game.startGame(engine.Game.availableHeroes.get(6));
			makeMaps();
		});
		layout.getChildren().add(David);

		// Henry button
		Image henryAtt = new Image("Henry att.png");
		ImageView hAttView = new ImageView(henryAtt);
		Image henry = new Image("Heny.png");
		ImageView henryView = new ImageView(henry);
		henryView.setFitHeight(250);
		henryView.setFitWidth(250);
		hAttView.setFitHeight(250);
		hAttView.setFitWidth(250);
		Button Henry = new Button();

		Henry.graphicProperty().bind(Bindings.when(Henry.hoverProperty()).then(hAttView).otherwise(henryView));
		Henry.setBackground(null);
		Henry.setOnAction(e -> {
			fadeOut(root3);
			stage.setScene(Game);
			mediaPlayer.stop();
			stage.setFullScreen(true);
			fadeIn(g);
			engine.Game.startGame(engine.Game.availableHeroes.get(7));
			makeMaps();
		});
		layout.getChildren().add(Henry);
		// Riley button
		Image rileyAtt = new Image("Riley att.png");
		ImageView rAttView = new ImageView(rileyAtt);
		Image riley = new Image("Riley.png");
		ImageView rileyView = new ImageView(riley);
		rileyView.setFitHeight(250);
		rileyView.setFitWidth(250);
		rAttView.setFitHeight(250);
		rAttView.setFitWidth(250);
		Button Riley = new Button();

		Riley.graphicProperty().bind(Bindings.when(Riley.hoverProperty()).then(rAttView).otherwise(rileyView));
		Riley.setBackground(null);
		Riley.setOnAction(e -> {
			fadeOut(root3);
			stage.setScene(Game);
			mediaPlayer.stop();
			stage.setFullScreen(true);
			fadeIn(g);
			engine.Game.startGame(engine.Game.availableHeroes.get(3));
			makeMaps();
		});
		layout.getChildren().add(Riley);

		// Tess button
		Image tessAtt = new Image("Tess att.png");
		ImageView tAttView = new ImageView(tessAtt);
		Image tess = new Image("Tess.png");
		ImageView tessView = new ImageView(tess);
		tessView.setFitHeight(250);
		tessView.setFitWidth(250);
		tAttView.setFitHeight(250);
		tAttView.setFitWidth(250);
		Button Tess = new Button();

		Tess.graphicProperty().bind(Bindings.when(Tess.hoverProperty()).then(tAttView).otherwise(tessView));
		Tess.setBackground(null);
		Tess.setOnAction(e -> {
			fadeOut(root3);
			stage.setScene(Game);
			mediaPlayer.stop();
			stage.setFullScreen(true);
			fadeIn(g);
			engine.Game.startGame(engine.Game.availableHeroes.get(2));
			makeMaps();
		});
		layout.getChildren().add(Tess);

		// Tommy button
		Image tommyAtt = new Image("Tommy att.png");
		ImageView toAttView = new ImageView(tommyAtt);
		Image tommy = new Image("Tommy.png");
		ImageView tommyView = new ImageView(tommy);
		tommyView.setFitHeight(250);
		tommyView.setFitWidth(250);
		toAttView.setFitHeight(250);
		toAttView.setFitWidth(250);
		Button Tommy = new Button();

		Tommy.graphicProperty().bind(Bindings.when(Tommy.hoverProperty()).then(toAttView).otherwise(tommyView));
		Tommy.setBackground(null);
		Tommy.setOnAction(e -> {
			fadeOut(root3);
			stage.setScene(Game);
			mediaPlayer.stop();
			stage.setFullScreen(true);
			fadeIn(g);
			engine.Game.startGame(engine.Game.availableHeroes.get(4));
			makeMaps();
		});
		layout.getChildren().add(Tommy);
		layout.setLayoutX(Screen.getPrimary().getVisualBounds().getWidth()-1336);
		layout.setLayoutY(Screen.getPrimary().getVisualBounds().getHeight()-666);
		root3.getChildren().add(layout);
		g.getChildren().add(maps);
		Image menuI = new Image("pause.png");
		ImageView menuIV = new ImageView(menuI);
		Button m = new Button();
		m.setScaleY(0.2);
		m.setScaleX(0.3);
		m.setLayoutY(Screen.getPrimary().getVisualBounds().getHeight()-876);
		m.setLayoutX(Screen.getPrimary().getVisualBounds().getWidth()-1556);
		m.setGraphic(menuIV);
		m.setBackground(null);
		ContextMenu menu = menu(stage, startPage);
		m.setOnAction(event -> menu.show(stage));
		g.getChildren().add(m);
		// keyboard handler
		Game.setOnKeyPressed(event -> {
			if (selected == null) {
				Tooltip t = createTooltip("Please choose a character!");
				t.show(stage);
			}
			if (usedHero instanceof Hero && usedHero.getCurrentHp()>0) {
				switch (event.getCode()) {

				case W:
					try {
						
						if (usedHero.getActionsAvailable()>0&&engine.Game.map[usedHero.getLocation().x + 1][usedHero
								.getLocation().y] instanceof TrapCell) {
							Tooltip trapMessage = createTooltip("You entered a trap cell!");
							trapMessage.show(stage);
							trapSoundPlayer.stop();
							trapSoundPlayer.seek(trapSoundPlayer.getStartTime());
							trapSoundPlayer.play();
						}
						ImageView iV = (ImageView) ((ToggleButton) Main.maps.getChildren()
								.get((14 - usedHero.getLocation().x) * 15 + usedHero.getLocation().y)).getGraphic();
						usedHero.move(Direction.UP);
						moveSoundPlayer.stop();
						moveSoundPlayer.seek(moveSoundPlayer.getStartTime());
						moveSoundPlayer.play();
						GameOver(stage);
						win(stage);
						((ToggleButton) Main.maps.getChildren()
								.get((14 - usedHero.getLocation().x) * 15 + usedHero.getLocation().y)).setGraphic(iV);
						updateMaps();
						if (g.getChildren().contains(healthBar))
							g.getChildren().remove(healthBar);
						healthBar = setHealthBar();
						g.getChildren().add(healthBar);
						if (g.getChildren().contains(attributes))
							g.getChildren().remove(attributes);
						attributes = attributes();
						g.getChildren().add(attributes);
						GameOver(stage);
						win(stage);
						break;
					} catch (MovementException e) {

						if (g.getChildren().contains(healthBar))
							g.getChildren().remove(healthBar);
						healthBar = setHealthBar();
						g.getChildren().add(healthBar);
						Tooltip t = createTooltip(e.getMessage());
						t.show(stage);
						// showAlert(e.getMessage()); // AlertBox REMAINING
						break;
					} catch (NotEnoughActionsException e) {
						if (g.getChildren().contains(healthBar))
							g.getChildren().remove(healthBar);
						healthBar = setHealthBar();
						g.getChildren().add(healthBar);

						Tooltip t = createTooltip(e.getMessage());
						t.show(stage);// ALERTBOX REMAINING
						break;
					}
				case S:
					try {
						
						if (usedHero.getActionsAvailable()>0 && engine.Game.map[usedHero.getLocation().x - 1][usedHero
								.getLocation().y] instanceof TrapCell) {
							Tooltip trapMessage = createTooltip("You entered a trap cell!");
							trapMessage.show(stage);
							trapSoundPlayer.stop();
							trapSoundPlayer.seek(trapSoundPlayer.getStartTime());
							trapSoundPlayer.play();
						}
						ImageView iV = (ImageView) ((ToggleButton) Main.maps.getChildren()
								.get((14 - usedHero.getLocation().x) * 15 + usedHero.getLocation().y)).getGraphic();
						usedHero.move(Direction.DOWN);
						moveSoundPlayer.stop();
						moveSoundPlayer.seek(moveSoundPlayer.getStartTime());
						moveSoundPlayer.play();
						((ToggleButton) Main.maps.getChildren()
								.get((14 - usedHero.getLocation().x) * 15 + usedHero.getLocation().y)).setGraphic(iV);
						updateMaps();
						if (g.getChildren().contains(healthBar))
							g.getChildren().remove(healthBar);
						healthBar = setHealthBar();
						g.getChildren().add(healthBar);
						if (g.getChildren().contains(attributes))
							g.getChildren().remove(attributes);
						attributes = attributes();
						g.getChildren().add(attributes);
						GameOver(stage);
						win(stage);
						break;
					} catch (MovementException e) {
						if (g.getChildren().contains(healthBar))
							g.getChildren().remove(healthBar);
						healthBar = setHealthBar();
						g.getChildren().add(healthBar);

						Tooltip t = createTooltip(e.getMessage());
						t.show(stage);// AlertBox REMAINING
						break;
					} catch (NotEnoughActionsException e) {
						if (g.getChildren().contains(healthBar))
							g.getChildren().remove(healthBar);
						healthBar = setHealthBar();
						g.getChildren().add(healthBar);

						Tooltip t = createTooltip(e.getMessage());
						t.show(stage);
						break;// ALERTBOX REMAINING
					}
				case D:
					try {
						
						if (usedHero.getActionsAvailable()>0 && engine.Game.map[usedHero.getLocation().x][usedHero.getLocation().y
								+ 1] instanceof TrapCell) {
							Tooltip trapMessage = createTooltip("You entered a trap cell!");
							trapMessage.show(stage);
							trapSoundPlayer.stop();
							trapSoundPlayer.seek(trapSoundPlayer.getStartTime());
							trapSoundPlayer.play();
						}
						ImageView iV = (ImageView) ((ToggleButton) Main.maps.getChildren()
								.get((14 - usedHero.getLocation().x) * 15 + usedHero.getLocation().y)).getGraphic();
						usedHero.move(Direction.RIGHT);
						moveSoundPlayer.stop();
						moveSoundPlayer.seek(moveSoundPlayer.getStartTime());
						moveSoundPlayer.play();
						((ToggleButton) Main.maps.getChildren()
								.get((14 - usedHero.getLocation().x) * 15 + usedHero.getLocation().y)).setGraphic(iV);
						updateMaps();
						if (g.getChildren().contains(healthBar))
							g.getChildren().remove(healthBar);
						healthBar = setHealthBar();
						g.getChildren().add(healthBar);
						if (g.getChildren().contains(attributes))
							g.getChildren().remove(attributes);
						attributes = attributes();
						g.getChildren().add(attributes);
						GameOver(stage);
						win(stage);
						break;
					} catch (MovementException e) {
						if (g.getChildren().contains(healthBar))
							g.getChildren().remove(healthBar);
						healthBar = setHealthBar();
						g.getChildren().add(healthBar);

						Tooltip t = createTooltip(e.getMessage());
						t.show(stage);
						break;// AlertBox REMAINING
					} catch (NotEnoughActionsException e) {
						if (g.getChildren().contains(healthBar))
							g.getChildren().remove(healthBar);
						healthBar = setHealthBar();
						g.getChildren().add(healthBar);

						Tooltip t = createTooltip(e.getMessage());
						t.show(stage);
						break;// ALERTBOX REMAINING
					}
				case A:
					try {
						
						if (usedHero.getActionsAvailable()>0 && engine.Game.map[usedHero.getLocation().x][usedHero.getLocation().y
								- 1] instanceof TrapCell) {
							Tooltip trapMessage = createTooltip("You entered a trap cell!");
							trapMessage.show(stage);
							trapSoundPlayer.stop();
							trapSoundPlayer.seek(trapSoundPlayer.getStartTime());
							trapSoundPlayer.play();
						}
						ImageView iV = (ImageView) ((ToggleButton) Main.maps.getChildren()
								.get((14 - usedHero.getLocation().x) * 15 + usedHero.getLocation().y)).getGraphic();
						usedHero.move(Direction.LEFT);
						moveSoundPlayer.stop();
						moveSoundPlayer.seek(moveSoundPlayer.getStartTime());
						moveSoundPlayer.play();
						((ToggleButton) Main.maps.getChildren()
								.get((14 - usedHero.getLocation().x) * 15 + usedHero.getLocation().y)).setGraphic(iV);
						updateMaps();
						if (g.getChildren().contains(healthBar))
							g.getChildren().remove(healthBar);
						healthBar = setHealthBar();
						g.getChildren().add(healthBar);
						if (g.getChildren().contains(attributes))
							g.getChildren().remove(attributes);
						attributes = attributes();
						g.getChildren().add(attributes);
						GameOver(stage);
						win(stage);
						break;
					} catch (MovementException e) {
						if (g.getChildren().contains(healthBar))
							g.getChildren().remove(healthBar);
						healthBar = setHealthBar();
						g.getChildren().add(healthBar);
						Tooltip t = createTooltip(e.getMessage());
						t.show(stage);
						break;// AlertBox REMAINING
					} catch (NotEnoughActionsException e) {
						if (g.getChildren().contains(healthBar))
							g.getChildren().remove(healthBar);
						healthBar = setHealthBar();
						g.getChildren().add(healthBar);
						Tooltip t = createTooltip(e.getMessage());
						t.show(stage);
						break;// ALERTBOX REMAINING
					}
				case T:
					target = true;
					
					if (g.getChildren().contains(healthBar))
						g.getChildren().remove(healthBar);
					healthBar = setHealthBar();
					g.getChildren().add(healthBar);
					if (g.getChildren().contains(attributes))
						g.getChildren().remove(attributes);
					attributes = attributes();
					g.getChildren().add(attributes);
					GameOver(stage);
					win(stage);
					break;
				case Q:
					try {
						usedHero.attack();
						attackSoundPlayer.stop();
						attackSoundPlayer.seek(attackSoundPlayer.getStartTime());
						attackSoundPlayer.play();
						updateMaps();
						if (g.getChildren().contains(healthBar))
							g.getChildren().remove(healthBar);
						healthBar = setHealthBar();
						g.getChildren().add(healthBar);
						if (g.getChildren().contains(attributes))
							g.getChildren().remove(attributes);
						attributes = attributes();
						g.getChildren().add(attributes);
						GameOver(stage);
						win(stage);
						break;
					} catch (NotEnoughActionsException e1) {
						if (g.getChildren().contains(healthBar))
							g.getChildren().remove(healthBar);
						healthBar = setHealthBar();
						g.getChildren().add(healthBar);
						Tooltip t = createTooltip(e1.getMessage());
						t.show(stage);
						// ALERTBOX REM
						break;
					} catch (InvalidTargetException e1) {
						if (g.getChildren().contains(healthBar))
							g.getChildren().remove(healthBar);
						healthBar = setHealthBar();
						g.getChildren().add(healthBar);
						Tooltip t = createTooltip(e1.getMessage());
						t.show(stage);
						// ALERTBOX REM
						break;
					}
				case C:
					try {
						Cell c = engine.Game.map[usedHero.getTarget().getLocation().x][usedHero.getTarget()
								.getLocation().y];
						usedHero.cure();
						vaccineSoundPlayer.stop();
						vaccineSoundPlayer.seek(vaccineSoundPlayer.getStartTime());
						vaccineSoundPlayer.play();
						selected = engine.Game.heroes.get(engine.Game.heroes.size() - 1);
						if (g.getChildren().contains(attributes))
							g.getChildren().remove(attributes);
						attributes = attributes();
						g.getChildren().add(attributes);
						// Cell c =
						// engine.Game.map[usedHero.getTarget().getLocation().x][usedHero.getTarget().getLocation().y];
						if (g.getChildren().contains(healthBar))
							g.getChildren().remove(healthBar);
						healthBar = setHealthBar();
						updateMaps();
						g.getChildren().add(healthBar);
						if (g.getChildren().contains(charView)) {
							g.getChildren().remove(charView);
							showImg((CharacterCell) c);
						}
						GameOver(stage);
						win(stage);
						selected = null;
						usedHero = null;
						break;
					} catch (NoAvailableResourcesException e1) {
						if (g.getChildren().contains(healthBar))
							g.getChildren().remove(healthBar);
						healthBar = setHealthBar();
						g.getChildren().add(healthBar);
						Tooltip t = createTooltip(e1.getMessage());
						t.show(stage);
						// TODO Auto-generated catch block

						break;
					} catch (InvalidTargetException e1) {
						if (g.getChildren().contains(healthBar))
							g.getChildren().remove(healthBar);
						healthBar = setHealthBar();
						g.getChildren().add(healthBar);
						Tooltip t = createTooltip(e1.getMessage());
						t.show(stage);
						// TODO Auto-generated catch block

						break;
					} catch (NotEnoughActionsException e1) {
						if (g.getChildren().contains(healthBar))
							g.getChildren().remove(healthBar);
						healthBar = setHealthBar();
						g.getChildren().add(healthBar);
						Tooltip t = createTooltip(e1.getMessage());
						t.show(stage);
						// TODO Auto-generated catch block

						break;
					}
				case U:
					try {
						usedHero.useSpecial();
						useSoundPlayer.stop();
						useSoundPlayer.seek(useSoundPlayer.getStartTime());
						useSoundPlayer.play();
						if (g.getChildren().contains(healthBar))
							g.getChildren().remove(healthBar);
						healthBar = setHealthBar();
						updateMaps();
						g.getChildren().add(healthBar);
						if (g.getChildren().contains(attributes))
							g.getChildren().remove(attributes);
						attributes = attributes();
						g.getChildren().add(attributes);
						GameOver(stage);
						win(stage);
						break;
					} catch (NoAvailableResourcesException e1) {
						if (g.getChildren().contains(healthBar))
							g.getChildren().remove(healthBar);
						healthBar = setHealthBar();
						g.getChildren().add(healthBar);
						Tooltip t = createTooltip(e1.getMessage());
						t.show(stage);
						// TODO Auto-generated catch block
						break;
					} catch (InvalidTargetException e1) {
						if (g.getChildren().contains(healthBar))
							g.getChildren().remove(healthBar);
						healthBar = setHealthBar();
						g.getChildren().add(healthBar);
						Tooltip t = createTooltip(e1.getMessage());
						t.show(stage);
						// TODO Auto-generated catch block
						break;
					}
				case E:
					try {
						engine.Game.endTurn();
						endSoundPlayer.stop();
						endSoundPlayer.seek(endSoundPlayer.getStartTime());
						endSoundPlayer.play();
						if (g.getChildren().contains(healthBar))
							g.getChildren().remove(healthBar);
						updateMaps();
						healthBar = setHealthBar();
						g.getChildren().add(healthBar);
						if (g.getChildren().contains(attributes))
							g.getChildren().remove(attributes);
						attributes = attributes();
						g.getChildren().add(attributes);
						GameOver(stage);
						win(stage);
					} catch (NotEnoughActionsException e1) {
						if (g.getChildren().contains(healthBar))
							g.getChildren().remove(healthBar);
						healthBar = setHealthBar();
						g.getChildren().add(healthBar);
						Tooltip t = createTooltip(e1.getMessage());
						t.show(stage);
						// ALERTBOX
					} catch (InvalidTargetException e1) {
						if (g.getChildren().contains(healthBar))
							g.getChildren().remove(healthBar);
						healthBar = setHealthBar();
						g.getChildren().add(healthBar);
						Tooltip t = createTooltip(e1.getMessage());
						t.show(stage);
						// ALERTBOX
					} catch (ConcurrentModificationException e) {

					}
				default:
					break;
				}
			} else {
				createTooltip("Please choose a Hero!");
			}

		});
		Image sh = new Image("heroes.png");
		ImageView shv = new ImageView(sh);
		Button shvb = new Button();
		shvb.setGraphic(shv);
		shvb.setOnAction(e -> {
			Tooltip showHeroes = showHeroes();
			showHeroes.show(stage);});
		
		shvb.setBackground(null);
		shvb.setLayoutX(Screen.getPrimary().getVisualBounds().getWidth()-386);
		shvb.setLayoutY(Screen.getPrimary().getVisualBounds().getHeight()-836);
		shvb.setScaleX(0.3);
		shvb.setScaleY(0.5);
		g.getChildren().add(shvb);
		stage.show();
	}

	public static void makeMaps() {

		for (int row = 0; row < 15; row++) {
			for (int col = 0; col < 15; col++) {
				ToggleButton tile = new ToggleButton();
				tile.setPrefSize(70, 55);
				if ((row + col) % 2 == 0) {
					tile.setStyle("-fx-background-color: lightblue; ");
				} else {
					tile.setStyle("-fx-background-color: black; ");
				}
				buttons[row][col] = tile;
				tile.setOnAction(e -> {
					for (ToggleButton[] r : buttons) {
						for (ToggleButton c : r) {
							c.setEffect(null);
							if (g.getChildren().contains(healthBar))
								g.getChildren().remove(healthBar);
							if (g.getChildren().contains(charView))
								g.getChildren().remove(charView);
							if (g.getChildren().contains(attributes))
								g.getChildren().remove(attributes);
						}
					}
					;
					if (tile.isSelected()) {
						// Add effects when the tile is selected
						tile.setEffect(new Lighting());
					} else {
						// Remove effects when the tile is deselected
						tile.setEffect(null);
					}
					Point p = getButton(tile);
					int x = 14 - p.x;
					int y = p.y;
					if (Game.map[x][y] instanceof CharacterCell
							&& ((CharacterCell) Game.map[x][y]).getCharacter() != null && Game.map[x][y].isVisible()) {
						healthBar = healthBar((CharacterCell) Game.map[x][y]);
						g.getChildren().add(healthBar);
						showImg((CharacterCell) Game.map[x][y]);
						selected = ((CharacterCell) Game.map[x][y]).getCharacter();

						if (target && usedHero != null) {
							usedHero.setTarget(((CharacterCell) Game.map[x][y]).getCharacter());
							target = false;
						} else {
							if (target == true && usedHero == null)
								// ALERTBOX REMAINING
								;
							else {
								if (((CharacterCell) Game.map[x][y]).getCharacter() instanceof Hero)
									usedHero = (Hero) ((CharacterCell) Game.map[x][y]).getCharacter();
							}
						}
						if (g.getChildren().contains(attributes))
							g.getChildren().remove(attributes);
						attributes = attributes();
						g.getChildren().add(attributes);
					} else {
						selected = null;
						usedHero = null;
						target = false;
					}

				});

				maps.add(tile, col, row);
			}
		}
		maps.setGridLinesVisible(true);
		maps.setLayoutX(Screen.getPrimary().getVisualBounds().getWidth()-1286);
		maps.setLayoutY(Screen.getPrimary().getVisualBounds().getHeight()-795);
		updateMaps();
	}

	public static void updateMaps() {

		for (int i = 0; i < 15; i++) {
			for (int j = 0; j < 15; j++) {
				int x = 14 - i;
				if (Game.map[i][j] instanceof CharacterCell
						&& ((CharacterCell) Game.map[i][j]).getCharacter() instanceof Fighter) {
					Image img = new Image("fighter2.png");
					ImageView imgV = new ImageView(img);
					imgV.setFitHeight(47);
					imgV.setFitWidth(55);
					((ToggleButton) Main.maps.getChildren().get(x * 15 + j)).setGraphic(imgV);
				} else {
					if (Game.map[i][j] instanceof CharacterCell
							&& ((CharacterCell) Game.map[i][j]).getCharacter() instanceof Explorer) {
						Image img = new Image("Explorer.png");
						ImageView imgV = new ImageView(img);
						imgV.setFitHeight(47);
						imgV.setFitWidth(55);
						((ToggleButton) Main.maps.getChildren().get(x * 15 + j)).setGraphic(imgV);
					} else {
						if (Game.map[i][j] instanceof CharacterCell
								&& ((CharacterCell) Game.map[i][j]).getCharacter() instanceof Medic) {
							Image img = new Image("medic2.png");
							ImageView imgV = new ImageView(img);
							imgV.setFitHeight(47);
							imgV.setFitWidth(55);
							((ToggleButton) Main.maps.getChildren().get(x * 15 + j)).setGraphic(imgV);
						} else {
							if (Game.map[i][j] instanceof CharacterCell
									&& ((CharacterCell) Game.map[i][j]).getCharacter() instanceof Zombie
									&& Game.map[i][j].isVisible()) {
								Image img;
								if ((i + j) % 2 == 0)
									img = new Image("zombie.png");
								else
									img = new Image("zombie2.png");
								ImageView imgV = new ImageView(img);
								imgV.setFitHeight(47);
								imgV.setFitWidth(55);
								((ToggleButton) Main.maps.getChildren().get(x * 15 + j)).setGraphic(imgV);
							} else {
								if (Game.map[i][j] instanceof CollectibleCell
										&& ((CollectibleCell) Game.map[i][j]).getCollectible() instanceof Vaccine
										&& Game.map[i][j].isVisible()) {
									Image img = new Image("vaccine.png");
									ImageView imgV = new ImageView(img);
									imgV.setFitHeight(50);
									imgV.setFitWidth(55);
									((ToggleButton) Main.maps.getChildren().get(x * 15 + j)).setGraphic(imgV);
								} else {
									if (Game.map[i][j] instanceof CollectibleCell
											&& ((CollectibleCell) Game.map[i][j]).getCollectible() instanceof Supply
											&& Game.map[i][j].isVisible()) {
										Image img = new Image("supply.png");
										ImageView imgV = new ImageView(img);
										imgV.setFitHeight(47);
										imgV.setFitWidth(55);
										((ToggleButton) Main.maps.getChildren().get(x * 15 + j)).setGraphic(imgV);
									} else {

										((ToggleButton) Main.maps.getChildren().get(x * 15 + j)).setGraphic(null);
									}
								}
							}
						}
					}
				}
			}
		}
	}

	public static Rectangle healthBar(CharacterCell c) {
		Rectangle healthBar = new Rectangle(145.0 * ((double)c.getCharacter().getCurrentHp()/c.getCharacter().getMaxHp()), 20);
		// healthBar.setStroke(Color.WHITE);
		// healthBar.setStrokeWidth(2);
		if (c.getCharacter().getCurrentHp() <= 0) {
			healthBar = new Rectangle(0, 0);
			g.getChildren().remove(charView);
		} else {
			if (c.getCharacter().getCurrentHp() <= (0.25 * c.getCharacter().getMaxHp()))
				healthBar.setFill(Color.RED);
			else {
				if (c.getCharacter().getCurrentHp() <= (0.5 * c.getCharacter().getMaxHp()))
					healthBar.setFill(Color.ORANGE);
				else {
					if (c.getCharacter().getCurrentHp() <= (0.75 * c.getCharacter().getMaxHp()))
						healthBar.setFill(Color.YELLOW);
					else {
						healthBar.setFill(Color.GREEN);
					}
				}
			}
		}
		healthBar.setLayoutX(Screen.getPrimary().getVisualBounds().getWidth()-186);
		healthBar.setLayoutY(Screen.getPrimary().getVisualBounds().getHeight()-396);
		return healthBar;
	}

	public static Point getButton(ToggleButton b) {
		for (int i = 0; i < 15; i++) {
			for (int j = 0; j < 15; j++) {
				if (buttons[i][j].equals(b)) {
					return new Point(i, j);
				}
			}
		}
		return new Point(-1, -1);
	}

	public static void showImg(CharacterCell c) {
		Image charImg;
		if (c.getCharacter() instanceof Zombie) {
			charImg = new Image("zombie.png");
			charView = new ImageView(charImg);
		} else {
			switch (c.getCharacter().getName()) {
			case "Joel Miller":
				charImg = new Image("Joel.png");
				charView = new ImageView(charImg);
				break;
			case "Ellie Williams":
				charImg = new Image("Ellie.png");
				charView = new ImageView(charImg);
				break;
			case "Tess":
				charImg = new Image("Tess.png");
				charView = new ImageView(charImg);
				break;
			case "Riley Abel":
				charImg = new Image("Riley.png");
				charView = new ImageView(charImg);
				break;
			case "Tommy Miller":
				charImg = new Image("Tommy.png");
				charView = new ImageView(charImg);
				break;
			case "Bill":
				charImg = new Image("Bill.png");
				charView = new ImageView(charImg);
				break;
			case "David":
				charImg = new Image("David.png");
				charView = new ImageView(charImg);
				break;
			default:
				charImg = new Image("Heny.png");
				charView = new ImageView(charImg);
				break;
			}
		}
		charView.setLayoutX(Screen.getPrimary().getVisualBounds().getWidth()-166);
		charView.setLayoutY(Screen.getPrimary().getVisualBounds().getHeight()-516);
		charView.setFitHeight(100);
		charView.setFitWidth(100);
		g.getChildren().add(charView);
	}

	public static Image setImg(Character c) {
		Image charImg;
		if (c instanceof Zombie) {
			charImg = new Image("zombie.png");

		} else {
			switch (c.getName()) {
			case "Joel Miller":
				charImg = new Image("Joel.png");

				break;
			case "Ellie Williams":
				charImg = new Image("Ellie.png");

				break;
			case "Tess":
				charImg = new Image("Tess.png");

				break;
			case "Riley Abel":
				charImg = new Image("Riley.png");

				break;
			case "Tommy Miller":
				charImg = new Image("Tommy.png");

				break;
			case "Bill":
				charImg = new Image("Bill.png");

				break;
			case "David":
				charImg = new Image("David.png");

				break;
			default:
				charImg = new Image("Heny.png");

				break;
			}
		}
		charView.setLayoutX(Screen.getPrimary().getVisualBounds().getWidth()-186);
		charView.setLayoutY(Screen.getPrimary().getVisualBounds().getHeight()-516);
		charView.setFitHeight(80);
		charView.setFitWidth(100);
		return charImg;
	}

	public static Rectangle setHealthBar() {
		if (!(selected instanceof Character))
			return new Rectangle(0, 0);
		healthBar = new Rectangle(145.0 * ((double)selected.getCurrentHp() / selected.getMaxHp()), 20);
		// Label healthLabel = new Label("Health: " + c.getCurrentHp());
		if (g.getChildren().contains(healthBar))
			g.getChildren().remove(healthBar);
		// healthBar.setStroke(Color.WHITE);
		healthBar.setStrokeWidth(2);
		if (selected.getCurrentHp() <= 0) {
			healthBar = new Rectangle(0, 0);
			g.getChildren().remove(charView);
			g.getChildren().remove(attributes);
			attributes = new TextFlow();
			usedHero = null;
		} else {
			if (selected.getCurrentHp() <= (0.25 * selected.getMaxHp()))
				healthBar.setFill(Color.RED);
			else {
				if (selected.getCurrentHp() <= (0.5 * selected.getMaxHp()))
					healthBar.setFill(Color.ORANGE);
				else {
					if (selected.getCurrentHp() <= (0.75 * selected.getMaxHp()))
						healthBar.setFill(Color.YELLOW);
					else {
						healthBar.setFill(Color.GREEN);
					}
				}
			}
		}
		healthBar.setLayoutX(Screen.getPrimary().getVisualBounds().getWidth()-186);
		healthBar.setLayoutY(Screen.getPrimary().getVisualBounds().getHeight()-396);
		return healthBar;
	}

	public static Tooltip createTooltip(String s) {
		Tooltip tooltip = new Tooltip(s);
		PauseTransition pause = new PauseTransition(Duration.seconds(3));
		pause.setOnFinished(e -> tooltip.hide());
		tooltip.setOnShowing(e -> pause.playFromStart());
		tooltip.setStyle("-fx-font-size: 35; -fx-text-fill: red;");
		tooltip.setFont(Font.font("Algerian"));
		return tooltip;
	}

	public ContextMenu menu(Stage stage, Scene scene) {

		Image resume = new Image("resume.png");
		ImageView resumeView = new ImageView(resume);
		resumeView.setFitHeight(80);
		MenuItem r = new MenuItem();
		MenuItem s = new MenuItem();
		MenuItem h = new MenuItem();
		MenuItem ex = new MenuItem();
		ContextMenu menu = new ContextMenu(r, h, s, ex);
		r.setGraphic(resumeView);
		r.setOnAction(e -> {
			menu.hide();
		});
		Image start = new Image("start.png");
		ImageView startView = new ImageView(start);
		startView.setFitHeight(80);
		MenuItem helpWindow = new MenuItem();
		Image howTo = new Image("howTo.png");
		ImageView howToV = new ImageView(howTo);
		howToV.setFitHeight(800);
		howToV.setFitWidth(1000);
		helpWindow.setGraphic(howToV);
		ContextMenu howWindow = new ContextMenu(helpWindow);
		s.setGraphic(startView);
		s.setOnAction(e -> {
			try {
				reset(stage);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		});
		Image help = new Image("how.png");
		ImageView helpView = new ImageView(help);
		helpView.setFitHeight(80);
		h.setGraphic(helpView);
		h.setOnAction(e -> {
			howWindow.show(stage);
		});
		Image exit = new Image("Exit.png");
		ImageView exitV = new ImageView(exit);
		exitV.setFitHeight(80);
		ex.setGraphic(exitV);
		ex.setOnAction(e-> stage.close());
		menu.setStyle("-fx-background-color: rgba(0, 100, 100, 0.5); -fx-background-radius: 10;");
		menu.setY(280);
		menu.setX(420);
		return menu;

	}

	public static void restart() {
		engine.Game.availableHeroes.clear();
		engine.Game.heroes.clear();
		for (int i = 0; i < 15; i++) {
			for (int j = 0; j < 15; j++) {
				engine.Game.map[i][j] = null;

			}
		}
	}

	public void reset(Stage stage) throws Exception {
		Platform.runLater(() -> {
			try {
				// Create a new instance of your main class
				restart();
				maps = new GridPane();
				g = new Group();
				Main newMain = new Main();
				// Call the start method on the JavaFX application thread
				
				newMain.start(new Stage());
				stage.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	public static TextFlow attributes() {
		Font font = Font.font("Comic Sans MS", FontWeight.BOLD, 15);
		Text text = new Text(30, 110, " Name : " + selected.getName() + "\n CurrentHp : " + selected.getCurrentHp());
		text.setFont(font);
		text.setFill(Color.WHITESMOKE);
		Text text2 = new Text("");
		if (selected instanceof Fighter) {
			text2 = new Text(30, 110,
					"\n Type : Fighter" + "\n Available vaccines : " + ((Hero) selected).getVaccineInventory().size()
							+ "\n Available supplies : " + ((Hero) selected).getSupplyInventory().size() + "\n Available actions : " + ((Hero) selected).getActionsAvailable());
			text2.setFont(font);
			text2.setFill(Color.WHITESMOKE);
		} else {
			if (selected instanceof Medic) {
				text2 = new Text(30, 110,
						"\n Type : Medic" + "\n Available vaccines : " + ((Hero) selected).getVaccineInventory().size()
								+ "\n Available supplies : " + ((Hero) selected).getSupplyInventory().size() + "\n Available actions : " + ((Hero) selected).getActionsAvailable());
				text2.setFont(font);
				text2.setFill(Color.WHITESMOKE);
			} else {
				if (selected instanceof Explorer) {
					text2 = new Text(30, 110,
							"\n Type : Explorer" + "\n Available vaccines : "
									+ ((Hero) selected).getVaccineInventory().size() + "\n Available supplies : "
									+ ((Hero) selected).getSupplyInventory().size() + "\n Available actions : " + ((Hero) selected).getActionsAvailable());
					text2.setFont(font);
					text2.setFill(Color.WHITESMOKE);
				}

			}

		}
		attributes = new TextFlow(text, text2);
		attributes.setLayoutX(Screen.getPrimary().getVisualBounds().getWidth()-196);
		attributes.setLayoutY(Screen.getPrimary().getVisualBounds().getHeight()-346);
		if (selected.getCurrentHp() <= 0)
			return new TextFlow();
		return attributes;

	}

	public static Tooltip showHeroes() {
		Button close = new Button("X");
		close.setBackground(null);
		String s = "";
		for(int i = 0 ; i<engine.Game.heroes.size(); i++) {
			Hero h = engine.Game.heroes.get(i);
			s += (i+1) + "- Name : " + h.getName() + "\t CurrentHp : " + h.getCurrentHp();
			if(h instanceof Fighter) {
				s += "\t Type : Fighter" + "\t Available vaccines : " + ((Hero) h).getVaccineInventory().size()
						+ "\t Available supplies : " + ((Hero) h).getSupplyInventory().size() + "\t Max Actions : " + h.getMaxActions();
			}
			else {
				if(h instanceof Medic) {
					s += "\t Type : Medic" + "\t Available vaccines : " + ((Hero) h).getVaccineInventory().size()
							+ "\t Available supplies : " + ((Hero) h).getSupplyInventory().size()+ "\t Max Actions : " + h.getMaxActions();
				}
				else {
					s += "\t Type : Explorer" + "\t Available vaccines : " + ((Hero) h).getVaccineInventory().size()
							+ "\t Available supplies : " + ((Hero) h).getSupplyInventory().size()+ "\t Max Actions : " + h.getMaxActions();
				}
			}
			s += "\n";
		}
		Tooltip show = new Tooltip(s);
		show.setFont(Font.font("Algerian"));
		show.setStyle("-fx-font-size: 20; -fx-text-fill: red;");
		show.setAutoHide(true);
		return show;
	}
	
	public static void GameOver(Stage stage) {
		if(engine.Game.checkGameOver()) {
			gameOverSoundPlayer.stop();
			gameOverSoundPlayer.seek(gameOverSoundPlayer.getStartTime());
			gameOverSoundPlayer.play();
			Tooltip GameOver = new Tooltip();
			PauseTransition pause = new PauseTransition(Duration.seconds(3));
			pause.setOnFinished(e ->{
				Platform.runLater(() -> {
					try {
						// Create a new instance of your main class
						restart();
						maps = new GridPane();
						g = new Group();
						Main newMain = new Main();
						// Call the start method on the JavaFX application thread
						newMain.start(new Stage());
						stage.close();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				});
			});
			Image over = new Image("over.png");
			ImageView overV = new ImageView(over);
			GameOver.setGraphic(overV);
			GameOver.setStyle("-fx-background-color: transparent");
			GameOver.setOnShowing(e -> pause.playFromStart());
			GameOver.show(stage,300,250);
		}
	}
	public static void win(Stage stage) {
		if(engine.Game.checkWin()) {
			winSoundPlayer.stop();
			winSoundPlayer.seek(winSoundPlayer.getStartTime());
			winSoundPlayer.play();
			Tooltip win = new Tooltip();
			PauseTransition pause = new PauseTransition(Duration.seconds(2));
			Tooltip end = new Tooltip();
			pause.setOnFinished(e-> {
				win.hide();
				end.show(stage,270,350);
			});
			PauseTransition pause2 = new PauseTransition(Duration.seconds(2));
			pause2.setOnFinished(e ->{
				Platform.runLater(() -> {
					try {
						restart();
						// Create a new instance of your main class
						maps = new GridPane();
						g = new Group();
						Main newMain = new Main();
						// Call the start method on the JavaFX application thread
						newMain.start(new Stage());
						stage.close();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				});
			});
			Image endr = new Image("end.png");
			ImageView endV = new ImageView(endr);
			endV.setFitWidth(1000);
			end.setGraphic(endV);
			end.setStyle("-fx-background-color: transparent");
			end.setOnShowing(e -> pause2.playFromStart());
			Image winr = new Image("won.png");
			ImageView winV = new ImageView(winr);
			win.setGraphic(winV);
			win.setStyle("-fx-background-color: transparent");
			win.setOnShowing(e -> pause.playFromStart());
			win.show(stage,300,250);
		}
	}
	
	
}