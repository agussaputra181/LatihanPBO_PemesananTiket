package Latihanpbo_pemesanantiket;

/**
 * Exception yang dilemparkan ketika tanggal pemesanan, keberangkatan, 
 * atau pembatalan tidak sesuai aturan
 */
public class TanggalTidakValidException extends Exception {
    
    public TanggalTidakValidException(String pesan){
        super(pesan);
    }
}
