package demo.demo;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.sql.*;
import java.lang.Integer;
import java.util.ArrayList;

@RestController
public class TryController {

    @RequestMapping("/display")
    @ResponseBody
    public Person display() {
        return new Person("Harshil", "taps");
    }

    @RequestMapping("/show")
    @ResponseBody
    public ArrayList<user> show() {
        // Create a variable for the connection string.
        String connectionUrl = "jdbc:mysql://localhost:3307/final_project";
        String user = "root";
        String pass = "shubham07";
        ArrayList<user> u1 = new ArrayList<user>();
        try (Connection con = DriverManager.getConnection(connectionUrl, user, pass);
                Statement stmt = con.createStatement();) {
            String SQL = "SELECT * FROM person";
            ResultSet rs = stmt.executeQuery(SQL);

            // Iterate through the data in the result set and display it.
            while (rs.next()) {
                u1.add(new user(rs.getString("name"), rs.getInt("age")));
            }

        }
        // Handle any errors that may have occurred.
        catch (SQLException e) {
            e.printStackTrace();
        }
        return u1;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/Register")
    @ResponseBody
    public void add(@RequestBody Person pItem) {
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
        }
        // Handle any errors that may have occurred.
        catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
