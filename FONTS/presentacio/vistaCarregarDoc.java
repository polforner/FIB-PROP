package presentacio;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * Aquesta vista s’encarrega de carregar un document guardat a l’ordinador i obrir-lo. La vista obre l’explorador d’arxius
 * on es podran seleccionar únicament fitxers que el nostre sistema és capaç d’obrir. Es proporciona un boto de
 * cancel·lar que tornara a la gestor del documents i un boto d’obrir que fara que es carrega el document
 * seleccionat.
 * @author Zheshuo Lin
 */
public class vistaCarregarDoc {
    /** Finestra de seleccio de l'arxiu que es vol carregar al nostre sistema*/
    JFileChooser chooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

    /** Constructora de la finestra de carregar document */
    public vistaCarregarDoc() {
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        chooser.setFileFilter(new FileNameExtensionFilter("xml/txt/json", "xml","txt","json"));
        chooser.setDialogTitle("Selecciona fitxer");
        chooser.setCurrentDirectory(new File(System.getProperty("user.dir") + "/data/dades"));
        int returnValue = chooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            boolean exit=true;
            File arxiu = chooser.getSelectedFile();
            if (arxiu.getName().endsWith(".txt")) {
                try {
                    CtrlPresentacio.carregarDocumentTxt(arxiu.getAbsolutePath());
                }
                catch (FileNotFoundException ex) {
                    new Missatge(ex.getMessage());
                    exit=false;
                }
                catch (RuntimeException ex) {
                    new Missatge(ex.getMessage());
                    exit=false;
                }
                catch (Exception e) {
                    new Missatge(e.getMessage());
                    System.out.println("error");
                    exit=false;
                }
            }
            else if (arxiu.getName().endsWith(".xml")) {
                try {
                    CtrlPresentacio.carregarDocumentXml(arxiu.getAbsolutePath());
                } catch (FileNotFoundException ex) {
                    new Missatge(ex.getMessage());
                    exit=false;
                }
                catch (RuntimeException ex) {
                    new Missatge(ex.getMessage());
                    exit=false;
                }catch (Exception e) {
                    new Missatge(e.getMessage());
                    System.out.println("error");
                    exit=false;

                }
            }
            else if (arxiu.getName().endsWith(".json")) {
                try {
                    CtrlPresentacio.carregarDocumentJson(arxiu.getAbsolutePath());
                } catch (FileNotFoundException ex) {
                    new Missatge(ex.getMessage());
                    exit=false;
                }
                catch (RuntimeException ex) {
                    new Missatge(ex.getMessage());
                    exit=false;
                }
                catch (Exception e) {
                    new Missatge(e.getMessage());
                    exit=false;
                }
            }
            else {
                new Missatge("El sistema no supporta aquest tipus de document");
            }
            if (exit) {
                new Missatge("Document s'ha carregat correctament");
            }
        } else if (returnValue == JFileChooser.CANCEL_OPTION) {

        }
    }

}