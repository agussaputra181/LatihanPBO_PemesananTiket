package latihanpbo_pemesanantiket;

/**
 * Data umum yang dimiliki semua tiket : kode, tujuan,harga,dan kursi.
 * Tiket tidak bisa dibuat langsung dari class ini,harus lewat class bus atau kereta.
 */
public abstract class Tiket{
    
    //==== Data yang disimpan setiap tiket ====
    private String kodeTiket;
    private String tujuan;
    private double hargaPerTiket;
    private int kursiTersedia;
    
    //==== Mengisi data saat tiket dibuat (sambil dicek)====
     public Tiket(String kodeTiket, String tujuan, double hargaPerTiket, int kursiTersedia) {
         if (kodeTiket == null || kodeTiket.trim().isEmpty()) {
             throw new IllegalArgumentException("Kode tiket tidak boleh kosong.");
         }
         if (tujuan == null || tujuan.trim().isEmpty()){
             throw new IllegalArgumentException("Tujuan tidak boleh kosong.");
         }
         if (hargaPerTiket <=0){
             throw new IllegalArgumentException("Harga tidak boleh 0.");
         }
         if (kursiTersedia <0){
             throw new IllegalArgumentException("Kursi tidak boleh negatif.");
         }
         this.kodeTiket = kodeTiket;
         this.tujuan = tujuan;
         this.hargaPerTiket = hargaPerTiket;
         this.kursiTersedia = kursiTersedia;
         }
     
     //==== Cara class lain melihat data tiket ====
     public String getKodeTiket(){
         return kodeTiket;
     }
     
     public String getTujuan(){
         return tujuan;
     }
     
     public double getHargaPerTiket(){
         return hargaPerTiket;
     }
     
     public int getKursiTersedia(){
         return kursiTersedia;
     }
     
     //==== Mengurangi dan menambah kursi ====
     public void KurangiKursi (int jumlah) throws KursiTidakTersediaException {
         if (jumlah <=0){
             throw new IllegalArgumentException("Jumlah tiket harus lebih dari 0.");
         }
         if (jumlah > kursiTersedia){
             throw new IllegalArgumentException("Kursi tidak cukup. Diminta" + jumlah 
                     +", tersedia hanya" + kursiTersedia + ".");
         }
         kursiTersedia -= jumlah;
     }
     
     public void tambahKursi(int jumlah){
         if (jumlah <=0){
             throw new IllegalArgumentException("Jumlah kursi harus lebih dari 0.");  
         }
         kursiTersedia += jumlah;
     }
     
     //==== Rumus total bayar, wajib ditulis sendiri oelh Bus dan Kereta ====
     public abstract double hitungTotalPembayaran(int jumlah);
     }