/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cs122bp1;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
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
        boolean step = false;
        // Create an execute an SQL statement to select all of table"Stars" records
        Statement select = connection.createStatement();
        Statement tableQuery = connection.createStatement();
        ResultSet tableList = tableQuery.executeQuery("SHOW TABLES");

        System.out.println("\n\n\n______________________________________");


        while (tableList.next()) {
            if (step) {
                pause();
            } else {
                step = true;
            }
            String table = tableList.getString(1);
            System.out.println("\nTABLE: " + table);

            ResultSet result = select.executeQuery("Select * from " + table);
            // Get metatdata from stars; print # of attributes in table
            //System.out.println("The results of the query");
            ResultSetMetaData metadata = result.getMetaData();
            System.out.println("There are " + metadata.getColumnCount() + " columns");
            // Print type of each attribute
            for (int i = 1; i <= metadata.getColumnCount(); i++) {
                System.out.println("(" + i + ") " + metadata.getColumnName(i) + " :: " + metadata.getColumnTypeName(i));
            }
            System.out.println("\n______________________________________");

        }
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
            System.out.println("\nThe results of the query\n");
            printStars(result);

        } catch (SQLException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void searchIDs() {

        System.out.print("\n\n\nEnter star ID: ");

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

            result = queryID(readLine);
            System.out.println("\nThe results of the query\n");
            printStars(result);

        } catch (SQLException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void printStars(ResultSet result) throws SQLException {
        // print table's contents, field by field
        int count = 0;
        while (result.next()) {
            System.out.println("ID = " + result.getInt(1));
            System.out.println("Name = " + result.getString(2) + " " + result.getString(3));
            System.out.println("DOB = " + result.getString(4));
            System.out.println("photoURL = " + result.getString(5));
            if (++count % 3 == 0) {
                System.out.println("______________________________________");
                pause();
                System.out.println("______________________________________\n");
            } else {
                System.out.println();
            }
        }
    }

    private static ResultSet queryNames(String readLine) throws SQLException {
        Statement select = connection.createStatement();
        ResultSet result = select.executeQuery("Select * from stars WHERE (first_name = '" + readLine + "' OR last_name = '" + readLine + "' )");
        return result;
    }

    private static ResultSet queryID(String readLine) throws SQLException {
        Statement select = connection.createStatement();
        ResultSet result = select.executeQuery("Select * from stars WHERE (id = '" + readLine + "' )");
        return result;
    }

    private static void mainMenu() {
        while (true) {
            System.out.print("\n\n\n\n=== Menu ===\n"
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
                        pause();
                        break;
                    case 2:
                        searchIDs();
                        pause();
                        break;
                    case 3:
                        addStarMenu();
                        break;
                    case 4:
                        addCustomerMenu();
                        pause();
                        break;
                    case 5:
                        deleteCustomerMenu();
                        pause();
                        break;
                    case 6:
                        getMetadata();
                        pause();
                        break;
                    case 7:
                        openQueryMenu();
                        pause();
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

    private static void pause() {
        BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Press Enter to continue:");
        try {
            int ch = stdin.read();
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private static int addStar(Integer id, String firstName, String lastName, String dob, String imgURL) {
        try {
            Statement update = connection.createStatement();
            int retID = update.executeUpdate("INSERT INTO stars VALUES("
                    + id + ", '"
                    + firstName + "', '"
                    + lastName + "', DATE('"
                    + dob + "'), '"
                    + imgURL + "');");
            //System.out.println("retID = " + retID);
            return retID;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return 0;
    }

    private static void addStarMenu() {
        int id = 0;
        String firstName = "";
        String lastName = "";
        String dob = "";
        String imageURL = "";

        while (true) {
            System.out.print("\n\n\n\n=== Add Star Menu ===\n"
                    + "\n"
                    + "ID (0=Auto): " + id + "\n"
                    + "First Name : " + firstName + "\n"
                    + "Last Name  : " + lastName + "\n"
                    + "D.O.B.     : " + dob + "\n"
                    + "Image URL  : " + imageURL + "\n"
                    + "\n"
                    + "1) Set ID\n"
                    + "2) Set First and Last Name\n"
                    + "3) Set Single Name\n"
                    + "4) Set Date of Birth\n"
                    + "5) Set Image URL\n"
                    + "6) Add Star\n"
                    + "0) Cancel\n"
                    + "______________________________________\n"
                    + ":");

            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String readLine = null;
            int menuChoice = -1;

            try {
                readLine = br.readLine();
                menuChoice = Integer.parseInt(readLine);


                switch (menuChoice) {
                    case 1:
                        //TODO add star ID editable?
                        System.out.print("Enter ID:");
                        readLine = br.readLine();
                        id = Integer.parseInt(readLine);
                        break;
                    case 2:
                        System.out.print("Enter First Name:");
                        firstName = br.readLine();
                        System.out.print("Enter Last Name:");
                        lastName = br.readLine();
                        break;
                    case 3:
                        System.out.print("Enter Name:");
                        firstName = "";
                        lastName = br.readLine();
                        break;
                    case 4:
                        System.out.print("Enter Date of Birth (YYYY/MM/DD):");
                        dob = br.readLine();
                        break;
                    case 5:
                        System.out.print("Enter Image URL:");
                        imageURL = br.readLine();
                        break;
                    case 6:
                        int added = 0;

                        added = addStar(id, firstName, lastName, dob, imageURL);
                        if (added != 0) {
                            System.out.println("______________________________________\n"
                                    + firstName + " " + lastName + " successfully added\n"
                                    + "______________________________________");
                            pause();
                        } else {
                            System.out.println("______________________________________\n"
                                    + firstName + " " + lastName + " NOT added\n"
                                    + "______________________________________");
                            pause();
                            break;
                        }
                        return;
                    case 0:
                        return;
                    default:
                        break;
                }

            } catch (IOException ioe) {
                System.out.println("Invalid Input!");
            } catch (NumberFormatException ex) {
                System.err.println("Not a valid number: " + readLine);
//            } catch (MySQLIntegrityConstraintViolationException e){}
            }



        }
    }

    private static void addCustomerMenu() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private static void deleteCustomerMenu() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private static void openQueryMenu() {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
