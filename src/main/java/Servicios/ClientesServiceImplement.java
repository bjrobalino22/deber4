package Servicios;

import Modelo.Clientes;
import java.util.Arrays;
import java.util.List;

public class ClientesServiceImplement implements ClientesService {
    @Override
    public List<Clientes> listar() {
        return Arrays.asList(
                new Clientes(1, 1723557029, "Bryan", "San Camilo", 2021600),
                new Clientes(2, 1710402021, "Daniela", "San Bartolo", 3812877),
                new Clientes(3, 172355744, "Andres", "Carapungo", 3111187)
        );
    }
}
