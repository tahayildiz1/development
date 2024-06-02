import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Die Klasse Kontoverwaltung ist eine JavaFX-Anwendung zur Verwaltung von Bankkonten.
 * Sie bietet Funktionen zum Erstellen, Löschen, Ändern und Anzeigen von Konten.
 */

public class Kontoverwaltung extends Application {
    private static final String DATEI_NAME = "konten.csv";
    private static List<Konto> kontenListe = new ArrayList<>();
    private static int kontonummerZaehler = 1000; // Startnummer für Kontonummern

    /**
     * Die main-Methode lädt die Konten und startet die Anwendung.
     * @param args die Befehlszeilenargumente
     */
    public static void main(String[] args) {
        ladeKonten();
        launch(args);
    }

    /**
     * Die Methode start initialisiert die Benutzeroberfläche der Anwendung.
     * @param primaryStage das Hauptfenster der Anwendung
     */
    @Override
    public void start(Stage primaryStage) {
        // Code für die Initialisierung der Benutzeroberfläche
        primaryStage.setTitle("Kontoverwaltung");

        /**
         * Die Methode createKontoErstellenPane erstellt das Pane zum Erstellen von Konten.
         * @return das Pane zum Erstellen von Konten
         */
        TabPane tabPane = new TabPane();

        Tab tabErstellen = new Tab("Konto erstellen", createKontoErstellenPane());
        Tab tabLoeschen = new Tab("Konto löschen", createKontoLoeschenPane());
        Tab tabAendern = new Tab("Konto ändern", createKontoAendernPane());
        Tab tabLesen = new Tab("Konten anzeigen", createKontoLesenPane());
        Tab tabDatenAnsehen = new Tab("Daten ansehen", createDatenAnsehenPane());
        Tab tabDatenAendern = new Tab("Daten ändern", createDatenAendernPane());
        Tab tabUmbuchen = new Tab("Umbuchen", createUmbuchenPane());
        Tab tabSuchen = new Tab("Suchen", createSuchenPane());
        Tab tabEinzahlenAuszahlen = new Tab("Einzahlen/Auszahlen", createEinzahlenAuszahlenPane());
        Tab tabUmbuchenMitIBAN = new Tab("Umbuchen mit IBAN", createUmbuchenMitIBANPane());
        tabPane.getTabs().addAll(tabErstellen, tabLoeschen, tabAendern, tabLesen, tabDatenAnsehen, tabDatenAendern, tabUmbuchen, tabSuchen, tabEinzahlenAuszahlen, tabUmbuchenMitIBAN);

        Scene scene = new Scene(tabPane, 950, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Pane createKontoErstellenPane() {
        // Code für die Erstellung des Pane zum Erstellen von Konten
        VBox vbox = new VBox();
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(10));

        TextField kontoinhaberField = new TextField();
        TextField saldoField = new TextField();
        ComboBox<String> kontoartBox = new ComboBox<>();
        kontoartBox.getItems().addAll("Girokonto", "Sparkonto");

        Button erstellenButton = new Button("Konto erstellen");
        erstellenButton.setOnAction(e -> {
            String kontoinhaber = kontoinhaberField.getText();
            double saldo = Double.parseDouble(saldoField.getText());
            String kontoart = kontoartBox.getValue();

            String anlegedatum = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            Konto neuesKonto = null;

            if (kontoart.equals("Girokonto")) {
                String iban = generateIBAN(); // You need to implement this method to generate an IBAN
                neuesKonto = new Girokonto(kontonummerZaehler++, kontoinhaber, saldo, anlegedatum, iban);
            } else if (kontoart.equals("Sparkonto")) {
                String iban = generateIBAN(); // You need to implement this method to generate an IBAN
                neuesKonto = new Sparkonto(kontonummerZaehler++, kontoinhaber, saldo, anlegedatum, iban);
            }

            kontenListe.add(neuesKonto);
            speichereKonten();
            kontoinhaberField.clear();
            saldoField.clear();
            kontoartBox.setValue(null);
        });

        vbox.getChildren().addAll(new Label("Kontoinhaber:"), kontoinhaberField, new Label("Saldo:"), saldoField, new Label("Kontoart:"), kontoartBox, erstellenButton);

        return vbox;
    }

    /**
     * Die Methode createEinzahlenAuszahlenPane erstellt das Pane zum Ein- und Auszahlen von Geld.
     * @return das Pane zum Ein- und Auszahlen von Geld
     */
    private Pane createEinzahlenAuszahlenPane() {
        // Code für die Erstellung des Pane zum Ein- und Auszahlen von Geld
        VBox vbox = new VBox();
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(10));

        TextField kontonummerField = new TextField();
        TextField betragField = new TextField();

        Button einzahlenButton = new Button("Einzahlen");
        Button auszahlenButton = new Button("Auszahlen");

        einzahlenButton.setOnAction(e -> {
            int kontonummer = Integer.parseInt(kontonummerField.getText());
            double betrag = Double.parseDouble(betragField.getText());
            Konto konto = findeKonto(kontonummer);

            if (konto != null) {
                if (!konto.einzahlen(betrag)) {
                    showAlert("Fehler", "Betrag muss positiv sein.");
                }
                speichereKonten();
            } else {
                showAlert("Fehler", "Konto nicht gefunden.");
            }
        });

        auszahlenButton.setOnAction(e -> {
            int kontonummer = Integer.parseInt(kontonummerField.getText());
            double betrag = Double.parseDouble(betragField.getText());
            Konto konto = findeKonto(kontonummer);

            if (konto != null) {
                if (!konto.auszahlen(betrag)) {
                    showAlert("Fehler", "Nicht genug Geld oder Betrag negativ.");
                }
                speichereKonten();
            } else {
                showAlert("Fehler", "Konto nicht gefunden.");
            }
        });

        vbox.getChildren().addAll(new Label("Kontonummer:"), kontonummerField, new Label("Betrag:"), betragField, einzahlenButton, auszahlenButton);

        return vbox;
    }

