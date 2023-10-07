#include <iostream>
#include <cstdlib>
#include <ctime>
#include <algorithm> // f�r die Funktion std::sort

using namespace std;

const int MAX_ANZAHL_SPIELE = 10; // maximale Anzahl von Spielen
const int MAX_WOCHE = 5; // maximale Anzahl von Wochen

int main() {
    // Deklaration der Variablen
    int anzahlSpiele, auswahlTag, auswahlWoche;
    int spiele[MAX_ANZAHL_SPIELE];
    double preis;

    // Eingabe der Anzahl der Spiele
    do {
        cout << "Wie viele Spiele moechten Sie spielen? (1-" << MAX_ANZAHL_SPIELE << "): ";
        cin >> anzahlSpiele;
    } while (anzahlSpiele < 1 || anzahlSpiele > MAX_ANZAHL_SPIELE);

    // Eingabe des Tages (Samstag, Mittwoch oder beide)
    do {
        cout << "Fuer welchen Tag moechten Sie spielen? (1: Samstag, 2: Mittwoch, 3: beide): ";
        cin >> auswahlTag;
    } while (auswahlTag < 1 || auswahlTag > 3);

    // Eingabe der Woche (1-5)
    do {
        cout << "Fuer welche Woche moechten Sie spielen? (1-" << MAX_WOCHE << "): ";
        cin >> auswahlWoche;
    } while (auswahlWoche < 1 || auswahlWoche > MAX_WOCHE);

    // Generierung der Zahlen f�r jedes Spiel
    srand(time(NULL)); // Initialisierung des Zufallszahlengenerators
    for (int i = 0; i < anzahlSpiele; i++) {
        cout << "Spiel " << (i+1) << ": ";
        for (int j = 0; j < 6; j++) {
            int zahl = rand() % 49 + 1; // Zufallszahl zwischen 1 und 49 generieren
            spiele[j] = zahl;
            cout << zahl << " ";
        }
        cout << endl;

        // Sortieren der Zahlen mit Bubblesort
        for (int k = 0; k < 5; k++) {
            for (int l = k+1; l < 6; l++) {
                if (spiele[k] > spiele[l]) {
                    int temp = spiele[k];
                    spiele[k] = spiele[l];
                    spiele[l] = temp;
                }
            }
        }

        // Ausgabe der sortierten Zahlen
        cout << "Sortierte Zahlen: ";
        for (int k = 0; k < 6; k++) {
            cout << spiele[k] << " ";
        }
        cout << endl;
    }

    // Berechnung des Preises
    if (auswahlTag == 1) {
        preis = 1.00 * anzahlSpiele;
    } else if (auswahlTag == 2) {
        preis = 1.50 * anzahlSpiele;
    } else {
        preis = 2.00 * anzahlSpiele;
    }

    // Ausgabe des Preises und der gew�hlten Optionen
    cout << "Preis: " << preis << " Euro" << endl;
    cout << "Gewaehlte Optionen: ";
   if (auswahlTag == 1) {
		cout << "Samstag";
	} else if (auswahlTag == 2) {
		cout << "Mittwoch";
	} else {
		cout << "Samstag und Mittwoch";
	}
	cout << " - Woche " << auswahlWoche << endl;

	// Programm wiederholen?
	char wiederholen;
	do {
    	cout << "Moechten Sie das Programm wiederholen? (j/n): ";
    	cin >> wiederholen;
	} while (wiederholen != 'j' && wiederholen != 'n');

	if (wiederholen == 'j') {
    	system("cls"); // Bildschirm leeren (funktioniert nur unter Windows)
    	main(); // Programm wiederholen
	}

return 0;
}

