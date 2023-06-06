package dao;

import modelo.Huesped;
import modelo.Reserva;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class HuespedDAO {
    private Connection con;
    public HuespedDAO(Connection con) {
        this.con = con;
    }
    public void guardar(Huesped huesped) {
        try {
            PreparedStatement statement;
            statement = con.prepareStatement(
                    "INSERT INTO huespedes "
                            + " (nombre, apellido, fecha_nacimiento, nacionalidad, telefono, id_reserva)"
                            + " VALUES (?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

            try (statement) {
                statement.setString(1,huesped.getNombre());
                statement.setString(2,huesped.getApellido());
                statement.setDate(3, new java.sql.Date(huesped.getFechaNacimiento().getTime().getTime()));
                statement.setString(4, huesped.getNacionalidad());
                statement.setString(5, huesped.getTelefono());
                statement.setInt(6, huesped.getIdReserva());
                statement.execute();

                final ResultSet resultSet = statement.getGeneratedKeys();

                try (resultSet) {
                    while (resultSet.next()) {
                        huesped.setId(resultSet.getInt(1));

                        System.out.println(String.format("Fue insertado el huesped: %s", huesped.getId()));
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public List<Huesped> listar() {
        List<Huesped> resultado = new ArrayList<>();

        try {
            final PreparedStatement statement = con
                    .prepareStatement("SELECT id,nombre, apellido, fecha_nacimiento, nacionalidad, telefono, id_reserva FROM huespedes");;

            try (statement) {
                statement.execute();

                final ResultSet resultSet = statement.getResultSet();

                try (resultSet) {
                    while (resultSet.next()) {
                        // Obtener fechas como objetos java.sql.Date
                        Date fechaNacimiento = resultSet.getDate("fecha_nacimiento");

                        // Convertir objetos java.sql.Date a objetos Calendar
                        Calendar calNacimiento = Calendar.getInstance();
                        calNacimiento.setTime(fechaNacimiento);


                        // Crear objeto Reserva y agregarlo a la lista resultado
                        resultado.add(new Huesped(
                                resultSet.getInt("id"),
                                resultSet.getString("nombre"),
                                resultSet.getString("apellido"),
                                calNacimiento,
                                resultSet.getString("nacionalidad"),
                                resultSet.getString("telefono"),
                                resultSet.getInt("id_reserva")
                        ));
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return resultado;
    }

    public void modificar(Huesped huesped) {
        try {
            final PreparedStatement statement = con.prepareStatement(
                    "UPDATE huespedes SET "
                            + " nombre = ?, "
                            + " apellido = ?,"
                            + " fecha_nacimiento = ?,"
                            + " nacionalidad = ?,"
                            + " telefono = ?,"
                            + " id_reserva = ?"
                            + " WHERE id = ?");

            try (statement) {
                statement.setString(1, huesped.getNombre());
                statement.setString(2, huesped.getApellido());
                statement.setDate(3, new java.sql.Date(huesped.getFechaNacimiento().getTime().getTime()));
                statement.setString(4, huesped.getNacionalidad());
                statement.setString(5, huesped.getTelefono());
                statement.setInt(6, huesped.getIdReserva());
                statement.setInt(7, huesped.getId());
                statement.execute();

                int updateCount = statement.getUpdateCount();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void eliminar(Integer id) {
        try {
            final PreparedStatement statement = con.prepareStatement("DELETE FROM huespedes WHERE ID = ?");

            try (statement) {
                statement.setInt(1, id);
                statement.execute();

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
