/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sqlitemanager;

/**
 *
 * @author ChaseLP
 */

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class DBConnection {

	private Connection conn;
	private String dbfilenameandpath;
	private java.sql.Statement stmt;
	public ResultSet rs;
	public Vector<String> columns;
	public Vector<String> tables;
	public Vector<String> gettables() {
		return this.tables;
	}
	public Vector<String> getcolumns() {
		return this.columns;
	}
	public boolean isconnected() {
		return (this.conn != null);
	}
	public boolean hasfilename() {
		return (this.dbfilenameandpath != "");
	}
	public DBConnection() {
		conn = null;
		this.dbfilenameandpath = "";
	}
	public void setDBPath(String dbfilename) {
		this.dbfilenameandpath = "jdbc:sqlite:" + dbfilename;
	}
	public String getDBPath() {
		return this.dbfilenameandpath.replace("jdbc:sqlite:","");
	}
	private void setTablesAndColumns() throws SQLException {
		columns = new Vector<String>();
		tables = new Vector<String>();
		
		DatabaseMetaData md = this.conn.getMetaData();
		this.rs = md.getTables(null, null, "%", null);
		while (this.rs.next()) {
			tables.add(rs.getString(3));
		}
		this.rs.close();
		if (tables.size() < 1) return;
		this.stmt = this.conn.createStatement();
		this.rs = this.stmt.executeQuery("pragma table_info(\'" + tables.firstElement() + "\')");
		while (this.rs.next()) {
			columns.add(rs.getString("name"));
		}
	}
	/**this method connects to db at dbfilenameandpath
	 * throws String = SQLException.getMessage() if error
	 */
	public void connect() throws SQLException {
        try {  
            // db parameters
            //String url = "jdbc:sqlite:C:/users/chaselp/developer/sqlite/" + dbfilename;
            // create a connection to the database
        	this.disconnect();
            conn = DriverManager.getConnection(dbfilenameandpath);
            this.setTablesAndColumns();
        } catch (SQLException e) {
            conn = null;
        	throw e;
        }
    }
	/**this method disconnects private Connection conn
	 * throws String = SQLException.getMessage() if error
	 */
	public void disconnect() throws SQLException {
        try {
            if (conn != null) {
                conn.close();
                conn = null;
            }
        } catch (SQLException e) {
            throw e;
        }
    }
    public void runQuery (String querystring) throws SQLException {
        if (conn == null) {
        	try { this.connect();
        	} catch(SQLException e) { throw e; }
        }
        try {
            stmt = conn.createStatement();
        	rs = stmt.executeQuery(querystring);
        } catch (SQLException e) { throw e; }
    }
}
