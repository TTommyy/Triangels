//Tomasz Koczar-5

import java.util.Scanner;

/* Drugie zadanie na BaCy z MP 2022
Idea rozwiazania:
Sortujemy zestaw algorytmem typu "quicksort"
Zlozonosc sortowania: srednia O(n log n) , pesymistycznie O(n^2)

Nastepnie w dwoch petlaacg ,gdzie piewsza reprezentuje najkrotszy bok
,a druga sredni szukamy binarnie pierwszego za duzego odcinka,
 z wszykich mniejszych da sie zbudowac trojkat.

Zlozonsc wyszukwania trojkatow:
 dwie petle daja nam n^2 ,a wyszukiwanie log(n)
 a wiec zlozonasc wynosi n^2 * log(n).
 */



/*Program w tescie zalaczonym w tresi zadania zwraca:
1: n= 4
2 2 3 3
(0,1,2) (0,1,3) (0,2,3) (1,2,3)
Number of triangles: 4
2: n= 6
1 1 2 2 3 3
(0,2,3) (0,4,5) (1,2,3) (1,4,5) (2,3,4) (2,3,5) (2,4,5) (3,4,5)
Number of triangles: 8
3: n= 6
1 2 2 3 3 4
(0,1,2) (0,3,4) (1,2,3) (1,2,4) (1,3,4) (1,3,5) (1,4,5) (2,3,4) (2,3,5) (2,4,5)
Number of triangles: 11
4: n= 4
1 1 1 1
(0,1,2) (0,1,3) (0,2,3) (1,2,3)
Number of triangles: 4
5: n= 4
1 1 3 4
Triangles cannot be built

Co jest zgodne z wyjsciem oczekiwanym.
 */


/**Klasa obslugujaca zestaw danych*/
class Zestaw{

    //******* Pola ********
    public int iloscOdcinkow,iloscTrojkatow;
    public int[] odcinki;
    String trojkaty;

    //********* Metody *********

    /**Konstruktor*/
    public Zestaw(Scanner sc){
        iloscTrojkatow = 0;//domyslnie, gdy znajdziemy bedziemy zwiekszac
        iloscOdcinkow = sc.nextInt();//Pobieramy ilosc odcinkow
        odcinki = new int[iloscOdcinkow];// tworzymy tablice na odcinki
        /* Pobieramy dlugosci odcinkow */
        for(int i = 0 ; i<iloscOdcinkow; i++)
            odcinki[i] = sc.nextInt();
        trojkaty="";
    }

    /**Metoda pomocznicza sortowania typu "quicksort" */
    public int  pattriton(int low, int high){

        //piwot ustawiamy na wartosc ostatniego odcinka z zakresu
        // i na pierwszy inedeks przed zakresem
        int pivot = odcinki[high], i = low - 1;

        int temp; // deklarujey zmienna pomocnicza przed petla

        // dla kazdego j z zakresu
        for(int j = low; j<high; j++) {

            //Jesli obecny element jest mniejszy/rowny niz/- pivot/owi
            if (odcinki[j] <= pivot) {
                i++; // to zwiekszamy i
                // oraz zamieniamy wartosci pod ideksem "i" i "j"

                temp = odcinki[j];
                odcinki[j] = odcinki[i];
                odcinki[i] = temp;
            }
        }
            // zamienamy wartosci pod indeksem "i+1" oraz "high"
            temp = odcinki[i+1];
            odcinki[i+1] = odcinki[high];
            odcinki[high] = temp;

            return  i+1;
        }
        /**Sortowanie typu "quicksort"
         *@param low poczatek zakresu
         *@param high koniec zakresu
         */
        public void sort(int low,int  high){

            // jesli zakres jest prawidlowy
            if( low < high){

                // podziel odpowiednio
                int pi = pattriton(low, high);

                //posortuj odzielnie dwa mniejsze zakresy
                sort(low, pi-1);
                sort(pi+1,high);
            }
        }


