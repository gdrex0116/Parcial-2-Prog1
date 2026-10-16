package model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Funcion {

    private String codigo;
    private LocalDate fecha;
    private LocalTime horaInicio;
    private double precioBase;
    private Pelicula pelicula;
    private Sala sala;

    // Constructor de Funcion
    public Funcion(String codigo, LocalDate fecha, LocalTime horaInicio, double precioBase, Pelicula pelicula, Sala sala) {
        this.codigo = codigo;
        this.fecha = fecha;
        this.horaInicio = horaInicio;
        this.precioBase = precioBase;
        this.pelicula = pelicula;
        this.sala = sala;
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

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public double getPrecioBase() {
        return precioBase;
    }

    public void setPrecioBase(double precioBase) {
        this.precioBase = precioBase;
    }

    public Pelicula getPelicula() {
        return pelicula;
    }

    public Sala getSala() {
        return sala;
    }


    public LocalDateTime calcularInicio() {
        return LocalDateTime.of(fecha, horaInicio);
    }


    public LocalDateTime calcularFin() {

        return calcularInicio().plusMinutes(pelicula.duracionMinutos());
    }


    public boolean seSolapaCon(Funcion otra) {
        if (!sala.getCodigo().equals(otra.getSala().getCodigo())) {
            return false;
        }
        return calcularInicio().isBefore(otra.calcularFin())
                && otra.calcularInicio().isBefore(calcularFin());
    }


    public double calcularValorBoleta(double descuento) {
        double valorConRecargo = precioBase * (1 + sala.getRecargo());
        return valorConRecargo * (1 - descuento / 100);
    }
}
