package com.bolsadeideas.springboot.web.app.controller;

import com.bolsadeideas.springboot.web.app.models.Cuenta;
import com.bolsadeideas.springboot.web.app.utils.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Date;
import java.sql.Timestamp;
public class CuentaManager {

    private static final String SQL_INSERT = "INSERT INTO cuenta (idinscripcion, monto, fecha, pagos, fechavencimiento) VALUES (?,?,?,?,?)";
    private static final String SQL = "SELECT * FROM cuenta";
    private static final String SQL_DELETE = "DELETE FROM cuenta WHERE idcuenta=?";
    private static final String SQL_MODIFY = "UPDATE cuenta SET monto=?, fecha=?, pagos=? WHERE idcuenta=?";
    private static final int Monto=480000;
    private static final byte Pagos=0;  //Pagos llega hasta 4 cuotas por semestre.

    public List<Cuenta> getAll() {

        try (Connection conn = ConnectionManager.getConnection();
             Statement statement = conn.createStatement()) {
            List<Cuenta> lista = new ArrayList<>();

            ResultSet resultSet = statement.executeQuery(SQL);

            while (resultSet.next()) {
                Cuenta cuenta = new Cuenta();
                cuenta.setIdcuenta(resultSet.getInt("idcuenta"));
                cuenta.setIdinscripcion(resultSet.getInt("idinscripcion"));
                cuenta.setFecha(resultSet.getTimestamp("fecha"));
                cuenta.setMonto(resultSet.getInt("monto"));
                cuenta.setPagos(resultSet.getByte("pagos"));
                lista.add(cuenta);
            }
            resultSet.close();
            return lista;
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        }

        return Collections.EMPTY_LIST;
    }

    public Cuenta add(int idinscripcion, Timestamp fecha, Timestamp fechavencimiento) throws SQLException {

        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement preparestatement = conn.prepareStatement(SQL_INSERT,
                     Statement.RETURN_GENERATED_KEYS)) {

            preparestatement.setInt(1, idinscripcion);
            preparestatement.setInt(2, Monto);
            preparestatement.setTimestamp(3, fecha);
            preparestatement.setByte(4, Pagos);
            preparestatement.setTimestamp(5,fechavencimiento);

            Cuenta cuenta = new Cuenta();

            int affectedRows = preparestatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }

            try (ResultSet generatedKeys = preparestatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {

                    cuenta.setIdinscripcion(idinscripcion);
                    cuenta.setMonto(Monto);
                    cuenta.setFecha(fecha);
                    cuenta.setPagos(Pagos);
                    cuenta.setFechaVencimiento(fechavencimiento);
                    cuenta.setIdcuenta(generatedKeys.getInt(1));
                    return cuenta;
                } else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
            throw e;
        }

    }

    public void delete(int idCursoHabilitado) {

        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement preparestatement = conn.prepareStatement(SQL_DELETE)) {

            preparestatement.setInt(1, idCursoHabilitado);

            preparestatement.executeUpdate();

        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        }

    }

    public void modify(int idcuenta, int idinscripcion, Timestamp fecha, Timestamp fechavencimiento, int monto, byte pagos){
        try(Connection conn = ConnectionManager.getConnection();
            PreparedStatement preparestatement = conn.prepareStatement(SQL_MODIFY)){

            preparestatement.setInt(1, idinscripcion);
            preparestatement.setTimestamp(2, fecha);
            preparestatement.setTimestamp(3, fechavencimiento);
            preparestatement.setInt(4, monto);
            preparestatement.setByte(5, pagos);
            preparestatement.setInt(1, idcuenta);

            preparestatement.executeUpdate();


        }catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        }
    }

    public Cuenta getByid(int idcuenta) {

        try (Connection conn = ConnectionManager.getConnection();
             Statement statement = conn.createStatement()) {

            ResultSet resultSet = statement.executeQuery(SQL);

            while (resultSet.next()) {

                if (resultSet.getInt("idcuenta")==idcuenta){
                    Cuenta cuenta = new Cuenta();
                    cuenta.setIdcuenta(resultSet.getInt("idcuenta"));
                    cuenta.setIdinscripcion(resultSet.getInt("idinscripcion"));
                    cuenta.setFechaVencimiento(resultSet.getTimestamp("fechavencimiento"));
                    cuenta.setFecha(resultSet.getTimestamp("fecha"));
                    cuenta.setMonto(resultSet.getInt("monto"));
                    cuenta.setPagos(resultSet.getByte("pagos"));

                    resultSet.close();
                    return cuenta;
                }

            }


        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        }
        return null;
    }

    public Timestamp sacarFecha() {
        Timestamp date = new Timestamp(0);
        long timeInMilliSeconds = date.getTime();
        java.sql.Timestamp date1 = new java.sql.Timestamp(timeInMilliSeconds);
        return date1;
    }

    public Timestamp FechaVencimiento(Date date1){
        Timestamp date2 = new Timestamp(0);
        date2.setMonth(date1.getMonth()+1);
        return date2;
    }
}
