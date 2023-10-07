#include <iostream>
#include <cstdlib>
#include <cmath>
#include <math.h>
# define M_PIl 3.14159 // PI wird auf die Zahl 3.14159 definiert


using namespace std;

double flRechteck(double a, double b){	
	double flache = a * b;
	cout << "Die Flaeche betraegt :" << flache << "\n";
}

double flQuadrat(double a){
	double flache = a * a;
	cout << "Die Flaeche betraegt :" << flache << "\n";
}

double flDreieck(double g, double h){
	double flache = 0.5 * g * h;
	cout << "Die Flaeche betraegt :" << flache << "\n";
}

double flKreis(double r){
	double flache = M_PIl * pow(r,2);
	cout << "Die Flaeche betraegt :" << flache << "\n";
}

/*Call By Reference habe ich versucht hier einzubauen.
Jedoch macht es hier keinen Sinn, dies anzuwenden.
Die Werte werden dann vertauscht und bei einer Volumenberechung bekommt man 
dann ganz andere Ergebnisse. Z.B.: R=10 H=15 V=4710 und bei R=15 H=10 V=7065
Somit verfälscht man dass Ergebniss. 
*/
double flZylinder(double r, double h){ 
	double volume = M_PIl * pow(r,2) * h;
	cout << "Das Volumen betraegt :" << volume << "\n";
}

int main(int argc, char** argv) {
	system("cls");
	char restart;
	int auswahl;
	double AQuadr,ARecht,BRecht,GDreieck,HDreieck,RKreis,RZyl,HZyl;
	do{
		cout << "Wilkommen in meinem Rechner\n";
		cout << "Waehle zwischen:\n 1: Rechteck\n 2: Quadrat\n 3: Dreieck\n 4: Kreis\n 5: Zylinder\n";
		cin >> auswahl;
		switch(auswahl){
			case 1:
				cout << "Bitte geben Sie die Laenge der ersten Seite an\n";
				cin >> ARecht;
				cout << "Bitte geben Sie die Laenge die zweite Seite an\n";
				cin >> BRecht;
				flRechteck(ARecht, BRecht);
				break;
			case 2:
				cout << "Bitte geben Sie die Laenge an\n";
				cin >> AQuadr;
				flQuadrat(AQuadr);
				break;	
			case 3:
				cout << "Bitte geben Sie die Laenge der Grundseite an: \n";
				cin >> GDreieck;
				cout << "Bitte geben Sie die Laenge der Hoehe an: \n";
				cin >> HDreieck;
				flDreieck(GDreieck, HDreieck);
				break;
			case 4:
				cout << "Bitte geben Sie den Radius an: \n";
				cin >> RKreis;
				flKreis(RKreis);
				break;
			case 5: 
				cout << "Bitte geben Sie den Radius an: \n";
				cin >> RZyl;
				cout << "Bitte geben Sie die Hoehe an: \n";
				cin >> HZyl;
				flZylinder(RZyl, HZyl);
				break;
			default:
				cout << "Falsche Eingabe, waehle ein Zahl zwischen 1 und 5\n";
		}
		cout << "Willst du das Programm neustarten? (Ja = j) (Nein = Beliebige Taste)\n";
		cin >> restart;
		system("cls");
	}while(restart=='j');
	return 0;
}
