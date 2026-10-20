package Latihanpbo_pemesanantiket;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

/**
 *  Program utama yang dijalankan: meminta data tiket di awal, menampilkan menu,
 * lalu melayani pemesanan dan pembatalan, seperti kasir di loket.
 */
public class AplikasiTiket{
    
    // ===== Data yang dipakai selama program berjalan =====
    private static Scanner input = new Scanner(System.in);
    private static Bus tiketBus = null;
    private static Kereta tiketKereta = null;
    private static ArrayList<Pemesanan> daftarPemesanan = new ArrayList<>();
    
     // ===== Program mulai berjalan dari sini =====
    public static void main(String[] args){
        Locale indonesia =  Locale.forLanguageTag("id-ID");
        System.out.println("==== SISTEM PEMESANAN TIKET TRANSPORTASI UMUM ====");
        
        System.out.println("-- Data awal tiket Bus --");
        while (tiketBus == null){
             String kode = bacaTeks("Kode tiket      : ");
             String tujuan = bacaTeks("Tujuan          : ");
             double harga = bacaDouble("Harga per tiket : ", false);
             int kursi = bacaInt("Kursi tersedia  : ");
             try {
                 tiketBus = new Bus(kode, tujuan, harga, kursi);
             } catch (IllegalArgumentException e){
                  System.out.println("  Data tidak valid: " + e.getMessage() + " Ulangi.");
             }
        }
        
        System.out.println("-- Data awal tiket Kereta --");
         while (tiketKereta == null){
           String kode = bacaTeks("Kode tiket      : ");
           String tujuan = bacaTeks("Tujuan          : ");
           double harga = bacaDouble("Harga per tiket : ", false);
           int kursi = bacaInt("Kursi tersedia  : ");
           double layanan = bacaDouble("Biaya layanan   : ", true);
           try {
                tiketKereta = new Kereta(kode, tujuan, harga, kursi, layanan);
           } catch (IllegalArgumentException e) {
               System.out.println("  Data tidak valid: " + e.getMessage() + " Ulangi.");
           } 
         }
         
        int pilihan;
        do{
            tampilMenu();
            pilihan = bacaInt("Pilih menu:");
            switch (pilihan) {
                case 1:
                     System.out.println("[" + tiketBus.getJenis() + "] "
                              + tiketBus.getKodeTiket() + " - " + tiketBus.getTujuan());
                     System.out.printf(indonesia, "Harga per tiket : Rp%,.0f%n",
                              tiketBus.getHargaPerTiket());
                     System.out.println("Kursi tersedia  : " + tiketBus.getKursiTersedia());
                     break;
                case 2:
                     double layanan = tiketKereta.hitungTotalPembayaran(1)
                             - tiketKereta.getHargaPerTiket();
                     System.out.println("[" + tiketKereta.getJenis() + "] "
                        + tiketKereta.getKodeTiket() + " - " + tiketKereta.getTujuan());
                     System.out.printf(indonesia, "Harga per tiket : Rp%,.0f%n",
                             tiketKereta.getHargaPerTiket());
                     System.out.printf(indonesia, "Biaya layanan   : Rp%,.0f%n", layanan);
                     System.out.println("Kursi tersedia  : " + tiketKereta.getKursiTersedia());
                     break;
                case 3:
                     prosesPemesanan();
                     break;
                case 4:
                     if (daftarPemesanan.isEmpty()){
                         System.out.println("Belum ada pemesanan");
                     }
                     for (int i = 0; i < daftarPemesanan.size(); i++) {
                          System.out.println("Pemesanan nomor " + (i + 1));
                           daftarPemesanan.get(i).cetakDetail();
                     }
                     break;
                case 5:
                     prosesPembatalan();
                     break;
                case 0:
                     System.out.println("Terima kasih. Program selesai.");
                     break;
                default:
                       System.out.println("Menu tidak tersedia, Silahkan Coba Lagi.");
            }
        } while (pilihan !=0);
    }
    
    // ==== Menampilkan daftar pilihan Menu ====
    private static void tampilMenu(){
        System.out.println();
        System.out.println("--------------- Menu -------------");
        System.out.println("1. Tampilkan tiket Bus");
        System.out.println("2. Tampilkan tiket Kereta");
        System.out.println("3. Buat pemesanan");
        System.out.println("4. Tampilkan detail pemesanan");
        System.out.println("5. Batalkan pemesanan");
        System.out.println("0. Keluar");
        System.out.println("----------------------------------");
    }
    
