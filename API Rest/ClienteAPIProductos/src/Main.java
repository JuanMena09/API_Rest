
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

import com.Mena.ClienteAPIProductos.cliente.ProductoCliente;
import com.Mena.ClienteAPIProductos.dto.ProductoDTO;

public class Main {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            JFrame ventana = new JFrame("Sistema de Gestión de Productos");
            ventana.setSize(700, 650);
            ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            ventana.setLocationRelativeTo(null);
            ventana.setResizable(false);

            Color azulOscuro = new Color(25, 45, 75);
            Color azul = new Color(45, 105, 180);
            Color fondo = new Color(240, 243, 248);

            ProductoCliente cliente = new ProductoCliente();

            JPanel principal = new JPanel(new BorderLayout());
            principal.setBackground(fondo);

            // ENCABEZADO
            JPanel encabezado = new JPanel(new GridLayout(2, 1));
            encabezado.setBackground(azulOscuro);
            encabezado.setBorder(
                    BorderFactory.createEmptyBorder(20, 25, 20, 25)
            );

            JLabel titulo = new JLabel("GESTIÓN DE PRODUCTOS");
            titulo.setForeground(Color.WHITE);
            titulo.setFont(new Font("Arial", Font.BOLD, 23));

            JLabel subtitulo = new JLabel(
                    "Administra y controla tus productos"
            );
            subtitulo.setForeground(new Color(210, 220, 235));
            subtitulo.setFont(new Font("Arial", Font.PLAIN, 14));

            encabezado.add(titulo);
            encabezado.add(subtitulo);

            // FORMULARIO
            JPanel formulario = new JPanel(new GridBagLayout());
            formulario.setBackground(Color.WHITE);
            formulario.setBorder(new CompoundBorder(
                    new LineBorder(new Color(220, 225, 235), 1),
                    BorderFactory.createEmptyBorder(20, 30, 20, 30)
            ));

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.weightx = 1;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.insets = new Insets(7, 5, 5, 5);

            JTextField txtCodigo =
                    agregarCampo(formulario, gbc, "Código del producto:");

            JTextField txtNombre =
                    agregarCampo(formulario, gbc, "Nombre del producto:");

            JTextField txtDescripcion =
                    agregarCampo(formulario, gbc, "Descripción:");

            JTextField txtPrecio =
                    agregarCampo(formulario, gbc, "Precio:");

            // BOTONES
            JPanel botones = new JPanel(new GridLayout(2, 3, 10, 10));
            botones.setBackground(Color.WHITE);
            botones.setBorder(
                    BorderFactory.createEmptyBorder(15, 5, 5, 5)
            );

            JButton btnGuardar = crearBoton("Guardar", azul);
            JButton btnConsultar = crearBoton("Consultar", azul);
            JButton btnListar = crearBoton("Listar", azul);
            JButton btnActualizar = crearBoton("Actualizar", azul);
            JButton btnEliminar = crearBoton(
                    "Eliminar", new Color(190, 55, 55)
            );
            JButton btnLimpiar = crearBoton(
                    "Limpiar", new Color(100, 110, 125)
            );

            botones.add(btnGuardar);
            botones.add(btnConsultar);
            botones.add(btnListar);
            botones.add(btnActualizar);
            botones.add(btnEliminar);
            botones.add(btnLimpiar);

            // GUARDAR PRODUCTO
            btnGuardar.addActionListener(e -> {

                try {
                    String codigo = txtCodigo.getText().trim();
                    String nombre = txtNombre.getText().trim();
                    String descripcion = txtDescripcion.getText().trim();
                    String precioTexto = txtPrecio.getText().trim();

                    if (codigo.isEmpty() || nombre.isEmpty()
                            || descripcion.isEmpty() || precioTexto.isEmpty()) {

                        JOptionPane.showMessageDialog(
                                ventana, "Completa todos los campos."
                        );
                        return;
                    }

                    double precio = Double.parseDouble(precioTexto);

                    if (precio < 0) {
                        JOptionPane.showMessageDialog(
                                ventana, "El precio no puede ser negativo."
                        );
                        return;
                    }

                    ProductoDTO producto = new ProductoDTO(
                            codigo, nombre, descripcion, precio
                    );

                    String respuesta =
                            cliente.registrarProducto(producto);

                    JOptionPane.showMessageDialog(
                            ventana,
                            "Producto registrado correctamente.\n" + respuesta
                    );

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(
                            ventana, "El precio debe ser un número válido."
                    );

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(
                            ventana,
                            "Error al guardar: " + ex.getMessage()
                    );
                }
            });

            // CONSULTAR PRODUCTO
            btnConsultar.addActionListener(e -> {

                try {
                    String codigo = txtCodigo.getText().trim();

                    if (codigo.isEmpty()) {
                        JOptionPane.showMessageDialog(
                                ventana, "Ingresa el código del producto."
                        );
                        return;
                    }

                    String respuesta =
                            cliente.consultarProducto(codigo);

                    if (respuesta == null || respuesta.equals("null")
                            || respuesta.isBlank()) {
                        JOptionPane.showMessageDialog(
                                ventana, "No se encontró el producto."
                        );
                    } else {
                        JOptionPane.showMessageDialog(
                                ventana,
                                respuesta,
                                "Producto consultado",
                                JOptionPane.INFORMATION_MESSAGE
                        );
                    }

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(
                            ventana,
                            "Error al consultar: " + ex.getMessage()
                    );
                }
            });

