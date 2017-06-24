package it.polito.tdp.flight;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.flight.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FlightController {

	private Model model;
	private double ultimoKsimulato;

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private TextField txtDistanzaInput;

	@FXML
	private TextField txtPasseggeriInput;

	@FXML
	private TextArea txtResult;

	@FXML
	void doCreaGrafo(ActionEvent event) {
		double k ;
		String input=txtDistanzaInput.getText();
		
		if (input.length() == 0) {
			txtResult.appendText("ERRORE: devi inserire una distanza\n");
			return;
		}
		
    	try {
    		k = Double.parseDouble(input);
    	} catch (NumberFormatException e) {
    		txtResult.appendText("ERRORE: la distanza deve essere in formato numerico \n");
    		return;
    	}
    	
    	if(k<1) {
    		txtResult.appendText("ERRORE: k deve essere > 0 \n");
    		return;
    	}
    	ultimoKsimulato=k;
    	model.CreaGrafo(k);
    	model.Analizza();
    	txtResult.appendText("linsieme è connesso :"+model.isConnesso()+"\n");
    	txtResult.appendText("aereo porto più distante raggiungibile da fiumicino:"+model.getAereoportoPiuLontanoRaggiungibileDaFiumicino().toString()+"\n");
	
	}

	@FXML
	void doSimula(ActionEvent event) {
		try {
    		if(ultimoKsimulato==Double.parseDouble(txtDistanzaInput.getText())){
		    			int n ;
		    			String input=txtPasseggeriInput.getText();
		    			
		    			if (input.length() == 0) {
		    				txtResult.appendText("ERRORE: devi inserire una numero di passeggeri\n");
		    				return;
		    			}
		    			
		    	    	try {
		    	    		n = Integer.parseInt(input);
		    	    	} catch (NumberFormatException e) {
		    	    		txtResult.appendText("ERRORE: il numero di passeggeri deve essere in formato numerico \n");
		    	    		return;
		    	    	}
		    	    	
		    	    	if(n<1) {
		    	    		txtResult.appendText("ERRORE: n deve essere > 0 \n");
		    	    		return;
		    	    	}
		    	    	
		    	    	txtResult.appendText(model.Simula(n).classificaOrdinata()+"\n");
		    			
    		}else{
    			txtResult.appendText("Prima di simulare carica le rotte per questa distanza \n");
    		}
    	} catch (NumberFormatException e) {
    		txtResult.appendText("ERRORE: la distanza deve essere in formato numerico \n");
    		return;
    	}
		
	}

	@FXML
	void initialize() {
		assert txtDistanzaInput != null : "fx:id=\"txtDistanzaInput\" was not injected: check your FXML file 'Untitled'.";
		assert txtPasseggeriInput != null : "fx:id=\"txtPasseggeriInput\" was not injected: check your FXML file 'Untitled'.";
		assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Untitled'.";

	}

	public void setModel(Model model) {
		this.model = model;
	}
}
