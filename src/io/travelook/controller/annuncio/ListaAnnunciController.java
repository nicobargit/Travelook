package io.travelook.controller.annuncio;
import java.util.ArrayList;
import java.util.List;

import io.travelook.controller.Controller;
import io.travelook.controller.filtro.Filtro;
import io.travelook.model.Viaggio;
import io.travelook.persistence.MssqlViaggioDAO;

public class ListaAnnunciController extends Controller {
	private Filtro f;
	private List<Viaggio> annunci;
	private MssqlViaggioDAO db;
	
	public ListaAnnunciController() {
		super();
		annunci = new ArrayList<Viaggio>();
		db = new MssqlViaggioDAO();
	}

	
	
	public List<Viaggio> getAnnunci() {
		
		db.setConn(super.getDbConnection());
		this.annunci = db.readViaggiListFromDb();
		return annunci;
	}

	public void setAnnunci(List<Viaggio> annunci) {
		this.annunci = annunci;
	}

	public boolean creaAnnuncio(Viaggio v) {
		boolean res=false;
		boolean resdb=false;
		if(v==null) {
			res=false;
			System.out.println("viaggio nullo !");
		}else {
			db.setConn(super.getDbConnection());
			resdb=db.create(v);
			res=annunci.add(v);
			res=true;
		}
		return res&resdb;
	}
	
	public boolean eliminaAnnuncio(int idannuncio) {
		boolean res=false;
		if(idannuncio<0 ) {
			res=false;
			System.out.println("id negativo ! ");
		}
		else {
			for(Viaggio v : annunci) {
				if(v.getIdViaggio()==idannuncio) {
					db.setConn(super.getDbConnection());
					db.delete(v.getIdViaggio());
					annunci.remove(v);
					res=true;
				}
			}
		}
		return res;
	}
	
	/*public List<Viaggio> convertToViaggi(List<Object> oggetti){
		List<Viaggio> res= new ArrayList<Viaggio>();
		for(Object o :oggetti) {
			Viaggio v=(Viaggio)o;
			res.add(v);
		}
		return res;
	}
	
   public List<Object> convertToObject (List<Viaggio> viaggi){
	   List<Object> res= new ArrayList<Object>();
		for(Viaggio v : viaggi) {
	        res.add(v);
		}
		return res;
   }*/


	public Viaggio visualizzaAnnuncio(int id) {
		Viaggio res=null; 
		for(Viaggio v:annunci ) {
			if(v.getIdViaggio()==id) {
				res=v;
			}
		}
		return res;
	}

	public Filtro getF() {
		return f;
	}

	public void setF(Filtro f) {
		this.f = f;
	}
}
