package Latihanpbo_pemesanantiket;

/**
 * Tiket Kereta. Total bayar = (harga satu tiket x jumlah tiket) + biaya layanan.
 * Biaya layanan dibayar sekali saja, berapapun jumlah tiket yang dibeli.
 */
public class Kereta extends Tiket{
    
    private double biayaLayanan;
    
    public Kereta(String kodeTiket, String tujuan, double hargaPerTiket,
            int kursiTersedia, double biayaLayanan){
        super(kodeTiket, tujuan, hargaPerTiket, kursiTersedia);
        if (biayaLayanan <0){
             throw new IllegalArgumentException("Biaya layanan tidak boleh negatif.");
        }
        this.biayaLayanan = biayaLayanan;
    }
    
   @Override
   public double hitungTotalPembayaran(int jumlah){
       if (jumlah<=0){
           throw new IllegalArgumentException("Jumlah tiket harus lebih dari 0.");
       }
       return (getHargaPerTiket()* jumlah) + biayaLayanan;
   }
   
   public String getJenis(){
       return "Kereta";
   }
}