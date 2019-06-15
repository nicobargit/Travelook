package io.travelook.persistence;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import io.travelook.model.Stato;
import io.travelook.model.Utente;
import io.travelook.model.Viaggio;

public class MssqlViaggioDAO implements ViaggioDAO {
	

	// campi interni alla table Viaggio // 
	static final String ID = "id";
	static final String IDC = "idCreatore";
	static final String IDP= "idPartecipante";
	static final String TITOLO = "titolo";
	static final String  DESTINAZIONE= "destinazione";
	static final String  DESCRIZIONE= "descrizione";
	static final String  LINGUA= "lingua";
	static final String  BUDGET= "budget";
	static final String  DATAP= "dataPartenza";
	static final String  DATAA= "dataFine";
	static final String  IMMP= "immagineProfilo";
	static final String  IMMA= "immaginiAlternative";

	
	static final String table = "Viaggio";
	
	//Query per inserire un viaggio nel DB//
	static final String insert = "INSERT INTO " + table + 
			" (idCreatore,titolo,destinazione,descrizione,lingua,budget,luogoPartenza,dataPartenza,dataFine"+
			",immagineProfilo)"+
			" VALUES (?,?,?,?,?,?,?,?,?,?)";
	//Query per creare la table viaggio nel DB//
	static final String create="create table " + table + " (" + 
			"id int not null IDENTITY PRIMARY KEY," +
			"idCreatore int not null," +
			"titolo char(20) not null,"+
		    "destinazione char(20) not null,"+
		    "descrizione char(200) not null,"+
			"lingua char(20) not null,"+
			"budget int not null,"+
			"luogoPartenza char(20) not null,"+
			"dataPartenza Date not null,"+
			"dataFine Date not null,"+
			"immagineProfilo char(1000),"+
			"primary key(id),"+
			"foreign key (idCreatore) references Utente(id),"+
			"foreign key(idPartecipante) references Utente(id),"+
			"unique(id, idCreatore, idPartecipante)"+
			")";
	static String read_all = "select 	v.id, v.titolo, v.destinazione, v.descrizione, v.budget, v.luogoPartenza, \r\n" + 
			"		v.dataPartenza, v.dataFine, v.lingua, v.stato, v.immagineProfilo, v.idCreatore, c.nickname,\r\n" + 
			"		c.email, c.nome, c.cognome, c.dataNascita, c.imgProfilo from Viaggio v\r\n" + 
			"inner join Utente AS c on c.id = v.idCreatore";
	
	// SELECT * FROM table WHERE idcolumn = ?;
	static String read_by_id = 
		"SELECT * " +
			"FROM " + table + " v " +
			"inner join UTENTE AS C ON v."+IDC+"=c.id" 
		;
	static String list_user_viaggio = "select rdp.idUtente, u.nickname, u.email, u.nome, u.cognome, u.dataNascita, u.imgProfilo from Richiesta_Di_Partecipazione rdp\r\n" + 
			"inner join Utente as u on u.id = rdp.idUtente\r\n" + 
			"where stato = 0;";
	// DELETE FROM table WHERE idcolumn = ?;
		static String delete = 
			"DELETE " +
				"FROM " + table + " " +
				"WHERE " + ID + " = ? "
			;
		// UPDATE  a Table 
		static String update = 
				"UPDATE " + table + " " +
					"SET "  
						 +IDC+ " = ?, " +
					"WHERE " + ID + " = ? "
				;

	public MssqlViaggioDAO(Connection conn) {
		this.conn = conn;
	}
	
