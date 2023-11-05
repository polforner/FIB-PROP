package presentacio;

import domini.exceptions.ExprMalFormadaException;

import javax.swing.*;
import javax.swing.undo.UndoManager;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Aquest vista s'encarrega de modificar una expressio
 */

public class vistaModificacioExpr extends JFrame {
    /**
     * Titol de la vista
     */
    private JLabel titolVista;
    /**
     * Boto per fer undo
     */
    private JButton bUndo;
    /**
     * Boto per fer redo
     */
    private JButton bRedo;
    /**
     * Boto per finalitzar el proces de modificacio i modificar la expressio indicada
     */
    private JButton bFi;
    /**
     * Area de text per posar nova expressio
     */
    private JTextArea expressio;
    /**
     * El panell que conte tots components de finestra
     */
    private JPanel lamina;
    private JButton bCancel;

    /**
     * Contructor de la vistaModificacioExpr
     *
     * @param id   identificador de la expressio que vol modificar
     * @param expr la expressio original
     */
    public vistaModificacioExpr(int id, String expr) {
        setContentPane(lamina);
        setBounds(500, 300, 500, 300);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        expressio.setText(expr);
        UndoManager manager = new UndoManager();
        expressio.getDocument().addUndoableEditListener(manager);

        bRedo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (manager.canRedo()) {
                    manager.redo();
                }

            }
        });

        bUndo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (manager.canUndo()) {
                    manager.undo();
                }

            }
        });

        bFi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean exit = true;
                try {
                    if (expressio.getText().isEmpty()) {
                        new Missatge("Expressio no pot ser buida");
                    }
                    else {
                        CtrlPresentacio.modificarExpressioBooleana(id, expressio.getText());
                    }
                } catch (ExprMalFormadaException ex) {
                    new Missatge(ex.getMessage());
                    exit = false;
                }


                if (exit) {
                    setVisible(false);
                    CtrlPresentacio.vistaModificarExpr();
                    dispose();
                    new Missatge("L'expressio s'ha modificat correctament");
                }
            }
        });
        bCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                CtrlPresentacio.vistaModificarExpr();
                dispose();
            }
        });
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
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new BorderLayout(0, 0));
        lamina.add(panel1, BorderLayout.NORTH);
        titolVista = new JLabel();
        titolVista.setText("modificacio");
        panel1.add(titolVista, BorderLayout.NORTH);
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new BorderLayout(0, 0));
        lamina.add(panel2, BorderLayout.SOUTH);
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        panel2.add(panel3, BorderLayout.NORTH);
        bUndo = new JButton();
        bUndo.setText("Undo");
        panel3.add(bUndo);
        bRedo = new JButton();
        bRedo.setText("Redo");
        panel3.add(bRedo);
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        panel2.add(panel4, BorderLayout.CENTER);
        bFi = new JButton();
        bFi.setText("Modificar");
        panel4.add(bFi);
        bCancel = new JButton();
        bCancel.setText("Cancelar");
        panel4.add(bCancel);
        final JScrollPane scrollPane1 = new JScrollPane();
        lamina.add(scrollPane1, BorderLayout.CENTER);
        expressio = new JTextArea();
        expressio.setEditable(true);
        expressio.setLineWrap(true);
        expressio.setText("");
        scrollPane1.setViewportView(expressio);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return lamina;
    }

}
