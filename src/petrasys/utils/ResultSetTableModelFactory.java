/*
 * rpc - 2/23/10 5:29 PM This is from
 *  http://www.oreillynet.com/pub/a/oreilly/java/news/javaex_1000.html
 * getting at a generic way to create TableModels from any query, and populate
 * jTables with the ResultSet. Pretty cool!
 */
package petrasys.utils;

import java.sql.*;

/**
 * This class encapsulates a JDBC database connection and, given a SQL query
 * as a string, returns a ResultSetTableModel object suitable for display
 * in a JTable Swing component
 **/
public class ResultSetTableModelFactory {

  Connection connection;  // Holds the connection to the database

  /** The constructor method uses the arguments to create db Connection */
  public ResultSetTableModelFactory(String driverClassName, String dbname,
          String username, String password)
          throws ClassNotFoundException, SQLException {
    // Look up the JDBC driver by class name.  When the class loads, it
    // automatically registers itself with the DriverManager used in
    // the next step.
    Class driver = Class.forName(driverClassName);

    // Now use that driver to connect to the database
    connection = DriverManager.getConnection(dbname, username, password);
  }

  public ResultSetTableModelFactory() {
    this.connection = DBops.setuptradesConnection();
  }

  /**
   * This method takes a SQL query, passes it to the database, obtains the
   * results as a ResultSet, and returns a ResultSetTableModel object that
   * holds the results in a form that the Swing JTable component can use.
   *
   * @param query a Query String
   * @return a ResultSetTableModel based on Prepared query in
   * @throws SQLException
   */
  public ResultSetTableModel getResultSetTableModel(String query)
          throws SQLException {
    // If we've called close(), then we can't call this method
    if (connection == null) {
      throw new IllegalStateException("Connection already closed.");
    }

    // Create a Statement object that will be used to excecute the query.
    // The arguments specify that the returned ResultSet will be
    // scrollable, read-only, and insensitive to changes in the db.
    // rpc - 2/25/10 2:54 PM Am changing from Statement and createStatement
    PreparedStatement statement =
            connection.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE,
            ResultSet.CONCUR_READ_ONLY);
    // Run the query, creating a ResultSet
    ResultSet r = statement.executeQuery(query);
    // Create and return a TableModel for the ResultSet
    //rpc - NOTE:3/1/10 1:36 PM - a ResultSet is all thats needed to construct ResultSetTableModel
    return new ResultSetTableModel(r);
  }



  /**
   * Call this method when done with the factory to close the DB connection
   **/
  public void close() {
    try {
      connection.close();
    } // Try to close the connection
    catch (Exception e) {
    }      // Do nothing on error. At least we tried.
    connection = null;
  }

  /** Automatically close the connection when we're garbage collected */
  protected void finalize() {
    close();
  }
}
