
        import java.util.Scanner;

public class tugas {     

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("=== Kalkulator Segitiga (Java) ===");
        System.out.println("Ketik 'q' atau 'quit' kapan saja untuk keluar.");

        while (true) {
            System.out.println("\nPilih metode perhitungan:");
            System.out.println("  1) Menghitung luas & keliling dari 3 sisi (SSS)");
            System.out.println("  2) Menghitung luas dari alas & tinggi (opsional keliling jika memasukkan sisi lain)");
            System.out.println("  3) Keluar");
            System.out.print("Masukkan pilihan (1/2/3): ");

            String choice = scanner.nextLine().trim();
            if (isQuit(choice) || "3".equals(choice)) {
                System.out.println("Terima kasih. Sampai jumpa.");
                break;
            }

            switch (choice) {
                case "1":
                    handleThreeSides();
                    break;
                case "2":
                    handleBaseHeight();
                    break;
                default:
                    System.out.println("Pilihan tidak dikenal. Silakan masukkan 1, 2, atau 3.");
            }
        }
    }

    private static void handleThreeSides() {
        System.out.println("\n-- Menghitung dari 3 sisi (SSS) --");
        Double a = readPositiveDouble("Masukkan panjang sisi a: ");
        if (a == null) return;
        Double b = readPositiveDouble("Masukkan panjang sisi b: ");
        if (b == null) return;
        Double c = readPositiveDouble("Masukkan panjang sisi c: ");
        if (c == null) return;

        if (!isValidTriangle(a, b, c)) {
            System.out.println("Ketiga nilai tidak membentuk segitiga yang valid (melanggar ketidakberataan segitiga).");
            return;
        }

        double perimeter = a + b + c;
        double area = heronArea(a, b, c);

        System.out.printf("Hasil:%n  Keliling = %.4f%n  Luas     = %.4f%n", perimeter, area);
    }

    private static void handleBaseHeight() {
        System.out.println("\n-- Menghitung luas dari alas & tinggi --");
        Double base = readPositiveDouble("Masukkan panjang alas (base): ");
        if (base == null) return;
        Double height = readPositiveDouble("Masukkan tinggi (height): ");
        if (height == null) return;

        double area = 0.5 * base * height;
        System.out.printf("%nHasil:%n  Luas = %.4f%n", area);

        while (true) {
            System.out.print("\nApakah Anda ingin memasukkan sisi-sisi lain untuk menghitung keliling? (y/n): ");
            String ans = scanner.nextLine().trim().toLowerCase();
            if (ans.isEmpty()) continue;
            if (isQuit(ans)) {
                System.out.println("Keluar.");
                return;
            }
            if (ans.startsWith("y")) {
                Double s1 = readPositiveDouble("Masukkan panjang sisi kedua: ");
                if (s1 == null) return;
                Double s2 = readPositiveDouble("Masukkan panjang sisi ketiga: ");
                if (s2 == null) return;

                if (!isValidTriangle(base, s1, s2)) {
                    System.out.println("Nilai-nilai tersebut tidak membentuk segitiga yang valid. Tidak dapat menghitung keliling.");
                    return;
                }

                double perimeter = base + s1 + s2;
                double areaHeron = heronArea(base, s1, s2);

                System.out.printf("%nKeliling = %.4f%n", perimeter);
                System.out.printf("Luas (dihitung dari alas & tinggi) = %.4f%n", area);
                System.out.printf("Luas (dihitung ulang dengan Heron) = %.4f%n", areaHeron);
                if (Math.abs(area - areaHeron) > 1e-6) {
                    System.out.println("Catatan: Luas dari alas & tinggi berbeda dengan luas dari tiga sisi (mungkin tinggi yang dimasukkan bukan tinggi tegak).");
                }
                return;
            } else if (ans.startsWith("n") || ans.startsWith("t")) {
                return;
            } else {
                System.out.println("Masukkan 'y' atau 'n'.");
            }
        }
    }

    // Membaca input baris, menerima 'q' atau 'quit' untuk keluar, memeriksa angka positif.
    private static Double readPositiveDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine().trim();
            if (isQuit(line)) {
                System.out.println("Keluar.");
                return null;
            }
            if (line.isEmpty()) {
                System.out.println("Input kosong. Masukkan angka (contoh: 3.5).");
                continue;
            }
            try {
                double val = Double.parseDouble(line);
                if (Double.isNaN(val) || Double.isInfinite(val)) {
                    System.out.println("Masukkan tidak valid. Coba lagi.");
                    continue;
                }
                if (val <= 0) {
                    System.out.println("Masukkan harus bernilai positif. Coba lagi.");
                    continue;
                }
                return val;
            } catch (NumberFormatException e) {
                System.out.println("Input tidak valid. Masukkan angka (contoh: 3.5).");
            }
        }
    }

    private static boolean isQuit(String s) {
        if (s == null) return false;
        String t = s.trim().toLowerCase();
        return t.equals("q") || t.equals("quit") || t.equals("exit");
    }

    private static boolean isValidTriangle(double a, double b, double c) {
        return (a + b > c) && (a + c > b) && (b + c > a);
    }

    private static double heronArea(double a, double b, double c) {
        double s = (a + b + c) / 2.0;
        double inside = s * (s - a) * (s - b) * (s - c);
        if (inside <= 0) return 0.0;
        return Math.sqrt(inside);
    }
}
