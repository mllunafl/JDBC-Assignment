import javax.xml.crypto.Data;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.concurrent.locks.StampedLock;

public class Main {

    public static void main(String[] args) {

        try (Connection conn = DatabaseUtils.getInstance().getConnection()) {
            PreparedStatement pStmt = conn.prepareStatement("UPDATE person SET contacted = ? WHERE id = ?");

            LocalDate localDate = LocalDate.of(2017, 1, 1);
            long epochMilliseconds = localDate.atStartOfDay(ZoneId.systemDefault()).toEpochSecond() * 1000;

            pStmt.setDate(1, new java.sql.Date(epochMilliseconds));
            pStmt.setInt(2, 3);
            int rslt = pStmt.executeUpdate();
            System.out.println(rslt);

            localDate = LocalDate.of(2016, 11, 1);
            epochMilliseconds = localDate.atStartOfDay(ZoneId.systemDefault()).toEpochSecond() * 1000;

            pStmt.setDate(1, new java.sql.Date(epochMilliseconds));
            pStmt.setInt(2, 5);
            rslt = pStmt.executeUpdate();
            System.out.println(rslt);

            pStmt = conn.prepareStatement("INSERT INTO person (name,dob,contacted) VALUES (?,?,?)");
            pStmt.setString(1, "Sybil Six");

            localDate = LocalDate.of(1991, 6, 6);
            epochMilliseconds = localDate.atStartOfDay(ZoneId.systemDefault()).toEpochSecond() * 1000;
            pStmt.setDate(2, new java.sql.Date(epochMilliseconds));


            localDate = LocalDate.of(2017, 1, 4);
            epochMilliseconds = localDate.atStartOfDay(ZoneId.systemDefault()).toEpochSecond() * 1000;
            pStmt.setDate(3, new java.sql.Date(epochMilliseconds));
            //rslt = pStmt.executeUpdate();
            //System.out.println(rslt);

            PreparedStatement pStmtEmail = conn.prepareStatement("INSERT INTO email (email,person_id) VALUES (?,?)");
            pStmt.setString(1, "sybil@six.com");
            pStmt.setInt(2, 9);
            //rslt = pStmt.executeUpdate();
            //System.out.println(rslt);

            pStmt = conn.prepareStatement("DELETE from email WHERE person_id = ?");
            pStmt.setInt(1, 9);
            //rslt =pStmt.executeUpdate();
            //System.out.println(rslt);

            pStmtEmail.setString(1, "sybil@six.com");
            pStmtEmail.setInt(2, 9);
            //rslt = pStmt.executeUpdate();
            //System.out.println(rslt);

            pStmtEmail.setString(1, "sybilsecond@six.org");
            pStmtEmail.setInt(2, 9);
            //rslt = pStmt.executeUpdate();
            //System.out.println(rslt);

            pStmt = conn.prepareStatement("INSERT INTO address (street1,city,stateAbbr,zip,person_id) VALUES (?,?,?,?,?)");
            pStmt.setString(1, "345 Congress St");
            pStmt.setString(2, "Austin");
            pStmt.setString(3, "TX");
            pStmt.setString(4, "87633");
            pStmt.setInt(5, 9);
            //rslt = pStmt.executeUpdate();
            //System.out.println(rslt);

        } catch (SQLException e) {

        }

        try (Connection conn = DatabaseUtils.getInstance().getConnection()) {

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from person");
            System.out.println("id,name,dob,gender,contacted");
            while (rs.next()) {
                StringBuilder sb = new StringBuilder();
                sb.append(rs.getInt("id") + ",");
                sb.append(rs.getString("name") + ",");

                Timestamp timestamp = rs.getTimestamp("dob");
                LocalDateTime ldt = timestamp.toLocalDateTime();
                LocalDate ld = LocalDate.from(ldt);
                sb.append(ld + ",");

                sb.append(rs.getString("gender") + ",");

                timestamp = rs.getTimestamp("contacted");
                if (timestamp != null) {
                    ldt = timestamp.toLocalDateTime();
                    ld = LocalDate.from(ldt);
                    sb.append(ld + ",");
                }
                System.out.println(sb.toString());
            }

            rs = stmt.executeQuery("select * from address");
            System.out.println("id,street1,street2,city,stateAbbr,zip,person_id");
            while (rs.next()) {
                StringBuilder sb = new StringBuilder();
                sb.append(rs.getInt("id") + ",");
                sb.append(rs.getString("street1") + ",");
                sb.append(rs.getString("street2") + ",");
                sb.append(rs.getString("city") + ",");
                sb.append(rs.getString("stateAbbr") + ",");
                sb.append(rs.getString("zip") + ",");
                sb.append(rs.getInt("person_id"));
                System.out.println(sb.toString());
            }

            rs = stmt.executeQuery("select * from email");
            System.out.println("id,email,person_id");
            while (rs.next()) {
                StringBuilder sb = new StringBuilder();
                sb.append(rs.getInt("id") + ",");
                sb.append(rs.getString("email") + ",");
                sb.append(rs.getInt("person_id") + ",");
                System.out.println(sb.toString());
            }

            rs = stmt.executeQuery("select p.id, p.name, p.dob, p.gender, p.contacted, a.street1, a.street2, a.city, a.stateAbbr, a.zip, e.email from person p left join address a on p.id = a.person_id left join email e on p.id = e.person_id");

            System.out.println("id,name,dob,gender,contacted,street1,street2,city,stateAbbr,zip,email");
            while (rs.next()){
                StringBuilder sb = new StringBuilder();
                sb.append(rs.getInt("id") + ",");
                sb.append(rs.getString("name") + ",");

                Timestamp timestamp = rs.getTimestamp("dob");
                LocalDateTime ldt = timestamp.toLocalDateTime();
                LocalDate ld = LocalDate.from(ldt);
                sb.append(ld + ",");

                sb.append(rs.getString("gender") + ",");

                timestamp = rs.getTimestamp("contacted");
                if (timestamp != null) {
                    ldt = timestamp.toLocalDateTime();
                    ld = LocalDate.from(ldt);
                    sb.append(ld + ",");
                }

                sb.append(rs.getString("street1") + ",");
                sb.append(rs.getString("street2") + ",");
                sb.append(rs.getString("city") + ",");
                sb.append(rs.getString("stateAbbr") + ",");
                sb.append(rs.getString("zip") + ",");
                sb.append(rs.getString("email"));
               System.out.println(sb.toString());
            }

        } catch (SQLException e) {

        }
    }
}