            // LISTAR PRODUCTOS
            btnListar.addActionListener(e -> {

                try {
                    String respuesta = cliente.listarProductos();

                    JTextArea area = new JTextArea(respuesta, 15, 45);
                    area.setEditable(false);
                    area.setLineWrap(true);
                    area.setWrapStyleWord(true);
                    area.setFont(new Font("Monospaced", Font.PLAIN, 13));

                    JScrollPane scroll = new JScrollPane(area);

                    JOptionPane.showMessageDialog(
                            ventana,
                            scroll,
                            "Lista de productos",
                            JOptionPane.INFORMATION_MESSAGE
                    );

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(
                            ventana,
                            "Error al listar: " + ex.getMessage()
                    );
                }
            });

            // ACTUALIZAR PRODUCTO
            btnActualizar.addActionListener(e -> {

                try {
                    String codigo = txtCodigo.getText().trim();
                    String nombre = txtNombre.getText().trim();
                    String descripcion = txtDescripcion.getText().trim();
                    String precioTexto = txtPrecio.getText().trim();

                    if (codigo.isEmpty() || nombre.isEmpty()
                            || descripcion.isEmpty() || precioTexto.isEmpty()) {

                        JOptionPane.showMessageDialog(
                                ventana, "Completa todos los campos."
                        );
                        return;
                    }

                    double precio = Double.parseDouble(precioTexto);

                    if (precio < 0) {
                        JOptionPane.showMessageDialog(
                                ventana, "El precio no puede ser negativo."
                        );
                        return;
                    }

                    ProductoDTO producto = new ProductoDTO(
                            codigo, nombre, descripcion, precio
                    );

                    String respuesta =
                            cliente.actualizarProducto(codigo, producto);

                    JOptionPane.showMessageDialog(
                            ventana,
                            "Respuesta de la API:\n" + respuesta
                    );

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(
                            ventana, "El precio debe ser un número válido."
                    );

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(
                            ventana,
                            "Error al actualizar: " + ex.getMessage()
                    );
                }
            });

            // ELIMINAR PRODUCTO
            btnEliminar.addActionListener(e -> {

                try {
                    String codigo = txtCodigo.getText().trim();

                    if (codigo.isEmpty()) {
                        JOptionPane.showMessageDialog(
                                ventana, "Ingresa el código del producto."
                        );
                        return;
                    }

                    int confirmacion = JOptionPane.showConfirmDialog(
                            ventana,
                            "¿Seguro que deseas eliminar el producto " + codigo + "?",
                            "Confirmar eliminación",
                            JOptionPane.YES_NO_OPTION
                    );

                    if (confirmacion == JOptionPane.YES_OPTION) {

                        String respuesta =
                                cliente.eliminarProducto(codigo);

                        JOptionPane.showMessageDialog(
                                ventana,
                                "Resultado de la eliminación:\n" + respuesta
                        );
                    }

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(
                            ventana,
                            "Error al eliminar: " + ex.getMessage()
                    );
                }
            });

            // LIMPIAR CAMPOS
            btnLimpiar.addActionListener(e -> {
                txtCodigo.setText("");
                txtNombre.setText("");
                txtDescripcion.setText("");
                txtPrecio.setText("");
                txtCodigo.requestFocus();
            });

            // CONTENIDO
            JPanel contenido = new JPanel(new BorderLayout());
            contenido.setBackground(fondo);
            contenido.setBorder(
                    BorderFactory.createEmptyBorder(20, 35, 20, 35)
            );

            contenido.add(formulario, BorderLayout.CENTER);
            contenido.add(botones, BorderLayout.SOUTH);

            JLabel pie = new JLabel(
                    "Sistema conectado a la API REST de Productos",
                    SwingConstants.CENTER
            );
            pie.setFont(new Font("Arial", Font.PLAIN, 12));
            pie.setForeground(new Color(100, 110, 125));
            pie.setBorder(
                    BorderFactory.createEmptyBorder(10, 10, 15, 10)
            );

            principal.add(encabezado, BorderLayout.NORTH);
            principal.add(contenido, BorderLayout.CENTER);
            principal.add(pie, BorderLayout.SOUTH);

            ventana.add(principal);
            ventana.setVisible(true);
        });
    }

    // MÉTODO PARA CREAR LOS CAMPOS
    public static JTextField agregarCampo(
            JPanel panel,
            GridBagConstraints gbc,
            String texto
    ) {

        JLabel etiqueta = new JLabel(texto);
        etiqueta.setFont(new Font("Arial", Font.BOLD, 13));

        gbc.gridy++;
        panel.add(etiqueta, gbc);

        JTextField campo = new JTextField(20);
        campo.setFont(new Font("Arial", Font.PLAIN, 14));
        campo.setPreferredSize(new Dimension(200, 32));

        gbc.gridy++;
        panel.add(campo, gbc);

        return campo;
    }

    // MÉTODO PARA CREAR LOS BOTONES
    public static JButton crearBoton(String texto, Color color) {

        JButton boton = new JButton(texto);
        boton.setFont(new Font("Arial", Font.BOLD, 13));
        boton.setForeground(Color.WHITE);
        boton.setBackground(color);
        boton.setFocusPainted(false);
        boton.setBorder(
                BorderFactory.createEmptyBorder(12, 10, 12, 10)
        );

        return boton;
    }
}
