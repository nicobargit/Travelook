package io.travelook.model;
import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Utente implements Serializable {

	private int idUtente;
	private String username;
	private String email; 
	private String nome;
	private String cognome;
	private String bio;
	private Date dataNascita;
	private String immagineProfilo;
	private Storico storico;
	private List<Interessi> interessi;
	private List<Recensione> recensioni;
	
	//Per creazione utente
	public Utente(int idUtente, String username, String email, String nome, String cognome, Date dataNascita, String immagineProfilo) 
			throws IllegalArgumentException {
		if(idUtente < 0 || email == null || nome == null || cognome == null || dataNascita == null)
			throw new IllegalArgumentException();
		this.idUtente = idUtente;
		this.username = username;
		this.email = email;
		this.nome = nome;
		this.cognome = cognome;
		this.dataNascita = dataNascita;
		this.immagineProfilo = immagineProfilo;
		this.interessi = new ArrayList<Interessi>();
		this.recensioni = new ArrayList<Recensione>();
		this.storico = new Storico(idUtente);
	}
	
	
	  //costruttore con anche il campo Bio
     public Utente(int idUtente, String username, String email, String nome, String cognome, String bio,
				Date dataNascita, String immagineProfilo, Storico storico, List<Interessi> interessi,
				List<Recensione> recensioni) {
			super();
			this.idUtente = idUtente;
			this.username = username;
			this.email = email;
			this.nome = nome;
			this.cognome = cognome;
			this.bio = bio;
			this.dataNascita = dataNascita;
			this.immagineProfilo = immagineProfilo;
			this.storico = storico;
			this.interessi = interessi;
			this.recensioni = recensioni;
		}
	public Utente(String username, String email, String nome, String cognome, Date dataNascita, String immagineProfilo) 
			throws IllegalArgumentException {
		if(email == null || nome == null || cognome == null || dataNascita == null)
			throw new IllegalArgumentException();
		this.username = username;
		this.email = email;
		this.nome = nome;
		this.cognome = cognome;
		this.dataNascita = dataNascita;
		this.immagineProfilo = immagineProfilo;
		this.interessi = new ArrayList<Interessi>();
		this.recensioni = new ArrayList<Recensione>();
		this.storico = new Storico(idUtente);
	}
	public Utente(int idUtente, String username, String email, String nome, String cognome, Date dataNascita, String immagineProfilo, 
					Storico storico, List<Recensione> recensioni, List<Interessi> interessi) throws IllegalArgumentException {
		if(idUtente < 0 || email == null || nome == null || cognome == null || dataNascita == null)
			throw new IllegalArgumentException();
		this.idUtente = idUtente;
		this.username = username;
		this.email = email;
		this.nome = nome;
		this.cognome = cognome;
		this.dataNascita = dataNascita;
		this.immagineProfilo = immagineProfilo;
		this.storico = storico;
		this.interessi = interessi;
		this.recensioni = recensioni;
	}
	
	public Utente() {
		// TODO Auto-generated constructor stub
	}
	public int getId() {
		return idUtente;
	}
	public void setId(int idUtente) {
		this.idUtente = idUtente;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	public Date getDataNascita() {
		return dataNascita;
	}
	public void setDataNascita(Date dataNascita) {
		this.dataNascita = dataNascita;
	}
	public String getImmagineProfilo() {
		return immagineProfilo;
	}
	public void setImmagineProfilo(String immagineProfilo) {
		this.immagineProfilo = immagineProfilo;
	}
	public Storico getStorico() {
		return storico;
	}
	public void setStorico(Storico storico) {
		this.storico = storico;
	}
	public List<Interessi> getInteressi() {
		return interessi;
	}
	public void setInteressi(List<Interessi> interessi) {
		this.interessi = interessi;
	}
	public List<Recensione> getRecensioni() {
		return recensioni;
	}
	public void setRecensioni(List<Recensione> recensioni) {
		this.recensioni = recensioni;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	@Override
	public String toString() {
		return this.username.trim() + ": " + this.nome.trim() + " " + this.cognome.trim();
	}
	public int getIdUtente() {
		return idUtente;
	}
	public void setIdUtente(int idUtente) {
		this.idUtente = idUtente;
	}
	public String getBio() {
		return this.bio;
	}
	public void setBio(String bio) {
		this.bio = bio;
	}
	
	
}