    /**
     * Die Methode findeKonto sucht ein Konto anhand seiner Kontonummer.
     * @param kontonummer die Kontonummer des gesuchten Kontos
     * @return das gefundene Konto oder null, wenn kein Konto gefunden wurde
     */
    private Konto findeKonto(int kontonummer) {
        for (Konto konto : kontenListe) {
            if (konto.kontonummer == kontonummer) {
                return konto;
            }
        }
        return null;
    }

    /**
     * Die Methode findeKontoMitIBAN sucht ein Konto anhand seiner IBAN.
     * @param iban die IBAN des gesuchten Kontos
     * @return das gefundene Konto oder null, wenn kein Konto gefunden wurde
     */
    private Konto findeKontoMitIBAN(String iban) {
        for (Konto konto : kontenListe) {
            if (konto.getIBAN().equals(iban)) { // Stellen Sie sicher, dass Ihre Konto-Klasse eine getIBAN-Methode hat
                return konto;
            }
        }
        return null; // Rückgabe null, wenn kein Konto mit der angegebenen IBAN gefunden wurde
    }

    /**
     * Die Methode umbuchen bucht Geld von einem Konto auf ein anderes.
     * @param vonKontonummer die Kontonummer des Kontos, von dem das Geld abgebucht wird
     * @param zuKontonummer die Kontonummer des Kontos, auf das das Geld gebucht wird
     * @param betrag der zu buchende Betrag
     */
    public void umbuchen(int vonKontonummer, int zuKontonummer, double betrag) {
        Konto vonKonto = findeKonto(vonKontonummer);
        Konto zuKonto = findeKonto(zuKontonummer);

        if (vonKonto == null || zuKonto == null) {
            showAlert("Fehler", "Eines oder beide Konten wurden nicht gefunden.");
            return;
        }

        if (vonKonto.getSaldo() < betrag) {
            showAlert("Fehler", "Nicht genug Geld auf dem Ausgangskonto.");
            return;
        }

        vonKonto.auszahlen(betrag);
        zuKonto.einzahlen(betrag);
        speichereKonten();
    }

