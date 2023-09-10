#include <iostream>
#include <cstdlib>
#include <stdlib.h>
/* run this program using the console pauser or add your own getch, system("pause") or input loop */
using namespace std;

int main(int argc, char** argv) {
	double umsatz, jahre, wachstum, checker ;
	int neustart ; 
	do {
	system("cls");
		cout << "Umsatzrechner\n" ;
		cout << "Bitte geben Sie ihren Umsatz ein.\n"; 
		cin >> umsatz ; 
		checker = umsatz * 2;
		cout << "Bitte geben Sie ihren Umsatzwachstum an.\n";
		cin >> wachstum; 
		wachstum = (100 + wachstum) / 100;
		jahre = 0; 
		do {
			umsatz = wachstum * umsatz ; 
			jahre++;
		} 
		while (checker >= umsatz);
		cout << "Man braucht " << jahre <<" bis man seinen Umsatz verdoppelt hat.\n";
		cout << "Nach " << jahre << " Jahren hat man einen Umsatz von " << umsatz << "\n";
		cout << "Moechten Sie das Programm neustarten? [1=y]\n"; 
		cin >> neustart;
	}
	while (neustart == 1);
	return 0;
}
