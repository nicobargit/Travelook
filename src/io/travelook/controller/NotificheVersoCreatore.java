package io.travelook.controller;

import io.travelook.model.Utente;
import io.travelook.model.Viaggio;

public class NotificheVersoCreatore extends NotificheVerso implements Observer {
	private Utente utente;
	private Viaggio viaggio;
	private String messaggio;
	
	public NotificheVersoCreatore(Utente utente, Viaggio viaggio) throws NullPointerException {
		if(utente == null || viaggio == null)
			throw new NullPointerException();
		this.utente = utente;
		this.viaggio = viaggio;
	}
	public NotificheVersoCreatore(Utente utente, Viaggio viaggio, String messaggio) throws NullPointerException {
		if(utente == null || viaggio == null || messaggio.trim().equals("") || messaggio == null)
			throw new NullPointerException();
		this.utente = utente;
		this.viaggio = viaggio;
	}
	
	@Override
	public void update() {
		INotifica notifica = new NotificheEmail(); //usare pattern singleton per inotifica
		String messaggio = "Nuova richiesta di partecipazione per " + this.viaggio.getTitolo() + " da "
				+ this.utente.getUsername() + "\nMessaggio:\n" + this.messaggio;
		notifica.inviaNotifica(this.utente.getEmail(), messaggio);
	}
	@Override
	public Utente getUtente() {
		return utente;
	}
	@Override
	public Viaggio getViaggio() {
		return viaggio;
	}
	@Override
	public String getMessaggio() {
		return messaggio;
	}
	@Override
	public void setMessaggio(String messaggio) {
		this.messaggio = messaggio;
	}
}