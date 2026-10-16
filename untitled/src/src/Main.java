package src;

import model.*;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;

public class Main {

    private static Cine cine = new Cine("CinePlus", "900123456-7", "Armenia, Quindío");

    private static final String TEXTO_MENU = "CINEPLUS - MENÚ PRINCIPAL\n\n"
            + "1. Registrar película\n"
            + "2. Registrar cliente\n"
            + "3. Programar función\n"
            + "4. Realizar venta de boletas\n"
            + "5. Consultar total de una venta\n"
            + "6. Consultar ocupación de una función\n"
            + "7. Registrar sala\n"
            + "8. Registrar operario\n"
            + "9. Mostrar datos registrados\n"
            + "0. Salir\n\n"
            + "Digite una opción:";

    public static void main(String[] args) {
        menu();
    }

    private static void menu() {
        int opcion = -1;
        do {
            try {
                String entrada = JOptionPane.showInputDialog(null, TEXTO_MENU, "CinePlus", JOptionPane.PLAIN_MESSAGE);
                if (entrada == null) {
                    opcion = 0;
                } else {
                    opcion = Integer.parseInt(entrada.trim());
                    ejecutarOpcion(opcion);
                }
            } catch (NumberFormatException e) {
                mensaje("Debe digitar un número válido.");
            } catch (DateTimeException e) {
                mensaje("Formato de fecha u hora inválido.");
            } catch (IllegalStateException e) {
                mensaje("Operación cancelada.");
            } catch (IllegalArgumentException e) {
                mensaje("No se permiten campos vacíos.");
            }
        } while (opcion != 0);
        mensaje("Gracias por usar CinePlus.");
    }

    private static void ejecutarOpcion(int opcion) {
        switch (opcion) {
            case 1:
                registrarPelicula();
                break;
            case 2:
                registrarCliente();
                break;
            case 3:
                programarFuncion();
                break;
            case 4:
                realizarVenta();
                break;
            case 5:
                consultarTotalVenta();
                break;
            case 6:
                consultarOcupacion();
                break;
            case 7:
                registrarSala();
                break;
            case 8:
                registrarOperario();
                break;
            case 9:
                mostrar(listarTodo());
                break;
            case 0:
                break;
            default:
                mensaje("Opción no válida.");
        }
    }

    private static void registrarPelicula() {
        String codigo = leerTexto("Código de la película:");
        String titulo = leerTexto("Título:");
        int duracion = leerEntero("Duración en minutos:");
        Genero genero = leerGenero();
        String clasificacion = leerTexto("Clasificación por edad (ej: +12):");
        if (duracion <= 0) {
            mensaje("La duración debe ser mayor a 0.");
            return;
        }
        Pelicula pelicula = new Pelicula(codigo, titulo, duracion, genero, clasificacion);
        if (cine.registrarPelicula(pelicula)) {
            mensaje("Película registrada correctamente.");
        } else {
            mensaje("Ya existe una película con ese código.");
        }
    }

    private static void registrarCliente() {
        String documento = leerTexto("Documento:");
        String nombre = leerTexto("Nombre completo:");
        String correo = leerTexto("Correo:");
        String telefono = leerTexto("Teléfono:");
        String afiliacion = leerTexto("Código de afiliación:");
        double descuento = leerDecimal("Porcentaje de descuento (0 a 100):");
        if (descuento < 0 || descuento > 100) {
            mensaje("El descuento debe estar entre 0 y 100.");
            return;
        }
        Cliente cliente = new Cliente(documento, nombre, correo, telefono, afiliacion, descuento);
        if (cine.registrarCliente(cliente)) {
            mensaje("Cliente registrado correctamente.");
        } else {
            mensaje("Ya existe una persona con ese documento.");
        }
    }

