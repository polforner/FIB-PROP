package presentacio;

import domini.Clases.ExprBooleana;
import domini.exceptions.ExprMalFormadaException;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.List;

/**
 * Aquesta vista s'encarrega de realitzar les consultes. Conte un panell
 * amb 6 tabs cada tab correspont a una consulta
 */
public class vistaNovaConsulta extends JFrame {

    /**
     * El panell que conte tots components de finestra
     */
    private JPanel lamina;
    /**
     * Boto per tornar a la pantalla de gestor de consultes
     */
    private JButton bSortir;
    /**
     * Boto per iniciar la consulta
     */
    private JButton bIniConsulta;
    /**
     * El panell que conte els panell de les consultes
     */
    private JTabbedPane tabbedPane1;
    /**
     * titol de la vista
     */
    private JLabel titolVista;

    /**
     * llista de autors per mostrar a la panell de consulta titols per autor
     */
    private JList autors;
    /**
     * resultat de la consulta titols per autor
     */
    private JTextArea resultat1;

    /**
     * Area de text per introduir autor per realitzar consulta titols per autor
     */
    private JTextArea autorConsulta1;

    /**
     * resultat de la consulta autors per prefix
     */
    private JTextArea resultat2;
    /**
     * Area de text per introduir prefix per realitzar consulta autors per prefix
     */
    private JTextArea prefix;
    /**
     * Titols i autors dels documents per mostrar a la pantalla de la consulta contingut de document
     */
    private JList titolsautors1;
    /**
     * Resultat de la consulta contingut de document
     */
    private JTextArea resultat3;
    /**
     * Area de text per introduir autor de la consulta contingut de document
     */
    private JTextArea autor2;
    /**
     * Area de text per introduir titol de la consulta contingut de document
     */
    private JTextArea titol2;

    /**
     * Titols i autors dels documents per mostrar a la pantalla de la consulta K documents mes semblants
     */
    private JList titolsautors2;

    /**
     * Resultat de la consulta K documents mes semblants
     */
    private JTextArea resultat4;

    /**
     * Area de text per introduir autor de la consulta K documents mes semblants
     */
    private JTextArea autor;
    /**
     * Area de text per introduir titol de la consulta K documents mes semblants
     */
    private JTextArea titol;
    /**
     * Area de text per introduir nombre k de la consulta K documents mes semblants
     */
    private JTextArea kSemblant;

    /**
     * Llista per mostrar a la pantalla totes paraules introduides
     */
    private JList query;
    /**
     * Resultat de la consulta K documents mes rellevants
     */
    private JTextArea resultat5;

    /**
     * Boto per inserir la paraula a la llista query
     */
    private JButton bInsert;

    /**
     * Area de text per introduir paraules de de llista
     */
    private JTextArea paraula;
    /**
     * Area de text per introduir nombre k de la consulta K documents mes rellevants
     */
    private JTextArea kRellevant;
    /**
     * Llista per guardar paraules inserides
     */
    private List<String> querylist = new ArrayList<>();

    /**
     * Area de text per introduir la expressio booleana per realitzar consulta booleana
     */
    private JTextArea expressio;

    /**
     * Expressions per mostrar a la pantalla de la consulta booleana
     */
    private JList expressions;
    /**
     * Resultat de la consulta booleana
     */
    private JTextArea resultat6;

    /**
     * Boto per netejar la pantalla, esborrar tots els resultats
     */

    private JButton bClean;
    private JRadioButton bGuardar;