	@Override //inserisce un viaggio nel db // 
	public void create(Viaggio viaggio) {
		if(viaggio==null) {
			System.out.println( "insert(): failed to insert a null entry");
			}
		// TODO Auto-generated method stub
		/*" (id,idCreatore,titolo,destinazione,descrizione,lingua,budget,dataPartenza,dataFine"+
		",immagineProfilo,immaginiAlternative"+*/
		try {
			PreparedStatement prep_stmt = conn.prepareStatement(MssqlViaggioDAO.insert);
			prep_stmt.clearParameters();
			//prep_stmt.setInt(1,viaggio.getIdViaggio());
			int i=1;
			prep_stmt.setInt(i++,viaggio.getCreatore().getId());
			prep_stmt.setString(i++,viaggio.getTitolo());
			prep_stmt.setString(i++,viaggio.getDestinazione());
			prep_stmt.setString(i++,viaggio.getDescrizione());
			prep_stmt.setString(i++,viaggio.getLingua());
			prep_stmt.setInt(i++,viaggio.getBudget());
			prep_stmt.setString(i++,viaggio.getLuogopartenza());
			prep_stmt.setDate(i++,viaggio.getDatainizio());
			prep_stmt.setDate(i++,viaggio.getDatafine());
			prep_stmt.setString(i++,viaggio.getImmaginiProfilo());		
			prep_stmt.executeUpdate();
			prep_stmt.close();
		}
		catch (Exception e) {
			System.out.println("create(): failed to insert entry: " + e.getMessage());
			e.printStackTrace();
		}
		finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public Viaggio read(int id) {
		Viaggio res=null;
		try {
			/*" (id,idCreatore,titolo,destinazione,descrizione,lingua,budget,dataPartenza,dataFine"+
			",immagineProfilo,immaginiAlternative"+*/
			PreparedStatement prep_stmt = conn.prepareStatement(MssqlViaggioDAO.read_by_id);
			prep_stmt.setInt(1,id);
			ResultSet rs = prep_stmt.executeQuery();
			if ( rs.next() ) {
				Viaggio v = new Viaggio();
				v.setIdViaggio(rs.getInt(1));
				int idU=rs.getInt(13);
				String user=rs.getString("nickname");
				String mail=rs.getString("email");
				String nome=rs.getString("nome");
				String cgnome=rs.getString("cognome");
				Date datan=rs.getDate("dataNascita");
				String imgP=rs.getString("imgProfilo");
				Utente c = new Utente(idU,user,mail,nome,cgnome,datan,imgP);
				v.setCreatore(c);
				v.setTitolo(rs.getString("titolo"));
				v.setDestinazione(rs.getString("destinazione"));
				v.setDescrizione(rs.getString("descrizione"));
				v.setLingua(rs.getString("lingua"));
				v.setBudget(rs.getInt("budget"));
				v.setLuogopartenza(rs.getString("luogoPartenza"));
				v.setDatainizio(rs.getDate("dataPartenza"));
				v.setDatafine(rs.getDate("dataFine"));
				v.setStato(Stato.values()[rs.getInt("stato")]);
				res=v;
			}
			rs.close();
			prep_stmt.close();
		    
		}
		catch(Exception e) {
			
		}
		finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return res;
	}

	@Override
	public boolean update(Viaggio viaggio) {
		boolean result = false;
		if ( viaggio == null )  {
			System.out.println( "update(): failed to update a null entry");
			return result;
		}
		try {
			/*PreparedStatement prep_stmt = conn.prepareStatement(Db2CourseDAO.update);
			prep_stmt.clearParameters();
			prep_stmt.setInt(1, course.getId());
			prep_stmt.setString(2, course.getName());
			prep_stmt.executeUpdate();
			result = true;
			prep_stmt.close();*/
		}
		catch (Exception e) {
			System.out.println("insert(): failed to update entry: "+e.getMessage());
			e.printStackTrace();
		}
		finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}

	@Override
	public boolean delete(int id) {
		boolean res=false;
		try {
			PreparedStatement prep_stmt = conn.prepareStatement(MssqlViaggioDAO.delete);
			prep_stmt.setInt(1,id);
			prep_stmt.executeUpdate();
			res = true;
			prep_stmt.close();			
		}catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return res;
	}

	@Override
	public List<Viaggio> readViaggiListFromDb() {
		
		List<Viaggio> listaViaggi = new ArrayList<Viaggio>();
		
		try {
			PreparedStatement prep_stmt = conn.prepareStatement(MssqlViaggioDAO.read_all);
			prep_stmt.clearParameters();
			ResultSet rs = prep_stmt.executeQuery();
			while(rs.next()) {
				
				int i=1;
				Viaggio v = new Viaggio();
				v.setIdViaggio(rs.getInt(i++));
				v.setTitolo(rs.getString(i++));
				v.setDestinazione(rs.getString(i++));
				v.setDescrizione(rs.getString(i++));
				v.setBudget(rs.getInt(i++));
				v.setLuogopartenza(rs.getString(i++));
				v.setDatainizio(rs.getDate(i++));
				v.setDatafine(rs.getDate(i++));
				v.setLingua(rs.getString(i++));
				v.setStato(Stato.values()[rs.getInt(i++)]);
				v.setImmaginiProfilo(rs.getString(i++));
				Utente c = new Utente();
				c.setId(rs.getInt(i++));
				c.setUsername(rs.getString(i++));
				c.setEmail(rs.getString(i++));
				c.setNome(rs.getString(i++));
				c.setCognome(rs.getString(i++));
				c.setDataNascita(rs.getDate(i++));
				c.setImmagineProfilo(rs.getString(i++));
				v.setCreatore(c);
				List<Utente> listaPartecipanti = new ArrayList<Utente>();
				PreparedStatement prep_stmt2 = conn.prepareStatement(MssqlViaggioDAO.list_user_viaggio);
				prep_stmt2.clearParameters();
				ResultSet rs2=prep_stmt2.executeQuery();
				int k=1;
				while(rs2.next()) {
					Utente u = new Utente();
					k=1;
					u.setId(rs2.getInt(k++));
					u.setUsername(rs2.getString(k++));
					u.setEmail(rs2.getString(k++));
					u.setNome(rs2.getString(k++));
					u.setCognome(rs2.getString(k++));
					u.setDataNascita(rs2.getDate(k++));
					u.setImmagineProfilo(rs2.getString(k++));
					listaPartecipanti.add(u);
				}
				prep_stmt2.close();
				rs2.close();
				v.setPartecipanti(listaPartecipanti);
				listaViaggi.add(v);
			}
			prep_stmt.close();
			rs.close();
			
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return listaViaggi;
	}

	@Override
	public boolean createTable() {
		boolean res=false;
		try {
			Statement stmt = conn.createStatement();
			stmt.execute(MssqlViaggioDAO.create);
			res = true;
			stmt.close();	
		}catch(Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public boolean dropTable() {
		// TODO Auto-generated method stub
		return false;
	}
	private Connection conn;
	public Connection getConn() {
		return conn;
	}

	public void setConn(Connection conn) {
		try {
			this.conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.conn = conn;
	}
}
