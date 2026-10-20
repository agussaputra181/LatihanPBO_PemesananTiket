package Latihanpbo_pemesanantiket;

/**
 * Tiket Bus. Total bayaar = harga satu tiket x jumlah tikey, tanpa biaya lainnya.
 */
public class Bus extends Tiket{
    
    public Bus(String kodeTiket, String tujuan, double hargaPerTiket, int kursiTersedia){
        super(kodeTiket, tujuan, hargaPerTiket, kursiTersedia);
    }
    
    @Override
    public double hitungTotalPembayaran (int jumlah){
        if (jumlah <=0){
             throw new IllegalArgumentException("Jumlah tiket harus lebih dari 0.");
        }
        return getHargaPerTiket()* jumlah;
    }
    
    public String getJenis(){
        return "Bus";
    }
}