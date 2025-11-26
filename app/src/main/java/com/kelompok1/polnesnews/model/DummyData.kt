package com.kelompok1.polnesnews.model

import com.kelompok1.polnesnews.R
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

/**
 * Objek singleton untuk menyediakan data palsu (dummy)
 * untuk kebutuhan preview, testing, dan pengembangan awal.
 */
object DummyData {

    /**
     * Helper internal untuk mengubah format tanggal string (yyyy-MM-dd)
     * menjadi format tampilan yang lebih mudah dibaca (Contoh: Tuesday, 04 November 2025).
     */
    fun formatDate(dateString: String): String {
        try {
            val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH)
            val outputFormatter = DateTimeFormatter.ofPattern("EEEE, dd MMMM yyyy", Locale.ENGLISH)

            val date = LocalDate.parse(dateString, inputFormatter)
            return date.format(outputFormatter)
        } catch (e: Exception) {
            return dateString
        }
    }

    // PENTING: Gunakan mutableListOf agar bisa ditambah/edit/hapus saat runtime
    val userList = mutableListOf(
        User(1, "Ade Darmawan", "ade@polnesnews.com", "password123", UserRole.EDITOR),
        User(2, "Editor B", "editor_b@polnesnews.com", "password123", UserRole.EDITOR),
        User(3, "John Doe", "johndoe@polnesnews.com", "password123", UserRole.USER),
        User(4, "test", "test@t.com", "test", UserRole.USER),
        User(5, "e", "e", "e", UserRole.EDITOR),
        User(6, "user", "user@polnesnews.com", "password123", UserRole.USER),
        User(7, "admin", "admin@polnesnews.com", "password123", UserRole.ADMIN)
    )

    // Kategori biasanya tetap, jadi listOf tidak masalah, tapi mutable juga boleh
    val categoryList = listOf(
        Category(1, "Kesehatan", R.drawable.category_health),
        Category(2, "Pendidikan", R.drawable.category_education),
        Category(3, "Pekerjaan", R.drawable.category_jobs),
        Category(4, "Prestasi", R.drawable.category_achievement),
        Category(5, "Acara", R.drawable.category_events)
    )

    // PENTING: Gunakan mutableListOf agar berita bisa dihapus/diedit
    val newsList = mutableListOf(
        // --- KATEGORI 1: KESEHATAN ---
        News(
            id = 1,
            title = "Golongan Darah Langka di Dunia",
            categoryId = 1,
            imageRes = R.drawable.sample_news1,
            authorId = 1,
            date = "2025-11-09",
            views = 4,
            youtubeVideoId = "dQw4w9WgXcQ",
            status = NewsStatus.PUBLISHED,
            content = """
            <p>Golongan darah A, B, AB, dan O mungkin menjadi jenis yang paling umum dikenal. Namun, ada beberapa golongan darah lain yang tergolong langka sehingga banyak orang mungkin belum pernah mendengarnya.</p>
            <p>Golongan darah langka memiliki peran penting dalam dunia medis, terutama ketika dibutuhkan transfusi dengan tingkat kecocokan yang sangat tinggi. Karena jumlah pemiliknya sangat terbatas, menemukan donor yang sesuai sering kali menjadi tantangan besar.</p>
            <h2>Berikut golongan darah langka yang pernah tercatat:</h2>
            <h3>1. Golongan Darah Fenotip Bombay (Bombay Blood / Oh)</h3>
            <p>Pertama adalah golongan darah Bombay atau Oh. Golongan darah ini pertama kali ditemukan di Mumbai, India dan paling banyak ditemukan pada orang etnis India. Frekuensi global untuk pemilik golongan darah Bombay kurang dari 1:1.000 orang di seluruh dunia.</p>
            <p>Dikutip dari Lifeblood, angkanya bervariasi untuk setiap populasi, sekitar 1:10.000 di Mumbai dan 1:1.000.000 di Eropa.</p>
            <p>Keunikan fenotipe Bombay adalah ketiadaan antigen H, A, dan B. Meskipun terlihat seperti golongan O dalam pemeriksaan ABO konvensional, individu dengan fenotipe Bombay memiliki antibodi alami IgM anti-H dalam plasma mereka. Antibodi ini bereaksi dengan semua sel darah merah golongan A, B, AB, maupun O normal.</p>
            <p>Jika menerima transfusi darah non-Bombay, penerima dapat mengalami reaksi hemolitik (kerusakan sel darah merah) berat dan hemolisis intravaskular (penghancuran sel darah merah dalam pembuluh darah). Karena itu, orang dengan fenotipe Bombay hanya bisa menerima darah dari donor dengan tipe langka yang sama.</p>
            <h3>Golongan 'Darah Emas' atau Golden Blood</h3>
            <p>Ini adalah golongan darah paling langka di dunia. Menurut laporan, golden blood atau Rh null, hanya dimiliki oleh 43 orang di seluruh dunia. Golongan darah ini diberi nama golden blood karena kelangkaannya yang luar biasa.</p>
            <p>Dikutip dari Cleveland Clinic, golden blood terjadi karena mutasi genetik yang sangat jarang.</p>
            <p>Dalam pemeriksaan golongan darah, laboratorium tidak hanya melihat golongan ABO, juga memeriksa faktor Rh (rhesus factor) dalam darah. Secara khusus, tes ini melihat adanya antigen yang disebut RhD.</p>
            <p>Jika seseorang punya antigen RhD, golongan darahnya disebut Rh positif, dan jika tidak punya disebut Rh negatif. Namun, RhD hanyalah satu dari lebih dari 50 jenis antigen dalam sistem Rh. Pada orang dengan golden blood, semua antigen Rh, tidak ada sama sekali.</p>
            <p>Seseorang dengan golden blood disebut sebagai donor universal, khususnya bagi orang dengan Rh langka. Namun sebaliknya, orang dengan Rh null hanya bisa menerima transfusi darah dengan golongan darah yang sama, sehingga ini begitu menyulitkan.</p>
        """.trimIndent()
        ),
        News(
            id = 2,
            title = "Indonesia Umumkan Kejadian Luar Biasa (KLB) Polio Berakhir",
            categoryId = 1,
            imageRes = R.drawable.sample_news2,
            authorId = 1,
            date = "2025-11-08",
            views = 12,
            youtubeVideoId = null,
            status = NewsStatus.PUBLISHED,
            content = """
            <p>Indonesia secara resmi telah mengakhiri Kejadian Luar Biasa (KLB) polio tipe 2, yang muncul akibat rendahnya cakupan imunisasi polio selama beberapa tahun. Hampir 60 juta dosis imunisasi polio tambahan telah diberikan kepada anak-anak selama respon KLB ini.</p>
            <p>Sejak Juni 2024 hingga saat ini, tidak ditemukan lagi virus polio pada anak-anak maupun lingkungan. Berdasarkan situasi ini, Badan Kesehatan Dunia (WHO) menyatakan KLB ditutup secara resmi pada 19 November 2025.</p>
            <p>“Kita berhasil menghentikan penyebaran polio di Indonesia berkat dedikasi tenaga kesehatan, komitmen orang tua dan seluruh anggota masyarakat agar anak-anak diimunisasi, serta dukungan mitra. Setiap anak berhak mendapatkan perlindungan. Kita harus terus bekerja sama agar polio tidak kembali dengan memastikan semua anak menerima imunisasi polio lengkap sesuai usia,” kata Menteri Kesehatan Budi Gunadi Sadikin, di Jakarta, Jumat (21/11).</p>
            <p>“Namun, kita tidak boleh berpuas diri. Risiko polio masih ada, terutama dengan adanya kesenjangan cakupan imunisasi di beberapa provinsi di Indonesia,” tambah Budi.</p>
            <p>Dr. Saia Ma'u Piukala, Direktur Regional WHO untuk Pasifik Barat mengatakan keberhasilan Indonesia merupakan langkah penting menuju dunia tanpa polio. Keberhasilan ini juga memperkuat kemampuan seluruh Wilayah Pasifik Barat WHO untuk mempertahankan status bebas polio yang telah dicapai 25 tahun lalu.</p>
            <p>"Saya mendorong seluruh 38 negara dan wilayah di Pasifik Barat untuk tetap waspada. Suatu hari nanti, polio hanya tinggal sejarah. Sampai saat itu tiba, kita harus melanjutkan imunisasi," ucapnya.</p>
            <p>KLB terjadi sejak bulan Oktober 2022, saat kasus pertama dilaporkan dari Aceh. Dalam dua tahun berikutnya, kasus juga ditemukan di provinsi Banten, Jawa Barat, Jawa Tengah, Jawa Timur, Maluku Utara, Papua Tengah, Papua Pegunungan, dan Papua Selatan. Kasus cVDPV2 (varian virus polio) terakhir terkonfirmasi di Papua Selatan pada 27 Juni 2024.</p>
            <p>Indonesia melakukan respons melalui dua putaran imunisasi tambahan polio dengan menggunakan vaksin novel OPV-2 (nOPV2) mulai akhir tahun 2022 hingga triwulan ketiga 2024. Secara paralel, cakupan imunisasi rutin juga meningkat, dengan persentase anak yang menerima dosis kedua vaksin polio inaktif (IPV) meningkat dari 63% (1,9 juta anak) pada 2023 menjadi 73% (3,2 juta anak) pada 2024.</p>
            <p>Dalam upaya mengakselerasi peningkatan cakupan IPV, Kementerian Kesehatan menginisiasi penggunaan vaksin heksavalen yang menggabungkan DPT-HB-Hib dan IPV dalam satu suntikan. Vaksin ini memberikan perlindungan terhadap enam penyakit sekaligus, yakni polio, difteri, pertusis, tetanus, hepatitis B, serta pneumonia dan meningitis akibat infeksi Haemophilus influenza tipe b.</p>
            <p>Penggunaan vaksin heksavalen diharapkan jumlah suntikan yang diterima anak, menghemat waktu dan biaya keluarga, serta mempercepat terbentuknya kekebalan terhadap berbagai penyakit. Program ini dimulai pada Oktober 2025 di provinsi DIY, NTB, Bali, serta enam provinsi di Tanah Papua, dengan pelaksanaan secara nasional direncanakan pada tahun mendatang.</p>
            <p>Indonesia juga mencatat kemajuan signifikan dalam deteksi dan investigasi lumpuh layuh akut atau Acute Flaccid Paralysis (AFP) pada anak-anak. Kualitas surveilans AFP semakin baik melalui deteksi kasus lebih sensitif dan peningkatan kualitas spesimen.</p>
            <p>Sesuai protokol Global Polio Eradication Initiative, tim independen global menilai kualitas respons KLB polio melalui Outbreak Response Assessment (OBRA) pada Juli 2023, Desember 2024, dan Juni 2025. Berdasarkan penilaian ini, disimpulkan Indonesia telah melaksanakan upaya respon yang berkualitas, melakukan serangkaian upaya penguatan dan peningkatan pelaksanaan program sebagaimana direkomendasikan tim OBRA, serta membuktikan tidak adanya kasus baru. Dengan demikian, WHO menyatakan Indonesia telah memenuhi kriteria berakhirnya KLB, sehingga status KLB Polio dapat ditutup.</p>
            <p>Pencapaian ini terwujud melalui kolaborasi antara Pemerintah Indonesia, baik pusat maupun daerah, dengan seluruh mitra pembangunan internasional seperti WHO, UNICEF, United Nations Development Programme (UNDP), Clinton Health Access Initiative (CHAI), dan Rotary International. Pencapaian ini pun terwujud berkat dedikasi para tenaga kesehatan dan masyarakat di seluruh Indonesia.</p>
            <p>Perwakilan UNICEF Indonesia Maniza Zaman, mengatakan ini menunjukkan hal yang bisa kita capai ketika masyarakat, tenaga kesehatan, dan mitra bersatu.</p>
            <p>"Kita harus terus menjaga momentum agar setiap anak mendapatkan imunisasi yang mereka butuhkan untuk tumbuh sehat dan bebas dari polio serta penyakit lainnya yang dapat dicegah dengan imunisasi,” ungkapnya.</p>
            <p>Dengan berakhirnya status KLB Polio ini, Kementerian Kesehatan menegaskan komitmennya untuk menjaga Indonesia tetap bebas polio melalui penguatan imunisasi rutin, peningkatan surveilans, kerja sama lintas sektor, dan dukungan masyarakat.</p>
        """.trimIndent()
        ),
        News(
            id = 3,
            title = "Uji Klinis Fase 1 Vaksin TBC Inhalasi",
            categoryId = 1,
            imageRes = R.drawable.sample_news3,
            authorId = 1,
            date = "2025-11-13",
            views = 10,
            youtubeVideoId = null,
            status = NewsStatus.PUBLISHED,
            content = """
            <p>Pemerintah Indonesia resmi memulai uji klinis fase 1 vaksin TBC berbasis inhalasi pertama di dunia. Wakil Menteri Kesehatan RI dr. Benjamin Paulus Octavianus (dr. Benny) menyebut langkah ini sebagai bagian dari program nasional pemberantasan TBC yang menjadi prioritas Presiden Prabowo Subianto.</p>
            <p>“Pemberantasan TBC adalah program hasil terbaik cepat dari Presiden Prabowo yang harus segera direalisasikan,” ujar dr. Benny saat meninjau pelaksanaan uji klinis di RS Islam Jakarta, Kamis (13/11).</p>
            <p>Berbeda dengan vaksin pada umumnya yang diberikan melalui suntikan, vaksin TBC inhalasi diberikan dalam bentuk uap halus yang dihirup oleh pasien. Penelitian ini dipimpin oleh Prof. Erlina Burhan dan melibatkan berbagai institusi, di antaranya RS Persahabatan, RS Islam Cempaka Putih, Etana, serta CanSino Incorporation dari Tiongkok.</p>
            <p>Prof. Erlina menjelaskan, uji klinis ini telah melewati proses panjang sebelum memasuki tahap fase 1. Setelah mendapatkan persetujuan etik dari Komite Etik RS Persahabatan (April), Komite Etik RS Islam Cempaka Putih (Juli), dan izin Badan POM (Mei 2025), uji klinis akhirnya resmi dimulai.</p>
            <p>“Tujuan utamanya adalah mengevaluasi keamanan dan kemampuan imunogenisitas vaksin pada individu dewasa sehat berusia 18–49 tahun,” ujar Prof. Erlina.</p>
            <p>Sebanyak 36 sukarelawan akan berpartisipasi, terbagi dalam dua kelompok dosis berbeda. Rekrutmen dilakukan di RS Islam Cempaka Putih, sementara tindakan medis lanjutan seperti Bronchoalveolar Lavage Fluid (BALF) dilaksanakan di RS Persahabatan yang memiliki fasilitas bronkoskopi.</p>
            <p>Menariknya, metode inhalasi memungkinkan vaksin masuk langsung ke sistem pernapasan dan menstimulasi kekebalan lokal di paru-paru. Para partisipan akan menjalani pemantauan lanjutan pada hari ke-28, ke-90, dan ke-180 untuk memastikan respons imun dan keamanan vaksin.</p>
            <p>“Vaksin TBC inhalasi ini akan menjadi terobosan besar dalam upaya pemberantasan tuberkulosis di Indonesia dan dunia,” kata Prof. Erlina.</p>
            <p>dr. Benny menambahkan, kebutuhan anggaran untuk program pemberantasan TBC secara nasional diperkirakan mencapai Rp10–20 triliun, termasuk dukungan sosial bagi pasien dari keluarga miskin.</p>
            <p>“Karena kami bukan hanya mengobati pasien TBC, tapi nanti rumah pasien TBC yang miskin akan dibantu renovasi. Pemberian makanan bergizi dari Kementerian Sosial dan Kementerian Tenaga Kerja juga akan dilibatkan,” jelasnya.</p>
            <p>Pemerintah menargetkan penurunan kasus TBC dari 380 menjadi 65 kasus per 100 ribu penduduk, agar Indonesia sejajar dengan negara maju dalam pengendalian TBC.</p>
            <p>Kepala Badan Pengawas Obat dan Makanan (BPOM) Taruna Ikrar menyampaikan dukungan penuh terhadap pengembangan vaksin ini.</p>
            <p>“Bukti dukungan kami berupa persetujuan pelaksanaan uji klinis fase 1 yang sudah kami keluarkan dan sampaikan,” jelas Taruna.</p>
            <p>Ia menambahkan, setelah fase 1 dinyatakan aman, BPOM akan memproses izin untuk fase 2 dan 3 guna menentukan dosis serta efikasi vaksin.</p>
            <p>“Saya yakin, berdasarkan insting saya sebagai ahli farmakologi, insya Allah ini sukses,” tegasnya.</p>
            <p>Pemerintah berharap, dengan percepatan uji klinis vaksin TBC inhalasi, Indonesia dapat menekan kasus TBC secara signifikan dan mencapai target bebas TBC pada 2030.</p>
        """.trimIndent()
        ),

        // --- KATEGORI 2: PENDIDIKAN ---
        News(
            id = 4,
            title = "Perayaan Hari Guru Nasional",
            categoryId = 2,
            imageRes = R.drawable.sample_news4,
            authorId = 1,
            date = "2025-11-25",
            views = 55,
            youtubeVideoId = null,
            status = NewsStatus.PUBLISHED,
            content = """
            <p>Perayaan Hari Guru Nasional (HGN) berlangsung khidmat di kompleks Sekolah Terpadu SD 028, SMP 16, dan SMA Prestasi di Kelurahan Loa Bakung, Kecamatan Sungai Kunjang, Senin (25/11).</p>
            <p>Mengusung tema “Guru Hebat, Indonesia Hebat”, upacara tahun ini menyoroti isu besar mengenai peningkatan kesejahteraan pendidik demi tercapainya layanan pendidikan yang merata.</p>
            <p>Di Samarinda, perwakilan Kementerian Pendidikan Dasar dan Menengah (Dikdasmen) RI, Prof Dr H Biyant, hadir sebagai pembina upacara. Staf Ahli Bidang Regulasi dan Hubungan Antar Lembaga tersebut membacakan amanat Menteri Dikdasmen, Abdul Mu’ti.</p>
            <p>Dalam pidatonya, pemerintah menegaskan bahwa selama satu tahun kepemimpinan Presiden Prabowo Subianto, berbagai langkah konkret telah ditempuh untuk memperkuat kualifikasi dan kompetensi guru. Pada 2025, misalnya, disiapkan beasiswa Rp 3 juta per semester bagi 12.500 guru yang belum bergelar S1 melalui program Rekognisi Pembelajaran Lampau.</p>
            <p>Pelatihan juga digencarkan, mulai dari pendidikan profesi guru, peningkatan kompetensi konselor, deep learning, koding dan kecerdasan artifisial, hingga kepemimpinan sekolah. Pemerintah memberikan tunjangan sertifikasi sebesar Rp 2 juta per bulan bagi guru non-ASN dan satu kali gaji pokok bagi guru ASN, sementara guru honorer mendapatkan insentif Rp 300 ribu per bulan. Seluruhnya ditransfer langsung ke rekening penerima.</p>
            <p>Namun pemerintah mengakui berbagai dukungan bagi guru belum sepenuhnya ideal. Karena itu, peningkatan akan dilanjutkan pada 2026. Kuota beasiswa diperluas menjadi 150 ribu guru. Insentif guru honorer naik menjadi Rp 400 ribu per bulan. Tugas administrasi dipangkas, kewajiban mengajar tidak lagi mutlak 24 jam per pekan, dan ditetapkan satu hari khusus belajar bagi guru.</p>
            <p>“Kebijakan ini agar guru bisa lebih fokus pada tugas utamanya sebagai pendidik profesional,” kata Prof Biyanto.</p>
            <p>Ia juga menekankan pentingnya pemerataan layanan pendidikan, semua anak bangsa harus punya peluang yang sama. Termasuk wilayah daerah 3T (tertinggal, terdepan dan terluar) yang sering kekurangan guru berkualitas. “Kata kuncinya ada pada penguatan tugas,” tegasnya.</p>
            <p>Kepala Balai Guru dan Tenaga Kependidikan (BGTK) Kaltim, Wiwik Setiawati menambahkan bahwa pihaknya terus memperkuat pemetaan kompetensi guru sebagai dasar penyusunan pelatihan. Bahkan telah memiliki peta kompetensi guru di Samarinda maupun Kaltim.</p>
            <p>“Aspirasi guru juga kami tampung. Apa pelatihan yang mereka butuhkan, kami usahakan untuk dipenuhi,” ujarnya.</p>
            <p>Wiwik menambahkan bahwa momentum HGN tahun ini menjadi pengingat pentingnya peningkatan kualitas guru. Ia berharap para pendidik terus berinovasi dan menjaga semangat mendidik agar tidak pernah padam dalam menginspirasi anak bangsa.</p>
            <p>“Guru adalah ujung tombak pembangunan SDM. Tuntutan terhadap guru di tengah gempuran teknologi juga tidak mudah,” singkatnya.</p>
            <p>Dia menekankan bahwa guru saat ini harus mengajar anak-anak yang hidup di era teknologi yang luar biasa cepat berubah. Karena itu, guru dituntut mampu beradaptasi. “Menyesuaikan metode pembelajaran dengan kebutuhan siswa masa kini,” pungkasnya.</p>
        """.trimIndent()
        ),
        News(
            id = 5,
            title = "Momentum Hari Guru Nasional",
            categoryId = 2,
            imageRes = R.drawable.sample_news5,
            authorId = 1,
            date = "2025-11-25",
            views = 32,
            youtubeVideoId = null,
            status = NewsStatus.PUBLISHED,
            content = """
            <p>Momentum Hari Guru Nasional 25 November tidak sekadar seremonial bagi para pendidik di Kota Samarinda. Bagi Dyah Ayu Wijaya, guru Bimbingan Konseling SMPN 8 Samarinda sekaligus pengurus ABKIN dan MGBK Kota Samarinda, peringatan tahun ini harus menjadi titik balik transformasi pendidikan menuju Indonesia Emas 2045.</p>
            <p>Menurut Dyah, pendidikan merupakan fondasi utama kemajuan bangsa. Ia menegaskan, pendidikan tidak hanya berkutat pada pengetahuan akademik, tetapi pembentukan karakter, etika, kecerdasan emosional, hingga kemampuan beradaptasi. “Tanpa dasar pendidikan yang kuat, sebuah bangsa akan kesulitan berinovasi dan bersaing di kancah global,” ujarnya.</p>
            <p>Ia menilai posisi Samarinda semakin strategis karena menjadi penyangga Ibu Kota Nusantara (IKN). Kota ini disebut tengah bertransformasi sebagai pusat peradaban baru, sehingga kebutuhan sumber daya manusia (SDM) unggul menjadi semakin mendesak. “Pendidik di Samarinda dituntut menghasilkan lulusan yang kritis, kreatif, kolaboratif, komunikatif, dan tetap berpegang pada nilai-nilai Pancasila,” terang Dyah.</p>
            <p>Guru, kata dia, tak sekadar pengajar, tetapi agen perubahan yang menumbuhkan motivasi, keteladanan, inovasi, dan semangat kewirausahaan. Mereka juga berperan menjaga kearifan lokal agar modernisasi tidak mengikis identitas budaya. “Bangunan peradaban hanya akan berdiri kokoh bila gurunya kuat,” tambahnya.</p>
            <p>Terkait digitalisasi pendidikan, Dyah menekankan bahwa teknologi harus menjadi perangkat pendukung, bukan tujuan akhir. Pemanfaatan platform digital, e-book, hingga analisis data dinilai mampu memperkuat penerapan Merdeka Belajar dan pembelajaran berbasis diferensiasi.</p>
            <p>“Transformasi digital mendorong siswa menjadi subjek pembelajaran yang aktif, bukan sekadar penerima materi,” tuturnya.</p>
            <p>Meski begitu, ia mengingatkan agar esensi pendidikan tidak hilang. Interaksi tatap muka dianggap tetap penting untuk membangun karakter, empati, dan keterampilan sosial. “Teknologi tidak dapat menggantikan sentuhan manusiawi antara guru dan peserta didik,” katanya.</p>
        """.trimIndent()
        ),
        News(
            id = 6,
            title = "Transformasi Digital Pendidikan",
            categoryId = 2,
            imageRes = R.drawable.sample_news6,
            authorId = 1,
            date = "2025-11-17",
            views = 88,
            youtubeVideoId = null,
            status = NewsStatus.PUBLISHED,
            content = """
            <p>Transformasi digital pendidikan kini benar-benar terasa di ruang-ruang belajar. Melalui Program Digitalisasi Pembelajaran, lebih dari 173 ribu sekolah telah menerima dan memanfaatkan Panel Interaktif Digital (Interactive Flat Panel/IFP) sebagai media belajar. Tidak hanya memperluas akses pembelajaran, teknologi ini terbukti mengubah cara guru mengajar dan cara siswa memahami materi: lebih cepat, lebih interaktif, dan jauh lebih menyenangkan.</p>
            <p>Program Digitalisasi Pembelajaran, yang menyasar 288.865 sekolah di seluruh Indonesia, menjadi salah satu langkah strategis pemerintah untuk memastikan setiap anak memiliki kesempatan belajar yang setara. Presiden Republik Indonesia, Prabowo Subianto, menyoroti bahwa program ini membawa manfaat besar bagi ekosistem pendidikan.</p>
            <p>“Kita harus benar-benar memberi pendidikan yang terbaik untuk anak-anak kita di seluruh Indonesia, tidak terkecuali. Tidak boleh ada bagian dari Indonesia yang tertinggal kualitas pendidikannya, harus sama baiknya, dan salah satu cara kita adalah menggunakan lompatan teknologi digitalisasi,” ujar Presiden saat meluncurkan Program Digitalisasi Pembelajaran untuk Indonesia Cerdas di SMP Negeri 4 Bekasi, Senin (17/11).</p>
            <p>Menteri Pendidikan Dasar dan Menengah (Mendikdasmen), Abdul Mu’ti, menambahkan bahwa hasil monitoring dan evaluasi Kementerian Pendidikan Dasar dan Menengah (Kemendikdasmen) menunjukkan peningkatan yang nyata di sekolah-sekolah penerima Panel Interaktif Digital.</p>
            <p>“Mereka belajar dengan gembira, penuh semangat, dan capaian pembelajaran terus meningkat. Ini bukti revolusi pendidikan yang diletakkan Bapak Presiden melalui digitalisasi pembelajaran dan pembagian Panel Digital Interaktif,” ungkapnya.</p>
            <p>Indarwati Komariah, guru SMP Negeri 4 Bekasi, membagikan pengalamannya menggunakan Papan Interaktif Digital dalam kegiatan belajar mengajar. Ia menyebutkan bahwa antusiasme siswa meningkat drastis sejak Panel Interaktif Digital dipakai dalam kelas. Bahkan siswa yang biasanya pasif, kini ikut berpartisipasi—maju ke depan, menulis, mencoba fitur. Menurutnya, pembelajaran menjadi lebih menggembirakan dan bermakna.</p>
            <p>Indarwati menambahkan bahwa fitur Ruang Murid sangat membantu proses belajar. “Anak-anak bisa mengeksplorasi, melakukan percobaan, bermain gim edukasi, dan berinteraksi dengan banyak sumber belajar digital,” katanya.</p>
            <p>Tidak hanya guru, para siswa pun merasakan langsung manfaat pembelajaran digital. Naufal Rakha Mahardika, siswa kelas IX SMP Negeri 4 Bekasi, mengaku lebih cepat memahami pembelajaran sejak IFP digunakan, terutama untuk pelajaran matematika dan IPA.</p>
            <p>“Di sana kami bisa mengeksplorasi banyak pembelajaran dengan mudah,” ucapnya.</p>
            <p>Senada dengan itu, siswa kelas IX SMP Negeri 4 Bekasi lainnya, Nafeza Ayasha Umbara, mengaku sangat senang dengan penggunaan Papan Interaktif Digital yang menghadirkan banyak fitur dan eksperimen, sehingga pembelajaran terasa seperti pengalaman baru setiap harinya.</p>
            <p>Nafeza juga menyampaikan apresiasinya. “Terima kasih Bapak Presiden atas fasilitas yang diberikan. Semoga seluruh Indonesia bisa merasakan manfaat PID dan menggunakannya dengan bijak.”</p>
            <p>Pengalaman para guru dan siswa ini menunjukkan bahwa digitalisasi pembelajaran bukan sekadar penyediaan perangkat, tetapi perubahan budaya belajar. Dengan hadirnya Panel Interaktif Digital di sekolah-sekolah, anak-anak Indonesia kini memiliki pengalaman belajar yang lebih setara, kreatif, dan menyenangkan, sebuah langkah penting menuju masa depan pendidikan yang lebih maju dan inklusif.</p>
        """.trimIndent()
        ),

        // --- KATEGORI 3: PEKERJAAN ---
        News(
            id = 7,
            title = "Polnes Gelar Job Fair 2025",
            categoryId = 3,
            imageRes = R.drawable.sample_news7,
            authorId = 1,
            date = "2025-11-21",
            views = 120,
            youtubeVideoId = null,
            status = NewsStatus.PUBLISHED,
            content = """
            <p><b>Politeknik Negeri Samarinda (Polnes) sukses menggelar Job Fair 2025 di area kampus, berpusat di Jurusan Kemaritiman. Kegiatan berlangsung selama dua hari, Kamis (20/11) hingga Jumat (21/11).</b></p>
            <p><b>“Kegiatan ini bertujuan memfasilitasi hubungan erat antara mahasiswa, lulusan, dan perusahaan mitra. Acara selama dua hari ini diharapkan menjadi ajang efektif untuk menciptakan kesempatan kerja dan magang bagi komunitas akademik Polnes,” ujar Wakil Direktur IV Polnes, Said Keliwar, S.ST.Par., M.Sc, selaku penanggung jawab kegiatan.</b></p>
            <p>Job Fair 2025 menghadirkan lebih dari 29 perusahaan dari berbagai sektor strategis, seperti teknologi, konstruksi, energi, jasa keuangan, hingga layanan publik. Total lebih dari 300 lowongan kerja ditawarkan pada kegiatan ini.</p>
            <p>Said menambahkan bahwa kegiatan ini merupakan agenda tahunan dengan tema <strong>“Bridging Talent and Opportunity”</strong>. Job fair ini dirancang untuk mempertemukan pencari kerja dengan perusahaan, membuka akses bagi alumni Polnes dan masyarakat umum.</p>
            <p>Ia menegaskan bahwa Polnes berkomitmen tidak hanya pada proses pembelajaran, tetapi juga keberlanjutan karier lulusan. Kegiatan ini sekaligus memperkuat kolaborasi triple helix antara pemerintah, industri, dan kampus.</p>
            <p>“Semoga kegiatan ini menjadi wadah bagi lulusan untuk menemukan pekerjaan yang sesuai, membangun kemitraan berkelanjutan dengan perusahaan, serta memperluas jejaring dan reputasi Polnes sebagai perguruan tinggi vokasi yang unggul dan berdaya saing,” harapnya.</p>
            <h2>Dukungan Pemerintah Provinsi dan Kota</h2>
            <p>Mewakili Kepala Disnakertrans Kaltim, Muhammad Abduh selaku Kepala Bidang Pengembangan Tenaga Kerja menyampaikan bahwa job fair ini menjadi fasilitas penting bagi perusahaan dan pencari kerja.</p>
            <p>“Kegiatan ini memfasilitasi perusahaan dan pencari kerja. Kita berharap dapat menurunkan tingkat pengangguran terbuka di Kaltim,” ujarnya dalam sambutan.</p>
            <p>Ia menekankan bahwa Polnes memiliki peran besar dalam menempatkan pencari kerja sesuai kompetensi, khususnya bagi alumni. “Jangan hanya mencari kerja, tapi temukan karier yang bermakna bagi diri sendiri dan perusahaan,” tegasnya.</p>
            <p>Kepala Disnaker Kota Samarinda, Yuyum Puspitaningrum, juga menilai kegiatan ini sangat strategis untuk para pencari kerja, terutama lulusan baru. Melalui job fair ini, mereka dapat belajar langsung mengenai dunia kerja dan peluang yang tersedia.</p>
            <p>“Kami berharap minimal 50 persen dari total lowongan dapat terisi melalui job fair ini. Pemerintah sudah membuka akses—tinggal kemauan"</p>
        """.trimIndent()
        ),
        News(
            id = 8,
            title = "Pergeseran Pasar Tenaga Kerja Menurut WEF",
            categoryId = 3,
            imageRes = R.drawable.sample_news8,
            authorId = 1,
            date = "2025-11-20",
            views = 200,
            youtubeVideoId = null,
            status = NewsStatus.PUBLISHED,
            content = """
            <p>Laporan World Economic Forum (WEF) menyoroti adanya pergeseran pasar tenaga kerja yang diproyeksikan hingga tahun 2030. Sejumlah pekerjaan dinilai semakin potensial untuk dijadikan karier, termasuk pada tahun 2026 mendatang.</p>
            <p>Menurut proyeksi WEF, sekitar <strong>92 juta pekerjaan</strong> akan tergantikan pada 2030, sementara <strong>170 juta pekerjaan baru</strong> diperkirakan tercipta. Pergeseran ini banyak dipengaruhi oleh keberadaan kecerdasan buatan atau artificial intelligence (AI).</p>
            <p>Bloomberg Intelligence melaporkan bahwa bank-bank global diprediksi akan memangkas hingga <strong>200 ribu pekerjaan</strong> dalam tiga sampai lima tahun ke depan akibat meningkatnya penggunaan AI untuk pekerjaan dasar.</p>
            <p>Meski demikian, beberapa sektor tetap menunjukkan potensi pertumbuhan. Berikut peningkatan sektor pekerjaan dalam lima tahun menurut WEF:</p>
            <h2>8 Pekerjaan yang Diproyeksikan Mengalami Peningkatan</h2>
            <ol>
               <li>Pengembang perangkat lunak dan aplikasi (57%)</li>
               <li>Spesialis manajemen keamanan (53%)</li>
               <li>Spesialis pergudangan data (49%)</li>
               <li>Spesialis kendaraan otonom dan listrik (48%)</li>
               <li>Desainer UI dan UX (48%)</li>
               <li>Pengemudi truk ringan atau layanan pengiriman (46%)</li>
               <li>Spesialis Internet of Things (42%)</li>
               <li>Analis data dan ilmuwan (41%)</li>
            </ol>
            <p>Di sisi lain, sektor teknologi juga menghadapi pengurangan tenaga kerja. Menurut Forbes, CEO Meta Mark Zuckerberg mengumumkan rencana pemutusan hubungan kerja sekitar 5% staf. Ia juga mencari cara menggantikan teknisi tingkat menengah dengan AI karena teknologi tersebut semakin mampu melakukan analisis data yang kompleks.</p>
            <h2>Sektor Pekerjaan yang Diproyeksikan Bertumbuh Pesat hingga 2030</h2>
            <h3>Keamanan Siber</h3>
            <p>Kebutuhan akan keamanan siber meningkat seiring dengan perkembangan teknologi. Perusahaan perlu melindungi informasi sensitif dan menjaga sistem tetap aman dari ancaman.</p>
            <h3>Manajemen Data</h3>
            <p>Data menjadi aset penting perusahaan dalam dekade terakhir. Keahlian dalam merancang, mengelola, dan memanfaatkan data sangat dibutuhkan untuk mendukung pengambilan keputusan dan inovasi.</p>
            <h3>Energi Terbarukan</h3>
            <p>Peningkatan perhatian global terhadap isu iklim mendorong pertumbuhan sektor energi terbarukan. Teknologi surya, angin, dan inovasi hijau lainnya semakin berkembang seiring investasi negara-negara dalam mengurangi jejak karbon.</p>
            <h3>Layanan Kesehatan dan Bioteknologi</h3>
            <p>Kesadaran masyarakat akan kesehatan terus meningkat, ditambah dengan kemajuan teknologi medis. Posisi dalam bidang telemedis, pengobatan personal, dan riset bioteknologi semakin diminati.</p>
            <h2>Pekerjaan yang Diproyeksikan Terus Mengalami Penurunan</h2>
            <p>WEF juga melaporkan sejumlah pekerjaan yang semakin tergerus oleh otomasi dan teknologi AI:</p>
            <ol>
               <li>Pekerja layanan pos (-34%)</li>
               <li>Teller bank dan peran serupa (-31%)</li>
               <li>Petugas entri data (-26%)</li>
               <li>Asisten administrasi dan sekretaris eksekutif (-20%)</li>
               <li>Pekerja percetakan dan perdagangan terkait (-20%)</li>
               <li>Petugas akuntansi, pembukuan, dan penggajian (-18%)</li>
            </ol>
        """.trimIndent()
        ),
        News(
            id = 9,
            title = "Kemnaker: 10,7 Juta Warga Indonesia Mencari Kerja Setiap Tahun",
            categoryId = 3,
            imageRes = R.drawable.sample_news9,
            authorId = 1,
            date = "2025-09-26",
            views = 95,
            youtubeVideoId = null,
            status = NewsStatus.PUBLISHED,
            content = """
            <p>Kementerian Ketenagakerjaan (Kemnaker) mencatat bahwa setidaknya <strong>10,7 juta orang Indonesia</strong> mencari pekerjaan setiap tahunnya.</p>
            <p>“Angka ini 10 juta (orang) di luar PHK. Ada lagi pekerja-pekerja yang ter-PHK, ada juga pekerja-pekerja yang mengundurkan diri dan mencari pekerjaan lagi. Tapi basisnya sudah 10 juta tiap tahun yang harus dibuka lowongan kerjanya,” ujar Kepala Pusat Pasar Kerja Kemnaker, Surya Lukita Warman, di Jakarta, Jumat (26/9/2025).</p>
            <p>Ia menambahkan bahwa angka tersebut belum termasuk pekerja yang mengundurkan diri dari tempat kerja serta mereka yang terdampak pemutusan hubungan kerja (PHK).</p>
            <p>“Pertumbuhan tenaga kerja di negara kita cukup besar. Tiap tahun ada 3,5 juta lulusan dari pendidikan SMK, SMA, hingga universitas yang masuk ke pasar kerja. Ini yang harus dicarikan pekerjaan,” lanjut Surya.</p>
            <p>Surya mengakui bahwa angka pengangguran di Indonesia masih cukup tinggi meski persentase 4,8 persen disebut sebagai yang terendah sejak era reformasi. Dalam angka absolut, masih terdapat <strong>7,2 juta warga Indonesia yang menganggur</strong>.</p>
            <p>“Bayangkan, 3,5 juta masuk ke pasar kerja sebagai angkatan kerja baru, sementara yang menganggur 7,2 juta. Jika diakumulasikan sudah lebih dari 10 juta. Ada 10,7 juta orang yang membutuhkan pekerjaan,” jelasnya.</p>
            <h2>Tantangan Pasar Kerja Indonesia</h2>
            <p>Selain pertumbuhan tenaga kerja baru yang besar dan angka pengangguran yang tinggi, Surya menyebut sejumlah tantangan lain yang masih membayangi pasar kerja nasional:</p>
            <ul>
               <li>Mismatch kompetensi, termasuk kurangnya soft skill meskipun kualifikasi pendidikan sesuai.</li>
               <li>Rendahnya kualitas tenaga kerja, terutama karena lulusan SMP ke bawah masih mendominasi.</li>
               <li>Perkembangan teknologi seperti digitalisasi, AI, dan Industri 4.0.</li>
               <li>Perubahan pasar kerja akibat dinamika ekonomi global dan transisi ekonomi hijau.</li>
            </ul>
            <p>Untuk menghadapi tantangan tersebut, Kemnaker terus mendorong terciptanya ekosistem pasar kerja yang lebih baik.</p>
            <h2>Upaya Kemnaker: Wajib Lapor Lowongan dan Digitalisasi Layanan</h2>
            <p>Salah satu langkah strategis adalah mendorong pemberi kerja mematuhi <strong>Peraturan Presiden Nomor 57 Tahun 2023 tentang Wajib Lapor Lowongan Pekerjaan (WLLP)</strong>.</p>
            <p>“Seluruh pemberi kerja wajib melaporkan lowongan pekerjaannya kepada Kemnaker melalui suatu sistem informasi yang kanalnya kini kami buat, yaitu <strong>KarirHub</strong>,” ujar Surya, dikutip Antara.</p>
            <p>Selain itu, Kemnaker juga menyediakan <strong>super-app SiapKerja</strong>, sebuah sistem informasi ketenagakerjaan yang menyediakan layanan online bagi pemberi kerja serta mitra ketenagakerjaan.</p>
        """.trimIndent()
        ),

        // --- KATEGORI 4: PRESTASI ---
        News(
            id = 10,
            title = "Mahasiswa POLNES Raih Bronze Medal di Ajang Internasional di Malaysia",
            categoryId = 4,
            imageRes = R.drawable.category_achievement,
            authorId = 1,
            date = "2025-10-22",
            views = 150,
            youtubeVideoId = null,
            status = NewsStatus.PUBLISHED,
            content = """
            <p><strong>Samarinda, 22 Oktober 2025</strong> – Prestasi membanggakan kembali diraih oleh mahasiswa Politeknik Negeri Samarinda (POLNES) di kancah internasional.</p>
            <p>Dua mahasiswa terbaik POLNES berhasil menorehkan prestasi dengan meraih <strong>Bronze Medal (Medali Perunggu)</strong> pada ajang <em>3rd International Youth Conference – International Student Summit & International Business Plan Competition</em> yang diselenggarakan di Malaysia pada 18–19 Oktober 2025.</p>
            <h2>Mahasiswa Peraih Medali</h2>
            <ul>
               <li><strong>Juan Emmanuel</strong> (NIM: 226431049) – Program Studi D4 Rekayasa Jalan & Jembatan</li>
               <li><strong>Shawallin Firnanda</strong> (NIM: 226131013) – Program Studi D4 Teknologi Rekayasa Konstruksi Bangunan Gedung</li>
            </ul>
            <p>Kompetisi internasional ini mempertemukan mahasiswa dari berbagai negara untuk menampilkan ide bisnis inovatif dan berkelanjutan yang menonjolkan kreativitas, analisis pasar, serta solusi berbasis teknologi. Keberhasilan tim POLNES menjadi bukti kemampuan mahasiswa vokasi Indonesia dalam bersaing secara global.</p>
            <p>Prestasi ini juga menunjukkan kualitas pendidikan vokasi POLNES yang unggul, profesional, dan memiliki dampak nyata.</p>
            <h2>Apresiasi dari Direktur POLNES</h2>
            <p>Direktur Politeknik Negeri Samarinda menyampaikan apresiasi dan kebanggaan atas pencapaian tersebut.</p>
            <blockquote>
               “Ini menjadi bukti bahwa mahasiswa POLNES tidak hanya kompeten secara teknis, tetapi juga memiliki kemampuan berpikir kritis, kreatif, dan siap bersaing di level internasional,” ujarnya.
            </blockquote>
            <p><strong>Selamat</strong> kepada Juan Emmanuel dan Shawallin Firnanda atas capaian luar biasa ini. Semoga prestasi ini menjadi inspirasi bagi seluruh sivitas akademika POLNES untuk terus berinovasi, berprestasi, dan membawa nama Indonesia ke panggung dunia.</p>
        """.trimIndent()
        ),
        News(
            id = 11,
            title = "Inovasi Polnes Tangani Limbah Laundry: Teknologi Sono-Elektrokoagulasi",
            categoryId = 4,
            imageRes = R.drawable.sample_news11,
            authorId = 1,
            date = "2025-11-01",
            views = 70,
            youtubeVideoId = null,
            status = NewsStatus.PUBLISHED,
            content = """
            <p><strong>Samarinda</strong> – Politeknik Negeri Samarinda (Polnes) kembali menunjukkan komitmennya dalam menghadirkan inovasi ramah lingkungan. Melalui kolaborasi dosen dan mahasiswa Jurusan Teknik Kimia, Polnes berhasil mengembangkan teknologi pengolahan limbah laundry bernama <strong>sono-elektrokoagulasi</strong>.</p>
            <p>Riset ini dikerjakan oleh tim <em>PKM Riset Eksakta (PKM-RE)</em> yang terdiri dari lima mahasiswa dan dibimbing oleh <strong>Ir. Zainal Arifin</strong>. Program penelitian ini memperoleh pendanaan dari <strong>Belmawa Kemdikbudristek tahun 2025</strong>.</p>
            <h2>Apa Itu Teknologi Sono-Elektrokoagulasi?</h2>
            <p>Teknologi hibrid ini menggabungkan dua proses sekaligus:</p>
            <ul>
               <li><strong>Gelombang ultrasonik (sono)</strong>, yang menghasilkan gelembung kavitasi bertekanan dan bersuhu tinggi untuk memecah polutan kompleks.</li>
               <li><strong>Elektrokoagulasi</strong>, yaitu proses penggumpalan partikel polutan menggunakan arus listrik sehingga lebih mudah dipisahkan dari air limbah.</li>
            </ul>
            <p>Hasil uji coba menunjukkan bahwa metode ini mampu menghilangkan hingga <strong>± 90% mikrofiber dan surfaktan</strong> dari air limbah laundry. Teknologi ini juga tidak membutuhkan bahan kimia tambahan sehingga lebih <strong>ramah lingkungan</strong> dan <strong>hemat biaya operasional</strong>.</p>
            <h2>Manfaat dan Potensi Implementasi</h2>
            <p>Dengan efektivitasnya, teknologi sono-elektrokoagulasi diharapkan dapat diimplementasikan secara luas, baik di industri laundry skala besar maupun usaha rumahan. Selain itu, tim Polnes juga berencana melakukan <strong>komersialisasi</strong> dan memperkenalkan teknologi ini kepada para pelaku usaha laundry.</p>
            <p>Inovasi ini menjadi salah satu bukti kontribusi nyata Polnes dalam menciptakan solusi teknologi tepat guna untuk keberlanjutan lingkungan.</p>
        """.trimIndent()
        ),
        News(
            id = 12,
            title = "Ragnar Azhar Saddiq Raih Medali Perak di HKIMO Final 2025",
            categoryId = 4,
            imageRes = R.drawable.sample_news12,
            authorId = 1,
            date = "2025-08-25",
            views = 210,
            youtubeVideoId = null,
            status = NewsStatus.PUBLISHED,
            content = """
            <p><strong>Tangerang</strong> – Ragnar Azhar Saddiq Lasabuda, siswa kelas dua sekolah dasar asal Tangerang, kembali mengharumkan nama Indonesia di kancah internasional. Ia berhasil meraih <strong>medali perak</strong> dalam ajang <em>Hong Kong International Mathematical Olympiad (HKIMO)</em> Final 2025 yang berlangsung pada 23–24 Agustus 2025.</p>
            <p>HKIMO merupakan olimpiade matematika tahunan yang digelar oleh Olympiad Education, Hong Kong. Kompetisi ini menjadi wadah bagi siswa dari seluruh dunia untuk bertukar pengalaman pendidikan dan budaya sekaligus menumbuhkan minat terhadap matematika.</p>
            <p>Pada tahun 2025, HKIMO mengundang peserta dari 21 wilayah/negara, termasuk Indonesia, Malaysia, Thailand, Singapura, Tiongkok, Taiwan, hingga Australia. Ragnar menjadi salah satu dari sekitar 90 peserta perwakilan Indonesia yang ikut bertanding mulai jenjang TK hingga SMA.</p>
            <h2>Dukungan dari Wapres Gibran dan Menko PMK</h2>
            <p>Ragnar kembali menorehkan prestasi internasional setelah sebelumnya meraih sejumlah penghargaan, antara lain:</p>
            <ul>
               <li>Gold Medal – Southeast Asian Mathematical Olympiad X (SEAMO X)</li>
               <li>Gold Medal – Final Thailand International Mathematical Olympiad (TIMO) + Euler Prize</li>
               <li>Silver Medal – Final Big Bay Bei Mathematical Olympiad</li>
               <li>Silver Medal – Singapore and Asian Schools Math Olympiad (SASMO)</li>
            </ul>
            <p>Setelah meraih emas di TIMO Thailand, Ragnar diundang ke Istana Negara bersama pelajar berprestasi lainnya untuk menerima apresiasi pemerintah. Wakil Presiden RI, <strong>Gibran Rakabuming Raka</strong>, memberikan beasiswa bagi Ragnar dan 17 pelajar lain untuk mengikuti HKIMO Final 2025.</p>
            <p>Ibunda Ragnar, Syahidah, menceritakan bahwa dukungan ini sangat berarti mengingat biaya keberangkatan ke final internasional biasanya sepenuhnya ditanggung orang tua. Selain itu, Ragnar juga mendapat dukungan pribadi dari <strong>Menko PMK Pratikno</strong>, berupa dana pengembangan untuk mendukung keikutsertaannya di lomba internasional.</p>
            <h2>Prestasi Indonesia di HKIMO 2025</h2>
            <p>HKIMO 2025 diikuti oleh 964 peserta secara langsung di Hong Kong dan 2.104 peserta secara online. Perwakilan Indonesia berhasil meraih total <strong>80 medali</strong> dari jenjang TK hingga SMA, terdiri dari 30 emas, 24 perak, dan 26 perunggu.</p>
            <h2>Ragnar Ingin Bertanding ke Macau</h2>
            <p>Meskipun kali ini meraih perak, Ragnar tetap bersemangat untuk terus berkembang. Ia mengatakan ingin mencoba kesempatan lagi di kompetisi mendatang dan bercita-cita mengikuti olimpiade matematika di <strong>Macau</strong>.</p>
            <p>Menurut Ragnar, momen paling berkesan selama mengikuti berbagai olimpiade adalah saat berkompetisi di Thailand, di mana ia bertemu teman-teman baru dari berbagai negara.</p>
            <p>“Thailand paling seru, soalnya pertama kali ketemu teman-teman yang suka ikut lomba juga. Tapi lomba di Hong Kong juga seru, karena pertama kalinya dibantu oleh Wapres dan Menteri,” kata Ragnar, siswa kelahiran 31 Juli 2018 tersebut.</p>
            <p>Selain ingin berlomba ke Macau, Ragnar juga mengungkapkan keinginannya untuk berlibur ke Maldives.</p>
        """.trimIndent()
        ),

        // --- KATEGORI 5: ACARA ---
        News(
            id = 13,
            title = "MoU dan Walk In Interview",
            categoryId = 5,
            imageRes = R.drawable.sample_news13,
            authorId = 1,
            date = "2025-11-22",
            views = 180,
            youtubeVideoId = null,
            status = NewsStatus.PUBLISHED,
            content = """
            <p>Politeknik Negeri Samarinda bersama PT Sims Jaya menggelar penandatanganan Memorandum of Understanding (MoU) yang sekaligus diikuti kegiatan Walk In Interview. Kerja sama ini bertujuan memperkuat hubungan antara institusi pendidikan vokasi dan industri serta menyediakan akses rekrutmen langsung bagi mahasiswa dan alumni.</p>
            <p>Untuk proses rekrutmen, adapun persyaratan yang wajib dipenuhi oleh pelamar adalah sebagai berikut:</p>
            <ol>
               <li>Usia maksimum 30 tahun</li>
               <li>Lulusan D3 Politeknik Negeri Samarinda Jurusan Teknik Mesin</li>
               <li>Sehat jasmani dan rohani</li>
               <li>Tidak sedang aktif bekerja</li>
               <li>Bersedia ditempatkan dan tinggal di aera operasional PT. SIMS JAYA KALTIM site Batu Kajang</li>
            </ol>
            <p>Tujuan rekruitmen ini memberikan kesempatan langsung bagi alumni untuk melamar pekerjaan, terutama bagi mereka yang mungkin kurang percaya diri dengan proses lamaran online. Hal ini membuka pintu bagi mereka untuk mendapatkan pekerjaan yang sesuai dengan kualifikasi dan minat mereka</p>
        """.trimIndent()
        ),
        News(
            id = 14,
            title = "Wisuda Polnes ke-37",
            categoryId = 5,
            imageRes = R.drawable.sample_news14,
            authorId = 1,
            date = "2025-10-18",
            views = 500,
            youtubeVideoId = null,
            status = NewsStatus.PUBLISHED,
            content = """
            <p>Sebanyak Total 1.667 orang lulusan dari 27 program studi di Politeknik Negeri Samarinda mengikuti rapat senat terbuka luar biasa dalam rangka wisuda ke-37 dengan 2 sesi, di Plenary Hall Jalan Wahid Hasyim, Sabtu (18/10).</p>
            <p>Tema yang di usung "Mencetak Generasi Emas 2045: Kompeten, Adaptif, Berakhlak, dan Berdampak Bagi Masyarakat."</p>
            <p>Wisuda ini turut dihadiri Direktur Akademik Pendidikan Tinggi Vokasi Direktorat Jenderal Pendidikan Vokasi Kementerian Pendidikan, Kebudayaan, Riset, dan Teknologi Republik Indonesia melalui tiping video, civitas akademi Polnes, orang tua, dan undangan baik mitra maupun industri lainnya.</p>
            <p>"Selamat atas kompetensi yang telah diraih oleh para lulusan melalui kerja keras, komitmen dan pengorbanan yang tentunya tidak mudah d ilalui hingga saat ini kalian dapat meraih prestasi yang di capai memuaskan," ungkap Ahyar Muhammad Diah.</p>
            <p>Ahyar Muhammad Diah lanjut menambahkan, wisuda ini merupakan langkah awal perjalanan panjang untuk menjadi agen perubahan di bidang keahlian dan profesi masing-masing serta menjadi calon pemimpin dimasa depan yang akan membawa Indonesia untuk bisa unggul dan memiliki daya saing yang tinggi.</p>
            <p>Saya yakin dengan kompetensi yang dimiliki saat ini dapat membuat para lulusan Polnes dapat berkiprah dalam dunia kerja, usaha dan industri, tidak hanya bergantung sebagai pencari kerja tetapi juga dapat menciptakan lapangan kerja sendiri," sambungannya.</p>
            <p>Dalam kesempatan itu juga Ahyar Muhammad Diah juga menuturkan tentang pencapaian berupa berprestasi di bidang akademik, mahasiswa Politeknik Negeri Samarinda yang berprestasi dalam bidang non akademik. Mahasiswa aktif mengikuti berbagai kegiatan lomba, dan menghasilkan prestasi - prestasi yang membanggakan. diantaranya</p>
            <ol>
               <li>In 1 d Indonesian Polytechnic English Championship (IPEC) Politeknik Negeri Manado</li>
               <li>Festival Olahraga Masyarakat Nasional (FORNAS) VIII Nusa di selenggarakan oleh Komite Olahraga Masyarakat Indonesia (KORMI) Nusa Tenggara Barat & KEMENPORA</li>
               <li>Juara Harapan 1 Musabaqah Tilawatil Qur'an XVIII 2025 tingkat Nasional di Banjarmasin an. Alan Arijal dari Jurusan Teknik Elektro yang berhasil menyisihkan sejumlah peserta 350 orang dari PTN dan PTS ternama.</li>
               <li>International And Innvention Competition Through Exhibition (i-Compex) di Sri Mas Hall Politeknik Sultan Abdul Halim Mu'adzam Shas (Jitra, Kedah Malaysia)</li>
               <li>Kompetisi Faperta Fair 8 Cabang Lomba Bussiness Plan</li>
               <li>Kompetisi Mahasiswa Infomatika Politeknik Nasional (KMIPN) 2025 di Padang</li>
            </ol>
            <p>Dan lebih banyak lagi perlomban yang diikuti dan prestasi yang dicetak oleh mahasiswa POLNES.</p>
            <p>Tidak lupa Ahyar juga menyampaikan bahwa Polnes terus berupaya meningkatkan kualitas pendidikan dan relevansi kurikulum dengan kebutuhan industri. "Kami terus menjalin kerjasama dengan berbagai pihak untuk memastikan lulusan Polnes memiliki kompetensi yang unggul dan siap bersaing di pasar kerja global," jelasnya.</p>
            <p>Dalam kesempatan itu Ahyar juga memberikan inspirasi bagi para wisudawan untuk terus mengembangkan diri dan berkontribusi dalam memajukan daerah.</p>
            <p>Sidang Senat Terbuka Wisuda Polnes 2025 ini menjadi bukti komitmen Polnes dalam menghasilkan sumber daya manusia yang unggul, berintegritas, dan siap berkontribusi bagi masyarakat, bangsa dan negara.</p>
            <p>Acara wisuda ini menjadi istimewa dengan pemberian penghargaan kepada wisudawan terbaik dari masing-masing jurusan. Diakhir penyataan, ia berterima kasih kepada para dosen dan staf Polnes yang telah memberikan ilmu dan bimbingan yang tak ternilai harganya. Kepada lulusan agar dapat terus mengaplikasikan ilmu ini di dunia kerja dan memberikan yang terbaik bagi bangsa," pungkasnya.</p>
            <p>Lululsan IPK Tertinggi Wisuda Polnes ke-37 Tahun 2025</p>
            <ol>
               <li>Adestya Ramadani Prodi S2 Terapan Pemasaran Inovasi dan Teknlogi IPK 4,00</li>
               <li>Alvina Harahap, D3 Akuntasi IPK 3,99</li>
               <li>Gutsi Tri Cahyani , D3 Nautika IPK 3,93</li>
               <li>Renita Tri Cahyani, S1 Terapan Akuntansi Manajerial, IPK 3,94</li>
               <li>Aripadi Maulana S1 Terapan Arsitektur Bangunan Gedung IPK 3,92</li>
               <li>Puti Safina Yamanda D3 KPNK IPK 3,90</li>
               <li>Nur Hidayati, S1 Terapan Bisnis Digital, IPK 3,89</li>
               <li>Salsabila Asyabiya D3 Petro Ole Kimia IPK 3,89</li>
            </ol>
        """.trimIndent()
        ),
        News(
            id = 15,
            title = "Dies Natalis Polnes ke-38",
            categoryId = 5,
            imageRes = R.drawable.sample_news15,
            authorId = 1,
            date = "2025-10-10",
            views = 340,
            youtubeVideoId = null,
            status = NewsStatus.PUBLISHED,
            content = """
            <p>pagi tadi, merupakan hari spesial bagi seluruh civitas akademika Politeknik Negeri Samarinda (Polnes). Karena kampus vokasi ini telah merayakan Dies Natalis ke-38. Peringatan hari lahir sejak 10 Oktober 1987 ini dimeriahkan dengan berbagai kegiatan hiburan dan menyehatkan.</p>
            <p>Mengusung tema hijau sesuai identitasnya yang ramah lingkungan dengan konsep sehat, bahagia dalam berbagai perlombaan menarik bertabur hadiah hingga prestasi.</p>
            <p>Peringatan acara ini di helat di area Kampus Pones, Jalan Cipto Mangunkusumo Samarinda Seberang. Diawali dengan jalan sehat oleh seluruh civitas akademika mulai pukul 07.00 WITa. Kemudian dilanjut dengan lomba menghias tumpeng. Juga ada penghargaan bagi para dosen dan mahasiswa pemenang berbagai kategori juara dalam rangkaian kegiatan lomba menuju Dies Natalis ini. Selanjutnya ada pemotongan tumpeng menandai puncak acara yang ditutup pengundian ratusan doorprize jalan sehat, serta sarasehan atau ramah tamah.</p>
            <p>“Dalam peringaan Dies Natalis ke-38 ini Polnes banyak meraih prestasi membanggakan. Mulai dari anugerah 2 guru besar sekaligus di 2025 dan 2024 lalu 1 guru besar, jadi totalnya 3 guru besar. Selain dosen juga ada anak didik mahasiswa kami juara MTQ Nasional,” jelas Direktur Polnes, Ahyar Muhammad Diah didampingi Wakil Direktur (Wadir) I, Dedy Irawan dan Wadir II, Karyo Budi Utomo di lokasi acara.</p>
            <p>Tampak dalam agenda ini Direktur Polnes Periode 2019-2023, Ramli hadir juga mengapresiasi capaian Polnes terkini. Karana sudah banyak bertabur prestasi dan juara yang diraih. Dirinya juga menitipkan doa terbaik bagi Polnes. Dalam posisi apapun peran memajukan civitas Pones harus tetap semangat dan bahagia.</p>
            <p>Kemudian ada Ibayasid Direktur Polnes Periode 2011-2019 juga bangga pada Polnes saat ini. Karena banyak prestasi yang dicapai. Ucapan selamat dan sukses pada Polnes agar semakin jaya serta semakin banyak diminati oleh masyarakat juga diungkapkannya. Terlebih diminati oleh dunia usaha dengan lulusan yang berprestasi.</p>
            <p>Ahyar mengungkapkan, dalam peringata Dies Natalis ke-38 kampus yang memiliki 7.000-an mahasiswa aktif ini, telah melampaui perjalanan panjang. Sejak didirikan pada 1987 silam, tentunya banyak mencetak prestasi dan saksi. Mulai dari Polnes hanya memiliki 5 Program Studi (Prodi) dengan 125 mahasiswa saja. Sekarang pada 2025 ini mencatatkan 7.000 mahasiswa aktif dari 28 Prodi dengan 10 jurusan. Termasuk juga kini telah memiliki Prodi Magister Terapan (S2).</p>
            <p>Hal tersebut tentunya diakui Ahyar merupakan capaian luar biasa selama kepemimpinannya. Jadi sudah sepatutnya pencapaian yang baik dan positif, untuk dijadikan pemicu semangat bagi seluruh tenaga pendidik di Polnes. Agar terus melakukan perubahan ke arah yang lebih baik. Dengan tujuan utama untuk mencetak lulusan SDM terampil di bidang keahliannya.</p>
            <p>“Kita harus menanamkan, bahwa setiap tahun harus bisa selalu berevolisi. Terus bergerak maju seiring dengan kemajuan jaman teknologi. Semangat itulah yang bisa mengantarkan berbagai macam kemajuan dan prestasi dimasa depan,” kata Ahyar diamini Wadir III, Suparno dan Wadir IV, Said Keliwar.</p>
            <p>“Semoga kegiatan Dies Natalis Ke-38 Polnes hari ini kita jadikan semangat untuk berprestasi. Memajukan institusi Polnes yang lebih profesional, berkualitas dan relevan dengan dunia industri (Dudi). Ditahun mendatang kita berharap berbagai kemajuan itu segera diraih,” harap Ahyar.</p>
        """.trimIndent()
        )
    )

    // PENTING: Gunakan mutableListOf agar komentar bisa ditambah
    val commentList = mutableListOf(
        // --- Komentar untuk Berita 1: Golongan Darah (Tayang 09 Nov) ---
        Comment(
            id = 1,
            newsId = 1,
            userId = 2, // Anggap user ID 2 adalah pembaca
            rating = 5,
            date = "2025-11-10"
        ),
        Comment(
            id = 2,
            newsId = 1,
            userId = 3,
            rating = 4,
            date = "2025-11-11"
        ),

        // --- Komentar untuk Berita 2: Polio Berakhir (Tayang 08 Nov) ---
        Comment(
            id = 3,
            newsId = 2,
            userId = 4,
            rating = 5,
            date = "2025-11-09"
        ),

        // --- Komentar untuk Berita 4: Hari Guru (Tayang 25 Nov) ---
        Comment(
            id = 4,
            newsId = 4,
            userId = 2,
            rating = 5,
            date = "2025-11-25"
        ),
        Comment(
            id = 5,
            newsId = 4,
            userId = 5,
            rating = 5,
            date = "2025-11-26"
        ),

        // --- Komentar untuk Berita 6: Digitalisasi Pendidikan (Tayang 17 Nov) ---
        Comment(
            id = 6,
            newsId = 6,
            userId = 3,
            rating = 4,
            date = "2025-11-18"
        ),

        // --- Komentar untuk Berita 7: Job Fair Polnes (Tayang 21 Nov) ---
        Comment(
            id = 7,
            newsId = 7,
            userId = 2,
            rating = 5,
            date = "2025-11-21"
        ),
        Comment(
            id = 8,
            newsId = 7,
            userId = 4,
            rating = 3,
            date = "2025-11-22"
        ),
        Comment(
            id = 9,
            newsId = 7,
            userId = 5,
            rating = 4,
            date = "2025-11-22"
        ),

        // --- Komentar untuk Berita 10: Prestasi Mahasiswa Malaysia (Tayang 22 Okt) ---
        Comment(
            id = 10,
            newsId = 10,
            userId = 3,
            rating = 5,
            date = "2025-10-23"
        ),

        // --- Komentar untuk Berita 12: Ragnar Math Olympiad (Tayang 25 Agt) ---
        Comment(
            id = 11,
            newsId = 12,
            userId = 2,
            rating = 5,
            date = "2025-08-26"
        ),
        Comment(
            id = 12,
            newsId = 12,
            userId = 4,
            rating = 5,
            date = "2025-08-27"
        ),

        // --- Komentar untuk Berita 14: Wisuda Polnes (Tayang 18 Okt) ---
        Comment(
            id = 13,
            newsId = 14,
            userId = 5,
            rating = 5,
            date = "2025-10-19"
        ),
        Comment(
            id = 14,
            newsId = 14,
            userId = 3,
            rating = 4,
            date = "2025-10-20"
        ),

        // --- Komentar untuk Berita 15: Dies Natalis (Tayang 10 Okt) ---
        Comment(
            id = 15,
            newsId = 15,
            userId = 2,
            rating = 5,
            date = "2025-10-11"
        )
    )

    /**
     * Membuat daftar notifikasi palsu.
     * Menggunakan fungsi agar selalu menghasilkan data baru saat dipanggil
     * berdasarkan newsList terkini.
     */
    fun getNotifications(): List<Notification> {
        return newsList.map { news ->
            val categoryName = categoryList.find { it.id == news.categoryId }?.name ?: "Unknown"
            Notification(
                id = news.id,
                iconRes = R.drawable.ic_news, // Pastikan icon ini ada di drawable
                category = categoryName,
                title = news.title,
                date = formatDate(news.date)
            )
        }
    }
}