    /**
     * Die Methode umbuchenMitIBAN bucht Geld von einem Konto auf ein anderes, wobei die Konten anhand ihrer IBAN identifiziert werden.
     * @param vonIBAN die IBAN des Kontos, von dem das Geld abgebucht wird
     * @param zuIBAN die IBAN des Kontos, auf das das Geld gebucht wird
     * @param betrag der zu buchende Betrag
     */
    public void umbuchenMitIBAN(String vonIBAN, String zuIBAN, double betrag) {
        Konto vonKonto = findeKontoMitIBAN(vonIBAN);
        Konto zuKonto = findeKontoMitIBAN(zuIBAN);

        if (vonKonto == null || zuKonto == null) {
            showAlert("Fehler", "Eines oder beide Konten wurden nicht gefunden.");
            return;
        }

        if (vonKonto.getSaldo() < betrag) {
            showAlert("Fehler", "Nicht genug Geld auf dem Ausgangskonto.");
            return;
        }

        vonKonto.auszahlen(betrag);
        zuKonto.einzahlen(betrag);
        speichereKonten();
    }

    private Pane createUmbuchenMitIBANPane() {
        VBox vbox = new VBox();
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(10));

        TextField vonIBANField = new TextField();
        TextField zuIBANField = new TextField();
        TextField betragField = new TextField();

        Button umbuchenButton = new Button("Umbuchen");
        umbuchenButton.setOnAction(e -> {
            String vonIBAN = vonIBANField.getText();
            String zuIBAN = zuIBANField.getText();
            double betrag = Double.parseDouble(betragField.getText());

            umbuchenMitIBAN(vonIBAN, zuIBAN, betrag);
        });

        vbox.getChildren().addAll(new Label("Von IBAN:"), vonIBANField, new Label("Zu IBAN:"), zuIBANField, new Label("Betrag:"), betragField, umbuchenButton);

