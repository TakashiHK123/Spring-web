package com.bolsadeideas.springboot.web.app.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import com.bolsadeideas.springboot.web.app.models.Alumno;
import com.bolsadeideas.springboot.web.app.utils.ConnectionManager;



public class StudentManager {

    private static final String SQL_INSERT = "INSERT INTO alumno (idalumno, nombre, apellido) VALUES (?, ?, ?)";
    private static final String SQL = "SELECT * FROM alumno";
    private static final String SQL_DELETE = "DELETE FROM alumno WHERE idalumno=?";
    private static final String SQL_MODIFY = "UPDATE alumno SET nombre=?, apellido=? WHERE idalumno=?";

    public List<Alumno> getAllStudents() {

        try (Connection conn = ConnectionManager.getConnection();
                Statement statement = conn.createStatement()) {
            List<Alumno> listaAlumnos = new ArrayList<>();
            
            ResultSet resultSet = statement.executeQuery(SQL);
            
            while (resultSet.next()) {
                Alumno alumno = new Alumno();
                alumno.setIdAlumno(resultSet.getInt("idalumno"));
                alumno.setApellido(resultSet.getString("apellido"));
                alumno.setNombre(resultSet.getString("nombre"));
                
                listaAlumnos.add(alumno);
            }
            resultSet.close();
            return listaAlumnos;
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        }
        
        return Collections.EMPTY_LIST; 
    }

    public void add(String nombre, String apellido) {

        try (Connection conn = ConnectionManager.getConnection();
                PreparedStatement preparestatement = conn.prepareStatement(SQL_INSERT)) {
        	
        	StudentManager studentManager = new StudentManager(); 
            preparestatement.setInt(1, studentManager.generarCodigo());
            preparestatement.setString(2, nombre);
            preparestatement.setString(3, apellido);

            preparestatement.executeUpdate();

        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        }

    }

    public void delete(int idAlumno) {

        try (Connection conn = ConnectionManager.getConnection();
                PreparedStatement preparestatement = conn.prepareStatement(SQL_DELETE)) {

            preparestatement.setInt(1, idAlumno);

            preparestatement.executeUpdate();

        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        }

    }
    
    public void modify(int idAlumno, String nombre, String apellido){
        try(Connection conn = ConnectionManager.getConnection();
                PreparedStatement preparestatement = conn.prepareStatement(SQL_MODIFY)){
            
            preparestatement.setString(1, nombre);
            preparestatement.setString(2, apellido);
            preparestatement.setInt(3, idAlumno); 
            
            preparestatement.executeUpdate(); 
            
            
        }catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        }
    }

    public Alumno getByid(int idAlumno) {

        try (Connection conn = ConnectionManager.getConnection();
                Statement statement = conn.createStatement()) {
            
            ResultSet resultSet = statement.executeQuery(SQL);
            
            while (resultSet.next()) {
                              
                if (resultSet.getInt("idalumno")==idAlumno){
                    Alumno student = new Alumno();
                    student.setIdAlumno(resultSet.getInt("idalumno"));
                    student.setNombre(resultSet.getString("nombre"));
                    student.setApellido(resultSet.getString("apellido"));
                    resultSet.close();
                    return student; 
                }
           
            }
            

        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        }
        return null; 
    }
    
    public int generarCodigo(){
        UUID uuid = UUID.randomUUID();
        int codigo = uuid.hashCode();
        return codigo; 
    }

}

