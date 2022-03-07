package com.bolsadeideas.springboot.web.app.models;

import jakarta.validation.constraints.NotEmpty;
import org.jetbrains.annotations.NotNull;

import java.sql.Timestamp;
import java.util.List;

public class Cuenta {

    private static final long serialVersionUID = 1L;
    @NotNull
    private int idcuenta;
    @NotNull
    private int idinscripcion;
    @NotNull
    private int monto;
    @NotNull
    private Timestamp fecha;
    @NotNull
    private byte Pagos;
    @NotNull
    private Timestamp fechaVencimiento;

    public Cuenta() {
    }

    public Cuenta(@NotNull int idcuenta, @NotNull int idinscripcion, @NotNull int monto, @NotNull Timestamp fecha, @NotNull byte pagos, @NotNull Timestamp fechaVencimiento) {
        this.idcuenta = idcuenta;
        this.idinscripcion = idinscripcion;
        this.monto = monto;
        this.fecha = fecha;
        Pagos = pagos;
        this.fechaVencimiento = fechaVencimiento;
    }

    public int getIdcuenta() {
        return idcuenta;
    }

    public void setIdcuenta(int idcuenta) {
        this.idcuenta = idcuenta;
    }

    public int getIdinscripcion() {
        return idinscripcion;
    }

    public void setIdinscripcion(int idinscripcion) {
        this.idinscripcion = idinscripcion;
    }

    public int getMonto() {
        return monto;
    }

    public void setMonto(int monto) {
        this.monto = monto;
    }

    public Timestamp getFecha() {
        return fecha;
    }

    public void setFecha(Timestamp fecha) {
        this.fecha = fecha;
    }

    public byte getPagos() {
        return Pagos;
    }

    public void setPagos(byte pagos) {
        Pagos = pagos;
    }

    public Timestamp getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(Timestamp fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    @Override
    public String toString() {
        return "Cuenta{" +
                "idcuenta=" + idcuenta +
                ", idinscripcion=" + idinscripcion +
                ", monto=" + monto +
                ", fecha=" + fecha +
                ", Pagos=" + Pagos +
                ", fechaVencimiento=" + fechaVencimiento +
                '}'+"\n";
    }
}
