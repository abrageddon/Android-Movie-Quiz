/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cs122bp1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;                              // Enable SQL processing
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author tonkpils
 */
public class Main {

    static Connection connection;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        // Incorporate mySQL driver
        Class.forName("com.mysql.jdbc.Driver").newInstance();

        // Connect to the test database
        connection = DriverManager.getConnection("jdbc:mysql:///moviedb", "cs122b", "cs122b");

        mainMenu();

    }

    private static void getMetadata() throws SQLException {
        // Create an execute an SQL statement to select all of table"Stars" records
        Statement select = connection.createStatement();
        ResultSet result = select.executeQuery("Select * from stars");
        // Get metatdata from stars; print # of attributes in table
        System.out.println("The results of the query");
        ResultSetMetaData metadata = result.getMetaData();
        System.out.println("There are " + metadata.getColumnCount() + " columns");
        // Print type of each attribute
        for (int i = 1; i <= metadata.getColumnCount(); i++) {
            System.out.println("Type of column " + i + " is " + metadata.getColumnTypeName(i));
        }
        printStars(result);
    }

    private static void searchNames() {

        System.out.print("\n\n\nEnter search term: ");

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String readLine = null;

        try {
            readLine = br.readLine();
        } catch (IOException ioe) {
            System.out.println("Invalid Input!");
        } catch (NumberFormatException ex) {
            System.err.println("Not a valid number: " + readLine);
        }





        ResultSet result;
        try {

            result = queryNames(readLine);
            System.out.println("The results of the query");
            printStars(result);
            
        } catch (SQLException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void printStars(ResultSet result) throws SQLException {
        // print table's contents, field by field
        while (result.next()) {
            System.out.println("Id = " + result.getInt(1));
            System.out.println("Name = " + result.getString(2) + result.getString(3));
            System.out.println("DOB = " + result.getString(4));
            System.out.println("photoURL = " + result.getString(5));
            System.out.println();
        }
    }

    private static ResultSet queryNames(String readLine) throws SQLException {
        Statement select = connection.createStatement();
        ResultSet result = select.executeQuery("Select * from stars WHERE (first_name = \"" + readLine + "\" OR last_name = \"" + readLine + "\" )");
        return result;
    }

    private static void mainMenu() {
        while (true) {
            System.out.print("=== Menu ===\n"
                    + "1) Search stars by name\n"
                    + "2) Search stars by ID\n"
                    + "3) Add star to database\n"
                    + "4) Add customer to database\n"
                    + "5) Delete customer from database\n"
                    + "6) Metadata of database\n"
                    + "7) SQL query\n"
                    + "8) Log out\n"
                    + "______________________________________\n"
                    + ":");

            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String readLine = null;
            int menuChoice = 0;

            try {
                readLine = br.readLine();
                menuChoice = Integer.parseInt(readLine);
            } catch (IOException ioe) {
                System.out.println("Invalid Input!");
            } catch (NumberFormatException ex) {
                System.err.println("Not a valid number: " + readLine);
            }


            try {
                switch (menuChoice) {
                    case 1:
                        searchNames();
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                    case 5:
                        break;
                    case 6:
                        getMetadata();
                        break;
                    case 7:
                        break;
                    case 8:
                        return;
                    default:
                        break;
                }
            } catch (SQLException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
