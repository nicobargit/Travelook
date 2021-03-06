package io.travelook.client.user;

import java.io.IOException;

import io.travelook.model.Utente;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ListCell;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;

public class UserCell extends ListCell<Utente> {
	@FXML
    private Text user;
	@FXML
    private FlowPane flowPane;
    private FXMLLoader mLLoader;

	protected void updateItem(Utente utente, boolean empty) {
	    super.updateItem(utente, empty);
	    if (empty || utente == null) {
	        setText(null);
	        setGraphic(null);
	    } else {
	        if (mLLoader == null) {
	            mLLoader = new FXMLLoader(getClass().getResource("USerCell.fxml"));
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
	        user.setText(utente.toString());
	        setText(null);
	        setGraphic(flowPane);
	    }
	}
}
