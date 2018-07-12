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

public class MateriaData {
  private String materiaNombre;
  private String materiaTitulacion;
  private int materiaCiclo;
  private boolean materiaActivo;
  private double materiaCalificacion;
  private boolean materiaAprobado;

  public MateriaData(
      String materiaNombre,
      String materiaTitulacion,
      int materiaCiclo,
      boolean materiaActivo,
      double materiaCalificacion,
      boolean materiaAprobado) {

    this.materiaNombre = materiaNombre;
    this.materiaTitulacion = materiaTitulacion;
    this.materiaCiclo = materiaCiclo;
    this.materiaActivo = materiaActivo;
    this.materiaCalificacion = materiaCalificacion;
    this.materiaAprobado = materiaAprobado;
  }

  public void setMateriaNombre(String materiaNombre) {
    this.materiaNombre = materiaNombre;
  }

  public String getMateriaNombre() {
    return materiaNombre;
  }

  public void setMateriaTitulacion(String materiaTitulacion) {
    this.materiaTitulacion = materiaTitulacion;
  }

  public String getMateriaTitulacion() {
    return materiaTitulacion;
  }

  public void setMateriaCiclo(int materiaCiclo) {
    this.materiaCiclo = materiaCiclo;
  }

  public int getMateriaCiclo() {
    return materiaCiclo;
  }

  public void setMateriaActivo(boolean materiaActivo) {
    this.materiaActivo = materiaActivo;
  }

  public boolean isMateriaActivo() {
    return materiaActivo;
  }

  public void setMateriaCalificacion(double materiaCalificacion) {
    this.materiaCalificacion = materiaCalificacion;
  }

  public double getMateriaCalificacion() {
    return materiaCalificacion;
  }

  public void setMateriaAprobado(boolean materiaAprobado) {
    this.materiaAprobado = materiaAprobado;
  }

  public boolean isMateriaAprobado() {
    return materiaAprobado;
  }
}
