package it.polito.tdp.movimenti;


import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.movimenti.bean.Model;
import it.polito.tdp.movimenti.bean.Stat;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class MovimentiController {
	private Model model;

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private ChoiceBox<Integer> boxCircPartenza;

	@FXML
	private Button btnElenca;

	@FXML
	private Button btnRicerca;

	@FXML
	private Label lblStatus;

	@FXML
	private ProgressBar pbProgress;

	@FXML
	private TextField txtLunghezza;

	@FXML
	private TextArea txtResult;
	
	@FXML
	void doElenca(ActionEvent event) {
		txtResult.clear();
		if(this.boxCircPartenza.getValue()==null){
			txtResult.appendText("ERRORE: Seleionare circoscrizione!");
			return;
		}
		for(Stat stemp: model.getCambiInd(this.boxCircPartenza.getValue())){
			txtResult.appendText("Cambi alla circoscrizione "+stemp.getCirc()+" tot="+stemp.getEventi()+"\n");
		}
	    }

    @FXML
	 void doRicerca(ActionEvent event) {
    	
		if(this.boxCircPartenza.getValue()==null){
			txtResult.appendText("ERRORE: Seleionare circoscrizione!");
			return;
		}
		
		String vertex=this.txtLunghezza.getText();
		if(vertex.equals(" ")){
			txtResult.appendText("ERRORE: inserire max nr di circ!");
			return;
		}
		int maxVertex;
		
		try{
			maxVertex=Integer.parseInt(vertex);
		}catch(NumberFormatException e){
			txtResult.appendText("ERRORE: Inserire un numero!");
			return;
		}
		
		for(int circ:model.getCamminomax(this.boxCircPartenza.getValue(), maxVertex)){
			txtResult.appendText("Circoscrizione "+circ+" \n");
		}
		}

	    

	@FXML
	void initialize() {
		assert boxCircPartenza != null : "fx:id=\"boxCircPartenza\" was not injected: check your FXML file 'Movimenti.fxml'.";
		assert btnElenca != null : "fx:id=\"btnElenca\" was not injected: check your FXML file 'Movimenti.fxml'.";
		assert btnRicerca != null : "fx:id=\"btnRicerca\" was not injected: check your FXML file 'Movimenti.fxml'.";
		assert lblStatus != null : "fx:id=\"lblStatus\" was not injected: check your FXML file 'Movimenti.fxml'.";
		assert pbProgress != null : "fx:id=\"pbProgress\" was not injected: check your FXML file 'Movimenti.fxml'.";
		assert txtLunghezza != null : "fx:id=\"txtLunghezza\" was not injected: check your FXML file 'Movimenti.fxml'.";
		assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Movimenti.fxml'.";

	}

	public void setModel(Model model) {
		this.model=model;
		this.boxCircPartenza.getItems().clear();
		this.boxCircPartenza.getItems().addAll(model.getAllCircoscrizioni());
		
	}

}
