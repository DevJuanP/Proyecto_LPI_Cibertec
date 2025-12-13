package model;

public class Libro {

    private int id;
    private String titulo;
    private String autor;
    private String imagen;
    private String categoria;

    public Libro() {
    }

    public Libro(int id, String titulo, String autor, String imagen, String categoria) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.imagen = imagen;
        this.categoria = categoria;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
}