    // ===== Melayani pembelian tiket (menu 3) =====
     private static void prosesPemesanan(){
         System.out.println("-- Buat Pemesanan --");
         System.out.println("Jenis tiket : 1. Bus 2. Kereta");
         int jenis = bacaInt("Pilih jenis:");
         
        Tiket dipilih;
        if(jenis == 1){
            dipilih = tiketBus;
        }else if(jenis == 2){
            dipilih = tiketKereta;
        } else {
            System.out.println("Jenis tiket tidak valid");
            return;
        }
        
        String status = "GAGAL";
        try{
            String kode = bacaTeks("Kode pemesanan                 : ");
            String nama = bacaTeks("Nama pemesan                   : ");
            int jumlah = bacaInt("Jumlah tiket                   : ");
            LocalDate tglPesan = bacaTanggal("Tanggal pesan (YYYY-MM-DD) : ");
            LocalDate tglBerangkat = bacaTanggal("Tanggal berangkat (YYYY-MM-DD) : ");
            
            Pemesanan p = new Pemesanan(kode, nama, dipilih, jumlah, tglPesan, tglBerangkat);
            daftarPemesanan.add(p);
            status = "BERHASIL";
            System.out.println("Pemesanan berhasil dibuat!");
            p.cetakDetail();
            System.out.println("Sisa Kursi tersedia :" + dipilih.getKursiTersedia());
        } catch (KursiTidakTersediaException e){
            System.out.println("Pemesanan gagal:"+ e.getMessage());
        } catch (TanggalTidakValidException e){
            System.out.println("Pemesanan gagal (tanggal): " + e.getMessage());
        } catch (IllegalArgumentException e){
            System.out.println("Pemesanan gagal: " + e.getMessage());
        } finally{
                System.out.println("[LOG] Transaksi pemesanan: " + status);
        }
    }
    
     // ===== Melayani pembatalan tiket (menu 5) =====
      private static void prosesPembatalan(){
          System.out.println("-- Batalkan Pemesanan --");
          if (daftarPemesanan.isEmpty()){
              System.out.println("Belum ada pemesanan.");
              return;
          }
          for (int i = 0; i < daftarPemesanan.size(); i++){
               Pemesanan p = daftarPemesanan.get(i);
               System.out.println((i + 1) + ". " + p.getTiket().getKodeTiket() + " | "
                       + p.getJumlahTiket() + " tiket | berangkat "
                       + p.gettanggalKeberangkatan() + " | " + p.getStatus());
          }
          
          String status = "GAGAL";
          try{
              int nomor = bacaInt("Nomor pemesanan yang dibatalkan : ");
              if (nomor < 1 || nomor > daftarPemesanan.size()){
                  throw new IllegalArgumentException("Nomor pemesanan " + nomor + " tidak ada.");
              }
              Pemesanan p = daftarPemesanan.get(nomor - 1);
              if (!p.getStatus().equals("AKTIF")){
                  throw new IllegalStateException("Pemesanan ini sudah dibatalkan sebelumnya.");
              }
              LocalDate tglBatal = bacaTanggal("Tanggal pembatalan (YYYY-MM-DD) : ");
              double biaya = bacaDouble("Biaya pembatalan                : ", true);
              
             Pembatalan batal = new Pembatalan(p, tglBatal, biaya);
             batal.prosesPembatalan();
             status = "BERHASIL";
          } catch (TanggalTidakValidException e){
              System.out.println("Pembatalan gagal (tanggal): " + e.getMessage());
          } catch (IllegalArgumentException | IllegalStateException e){
               System.out.println("Pembatalan gagal: " + e.getMessage());
          } finally {
              System.out.println("[LOG] Transaksi pembatalan: " + status);
          }
      }
      
       // ===== Alat bantu membaca ketikan (salah ketik diminta ulang) =====
       private static String bacaTeks(String prompt){
          while (true){
              System.out.println(prompt);
              String teks = input.nextLine().trim();
              if(!teks.isEmpty()){
                  return teks;
              }
              System.out.println("Input tidak boleh kosong.");
          }  
       }
       
       private static int bacaInt(String prompt){
            while (true){
                System.out.println(prompt);
                String teks = input.nextLine().trim();
                try{
                    return Integer.parseInt(teks);
                } catch (NumberFormatException e){
                    System.out.println(" Input harus berupa bilangan bulat. Coba lagi.");
                }
            }
       } 
        
       private static double bacaDouble(String prompt, boolean bolehNol){
          while (true){
              System.out.println(prompt);
              String teks = input.nextLine().trim();
              if (!teks.matches("\\d+(\\.\\d{1,2})?")){
                  System.out.println(" Ketik angka saja tanpa titik/koma ribuan, "
                          + "contoh: 100000 atau 12.5");
                  continue;
              }
              double nilai = Double.parseDouble(teks);
              if (Double.isNaN(nilai) || Double.isInfinite(nilai)){
                  System.out.println("Angka tidak valid. Coba lagi.");
              } else if (!bolehNol && nilai <= 0) {
                  System.out.println("  Nilai harus lebih dari 0. Coba lagi.");
              } else {
                  return nilai;
              }
          } 
       }
       
        private static LocalDate bacaTanggal(String prompt){
            while (true) {
                System.out.println(prompt);
                String teks = input.nextLine().trim();
                try{
                    return LocalDate.parse(teks);
                } catch (DateTimeParseException e){
                    System.out.println("Format tanggal salah. Gunakan YYYY-MM-DD,"
                            +"contoh 2026-10-12.");
                }
            }
        }
}