package presentacio;

import domini.Clases.ResultatConsultaNoms;
import domini.Clases.ResultatConsultaPairs;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.*;

public class vistaOperarConsulta extends JFrame {
    private JPanel lamina;
    private JTextArea id;
    private JButton bSortir;
    private JButton bOperar;
    private JButton bAfegir;
    private JComboBox criteri;
    private JLabel titolVista;
    private JComboBox TipusConsulta;
    private JList Consultes;
    private JTextArea textArea1;
    private JButton bNetejar;

    public vistaOperarConsulta() {
        textArea1.setEditable(false);
        setContentPane(lamina);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(300, 200, 700, 500);
        bSortir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CtrlPresentacio.vistaGestorConsulta();
                setVisible(false);
                dispose();
            }
        });
        TipusConsulta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Map<Integer, ResultatConsultaNoms> cn = CtrlPresentacio.getResultatConsultaNoms();
                Map<Integer, ResultatConsultaPairs> cp = CtrlPresentacio.getResultatConsultaPairs();
                DefaultListModel cns = new DefaultListModel();
                DefaultListModel clear = new DefaultListModel();
                Consultes.setModel(clear);
                Consultes.clearSelection();
                switch (TipusConsulta.getSelectedIndex()) {
                    case 1:
                        for (Integer j : cp.keySet()) {
                            String parametres2 = "";
                            for (String s : cp.get(j).getParametres()) {
                                parametres2 += s + ", ";
                            }
                            String parametresArreglats = parametres2.substring(0, parametres2.length() - 2);
                            cns.addElement(j + ": Consulta de " + cp.get(j).getTipusConsulta() + ". Parametres: " + parametresArreglats);
                        }
                        break;
                    case 2:
                        for (Integer i : cn.keySet()) {
                            if (cn.get(i).getTipusConsulta() == "TitolsAutor" || cn.get(i).getTipusConsulta() == "Interseccio-TitolsAutor" || cn.get(i).getTipusConsulta() == "Unio-TitolsAutor"
                            || cn.get(i).getTipusConsulta() == "Diferencia-TitolsAutor" || cn.get(i).getTipusConsulta() == "DiferenciaSimetrica-TitolsAutor") {
                                String parametres = "";
                                parametres += cn.get(i).getParametres().get(0);
                                cns.addElement(i + ": Consulta de " + cn.get(i).getTipusConsulta() + ". Parametres: " + parametres);
                            }
                        }
                        break;
                    case 3:
                        for (Integer k : cn.keySet()) {
                            if (cn.get(k).getTipusConsulta() == "PrefixAutor" || cn.get(k).getTipusConsulta() == "Interseccio-PrefixosAutor" || cn.get(k).getTipusConsulta() == "Unio-PrefixosAutor"
                            || cn.get(k).getTipusConsulta() == "Diferencia-PrefixosAuto" || cn.get(k).getTipusConsulta() == "DiferenciaSimetrica-PrefixosAutor") {
                                String parametres3 = "";
                                parametres3 += cn.get(k).getParametres().get(0);
                                cns.addElement(k + ": Consulta de " + cn.get(k).getTipusConsulta() + ". Parametres: " + parametres3);
                            }
                        }
                        break;
                }
                Consultes.setModel(cns);
            }
        });
        ArrayList<Integer> cl = new ArrayList<Integer>();
        bAfegir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (cl.size() < 2 && Consultes.getSelectedValue() != null) {
                    if (textArea1.getText() == "") textArea1.append("Consultes seleccionades: \n");
                    String Consulta = Consultes.getSelectedValue().toString() + "\n";
                    //System.out.println("Consulta triada " + Consultes.getSelectedValue().toString());
                    int idConsulta = Integer.valueOf(Consulta.substring(0, 1));
                    cl.add(idConsulta);
                    textArea1.append(Consulta);
                }
            }
        });
        bNetejar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cl.clear();
                textArea1.setText("");
            }
        });
        TipusConsulta.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                cl.clear();
                textArea1.setText("");
            }
        });
        bOperar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (cl.size() != 2 || criteri.getSelectedIndex() == 0) new Missatge("Per a operar has de seleccionar dues consultes i el tipus d'operacio");
                else if (cl.size() == 2 && criteri.getSelectedIndex() != 0) {
                    textArea1.setText("");
                    Map<Integer, ResultatConsultaNoms> cn = CtrlPresentacio.getResultatConsultaNoms();
                    Map<Integer, ResultatConsultaPairs> cp = CtrlPresentacio.getResultatConsultaPairs();
                    String resultatOp = "";
                    int resultat = -1;
                    switch (criteri.getSelectedIndex()) {
                        case 1://interseccio
                            resultat = CtrlPresentacio.OperarConsulta(cl.get(0), cl.get(1), "Interseccio");
                            break;
                        case 2:
                            resultat = CtrlPresentacio.OperarConsulta(cl.get(0), cl.get(1), "Unio");
                            break;
                        case 3:
                            resultat = CtrlPresentacio.OperarConsulta(cl.get(0), cl.get(1), "Diferencia");
                            break;
                        case 4:
                            resultat = CtrlPresentacio.OperarConsulta(cl.get(0), cl.get(1), "DiferenciaSimetrica");
                            break;
                    }
                    if (resultat == -1) new Missatge("Ha ocorregut un error en la operacio");
                    if (cp.containsKey(cl.get(0))) { //Operacio entre ResultatConsultaPairs
                        Map<String, Set<String>> cpAct = CtrlPresentacio.getResultatConsultaPairs().get(resultat).getResultat();
                        for (String s : cpAct.keySet()) {
                            for (String j : cpAct.get(s)) {
                                resultatOp += "Autor: " + s + " Titol: " + j + "\n";
                            }
                        }
                    } else if (cn.containsKey(cl.get(0))) { //Operacio entre ResultatConsultaNoms
                        Set<String> cnAct = CtrlPresentacio.getResultatConsultaNoms().get(resultat).getResultat();
                        for (String s : cnAct) {
                            resultatOp += s + "\n";
                        }
                    }
                    textArea1.append(resultatOp);
                }
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
        panel2.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
        panel1.add(panel2, BorderLayout.CENTER);
        bSortir = new JButton();
        bSortir.setHorizontalAlignment(0);
        bSortir.setText("Sortir");
        bSortir.setVerticalAlignment(0);
        panel2.add(bSortir);
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        panel1.add(panel3, BorderLayout.NORTH);
        bAfegir = new JButton();
        bAfegir.setText("Afegir");
        panel3.add(bAfegir);
        bNetejar = new JButton();
        bNetejar.setText("Netejar");
        panel3.add(bNetejar);
        bOperar = new JButton();
        bOperar.setText("Operar");
        panel3.add(bOperar);
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new BorderLayout(0, 0));
        lamina.add(panel4, BorderLayout.NORTH);
        final JPanel panel5 = new JPanel();
        panel5.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        panel4.add(panel5, BorderLayout.CENTER);
        final JLabel label1 = new JLabel();
        label1.setText("Tipus de Consulta a Operar");
        panel5.add(label1);
        TipusConsulta = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        defaultComboBoxModel1.addElement("Selecciona Tipus");
        defaultComboBoxModel1.addElement("Consultes de Documents");
        defaultComboBoxModel1.addElement("Consultes TitolsAutor");
        defaultComboBoxModel1.addElement("Consultes PrefixosAutors");
        TipusConsulta.setModel(defaultComboBoxModel1);
        panel5.add(TipusConsulta);
        final JLabel label2 = new JLabel();
        label2.setText("operacio");
        panel5.add(label2);
        criteri = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel2 = new DefaultComboBoxModel();
        defaultComboBoxModel2.addElement("Selecciona Operacio");
        defaultComboBoxModel2.addElement("interseccio");
        defaultComboBoxModel2.addElement("unio");
        defaultComboBoxModel2.addElement("diferencia");
        defaultComboBoxModel2.addElement("diferencia simetrica");
        criteri.setModel(defaultComboBoxModel2);
        panel5.add(criteri);
        titolVista = new JLabel();
        titolVista.setForeground(new Color(-4473925));
        titolVista.setHorizontalAlignment(0);
        titolVista.setHorizontalTextPosition(0);
        titolVista.setText("Operació de Consultes");
        panel4.add(titolVista, BorderLayout.NORTH);
        final JScrollPane scrollPane1 = new JScrollPane();
        lamina.add(scrollPane1, BorderLayout.CENTER);
        scrollPane1.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), null, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        textArea1 = new JTextArea();
        textArea1.setText("");
        scrollPane1.setViewportView(textArea1);
        final JScrollPane scrollPane2 = new JScrollPane();
        lamina.add(scrollPane2, BorderLayout.WEST);
        scrollPane2.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), null, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        Consultes = new JList();
        final DefaultListModel defaultListModel1 = new DefaultListModel();
        Consultes.setModel(defaultListModel1);
        scrollPane2.setViewportView(Consultes);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return lamina;
    }

}
