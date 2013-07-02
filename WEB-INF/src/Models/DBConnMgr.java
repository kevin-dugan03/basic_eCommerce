package Models;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.sql.DataSource;

/**
 * Single point of contact for getting a database connection.
 * @author Kevin
 *
 */
public class DBConnMgr implements Serializable {
	
	private static final long serialVersionUID = 1L;
	Connection conn;
	
	/**
	 * Implicit Constructor.
	 */
	public DBConnMgr() {
		conn = null;
	}
	
	/**
	 * Returns a connection to the database.
	 * @return Connection The database connection
	 * @throws SQLException
	 */
	public Connection connection() throws SQLException{

		try
        {
			InitialContext ctx = new InitialContext();
			DataSource data = (DataSource) ctx.lookup("java:comp/env/jdbc/DB461");
			conn = data.getConnection();
			
			if (conn == null) {
				return null;
			}
        }
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return conn;
	}
}
