package dao;

import modelo.Reserva;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ReservaDAO {
    private Connection con;
    public ReservaDAO(Connection con) {
        this.con = con;
    }
    public int guardar(Reserva reserva) {
        try {
            PreparedStatement statement;
            statement = con.prepareStatement(
                    "INSERT INTO reservas "
                            + " (fecha_entrada, fecha_salida, valor, forma_pago)"
                            + " VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

            try (statement) {
                statement.setDate(1, new java.sql.Date(reserva.getFechaEntrada().getTime().getTime()));
                statement.setDate(2, new java.sql.Date(reserva.getFechaSalida().getTime().getTime()));
                statement.setBigDecimal(3, reserva.getValor());
                statement.setString(4, reserva.getFormaPago());

                statement.execute();

                final ResultSet resultSet = statement.getGeneratedKeys();

                try (resultSet) {
                    while (resultSet.next()) {
                        reserva.setId(resultSet.getInt(1));

                        System.out.println(String.format("Fue insertado la Reserva: %s", reserva.getId()));
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return reserva.getId();
    }

    public List<Reserva> listar() {
        List<Reserva> resultado = new ArrayList<>();

        try {
            final PreparedStatement statement = con
                    .prepareStatement("SELECT id, fecha_entrada, fecha_salida, valor, forma_pago FROM reservas");;

            try (statement) {
                statement.execute();

                final ResultSet resultSet = statement.getResultSet();

                try (resultSet) {
                    while (resultSet.next()) {
                        // Obtener fechas como objetos java.sql.Date
                        Date fechaEntrada = resultSet.getDate("fecha_entrada");
                        Date fechaSalida = resultSet.getDate("fecha_salida");

                        // Convertir objetos java.sql.Date a objetos Calendar
                        Calendar calEntrada = Calendar.getInstance();
                        calEntrada.setTime(fechaEntrada);
                        Calendar calSalida = Calendar.getInstance();
                        calSalida.setTime(fechaSalida);

                        // Crear objeto Reserva y agregarlo a la lista resultado
                        resultado.add(new Reserva(
                                resultSet.getInt("id"),
                                calEntrada,
                                calSalida,
                                resultSet.getBigDecimal("valor"),
                                resultSet.getString("forma_pago")
                                ));
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return resultado;
    }

    public int modificar(Reserva reserva) {
        try {
            final PreparedStatement statement = con.prepareStatement(
                    "UPDATE reservas SET "
                            + " fecha_entrada = ?, "
                            + " fecha_salida = ?,"
                            + " valor = ?,"
                            + " forma_pago = ?"
                            + " WHERE id = ?");

            try (statement) {
                statement.setDate(1, new java.sql.Date(reserva.getFechaEntrada().getTime().getTime()));
                statement.setDate(2, new java.sql.Date(reserva.getFechaSalida().getTime().getTime()));
                statement.setBigDecimal(3, reserva.getValor());
                statement.setString(4, reserva.getFormaPago());
                statement.setInt(5, reserva.getId());
                statement.execute();

                int updateCount = statement.getUpdateCount();

                return updateCount;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void eliminar(Integer id) {
        try {
            final PreparedStatement statement = con.prepareStatement("DELETE FROM reservas WHERE ID = ?");

            try (statement) {
                statement.setInt(1, id);
                statement.execute();

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
