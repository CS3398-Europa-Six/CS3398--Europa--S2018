/** ChaseLP
 * This Java program uses the sql file made by csv2sql to create a SQLite database
 * This code is using JDBC for Java to work with SQLite
 */

package jsqlite;

import java.beans.Statement;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
 
public class dbmain {
 
	/***
	 * this creates a new (empty) SQLite database
	 */
	public static void createNewDatabase(String dbfilename) {
        String url = "jdbc:sqlite:C:/users/chaselp/developer/sqlite/" + dbfilename;
         try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created.");
            }
         } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
	/***
	 * this only connects to the database ... not useful in itself
	 */
    public static void connect(String dbfilename) {  
        Connection conn = null;
        try {  
            // db parameters  
            String url = "jdbc:sqlite:C:/users/chaselp/developer/sqlite/" + dbfilename;
            // create a connection to the database  
            conn = DriverManager.getConnection(url);  
              
            System.out.println("Connection to SQLite has been established.");  
              
        } catch (SQLException e) {  
            System.out.println(e.getMessage());  
        } finally {  
            try {  
                if (conn != null) {  
                    conn.close();  
                }  
            } catch (SQLException ex) {  
                System.out.println(ex.getMessage());  
            }  
        }
    }
    /***
     *  The method for runQuery, when usefile is false, will run just the query
     *  though for testing it selects every vehicle manufacturer and prints it
     *  this shows how select can work... from the file runs commands line by line
     *  from a file ... I used this to convert the epadb.sql file to an actual SQLite file
     *  the epadb.sql file was made by the csv2sql c++ program and can be modified
     *  for different seperators / etc .... also columns in the csv can be ignored
     */
    public static void runQuery
    (String dbfilename, String querystring, boolean usefile) {  
        Connection conn = null;
        try {
            // db parameters  
            String url = "jdbc:sqlite:C:/users/chaselp/developer/sqlite/" + dbfilename;
            // create a connection to the database  
            conn = DriverManager.getConnection(url);  
              
            System.out.println("Connection to SQLite has been established.");  

            java.sql.Statement stmt = conn.createStatement();
            if (!usefile) {
            	ResultSet rs = stmt.executeQuery(querystring);
            	short countz = 0;
            	while (rs.next()) {
            		++countz;
            		String s = rs.getString("VehicleManufacturerName");
            		System.out.print(s);
            		if (countz % 10 == 0)
            			System.out.print('\n');
            		else System.out.print(", ");
            	}
            }
            else try {
				BufferedReader infile =
						new BufferedReader(new FileReader(querystring));
				String buffa = infile.readLine();
				int count = 0;
				System.out.print("count = ");
				while (buffa != null) {
					++count;
					System.out.print(count);
					if (count % 30 != 0) 
						System.out.print(", ");
					else System.out.print('\n');
					stmt.executeUpdate(buffa);
					buffa = infile.readLine();
				}
				System.out.print("\ncommands finished\n");
				infile.close();
            } catch(IOException e) { System.out.println(e.getMessage()); }
        } catch (SQLException e) { System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null) {  
                    conn.close();  
                }  
            } catch (SQLException ex) {  
                System.out.println(ex.getMessage());  
            }  
        }
    }
    /***
     *  main driver program ... used it to build / test db
     */
    public static void main(String[] args) {
    	try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	System.out.println("Creating database ... ");
        createNewDatabase("cqa.db");
//      System.out.println("Connecting to database ... ");
//      connect("clptest.db");
      System.out.println("Running query file ... ");
      runQuery("cqa.db","c:/users/chaselp/developer/sqlite/cqa.sql",true);
//
//    	System.out.println("select manufacturers from epa2016");
//      runQuery("clptest.db","select VehicleManufacturerName from epa2016",false);

    }
}