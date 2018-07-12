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

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class LectorCSV {

  public ArrayList LeerCSV(File archivo) {

    ArrayList<MateriaData> listaObtenida =
        new ArrayList<MateriaData>(); // Inicializar Arraylist de objetos tipo MateriaData

    Scanner inputStream; // Declarar Scanner para leer el archivo

    MateriaData materias;

    try {
      inputStream = new Scanner(archivo);

      inputStream.useDelimiter(","); // Delimitar por comas

      // Leer cada parte obtenida por el Scanner hasta el fin del archivo
      while (inputStream.hasNext()) {

        // Inicializar variable con datos tipo placeholder
        materias = new MateriaData("0", "0", 0, true, 0, true);

        // Agregar datos obtenidos al objeto materias de tipo MateriaData
        materias.setMateriaNombre(inputStream.next());
        materias.setMateriaCiclo(Integer.parseInt(inputStream.next()));
        materias.setMateriaCalificacion(Integer.parseInt(inputStream.next()));
        materias.setMateriaAprobado(Boolean.valueOf(inputStream.next()));

        listaObtenida.add(materias); // Agregar objeto al ArrayList
      }

      inputStream.close();

    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }

    return listaObtenida;
  }
}
