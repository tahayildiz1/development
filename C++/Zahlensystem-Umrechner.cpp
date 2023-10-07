#include <iostream>
#include <cstdlib>
#include <cmath>
#include <chrono>
#include <thread>
/* run this program using the console pauser or add your own getch, system("pause") or input loop */
using namespace std;

int deztobin(){
    int zahl, i, j; 
    // Eingabeaufforderung für die Dezimalzahl
    cout << "Bitte geben Sie eine Dezimalzahl ein: "; 
    cin >> zahl; 
    
    int array[100]; 
    i = 0;
    // Schleife zur Berechnung der Binärdarstellung
    while (zahl > 0) { 
        // Berechne den Rest und speichere ihn im Array
        array[i] = zahl % 2; 
        // Teile die Dezimalzahl durch 2
        zahl = zahl / 2; 
        i++; 
    } 
    // Ausgabe der Binärdarstellung
    cout << "Die binäre Darstellung lautet: "; 
    for (j = i - 1; j >= 0; j--) { 
        cout << array[j]; 
    }
}

int dectohex(){
    int zahl, i, j;
    // Eingabeaufforderung für die Dezimalzahl
    cout << "Bitte geben Sie eine Dezimalzahl ein: ";
    cin >> zahl;
    int array[100]; 
    i = 0;
    // Schleife zur Berechnung der hexadezimalen Darstellung
    while (zahl > 0) { 
        // Berechne den Rest und speichere ihn im Array
        array[i] = zahl % 16; 
        // Teile die Dezimalzahl durch 16
        zahl = zahl / 16; 
        i++; 
    } 
    // Ausgabe der hexadezimalen Darstellung
    cout << "Die hexadezimale Darstellung lautet: "; 
    for (j = i - 1; j >= 0; j--) { 
        // Überprüfen, ob der Wert einstellig ist oder nicht
        if (array[j] < 10) { 
            cout << array[j]; 
        } 
        else { 
            // Umwandeln in einen entsprechenden ASCII-Zeichen
            cout << (char)(array[j] + 55); 
        }
    } 
    return 0;
}

int bintodec(){
    int zahl, i, deci;
    zahl = 0;
    // Eingabeaufforderung für die binäre Zahl
    cout << "Bitte geben Sie eine binäre Zahl ein: \n";
    cin >> zahl;
    i = 0; 
    deci = 0;
    // Schleife zur Berechnung der Dezimalzahl
    while (zahl > 0) { 
        // Addieren des Restes der binären Zahl zur Dezimalzahl
        deci += zahl % 10 * pow(2,i); 
        // Teilen der binären Zahl durch 10
        zahl = zahl / 10; 
        i++; 
    } 
    // Ausgabe der Dezimalzahl
    cout << "Die Dezimalzahl lautet: " << deci; 
    return 0;
}

int bintohex(){
    int hex[1000];  // Array zur Speicherung des Ergebnisses in Hexadezimalform
    int i = 1, j = 0, rem, dec = 0, binaryNumber;  // Initialisierung von Variablen
    cout << "Bitte geben Sie eine binaere Zahl ein: \n";  // Eingabeaufforderung
    cin >> binaryNumber;  // Eingabe der binaeren Zahl
    while (binaryNumber > 0) {  // Konvertierung der binaeren Zahl in eine Dezimalzahl
        rem = binaryNumber % 2;
        dec = dec + rem * i;
        i = i * 2;
        binaryNumber = binaryNumber / 10;
    }
    i = 0;  // Initialisierung von i für die Konvertierung der Dezimalzahl in eine Hexadezimalzahl
    while (dec != 0){  // Konvertierung der Dezimalzahl in eine Hexadezimalzahl
        hex[i] = dec % 16;
        dec = dec / 16;
        i++;
    }
    cout<<"Das Ergebnis lautet: ";  // Ausgabe des Ergebnisses
    for (j = i - 1; j >= 0; j--){  // Ausgabe der Hexadezimalzahl in umgekehrter Reihenfolge
        if (hex[j] > 9)  // Überprüfung, ob ein Buchstabe oder eine Zahl ausgegeben werden muss
            cout<<(char)(hex[j] + 55)<<"\n";  // Konvertierung des Wertes in einen Buchstaben
        else
            cout<<hex[j];  // Ausgabe des Wertes
    }
  return 0;  // Rückgabe des Funktionswerts
}

