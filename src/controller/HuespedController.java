package controller;

import dao.HuespedDAO;
import dao.ReservaDAO;
import factory.ConnectionFactory;
import modelo.Huesped;
import modelo.Reserva;

import java.util.List;

public class HuespedController {
    private HuespedDAO huespedDAO;
    public HuespedController(){
        ConnectionFactory factory = new ConnectionFactory();
        this.huespedDAO = new HuespedDAO(factory.recuperaConexion());
    }
    public void guardar(Huesped huesped, int idReserva){
        huesped.setIdReserva(idReserva);
        huespedDAO.guardar(huesped);
    }
    public List<Huesped> listar() {
        return huespedDAO.listar();
    }
    public void modificar(Huesped huesped) {
        huespedDAO.modificar(huesped);
    }
    public void eliminar(Integer id) {
        huespedDAO.eliminar(id);
    }
}
