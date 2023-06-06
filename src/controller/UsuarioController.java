package controller;

import dao.UsuarioDAO;
import factory.ConnectionFactory;
import modelo.Usuario;

public class UsuarioController {
    private UsuarioDAO usuarioDAO;

    public UsuarioController() {
        ConnectionFactory factory = new ConnectionFactory();
        this.usuarioDAO = new UsuarioDAO(factory.recuperaConexion());
    }
    public boolean validarUsuario(Usuario usuario) {
        return usuarioDAO.login(usuario);
    }

}
