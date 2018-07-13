/*
 * The MIT License
 *
 * Copyright 2018 Pablo Castro.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package validador;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import info.debatty.java.stringsimilarity.Jaccard;
import info.debatty.java.stringsimilarity.LongestCommonSubsequence;

public class PantallaPrincipal {
  private JPanel panelPrincipal;
  private JButton botonSubirArchivo;
  private JLabel labelTitulo;
  private JLabel labelInstrucciones;
  private JLabel labelArchivoSeleccionado;
  private JButton botonProcesarArchivo;
  private JTable tablaParesMaterias;
  private JLabel imagenLogo;

  // Declaracion de FileChooser para seleccionar el archivo CSV
  final JFileChooser fc = new JFileChooser();

  // Declaracion de variable tipo File para almacenar el archivo CSV
  private File archivoSeleccionado = null;

  // Declaracion de conector de la base de datos
  static ConectorBaseDeDatos conector = new ConectorBaseDeDatos();

  private ArrayList<MateriaData> materiasEstudiante;
  private ArrayList<MateriaData> materiasBase;

  // Evento al presionar el boton botonSubirArchivo, el cual inicializa el FileChooser
  public PantallaPrincipal() {
    botonSubirArchivo.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {

            // Abrir el FileChooser dentro del panelPrincipal
            int returnVal = fc.showOpenDialog(panelPrincipal);

            // Si el usuario selecciona correctamente un archivo, seguir con la asignacion a la
            // variable, caso contrario, no hacer nada
            if (returnVal == JFileChooser.APPROVE_OPTION) {

              // Agregar el archivo seleccionado a la variable
              archivoSeleccionado = fc.getSelectedFile();

              // Indicar al usuario que archivo selecciono
              labelArchivoSeleccionado.setText(
                  "Archivo seleccionado:" + " " + archivoSeleccionado.getName());

              // Inicializar la clase LectorCSV y transformar el archivo CSV en una ArrayList de
              // MateriaData
              LectorCSV lector = new LectorCSV();
              materiasEstudiante = lector.LeerCSV(archivoSeleccionado);
            }
          }
        });

    botonProcesarArchivo.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {

            materiasBase = conector.MapearBaseDeDatosConMateriaData();

            String[] columnasTabla = {"Materias del estudiante", "Materias del Pensum"};

            DefaultTableModel modeloTablaParesMaterias = new DefaultTableModel(null, columnasTabla);

            Jaccard similaridadJaccar = new Jaccard();
            LongestCommonSubsequence similaridadLCS = new LongestCommonSubsequence();

            for (MateriaData materiaEstudiante : materiasEstudiante) {
              for (MateriaData materiaBase : materiasBase) {

                // System.out.print(materiaEstudiante.getMateriaNombre() + " comparada con " +
                // materiaBase.getMateriaNombre() + "      ");

                // System.out.print(similaridadJaccar.similarity(materiaEstudiante.getMateriaNombre(), materiaBase.getMateriaNombre()) + "     ");

                // System.out.println(similaridadLCS.distance(materiaEstudiante.getMateriaNombre(),
                // materiaBase.getMateriaNombre()) + "     ");

                if ((similaridadJaccar.similarity(
                            materiaEstudiante.getMateriaNombre(), materiaBase.getMateriaNombre())
                        >= 0.7)
                    || (similaridadLCS.distance(
                            materiaEstudiante.getMateriaNombre(), materiaBase.getMateriaNombre())
                        <= 5)) {
                  System.out.print(materiaEstudiante.getMateriaNombre() + "     ");
                  System.out.print(
                      similaridadJaccar.similarity(
                              materiaEstudiante.getMateriaNombre(), materiaBase.getMateriaNombre())
                          + "     ");
                  System.out.println(
                      similaridadLCS.distance(
                              materiaEstudiante.getMateriaNombre(), materiaBase.getMateriaNombre())
                          + "     ");

                  Object[] filaModeloTablaParesMaterias = {
                    materiaEstudiante.getMateriaNombre(), materiaBase.getMateriaNombre()
                  };
                  modeloTablaParesMaterias.addRow(filaModeloTablaParesMaterias);
                }
              }
            }

            tablaParesMaterias.setModel(modeloTablaParesMaterias);
          }
        });
  }

  public static void main(String[] args) {
    JFrame frame = new JFrame("PantallaPrincipal");
    frame.setContentPane(new PantallaPrincipal().panelPrincipal);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.pack();
    frame.setVisible(true);

    conector.InicializarBaseDeDatos();
  }
}
