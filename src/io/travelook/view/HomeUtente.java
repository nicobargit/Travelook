package io.travelook.view;		
import java.io.File;
import java.io.IOException;
import java.util.List;

import io.travelook.controller.annuncio.ListaAnnunciController;
import io.travelook.controller.utente.UtenteController;
import io.travelook.model.Recensione;
import io.travelook.model.RichiestaDiPartecipazione;
import io.travelook.model.Utente;
import io.travelook.model.Viaggio;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class HomeUtente extends Application {
	private Stage primaryStage;
	@FXML
    private FlowPane rootLayout;
    @FXML
    private ListView<Viaggio> listViaggio;
    @FXML
    private ListView<Viaggio> listRecensioni;
    @FXML
    private ListView<RichiestaDiPartecipazione> listRichieste;
    @FXML
    private Button back;
    @FXML
    private Button modifica;
    @FXML
    private Text username;
    @FXML
    private Text nomeCognome;
    @FXML
    private Text email;
    @FXML
    private Text interessi;
    @FXML
    private SVGPath star1;
    @FXML
    private SVGPath star2;
    @FXML
    private SVGPath star3;
    @FXML
    private SVGPath star4;
    @FXML
    private SVGPath star5;
    @FXML
    private Button showStorico;
    @FXML
    private Button showRichieste;
    @FXML
    private Button showViaggi;
    @FXML
    private Button showRecensioni;
    @FXML
    private ImageView userImage;
    @FXML
    private Button refresh;
    @FXML
    private Button creaAnnuncio;
    private Utente user;
    private UtenteController uc;
    private List<Recensione> listaRecensioni;
    private List<Viaggio> listaViaggio;
    private List<RichiestaDiPartecipazione> listaRichieste;
    private FXMLLoader loader;
	public HomeUtente(Utente user) {
        this.user = user;
        uc = new UtenteController(user);
	}
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Travelook");

        initRootLayout();
	}

	public static void main(String[] args) {
		launch(args);
	}
	
	public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            //FXMLLoader loader = new FXMLLoader();
            loader = new FXMLLoader(getClass().getResource("HomeUtente.fxml"));
            loader.setController(this);
            loader.load();
            //loader.setLocation(HomeUtente.class.getResource("HomeUtente.fxml"));
            //rootLayout = (FlowPane) loader.load();
            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            listaRecensioni = uc.visualizzaRecensioni();
            back.setOnMouseClicked(event -> {
            	new HomeListaAnnunci(user).start(primaryStage);
            });
            star1.setFill(Paint.valueOf("orange"));
       
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	public Stage getPrimaryStage() {
        return primaryStage;
    }
	public void initStars() {
		int sum = 0;
		Double average = new Double(0);
		for(Recensione r : listaRecensioni) {
			sum += r.getVoto();
		}
		average = (double) (sum/listaRecensioni.size());
		average.intValue();
		if(average < 0.5) {
			
		}
		else if(average >= 0.5 && average < 1.5) {
			star1.setStyle("-fx-background-color: orange;");
		}
		else if(average >= 1.5 && average < 2.5) {
			
		}
		else if(average >= 2.5 && average < 3.5) {
	
		}
		else if(average >= 3.5 && average < 4.5) {
	
		}
		else if(average >= 4.5) {
			
		}
	}
	public void initRecensioni() {
		
	}
}

