package com.bolsadeideas.springboot.web.app.controller;

import com.bolsadeideas.springboot.web.app.models.Cuenta;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/cuenta")
public class CuentaController {

    @GetMapping("/listar")
    public String lista(Model model) {
        CuentaManager cuentaManager = new CuentaManager();
        List<Cuenta> cuentas = cuentaManager.getAll();
        model.addAttribute("titulo", "Lista de Cuentas");
        model.addAttribute("idcuenta", "ID cuenta");
        model.addAttribute("idinscripcion", "ID Materia");
        model.addAttribute("monto", "ID inscripcion");
        model.addAttribute("fecha", "ID Profesor");
        model.addAttribute("fechavencimiento", "Fecha Vencimiento");
        model.addAttribute("pagos", "Pagos");
        model.addAttribute("cuentas", cuentas );
        return "cuenta-template/listar";
    }

    @GetMapping("/buscar")
    public String buscar(Model model) {
        Cuenta cuenta = new Cuenta();
        model.addAttribute("titulo", "Buscar Cuenta");
        model.addAttribute("cuenta", cuenta);
        model.addAttribute("error", new HashMap<>());
        return "cuenta-template/buscar";
    }

    @PostMapping("/buscar")
    public String buscarPro(@Valid Cuenta cuenta, BindingResult result, Model model,
                            @RequestParam(name= "idcuenta") int idcuenta) throws SQLException {

        if(result.hasGlobalErrors()) {
            Map<String, String> errores = new HashMap<>();
            result.getFieldErrors().forEach(err ->{
                errores.put(err.getField(), "El campo ".concat(err.getField()).concat(" ").concat(err.getDefaultMessage()));
            });
            model.addAttribute("titulo", "Debe ser numero entero");
            model.addAttribute("error", errores);
            return "cuenta-template/buscar";
        }
        CuentaManager cuentaManager = new CuentaManager();
        cuenta = cuentaManager.getByid(idcuenta);
        model.addAttribute("idcuenta", "ID cuenta");
        model.addAttribute("idinscripcion", "ID Inscripcion");
        model.addAttribute("fecha", "Fecha");
        model.addAttribute("fechavencimiento", "Fecha Vencimiento");
        model.addAttribute("monto", "Monto");
        model.addAttribute("pagos", "Pagos");
        model.addAttribute("cuenta", cuenta);
        return "cuenta-template/resultado";

    }
    @GetMapping("/modificar")
    public String modificar(Model model) {
        Cuenta cuenta = new Cuenta();
        model.addAttribute("titulo", "Modificar inscripcion Habilitado");
        model.addAttribute("cuenta", cuenta);
        model.addAttribute("error", new HashMap<>());
        return "cuenta-template/modificar";
    }

    @PostMapping("/modificar")
    public String modificar(@Valid Cuenta cuenta, BindingResult result, Model model,
                            @RequestParam(name="idcuenta") int idcuenta,
                            @RequestParam(name="idinscripcion") int idinscripcion,
                            @RequestParam(name="fecha") Timestamp fecha,
                            @RequestParam(name="fechavencimiento") Timestamp fechavencimiento,
                            @RequestParam(name="monto") int monto ,
                            @RequestParam(name="pagos") byte pagos ) {
        model.addAttribute("titulo", "Falta datos");
        if(result.hasErrors()) {
            Map<String, String> errores = new HashMap<>();
            result.getFieldErrors().forEach(err ->{
                errores.put(err.getField(), "El campo ".concat(err.getField()).concat(" ").concat(err.getDefaultMessage()));
            });
            model.addAttribute("titulo", "Modificar inscripcion Habilitado");
            model.addAttribute("error", errores);
            return "cuenta-template/modificar";
        }
        CuentaManager cuentaManager = new CuentaManager();
        cuentaManager.modify(idcuenta, idinscripcion, fecha, fechavencimiento,monto, pagos);

        cuenta.setIdcuenta(idcuenta);
        cuenta.setIdinscripcion(idinscripcion);
        cuenta.setFecha(fecha);
        cuenta.setFechaVencimiento(fechavencimiento);
        cuenta.setMonto(monto);
        cuenta.setPagos(pagos);
        model.addAttribute("idcuenta", "ID Cuenta");
        model.addAttribute("idinscripcion", "ID Inscripcion");
        model.addAttribute("fecha", "Fecha");
        model.addAttribute("fechavencimiento", "Fecha Vencimiento");
        model.addAttribute("monto", "monto");
        model.addAttribute("pagos", "pagos");
        model.addAttribute("cuenta", cuenta);
        return "cuenta-template/resultado";
    }
}