    private static void registrarOperario() {
        String documento = leerTexto("Documento:");
        String nombre = leerTexto("Nombre completo:");
        String correo = leerTexto("Correo:");
        String telefono = leerTexto("Teléfono:");
        String codigoEmpleado = leerTexto("Código de empleado:");
        LocalDate fecha = leerFecha("Fecha de contratación");
        double salario = leerDecimal("Salario básico:");
        if (salario <= 0) {
            mensaje("El salario debe ser mayor a 0.");
            return;
        }
        Operario operario = new Operario(documento, nombre, correo, telefono, codigoEmpleado, fecha, salario);
        if (cine.registrarOperario(operario)) {
            mensaje("Operario registrado correctamente.");
        } else {
            mensaje("Ya existe una persona con ese documento.");
        }
    }

    private static void registrarSala() {
        String codigo = leerTexto("Código de la sala:");
        String nombre = leerTexto("Nombre de la sala:");
        int filas = leerEntero("Cantidad de filas:");
        int columnas = leerEntero("Cantidad de columnas (asientos por fila):");
        TipoSala tipo = leerTipoSala();
        if (filas <= 0 || columnas <= 0) {
            mensaje("Las filas y columnas deben ser mayores a 0.");
            return;
        }
        Sala sala = new Sala(codigo, nombre, filas, columnas, tipo);
        if (cine.registrarSala(sala)) {
            mensaje("Sala registrada. Capacidad: " + sala.getCapacidadMaxima());
        } else {
            mensaje("Ya existe una sala con ese código.");
        }
    }

    private static void programarFuncion() {
        if (cine.getPeliculas().isEmpty() || cine.getSalas().isEmpty()) {
            mensaje("Debe haber al menos una película y una sala registradas.");
            return;
        }
        String codigo = leerTexto("Código de la función:");
        LocalDate fecha = leerFecha("Fecha de la función");
        LocalTime hora = leerHora("Hora de inicio");
        double precio = leerDecimal("Precio base de la boleta:");
        Pelicula pelicula = cine.buscarPelicula(leerTexto("Código de la película:\n" + listarPeliculas()));
        if (pelicula == null) {
            mensaje("La película no existe.");
            return;
        }
        Sala sala = cine.buscarSala(leerTexto("Código de la sala:\n" + listarSalas()));
        if (sala == null) {
            mensaje("La sala no existe.");
            return;
        }
        if (precio <= 0) {
            mensaje("El precio debe ser mayor a 0.");
            return;
        }
        Funcion funcion = new Funcion(codigo, fecha, hora, precio, pelicula, sala);
        if (cine.programarFuncion(funcion)) {
            mensaje("Función programada. Termina a las " + funcion.calcularFin().toLocalTime());
        } else {
            mensaje("Código repetido o la sala está ocupada en ese horario.");
        }
    }

    private static void realizarVenta() {
        String codigo = leerTexto("Código de la venta:");
        if (cine.buscarVenta(codigo) != null) {
            mensaje("Ya existe una venta con ese código.");
            return;
        }
        Cliente cliente = cine.buscarCliente(leerTexto("Documento del cliente:"));
        if (cliente == null) {
            mensaje("El cliente no existe.");
            return;
        }
        Operario operario = cine.buscarOperario(leerTexto("Documento del operario:"));
        if (operario == null) {
            mensaje("El operario no existe.");
            return;
        }
        Funcion funcion = cine.buscarFuncion(leerTexto("Código de la función:\n" + listarFunciones()));
        if (funcion == null) {
            mensaje("La función no existe.");
            return;
        }
        Venta venta = new Venta(codigo, LocalDate.now(), cliente, operario, funcion);
        int otra;
        do {
            int fila = leerEntero("Fila del asiento (1 a " + funcion.getSala().getFilas() + "):");
            int numero = leerEntero("Número de asiento (1 a " + funcion.getSala().getColumnas() + "):");
            String codigoBoleta = codigo + "-B" + (venta.contarBoletas() + 1);
            if (cine.agregarBoleta(venta, codigoBoleta, fila, numero)) {
                mensaje("Boleta agregada: fila " + fila + ", asiento " + numero);
            } else {
                mensaje("Asiento inválido u ocupado.");
            }
            otra = JOptionPane.showConfirmDialog(null, "¿Desea agregar otra boleta?", "CinePlus", JOptionPane.YES_NO_OPTION);
        } while (otra == JOptionPane.YES_OPTION);
        if (cine.registrarVenta(venta)) {
            mensaje("Venta registrada.\n\n" + detalleVenta(venta));
        } else {
            mensaje("La venta no se registró porque no tiene boletas.");
        }
    }