int hextodec(){
            int Ergebnis = 0;
			int i=0;
			string Umgedreht = "";
            string zahl = 0;
            cout << "Bitte geben Sie eine hexadezimalzahl ein: "; 
			cin >> zahl;
			
			for (int i=zahl.length()-1; i >= 0; i--) //Zählergesteuerte Schleife startet bei der Länge von invert-1, so lange, bis i>=0 ist, pro durchlauf wird i um 1 verringert
			{
				Umgedreht+=zahl[i]; //Der Eingabestring wird invertiert und in die Variable 'Umgedreht' geschrieben werden
			}

			for (int i = 0; i < Umgedreht.length(); i++) 
			{
				switch (Umgedreht[i]) 
				{
					case '0': 
					{
						break;
					}
					case '1': 
					{	
						Ergebnis += pow(16, i); break;// pow = 16 ^ i 	
					}
					case '2': 
					{
						Ergebnis += 2 * pow(16, i); break; // Bsp. 2 * 16 ^ i
					}
					case '3': 
					{
						Ergebnis += 3 * pow(16, i); break;
					}
					case '4': 
					{ 
						Ergebnis += 4 * pow(16, i); break;
					}
					case '5':
					{
						Ergebnis += 5 * pow(16, i); break;
					}
					case '6': 
					{
						Ergebnis += 6 * pow(16, i); break;
					}
					case '7': 
					{
						Ergebnis += 7 * pow(16, i); break;
					}
					case '8': 
					{
						Ergebnis += 8 * pow(16, i); break;
					}
					case '9': 
					{
						Ergebnis += 9 * pow(16, i); break;
					}
					case 'A': 
					{
						Ergebnis += 10 * pow(16, i); break;
					}
					case 'B': 
					{
						Ergebnis += 11 * pow(16, i); break;
					}
					case 'C': 
					{
						Ergebnis += 12	* pow(16, i); break;
					}
					case 'D': 
					{
						Ergebnis += 13 * pow(16, i); break;
					}
					case 'E': 
					{
						Ergebnis += 14 * pow(16, i); break;
					}
					case 'F':
					{
						Ergebnis += 15 * pow(16, i); break;
					}
					case 'a': 								//Ab hier nochmal a, damit es egal ist, ob man Klein- oder Großbuchstaben eingibt 
					{
						Ergebnis += 10 * pow(16, i); break;
					}
					case 'b': 
					{
						Ergebnis += 11 * pow(16, i); break;
					}
					case 'c': 
					{
						Ergebnis += 12	* pow(16, i); break;
					}
					case 'd': 
					{
						Ergebnis += 13 * pow(16, i); break;
					}
					case 'e': 
					{
						Ergebnis += 14 * pow(16, i); break;
					}
					case 'f':
					{
						Ergebnis += 15 * pow(16, i); break;
					}
				}
		
			}
			cout << "Ihre gewuenschte Zahl im Dezimalsystem lautet: " << Ergebnis << "\n";
}
int hextobin() {
  int i = 0;
  char hex[10];
  cout << "Bitte geben Sie eine Hexadezimalzahl ein: \n";
  cin >> hex;
  cout << "Das Ergebnis lautet: ";
  while (hex[i]) {
    // Überprüfen des aktuellen Zeichens im hexadezimalen Array
    switch (hex[i]) {
      case '0':
        cout << "0000";
        break;
      case '1':
        cout << "0001";
        break;
      case '2':
        cout << "0010";
        break;
      case '3':
        cout << "0011";
        break;
      case '4':
        cout << "0100";
        break;
      case '5':
        cout << "0101";
        break;
      case '6':
        cout << "0110";
        break;
      case '7':
        cout << "0111";
        break;
      case '8':
        cout << "1000";
        break;
      case '9':
        cout << "1001";
        break;
      case 'A':
      case 'a':
        cout << "1010";
        break;
      case 'B':
      case 'b':
        cout << "1011";
        break;
      case 'C':
      case 'c':
        cout << "1100";
        break;
      case 'D':
      case 'd':
        cout << "1101";
        break;
      case 'E':
      case 'e':
        cout << "1110";
        break;
      case 'F':
      case 'f':
        cout << "1111";
        break;
      // Falls das aktuelle Zeichen keinem hexadezimalen Zeichen entspricht
      default:
        cout << "Falsche Eingabe, bitte überprüfen (" << hex[i] << ") -- ";
    }
    i++;
  }
  return 0;
}

int main(int argc, char** argv) {
  char restart; // Variable, um das Programm erneut zu starten
  do {  
    int auswahl;
    cout << "Willkommen im Zahlensystem-Rechner\n";

    cout << "1: Dezimal in Binär\n2: Dezimal in Hexadezimal\n3: Binär in Dezimal\n4: Binär in Hexadezimal\n5: Hexadezimal in Dezimal\n6: Hexadezimal in Binär\n";

    cin >> auswahl;
    switch(auswahl){
      case 1:
        // Funktionsaufruf für Dezimal-in-Binär-Umwandlung
        deztobin();
        break;
      case 2:
        // Funktionsaufruf für Dezimal-in-Hexadezimal-Umwandlung
        dectohex();
        break;
      case 3:
        // Funktionsaufruf für Binär-in-Dezimal-Umwandlung
        bintodec();
        break;
      case 4:
        // Funktionsaufruf für Binär-in-Hexadezimal-Umwandlung
        bintohex();
        break;
      case 5:
        // Funktionsaufruf für Hexadezimal-in-Dezimal-Umwandlung
        hextodec();
        break;
      case 6:
        // Funktionsaufruf für Hexadezimal-in-Binär-Umwandlung
        hextobin();
        break;
      default:
        cout << "Ups, wähle eine Zahl zwischen 1 und 6 aus.\n";
    }

    cout << "\nMöchtest du das Programm neu starten? (j=ja) oder (n=nein)\n";
    cin >> restart;
    system("cls");
  } while (restart =='j');
  return 0;
}