        /**Wypisywanie */
        public void display(){
            for(int i = 0; i < iloscOdcinkow; i++){
                System.out.print(odcinki[i]);
                if((i+1)%25 == 0 )
                        System.out.print("\n");
                else if(i!=iloscOdcinkow-1)
                    System.out.print(" ");
            }
            if(iloscOdcinkow%25 != 0)
                System.out.println();
        }

        /**Wyszukuje binarnie indeks pierwszego odcinka z ktorego razem z dwoma
         * podanymi w argumentach nie mozna zbudowac trojkata.
         * Jak nie znajdzie zwraca ilosc odcinkow */
        public int binSearch(int pierwszy, int drugi){
            /*Po pierwsze wyliczymy sobie
             jakich odcinkow szukamy*/

            int PierwszyZaDuzy = pierwszy + drugi; // bo dwa musza byc wieksze niz trzeci


            int low = 0,upp = iloscOdcinkow-1,curr,first=iloscOdcinkow;


            while(low <= upp){
                curr = (low+upp)/2;

                /*Jesli wartos odcinka jest juz zaduza to
                bedziemy szukac dalej w niszyzch indeksach*/
                if(odcinki[curr]>=PierwszyZaDuzy){
                    first=curr;
                    upp=curr-1;
                }

                /*Jesli nie jest za duza to szukamy po wyzszych indeksach*/
                else{
                    if(odcinki[curr]<PierwszyZaDuzy)
                        low = curr + 1;
                }

            }

            return first;
        }


        /**Znajduje trojkaty*/
        public void findTriangles(){
            int pierwszyZly;//przyda sie pozniej

          /*Pierwsza petla. Wartosc skryta pod indeksem jej
          iteratora bedzie pierwszym(najmnijeszym odcinkiem)
           */
            for(int pierwszy = 0; pierwszy < iloscOdcinkow-2; pierwszy++){
                /*Druga petla. Wartosc skryta pod indeksem jej
                iteratora bedzie drugim(srednim odcinkiem)
           */
                for(int drugi = pierwszy + 1; drugi < iloscOdcinkow -1;drugi++){
                    /*Znajdujemy ostatni*/
                    pierwszyZly=binSearch(odcinki[pierwszy],odcinki[drugi]);
                    //System.out.println("pierwszy zly" + pierwszyZly);
                    /*Dopisujemy znalezione trojakty do naszej listy*/
                    for(int trzeci = drugi + 1; trzeci <pierwszyZly; trzeci++ ){
                        iloscTrojkatow++;
                        if(iloscTrojkatow <= 10){
                            trojkaty+=("("+ pierwszy + "," + drugi + "," + trzeci +") ");
                        }
                    }
                }
            }


        }

}




/**Klasa glowna programu*/
public class Source {

    /*Wejscie do programu*/
    public static Scanner sc = new Scanner(System.in);

    /*Metoda Main*/
    public static void main(String[] args){

        int iloscZestawow;//pobieramy ilosc zestawow
        iloscZestawow = sc.nextInt();

        Zestaw zestaw;// zestaw danych

        /*Petla obslugujaca kazdy zestaw*/
        for(int i = 1; i<=iloscZestawow ;i++){
            /*wypelniamy zestaw*/
            zestaw = new Zestaw(sc);
            /*Sortujemy*/
            zestaw.sort(0,zestaw.iloscOdcinkow-1);

            /*Znajdujemy trojkaty*/
            zestaw.findTriangles();

            /*Prezentujemy wyniki*/
            System.out.println(i+": n= " + zestaw.iloscOdcinkow + " ");
            zestaw.display();
            //System.out.println();


            if(zestaw.iloscTrojkatow>0) {
                System.out.println(zestaw.trojkaty);
                System.out.println("Number of triangles: " + zestaw.iloscTrojkatow + " ");
            }
            else
                System.out.println("Triangles cannot be built ");

        }

    }

}
