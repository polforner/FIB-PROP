package presentacio;

import domini.Clases.Document;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.List;


/**
 * Aquesta vista s’encarrega de guardar documents a l’ordinador. La vista obre una finestra
 * on es podran seleccionar documents, el path i format de documents. Es proporciona un boto de
 * sortir que tornara a la gestor del documents, un boto d’Afegir per afegir document un boto d'Eliminar
 * per eliminar document (aquestes funcionalitats es poden realitzar amb doble click)i un  boto guardar
 * per guardar tots documents seleccionats
 *
 * @author Zheshuo Lin
 */
public class vistaGuardarDoc extends JFrame {

    /**
     * El panell que conte tots components de finestra
     */
    private JPanel lamina;
    /**
     * Boto per afegir un document a llista de documents seleccionats
     */
    private JButton bAfegir;
    /**
     * Boto per guardar documents
     */
    private JButton bGuardar;

    /**
     * Boto per sortir la finestra
     */
    private JButton bSortir;
    /**
     * Boto per eliminar un document desde llista de documents seleccionats
     */
    private JButton bEliminar;

    /**
     * Llista per mostrar a la pantalla tots documents
     */
    private JList documents;
    /**
     * Llista per mostrar a la pantalla tots documents seleccionats per guardar
     */
    private JList documentAGuardar;

    /**
     * El path per guardar els documents
     */
    private JTextArea path;

    /**
     * Boto per anar a finestra per seleccionar el path per guarda els documents
     */
    private JButton bTriar;

    /**
     * Caixa per seleccionat el format de document
     */
    private JComboBox Format;
    private JRadioButton bJunt;
    private JLabel Lnom;
    private JTextArea nom;

    /**
     * Documents per guardar
     */
    private Map<String, Map<String, List<String>>> documents1 = new HashMap<>();

