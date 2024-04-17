import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.Scanner;

public class Kontaktverfolgung {

    private static final String CSV_FILE_PATH = "kontaktdaten.csv";
    private static final int DATA_RETENTION_DAYS = 14;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Willkommen zur Kontaktverfolgung.");
        System.out.println("Bitte geben Sie Ihre Daten ein:");

        System.out.print("Name: ");
        String name = scanner.nextLine();

        System.out.print("Vorname: ");
        String vorname = scanner.nextLine();

        System.out.print("E-Mail-Adresse: ");
        String email = scanner.nextLine();

        System.out.print("Adresse: ");
        String adresse = scanner.nextLine();

        System.out.print("Telefonnummer: ");
        String telefonnummer = scanner.nextLine();

        if (!isValidEmail(email)) {
            System.out.println("Ungültige E-Mail-Adresse. Bitte erneut eingeben.");
            return;
        }

        speichereKontaktdaten(name, vorname, email, adresse, telefonnummer);

        System.out.println("Kontaktdaten erfolgreich gespeichert.");

        planLöschungNach14Tagen();
    }

    private static boolean isValidEmail(String email) {
        return email.contains("@") && email.contains(".");
    }

    private static void speichereKontaktdaten(String name, String vorname, String email, String adresse, String telefonnummer) {
        try (FileWriter fileWriter = new FileWriter(CSV_FILE_PATH, true);
             PrintWriter writer = new PrintWriter(fileWriter)) {

            LocalDate speicherDatum = LocalDate.now();

            writer.println(name + "," + vorname + "," + email + "," + adresse + "," + telefonnummer + "," + speicherDatum);

        } catch (IOException e) {
            System.out.println("Fehler beim Speichern der Kontaktdaten: " + e.getMessage());
        }
    }

    private static void planLöschungNach14Tagen() {
        LocalDate löschDatum = LocalDate.now().plusDays(DATA_RETENTION_DAYS);

        System.out.println("Die Kontaktdaten werden automatisch nach " + DATA_RETENTION_DAYS + " Tagen gelöscht.");
        System.out.println("Löschdatum: " + löschDatum);
    }
}
