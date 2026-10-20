package Latihanpbo_pemesanantiket;

/**
 * Exception yang dilemparkan ketika jumlah tiket yang diminta
 * melebihi jumlah yang tersedia
 * dan lainnya
 */
public class KursiTidakTersediaException extends Exception {
    
    public KursiTidakTersediaException(String pesan){
        super(pesan);
    }
}