    /**
     * Dialog per seleccionar path per guardar document
     */
    private JFileChooser chooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());


    public vistaGuardarDoc() {
        setContentPane(lamina);
        setBounds(200, 200, 800, 400);
        setVisible(true);


        Map<String, Map<String, Integer>> ta = CtrlPresentacio.getTitolsAutors();
        TreeMap<String, Map<String, Integer>> tadocuments = new TreeMap<>(ta);
        DefaultListModel tas = new DefaultListModel();
        for (String s : tadocuments.keySet()) {
            for (String t : tadocuments.get(s).keySet()) {
                tas.addElement(s + "," + t);
            }
        }
        documents.setModel(tas);
        DefaultListModel docAGuarda = new DefaultListModel();
        documentAGuardar.setModel(docAGuarda);
        Map<Integer, Document> ids = CtrlPresentacio.getIdentificadors();

        documents.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!documents.isSelectionEmpty()) {
                    if (e.getClickCount() == 2 && !docAGuarda.contains(documents.getSelectedValue())) {
                        docAGuarda.addElement(documents.getSelectedValue());
                        String s = documents.getSelectedValue().toString();
                        String[] at = s.split(",");
                        Document d = ids.get(ta.get(at[0]).get(at[1]));

                        if (documents1.containsKey(d.getAutor())) {
                            documents1.get(d.getAutor()).put(d.getTitol(), d.getContingut());
                        } else {
                            Map<String, List<String>> titolcontingut = new HashMap<>();
                            titolcontingut.put(d.getTitol(), d.getContingut());
                            documents1.put(d.getAutor(), titolcontingut);
                        }
                        documents.clearSelection();
                        documentAGuardar.setModel(docAGuarda);

                    }
                }
            }
        });

        documentAGuardar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!documentAGuardar.isSelectionEmpty()) {
                    if (e.getClickCount() == 2) {
                        docAGuarda.remove(documentAGuardar.getSelectedIndex());
                        String s = documentAGuardar.getSelectedValue().toString();
                        String[] at = s.split(",");
                        documents1.get(at[0]).remove(at[1]);
                        if (documents1.get(at[0]).isEmpty()) {
                            documents1.remove(at[0]);
                        }
                        documentAGuardar.setModel(docAGuarda);
                        documentAGuardar.clearSelection();
                    }
                }
            }
        });

        bAfegir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!documents.isSelectionEmpty() && !docAGuarda.contains(documents.getSelectedValue())) {
                    docAGuarda.addElement(documents.getSelectedValue());
                    String s = documents.getSelectedValue().toString();
                    String[] at = s.split(",");
                    Document d = ids.get(ta.get(at[0]).get(at[1]));
                    if (documents1.containsKey(d.getAutor())) {
                        documents1.get(d.getAutor()).put(d.getTitol(), d.getContingut());
                    } else {
                        Map<String, List<String>> titolcontingut = new HashMap<>();
                        titolcontingut.put(d.getTitol(), d.getContingut());
                        documents1.put(d.getAutor(), titolcontingut);
                    }
                    documentAGuardar.setModel(docAGuarda);
                    documents.clearSelection();
                }
            }
        });

        bEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (documentAGuardar.isSelectionEmpty()) {
                    docAGuarda.remove(documentAGuardar.getSelectedIndex());
                    String s = documentAGuardar.getSelectedValue().toString();
                    String[] at = s.split(",");
                    documents1.get(at[0]).remove(at[1]);
                    if (documents1.get(at[0]).isEmpty()) {
                        documents1.remove(at[0]);
                    }
                    documentAGuardar.setModel(docAGuarda);
                    documentAGuardar.clearSelection();
                }
            }
        });

        Format.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                String format = Format.getSelectedItem().toString();
                if (format == "xml") {
                    bJunt.setVisible(true);
                } else {
                    bJunt.setVisible(false);
                    nom.setVisible(false);
                    Lnom.setVisible(false);
                    bJunt.setSelected(false);
                }
            }
        });
        bJunt.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (bJunt.isSelected()) {
                    nom.setVisible(true);
                    Lnom.setVisible(true);
                } else {
                    nom.setVisible(false);
                    Lnom.setVisible(false);
                }
            }
        });

        bGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean exit = true;
                if (path.getText().length() == 0) {
                    new Missatge("El path no pot ser buit");
                    exit = false;

                } else if (Format.getSelectedItem().toString() == "Selecciona Format") {
                    new Missatge("Selecciona un format per guardar");
                    exit = false;

                } else if (documents1.size() == 0) {
                    new Missatge("No ha seleccionat cap document per guardar");
                    exit = false;
                } else {

                    docAGuarda.removeAllElements();
                    documentAGuardar.setModel(docAGuarda);
                    if (Format.getSelectedItem() == "txt") {
                        try {
                            CtrlPresentacio.guardarDocumentsTxt(documents1, path.getText());
                        } catch (IOException ex) {
                            new Missatge(ex.getMessage());
                            exit = false;
                        }
                    } else if (Format.getSelectedItem() == "xml") {
                        try {
                            if (bJunt.isSelected()) {
                                String n = nom.getText();
                                if (nom.getText().isEmpty()) {
                                    n = "Nou document";
                                }
                                String p = path.getText() + "/" + n + ".xml";
                                CtrlPresentacio.guardarDocumentXml(documents1, p, true);
                            } else {
                                CtrlPresentacio.guardarDocumentXml(documents1, path.getText(), false);
                            }

                        } catch (IOException ex) {
                            new Missatge(ex.getMessage());
                            exit = false;
                        }

                    } else if (Format.getSelectedItem() == "json") {
                        try {

                                CtrlPresentacio.guardarDocumentJson(documents1, path.getText());

                        } catch (IOException ex) {
                            new Missatge(ex.getMessage());
                            exit = false;
                        }
                    }
                }

                if (exit) {
                    new Missatge("Els documents s'han guardat correctament");
                }
            }
        });

        bTriar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chooser.setDialogTitle("Selecciona directori");
                chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                chooser.setCurrentDirectory(new File(System.getProperty("user.dir") + "/data/dades"));
                int returnValue = chooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File arxiu = chooser.getSelectedFile();
                    try {
                        path.setText(arxiu.getCanonicalPath());
                    } catch (IOException ex) {
                        new Missatge(ex.getMessage());
                    }
                }
            }
        });
        bSortir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CtrlPresentacio.vistaGestorDocument();
                setVisible(false);
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
        lamina.add(panel1, BorderLayout.SOUTH);
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        panel1.add(panel2, BorderLayout.CENTER);
        bGuardar = new JButton();
        bGuardar.setText("Guardar");
        panel2.add(bGuardar);
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        panel1.add(panel3, BorderLayout.NORTH);
        bAfegir = new JButton();
        bAfegir.setText("Afegir");
        panel3.add(bAfegir);
        bEliminar = new JButton();
        bEliminar.setText("Eliminar");
        panel3.add(bEliminar);
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        panel1.add(panel4, BorderLayout.SOUTH);
        bSortir = new JButton();
        bSortir.setText("Sortir");
        panel4.add(bSortir);
        final JPanel panel5 = new JPanel();
        panel5.setLayout(new BorderLayout(0, 0));
        lamina.add(panel5, BorderLayout.NORTH);
        final JPanel panel6 = new JPanel();
        panel6.setLayout(new BorderLayout(0, 0));
        panel5.add(panel6, BorderLayout.NORTH);
        final JLabel label1 = new JLabel();
        label1.setText("GuardarDocument");
        panel6.add(label1, BorderLayout.CENTER);
        final JPanel panel7 = new JPanel();
        panel7.setLayout(new BorderLayout(0, 0));
        panel5.add(panel7, BorderLayout.SOUTH);
        final JLabel label2 = new JLabel();
        label2.setText("Selecciona documents");
        panel7.add(label2, BorderLayout.WEST);
        final JLabel label3 = new JLabel();
        label3.setText("Documents seleccionats");
        panel7.add(label3, BorderLayout.EAST);
        final JPanel panel8 = new JPanel();
        panel8.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        panel5.add(panel8, BorderLayout.CENTER);
        final JLabel label4 = new JLabel();
        label4.setText("path");
        panel8.add(label4);
        path = new JTextArea();
        path.setLineWrap(true);
        path.setMinimumSize(new Dimension(200, 15));
        panel8.add(path);
        bTriar = new JButton();
        bTriar.setText("Tria Path");
        panel8.add(bTriar);
        Lnom = new JLabel();
        Lnom.setText("Nom");
        Lnom.setVisible(false);
        panel8.add(Lnom);
        nom = new JTextArea();
        nom.setLineWrap(true);
        nom.setMinimumSize(new Dimension(200, 15));
        nom.setVisible(false);
        panel8.add(nom);
        bJunt = new JRadioButton();
        bJunt.setText("Junt");
        bJunt.setVisible(false);
        panel8.add(bJunt);
        Format = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        defaultComboBoxModel1.addElement("Selecciona Format");
        defaultComboBoxModel1.addElement("xml");
        defaultComboBoxModel1.addElement("txt");
        defaultComboBoxModel1.addElement("json");
        Format.setModel(defaultComboBoxModel1);
        panel8.add(Format);
        final JScrollPane scrollPane1 = new JScrollPane();
        lamina.add(scrollPane1, BorderLayout.WEST);
        documents = new JList();
        scrollPane1.setViewportView(documents);
        final JScrollPane scrollPane2 = new JScrollPane();
        lamina.add(scrollPane2, BorderLayout.CENTER);
        documentAGuardar = new JList();
        scrollPane2.setViewportView(documentAGuardar);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return lamina;
    }

}
