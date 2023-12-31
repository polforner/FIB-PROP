package presentacio;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.DefaultListModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.util.*;

/**
 * Aquesta vista s'encarrega de modificar un document
 * @author Zheshuo Lin
 */
public class vistaModificarDoc extends JFrame {
    /**
     * Boto per iniciar el proces de modificar document
     */
    private JButton bModificar;

    /**
     * Boto per tornar al gestor de documents
     */
    private JButton bSortir;
    /**
     * L'area de text per introduir titol
     */
    private JTextArea titol;
    /**
     * L'area de text per introduir autor
     */
    private JTextArea autor;
    /**
     * El panell que conte tots components de finestra
     */
    private JPanel lamina;
    /**
     * Titol de la vista
     */
    private JLabel titolVista;
    /**
     * Titol de l'area de text per introduir autor
     */
    private JLabel lAutor;
    /**
     * Titol de l'area de text per introduir titol
     */
    private JLabel lTitol;
    /**
     * Per mostrar a la pantalla els documents
     */
    private JTextArea documents;
    /**
     * Llista per seleccionar documents
     */
    private JList titolautors;
    /**
     * Vista per fer modificacions
     */
    private JFrame pModificacio = new JFrame();

    public vistaModificarDoc() {
        setTitle("Modificar document");
        setContentPane(lamina);
        setBounds(500, 300, 800, 400);
        setVisible(true);

        bSortir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CtrlPresentacio.vistaGestorDocument();
                setVisible(false);
                dispose();


            }
        });
        Map<String, Map<String, Integer>> ta = CtrlPresentacio.getTitolsAutors();
        TreeMap<String, Map<String, Integer>> tadocuments = new TreeMap<>();
        tadocuments.putAll(ta);
        DefaultListModel tas = new DefaultListModel();
        for (String s : tadocuments.keySet()) {
            for (String t : tadocuments.get(s).keySet()) {
                tas.addElement(s + "," + t);
                documents.append("Autor: " + s + " Titol: " + t + "\n");
            }
        }
        titolautors.setModel(tas);

        titolautors.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (titolautors.getValueIsAdjusting()) {
                    String s = (String) titolautors.getSelectedValue();
                    String[] at = s.split(",");
                    titol.setText(at[1]);
                    autor.setText(at[0]);
                }

            }
        });


        bModificar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (pModificacio.isVisible()) {
                    new Missatge("No pot modificar dos documents alhora");
                } else if (ta.containsKey(autor.getText())) {
                    if (ta.get(autor.getText()).containsKey(titol.getText())) {
                        pModificacio = new vistaModificacio(titol.getText(), autor.getText());
                        setVisible(false);
                        dispose();

                    } else {
                        new Missatge("El autor " + autor.getText() + " no te el document amb titol " + titol.getText());
                    }
                } else {
                    new Missatge("El nostre sistema no te aquest autor " + autor.getText());
                }

                titol.setText("");
                autor.setText("");
                titolautors.clearSelection();


            }
        });
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
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new BorderLayout(0, 0));
        lamina.add(panel1, BorderLayout.NORTH);
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        panel1.add(panel2, BorderLayout.CENTER);
        lAutor = new JLabel();
        lAutor.setText("autor");
        panel2.add(lAutor);
        autor = new JTextArea();
        autor.setLineWrap(true);
        autor.setMinimumSize(new Dimension(100, 15));
        panel2.add(autor);
        lTitol = new JLabel();
        lTitol.setText("titol");
        panel2.add(lTitol);
        titol = new JTextArea();
        titol.setLineWrap(true);
        titol.setMinimumSize(new Dimension(100, 15));
        panel2.add(titol);
        titolVista = new JLabel();
        titolVista.setText("Modificar document");
        panel1.add(titolVista, BorderLayout.NORTH);
        final JScrollPane scrollPane1 = new JScrollPane();
        scrollPane1.setMinimumSize(new Dimension(14, 15));
        scrollPane1.setPreferredSize(new Dimension(256, 64));
        panel1.add(scrollPane1, BorderLayout.SOUTH);
        titolautors = new JList();
        scrollPane1.setViewportView(titolautors);
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new BorderLayout(0, 0));
        lamina.add(panel3, BorderLayout.SOUTH);
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        panel3.add(panel4, BorderLayout.NORTH);
        bModificar = new JButton();
        bModificar.setText("modificar");
        panel4.add(bModificar);
        final JPanel panel5 = new JPanel();
        panel5.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        panel3.add(panel5, BorderLayout.CENTER);
        bSortir = new JButton();
        bSortir.setText("Sortir");
        panel5.add(bSortir);
        final JScrollPane scrollPane2 = new JScrollPane();
        lamina.add(scrollPane2, BorderLayout.CENTER);
        documents = new JTextArea();
        documents.setEditable(false);
        documents.setLineWrap(true);
        scrollPane2.setViewportView(documents);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return lamina;
    }

}
