#include <iostream>
#include <cstdlib>
#include <stdlib.h>
#include <math.h>
/* run this program using the console pauser or add your own getch, system("pause") or input loop */
using namespace std;



int main(int argc, char** argv) {
	double kapital,zinssatz;
	int laufzeit,restart; 
	restart = 0;
	do{
		system("cls"); // S�ubert das Terminal vor dem Neustart bzw. beim starten
		cout << "************************************\n";
		cout << "	Sparplanrechner\n";
		cout << "************************************\n";
		cout << "Bitte geben Sie ihren Startkapital an.\n"; 
		cin >> kapital;
		cout << "Bitte w�hlen Sie eine Laufzeit aus.\n";
		cin >> laufzeit;
// Startet die Abfrage von Jahren mit der jeweiligen Zinss�zten.
	if (laufzeit <= 2){ 
		zinssatz = 2.8;
			kapital = kapital * pow(1+zinssatz / 100, laufzeit);
	} else if (laufzeit <=10){
		zinssatz = 3.8;
			kapital = kapital * pow(1+zinssatz / 100, laufzeit);
	} else if(laufzeit > 10){
		zinssatz = 4.5;
			kapital = kapital * pow(1+zinssatz / 100, laufzeit);
	}
	cout << "Dein Kapital ist " << kapital<< " Euro nach " << laufzeit << " Jahre. \n"; //Ausgabe von Ergebniss
	cout << "M�chtest du gerne das Programm neustarte? [Ja = 1]\n"; // Abfrage Programm neustart
	cin >> restart;
	  }
	while(restart == 1); // Neustarten nur wenn User 1 ein gibt
	
	return 0;
}
