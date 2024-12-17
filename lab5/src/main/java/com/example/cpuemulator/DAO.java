package com.example.cpuemulator;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DAO {
    Connection c;
    {
        try {
            c = DriverManager.getConnection("jdbc:sqlite:instr.db");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteInstruction(String instruction) {
        String query = "DELETE FROM instructions WHERE instruction = ?";
        try {
            PreparedStatement state = c.prepareStatement(query);
            state.setString(1, instruction);
            state.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void saveInstruction(String instruction) {
        String query = "INSERT INTO instructions (instruction) VALUES (?)";
        try  {
            PreparedStatement state = c.prepareStatement(query);
            state.setString(1, instruction);
            state.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public List<String> loadInstructions() {
        List<String> instructions = new ArrayList<>();
        String query = "SELECT instruction FROM instructions";
        try {
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                instructions.add(rs.getString("instruction"));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return instructions;
    }

    public void clearInstructions() {
        String query = "DELETE FROM instructions";
        try {
            Statement stmt = c.createStatement();
            stmt.executeUpdate(query);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
