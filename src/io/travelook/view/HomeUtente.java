package io.travelook.view;

import java.io.File;
import java.io.IOException;

import io.travelook.controller.annuncio.ListaAnnunciController;
import io.travelook.controller.utente.UtenteController;
import io.travelook.model.Utente;
import io.travelook.model.Viaggio;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class HomeUtente extends Application {
	private Stage primaryStage;
    private FlowPane rootLayout;
    private ListView<Viaggio> listView;
    private ImageView logo;
    private Button logout;
    private Button filtra;
    private Text currentUser;
    private ImageView userImg;
    private Button refresh;
    private Button creaAnnuncio;
    private Utente user;
    private int count;
    private UtenteController controlleru;
	public HomeUtente(String username) {
		controlleru = new UtenteController();
		int iduser = controlleru.getIdUtenteFromUsername(username);
		System.out.println("PORCODIO: " + username + "PORCODIO:" + iduser);
        this.user = controlleru.getUtenteById(iduser);
        controlleru.setU(user);
	}
	public HomeUtente(Utente user) {
        this.user = user;
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
	
	@SuppressWarnings("unchecked")
	public void initRootLayout() {
        try {
        	count = 0;
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(HomeUtente.class.getResource("HomeListaAnnunci.fxml"));
            rootLayout = (FlowPane) loader.load();
            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            
            listView = (ListView<Viaggio>) scene.lookup("#lista");
            userImg = (ImageView) scene.lookup("#imgUtente");
            logout = (Button) scene.lookup("#logout");
            currentUser = (Text) scene.lookup("#currentUser");
            if(user.getImmagineProfilo() != null && !user.getImmagineProfilo().trim().equals("") && new File("src/"+user.getImmagineProfilo().trim()).exists())
        		userImg.setImage(new Image(user.getImmagineProfilo().trim()));
            currentUser.setText("Current user: " + user.getUsername());
            currentUser.setOnMouseClicked(event -> {
            	//open profilo utente
            	new Alert(AlertType.INFORMATION, "todo").show();
            });
            userImg.setOnMouseClicked(event -> {
            	//open profilo utente
            	new Alert(AlertType.INFORMATION, "todo").show();
            });
            logout.setOnMouseClicked(event -> {
            	new HomeTravelook().start(primaryStage);
            	new Alert(AlertType.INFORMATION, "Logout effettuato con successo").show();
            });
            ListaAnnunciController controller = new ListaAnnunciController();
            ObservableList<Viaggio> items = FXCollections.observableArrayList(controller.getAnnunci());
            listView.setItems(items);
            listView.setCellFactory(userCell -> new ViaggioCell());
            listView.setOnMouseClicked(event -> { 
            	MouseEvent me = (MouseEvent) event;
            	if(me.getClickCount() == 2)
            		new HomeAnnuncio(listView.getSelectionModel().getSelectedItem(), user).start(primaryStage);
            });
            creaAnnuncio = (Button) scene.lookup("#crea");
            creaAnnuncio.setOnAction(event -> {
            	new CreaAnnuncio(null, 1, user).start(primaryStage);
            });
            refresh = (Button) scene.lookup("#refresh");
            refresh.setOnAction(event -> {
            	ObservableList<Viaggio> refresh = FXCollections.observableArrayList(controller.getAnnunci());
                listView.setItems(refresh);
            });
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	public Stage getPrimaryStage() {
        return primaryStage;
    }
}

