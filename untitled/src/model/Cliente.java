package model;

public class Cliente extends Persona {

    private String codigoAfiliacion;
    private double porcentajeDescuento;

    // Constructor de Cliente
    public Cliente(String documento, String nombreCompleto, String correo, String telefono,
                   String codigoAfiliacion, double porcentajeDescuento) {
        super(documento, nombreCompleto, correo, telefono);
        this.codigoAfiliacion = codigoAfiliacion;
        this.porcentajeDescuento = porcentajeDescuento;
    }

    public String getCodigoAfiliacion() {
        return codigoAfiliacion;
    }

    public void setCodigoAfiliacion(String codigoAfiliacion) {
        this.codigoAfiliacion = codigoAfiliacion;
    }

    public double getPorcentajeDescuento() {
        return porcentajeDescuento;
    }

    public void setPorcentajeDescuento(double porcentajeDescuento) {
        this.porcentajeDescuento = porcentajeDescuento;
    }


    public boolean tieneDescuento() {
        return porcentajeDescuento > 0;
    }
}
