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

    // Show logged in user's details
    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/ShowUserDetails", method = { RequestMethod.POST, RequestMethod.GET })
    @ResponseBody
    public Person show() { // Create a variable for the
        String connectionUrl = "jdbc:mysql://localhost:3307/final_project";
        String user = "root";
        String pass = "shubham07";
        Person p1;
        try (Connection con = DriverManager.getConnection(connectionUrl, user, pass);
                Statement stmt = con.createStatement();) {

            String SQL = "SELECT * FROM person where id in(select id from registration where LoggedIn like true)";
            ResultSet rs = stmt.executeQuery(SQL);

            // Iterate through the data in the result set and display it.
            rs.next();
            p1 = new Person(rs.getString("Name"), rs.getString("Email"), rs.getString("BloodGroup"),
                    rs.getLong("ContactNo"), rs.getDate("DOB"), rs.getFloat("weight"), rs.getFloat("height"));
            return p1;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/GetPersonName", method = { RequestMethod.POST, RequestMethod.GET })
    @ResponseBody
    public Person getUserName() { // Create a variable for the
        String connectionUrl = "jdbc:mysql://localhost:3307/final_project";
        String user = "root";
        String pass = "shubham07";
        Person p1;
        try (Connection con = DriverManager.getConnection(connectionUrl, user, pass);
                Statement stmt = con.createStatement();) {

            String SQL = "SELECT Name FROM person where id in(select id from registration where LoggedIn like true)";
            ResultSet rs = stmt.executeQuery(SQL);

            // Iterate through the data in the result set and display it.
            rs.next();
            p1 = new Person(rs.getString("Name"));
            return p1;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

    // Show dependents' details
    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/ShowDependentDetails", method = { RequestMethod.POST, RequestMethod.GET })
    @ResponseBody
    public Person showDependent(@RequestBody SendDependentRelationship sItem) { // Create a variable for the
        String connectionUrl = "jdbc:mysql://localhost:3307/final_project";
        String user = "root";
        String pass = "shubham07";
        Person p1;
        try (Connection con = DriverManager.getConnection(connectionUrl, user, pass);
                Statement stmt = con.createStatement();) {

            String SQL = "SELECT * FROM person where id in(select did from relationship where id in(select id from registration where LoggedIn like true) and relation like '"
                    + sItem.getRelation() + "')";
            ResultSet rs = stmt.executeQuery(SQL);

            // Iterate through the data in the result set and display it.
            if (rs.next()) {
                p1 = new Person(rs.getString("Name"), rs.getString("Email"), rs.getString("BloodGroup"),
                        rs.getLong("ContactNo"), rs.getDate("DOB"), rs.getFloat("weight"), rs.getFloat("height"));
                return p1;
            } else {
                Person p2 = new Person();
                return p2;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

    // Register new user
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
    @RequestMapping(method = RequestMethod.PUT, value = "/UpdateUserData")
    @ResponseBody
    public ResponseEntity<?> update(@RequestBody UpdatePerson pItem) {
        // Create a variable for the connection string.
        String connectionUrl = "jdbc:mysql://localhost:3307/final_project";
        String user = "root";
        String pass = "shubham07";

        try (Connection con = DriverManager.getConnection(connectionUrl, user, pass);
                Statement stmt = con.createStatement();) {

            String SQL = "update person set Name='" + pItem.getName() + "',Email ='" + pItem.getEmail()
                    + "' ,ContactNo ='" + pItem.getContactNo() + "',BloodGroup ='" + pItem.getBloodGroup() + "',DOB ='"
                    + pItem.getDOB() + "',weight ='" + pItem.getWeight() + "',height='" + pItem.getHeight()
                    + "' where id in (select id from registration where LoggedIn like true)";
            stmt.executeUpdate(SQL);

            return ResponseEntity.ok(new MessageResponse("User updated successfully!"));
        }
        // Handle any errors that may have occurred.
        catch (SQLException e) {
            e.printStackTrace();
            return ResponseEntity.ok(new MessageResponse("Error!"));
        }

    }

    // Login
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
                return ResponseEntity.ok(new MessageResponse("User Logged In successfully!"));
            }
            return ResponseEntity.ok(new MessageResponse("Wrong Password!"));
        }
        // Handle any errors that may have occurred.
        catch (SQLException e) {
            e.printStackTrace();
            return ResponseEntity.ok(new MessageResponse("Error!"));
        }

    }

    @CrossOrigin(origins = "*")
    @RequestMapping(method = RequestMethod.POST, value = "/ResetPassword")
    @ResponseBody
    public ResponseEntity<?> resetPassword(@RequestBody ResetPassword pItem) {
        // Create a variable for the connection string.
        String connectionUrl = "jdbc:mysql://localhost:3307/final_project";
        String user = "root";
        String pass = "shubham07";

        try (Connection con = DriverManager.getConnection(connectionUrl, user, pass);
                Statement stmt = con.createStatement();) {

            String SQL = "select * from registration where LoggedIn like true";
            ResultSet rs = stmt.executeQuery(SQL);
            rs.next();

            if (pItem.getOldpassword().equals(rs.getString("Password"))) {
                String SQL1 = "update registration set Password = '" + pItem.getNewpassword()
                        + "' where LoggedIn like true";
                stmt.executeUpdate(SQL1);
                return ResponseEntity.ok(new MessageResponse("Password Updated successfully!"));
            }

            return ResponseEntity.ok(new MessageResponse("Enter correct current password!"));
        }
        // Handle any errors that may have occurred.
        catch (SQLException e) {
            e.printStackTrace();
            return ResponseEntity.ok(new MessageResponse("Error!"));
        }

    }

    // Add dependent
    @CrossOrigin(origins = "*")
    @RequestMapping(method = RequestMethod.POST, value = "/AddDependent")
    @ResponseBody
    public ResponseEntity<?> addDependent(@RequestBody Dependent dItem) {
        // Create a variable for the connection string.
        String connectionUrl = "jdbc:mysql://localhost:3307/final_project";
        String user = "root";
        String pass = "shubham07";

        try (Connection con = DriverManager.getConnection(connectionUrl, user, pass);
                Statement stmt = con.createStatement();) {

            String SQL = "insert into person(Name,Email,ContactNo,BloodGroup,DOB,weight,height) values ('"
                    + dItem.getName() + "','" + dItem.getEmail() + "','" + dItem.getContactNo() + "','"
                    + dItem.getBloodGroup() + "','" + dItem.getDOB() + "','" + dItem.getWeight() + "','"
                    + dItem.getHeight() + "')";
            stmt.executeUpdate(SQL);

            String SQL1 = "insert into relationship(id,relation,did) values ((select id from registration where LoggedIn like true),'"
                    + dItem.getRelationship() + "',(select id from person where Email like '" + dItem.getEmail()
                    + "'))";
            stmt.executeUpdate(SQL1);

            return ResponseEntity.ok(new MessageResponse("Dependent Added successfully!"));
        }
        // Handle any errors that may have occurred.
        catch (SQLException e) {
            e.printStackTrace();
            return ResponseEntity.ok(new MessageResponse("Email ID Already registered!"));
        }

    }

    @CrossOrigin(origins = "*")
    @RequestMapping(method = RequestMethod.PUT, value = "/UpdateDependent")
    @ResponseBody
    public ResponseEntity<?> UpdateDependent(@RequestBody Dependent dItem) {
        // Create a variable for the connection string.
        String connectionUrl = "jdbc:mysql://localhost:3307/final_project";
        String user = "root";
        String pass = "shubham07";

        try (Connection con = DriverManager.getConnection(connectionUrl, user, pass);
                Statement stmt = con.createStatement();) {

            String SQL = "update person set Name='" + dItem.getName() + "',Email ='" + dItem.getEmail()
                    + "' ,ContactNo ='" + dItem.getContactNo() + "',BloodGroup ='" + dItem.getBloodGroup() + "',DOB ='"
                    + dItem.getDOB() + "',weight ='" + dItem.getWeight() + "',height='" + dItem.getHeight()
                    + "' where id in(select did from relationship where relation like '" + dItem.getRelationship()
                    + "' and id in(select id from registration where LoggedIn like true))";
            stmt.executeUpdate(SQL);

            return ResponseEntity.ok(new MessageResponse("Dependent Updated successfully!"));
        }
        // Handle any errors that may have occurred.
        catch (SQLException e) {
            e.printStackTrace();
            return ResponseEntity.ok(new MessageResponse("Error!"));
        }

    }

    // Logout
    @CrossOrigin(origins = "*")
    @RequestMapping(method = { RequestMethod.POST, RequestMethod.GET }, value = "/Logout")
    @ResponseBody
    public ResponseEntity<?> logout() {
        // Create a variable for the connection string.
        String connectionUrl = "jdbc:mysql://localhost:3307/final_project";
        String user = "root";
        String pass = "shubham07";

        try (Connection con = DriverManager.getConnection(connectionUrl, user, pass);
                Statement stmt = con.createStatement();) {

            String SQL = "update registration set LoggedIn = false where LoggedIn =true";
            stmt.executeUpdate(SQL);

            return ResponseEntity.ok(new MessageResponse("Logged Out Successfully!"));
        }
        // Handle any errors that may have occurred.
        catch (SQLException e) {
            e.printStackTrace();
            return ResponseEntity.ok(new MessageResponse("Error!"));
        }

    }

    // Add medical history
    @CrossOrigin(origins = "*")
    @RequestMapping(method = { RequestMethod.POST, RequestMethod.GET }, value = "/AddMedicalHistory")
    @ResponseBody
    public ResponseEntity<?> addMedicalHistory(@RequestBody MedicalHistory mItem) {
        // Create a variable for the connection string.
        String connectionUrl = "jdbc:mysql://localhost:3307/final_project";
        String user = "root";
        String pass = "shubham07";

        try (Connection con = DriverManager.getConnection(connectionUrl, user, pass);
                Statement stmt = con.createStatement();) {

            if (mItem.getRelation().equalsIgnoreCase("self")) {
                String SQL = "insert into medicalHistory(id,illness,DoctorDetails,medicine,startDate,endDate,DosageAmount,DosageFrequency,DosageTime) values ((select id from registration where LoggedIn like true),'"
                        + mItem.getIllness() + "','" + mItem.getDoctorDetails() + "','" + mItem.getMedicine() + "','"
                        + mItem.getStartDate() + "','" + mItem.getEndDate() + "','" + mItem.getDosageAmount() + "','"
                        + mItem.getDosageFrequency() + "','" + mItem.getDosageTime() + "')";
                stmt.executeUpdate(SQL);
            } else {
                String SQL = "insert into medicalHistory(id,illness,DoctorDetails,medicine,startDate,endDate,DosageAmount,DosageFrequency,DosageTime) values ((select did from relationship where id in(select id from registration where LoggedIn like true) and relation like '"
                        + mItem.getRelation() + "'),'" + mItem.getIllness() + "','" + mItem.getDoctorDetails() + "','"
                        + mItem.getMedicine() + "','" + mItem.getStartDate() + "','" + mItem.getEndDate() + "','"
                        + mItem.getDosageAmount() + "','" + mItem.getDosageFrequency() + "','" + mItem.getDosageTime()
                        + "')";
                stmt.executeUpdate(SQL);
            }

            return ResponseEntity.ok(new MessageResponse("Medical History Added Successfully!"));

        } // Handle any errors that may have occurred.
        catch (SQLException e) {
            e.printStackTrace();
            return ResponseEntity.ok(new MessageResponse("Error!"));
        }

    }

    // Show medical history
    @CrossOrigin(origins = "*")
    @RequestMapping(method = { RequestMethod.POST, RequestMethod.GET }, value = "/ShowMedicalHistory")
    @ResponseBody
    public MedicalHistory showMedicalHistory(@RequestBody MedicalHistory mItem) { // Create a variable for the
        String connectionUrl = "jdbc:mysql://localhost:3307/final_project";
        String user = "root";
        String pass = "shubham07";
        MedicalHistory m1;
        try (Connection con = DriverManager.getConnection(connectionUrl, user, pass);
                Statement stmt = con.createStatement();) {

            if (mItem.getRelation().equalsIgnoreCase("self")) {
                String SQL = "SELECT * FROM medicalhistory where id in(select id from registration where LoggedIn like true)";
                ResultSet rs = stmt.executeQuery(SQL);

                // Iterate through the data in the result set and display it.
                if (rs.next()) {
                    // rs.next();
                    m1 = new MedicalHistory(rs.getString("illness"), rs.getString("DoctorDetails"),
                            rs.getString("medicine"), rs.getString("DosageAmount"), rs.getString("DosageFrequency"),
                            rs.getDate("startDate"), rs.getDate("endDate"), rs.getTime("DosageTime"),
                            rs.getBoolean("EmailNotification"));
                    return m1;
                } else {
                    MedicalHistory m2 = new MedicalHistory("Relation not found");
                    return m2;
                }
            } else {
                String SQL = "SELECT * FROM medicalhistory where id in(select did from relationship where id in(select id from registration where LoggedIn like true and relation like '"
                        + mItem.getRelation() + "'))";
                ResultSet rs = stmt.executeQuery(SQL);

                // Iterate through the data in the result set and display it.
                if (rs.next()) {
                    // rs.next();
                    m1 = new MedicalHistory(rs.getString("illness"), rs.getString("DoctorDetails"),
                            rs.getString("medicine"), rs.getString("DosageAmount"), rs.getString("DosageFrequency"),
                            rs.getDate("startDate"), rs.getDate("endDate"), rs.getTime("DosageTime"),
                            rs.getBoolean("EmailNotification"));
                    return m1;
                } else {
                    MedicalHistory m2 = new MedicalHistory("Relation not found");
                    return m2;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

    @CrossOrigin(origins = "*")
    @RequestMapping(method = { RequestMethod.DELETE, RequestMethod.GET }, value = "/DeleteMedicalHistory")
    @ResponseBody
    public ResponseEntity<?> DeleteMedicalHistory(@RequestBody MedicalHistory mItem) {
        // Create a variable for the connection string.
        String connectionUrl = "jdbc:mysql://localhost:3307/final_project";
        String user = "root";
        String pass = "shubham07";

        try (Connection con = DriverManager.getConnection(connectionUrl, user, pass);
                Statement stmt = con.createStatement();) {

            if (mItem.getRelation().equals("self")) {
                String SQL = "Delete from medicalhistory where id in(select id from registration where LoggedIn like true) ";
                stmt.executeUpdate(SQL);
                return ResponseEntity.ok(new MessageResponse("Self Medical history Deleted Successfully!"));
            } else {
                String SQL = "Delete from medicalhistory where id in(select did from relationship where relation like '"
                        + mItem.getRelation() + "' and id in(select id from registration where LoggedIn like true))   ";
                stmt.executeUpdate(SQL);
                return ResponseEntity.ok(new MessageResponse("Dependent Medical HistoryDeleted Successfully!"));
            }

        } // Handle any errors that may have occurred.
        catch (SQLException e) {
            e.printStackTrace();
            return ResponseEntity.ok(new MessageResponse("Error!"));
        }

    }

}