    public vistaNovaConsulta() {
        inicialitzacio();
        setTitle("Nova consulta");
        setBounds(200, 150, 1000, 600);
        setContentPane(lamina);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        bSortir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CtrlPresentacio.vistaGestorConsulta();
                setVisible(false);
                dispose();
            }
        });

        autors.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (autors.getValueIsAdjusting()) {
                    autorConsulta1.setText(autors.getSelectedValue().toString());
                }
            }
        });

        titolsautors1.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (titolsautors1.getValueIsAdjusting()) {
                    String s = (String) titolsautors1.getSelectedValue();
                    String[] at = s.split(",");
                    titol2.setText(at[1]);
                    autor2.setText(at[0]);
                }

            }
        });

        titolsautors2.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (titolsautors2.getValueIsAdjusting()) {
                    String s = (String) titolsautors2.getSelectedValue();
                    String[] at = s.split(",");
                    titol.setText(at[1]);
                    autor.setText(at[0]);
                }

            }
        });

        expressions.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (expressions.getValueIsAdjusting()) {
                    String s = expressions.getSelectedValue().toString();
                    String ss = s.substring(2, s.length());
                    expressio.setText(ss);
                }
            }
        });

        DefaultListModel<String> paraules = new DefaultListModel<>();
        bInsert.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                paraules.addElement(paraula.getText());
                query.setModel(paraules);
                querylist.add(paraula.getText());
            }
        });
        Map<Integer, ExprBooleana> exprs = CtrlPresentacio.getExpressions();
        Map<String, Map<String, Integer>> ta = CtrlPresentacio.getTitolsAutors();
        ActionListener iniConsulta = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean exit = true;
                switch (tabbedPane1.getSelectedIndex()) {
                    case 0:
                        try {
                            if (ta.containsKey(autorConsulta1.getText())) {
                                resultat1.setText("");
                                Set<String> resultatC1 = CtrlPresentacio.consultaTitolsAutor(autorConsulta1.getText());
                                resultat1.append("titol" + "\n");
                                for (String t : resultatC1) {
                                    resultat1.append(t + "\n");
                                }
                            } else {
                                new Missatge("El nostre sistema no te aquest autor" + autorConsulta1.getText());
                                exit = false;
                            }
                        } catch (FileNotFoundException ex) {
                            new Missatge(ex.getMessage());
                            exit = false;
                        } finally {
                            autorConsulta1.setText("");
                            autors.clearSelection();
                        }
                        break;
                    case 1:
                        try {
                            if (prefix.getText().isEmpty()) {
                                new Missatge("El prefix no pot ser buit");
                                exit = false;
                            } else {
                                resultat2.setText("");
                                Set<String> resultatC2 = CtrlPresentacio.consultaPrefixAutors(prefix.getText());
                                resultat2.append("autor" + "\n");
                                for (String a : resultatC2) {
                                    resultat2.append(a + "\n");
                                }
                            }
                        } catch (FileNotFoundException ex) {
                            new Missatge(ex.getMessage());
                            exit = false;
                        } finally {
                            prefix.setText("");
                        }

                        break;
                    case 2:
                        try {
                            if (ta.containsKey(autor2.getText())) {
                                if (ta.get(autor2.getText()).containsKey(titol2.getText())) {
                                    resultat3.setText("");
                                    List<String> resultatC3 = CtrlPresentacio.consultaContingut(autor2.getText(), titol2.getText());
                                    for (String frase : resultatC3) {
                                        resultat3.append(frase + "\n");
                                    }
                                } else {
                                    exit = false;
                                    new Missatge("El autor " + autor2.getText() + " no te document amb aquest titol " + titol2.getText());
                                }
                            } else {
                                exit = false;
                                new Missatge("El nostre sistema no te aquest autor " + autor2.getText());
                            }

                        } catch (FileNotFoundException ex) {
                            new Missatge(ex.getMessage());
                            exit = false;
                        } finally {
                            titolsautors1.clearSelection();
                            autor2.setText("");
                            titol2.setText("");
                        }
                        break;
                    case 5:
                        try {
                            if (!expressio.getText().isEmpty()) {
                                resultat6.setText("");
                                Map<String, Set<String>> resultatC6 = CtrlPresentacio.consultaBooleana(expressio.getText());
                                resultat6.append("Autor" + "\t" + "Titol" + "\n");
                                for (String a : resultatC6.keySet()) {
                                    for (String t : resultatC6.get(a)) {
                                        resultat6.append(a + "\t" + t + "\n");
                                    }

                                }
                            } else {
                                new Missatge("Expressio no pot ser buida");
                                exit = false;
                            }
                        } catch (ExprMalFormadaException ex) {
                            new Missatge(ex.getMessage());
                            exit = false;
                        } catch (FileNotFoundException ex) {
                            new Missatge(ex.getMessage());
                            exit = false;
                        } finally {

                            if (exit) {
                                if (bGuardar.isSelected()) {
                                    boolean potGuardar = false;
                                    if (expressions.isSelectionEmpty()) {
                                        potGuardar = true;
                                    } else {
                                        String[] s = expressions.getSelectedValue().toString().split(" ");
                                        potGuardar = !(s[1].equalsIgnoreCase(expressio.getText()));
                                    }
                                    if (potGuardar) {
                                        try {
                                            CtrlPresentacio.altaExpressioBooleana(expressio.getText());
                                            actualizarExpressio();
                                        } catch (ExprMalFormadaException ex) {
                                            new Missatge(ex.getMessage());
                                        }
                                    }

                                }
                            }
                            expressio.setText("");
                            expressions.clearSelection();
                        }


                        break;
                    case 3:
                        try {
                            if (ta.containsKey(autor.getText())) {
                                if (ta.get(autor.getText()).containsKey(titol.getText())) {
                                    resultat4.setText("");
                                    Map<String, Set<String>> resultatC4 = CtrlPresentacio.consultaKmesSemblant(autor.getText(), titol.getText(), Integer.parseInt(kSemblant.getText()));
                                    resultat4.append("Autor" + "\t" + "Titol" + "\n");
                                    for (String a : resultatC4.keySet()) {
                                        for (String t : resultatC4.get(a)) {
                                            resultat4.append(a + "\t" + t + "\n");
                                        }
                                    }
                                } else {
                                    exit = false;
                                    new Missatge("El autor " + autor.getText() + " no te document amb aquest titol " + titol.getText());
                                }
                            } else {
                                exit = false;
                                new Missatge("El nostre sistema no te aquest autor " + autor.getText());
                            }

                        } catch (FileNotFoundException ex) {
                            new Missatge(ex.getMessage());
                            exit = false;
                        } catch (NumberFormatException ex) {
                            new Missatge("k ha de ser un nombre");
                            exit = false;
                        } catch (RuntimeException ex) {
                            new Missatge(ex.getMessage());
                            exit = false;
                        } finally {
                            kSemblant.setText("");
                            autor.setText("");
                            titol.setText("");
                        }
                        break;
                    case 4:
                        try {
                            if (querylist.size() > 0) {
                                resultat5.setText("");
                                Map<String, Set<String>> resultatC5 = CtrlPresentacio.consultaKmesRellevant(querylist, Integer.parseInt(kRellevant.getText()));
                                resultat5.append("Autor" + "\t" + "Titol" + "\n");
                                for (String a : resultatC5.keySet()) {
                                    for (String t : resultatC5.get(a)) {
                                        resultat5.append(a + "\t" + t + "\n");
                                    }
                                }
                            } else {
                                exit = false;
                                new Missatge("La llista de paraules no pot ser buida");
                            }
                        } catch (FileNotFoundException ex) {
                            new Missatge(ex.getMessage());
                            exit = false;
                        } catch (NumberFormatException ex) {
                            new Missatge("k ha de ser un nombre");
                            exit = false;
                        } catch (RuntimeException ex) {
                            new Missatge(ex.getMessage());
                            exit = false;
                        } finally {
                            kRellevant.setText("");
                            paraules.removeAllElements();
                            query.setModel(paraules);
                            querylist = new ArrayList<>();
                        }
                        break;
                    default:
                        new Missatge("Tipus de consulta no existeix");
                }
                if (exit) {
                    new Missatge("Consulta s'ha realitzat correctament");
                }
            }
        };
        bIniConsulta.addActionListener(iniConsulta);

        bClean.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resultat1.setText("");
                resultat2.setText("");
                resultat3.setText("");
                resultat4.setText("");
                resultat5.setText("");
                resultat6.setText("");
            }
        });

    }

    private void inicialitzacio() {
        Map<String, Map<String, Integer>> ta = CtrlPresentacio.getTitolsAutors();
        TreeMap<String, Map<String, Integer>> tadocuments = new TreeMap<>();
        tadocuments.putAll(ta);
        DefaultListModel as = new DefaultListModel<>();
        DefaultListModel tas = new DefaultListModel();
        for (String s : tadocuments.keySet()) {
            as.addElement(s);
            for (String t : tadocuments.get(s).keySet()) {
                tas.addElement(s + "," + t);
            }
        }

        actualizarExpressio();
        titolsautors1.setModel(tas);
        titolsautors2.setModel(tas);
        autors.setModel(as);
    }

    private void actualizarExpressio() {
        DefaultListModel expres = new DefaultListModel<>();
        Map<Integer, ExprBooleana> exprs = CtrlPresentacio.getExpressions();
        for (int id : exprs.keySet()) {
            expres.addElement(id + " " + exprs.get(id).getExpresio());
        }
        expressions.setModel(expres);

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
        bSortir = new JButton();
        bSortir.setText("sortir");
        panel2.add(bSortir);
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        panel1.add(panel3, BorderLayout.NORTH);
        bIniConsulta = new JButton();
        bIniConsulta.setText("Iniciar Consulta");
        panel3.add(bIniConsulta);
        bClean = new JButton();
        bClean.setText("Esborra resultat");
        panel3.add(bClean);
        tabbedPane1 = new JTabbedPane();
        lamina.add(tabbedPane1, BorderLayout.CENTER);
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new BorderLayout(0, 0));
        tabbedPane1.addTab("Consulta titols autor", panel4);
        final JPanel panel5 = new JPanel();
        panel5.setLayout(new BorderLayout(0, 0));
        panel4.add(panel5, BorderLayout.NORTH);
        final JPanel panel6 = new JPanel();
        panel6.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        panel5.add(panel6, BorderLayout.CENTER);
        final JLabel label1 = new JLabel();
        label1.setText("autor");
        panel6.add(label1);
        autorConsulta1 = new JTextArea();
        autorConsulta1.setLineWrap(true);
        autorConsulta1.setMinimumSize(new Dimension(250, 15));
        panel6.add(autorConsulta1);
        final JPanel panel7 = new JPanel();
        panel7.setLayout(new BorderLayout(0, 0));
        panel5.add(panel7, BorderLayout.NORTH);
        final JLabel label2 = new JLabel();
        label2.setText("Consulta titols del autor");
        panel7.add(label2, BorderLayout.CENTER);
        final JScrollPane scrollPane1 = new JScrollPane();
        scrollPane1.setPreferredSize(new Dimension(380, 128));
        panel4.add(scrollPane1, BorderLayout.WEST);
        autors = new JList();
        scrollPane1.setViewportView(autors);
        final JScrollPane scrollPane2 = new JScrollPane();
        panel4.add(scrollPane2, BorderLayout.CENTER);
        resultat1 = new JTextArea();
        resultat1.setEditable(false);
        resultat1.setLineWrap(true);
        scrollPane2.setViewportView(resultat1);
        final JPanel panel8 = new JPanel();
        panel8.setLayout(new BorderLayout(0, 0));
        tabbedPane1.addTab("Consulta prefix autor", panel8);
        final JPanel panel9 = new JPanel();
        panel9.setLayout(new BorderLayout(0, 0));
        panel8.add(panel9, BorderLayout.NORTH);
        final JPanel panel10 = new JPanel();
        panel10.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        panel9.add(panel10, BorderLayout.CENTER);
        final JLabel label3 = new JLabel();
        label3.setText("prefix");
        panel10.add(label3);
        prefix = new JTextArea();
        prefix.setLineWrap(true);
        prefix.setMinimumSize(new Dimension(250, 15));
        panel10.add(prefix);
        final JPanel panel11 = new JPanel();
        panel11.setLayout(new BorderLayout(0, 0));
        panel9.add(panel11, BorderLayout.NORTH);
        final JLabel label4 = new JLabel();
        label4.setText("Consulta autors per prefix");
        panel11.add(label4, BorderLayout.CENTER);
        final JScrollPane scrollPane3 = new JScrollPane();
        panel8.add(scrollPane3, BorderLayout.CENTER);
        resultat2 = new JTextArea();
        resultat2.setEditable(false);
        resultat2.setLineWrap(true);
        scrollPane3.setViewportView(resultat2);
        final JPanel panel12 = new JPanel();
        panel12.setLayout(new BorderLayout(0, 0));
        tabbedPane1.addTab("Consulta contingut", panel12);
        final JPanel panel13 = new JPanel();
        panel13.setLayout(new BorderLayout(0, 0));
        panel12.add(panel13, BorderLayout.NORTH);
        final JPanel panel14 = new JPanel();
        panel14.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        panel13.add(panel14, BorderLayout.CENTER);
        final JLabel label5 = new JLabel();
        label5.setText("autor");
        panel14.add(label5);
        autor2 = new JTextArea();
        autor2.setLineWrap(true);
        autor2.setMinimumSize(new Dimension(200, 15));
        panel14.add(autor2);
        final JLabel label6 = new JLabel();
        label6.setText("titol");
        panel14.add(label6);
        titol2 = new JTextArea();
        titol2.setLineWrap(true);
        titol2.setMinimumSize(new Dimension(200, 15));
        panel14.add(titol2);
        final JPanel panel15 = new JPanel();
        panel15.setLayout(new BorderLayout(0, 0));
        panel13.add(panel15, BorderLayout.NORTH);
        final JLabel label7 = new JLabel();
        label7.setText("Consulta contingut de document");
        panel15.add(label7, BorderLayout.CENTER);
        final JScrollPane scrollPane4 = new JScrollPane();
        scrollPane4.setPreferredSize(new Dimension(380, 128));
        panel12.add(scrollPane4, BorderLayout.WEST);
        titolsautors1 = new JList();
        scrollPane4.setViewportView(titolsautors1);
        final JScrollPane scrollPane5 = new JScrollPane();
        panel12.add(scrollPane5, BorderLayout.CENTER);
        resultat3 = new JTextArea();
        resultat3.setEditable(false);
        resultat3.setLineWrap(true);
        scrollPane5.setViewportView(resultat3);
        final JPanel panel16 = new JPanel();
        panel16.setLayout(new BorderLayout(0, 0));
        tabbedPane1.addTab("K document semblant", panel16);
        final JPanel panel17 = new JPanel();
        panel17.setLayout(new BorderLayout(0, 0));
        panel16.add(panel17, BorderLayout.NORTH);
        final JPanel panel18 = new JPanel();
        panel18.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        panel17.add(panel18, BorderLayout.CENTER);
        final JLabel label8 = new JLabel();
        label8.setText("autor");
        panel18.add(label8);
        autor = new JTextArea();
        autor.setLineWrap(true);
        autor.setMinimumSize(new Dimension(200, 15));
        panel18.add(autor);
        final JLabel label9 = new JLabel();
        label9.setText("titol");
        panel18.add(label9);
        titol = new JTextArea();
        titol.setLineWrap(true);
        titol.setMinimumSize(new Dimension(200, 15));
        panel18.add(titol);
        final JLabel label10 = new JLabel();
        label10.setText("k");
        panel18.add(label10);
        kSemblant = new JTextArea();
        kSemblant.setLineWrap(true);
        kSemblant.setMinimumSize(new Dimension(200, 15));
        panel18.add(kSemblant);
        final JPanel panel19 = new JPanel();
        panel19.setLayout(new BorderLayout(0, 0));
        panel17.add(panel19, BorderLayout.NORTH);
        final JLabel label11 = new JLabel();
        label11.setText("Consulta k documents mes semblants");
        panel19.add(label11, BorderLayout.CENTER);
        final JScrollPane scrollPane6 = new JScrollPane();
        scrollPane6.setPreferredSize(new Dimension(380, 128));
        panel16.add(scrollPane6, BorderLayout.WEST);
        titolsautors2 = new JList();
        scrollPane6.setViewportView(titolsautors2);
        final JScrollPane scrollPane7 = new JScrollPane();
        panel16.add(scrollPane7, BorderLayout.CENTER);
        resultat4 = new JTextArea();
        resultat4.setEditable(false);
        resultat4.setLineWrap(true);
        scrollPane7.setViewportView(resultat4);
        final JPanel panel20 = new JPanel();
        panel20.setLayout(new BorderLayout(0, 0));
        tabbedPane1.addTab("K document rellevant", panel20);
        final JPanel panel21 = new JPanel();
        panel21.setLayout(new BorderLayout(0, 0));
        panel20.add(panel21, BorderLayout.NORTH);
        final JPanel panel22 = new JPanel();
        panel22.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        panel21.add(panel22, BorderLayout.CENTER);
        final JLabel label12 = new JLabel();
        label12.setText("paraula");
        panel22.add(label12);
        paraula = new JTextArea();
        paraula.setLineWrap(true);
        paraula.setMinimumSize(new Dimension(200, 15));
        panel22.add(paraula);
        final JLabel label13 = new JLabel();
        label13.setText("k");
        panel22.add(label13);
        kRellevant = new JTextArea();
        kRellevant.setLineWrap(true);
        kRellevant.setMinimumSize(new Dimension(200, 15));
        panel22.add(kRellevant);
        bInsert = new JButton();
        bInsert.setText("Insert");
        panel22.add(bInsert);
        final JPanel panel23 = new JPanel();
        panel23.setLayout(new BorderLayout(0, 0));
        panel21.add(panel23, BorderLayout.NORTH);
        final JLabel label14 = new JLabel();
        label14.setText("Consulta k documents mes rellevants");
        panel23.add(label14, BorderLayout.CENTER);
        final JScrollPane scrollPane8 = new JScrollPane();
        panel20.add(scrollPane8, BorderLayout.WEST);
        query = new JList();
        scrollPane8.setViewportView(query);
        final JScrollPane scrollPane9 = new JScrollPane();
        panel20.add(scrollPane9, BorderLayout.CENTER);
        resultat5 = new JTextArea();
        resultat5.setEditable(false);
        resultat5.setLineWrap(true);
        scrollPane9.setViewportView(resultat5);
        final JPanel panel24 = new JPanel();
        panel24.setLayout(new BorderLayout(0, 0));
        tabbedPane1.addTab("Consulta expressio booleana", panel24);
        final JPanel panel25 = new JPanel();
        panel25.setLayout(new BorderLayout(0, 0));
        panel24.add(panel25, BorderLayout.NORTH);
        final JPanel panel26 = new JPanel();
        panel26.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        panel25.add(panel26, BorderLayout.CENTER);
        final JLabel label15 = new JLabel();
        label15.setText("expressio");
        panel26.add(label15);
        expressio = new JTextArea();
        expressio.setLineWrap(true);
        expressio.setMinimumSize(new Dimension(200, 15));
        panel26.add(expressio);
        bGuardar = new JRadioButton();
        bGuardar.setText("Guardar expressio");
        panel26.add(bGuardar);
        final JPanel panel27 = new JPanel();
        panel27.setLayout(new BorderLayout(0, 0));
        panel25.add(panel27, BorderLayout.NORTH);
        final JLabel label16 = new JLabel();
        label16.setText("Consulta documents per expressio booleana");
        panel27.add(label16, BorderLayout.CENTER);
        final JScrollPane scrollPane10 = new JScrollPane();
        scrollPane10.setPreferredSize(new Dimension(380, 128));
        panel24.add(scrollPane10, BorderLayout.WEST);
        expressions = new JList();
        scrollPane10.setViewportView(expressions);
        final JScrollPane scrollPane11 = new JScrollPane();
        panel24.add(scrollPane11, BorderLayout.CENTER);
        resultat6 = new JTextArea();
        resultat6.setEditable(false);
        resultat6.setLineWrap(true);
        scrollPane11.setViewportView(resultat6);
        titolVista = new JLabel();
        titolVista.setText("Nova consulta");
        lamina.add(titolVista, BorderLayout.NORTH);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return lamina;
    }

}
