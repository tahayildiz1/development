#include <iostream>
#include <cstdlib>

/* run this program using the console pauser or add your own getch, system("pause") or input loop */
using namespace std;

int main(int argc, char** argv) {
	double netto , netto2, rabatt, mwst, betrag, mwstrabatt ;
	char kategorie ; 
	mwst = 1.19 ; 
	cout << "Hardwarerabatt Rechner\n" ;
	cout << "Bitte geben Sie Ihren Nettopreis an. \n" ;
	cin >> netto ;
	cout << "Bitte geben Sie Ihre Kategorie. \n" ; 
	cin >> kategorie ;
		switch (kategorie){
			case 'a': 
			 rabatt = 1 - 0.1 ;
			 	break;
			case 'b': 
			 rabatt = 1 - 0.12 ;
			 	break;
			case 'c': 
			 rabatt = 1 - 0.15 ;
			 	break;
			case 'd': 
			 rabatt = 1 - 0.20 ;
				 break;
			case 'e': 
			 rabatt = 1 - 0.30 ;
			 	break;
		default:
            cout << "Fehler, Eingabe nochmal �berpr�fen.";
            break;
		}
		netto2 = netto - rabatt; 
		mwstrabatt = netto2 * mwst;
		cout << "Nettobetrag:" << netto2 << "Euro\n"; 
		cout << "Rabatt:" << rabatt << "Euro\n"; 
		
		cout << "Mwst:" << mwstrabatt << "Euro\n"; 
		cout << "F�lliger Betrag:" << mwstrabatt <<"Euro\n"; 
	return 0;
}
