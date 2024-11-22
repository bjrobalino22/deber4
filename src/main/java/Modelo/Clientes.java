package Modelo;

public class Clientes {
    private int idCliente;
    private int Ci;
    private String nombre;
    private String direccion;
    private int telefono;
    //implementamps un constructor vacio
    public Clientes() {

    }
    ///implementamos el constructor
    public Clientes(int idCliente, int Ci, String nombre, String direccion, int telefono) {
        this.idCliente = idCliente;
        this.Ci = Ci;
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;


    }
    ///////Metodo get//

    public int getidCliente() {return idCliente;}

    public int getCi() {
        return Ci;
    }

    public String getnombre() {
        return nombre;
    }

    public String getdireccion() {
        return direccion;
    }

    public int gettelefono() { return telefono;}
    ////// metodo set////

    public void setidCliente(int idCliente) {
        this.idCliente= idCliente;
    }

    public void setCi(int Ci) {
        this.Ci= Ci;
    }

    public void setnombre(String nombre) {
        this.nombre = nombre;
    }

    public void setdireccion(String direccion) {
        this.direccion = direccion;
    }

    public void settelefono(int telefono) {
        this.telefono = telefono;
    }

}