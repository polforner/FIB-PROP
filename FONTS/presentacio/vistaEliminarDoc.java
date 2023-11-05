package presentacio;

import domini.Clases.Document;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author Zheshuo Lin
 */
public class vistaEliminarDoc extends JFrame {

    /**
     * Pantalla per comfirmar l'eliminacio
     */
    private JFrame eliminacio = new JFrame();

    /**
     * El panell que conte tots components de finestra
     */
    private JPanel lamina;
    /**
     * Titol d'area de text per introduir el autor del document
     */
    private JLabel lAutor;
    /**
     * Area de text per introduir el autor del document
     */
    private JTextArea autor;
    /**
     * Titol d'area de text per introduir el titol del document
     */
    private JLabel lTitol;
    /**
     * Area de text per introduir el titol del document
     */
    private JTextArea titol;
    /**
     * Titol de la vista
     */
    private JLabel titolVista;
    /**
     * Llista per seleccionar el document que vol eliminar
     */
    private JList titolautors;
    /**
     * Boto per eliminar un document
     */
    private JButton bEliminar;
    /**
     * Boto per tornar al gestor de documents
     */
    private JButton bSortir;
    /**
     * Area de text per mostrar tots documents
     */
    private JTextArea documents;

    public vistaEliminarDoc() {
        setTitle("Eliminar document");
        setContentPane(lamina);
        setBounds(500, 300, 800, 400);
        setVisible(true);

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

        bSortir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CtrlPresentacio.vistaGestorDocument();
                setVisible(false);

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
        bEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (ta.containsKey(autor.getText())) {
                    if (ta.get(autor.getText()).containsKey(titol.getText())) {
                        Missatge.setEditable(true);
                        Missatge.setText("Estas segur eliminar el document " + titol.getText() + " del " + autor.getText() + "\n");
                        eliminacio.setVisible(true);
                        setVisible(false);
                        Missatge.setEditable(false);
                    }
                    else {
                        new Missatge("El autor " + autor.getText() + " no te el document amb titol " + titol.getText());
                    }
                }
                else {
                    new Missatge("El nostre sistema no te aquest autor " + autor.getText());
                }


            }
        });



        siButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean exit = true;

                try {
                    CtrlPresentacio.eliminarDocument(titol.getText(), autor.getText());

                } catch (Exception ex) {
                    new Missatge(ex.getMessage());
                    exit = false;
                }

                if (exit) {
                    CtrlPresentacio.vistaEliminarDoc();
                    eliminacio.dispose();
                    new Missatge("El document s'ha eliminat correctament");

                }
            }
        });

        noButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(true);
                eliminacio.dispose();
            }
        });
        eliminacio.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
        autor.setText("");
        panel2.add(autor);
        lTitol = new JLabel();
        lTitol.setText("titol");
        panel2.add(lTitol);
        titol = new JTextArea();
        titol.setLineWrap(true);
        titol.setMinimumSize(new Dimension(100, 15));
        panel2.add(titol);
        titolVista = new JLabel();
        titolVista.setText("Eliminar document");
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
        bEliminar = new JButton();
        bEliminar.setText("Eliminar");
        panel4.add(bEliminar);
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
