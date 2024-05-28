import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class Kontoverwaltung extends Application {
    private static final String DATEI_NAME = "konten.csv";
    private static List<Konto> kontenListe = new ArrayList<>();
    private static int kontonummerZaehler = 1000; // Startnummer für Kontonummern

    public static void main(String[] args) {
        ladeKonten();
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Kontoverwaltung");

        TabPane tabPane = new TabPane();

        Tab tabErstellen = new Tab("Konto erstellen", createKontoErstellenPane());
        Tab tabLoeschen = new Tab("Konto löschen", createKontoLoeschenPane());
        Tab tabAendern = new Tab("Konto ändern", createKontoAendernPane());
        Tab tabLesen = new Tab("Konten anzeigen", createKontoLesenPane());
        Tab tabDatenAnsehen = new Tab("Daten ansehen", createDatenAnsehenPane());
        Tab tabDatenAendern = new Tab("Daten ändern", createDatenAendernPane());
        Tab tabUmbuchen = new Tab("Umbuchen", createUmbuchenPane());
        Tab tabSuchen = new Tab("Suchen", createSuchenPane());
        tabPane.getTabs().addAll(tabErstellen, tabLoeschen, tabAendern, tabLesen, tabDatenAnsehen, tabDatenAendern, tabUmbuchen, tabSuchen);

        Scene scene = new Scene(tabPane, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Pane createKontoErstellenPane() {
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
                neuesKonto = new Girokonto(kontonummerZaehler++, kontoinhaber, saldo, anlegedatum);
            } else if (kontoart.equals("Sparkonto")) {
                neuesKonto = new Sparkonto(kontonummerZaehler++, kontoinhaber, saldo, anlegedatum);
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

        public void umbuchen(int vonKontonummer, int zuKontonummer, double betrag) {
            Konto vonKonto = null;
            Konto zuKonto = null;

            for (Konto konto : kontenListe) {
                if (konto.kontonummer == vonKontonummer) {
                    vonKonto = konto;
                } else if (konto.kontonummer == zuKontonummer) {
                    zuKonto = konto;
                }
            }

            if (vonKonto == null || zuKonto == null) {
                showAlert("Fehler", "Eines oder beide Konten wurden nicht gefunden.");
                return;
            }

            if (vonKonto.saldo < betrag) {
                showAlert("Fehler", "Nicht genug Geld auf dem Ausgangskonto.");
                return;
            }

            vonKonto.saldo -= betrag;
            zuKonto.saldo += betrag;
            speichereKonten();
        }

        public List<Konto> suchen(String suchbegriff) {
            List<Konto> gefundeneKonten = new ArrayList<>();

            for (Konto konto : kontenListe) {
                if (konto.kontoinhaber.contains(suchbegriff)) {
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
            Konto zuLoeschen = null;
            for (Konto konto : kontenListe) {
                if (konto.kontonummer == kontonummer) {
                    zuLoeschen = konto;
                    break;
                }
            }

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
            Konto zuAendern = null;
            for (Konto konto : kontenListe) {
                if (konto.kontonummer == kontonummer) {
                    zuAendern = konto;
                    break;
                }
            }

            if (zuAendern != null) {
                zuAendern.kontoinhaber = kontoinhaberField.getText();
                zuAendern.saldo = Double.parseDouble(saldoField.getText());
                speichereKonten();
                kontonummerField.clear();
                kontoinhaberField.clear();
                saldoField.clear();
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

        TextArea kontenTextArea = new TextArea();
        kontenTextArea.setEditable(false);
        Button ladenButton = new Button("Konten laden");
        ladenButton.setOnAction(e -> {
            kontenTextArea.clear();
            for (Konto konto : kontenListe) {
                kontenTextArea.appendText(konto + "\n");
            }
        });

        vbox.getChildren().addAll(kontenTextArea, ladenButton);

        return vbox;
    }

    private Pane createDatenAnsehenPane() {
        VBox vbox = new VBox();
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(10));

        TextField kontonummerField = new TextField();
        TextArea datenTextArea = new TextArea();
        datenTextArea.setEditable(false);

        Button ansehenButton = new Button("Daten ansehen");
        ansehenButton.setOnAction(e -> {
            int kontonummer = Integer.parseInt(kontonummerField.getText());
            Konto konto = null;
            for (Konto k : kontenListe) {
                if (k.kontonummer == kontonummer) {
                    konto = k;
                    break;
                }
            }

            if (konto != null) {
                datenTextArea.setText("Kontoinhaber: " + konto.kontoinhaber + "\nSaldo: " + konto.saldo + "\nKontoart: " + konto.kontoart + "\nAnlegedatum: " + konto.anlegedatum);
            } else {
                showAlert("Fehler", "Konto nicht gefunden.");
            }
        });

        vbox.getChildren().addAll(new Label("Kontonummer:"), kontonummerField, ansehenButton, datenTextArea);

        return vbox;
    }

    private Pane createSuchenPane() {
        VBox vbox = new VBox();
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(10));

        TextField suchbegriffField = new TextField();
        Button suchenButton = new Button("Suchen");
        TextArea ergebnisArea = new TextArea();

        suchenButton.setOnAction(e -> {
            String suchbegriff = suchbegriffField.getText();
            List<Konto> ergebnisse = suchen(suchbegriff);
            ergebnisArea.clear();
            for (Konto konto : ergebnisse) {
                ergebnisArea.appendText(konto.toString() + "\n");
            }
        });

        vbox.getChildren().addAll(new Label("Suchbegriff:"), suchbegriffField, suchenButton, ergebnisArea);

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

    private Pane createDatenAendernPane() {
        VBox vbox = new VBox();
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(10));

        TextField kontonummerField = new TextField();
        TextField neuerKontoinhaberField = new TextField();

        Button aendernButton = new Button("Daten ändern");
        aendernButton.setOnAction(e -> {
            int kontonummer = Integer.parseInt(kontonummerField.getText());
            Konto konto = null;
            for (Konto k : kontenListe) {
                if (k.kontonummer == kontonummer) {
                    konto = k;
                    break;
                }
            }

            if (konto != null) {
                konto.kontoinhaber = neuerKontoinhaberField.getText();
                speichereKonten();
                kontonummerField.clear();
                neuerKontoinhaberField.clear();
            } else {
                showAlert("Fehler", "Konto nicht gefunden.");
            }
        });

        vbox.getChildren().addAll(new Label("Kontonummer:"), kontonummerField, new Label("Neuer Kontoinhaber:"), neuerKontoinhaberField, aendernButton);

        return vbox;
    }

    private static void ladeKonten() {
        try (BufferedReader br = new BufferedReader(new FileReader(DATEI_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] daten = line.split(",");
                int kontonummer = Integer.parseInt(daten[0]);
                String kontoinhaber = daten[1];
                double saldo = Double.parseDouble(daten[2]);
                String kontoart = daten[3];
                String anlegedatum = daten[4];

                Konto konto;
                if (kontoart.equals("Girokonto")) {
                    konto = new Girokonto(kontonummer, kontoinhaber, saldo, anlegedatum);
                } else {
                    konto = new Sparkonto(kontonummer, kontoinhaber, saldo, anlegedatum);
                }

                kontenListe.add(konto);
                kontonummerZaehler = Math.max(kontonummerZaehler, kontonummer + 1);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void speichereKonten() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(DATEI_NAME))) {
            for (Konto konto : kontenListe) {
                bw.write(konto.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

class Konto {
    protected int kontonummer;
    protected String kontoinhaber;
    protected double saldo;
    protected String kontoart;
    protected String anlegedatum;

    public Konto(int kontonummer, String kontoinhaber, double saldo, String kontoart, String anlegedatum) {
        this.kontonummer = kontonummer;
        this.kontoinhaber = kontoinhaber;
        this.saldo = saldo;
        this.kontoart = kontoart;
        this.anlegedatum = anlegedatum;
    }

    public String toString() {
        return kontonummer + "," + kontoinhaber + "," + saldo + "," + kontoart + "," + anlegedatum;
    }
}

class Girokonto extends Konto {
    public Girokonto(int kontonummer, String kontoinhaber, double saldo, String anlegedatum) {
        super(kontonummer, kontoinhaber, saldo, "Girokonto", anlegedatum);
    }
}

class Sparkonto extends Konto {
    public Sparkonto(int kontonummer, String kontoinhaber, double saldo, String anlegedatum) {
        super(kontonummer, kontoinhaber, saldo, "Sparkonto", anlegedatum);
    }
}
