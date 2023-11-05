package presentacio;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;

/**
 * Aquesta vista s'encarrega de mostrar totes funcionalitats que es poden fer amb expressions i
 * permet anar a la vista que pot realitzar aquestes funcionalitats amb botons
 * @author Zheshuo Lin
 */
public class vistaGestorExpr extends JFrame {

    /**
     * El panell que conte tots components de finestra
     */
    private JPanel lamina;
    /**
     * Títol de la finestra
     */
    private JLabel titolVista;
    /**
     * boto per tornar a la menu principal
     */
    private JButton bSortir;
    /**
     * Boto per anar a la finestra de modificar expressio booleana
     */
    private JButton bModificarExpr;

    /**
     * Boto per anar a la finestra d'eliminar expressio booleana
     */
    private JButton bEliminarExpr;
    /**
     * boto per anar a la finestra de crear nova expressio booleana
     */
    private JButton bAlta;

    public vistaGestorExpr() {
        setBounds(500, 300, 500, 300);
        setResizable(true);
        setTitle("Gestor De Document");
        setContentPane(lamina);

        bSortir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CtrlPresentacio.iniPresentacio();
                setVisible(false);
                dispose();
            }
        });

        bAlta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CtrlPresentacio.vistaAltaExpressio();
                setVisible(false);
                dispose();
            }
        });

        bEliminarExpr.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CtrlPresentacio.vistaEliminarExpr();
                setVisible(false);
                dispose();
            }
        });

        bModificarExpr.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CtrlPresentacio.vistaModificarExpr();
                setVisible(false);
                dispose();
            }
        });




        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        lamina = new JPanel();
        lamina.setLayout(new BorderLayout(0, 0));
        titolVista = new JLabel();
        titolVista.setText("Gestor de expressions");
        lamina.add(titolVista, BorderLayout.NORTH);
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        lamina.add(panel1, BorderLayout.SOUTH);
        bSortir = new JButton();
        bSortir.setText("sortir");
        panel1.add(bSortir);
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridBagLayout());
        lamina.add(panel2, BorderLayout.CENTER);
        bAlta = new JButton();
        bAlta.setText("Alta expressio");
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel2.add(bAlta, gbc);
        final JPanel spacer1 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel2.add(spacer1, gbc);
        bModificarExpr = new JButton();
        bModificarExpr.setText("Modificar expressio");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel2.add(bModificarExpr, gbc);
        bEliminarExpr = new JButton();
        bEliminarExpr.setText("Eliminar expressio");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel2.add(bEliminarExpr, gbc);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return lamina;
    }
}