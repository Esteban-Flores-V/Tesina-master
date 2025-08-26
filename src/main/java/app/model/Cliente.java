package app.model;

public class Cliente {
    private String nombre;
    private String apellido;
    private String dni;
    private String telefono;
    private String email;

    public Cliente(String nombre, String apellido, String dni, String telefono, String email) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.telefono = telefono;
        this.email = email;
    }

    public String getNombre() {return nombre;}

    public String getApellido() {return apellido;}

    public String getDni() {return dni;}

    public String getTelefono() {return telefono;}

    public String getEmail() {return email;}
}
