package views;

import controller.HuespedController;
import controller.ReservaController;
import modelo.Huesped;
import modelo.Reserva;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.swing.JTabbedPane;
import java.awt.Toolkit;
import javax.swing.SwingConstants;
import javax.swing.JSeparator;
import javax.swing.ListSelectionModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

@SuppressWarnings("serial")
public class Busqueda extends JFrame {

	private JPanel contentPane;
	private JTextField txtBuscar;
	private JTable tbHuespedes;
	private JTable tbReservas;
	private DefaultTableModel modelo;
	private DefaultTableModel modeloHuesped;
	private JLabel labelAtras;
	private JLabel labelExit;
	int xMouse, yMouse;
	private ReservaController reservaController;
	private HuespedController huespedController;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Busqueda frame = new Busqueda();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Busqueda() {
		this.reservaController = new ReservaController();
		this.huespedController = new HuespedController();
		setIconImage(Toolkit.getDefaultToolkit().getImage(Busqueda.class.getResource("/imagenes/lupa2.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 910, 571);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		setUndecorated(true);
		
		txtBuscar = new JTextField();
		txtBuscar.setBounds(536, 127, 193, 31);
		txtBuscar.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		contentPane.add(txtBuscar);
		txtBuscar.setColumns(10);
		
		
		JLabel lblNewLabel_4 = new JLabel("SISTEMA DE BÚSQUEDA");
		lblNewLabel_4.setForeground(new Color(12, 138, 199));
		lblNewLabel_4.setFont(new Font("Roboto Black", Font.BOLD, 24));
		lblNewLabel_4.setBounds(331, 62, 280, 42);
		contentPane.add(lblNewLabel_4);
		
		JTabbedPane panel = new JTabbedPane(JTabbedPane.TOP);
		panel.setBackground(new Color(12, 138, 199));
		panel.setFont(new Font("Roboto", Font.PLAIN, 16));
		panel.setBounds(20, 169, 865, 328);
		contentPane.add(panel);

		
		
		
		tbReservas = new JTable();
		tbReservas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tbReservas.setFont(new Font("Roboto", Font.PLAIN, 16));
		modelo = (DefaultTableModel) tbReservas.getModel();
		modelo.addColumn("Numero de Reserva");
		modelo.addColumn("Fecha Check In");
		modelo.addColumn("Fecha Check Out");
		modelo.addColumn("Valor");
		modelo.addColumn("Forma de Pago");
		JScrollPane scroll_table = new JScrollPane(tbReservas);
		panel.addTab("Reservas", new ImageIcon(Busqueda.class.getResource("/imagenes/reservado.png")), scroll_table, null);
		scroll_table.setVisible(true);


		listarReservas();

		tbHuespedes = new JTable();
		tbHuespedes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tbHuespedes.setFont(new Font("Roboto", Font.PLAIN, 16));
		modeloHuesped = (DefaultTableModel) tbHuespedes.getModel();
		modeloHuesped.addColumn("Número de Huesped");
		modeloHuesped.addColumn("Nombre");
		modeloHuesped.addColumn("Apellido");
		modeloHuesped.addColumn("Fecha de Nacimiento");
		modeloHuesped.addColumn("Nacionalidad");
		modeloHuesped.addColumn("Telefono");
		modeloHuesped.addColumn("Número de Reserva");
		JScrollPane scroll_tableHuespedes = new JScrollPane(tbHuespedes);
		panel.addTab("Huéspedes", new ImageIcon(Busqueda.class.getResource("/imagenes/pessoas.png")), scroll_tableHuespedes, null);
		scroll_tableHuespedes.setVisible(true);
		listarHuespedes();

		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setIcon(new ImageIcon(Busqueda.class.getResource("/imagenes/Ha-100px.png")));
		lblNewLabel_2.setBounds(56, 51, 104, 107);
		contentPane.add(lblNewLabel_2);
		
		JPanel header = new JPanel();
		header.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				headerMouseDragged(e);
			     
			}
		});
		header.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				headerMousePressed(e);
			}
		});
		header.setLayout(null);
		header.setBackground(Color.WHITE);
		header.setBounds(0, 0, 910, 36);
		contentPane.add(header);
		
		JPanel btnAtras = new JPanel();
		btnAtras.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				MenuUsuario usuario = new MenuUsuario();
				usuario.setVisible(true);
				dispose();				
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnAtras.setBackground(new Color(12, 138, 199));
				labelAtras.setForeground(Color.white);
			}			
			@Override
			public void mouseExited(MouseEvent e) {
				 btnAtras.setBackground(Color.white);
			     labelAtras.setForeground(Color.black);
			}
		});
		btnAtras.setLayout(null);
		btnAtras.setBackground(Color.WHITE);
		btnAtras.setBounds(0, 0, 53, 36);
		header.add(btnAtras);
		
		labelAtras = new JLabel("<");
		labelAtras.setHorizontalAlignment(SwingConstants.CENTER);
		labelAtras.setFont(new Font("Roboto", Font.PLAIN, 23));
		labelAtras.setBounds(0, 0, 53, 36);
		btnAtras.add(labelAtras);
		
		JPanel btnexit = new JPanel();
		btnexit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				MenuUsuario usuario = new MenuUsuario();
				usuario.setVisible(true);
				dispose();
			}
			@Override
			public void mouseEntered(MouseEvent e) { //Al usuario pasar el mouse por el botón este cambiará de color
				btnexit.setBackground(Color.red);
				labelExit.setForeground(Color.white);
			}			
			@Override
			public void mouseExited(MouseEvent e) { //Al usuario quitar el mouse por el botón este volverá al estado original
				 btnexit.setBackground(Color.white);
			     labelExit.setForeground(Color.black);
			}
		});
		btnexit.setLayout(null);
		btnexit.setBackground(Color.WHITE);
		btnexit.setBounds(857, 0, 53, 36);
		header.add(btnexit);
		
		labelExit = new JLabel("X");
		labelExit.setHorizontalAlignment(SwingConstants.CENTER);
		labelExit.setForeground(Color.BLACK);
		labelExit.setFont(new Font("Roboto", Font.PLAIN, 18));
		labelExit.setBounds(0, 0, 53, 36);
		btnexit.add(labelExit);
		
		JSeparator separator_1_2 = new JSeparator();
		separator_1_2.setForeground(new Color(12, 138, 199));
		separator_1_2.setBackground(new Color(12, 138, 199));
		separator_1_2.setBounds(539, 159, 193, 2);
		contentPane.add(separator_1_2);
		
		JPanel btnbuscar = new JPanel();
		btnbuscar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

			}
		});
		btnbuscar.setLayout(null);
		btnbuscar.setBackground(new Color(12, 138, 199));
		btnbuscar.setBounds(748, 125, 122, 35);
		btnbuscar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		contentPane.add(btnbuscar);
		
		JLabel lblBuscar = new JLabel("BUSCAR");
		lblBuscar.setBounds(0, 0, 122, 35);
		btnbuscar.add(lblBuscar);
		lblBuscar.setHorizontalAlignment(SwingConstants.CENTER);
		lblBuscar.setForeground(Color.WHITE);
		lblBuscar.setFont(new Font("Roboto", Font.PLAIN, 18));
		
		JPanel btnEditar = new JPanel();
		btnEditar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String panelSelecionado = panel.getTitleAt(panel.getSelectedIndex());

				if (panelSelecionado.equals("Reservas")) {
					generarInterfazEditarReserva(extraerDatoReserva());
				}
				if (panelSelecionado.equals("Huéspedes")) {
					generarInterfazEditarHuesped( extraerDatoHuesped());
				}

			}
		});
		btnEditar.setLayout(null);
		btnEditar.setBackground(new Color(12, 138, 199));
		btnEditar.setBounds(635, 508, 122, 35);
		btnEditar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		contentPane.add(btnEditar);
		
		JLabel lblEditar = new JLabel("EDITAR");
		lblEditar.setHorizontalAlignment(SwingConstants.CENTER);
		lblEditar.setForeground(Color.WHITE);
		lblEditar.setFont(new Font("Roboto", Font.PLAIN, 18));
		lblEditar.setBounds(0, 0, 122, 35);
		btnEditar.add(lblEditar);
		
		JPanel btnEliminar = new JPanel();
		btnEliminar.setLayout(null);
		btnEliminar.setBackground(new Color(12, 138, 199));
		btnEliminar.setBounds(767, 508, 122, 35);
		btnEliminar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		contentPane.add(btnEliminar);
		
		JLabel lblEliminar = new JLabel("ELIMINAR");
		lblEliminar.setHorizontalAlignment(SwingConstants.CENTER);
		lblEliminar.setForeground(Color.WHITE);
		lblEliminar.setFont(new Font("Roboto", Font.PLAIN, 18));
		lblEliminar.setBounds(0, 0, 122, 35);
		btnEliminar.add(lblEliminar);

		btnEliminar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String panelSelecionado = panel.getTitleAt(panel.getSelectedIndex());

				if (panelSelecionado.equals("Reservas")) {
					Reserva reservaEliminada = extraerDatoReserva();
					reservaController.eliminar(reservaEliminada.getId());
					modelo.removeRow(tbReservas.getSelectedRow());
				}
				if (panelSelecionado.equals("Huéspedes")) {
					Huesped huespedEliminado = extraerDatoHuesped();
					huespedController.eliminar(huespedEliminado.getId());
					modeloHuesped.removeRow(tbHuespedes.getSelectedRow());
				}

			}
		});
		setResizable(false);
	}

	private Reserva extraerDatoReserva() {
		int filaSeleccionada = tbReservas.getSelectedRow();
		Reserva datoReserva = new Reserva();
		if (filaSeleccionada >= 0) {
			datoReserva.setId(Integer.parseInt(tbReservas.getValueAt(filaSeleccionada, 0).toString()));
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Date fechaEntrada = null;
			try {
				fechaEntrada = sdf.parse((String) tbReservas.getValueAt(filaSeleccionada, 1));
			} catch (ParseException ex) {
				throw new RuntimeException(ex);
			}
			Date fechaSalida = null;
			try {
				fechaSalida = sdf.parse((String) tbReservas.getValueAt(filaSeleccionada, 2));
			} catch (ParseException ex) {
				throw new RuntimeException(ex);
			}

			Calendar calEntrada = Calendar.getInstance();
			calEntrada.setTime(fechaEntrada);

			Calendar calSalida = Calendar.getInstance();
			calSalida.setTime(fechaSalida);

			datoReserva.setFechaEntrada(calEntrada);
			datoReserva.setFechaSalida(calSalida);

			BigDecimal valor = BigDecimal.valueOf(Double.parseDouble(tbReservas.getValueAt(filaSeleccionada, 3).toString()));
			datoReserva.setValor(valor);
			datoReserva.setFormaPago(tbReservas.getValueAt(filaSeleccionada, 4).toString());



		}
		return datoReserva;
	}

	private void generarInterfazEditarReserva(Reserva datoReserva) {
		ReservasEditar reservasEditar = new ReservasEditar(datoReserva);
		reservasEditar.setVisible(true);
		dispose();
	}

	private Huesped extraerDatoHuesped() {
		int filaSeleccionada = tbHuespedes.getSelectedRow();
		Huesped datoHuesped = new Huesped();
		if (filaSeleccionada >= 0) {
			datoHuesped.setId(Integer.parseInt(tbHuespedes.getValueAt(filaSeleccionada, 0).toString()));
			datoHuesped.setNombre(tbHuespedes.getValueAt(filaSeleccionada,1).toString());
			datoHuesped.setApellido(tbHuespedes.getValueAt(filaSeleccionada,2).toString());
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Date fechaNacimiento = null;
			try {
				fechaNacimiento = sdf.parse((String) tbHuespedes.getValueAt(filaSeleccionada, 3));
			} catch (ParseException ex) {
				throw new RuntimeException(ex);
			}


			Calendar calNacimiento = Calendar.getInstance();
			calNacimiento.setTime(fechaNacimiento);


			datoHuesped.setFechaNacimiento(calNacimiento);
			datoHuesped.setNacionalidad(tbHuespedes.getValueAt(filaSeleccionada,4).toString());
			datoHuesped.setTelefono(tbHuespedes.getValueAt(filaSeleccionada,5).toString());
			datoHuesped.setIdReserva(Integer.parseInt(tbHuespedes.getValueAt(filaSeleccionada, 6).toString()));


		}
		return datoHuesped;
	}
	private void generarInterfazEditarHuesped(Huesped datoHuesped) {
		HuespedEditar huespedEditar = new HuespedEditar(datoHuesped);
		huespedEditar.setVisible(true);
		dispose();
	}

	private void listarReservas() {
		List<Reserva> resultado = reservaController.listar();

		for (Reserva reserva : resultado) {

			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			String fechaEntradaComoString = formatter.format(reserva.getFechaEntrada().getTime());
			String fechaSalidaComoString = formatter.format(reserva.getFechaSalida().getTime());

			Object[] row = { reserva.getId(), fechaEntradaComoString, fechaSalidaComoString, reserva.getValor(), reserva.getFormaPago() };
			modelo.addRow(row);

		}
	}
	private void listarHuespedes() {
		List<Huesped> resultado = huespedController.listar();

		for (Huesped huesped : resultado) {

			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			String fechaNacimientoString = formatter.format(huesped.getFechaNacimiento().getTime());


			Object[] row = { huesped.getId(),huesped.getNombre(),huesped.getApellido(), fechaNacimientoString, huesped.getNacionalidad(), huesped.getTelefono(),huesped.getIdReserva() };
			modeloHuesped.addRow(row);

		}
	}

	//Código que permite mover la ventana por la pantalla según la posición de "x" y "y"
	 private void headerMousePressed(java.awt.event.MouseEvent evt) {
	        xMouse = evt.getX();
	        yMouse = evt.getY();
	    }

	    private void headerMouseDragged(java.awt.event.MouseEvent evt) {
	        int x = evt.getXOnScreen();
	        int y = evt.getYOnScreen();
	        this.setLocation(x - xMouse, y - yMouse);
}
}