    private static void consultarTotalVenta() {
        String codigo = leerTexto("Código de la venta:");
        double total = cine.calcularTotalVenta(codigo);
        if (total < 0) {
            mensaje("La venta no existe.");
            return;
        }
        mensaje(detalleVenta(cine.buscarVenta(codigo)));
    }

    private static void consultarOcupacion() {
        String codigo = leerTexto("Código de la función:\n" + listarFunciones());
        double ocupacion = cine.calcularOcupacion(codigo);
        if (ocupacion < 0) {
            mensaje("La función no existe.");
            return;
        }
        int capacidad = cine.buscarFuncion(codigo).getSala().getCapacidadMaxima();
        long vendidas = Math.round(ocupacion * capacidad / 100);
        mensaje("Ocupación de la función " + codigo + ": " + vendidas + "/" + capacidad + " sillas");
    }

    private static String detalleVenta(Venta venta) {
        String texto = "Venta: " + venta.getCodigo()
                + "\nFecha: " + venta.getFecha()
                + "\nCliente: " + venta.getCliente().getNombreCompleto()
                + "\nOperario: " + venta.getOperario().getNombreCompleto()
                + "\nFunción: " + venta.getFuncion().getCodigo()
                + " (" + venta.getFuncion().getPelicula().titulo() + ")"
                + "\nBoletas:";
        for (Boleta boleta : venta.getBoletas()) {
            texto += "\n   " + boleta.codigo() + " | fila " + boleta.fila()
                    + ", asiento " + boleta.numeroAsiento()
                    + " | $" + String.format("%.2f", boleta.valor());
        }
        texto += "\nTOTAL: $" + String.format("%.2f", venta.calcularTotal());
        return texto;
    }

    private static String listarPeliculas() {
        String texto = "PELÍCULAS";
        for (Pelicula pelicula : cine.getPeliculas()) {
            texto += "\n   " + pelicula.codigo() + " | " + pelicula.titulo()
                    + " | " + pelicula.duracionMinutos() + " min"
                    + " | " + pelicula.genero()
                    + " | " + pelicula.clasificacionEdad();
        }
        if (cine.getPeliculas().isEmpty()) {
            texto += "\n   (sin registros)";
        }
        return texto;
    }

    private static String listarSalas() {
        String texto = "SALAS";
        for (Sala sala : cine.getSalas()) {
            texto += "\n   " + sala.getCodigo() + " | " + sala.getNombre()
                    + " | " + sala.getTipo()
                    + " | " + sala.getFilas() + "x" + sala.getColumnas()
                    + " | capacidad " + sala.getCapacidadMaxima();
        }
        if (cine.getSalas().isEmpty()) {
            texto += "\n   (sin registros)";
        }
        return texto;
    }

    private static String listarFunciones() {
        String texto = "FUNCIONES";
        for (Funcion funcion : cine.getFunciones()) {
            texto += "\n   " + funcion.getCodigo()
                    + " | " + funcion.getPelicula().titulo()
                    + " | " + funcion.getSala().getNombre()
                    + " | " + funcion.getFecha()
                    + " | " + funcion.getHoraInicio() + " a " + funcion.calcularFin().toLocalTime()
                    + " | base $" + String.format("%.2f", funcion.getPrecioBase());
        }
        if (cine.getFunciones().isEmpty()) {
            texto += "\n   (sin registros)";
        }
        return texto;
    }

