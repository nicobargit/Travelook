package io.travelook.client.user;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Optional;

import io.travelook.broker.ClientProxy;
import io.travelook.model.RichiestaDiPartecipazione;
import io.travelook.model.Stato;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;

public class RDPCellUtente extends ListCell<RichiestaDiPartecipazione> {
	@FXML
    private Text user;
	@FXML
    private Text body;
	@FXML
    private Text stato;
	@FXML
    private FlowPane flowPane;
	@FXML
	private Button accept;
	@FXML
	private Button reject;
    private FXMLLoader mLLoader;
    private ClientProxy c;
    private ListView<RichiestaDiPartecipazione> rdpview;
    public RDPCellUtente(ClientProxy c, ListView<RichiestaDiPartecipazione> rdpview) {
    	this.c=c;
    	this.rdpview = rdpview;
    }
	protected void updateItem(RichiestaDiPartecipazione rdp, boolean empty) {
	    super.updateItem(rdp, empty);
	    if (empty || rdp == null) {
	        setText(null);
	        setGraphic(null);
	    } else {
	        if (mLLoader == null) {
	            mLLoader = new FXMLLoader(getClass().getResource("RDPCellUtente.fxml"));
	            mLLoader.setController(this);
	            try {
	                mLLoader.load();
	            } catch (IOException e) {
	                Alert alert = new Alert(AlertType.ERROR);
	                alert.setTitle("title");
	                alert.setContentText("error");
	                alert.show();
	            }
	        }
	        
	        user.setText(rdp.getUtente().getUsername().trim());
	        body.setText(rdp.getMessaggioRichiesta().trim());
	        stato.setText(rdp.getStato().name());
	        if(rdp.getStato().compareTo(Stato.NONVISTA) != 0) {
	        	accept.setVisible(false);
	        	reject.setVisible(false);
	        }
	        
	        accept.setOnMouseClicked(event -> {
	        	Optional<String> mexRisp = new TextInputDialog("Inserisci il messaggio di risposta:").showAndWait();
	        	if(mexRisp.isPresent()) {
	        		rdp.setRisposta(Stato.ACCETTATA, mexRisp.get());
	        		try {
						System.out.println(c.rispondiRichiesta(rdp));
					} catch (ClassNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (UnknownHostException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					refreshRdp(rdp);
					
	        	}
	        });
	        
	        reject.setOnMouseClicked(event -> {
	        	Optional<String> mexRisp = new TextInputDialog("Inserisci il messaggio di risposta:").showAndWait();
	        	if(mexRisp.isPresent()) {
	        		rdp.setRisposta(Stato.NONACCETTATA, mexRisp.get());
	        		try {
						c.rispondiRichiesta(rdp);
						refreshRdp(rdp);
					} catch (ClassNotFoundException | IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	        		

	        	}
	        });
	        setText(null);
	        setGraphic(flowPane);
	    }
	}
	private void refreshRdp(RichiestaDiPartecipazione rdp) {
		List<RichiestaDiPartecipazione> rdpList = null;
		try {
			rdpList = c.getRichiesteForCreatoreViaggio(rdp.getViaggio().getCreatore(), rdp.getViaggio() );
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        ObservableList<RichiestaDiPartecipazione> obsrdp = FXCollections.observableArrayList(rdpList);
        if(!rdpList.isEmpty() && rdpList != null) {
        	rdpview.setItems(obsrdp);
        }
	}
}
