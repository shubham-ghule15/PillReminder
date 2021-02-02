package demo.demo;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.sql.*;
import java.lang.Integer;
import java.util.ArrayList;

@RestController
public class TryController {

    /*
     * @RequestMapping("/display")
     * 
     * @ResponseBody public Person display() { return new Person("Harshil", "taps");
     * }
     * 
     * @RequestMapping("/show")
     * 
     * @ResponseBody public ArrayList<user> show() { // Create a variable for the
     * connection string. String connectionUrl =
     * "jdbc:mysql://localhost:3307/final_project"; String user = "root"; String
     * pass = "shubham07"; ArrayList<user> u1 = new ArrayList<user>(); try
     * (Connection con = DriverManager.getConnection(connectionUrl, user, pass);
     * Statement stmt = con.createStatement();) { String SQL =
     * "SELECT * FROM person"; ResultSet rs = stmt.executeQuery(SQL);
     * 
     * // Iterate through the data in the result set and display it. while
     * (rs.next()) { u1.add(new user(rs.getString("name"), rs.getInt("age"))); }
     * 
     * } // Handle any errors that may have occurred. catch (SQLException e) {
     * e.printStackTrace(); } return u1; }
     */
    @CrossOrigin(origins = "*")
    @RequestMapping(method = RequestMethod.POST, value = "/Register")
    @ResponseBody
    public ResponseEntity<?> add(@RequestBody Person pItem) {
        // Create a variable for the connection string.
        String connectionUrl = "jdbc:mysql://localhost:3307/final_project";
        String user = "root";
        String pass = "shubham07";

        try (Connection con = DriverManager.getConnection(connectionUrl, user, pass);
                Statement stmt = con.createStatement();) {

            String SQL = "insert into person(Name,Email,ContactNo,BloodGroup,DOB,weight,height) values ('"
                    + pItem.getName() + "','" + pItem.getEmail() + "','" + pItem.getContactNo() + "','"
                    + pItem.getBloodGroup() + "','" + pItem.getDOB() + "','" + pItem.getWeight() + "','"
                    + pItem.getHeight() + "')";
            stmt.executeUpdate(SQL);

            String SQL1 = "insert into registration(id,Password) values ((select id from person where Email like '"
                    + pItem.getEmail() + "'),'" + pItem.getPassword() + "')";
            stmt.executeUpdate(SQL1);
            return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
        }
        // Handle any errors that may have occurred.
        catch (SQLException e) {
            e.printStackTrace();
            return ResponseEntity.ok(new MessageResponse("Email ID Already registered!"));
        }

    }

    @CrossOrigin(origins = "*")
    @RequestMapping(method = RequestMethod.POST, value = "/Login")
    @ResponseBody
    public ResponseEntity<?> login(@RequestBody Login lItem) {
        // Create a variable for the connection string.
        String connectionUrl = "jdbc:mysql://localhost:3307/final_project";
        String user = "root";
        String pass = "shubham07";

        try (Connection con = DriverManager.getConnection(connectionUrl, user, pass);
                Statement stmt = con.createStatement();) {

            String SQL = "select * from registration where id in(select id from Person where email like '"
                    + lItem.getEmailId() + "')";
            ResultSet rs = stmt.executeQuery(SQL);
            rs.next();
            if (lItem.getPassword().equals(rs.getString("password"))) {
                String SQL1 = "update registration set LoggedIn=true where id='" + rs.getString("id") + "'";
                stmt.executeUpdate(SQL1);
            }
            return ResponseEntity.ok(new MessageResponse("User Logged In successfully!"));
        }
        // Handle any errors that may have occurred.
        catch (SQLException e) {
            e.printStackTrace();
            return ResponseEntity.ok(new MessageResponse("Error!"));
        }

    }

    /*
     * @RequestMapping(method = RequestMethod.POST, value = "/AddMedicalHistory")
     * 
     * @ResponseBody public void addMedicalHistory(@RequestBody MedicalHistory
     * mItem) { // Create a variable for the connection string. String connectionUrl
     * = "jdbc:mysql://localhost:3307/final_project"; String user = "root"; String
     * pass = "shubham07";
     * 
     * try (Connection con = DriverManager.getConnection(connectionUrl, user, pass);
     * Statement stmt = con.createStatement();) {
     * 
     * String SQL =
     * "insert into medicalHistory(id,illness,DoctorDetails,medicine,startDate,endDate,DosageAmount,DosageFrequency,DosageTime) values ('"
     * + pItem.getName() + "','" + pItem.getEmail() + "','" + pItem.getContactNo() +
     * "','" + pItem.getBloodGroup() + "','" + pItem.getDOB() + "','" +
     * pItem.getWeight() + "','" + pItem.getHeight() + "')";
     * stmt.executeUpdate(SQL);
     * 
     * String SQL1 =
     * "insert into registration(id,Password) values ((select id from person where Email like '"
     * + pItem.getEmail() + "'),'" + pItem.getPassword() + "')";
     * stmt.executeUpdate(SQL1); } // Handle any errors that may have occurred.
     * catch (SQLException e) { e.printStackTrace(); }
     * 
     * }
     */
}
