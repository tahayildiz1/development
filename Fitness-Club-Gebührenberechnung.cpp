#include <iostream>
#include <cstdlib>

using namespace std;

int main() {
	int jahre, mitglieder;
	double kosten = 40, prozent = 1;
	
	cout << "Fitnessstudio Beitragsrechner" << endl;
	cout << "Wie lang sind bist du schon Mitglied? " << endl;
	cin >> jahre;
	cout << "Wie viele Familienmitglieder sind angemeldet? " << endl;
	cin >> mitglieder;
	
	if(jahre > 5){
		prozent = prozent - 0.05;
		cout << "Du bist länger als 5 Jahre, deswegen bekommst du 5% Rabatt" << endl;
	}
	if(mitglieder == 2){
		prozent = prozent - 0.05;
		cout << "Du hast 2 Familienmitglieder, deswegen bekommst du 5% Rabatt" << endl;
	}
	else{
		if(mitglieder == 3){
			prozent = prozent - 0.15;
			cout << "Du hast 3 Familienmitglieder, deswegen bekommst du 15% Rabatt" << endl;
		}
		else{
			if(mitglieder >= 4){
				prozent = prozent - 0.2;
				cout << "Du hast 4 Familienmitglieder, deswegen bekommst du 20% Rabatt" << endl;
			}
		}
	}
	
	kosten = kosten * prozent;
	cout << "Du musst " << kosten << " Euro zahlen.";
}
