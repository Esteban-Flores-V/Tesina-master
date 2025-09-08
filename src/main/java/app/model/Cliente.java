package app.model;

public class Cliente {
    private int idCliente; // nuevo
    private String nombre;
    private String apellido;
    private String dni;
    private String telefono;
    private String email;

    // Constructor completo (para cuando traes datos desde la DB)
    public Cliente(int idCliente, String nombre, String apellido, String dni, String telefono, String email) {
        this.idCliente = idCliente;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.telefono = telefono;
        this.email = email;
    }

    // Constructor sin id (para insertar nuevos clientes)
    public Cliente(String nombre, String apellido, String dni, String telefono, String email) {
        this(0, nombre, apellido, dni, telefono, email);
    }

    public int getIdCliente() { return idCliente; }
    public void setIdCliente(int idCliente) { this.idCliente = idCliente; }

    public String getNombre() { return nombre; }
    public String getApellido() { return apellido; }
    public String getDni() { return dni; }
    public String getTelefono() { return telefono; }
    public String getEmail() { return email; }

    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setApellido(String apellido) { this.apellido = apellido; }
    public void setDni(String dni) { this.dni = dni; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public void setEmail(String email) { this.email = email; }
}