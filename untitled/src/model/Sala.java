package model;

public class Sala {

    private String codigo;
    private String nombre;
    private int capacidadMaxima;
    private int filas;
    private int columnas;
    private TipoSala tipo;

    // Constructor de Sala
    public Sala(String codigo, String nombre, int filas, int columnas, TipoSala tipo) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.filas = filas;
        this.columnas = columnas;
        this.capacidadMaxima = filas * columnas;
        this.tipo = tipo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCapacidadMaxima() {
        return capacidadMaxima;
    }

    public int getFilas() {
        return filas;
    }

    public int getColumnas() {
        return columnas;
    }

    public TipoSala getTipo() {
        return tipo;
    }

    public void setTipo(TipoSala tipo) {
        this.tipo = tipo;
    }


    public double getRecargo() {
        switch (tipo) {
            case VIP:
                return 0.30;
            case TRES_D:
                return 0.20;
            default:
                return 0.0;
        }
    }


    public boolean esAsientoValido(int fila, int numero) {
        return fila >= 1 && fila <= filas && numero >= 1 && numero <= columnas;
    }
}
