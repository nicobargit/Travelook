package io.travelook.broker;

import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

import com.google.gson.Gson;

import io.travelook.model.Viaggio;

public class ClientProva {
	
	public static void main(String args[]) throws UnknownHostException, IOException, JSONException {
		Socket s = new Socket("localhost",4000);
		BufferedWriter out = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
		DataInputStream dis = new DataInputStream(s.getInputStream());
		Object[] lista = new Object[2];
		Object a = new Object();
		lista[0] = a;
		lista[1] = a;
		Richiesta r = new Richiesta("local",123,lista,"getListaAnnunci");
		Gson gson = new Gson();
		String stringaRichiesta = gson.toJson(r).toString();
		System.out.println(stringaRichiesta);
		DataOutputStream dos = new DataOutputStream(s.getOutputStream());
		dos.writeUTF(stringaRichiesta);
		String stringaRisposta = dis.readUTF();
		Risposta risposta = gson.fromJson(stringaRisposta, Risposta.class);
		System.out.println("Risposta ricevuta dal server: " + stringaRisposta);
		System.out.println(risposta.getIpDestinatario());
		//Viaggio[] arrayViaggi = (Viaggio[]) risposta.getValori();
		//Viaggio v = null;
		List<Viaggio> vl = new ArrayList<>();
		for(int i=0; i<risposta.getValori().length; i++) {
			if(risposta.getValori()[i] instanceof Viaggio)
				vl.add((Viaggio) risposta.getValori()[i]);
			else
				System.out.println("DC");
		}
		for(Viaggio v : vl) {
			System.out.println(v.getTitolo());
		}
		s.close();
	}
	

}
