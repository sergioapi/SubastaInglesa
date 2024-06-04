package vendedor;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUIVendedor extends JFrame {
    private JPanel panelPrincipal;
    private JTabbedPane menuSuperior;
    private JPanel nuevaPanel;
    private JPanel activasPanel;
    private JPanel panelTitulo;
    private JTextField txTItulo;
    private JLabel labelTitulo;
    private JPanel panelPrecio;
    private JPanel panlIncremento;
    private JButton btnAceptar;
    private JTextField txPrecio;
    private JTextField txIncremento;
    private JLabel labelPrecio;
    private JLabel labelIncremento;
    private JPanel panelBtn;

    private Vendedor vendedor;

    public GUIVendedor(Vendedor vendedor) {
        this.vendedor = vendedor;
        btnAceptar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnAceptarActionPerformed(e);
            }
        });
    }

    private void btnAceptarActionPerformed(ActionEvent evt) {
        String titulo = txTItulo.getText();
        String sPrecio = txPrecio.getText();
        String sIncremento = txIncremento.getText();
        if (!titulo.isBlank() && !sPrecio.isBlank() && !sIncremento.isBlank())
            try {
                float precio = Float.parseFloat(sPrecio);
                float incremento = Float.parseFloat(sIncremento);

            } catch (NumberFormatException ex) {

            }
    }
}
