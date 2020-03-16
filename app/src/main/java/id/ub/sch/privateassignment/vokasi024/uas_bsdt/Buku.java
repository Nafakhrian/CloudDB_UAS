package id.ub.sch.privateassignment.vokasi024.uas_bsdt;

class Buku {
    String id;
    String judul;
    String pengarang;
    String penerbit;
    String stock;

    public Buku(String judul, String pengarang, String penerbit, String stock) {
        this.judul = judul;
        this.pengarang = pengarang;
        this.penerbit = penerbit;
        this.stock = stock;
    }

    public Buku() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getPengarang() {
        return pengarang;
    }

    public void setPengarang(String pengarang) {
        this.pengarang = pengarang;
    }

    public String getPenerbit() {
        return penerbit;
    }

    public void setPenerbit(String penerbit) {
        this.penerbit = penerbit;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }
}
