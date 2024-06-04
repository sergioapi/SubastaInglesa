package comprador;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUIComprador extends JFrame {
    private JTabbedPane menuSuperior;
    private JPanel panelPrincipal;
    private JPanel panelLibros;
    private JPanel panelSubastas;
    private JPanel panelNombre;
    private JPanel panelBoton;
    private JPanel panelPrecio;
    private JButton btnAceptar;
    private JTextField txNombre;
    private JTextField txPrecio;
    private JLabel labelNombre;
    private JLabel labelPrecio;

    private Comprador comprador;

    public GUIComprador(Comprador comprador) {
        this.comprador = comprador;
        btnAceptar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnAceptarActionPerformed(e);
            }
        });
    }

    private void btnAceptarActionPerformed(ActionEvent evt) {
        String nombre = txNombre.getText();
        String sPrecio = txPrecio.getText();
        if (nombre.isBlank() || sPrecio.isBlank()) {

        } else {
            try {
                int precio = Integer.parseInt(sPrecio);
                comprador.anadirLibro(nombre, precio);
            } catch (NumberFormatException e) {

            }
        }
    }

}
