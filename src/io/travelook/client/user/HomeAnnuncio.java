package io.travelook.client.user;

import java.io.IOException;
import java.net.UnknownHostException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import io.travelook.broker.ClientProxy;
import io.travelook.model.Chat;
import io.travelook.model.Messaggio;
import io.travelook.model.RichiestaDiPartecipazione;
import io.travelook.model.Utente;
import io.travelook.model.Viaggio;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class HomeAnnuncio extends Application {
	private ClientProxy c;

	private Stage primaryStage;
    private FlowPane rootLayout;
    private Text destinazione;
    private Text lingua;
    private Text dataInizio;
    private Text dataFine;
    private Text luogoPartenza;
    private Text budget;
    private ImageView logo;
    private Text titolo;
    private ListView<Messaggio> chatView;
    private TextField newMessage;
    private ImageView immagine;
    private Button backButton;
    private Button sendButton;
    private List<Utente> listUserViaggio;
    private Button modificaAnnuncio;
    private Text descrizione;
    private Viaggio viaggio;
    private SimpleDateFormat formatter;
    private Rectangle chatShape;
    private Button rdpbutton;
    private ListView<RichiestaDiPartecipazione> rdpview;
    private Utente user;
    private boolean rdpon;
    private Button sendrdp;
    private Button cancrdp;
    private TextArea textrdp;
    private boolean initUser = false;
    private Text rdplabel;
    private Text creatoDaText;
    private ListView<Utente> utentiView;
    private String code;
	@Override
	public void start(Stage primaryStage) throws ClassNotFoundException {
		this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Annuncio");

        initRootLayout();
	}

	public static void main(String[] args) {
		launch(args);
	}
	public HomeAnnuncio(ClientProxy c, Viaggio viaggio, Utente user, String code) throws ClassNotFoundException, UnknownHostException, IOException {
		this.c = c;
		this.viaggio = viaggio;
		this.user= user;
		this.code = code;
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void initRootLayout() throws ClassNotFoundException {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(HomeAnnuncio.class.getResource("HomeAnnuncio.fxml"));
            rootLayout = (FlowPane) loader.load();
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            formatter = new SimpleDateFormat("dd-MM-yyyy");
            chatView = (ListView) scene.lookup("#chatView");
            newMessage = (TextField) scene.lookup("#messageBox");
            sendButton = (Button) scene.lookup("#send");
            
            titolo = (Text) scene.lookup("#titolo");
            immagine = (ImageView) scene.lookup("#immagine");
            chatShape = (Rectangle) scene.lookup("#chatShape");
           	if(viaggio.getImmaginiProfilo() != null && !viaggio.getImmaginiProfilo().trim().equals(""))
            	immagine.setImage(new Image(viaggio.getImmaginiProfilo().trim()));
            titolo.setText(viaggio.getTitolo().trim());
            if(viaggio.getTitolo().length() > 30)
            	titolo.setStyle("-fx-font: 20 arial;");
            else
            	titolo.setStyle("-fx-font: 30 arial;");
            destinazione = (Text) scene.lookup("#destinazione");
            destinazione.setText(viaggio.getDestinazione().trim());
            lingua = (Text) scene.lookup("#lingua");
            lingua.setText(viaggio.getLingua().trim());
            dataInizio = (Text) scene.lookup("#datainizio");
            dataInizio.setText(formatter.format(viaggio.getDatainizio()));
            dataFine = (Text) scene.lookup("#datafine");
            dataFine.setText(formatter.format(viaggio.getDatafine()));
            luogoPartenza = (Text) scene.lookup("#luogopartenza");
            luogoPartenza.setText(viaggio.getLuogopartenza().trim());
            budget = (Text) scene.lookup("#budget");
            budget.setText(budgetFormat(viaggio.getBudget()));
            descrizione = (Text) scene.lookup("#descrizione");
            descrizione.setText(viaggio.getDescrizione().trim());
            backButton = (Button) scene.lookup("#back");
            modificaAnnuncio = (Button) scene.lookup("#modifica");
            rdpbutton = (Button) scene.lookup("#rdpbutton");
            rdpview = (ListView) scene.lookup("#rdpview");
            sendrdp = (Button) scene.lookup("#sendrdp");
            cancrdp = (Button) scene.lookup("#cancrdp");
            textrdp = (TextArea) scene.lookup("#textrdp");
            rdplabel = (Text) scene.lookup("#labelrdp");
            utentiView = (ListView) scene.lookup("#utentiView");
            creatoDaText = (Text) scene.lookup("#creatoDaText");
            logo = (ImageView) scene.lookup("#logoi");
            logo.setImage(new Image("http://travelook.altervista.org/logo.png"));
            creatoDaText.setText("creato da " + viaggio.getCreatore().getUsername());
            listUserViaggio = viaggio.getPartecipanti();
            backButton.setOnMouseClicked(event -> {
            	if(code == "lista")
					try {
						new HomeListaAnnunci(user, c).start(primaryStage);
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				else if(code == "utente")
            		new HomeUtente(c,user).start(primaryStage);
            });
            
            if(user.getId() == viaggio.getCreatore().getId()) {
            	//CREATORE
            	chatView.setVisible(true);
        		sendButton.setVisible(true);
        		newMessage.setVisible(true);
        		chatShape.setVisible(true);
        		sendrdp.setVisible(false);
        		cancrdp.setVisible(false);
        		textrdp.setVisible(false);
        		rdplabel.setVisible(false);
        		utentiView.setVisible(true);
        		modificaAnnuncio.setVisible(true);
        		rdpview.setVisible(false);
        		rdpbutton.setVisible(true);
                refreshRdp();
                rdpon = false;
                
                rdpbutton.setOnMouseClicked(event -> {
                	if(rdpon) {
                		rdpview.setVisible(false);
                		utentiView.setVisible(true);
                		rdpon = false;
                	}
                	else {
                		rdpview.setVisible(true);
                		utentiView.setVisible(false);
                		rdpon = true;
                	}
                });
                modificaAnnuncio.setOnMouseClicked(event -> {
            			new CreaAnnuncio(viaggio, 0, user).start(primaryStage);
                });
              //CHAT
                refreshChat();
                sendButton.setOnMouseClicked(event -> {
                	Messaggio m = new Messaggio();
                	m.setMessaggio(newMessage.getText());
                	m.setTimestamp(new Timestamp(System.currentTimeMillis()));
                	m.setUtente(user);
                	try {
						c.inviaMessaggio(m, viaggio);
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                	refreshChat();
                	newMessage.setText("");
                });
                newMessage.setOnKeyReleased(event -> {
                	if(newMessage.getText().trim().equals(""))
                		sendButton.setDisable(true);
                	else
                		sendButton.setDisable(false);
                });
                sendButton.setDisable(true);
                refreshUser();
                
            }
            else {
            	listUserViaggio = viaggio.getPartecipanti();
            	
            	boolean trovato = false;
            	for(Utente u : listUserViaggio) {
            		System.out.println(u.getId());
            		if(u.getId() == user.getId())
            			trovato = true;
            	}
            	if(trovato) {
            		//PARTECIPANTE
            		chatView.setVisible(true);
            		sendButton.setVisible(true);
            		newMessage.setVisible(true);
            		chatShape.setVisible(true);
            		sendrdp.setVisible(false);
            		cancrdp.setVisible(false);
            		textrdp.setVisible(false);
            		rdplabel.setVisible(false);
            		utentiView.setVisible(true);
            		rdpview.setVisible(false);
            		rdpbutton.setVisible(false);
            		modificaAnnuncio.setVisible(false);
            		//CHAT
                    refreshChat();
                    sendButton.setOnMouseClicked(event -> {
                    	Messaggio m = new Messaggio();
                    	m.setMessaggio(newMessage.getText());
                    	m.setTimestamp(new Timestamp(System.currentTimeMillis()));
                    	m.setUtente(user);
                    	try {
							c.inviaMessaggio(m, viaggio);
						} catch (ClassNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
                    	refreshChat();
                    	newMessage.setText("");
                    });
                    newMessage.setOnKeyReleased(event -> {
                    	if(newMessage.getText().trim().equals(""))
                    		sendButton.setDisable(true);
                    	else
                    		sendButton.setDisable(false);
                    });
                    sendButton.setDisable(true);
                    refreshUser();
            	}
            	else {
            		//GUEST
            		chatView.setVisible(false);
            		sendButton.setVisible(false);
            		newMessage.setVisible(false);
            		chatShape.setVisible(false);
            		sendrdp.setVisible(true);
            		cancrdp.setVisible(true);
            		textrdp.setVisible(true);
            		rdplabel.setVisible(true);
            		utentiView.setVisible(false);
            		modificaAnnuncio.setVisible(false);
            		rdpview.setVisible(false);
            		rdpbutton.setVisible(false);
            		rdplabel.setText(rdplabel.getText()+ " " + viaggio.getCreatore().getUsername());
            		cancrdp.setOnMouseClicked(event -> {
            			textrdp.setText("");
            		});
            		sendrdp.setOnMouseClicked(event -> {
            			try {
							c.nuovaRichiesta(new RichiestaDiPartecipazione(user, viaggio, textrdp.getText()));
						} catch (ClassNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IllegalArgumentException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
            		});
            	}
            }
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	private void refreshChat() {
		Chat chat= new Chat();
		try {
			chat = c.getChat(viaggio);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        ObservableList<Messaggio> messaggi = FXCollections.observableArrayList(chat.getChat());
        if(!chat.getChat().isEmpty() && messaggi != null) {
        	chatView.setItems(messaggi);
        	chatView.setCellFactory(userCell -> new ChatCell(user));
        	chatView.scrollTo(chat.getChat().size());
        }
	}
	private void refreshRdp() throws ClassNotFoundException, IOException {
		List<RichiestaDiPartecipazione> rdpList = new ClientProxy().getRichiesteForCreatoreViaggio(user, viaggio);
		if(rdpList==null)System.out.println("La lista di rdp e' null");
        ObservableList<RichiestaDiPartecipazione> obsrdp = FXCollections.observableArrayList(rdpList);
        if(!rdpList.isEmpty() && rdpList != null) {
        	rdpview.setItems(obsrdp);
        	rdpview.setCellFactory(userCell -> new RDPCell(c, rdpview));
        	//rdpview.scrollTo(chat.getChat().size());
        }
	}
	private void refreshUser() {
		if(!initUser) {
			boolean trovato = false;
			for(Utente u : listUserViaggio) {
				if(u.getId() == viaggio.getCreatore().getId())
					trovato = true;
			}
			if(!trovato) {
				listUserViaggio.add(viaggio.getCreatore());
			}
			initUser = true;
		}
		ObservableList<Utente> userPart = FXCollections.observableArrayList(listUserViaggio);
        if(!userPart.isEmpty() && userPart != null) {
        	utentiView.setItems(userPart);
        }
        utentiView.setOnMouseClicked(event -> {
        	MouseEvent me = (MouseEvent) event;
        	if(me.getClickCount() == 2)
				try {
					new VisitaUtente(c, utentiView.getSelectionModel().getSelectedItem(), user, viaggio).start(primaryStage);
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        });
	}
	public Stage getPrimaryStage() {
        return primaryStage;
    }
	private String budgetFormat(int budget) {
		if(budget == 1)
			return "$";
		if(budget == 2)
			return "$$";
		if(budget == 3)
			return "$$$";
		if(budget == 4)
			return "$$$$";
		if(budget == 5)
			return "$$$$$";
		return "";
	}
}
