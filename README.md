# Latihan PBO - Sistem Pemesanan Tiket
Program pemesanan tiket berbasis Java yang dibuat sebagai tugas
mata kuliah Pemrograman Berorientasi Objek (PBO).
## Fitur
- Memesan tiket
- Menampilkan sisa kursi
- Validasi jumlah tiket menggunakan custom exception
## Konsep PBO yang Digunakan
- **Class dan Object** — ...
- **Encapsulation** — atribut private dengan getter/setter di class ...
- **Inheritance** — `KursiTidakTersediaException` mewarisi `Exception`
- **Exception Handling** — try-catch saat jumlah tiket melebihi kursi
## Struktur Class
| Class | Keterangan |
|---|---|
| `KursiTidakTersediaException` | Exception ketika jumlah tiket yang diminta 
melebihi kursi tersedia |
| `...` | ... |
| `Main` | Titik awal program |

## Cara Menjalankan
1. Clone repository:
git clone <link-repository>
2. Buka proyek menggunakan NetBeans
3. Klik kanan proyek, pilih Run (atau jalankan `Main.java`)

## Contoh Output
Masukkan jumlah tiket: 2
Pemesanan berhasil. Sisa kursi: 8
Masukkan jumlah tiket: 20
Error: Kursi tidak tersedia, sisa kursi hanya 8

## Identitas
- Nama  : Putu Agus Saputra
- NIM   : 2501010063
- Kelas : SI MLM 1
