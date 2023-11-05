package presentacio;

import domini.Clases.ExprBooleana;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import java.util.TreeMap;

/**
 * Aquesta vista s'encarrega d'eliminar expressions booleanes
 */
public class vistaEliminarExpr extends JFrame {

    /**
     * El panell que conte tots components de finestra
     */
    private JPanel lamina;
    /**
     * La pantalla per comfirmar l'eliminacio
     */
    private JFrame eliminacio = new JFrame();

    /**
     * Boto per eliminar una expressio booleana
     */
    private JButton bEliminar;
    /**
     * Boto per tornar al gestor de expressions booleanes
     */
    private JButton bSortir;
    /**
     * Titol de la vista
     */
    private JLabel titolVista;
    /**
     * Area de text per introduir identificar de la expressio que vol eliminar
     */
    private JTextArea identificador;
    /**
     * Llista per seleccionar la expressio que vol eliminar
     */
    private JList expressions;
    /**
     * Area de text per mostrar totes expressions guardades en el sistema
     */
    private JTextArea infoExpressions;
    /**
     * Titol de l'area de text per introduir identificador
     */
    private JLabel lIdentificador;

    public vistaEliminarExpr() {
        setContentPane(lamina);
        setBounds(500, 300, 500, 300);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        eliminacio.setVisible(false);
        eliminacio.setBounds(500, 300, 500, 300);
        JPanel Panel1 = new JPanel();
        Panel1.setLayout(new BorderLayout(0, 0));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        Panel1.add(panel1, BorderLayout.SOUTH);
        JButton siButton = new JButton();
        siButton.setText("Si");
        panel1.add(siButton);
        JButton noButton = new JButton();
        noButton.setText("No");
        panel1.add(noButton);
        JTextArea Missatge = new JTextArea();
        Missatge.setColumns(0);
        Panel1.add(Missatge, BorderLayout.CENTER);
        eliminacio.setContentPane(Panel1);

        Map<Integer, ExprBooleana> exprs = CtrlPresentacio.getExpressions();

        DefaultListModel texprs = new DefaultListModel();

        for (Integer i : exprs.keySet()) {
            String expr = exprs.get(i).getExpresio();
            texprs.addElement(i + ": " + expr);
            infoExpressions.append("Expressio" + i + " " + expr + "\n");
        }
        expressions.setModel(texprs);

        expressions.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (expressions.getValueIsAdjusting()) {
                    String s = (String) expressions.getSelectedValue();
                    String[] at = s.split(":");
                    identificador.setText(at[0]);
                }
            }
        });


        bEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (exprs.containsKey(Integer.parseInt(identificador.getText()))) {
                        Missatge.setEditable(true);
                        Missatge.setText("Estas segur eliminar la expressio " + exprs.get(Integer.parseInt(identificador.getText())).getExpresio() + "\n");
                        eliminacio.setVisible(true);
                        Missatge.setEditable(false);
                        setVisible(false);
                    } else {
                        new Missatge("No existeix una expressio booleana identificada per " + identificador.getText());
                    }
                } catch (NumberFormatException ex) {
                    new Missatge("El identificador ha de ser un nombre");
                }
                finally {
                    expressions.clearSelection();
                }


            }
        });

        siButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean exit = true;
                CtrlPresentacio.eliminarExpressioBooleana((Integer.parseInt(identificador.getText())));
                CtrlPresentacio.vistaEliminarExpr();
                eliminacio.dispose();
                new Missatge("L'expressio booleana s'ha eliminat correctament");
            }
        });

        noButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(true);
                eliminacio.dispose();
            }
        });

        bSortir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CtrlPresentacio.vistaGestorExpr();
                setVisible(false);
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
        lamina.add(panel1, BorderLayout.SOUTH);
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        panel1.add(panel2, BorderLayout.NORTH);
        bEliminar = new JButton();
        bEliminar.setText("Eliminar");
        panel2.add(bEliminar);
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        panel1.add(panel3, BorderLayout.SOUTH);
        bSortir = new JButton();
        bSortir.setText("Sortir");
        panel3.add(bSortir);
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new BorderLayout(0, 0));
        lamina.add(panel4, BorderLayout.NORTH);
        titolVista = new JLabel();
        titolVista.setText("Eliminar expressio booleana");
        panel4.add(titolVista, BorderLayout.NORTH);
        final JPanel panel5 = new JPanel();
        panel5.setLayout(new BorderLayout(0, 0));
        panel4.add(panel5, BorderLayout.SOUTH);
        final JScrollPane scrollPane1 = new JScrollPane();
        scrollPane1.setPreferredSize(new Dimension(256, 64));
        panel5.add(scrollPane1, BorderLayout.CENTER);
        expressions = new JList();
        scrollPane1.setViewportView(expressions);
        final JPanel panel6 = new JPanel();
        panel6.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        panel4.add(panel6, BorderLayout.CENTER);
        lIdentificador = new JLabel();
        lIdentificador.setText("identificador");
        panel6.add(lIdentificador);
        identificador = new JTextArea();
        identificador.setLineWrap(true);
        identificador.setMinimumSize(new Dimension(100, 15));
        panel6.add(identificador);
        final JScrollPane scrollPane2 = new JScrollPane();
        lamina.add(scrollPane2, BorderLayout.CENTER);
        infoExpressions = new JTextArea();
        infoExpressions.setEditable(false);
        infoExpressions.setLineWrap(true);
        scrollPane2.setViewportView(infoExpressions);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return lamina;
    }

}
