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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ConectorBaseDeDatos {

  // Variables para conexion a la bdd
  private String driver = "org.h2.Driver";
  private String bddUrl = "jdbc:h2:~/BaseDatosConvalidacion";
  private String bddUsuario = "test";
  private String bddClave = "test";

  // Comandos para crear las tablas necesarias en la bdd
  private String tablaTitulacionesInsert =
      "CREATE TABLE IF NOT EXISTS TITULACIONES(ID INT PRIMARY KEY AUTO_INCREMENT NOT NULL, NOMBRE VARCHAR(255) NOT NULL)";
  private String tablaMateriasInsert =
      "CREATE TABLE IF NOT EXISTS MATERIAS(ID INT PRIMARY KEY AUTO_INCREMENT NOT NULL, NOMBRE VARCHAR(255) NOT NULL, CICLO INT NOT NULL, ACTIVO BOOLEAN NOT NULL, TITULACION INT NOT NULL, FOREIGN KEY (TITULACION) REFERENCES TITULACIONES(ID))";

  // Comandos para verificar si las tablas tienen informacion o estan vacias
  private String verificarTablaTitulaciones = "SELECT * FROM TITULACIONES";
  private String verificarTablaMaterias = "SELECT * FROM MATERIAS";

  // Comandos para insertar las carreras y materias existentes en la universidad
  private String[] titulacionesInsert = {
    "INSERT INTO TITULACIONES(NOMBRE) VALUES('ADMINISTRACIÓN DE EMPRESAS')",
    "INSERT INTO TITULACIONES(NOMBRE) VALUES('ADMINISTRACIÓN DE EMPRESAS TURÍSTICAS Y HOTELERAS')",
    "INSERT INTO TITULACIONES(NOMBRE) VALUES('ADMINISTRACIÓN EN BANCA Y FINANZAS ')",
    "INSERT INTO TITULACIONES(NOMBRE) VALUES('ADMINISTRACIÓN EN GESTIÓN PÚBLICA')",
    "INSERT INTO TITULACIONES(NOMBRE) VALUES('ARQUITECTURA')",
    "INSERT INTO TITULACIONES(NOMBRE) VALUES('ARTES PLÁSTICAS Y DISEÑO')",
    "INSERT INTO TITULACIONES(NOMBRE) VALUES('BIOLOGÍA')",
    "INSERT INTO TITULACIONES(NOMBRE) VALUES('BIOQUÍMICA Y FARMACIA')",
    "INSERT INTO TITULACIONES(NOMBRE) VALUES('COMUNICACIÓN SOCIAL')",
    "INSERT INTO TITULACIONES(NOMBRE) VALUES('CONTABILIDAD Y AUDITORÍA')",
    "INSERT INTO TITULACIONES(NOMBRE) VALUES('DERECHO')",
    "INSERT INTO TITULACIONES(NOMBRE) VALUES('ECONOMÍA')",
    "INSERT INTO TITULACIONES(NOMBRE) VALUES('ELECTRÓNICA Y TELECOMUNICACIONES')",
    "INSERT INTO TITULACIONES(NOMBRE) VALUES('GASTRONOMÍA')",
    "INSERT INTO TITULACIONES(NOMBRE) VALUES('GEOLOGÍA Y MINAS')",
    "INSERT INTO TITULACIONES(NOMBRE) VALUES('GESTIÓN AMBIENTAL')",
    "INSERT INTO TITULACIONES(NOMBRE) VALUES('HOTELERÍA Y TURISMO')",
    "INSERT INTO TITULACIONES(NOMBRE) VALUES('INFORMATICA')",
    "INSERT INTO TITULACIONES(NOMBRE) VALUES('INGENIERÍA AGROPECUARIA')",
    "INSERT INTO TITULACIONES(NOMBRE) VALUES('INGENIERÍA CIVIL')",
    "INSERT INTO TITULACIONES(NOMBRE) VALUES('INGENIERÍA EN ALIMENTOS')",
    "INSERT INTO TITULACIONES(NOMBRE) VALUES('INGENIERÍA INDUSTRÍAL')",
    "INSERT INTO TITULACIONES(NOMBRE) VALUES('INGENIERÍA QUÍMICA')",
    "INSERT INTO TITULACIONES(NOMBRE) VALUES('INGLÉS')",
    "INSERT INTO TITULACIONES(NOMBRE) VALUES('PSICOLOGÍA')",
    "INSERT INTO TITULACIONES(NOMBRE) VALUES('RELACIONES PÚBLICAS')",
    "INSERT INTO TITULACIONES(NOMBRE) VALUES('SISTEMAS INFORMÁTICOS Y COMPUTACION')"
  };
  private String[] materiasInsert = {
    "INSERT INTO MATERIAS(NOMBRE,CICLO,ACTIVO,TITULACION) VALUES('FUNDAMENTOS INFORMATICOS',1,TRUE,18)",
    "INSERT INTO MATERIAS(NOMBRE,CICLO,ACTIVO,TITULACION) VALUES('LOGICA DE LA PROGRAMACION',1,TRUE,18)",
    "INSERT INTO MATERIAS(NOMBRE,CICLO,ACTIVO,TITULACION) VALUES('METODOLOGIA DE ESTUDIO',1,TRUE,18)",
    "INSERT INTO MATERIAS(NOMBRE,CICLO,ACTIVO,TITULACION) VALUES('REALIDAD NACIONAL Y AMBIENTAL',1,TRUE,18)",
    "INSERT INTO MATERIAS(NOMBRE,CICLO,ACTIVO,TITULACION) VALUES('EXPRESION ORAL Y ESCRITA',1,TRUE,18)",
    "INSERT INTO MATERIAS(NOMBRE,CICLO,ACTIVO,TITULACION) VALUES('MATEMATICAS DISCRETAS',2,TRUE,18)",
    "INSERT INTO MATERIAS(NOMBRE,CICLO,ACTIVO,TITULACION) VALUES('CONTABILIDAD',2,TRUE,18)",
    "INSERT INTO MATERIAS(NOMBRE,CICLO,ACTIVO,TITULACION) VALUES('FUNDAMENTOS MATEMATICOS',2,TRUE,18)",
    "INSERT INTO MATERIAS(NOMBRE,CICLO,ACTIVO,TITULACION) VALUES('FUNDAMENTOS DE LA PROGRAMACION',2,TRUE,18)",
    "INSERT INTO MATERIAS(NOMBRE,CICLO,ACTIVO,TITULACION) VALUES('ESTADISTICA',3,TRUE,18)",
    "INSERT INTO MATERIAS(NOMBRE,CICLO,ACTIVO,TITULACION) VALUES('OGANIZACION Y ADMINISTRACION EMPRESARIAL',3,TRUE,18)",
    "INSERT INTO MATERIAS(NOMBRE,CICLO,ACTIVO,TITULACION) VALUES('ESTRUCTURA DE DATOS',3,TRUE,18)",
    "INSERT INTO MATERIAS(NOMBRE,CICLO,ACTIVO,TITULACION) VALUES('PROGRAMACION DE ALGORITMOS',3,TRUE,18)",
    "INSERT INTO MATERIAS(NOMBRE,CICLO,ACTIVO,TITULACION) VALUES('ECONOMIA FINANZAS E INVERSIONES',4,TRUE,18)",
    "INSERT INTO MATERIAS(NOMBRE,CICLO,ACTIVO,TITULACION) VALUES('CALCULO',4,TRUE,18)",
    "INSERT INTO MATERIAS(NOMBRE,CICLO,ACTIVO,TITULACION) VALUES('FUNDAMENTOS DE BASE DE DATOS',4,TRUE,18)",
    "INSERT INTO MATERIAS(NOMBRE,CICLO,ACTIVO,TITULACION) VALUES('PROGRAMACION AVANZADA',4,TRUE,18)",
    "INSERT INTO MATERIAS(NOMBRE,CICLO,ACTIVO,TITULACION) VALUES('METODOS CUANTITATIVOS',5,TRUE,18)",
    "INSERT INTO MATERIAS(NOMBRE,CICLO,ACTIVO,TITULACION) VALUES('ARQUITECTURA DE COMPUTADORES',5,TRUE,18)",
    "INSERT INTO MATERIAS(NOMBRE,CICLO,ACTIVO,TITULACION) VALUES('BASE DE DATOS AVANZADA',5,TRUE,18)",
    "INSERT INTO MATERIAS(NOMBRE,CICLO,ACTIVO,TITULACION) VALUES('FUNDAMENTOS DE INGENIERIA DE SOFTWARE',5,TRUE,18)",
    "INSERT INTO MATERIAS(NOMBRE,CICLO,ACTIVO,TITULACION) VALUES('ANTROPOLOGIA',6,TRUE,18)",
    "INSERT INTO MATERIAS(NOMBRE,CICLO,ACTIVO,TITULACION) VALUES('GESTION DE PROYECTOS INFORMATICOS',6,TRUE,18)",
    "INSERT INTO MATERIAS(NOMBRE,CICLO,ACTIVO,TITULACION) VALUES('SISTEMAS OPERATIVOS',6,TRUE,18)",
    "INSERT INTO MATERIAS(NOMBRE,CICLO,ACTIVO,TITULACION) VALUES('INGENIERIA DE REQUISITOS',6,TRUE,18)",
    "INSERT INTO MATERIAS(NOMBRE,CICLO,ACTIVO,TITULACION) VALUES('ETICA',7,TRUE,18)",
    "INSERT INTO MATERIAS(NOMBRE,CICLO,ACTIVO,TITULACION) VALUES('FUNDAMENTOS DE REDES Y TELECOMUNICACIONES',7,TRUE,18)",
    "INSERT INTO MATERIAS(NOMBRE,CICLO,ACTIVO,TITULACION) VALUES('INGENIERIA WEB',7,TRUE,18)",
    "INSERT INTO MATERIAS(NOMBRE,CICLO,ACTIVO,TITULACION) VALUES('TEORIA DE AUTOMATAS',8,TRUE,18)",
    "INSERT INTO MATERIAS(NOMBRE,CICLO,ACTIVO,TITULACION) VALUES('INTELIGENCIA ARTIFICIAL',8,TRUE,18)",
    "INSERT INTO MATERIAS(NOMBRE,CICLO,ACTIVO,TITULACION) VALUES('REDES Y SISTEMAS DISTRIBUIDOS',8,TRUE,18)",
    "INSERT INTO MATERIAS(NOMBRE,CICLO,ACTIVO,TITULACION) VALUES('ARQUITECTURA DE APLICACIONES',8,TRUE,18)",
    "INSERT INTO MATERIAS(NOMBRE,CICLO,ACTIVO,TITULACION) VALUES('SEGURIDAD DE LA INFORMACION',9,TRUE,18)",
    "INSERT INTO MATERIAS(NOMBRE,CICLO,ACTIVO,TITULACION) VALUES('GESTION DE TECNOLOGIAS DE INFORMACION',9,TRUE,18)",
    "INSERT INTO MATERIAS(NOMBRE,CICLO,ACTIVO,TITULACION) VALUES('INTELIGENCIA ARTIFICIAL AVANZADA',9,TRUE,18)",
    "INSERT INTO MATERIAS(NOMBRE,CICLO,ACTIVO,TITULACION) VALUES('INGENIERIA DE SOFTWARE',9,TRUE,18)",
    "INSERT INTO MATERIAS(NOMBRE,CICLO,ACTIVO,TITULACION) VALUES('ARQUITECTURA DE REDES',9,TRUE,18)",
    "INSERT INTO MATERIAS(NOMBRE,CICLO,ACTIVO,TITULACION) VALUES('SISTEMAS BASADOS EN EL CONOCIMIENTO',10,TRUE,18)",
    "INSERT INTO MATERIAS(NOMBRE,CICLO,ACTIVO,TITULACION) VALUES('CONTROL DE CALIDAD',10,TRUE,18)",
    "INSERT INTO MATERIAS(NOMBRE,CICLO,ACTIVO,TITULACION) VALUES('PLANEACION ESTRATEGICA',10,TRUE,18)",
    "INSERT INTO MATERIAS(NOMBRE,CICLO,ACTIVO,TITULACION) VALUES('AUDITORIA INFORMATICA',10,TRUE,18)",
  };

  public void InicializarBaseDeDatos() {
    try {
      Class.forName(driver);
      Connection conexion = DriverManager.getConnection(bddUrl, bddUsuario, bddClave);
      Statement instruccion = conexion.createStatement();
      instruccion.execute(tablaTitulacionesInsert); // Crear tabla titulaciones
      instruccion.execute(tablaMateriasInsert); // Crear tabla materias

      ResultSet tablaTitulacion =
          instruccion.executeQuery(verificarTablaTitulaciones); // Revisar si la tabla esta vacia

      // Si esta vacia, insertar los datos
      if (!tablaTitulacion.next()) {
        for (String insert : titulacionesInsert) {
          instruccion.addBatch(insert);
          System.out.println(insert);
        }
      }

      ResultSet tablaMaterias =
          instruccion.executeQuery(verificarTablaMaterias); // Revisar si la tabla esta vacia

      // Si esta vacia, insertar los datos
      if (!tablaMaterias.next()) {
        for (String insert : materiasInsert) {
          instruccion.addBatch(insert);
          System.out.println(insert);
        }
      }

      // Ejecutar todos los comandos SQL y cerrar las conexiones
      instruccion.executeBatch();
      instruccion.close();
      conexion.close();

    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public ArrayList MapearBaseDeDatosConMateriaData() {

    ResultSet resultado;

    ArrayList<MateriaData> listaObtenida =
        new ArrayList<MateriaData>(); // Arraylist para los objetos

    try {
      Class.forName(driver);
      Connection conexion = DriverManager.getConnection(bddUrl, bddUsuario, bddClave);
      Statement instruccion = conexion.createStatement();
      resultado = instruccion.executeQuery(verificarTablaMaterias);

      // Recorrer el ResultSet y asignar valores al Arraylist de objetos tipo MateriaData
      while (resultado.next()) {
        MateriaData materias = new MateriaData("0", "0", 0, true, 0, true);

        materias.setMateriaNombre(resultado.getString("NOMBRE"));
        materias.setMateriaActivo(Boolean.valueOf(resultado.getString("ACTIVO")));
        materias.setMateriaTitulacion(resultado.getString("TITULACION"));

        listaObtenida.add(materias); // Agregar objeto a ArrayList de objetos tipo MateriaData
      }

      // Cerrar conexiones
      instruccion.close();
      conexion.close();

    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return listaObtenida;
  }
}
