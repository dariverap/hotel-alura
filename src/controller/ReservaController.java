package controller;

import dao.ReservaDAO;
import factory.ConnectionFactory;
import modelo.Reserva;

import java.util.List;

public class ReservaController {


    private ReservaDAO reservaDAO;
    public ReservaController(){
        ConnectionFactory factory = new ConnectionFactory();
        this.reservaDAO = new ReservaDAO(factory.recuperaConexion());

    }
    public int guardar(Reserva reserva){
        int reservaid = reservaDAO.guardar(reserva);
        return reservaid;
    }

    public List<Reserva> listar() {
        return reservaDAO.listar();
    }

    public int modificar(Reserva reserva) {
        return reservaDAO.modificar(reserva);
    }
    public void eliminar(Integer id) {

        reservaDAO.eliminar(id);
    }
}