        return vbox;
    }

    public List<Konto> suchen(String suchbegriff) {
        List<Konto> gefundeneKonten = new ArrayList<>();

        for (Konto konto : kontenListe) {
            if (konto.getKontoinhaber().contains(suchbegriff)) {
                gefundeneKonten.add(konto);
            }
        }
        return gefundeneKonten;
    }

    private Pane createKontoLoeschenPane() {
        VBox vbox = new VBox();
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(10));

        TextField kontonummerField = new TextField();

        Button loeschenButton = new Button("Konto löschen");
        loeschenButton.setOnAction(e -> {
            int kontonummer = Integer.parseInt(kontonummerField.getText());
            Konto zuLoeschen = findeKonto(kontonummer);

            if (zuLoeschen != null) {
                kontenListe.remove(zuLoeschen);
                speichereKonten();
                kontonummerField.clear();
            } else {
                showAlert("Fehler", "Konto nicht gefunden.");
            }
        });

        vbox.getChildren().addAll(new Label("Kontonummer:"), kontonummerField, loeschenButton);

        return vbox;
    }

    private Pane createKontoAendernPane() {
        VBox vbox = new VBox();
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(10));

        TextField kontonummerField = new TextField();
        TextField kontoinhaberField = new TextField();
        TextField saldoField = new TextField();

        Button aendernButton = new Button("Konto ändern");
        aendernButton.setOnAction(e -> {
            int kontonummer = Integer.parseInt(kontonummerField.getText());
            Konto zuAendern = findeKonto(kontonummer);

            if (zuAendern != null) {
                zuAendern.setKontoinhaber(kontoinhaberField.getText());
                zuAendern.setSaldo(Double.parseDouble(saldoField.getText()));
                speichereKonten();
            } else {
                showAlert("Fehler", "Konto nicht gefunden.");
            }
        });

        vbox.getChildren().addAll(new Label("Kontonummer:"), kontonummerField, new Label("Neuer Kontoinhaber:"), kontoinhaberField, new Label("Neuer Saldo:"), saldoField, aendernButton);

        return vbox;
    }

    private Pane createKontoLesenPane() {
        VBox vbox = new VBox();
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(10));

        TextArea textArea = new TextArea();
        textArea.setEditable(false);

        Button anzeigenButton = new Button("Konten anzeigen");
        anzeigenButton.setOnAction(e -> {
            StringBuilder sb = new StringBuilder();
            for (Konto konto : kontenListe) {
                sb.append(konto).append("\n");
            }
            textArea.setText(sb.toString());
        });

        vbox.getChildren().addAll(anzeigenButton, textArea);

        return vbox;
    }

    private Pane createDatenAnsehenPane() {
        VBox vbox = new VBox();
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(10));

        TextField kontonummerField = new TextField();
        TextArea textArea = new TextArea();
        textArea.setEditable(false);

        Button anzeigenButton = new Button("Daten anzeigen");
        anzeigenButton.setOnAction(e -> {
            String kontonummerText = kontonummerField.getText();
            if (kontonummerText.isEmpty()) {
                showAlert("Fehler", "Bitte geben Sie eine Kontonummer ein.");
                return;
            }
            int kontonummer = Integer.parseInt(kontonummerText);
            Konto konto = findeKonto(kontonummer);
            if (konto == null) {
                showAlert("Fehler", "Konto nicht gefunden.");
                return;
            }
            textArea.setText(konto.toString());
        });

        vbox.getChildren().addAll(new Label("Kontonummer:"), kontonummerField, anzeigenButton, textArea);

        return vbox;
    }
    private Pane createDatenAendernPane() {
        VBox vbox = new VBox();
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(10));

        TextField kontonummerField = new TextField();
        TextField kontoinhaberField = new TextField();
        TextField saldoField = new TextField();

        Button aendernButton = new Button("Daten ändern");
        aendernButton.setOnAction(e -> {
            int kontonummer = Integer.parseInt(kontonummerField.getText());
            Konto konto = findeKonto(kontonummer);

            if (konto != null) {
                konto.setKontoinhaber(kontoinhaberField.getText());
                konto.setSaldo(Double.parseDouble(saldoField.getText()));
                speichereKonten();
            } else {
                showAlert("Fehler", "Konto nicht gefunden.");
            }
        });

        vbox.getChildren().addAll(new Label("Kontonummer:"), kontonummerField, new Label("Neuer Kontoinhaber:"), kontoinhaberField, new Label("Neuer Saldo:"), saldoField, aendernButton);

        return vbox;
    }

    private Pane createUmbuchenPane() {
        VBox vbox = new VBox();
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(10));

        TextField vonKontonummerField = new TextField();
        TextField zuKontonummerField = new TextField();
        TextField betragField = new TextField();

        Button umbuchenButton = new Button("Umbuchen");
        umbuchenButton.setOnAction(e -> {
            int vonKontonummer = Integer.parseInt(vonKontonummerField.getText());
            int zuKontonummer = Integer.parseInt(zuKontonummerField.getText());
            double betrag = Double.parseDouble(betragField.getText());

            umbuchen(vonKontonummer, zuKontonummer, betrag);
        });

        vbox.getChildren().addAll(new Label("Von Kontonummer:"), vonKontonummerField, new Label("Zu Kontonummer:"), zuKontonummerField, new Label("Betrag:"), betragField, umbuchenButton);

        return vbox;
    }

    private Pane createSuchenPane() {
        VBox vbox = new VBox();
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(10));

        TextField suchbegriffField = new TextField();
        TextArea textArea = new TextArea();
        textArea.setEditable(false);

        Button suchenButton = new Button("Suchen");
        suchenButton.setOnAction(e -> {
            String suchbegriff = suchbegriffField.getText();
            List<Konto> gefundeneKonten = suchen(suchbegriff);

            StringBuilder sb = new StringBuilder();
            for (Konto konto : gefundeneKonten) {
                sb.append(konto).append("\n");
            }
            textArea.setText(sb.toString());
        });

        vbox.getChildren().addAll(new Label("Suchbegriff:"), suchbegriffField, suchenButton, textArea);

        return vbox;
    }

    /**
     * Die Methode showAlert zeigt einen Fehlerdialog an.
     * @param title der Titel des Dialogs
     * @param message die Nachricht des Dialogs
     */
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Die Methode generateIBAN generiert eine zufällige IBAN.
     * @return die generierte IBAN
     */
    private static String generateIBAN() {
        // This is a very basic implementation and might not be suitable for your needs.
        // You should replace this with your own IBAN generation logic.
        return "DE" + (new Random().nextInt(90000000) + 10000000) + "50010517";
    }

    /**
     * Die Methode ladeKonten lädt die Konten aus einer Datei.
     */
    private static void ladeKonten() {
        try (BufferedReader br = new BufferedReader(new FileReader(DATEI_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] daten = line.split(";");
                if (daten.length < 6) {
                    System.out.println("Skipping line with less than 6 values: " + line);
                    continue;
                }
                int kontonummer = Integer.parseInt(daten[0]);
                String kontoinhaber = daten[1];
                double saldo = Double.parseDouble(daten[2]);
                String kontoart = daten[3];
                String anlegedatum = daten[4];
                String iban = daten[5];

                Konto konto;
                if (kontoart.equals("Girokonto")) {
                    konto = new Girokonto(kontonummer, kontoinhaber, saldo, anlegedatum, iban);
                } else {
                    konto = new Sparkonto(kontonummer, kontoinhaber, saldo, anlegedatum, iban);
                }

                kontenListe.add(konto);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Die Methode speichereKonten speichert die Konten in einer Datei.
     */
    private static void speichereKonten() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(DATEI_NAME))) {
            for (Konto konto : kontenListe) {
                bw.write(konto.toCSV() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Die Klasse Konto repräsentiert ein Bankkonto.
     */
    abstract static class Konto {
        protected int kontonummer;
        protected String kontoinhaber;
        protected double saldo;
        protected String anlegedatum;
        protected String iban; // Hinzufügen des iban Attributs

        public Konto(int kontonummer, String kontoinhaber, double saldo, String anlegedatum, String iban) {
            this.kontonummer = kontonummer;
            this.kontoinhaber = kontoinhaber;
            this.saldo = saldo;
            this.anlegedatum = anlegedatum;
            this.iban = iban; // Initialisieren des iban Attributs
        }

        public String getIBAN() { // Hinzufügen der getIBAN Methode
            return iban;
        }

        public int getKontonummer() {
            return kontonummer;
        }

        public String getKontoinhaber() {
            return kontoinhaber;
        }

        public void setKontoinhaber(String kontoinhaber) {
            this.kontoinhaber = kontoinhaber;
        }

        public double getSaldo() {
            return saldo;
        }

        public void setSaldo(double saldo) {
            this.saldo = saldo;
        }

        private static Konto findeKonto(int kontonummer) {
            for (Konto konto : kontenListe) {
                if (konto.kontonummer == kontonummer) {
                    return konto;
                }
            }
            return null;
        }

        private static void showAlert(String title, String message) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        }
        public boolean einzahlen(double betrag) {
            if (betrag > 0) {
                saldo += betrag;
                return true;
            }
            return false;
        }

        public boolean auszahlen(double betrag) {
            if (betrag > 0 && saldo >= betrag) {
                saldo -= betrag;
                return true;
            }
            return false;
        }

        public abstract String toCSV();

        @Override
        public String toString() {
            return "Konto{" +
                    "kontonummer=" + kontonummer +
                    ", kontoinhaber='" + kontoinhaber + '\'' +
                    ", saldo=" + saldo +
                    ", anlegedatum='" + anlegedatum + '\'' +
                    '}';
        }
    }

    static class Girokonto extends Konto {
        public Girokonto(int kontonummer, String kontoinhaber, double saldo, String anlegedatum, String iban) {
            super(kontonummer, kontoinhaber, saldo, anlegedatum, iban);
        }

        @Override
        public String toCSV() {
            return kontonummer + ";" + kontoinhaber + ";" + saldo + ";Girokonto;" + anlegedatum + ";" + iban;
        }

        @Override
        public String toString() {
            return "Girokonto{" +
                    "kontonummer=" + kontonummer +
                    ", kontoinhaber='" + kontoinhaber + '\'' +
                    ", saldo=" + saldo +
                    ", anlegedatum='" + anlegedatum + '\'' +
                    ", iban='" + iban + '\'' +
                    '}';
        }
    }

    static class Sparkonto extends Konto {
        public Sparkonto(int kontonummer, String kontoinhaber, double saldo, String anlegedatum, String iban) {
            super(kontonummer, kontoinhaber, saldo, anlegedatum, iban);
        }

        @Override
        public String toCSV() {
            return kontonummer + ";" + kontoinhaber + ";" + saldo + ";Sparkonto;" + anlegedatum + ";" + iban;
        }

        @Override
        public String toString() {
            return "Sparkonto{" +
                    "kontonummer=" + kontonummer +
                    ", kontoinhaber='" + kontoinhaber + '\'' +
                    ", saldo=" + saldo +
                    ", anlegedatum='" + anlegedatum + '\'' +
                    ", iban='" + iban + '\'' +
                    '}';
        }
    }
}
