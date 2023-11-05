package presentacio;

import domini.Clases.Document;

import javax.swing.*;
import javax.swing.undo.UndoManager;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * Aquesta vista s'encarrega de modificar un document
 *
 * @author Zheshuo Lin
 */
public class vistaModificacio extends JFrame {
    /**
     * El panell que conte tots components de finestra
     */
    private JPanel lamina;
    /**
     * Area de text per posar nou contingut
     */
    private JTextArea contingut;
    /**
     * Titol de la vista
     */
    private JLabel titolVista;
    /**
     * Boto per fer redo
     */
    private JButton bRedo;
    /**
     * Boto per fer undo
     */
    private JButton bUndo;
    /**
     * Boto per finalitzar el proces de modificacio i modificar el document
     */
    private JButton bFi;
    /**
     * Area de text per posar nou autor
     */
    private JTextArea nouAutor;
    /**
     * Area de text per posar nou titol
     */
    private JTextArea nouTitol;
    /**
     * Titol d'area de text per posar nou titol
     */
    private JLabel ltitol;
    /**
     * Titol d'area de text per posar nou autor
     */
    private JLabel lautor;
    private JButton bCancel;

    /**
     * Constructor de la vistaModificacio
     *
     * @param titol titol del document que vol modificar
     * @param autor autor del document que vol modificar
     */

    public vistaModificacio(String titol, String autor) {
        setVisible(true);
        setContentPane(lamina);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(500, 300, 500, 300);
        Map<String, Map<String, Integer>> ta = CtrlPresentacio.getTitolsAutors();
        Map<Integer, Document> ids = CtrlPresentacio.getIdentificadors();
        Document documentObert = ids.get(ta.get(autor).get(titol));
        List<String> continguts = documentObert.getContingut();
        for (String s : continguts) {
            contingut.append(s + "\n");
        }
        List<String> noucontinguts = new ArrayList<String>();
        bFi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean exit = true;
                String con = contingut.getText();
                String[] cons = con.split("\n");
                for (String s : cons) {
                    if (s.length() > 0) {
                        noucontinguts.add(s);
                    }
                }
                if (!noucontinguts.equals(continguts) && noucontinguts.size() > 0) {
                    try {
                        CtrlPresentacio.modificaContingut(autor, titol, noucontinguts);
                    } catch (Exception ex) {
                        new Missatge(ex.getMessage());
                        exit = false;
                    }
                }
                if (nouAutor.getText() != autor && nouAutor.getText().length() > 0) {
                    try {
                        CtrlPresentacio.modificaAutor(autor, titol, nouAutor.getText());
                    } catch (Exception ex) {
                        new Missatge(ex.getMessage());
                        exit = false;
                    }
                }

                if (nouTitol.getText() != titol && nouTitol.getText().length() > 0) {
                    try {
                        if (nouAutor.getText().isEmpty()) {
                            CtrlPresentacio.modificaTitol(autor, titol, nouTitol.getText());

                        }
                        else {
                            CtrlPresentacio.modificaTitol(nouAutor.getText(), titol, nouTitol.getText());
                        }
                    } catch (Exception ex) {
                        new Missatge(ex.getMessage());
                        exit = false;
                    }
                }

                setVisible(false);
                CtrlPresentacio.vistaModificarDoc();
                if (exit) {
                    new Missatge("El document s'ha modificat correctament");
                }
                dispose();

            }
        });
        UndoManager manager = new UndoManager();
        contingut.getDocument().addUndoableEditListener(manager);
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
        bCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                CtrlPresentacio.vistaModificarDoc();
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
        final JScrollPane scrollPane1 = new JScrollPane();
        lamina.add(scrollPane1, BorderLayout.CENTER);
        contingut = new JTextArea();
        contingut.setEditable(true);
        contingut.setLineWrap(true);
        contingut.setText("");
        scrollPane1.setViewportView(contingut);
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new BorderLayout(0, 0));
        lamina.add(panel1, BorderLayout.SOUTH);
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        panel1.add(panel2, BorderLayout.NORTH);
        bUndo = new JButton();
        bUndo.setText("Undo");
        panel2.add(bUndo);
        bRedo = new JButton();
        bRedo.setText("Redo");
        panel2.add(bRedo);
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        panel1.add(panel3, BorderLayout.CENTER);
        bFi = new JButton();
        bFi.setText("Modificar");
        panel3.add(bFi);
        bCancel = new JButton();
        bCancel.setText("Cancelar");
        panel3.add(bCancel);
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new BorderLayout(0, 0));
        lamina.add(panel4, BorderLayout.NORTH);
        titolVista = new JLabel();
        titolVista.setText("modificacio");
        panel4.add(titolVista, BorderLayout.NORTH);
        final JPanel panel5 = new JPanel();
        panel5.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        panel4.add(panel5, BorderLayout.CENTER);
        lautor = new JLabel();
        lautor.setText("nou autor");
        panel5.add(lautor);
        nouAutor = new JTextArea();
        nouAutor.setLineWrap(true);
        nouAutor.setMinimumSize(new Dimension(10, 15));
        nouAutor.setText("");
        panel5.add(nouAutor);
        ltitol = new JLabel();
        ltitol.setText("nou titol ");
        panel5.add(ltitol);
        nouTitol = new JTextArea();
        nouTitol.setLineWrap(true);
        nouTitol.setMinimumSize(new Dimension(10, 15));
        nouTitol.setText("");
        panel5.add(nouTitol);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return lamina;
    }

}
