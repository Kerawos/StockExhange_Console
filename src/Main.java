
public class Main {

    public static void main(String[] args) {
        Game.welcomeScreen();
        Game.start();

    }
}

/*

    User wciela sie w gracza gieldowego ktory kupuje i psrzedaje po losowych kursach akcje, zobaczymy ile uda mu sie zarobic po 10 dniach:

    - klasa akcje:
        pola:
            - nazwa akcji
            - warosc akcji
            - typ akcji (stabilna, zroznicowana, agresywna) - od tego bedzie zalezec szybkosc jej wzrostu/spadku
        metody:


    - klasa gielda:
        pola:
            - ilosc akcji
        metody:
            - zmodyfikuj kurs akcji
            - zniszcz akcje (jezeli wartosc osiagnie mniej niz minimum to zniszcz akcje)
            - dodaj nowa akcje (okresla czy jakas firma weszla na rynek)

        w mainie bedziemy operowac gra, codzien kursy beda okreslane od nowa my bedziemy sprzedawac lub kupywac akcje

 */
