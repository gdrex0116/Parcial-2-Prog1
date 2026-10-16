package model;

import java.time.LocalDate;
import java.util.ArrayList;

public class Venta {

    private String codigo;
    private LocalDate fecha;
    private Cliente cliente;
    private Operario operario;
    private Funcion funcion;
    private ArrayList<Boleta> boletas;

    // Constructor de Venta
    public Venta(String codigo, LocalDate fecha, Cliente cliente, Operario operario, Funcion funcion) {
        this.codigo = codigo;
        this.fecha = fecha;
        this.cliente = cliente;
        this.operario = operario;
        this.funcion = funcion;
        this.boletas = new ArrayList<>();
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Operario getOperario() {
        return operario;
    }

    public Funcion getFuncion() {
        return funcion;
    }

    public ArrayList<Boleta> getBoletas() {
        return boletas;
    }


    public void agregarBoleta(Boleta boleta) {
        boletas.add(boleta);
    }


    public boolean tieneAsiento(int fila, int numero) {
        for (Boleta boleta : boletas) {
            if (boleta.fila() == fila && boleta.numeroAsiento() == numero) {
                return true;
            }
        }
        return false;
    }


    public double calcularTotal() {
        double total = 0;
        for (Boleta boleta : boletas) {
            total += boleta.valor();
        }
        return total;
    }


    public int contarBoletas() {
        return boletas.size();
    }
}
