package model;

import java.util.ArrayList;

public class Cine {

    private String nombreComercial;
    private String nit;
    private String direccion;
    private ArrayList<Persona> personas;
    private ArrayList<Sala> salas;
    private ArrayList<Pelicula> peliculas;
    private ArrayList<Funcion> funciones;
    private ArrayList<Venta> ventas;

    // Constructor de Cine
    public Cine(String nombreComercial, String nit, String direccion) {
        this.nombreComercial = nombreComercial;
        this.nit = nit;
        this.direccion = direccion;
        this.personas = new ArrayList<>();
        this.salas = new ArrayList<>();
        this.peliculas = new ArrayList<>();
        this.funciones = new ArrayList<>();
        this.ventas = new ArrayList<>();
    }

    public String getNombreComercial() {
        return nombreComercial;
    }

    public String getNit() {
        return nit;
    }

    public String getDireccion() {
        return direccion;
    }

    public ArrayList<Persona> getPersonas() {
        return personas;
    }

    public ArrayList<Sala> getSalas() {
        return salas;
    }

    public ArrayList<Pelicula> getPeliculas() {
        return peliculas;
    }

    public ArrayList<Funcion> getFunciones() {
        return funciones;
    }

    public ArrayList<Venta> getVentas() {
        return ventas;
    }


    public boolean registrarSala(Sala sala) {
        if (buscarSala(sala.getCodigo()) != null) {
            return false;
        }
        salas.add(sala);
        return true;
    }


    public boolean registrarPelicula(Pelicula pelicula) {
        if (buscarPelicula(pelicula.codigo()) != null) {
            return false;
        }
        peliculas.add(pelicula);
        return true;
    }


    public boolean registrarCliente(Cliente cliente) {
        if (buscarPersona(cliente.getDocumento()) != null) {
            return false;
        }
        personas.add(cliente);
        return true;
    }


    public boolean registrarOperario(Operario operario) {
        if (buscarPersona(operario.getDocumento()) != null) {
            return false;
        }
        personas.add(operario);
        return true;
    }


    public boolean programarFuncion(Funcion funcion) {
        if (buscarFuncion(funcion.getCodigo()) != null) {
            return false;
        }
        for (Funcion existente : funciones) {
            if (funcion.seSolapaCon(existente)) {
                return false;
            }
        }
        funciones.add(funcion);
        return true;
    }


    public boolean asientoOcupado(Funcion funcion, int fila, int numero) {
        for (Venta venta : ventas) {
            if (venta.getFuncion().getCodigo().equals(funcion.getCodigo()) && venta.tieneAsiento(fila, numero)) {
                return true;
            }
        }
        return false;
    }


    public boolean agregarBoleta(Venta venta, String codigo, int fila, int numero) {
        Funcion funcion = venta.getFuncion();
        if (!funcion.getSala().esAsientoValido(fila, numero)) {
            return false;
        }
        if (asientoOcupado(funcion, fila, numero) || venta.tieneAsiento(fila, numero)) {
            return false;
        }
        double valor = funcion.calcularValorBoleta(venta.getCliente().getPorcentajeDescuento());
        venta.agregarBoleta(new Boleta(codigo, fila, numero, valor));
        return true;
    }


    public boolean registrarVenta(Venta venta) {
        if (buscarVenta(venta.getCodigo()) != null || venta.contarBoletas() == 0) {
            return false;
        }
        ventas.add(venta);
        return true;
    }

    public double calcularTotalVenta(String codigoVenta) {
        Venta venta = buscarVenta(codigoVenta);
        if (venta == null) {
            return -1;
        }
        return venta.calcularTotal();
    }


    public double calcularOcupacion(String codigoFuncion) {
        Funcion funcion = buscarFuncion(codigoFuncion);
        if (funcion == null) {
            return -1;
        }
        int vendidas = 0;
        for (Venta venta : ventas) {
            if (venta.getFuncion().getCodigo().equals(codigoFuncion)) {
                vendidas += venta.contarBoletas();
            }
        }
        return vendidas * 100.0 / funcion.getSala().getCapacidadMaxima();
    }

    public Persona buscarPersona(String documento) {
        for (Persona persona : personas) {
            if (persona.getDocumento().equals(documento)) {
                return persona;
            }
        }
        return null;
    }

    public Cliente buscarCliente(String documento) {
        Persona persona = buscarPersona(documento);
        if (persona instanceof Cliente) {
            return (Cliente) persona;
        }
        return null;
    }

    public Operario buscarOperario(String documento) {
        Persona persona = buscarPersona(documento);
        if (persona instanceof Operario) {
            return (Operario) persona;
        }
        return null;
    }

    public Pelicula buscarPelicula(String codigo) {
        for (Pelicula pelicula : peliculas) {
            if (pelicula.codigo().equals(codigo)) {
                return pelicula;
            }
        }
        return null;
    }

    public Sala buscarSala(String codigo) {
        for (Sala sala : salas) {
            if (sala.getCodigo().equals(codigo)) {
                return sala;
            }
        }
        return null;
    }

    public Funcion buscarFuncion(String codigo) {
        for (Funcion funcion : funciones) {
            if (funcion.getCodigo().equals(codigo)) {
                return funcion;
            }
        }
        return null;
    }

    public Venta buscarVenta(String codigo) {
        for (Venta venta : ventas) {
            if (venta.getCodigo().equals(codigo)) {
                return venta;
            }
        }
        return null;
    }
}
