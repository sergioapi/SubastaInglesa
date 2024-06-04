package subasta;

import java.util.Objects;

public class Subasta {
    private int idSubasta;
    private Estado estado;
    private int ronda;
    private String ganador;

    private String tituloLibro;

    private float precioLibro;

    private float incremento;

    public Subasta(int idSubasta, String titulo, float precio, float incremento) {
        this.idSubasta = idSubasta;
        this.tituloLibro = titulo;
        this.precioLibro = precio;
        this.incremento = incremento;

        this.estado = Estado.INICIANDOSE;
        this.ronda = 0;
        this.ganador = "-";
    }

    public int getIdSubasta() {
        return idSubasta;
    }

    public void setIdSubasta(int idSubasta) {
        this.idSubasta = idSubasta;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public int getRonda() {
        return ronda;
    }

    public void setRonda(int ronda) {
        this.ronda = ronda;
    }

    public String getGanador() {
        return ganador;
    }

    public void setGanador(String ganador) {
        this.ganador = ganador;
    }

    public String getTituloLibro() {
        return tituloLibro;
    }

    public void setTituloLibro(String tituloLibro) {
        this.tituloLibro = tituloLibro;
    }

    public float getPrecioLibro() {
        return precioLibro;
    }

    public void setPrecioLibro(float precioLibro) {
        this.precioLibro = precioLibro;
    }

    public float getIncremento() {
        return incremento;
    }

    public void setIncremento(float incremento) {
        this.incremento = incremento;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subasta subasta = (Subasta) o;
        return Objects.equals(idSubasta, subasta.idSubasta);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idSubasta);
    }

    enum Estado {
        EN_CURSO, FINALIZADA, INICIANDOSE
    }
}
