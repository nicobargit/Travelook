package io.travelook.controller.filtro;

import java.util.ArrayList;
import java.util.List;

import io.travelook.model.Viaggio;

public class FiltraViaggioDestinazione implements Filtro {
     
	 private Object[] filtri=null;
	
	 public FiltraViaggioDestinazione() {
		 super();
	 }
	 
	 public List<Viaggio> convertToViaggi(List<Object> oggetti){
			List<Viaggio> res= new ArrayList<Viaggio>();
			for(Object o :oggetti) {
				if(o instanceof Viaggio) {
				Viaggio v=(Viaggio)o;
				res.add(v);
				}
			}
			return res;
		}
		
	   public Object[] convertToObjectArray (List<Viaggio> viaggi){
		   int size=viaggi.size();
		   Object[] res= new Object[size];
		   int count=0;
			for(Viaggio v : viaggi) {
		        res[count]=v;
		        count++;
			}
			return res;
	   }
	   
	@Override
	public List<Object> filtra(Object[] ogg, List<Object> o) {
		this.filtri=ogg;
		List<Object> viaggi= new ArrayList<Object>();
		List<String> destinazioni= new ArrayList<String>();
		 for(Object ogget : ogg) {
			  if(ogget instanceof String) {
		    	destinazioni.add(String.valueOf(ogget));
			  }
		    }
		    for(Object oggetto :o) {
		    	if(oggetto instanceof Viaggio) {
		    	   Viaggio v =(Viaggio)oggetto;
		    	     for(String s:destinazioni) {
		    		    if(v.getDestinazione().equals(s)) {
		    			   Object ob=v;
		    			   viaggi.add(ob);
		    		    }
		    	     }
		    	}     
		    }
			return viaggi;
	}

	@Override
	public Object[] getFiltri() {
		return this.filtri;
	}

	public void setFiltri(Object[] filtri) {
		this.filtri = filtri;
	}
	

}
