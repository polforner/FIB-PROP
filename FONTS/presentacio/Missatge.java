package presentacio;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Aquesta vista s'encarrega de mostrar tots missatges d'error o d'exit
 * @author Zheshuo Lin
 */
public class Missatge extends JDialog {

    /**
     * Constructor de la vista
     * @param missatge el missatge que volem mostrar a la pantalla
     */

    public Missatge (String missatge) {

        setTitle("Missatge");
        setBounds(600, 200, 600, 300);
        setLayout(null);

        JLabel txtErrorDoc = new JLabel(missatge);
        txtErrorDoc.setBounds(160, 40, 400, 80);
        JButton botoSortirErrorDoc = new JButton("Sortir");
        botoSortirErrorDoc.setVisible(true);
        botoSortirErrorDoc.setBounds(250, 220, 100, 30);
        add(txtErrorDoc);
        add(botoSortirErrorDoc);
        ActionListener lSortirError = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                setVisible(false);
            }
        };
        setVisible(true);

        botoSortirErrorDoc.addActionListener(lSortirError);
    }
}
