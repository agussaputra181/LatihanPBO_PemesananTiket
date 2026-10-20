package Latihanpbo_pemesanantiket;

import java.time.LocalDate;
import java.util.Locale;

/**
 * Satu catatan pembelian tiket, seperti struk, lengkap dengan status AKTIF/DIBATALKAN.
 */
public class Pemesanan{
    
    //==== Data yang dicatat di setiap pemesanana ====
    private String kodePesan;
    private String namaPemesan;
    private Tiket tiket;
    private int jumlahTiket;
    private LocalDate tanggalPesan;
    private LocalDate tanggalKeberangkatan;
    private String status;
    
    //==== Membuat Pemesanan Baru (data dicek dulu, kursi dikurangi terakhir) ====
    public Pemesanan(String kodePesan, String namaPemesan, Tiket tiket,
            int jumlahTiket, LocalDate tanggalPesan, LocalDate tanggalKeberangkatan)
            throws KursiTidakTersediaException, TanggalTidakValidException {
        
        if (tiket == null) {
            throw new IllegalArgumentException("Tiket belum dipilih.");
        }
        if (jumlahTiket <= 0){
            throw new IllegalArgumentException("Jumlah tiket harus lebih dari 0.");
        }
        if (tanggalPesan == null || tanggalKeberangkatan == null){
            throw new TanggalTidakValidException("Tanggal tidak boleh kosong.");
        }
        if (tanggalKeberangkatan.isBefore(tanggalPesan)){
            throw new TanggalTidakValidException("Tanggal keberangkatan("+ tanggalKeberangkatan
            +") tidak boleh sebelum tanggal pemesanan (" + tanggalPesan+").");
        }
        
        tiket.KurangiKursi(jumlahTiket);
        
        this.kodePesan = kodePesan;
        this.namaPemesan = namaPemesan;
        this.tiket = tiket;
        this.jumlahTiket = jumlahTiket;
        this.tanggalPesan = tanggalPesan;
        this.tanggalKeberangkatan = tanggalKeberangkatan;
        this.status ="AKTIF";
    }
    
    //==== Menghitung total Bayar dan Mencetak Struk ====
    private double hitungTotal(){
        return tiket.hitungTotalPembayaran(jumlahTiket);
    }
    
    public void cetakDetail(){
        String jenis;
        if (tiket instanceof Bus){
            jenis = ((Bus)tiket).getJenis();
        } else {
            jenis = ((Kereta)tiket).getJenis();
        }
        Locale indonesia = Locale.forLanguageTag("id.ID");
        
        System.out.println("==========Detail Pemesanan==========");
        System.out.println("KOde Pesan          : "+ kodePesan);
        System.out.println("Nama Pemesan        : "+ namaPemesan);
        System.out.println("Jenis Tiket         : "+ jenis);
        System.out.println("KOde Tiket          : "+ tiket.getKodeTiket());
        System.out.println("Tujuan              : "+ tiket.getTujuan());
        System.out.println("Jumlah Tiket        : "+ jumlahTiket);
        System.out.println("Tanggal Pesan       : "+ tanggalPesan);
        System.out.println("Tanggal Berangkat   : "+ tanggalKeberangkatan);
        System.out.printf(indonesia, "Total Bayar   : Rp%,.0f%n", hitungTotal());
        System.out.println("Status              : "+ status);
        System.out.println("===================================="); 
    }
    
    //==== Cara class lain melihat data pemesanan ====
    public Tiket getTiket(){
        return tiket;
    }
    
    public int getJumlahTiket(){
        return jumlahTiket;
    }
    
    public LocalDate gettanggalPesan(){
        return tanggalPesan;
    }
    
    public LocalDate gettanggalKeberangkatan(){
        return tanggalKeberangkatan;
    }
    
    public String getStatus(){
        return status;
    }
    
    //==== Membatalkan pesanan dan mengembalikan kursi ====
    public void batalkan(){
        if(status.equals("DIBATALKAN")){
            return;
        }
        status = "DIBATALKAN";
        tiket.tambahKursi(jumlahTiket);
    }
}