    private static String listarPersonas() {
        String texto = "PERSONAS";
        for (Persona persona : cine.getPersonas()) {
            texto += "\n   " + persona.getDocumento() + " | " + persona.getNombreCompleto()
                    + " | " + persona.getCorreo() + " | " + persona.getTelefono();
            if (persona instanceof Cliente) {
                Cliente cliente = (Cliente) persona;
                texto += " | CLIENTE | afiliación " + cliente.getCodigoAfiliacion()
                        + " | descuento " + cliente.getPorcentajeDescuento() + "%";
            } else if (persona instanceof Operario) {
                Operario operario = (Operario) persona;
                texto += " | OPERARIO | código " + operario.getCodigoEmpleado()
                        + " | contratado " + operario.getFechaContratacion()
                        + " | salario $" + String.format("%.2f", operario.getSalarioBasico());
            }
        }
        if (cine.getPersonas().isEmpty()) {
            texto += "\n   (sin registros)";
        }
        return texto;
    }

    private static String listarVentas() {
        String texto = "VENTAS";
        for (Venta venta : cine.getVentas()) {
            texto += "\n   " + venta.getCodigo()
                    + " | cliente " + venta.getCliente().getNombreCompleto()
                    + " | función " + venta.getFuncion().getCodigo()
                    + " | boletas " + venta.contarBoletas()
                    + " | total $" + String.format("%.2f", venta.calcularTotal());
        }
        if (cine.getVentas().isEmpty()) {
            texto += "\n   (sin registros)";
        }
        return texto;
    }

    private static String listarTodo() {
        return cine.getNombreComercial() + " | NIT " + cine.getNit() + " | " + cine.getDireccion()
                + "\n\n" + listarPeliculas()
                + "\n\n" + listarSalas()
                + "\n\n" + listarFunciones()
                + "\n\n" + listarPersonas()
                + "\n\n" + listarVentas();
    }

    private static void mostrar(String texto) {
        JTextArea area = new JTextArea(texto, 22, 70);
        area.setEditable(false);
        JOptionPane.showMessageDialog(null, new JScrollPane(area), "CinePlus", JOptionPane.INFORMATION_MESSAGE);
    }

    private static void mensaje(String texto) {
        JOptionPane.showMessageDialog(null, texto, "CinePlus", JOptionPane.INFORMATION_MESSAGE);
    }

    private static String leerTexto(String mensaje) {
        String respuesta = JOptionPane.showInputDialog(null, mensaje, "CinePlus", JOptionPane.QUESTION_MESSAGE);
        if (respuesta == null) {
            throw new IllegalStateException("cancelado");
        }
        if (respuesta.trim().isEmpty()) {
            throw new IllegalArgumentException("vacío");
        }
        return respuesta.trim();
    }

    private static int leerEntero(String mensaje) {
        return Integer.parseInt(leerTexto(mensaje));
    }

    private static double leerDecimal(String mensaje) {
        return Double.parseDouble(leerTexto(mensaje).replace(",", "."));
    }

    private static LocalDate leerFecha(String mensaje) {
        String[] partes = leerTexto(mensaje + " (Año Mes Día):").split("[\\s\\-/]+");
        if (partes.length != 3) {
            throw new DateTimeException("formato");
        }
        return LocalDate.of(Integer.parseInt(partes[0]), Integer.parseInt(partes[1]), Integer.parseInt(partes[2]));
    }

    private static LocalTime leerHora(String mensaje) {
        String[] partes = leerTexto(mensaje + " (Hora Minuto):").split("[\\s:.]+");
        if (partes.length != 2) {
            throw new DateTimeException("formato");
        }
        return LocalTime.of(Integer.parseInt(partes[0]), Integer.parseInt(partes[1]));
    }

    private static Genero leerGenero() {
        Object elegido = JOptionPane.showInputDialog(null, "Género:", "CinePlus",
                JOptionPane.QUESTION_MESSAGE, null, Genero.values(), Genero.values()[0]);
        if (elegido == null) {
            throw new IllegalStateException("cancelado");
        }
        return (Genero) elegido;
    }

    private static TipoSala leerTipoSala() {
        Object elegido = JOptionPane.showInputDialog(null, "Tipo de sala:", "CinePlus",
                JOptionPane.QUESTION_MESSAGE, null, TipoSala.values(), TipoSala.values()[0]);
        if (elegido == null) {
            throw new IllegalStateException("cancelado");
        }
        return (TipoSala) elegido;
    }
}
