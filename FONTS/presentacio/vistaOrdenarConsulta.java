package presentacio;

import domini.Clases.ResultatConsulta;
import domini.Clases.ResultatConsultaNoms;
import domini.Clases.ResultatConsultaPairs;
import domini.controladors.CtrlDomini;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import java.util.TreeMap;

public class vistaOrdenarConsulta extends JFrame {
    private JPanel lamina;
    private JLabel titolVista;
    private JComboBox criteri;

    private JTextArea id;
    private JButton bOrdenar;
    private JButton bSortir;
    private JTextArea resultat;
    private JList consultes;

    public vistaOrdenarConsulta() {
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
        Map<Integer, ResultatConsultaNoms> cn = CtrlPresentacio.getResultatConsultaNoms();
        Map<Integer, ResultatConsultaPairs> cp = CtrlPresentacio.getResultatConsultaPairs();
        DefaultListModel cns = new DefaultListModel();
        for (Integer i : cn.keySet()) {
            String parametres = "";
            parametres += cn.get(i).getParametres().get(0);
            cns.addElement(i + ": Consulta de " + cn.get(i).getTipusConsulta() + ". Parametres: " + parametres);
        }
        for (Integer j : cp.keySet()) {
            String parametres2 = "";
            for (String s : cp.get(j).getParametres()) {
                parametres2 += s + ", ";
            }
            String parametresArreglats = parametres2.substring(0, parametres2.length() - 2);
            cns.addElement(j + ": Consulta de " + cp.get(j).getTipusConsulta() + ". Parametres: " + parametresArreglats);
        }
        consultes.setModel(cns);

        consultes.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                resultat.setText("");
                String consulta = consultes.getSelectedValue().toString();
                //obtenim l'identificador de la consulta
                consulta = consulta.substring(0, 1);
                String resultatc = "";
                if (cn.containsKey(Integer.valueOf(consulta))) {
                    ResultatConsultaNoms rcn = cn.get(Integer.valueOf(consulta));
                    for (String rs : rcn.getResultat()) {
                        resultatc += rs;
                        resultatc += "\n";
                    }
                } else {
                    ResultatConsultaPairs rcp = cp.get(Integer.valueOf(consulta));
                    for (String rsp : rcp.getResultat().keySet()) {
                        for (String titol : rcp.getResultat().get(rsp)) {
                            resultatc += "Autor: " + rsp + " Titol: " + titol + "\n";
                        }
                    }
                }
                resultat.append(resultatc);
            }
        });
        ActionListener iniOrdenacio = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (consultes.getSelectedValue() == null || criteri.getSelectedIndex() == 0)
                    new Missatge("S'ha de seleccionar una consulta i un criteri per a realitzar l'ordenacio");
                else if (consultes.getSelectedValue() != null && criteri.getSelectedIndex() != 0) {
                    resultat.setText("");
                    switch (criteri.getSelectedIndex()) {
                        case 1:
                            String consulta = consultes.getSelectedValue().toString();
                            //obtenim l'identificador de la consulta
                            consulta = consulta.substring(0, 1);
                            String resultatc = "";
                            if (cn.containsKey(Integer.valueOf(consulta))) {
                                ResultatConsultaNoms rcn = cn.get(Integer.valueOf(consulta));
                                rcn.ordenarAlfabeticament();
                                for (String rs : rcn.getResultat()) {
                                    resultatc += rs;
                                    resultatc += "\n";
                                }
                            } else {
                                ResultatConsultaPairs rcp = cp.get(Integer.valueOf(consulta));
                                rcp.ordenarAlfabeticament();
                                for (String rsp : rcp.getResultat().keySet()) {
                                    for (String titol : rcp.getResultat().get(rsp)) {
                                        resultatc += "Autor: " + rsp + " Titol: " + titol + "\n";
                                    }
                                }
                            }
                            resultat.append(resultatc);
                            break;
                        case 2:
                            String consulta2 = consultes.getSelectedValue().toString();
                            //obtenim l'identificador de la consulta
                            consulta2 = consulta2.substring(0, 1);
                            String resultatc2 = "";
                            if (cn.containsKey(Integer.valueOf(consulta2))) {
                                ResultatConsultaNoms rcn = cn.get(Integer.valueOf(consulta2));
                                rcn.ordenarInversament();
                                for (String rs : rcn.getResultat()) {
                                    resultatc2 += rs;
                                    resultatc2 += "\n";
                                }
                            } else {
                                ResultatConsultaPairs rcp = cp.get(Integer.valueOf(consulta2));
                                rcp.ordenarInversament();
                                for (String rsp : rcp.getResultat().keySet()) {
                                    for (String titol : rcp.getResultat().get(rsp)) {
                                        resultatc2 += "Autor: " + rsp + " Titol: " + titol + "\n";
                                    }
                                }
                            }
                            resultat.append(resultatc2);
                            break;
                    }
                }
            }
        };
        bOrdenar.addActionListener(iniOrdenacio);
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
        final JLabel label1 = new JLabel();
        label1.setText("criteri");
        panel2.add(label1);
        criteri = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        defaultComboBoxModel1.addElement("Selecciona Criteri");
        defaultComboBoxModel1.addElement("Alfabeticament");
        defaultComboBoxModel1.addElement("Inversament");
        criteri.setModel(defaultComboBoxModel1);
        panel2.add(criteri);
        titolVista = new JLabel();
        titolVista.setHorizontalAlignment(0);
        titolVista.setText("Ordenació de Consultes");
        panel1.add(titolVista, BorderLayout.NORTH);
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new BorderLayout(0, 0));
        lamina.add(panel3, BorderLayout.SOUTH);
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        panel3.add(panel4, BorderLayout.NORTH);
        bOrdenar = new JButton();
        bOrdenar.setText("Ordenar");
        panel4.add(bOrdenar);
        final JPanel panel5 = new JPanel();
        panel5.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        panel3.add(panel5, BorderLayout.CENTER);
        bSortir = new JButton();
        bSortir.setText("Sortir");
        panel5.add(bSortir);
        final JScrollPane scrollPane1 = new JScrollPane();
        scrollPane1.setPreferredSize(new Dimension(380, 128));
        lamina.add(scrollPane1, BorderLayout.WEST);
        consultes = new JList();
        scrollPane1.setViewportView(consultes);
        final JScrollPane scrollPane2 = new JScrollPane();
        lamina.add(scrollPane2, BorderLayout.CENTER);
        resultat = new JTextArea();
        scrollPane2.setViewportView(resultat);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return lamina;
    }

}
