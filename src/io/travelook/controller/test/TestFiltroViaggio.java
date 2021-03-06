package io.travelook.controller.test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.sql.Date;

import io.travelook.controller.filtro.FiltraViaggioBudget;
import io.travelook.controller.filtro.FiltraViaggioData;
import io.travelook.controller.filtro.FiltraViaggioDestinazione;
import io.travelook.controller.filtro.FiltraViaggioLingua;
import io.travelook.model.Utente;
import io.travelook.model.Viaggio;
import junit.framework.Assert;

public class TestFiltroViaggio {
   
	public static void main(String[] args) {
		 List<Object> viaggi = new ArrayList<Object>();
 		 FiltraViaggioBudget filtrobudg= new FiltraViaggioBudget();
		 FiltraViaggioData filtrodata= new FiltraViaggioData();
		 FiltraViaggioDestinazione filtrodest= new FiltraViaggioDestinazione();
		 FiltraViaggioLingua filtroling= new FiltraViaggioLingua();
		 Utente u1=new Utente();
		 u1.setId(1);
		 Utente u2=new Utente();
		 u2.setId(2);
		 Utente u3=new Utente();
		 u3.setId(3);
		 Utente u4=new Utente();
		 u1.setId(4);
		 Utente u5=new Utente();
		 u5.setId(5);
		 Utente u6=new Utente();
		 u6.setId(6);
		 Utente u7=new Utente();
		 u7.setId(7);
		 Utente u8=new Utente();
		 u8.setId( 8);	
	     Date p1=new Date(2019,7,20);
		 Date a1=new Date(2019,7,23);
		 Date p2=new Date(2019,5,10);
		 Date a2=new Date(2019,5,15);
		 Date p3=new Date(2019,3,8);
		 Date a3=new Date(2019,3,11);
		 Date p4=new Date(2019,3,5);
		 Date a4=new Date(2019,3,12);
		 Date t1=new Date(2019,3,9);
	     Viaggio v1=new Viaggio();
	     v1.setTitolo("weekend a Barcellona");
	     v1.setDestinazione("Barcellona");
	     v1.setBudget(3);
	     v1.setDescrizione( "viaggio di un weekend alla visita delle bellezze della capitale catalana");
	     v1.setLingua("spagnolo");
	     v1.setCreatore(u1);
	     v1.setLuogopartenza("Roma");
	     v1.setDatainizio(p1);
	     v1.setDatafine(a1);;
	     Viaggio v2=new Viaggio();
	     v2.setTitolo("weekend a Bari");
	     v2.setDestinazione("Bari");
	     v2.setBudget(1);
	     v2.setDescrizione( "du giorn e du' frittur ");
	     v2.setLingua("barese");
	     v2.setCreatore(u2);
	     v2.setLuogopartenza("Bologna");
	     v2.setDatainizio(p2);
	     v2.setDatafine(a2);
	     Viaggio v3=new Viaggio();
	     v3.setTitolo("weekend a Copenaghen");
	     v3.setDestinazione("Copenaghen");
	     v3.setBudget(4);
	     v3.setDescrizione( "visita al birrifiicio mikkeller e tool ");
	     v3.setLingua("inglese");
	     v3.setCreatore(u3);
	     v3.setLuogopartenza("Bologna");
	     v3.setDatainizio(p3);
	     v3.setDatafine(a3);
	     Viaggio v4=new Viaggio();
	     v4.setTitolo("weekend a Londra");
	     v4.setDestinazione("Londra");
	     v4.setBudget(4);
	     v4.setDescrizione( "concerto clash  ");
	     v4.setLingua("inglese");
	     v4.setCreatore(u4);
	     v4.setLuogopartenza("Bologna");
	     v4.setDatainizio(p4);
	     v4.setDatafine(a4);
	     viaggi.add(v1);
	     viaggi.add(v2);
	     viaggi.add(v3);
	     viaggi.add(v4);
	     Object[] filtribudget=new Object[1];
	     Object[] filtridata=new Object[1];
	     Object[] filtridest=new Object[1];
	     Object[] filtridest2=new Object[1];
	     Object[] filtrilingua1=new Object[1];
	     Object[] filtrilingua2=new Object[2];
	     filtribudget[0]=4;
	     filtridata[0]=t1;
	     filtridest[0]="Londra";
	     filtridest2[0]="Brindisi";
	     filtrilingua1[0]="inglese"; 
	     filtrilingua2[0]="spagnolo";
	     filtrilingua2[1]="barese";
	     System.out.println("filtraggio di "+viaggi.size()+" viaggi\n");
	     List<Viaggio> ris1=filtrobudg.convertToViaggi(filtrobudg.filtra(filtribudget, viaggi));
	     Assert.assertEquals(2,ris1.size());
	     System.out.println("filtraggio per budget ok !\n");
	     List<Viaggio> ris2=filtrodata.convertToViaggi(filtrodata.filtra(filtridata, viaggi));
	     Assert.assertEquals(2,ris2.size());
	     System.out.println("filtraggio per data ok !\n");
	     List<Viaggio> ris3=filtrodest.convertToViaggi(filtrodest.filtra(filtridest, viaggi));
	     Assert.assertEquals(1,ris3.size());
	     System.out.println("filtraggio per destinzazione giusta ok !\n");
	     List<Viaggio> ris4=filtrodest.convertToViaggi(filtrodest.filtra(filtridest2, viaggi));
	     Assert.assertEquals(0,ris4.size());
	     System.out.println("filtraggio per destinazione sbagliata ok !\n");
	     List<Viaggio> ris5=filtroling.convertToViaggi(filtroling.filtra(filtrilingua1, viaggi));
	     Assert.assertEquals(2,ris5.size());
	     System.out.println("filtraggio una lingua ok !\n");
	     List<Viaggio> ris6=filtrobudg.convertToViaggi(filtroling.filtra(filtrilingua2, viaggi));
	     Assert.assertEquals(2,ris6.size());
	     System.out.println("filtraggio per due lingue ok !\n");
	}
} 
