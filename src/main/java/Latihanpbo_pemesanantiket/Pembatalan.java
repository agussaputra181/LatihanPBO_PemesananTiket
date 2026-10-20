package Latihanpbo_pemesanantiket;

import java.time.LocalDate;
import java.util.Locale;

/**
 * Mengecek apakah sebuah pemesanan boleh dibatalkan,lalu membatalkannya.
 * Besar biaya pembatalan diketik sendiri oleh pengguna.
 */
public class Pembatalan{
    
    //==== Data yang dicatat saat pembatalan ====
    private Pemesanan pemesanan;
    private LocalDate tanggalPembatalan;
    private double biayaPembatalan; 
    
    //==== Mengecek semua aturan sebelum pembatalan diterima ====
    public Pembatalan(Pemesanan pemesanan, LocalDate tanggalPembatalan,
            double biayaPembatalan)throws TanggalTidakValidException {
        
        if (pemesanan == null) {
            throw new IllegalArgumentException("Pemesanan tidak ditemukan.");
        }
        if (!pemesanan.getStatus().equals("AKTIF")){
            throw new IllegalStateException("Pemesanan ini sudah dibatalkan sebelumnya.");
        }
        if (biayaPembatalan <0){
            throw new IllegalArgumentException("Biaya Pembatalan tidak boleh negatif.");
        }
        double totalBayar = pemesanan.getTiket()
                .hitungTotalPembayaran(pemesanan.getJumlahTiket());
        if (biayaPembatalan > totalBayar){
            throw new IllegalArgumentException("Biaya pembatalan tidak boleh melebihi"
            + "total pemabayaran.");
        }
        if (tanggalPembatalan == null){
            throw new TanggalTidakValidException("Tanggal pembatalan tidak boleh kosong");
        }
        if (tanggalPembatalan.isBefore(pemesanan.gettanggalPesan())){
            throw new TanggalTidakValidException("Tanggal pembatalan tidak boleh sebelum"
                    + "tanggal pemesanan ("+ pemesanan.gettanggalPesan()+").");
        }
        if (tanggalPembatalan.isAfter(pemesanan.gettanggalKeberangkatan())){
            throw new TanggalTidakValidException("Pembatalan ditolak: keberangkatan ("
                    + pemesanan.gettanggalKeberangkatan()+") sudah lewat.");
        }
        
        this.pemesanan = pemesanan;
        this.tanggalPembatalan = tanggalPembatalan;
        this.biayaPembatalan = biayaPembatalan;
    }
    
    //==== Menjalankan pembatalan dan mencetak hasilnya ====
    private double hitungDenda(){
        return biayaPembatalan;
    }
    
    public void prosesPembatalan(){
        pemesanan.batalkan();
        Locale indonesia = Locale.forLanguageTag("id.ID");
        System.out.println("Pembatalan berhasil.");
        System.out.println("Tanggal Pembatalan         : "+ tanggalPembatalan);
        System.out.printf(indonesia, "Biaya Pembatalan   : Rp%,.0f%n", hitungDenda());
        System.out.println("Status sekarang            : "+ pemesanan.getStatus());
        System.out.println("Kursi tersedia kini        : "+ pemesanan.getTiket().getKursiTersedia()); 
    }
    
    //==== Cara class lain melihat data pembatalan ====
    public LocalDate gettanggalPembatalan(){
        return tanggalPembatalan;
    }
    
    public double getbiayaPembatalan(){
    return biayaPembatalan;
    }
}