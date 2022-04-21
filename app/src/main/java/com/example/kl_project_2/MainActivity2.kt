package com.example.kl_project_2

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.TextView
import java.lang.Math.max
import java.lang.Math.min

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)

        //Tiek definētas pogas
        //btnup - datora pogas, btndp - spēlētāja pogas
        val btnup6 = findViewById(R.id.button3) as Button
        val btnup5 = findViewById(R.id.button4) as Button
        val btnup4 = findViewById(R.id.button5) as Button
        val btnup3 = findViewById(R.id.button6) as Button
        val btnup2 = findViewById(R.id.button7) as Button
        val btnup1 = findViewById(R.id.button8) as Button
        val btndp1 = findViewById(R.id.button11) as Button
        val btndp2 = findViewById(R.id.button12) as Button
        val btndp3 = findViewById(R.id.button13) as Button
        val btndp4 = findViewById(R.id.button14) as Button
        val btndp5 = findViewById(R.id.button15) as Button
        val btndp6 = findViewById(R.id.button16) as Button
        val btnupk = findViewById(R.id.button10) as Button
        val btndpk = findViewById(R.id.button9) as Button

        //"Menu" un "Again" pogas definēšana
        val btnmenu = findViewById(R.id.button17) as Button
        val btnagain = findViewById(R.id.button18) as Button

        //TextView definēšana, kas izvada uzvarētājus un spēles gājienu
        val text1 = findViewById(R.id.textView2) as TextView
        //TextView definēšana
        val text2 = findViewById(R.id.textView3) as TextView
        val text3 = findViewById(R.id.textView4) as TextView

        //Globālais atribūts
        var bettermove = 0

        //Masīvu izveidošana. Arī sākumstāvokļa nodefinēšana šajā gadījumā gan datoram, gan spēlētājam pirmie seši
        // lauciņi tiek aizpildīti ar trim kauliņiem un īpašais laucīnš ir tukš
        //matrix - augša(computer)  matrix2 - player
        val matrix = arrayOf(3,3,3,3,3,3,0)
        val matrix2 = arrayOf(3,3,3,3,3,3,0)

        //Iegūst informāciju no iepriekšējās aktivitātes, kurš sāk
        val starts = intent.getStringExtra("starts")

        //btnpos ir funkcija, kurā ievadot pogas atribūtu atgriež masīvu, kas pirmajā indeksā pasaka kurā masīvā
        // poga atrodas(ja ir 0, tad poga atrodas datora masīvā(matrix), ja ir 1 spēlētāju(matrix2)) un ots indeks pasaka, kurš indeks tas ir tajā masīvā.
        fun btnpos(btn:Button): Array<Int>{
            if(btn == btnup1) return arrayOf(0,0)
            if(btn == btnup2) return arrayOf(0,1)
            if(btn == btnup3) return arrayOf(0,2)
            if(btn == btnup4) return arrayOf(0,3)
            if(btn == btnup5) return arrayOf(0,4)
            if(btn == btnup6) return arrayOf(0,5)
            if(btn == btndp1) return arrayOf(1,0)
            if(btn == btndp2) return arrayOf(1,1)
            if(btn == btndp3) return arrayOf(1,2)
            if(btn == btndp4) return arrayOf(1,3)
            if(btn == btndp5) return arrayOf(1,4)
            if(btn == btndp6) return arrayOf(1,5)
            return arrayOf(-1,-1)
        }

        //Kurš kuram ir gājiens spēlētājam vai datoram
        // false is player, true is computer
        var playerorcomp = true
        //starts ir iegūtā informācija no pirmās aktivitātes ja ir "P" - spēlētājs sāk, ja ir "C" dators sāk
        if(starts == "P")playerorcomp = false
        if(starts == "C")playerorcomp = true

        // Spēlēs gājienu funkcija, kas izmaina matricas vērtības
        fun move(btn: Button, array1: Array<Int>, array2: Array<Int>){

            bettermove = 0

            val array = btnpos(btn)
            //updown kurš masīvs(0 - matrix, 1-matix2)
            val updown = array[0]
            val pos = array[1]
            //Papild masīvi
            var ar1 = arrayOf(0,0,0,0,0,0,0,)
            var ar2 = arrayOf(0,0,0,0,0,0,0,)
            var btnvalue = 0

            //masīvu kopēšana
            if(updown == 0) {
                ar1 = array1
                ar2 = array2
            } else if(updown == 1) {
                ar1 = array2
                ar2 = array1
            }
            btnvalue = ar1[pos]
            ar1[pos] = 0

            //nosaka
            val b = pos+btnvalue

            //Ja gājiens beidzas savā lauciņā
            if(b <= 6){
                for(n in 1..btnvalue){
                    ar1[pos+n]++
                }
                //  ja pēdējais kauliņš iekrīt tukšā lauciņā un pretējā pretinieku lauciņā ir kauliņi viņš dabu savā īpašajā lauciņā visus tos kauliņus
                if(ar1[pos+btnvalue] == 1 && (pos+btnvalue) !=6  && ar2[5-(pos+btnvalue)] != 0){
                    ar1[6] =  ar1[6] + ar2[5-(pos+btnvalue)] + 1

                    //Pārbauda vai pretējie laiciņi ir tukši, ja nav tad pēdēja gājiena lauciņš ir tukš
                    if(ar2[5-(pos+btnvalue)] != 0)ar1[pos+btnvalue] = 0
                    //Pretinieka lauciņu pārvērš tukšu
                    ar2[5-(pos+btnvalue)] = 0

                    //Ja dators dabū pretinieka kauliņus, tas atzīmē to kā labāku gājienu
                    if(updown == 0) bettermove = 1
                    if(updown == 1) bettermove = -1
                }
            }
            //Ja gajiens beidzas pretinieku lauciņā
            if(b > 6){
                //Pieskaita nākamajiem lauciņiem +1 kauliņu līdz gājiens beidzas
                for(n in 1..6-pos){
                    ar1[pos+n]++
                }
                //pieskata pretiniekam katram lauciņam +1 kauliņu līdz gājiens beidzas
                for(n in 0..btnvalue-(6-pos)-1){
                    if(n < 6) ar2[n]++
                    if(n >= 6) ar1[n-6]++
                }

            }

            //Rezultātus iekopē īstajās matricās.
            // Datora gājienā
            if(updown == 0){
               for(n in 0..6) matrix[n] = ar1[n]
               for(n in 0..6) matrix2[n] = ar2[n]
            }
            //Rezultātus iekopē īstajās matricās.
            // Spēlētāju gājienā
            if(updown == 1){
                for(n in 0..6) matrix2[n] = ar1[n]
                for(n in 0..6) matrix[n] = ar2[n]
            }
             // Ja gājiens beidzas savā īpašajā lauciņā, tad ir papild gājiens
            // Datora gājienā
            if(updown == 0 && (pos+btnvalue) != 6) {
                playerorcomp = false
            } else if(updown == 0 && (pos+btnvalue) == 6){
                playerorcomp = true
                bettermove = 1
            }
            // Ja gājiens beidzas savā īpašajā lauciņā, tad ir papild gājiens
            // Spēlētāju gājienā
            if(updown == 1 && (pos+btnvalue) != 6){
                playerorcomp = true
            } else if(updown == 1 && (pos+btnvalue) == 6){
                playerorcomp =  false
                bettermove = -1
            }

        }
        //Funkcija, kas padara datora pogas sarkanas. Gaiši sarkanas uz īsu mirkli un pārējo laiku atsāj atsāj tumši sarkanas.
        // Šī funkcija ļauj vieglak saprast izmaiņas,kad notiek spēlēs gajiens
        // Handler().postDelayed({ }, time.toLong()) Šī funkcija ļauj uzlikt kavēšanos

        fun reddelay(btn: Button){
            var time = 0
            Handler().postDelayed({
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    btn.backgroundTintList = getColorStateList(android.R.color.holo_red_light)
                }
            }, time.toLong())

            Handler().postDelayed({
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    btn.backgroundTintList = getColorStateList(android.R.color.holo_red_dark)
                }
            }, time.toLong()+ 550)
        }
        //Funkcija, kas padara spēlētāja pogas zilas. Gaiši zilas uz īsu mirkli un pārējo laiku atsāj tumši zilas.
        // Šī funkcija ļauj vieglak saprast izmaiņas,kad notiek spēlēs gajiens.
        //Ņemta no https://www.codegrepper.com/code-examples/kotlin/kotlin+android+how+to+delay
        fun bluedelay(btn: Button){
            var time = 0

            Handler().postDelayed({
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    btn.backgroundTintList = getColorStateList(android.R.color.holo_blue_light)
                }
            }, time.toLong())

            Handler().postDelayed({
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    btn.backgroundTintList = getColorStateList(android.R.color.holo_blue_dark)
                }
            }, time.toLong()+ 550)
        }

        var time = 0
        //funkcija, kas kavē teksa parādīšanos uz pogas
        fun delaytext(btn: Button, string: String,i: Int){
             //https://www.codegrepper.com/code-examples/kotlin/kotlin+android+how+to+delay
            Handler().postDelayed({
                btn.text = string
                if(i == 1) bluedelay(btn)
                if(i == 2) reddelay(btn)
            }, time.toLong())
        }
        //Nosaka uzvarētāju. Izvadot skaitli 10(dators uzvar),-10(spēlētājs uzvar),0(neizšķirts)
        fun winner(array1: Array<Int>, array2: Array<Int>): Int {
            var x = 0
            var y = 0
            //Pārbauda kura matricas lauciņi ir tukši, izņemot īpāos lauciņus
            for(i in 0..5){
                if(array1[i] == 0) x++
                if(array2[i] == 0) y++
            }

            //Ja datora lauciņi ir tukši, spēlētāja pārbauda un saskaita atlikušos kauliņus un pieskaita īpāšajā lauciņā
            if(x==6){
                for(n in 0..5){
                    matrix2[6] = matrix2[6] + matrix2[n]
                    matrix2[n] = 0
                }
            }
            //Ja spēlētāja lauciņi ir tukši, datora lauciņus pārbauda un saskaita atlikušos kauliņus un pieskaita īpāšajā lauciņā
            if(y==6){
                for(n in 0..5){
                    matrix[6] = matrix[6] + matrix[n]
                    matrix[n] = 0
                }
            }

            //Pārbauda uzvarētājus, kuram ir lielāks skaitlis īpašajā lauciņa tas uzvar
            if((x==6 || y==6) && array2[6] > array1[6]) return -10
            if((x==6 || y==6) && array2[6] < array1[6]) return 10
            if((x==6 || y==6) && array2[6] == array1[6]) return 0
            return 2
        }
        //turn funkcija pasaka kuram ir gājiens un iekrāso spēlētāja vai datora teksta lauciņu, lai vieglāk saprastu kuram ir gājiens
        fun turn(PorC: Boolean){
            if(PorC == true){
                text1.text = "Computer turn"
                //Links, kas palīdzēja saprast kā nokrāsot pogas - https://stackoverflow.com/questions/32671004/how-to-change-the-color-of-a-button
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    text2.backgroundTintList = getColorStateList(android.R.color.holo_red_light)
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    text3.backgroundTintList = getColorStateList(android.R.color.black)
                }
            }
            if(PorC == false){
                text1.text = "Player turn"
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    text3.backgroundTintList = getColorStateList(android.R.color.holo_blue_light)
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    text2.backgroundTintList = getColorStateList(android.R.color.black)
                }
            }

            //Uzvarētāja teksts tiek iekrāsots oranžā krāsā
            if(winner(matrix,matrix2) == -10){
                text1.text = "Player won"
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    text3.backgroundTintList = getColorStateList(android.R.color.holo_orange_dark)
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    text2.backgroundTintList = getColorStateList(android.R.color.black)
                }
            }
            if(winner(matrix,matrix2) == 10){
                text1.text = "Computer won"
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    text2.backgroundTintList = getColorStateList(android.R.color.holo_orange_dark)
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    text3.backgroundTintList = getColorStateList(android.R.color.black)
                }
            }
            if(winner(matrix,matrix2) == 0){
                text1.text = "Draw"
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    text2.backgroundTintList = getColorStateList(android.R.color.holo_orange_dark)
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    text3.backgroundTintList = getColorStateList(android.R.color.holo_orange_dark)
                }
            }
        }

        //Šī funkcija izmaina pogu tekstu, tiem kuriem ir izmainijušās vērtības. Lai vieglāk saprastu gājienus
        // ir uzlikts teksta kavēšanās, kas lēnām izvada pogu vērtību izmaiņas
        fun arraytobtn(array: Array<Int>, array2: Array<Int>){

            time = 0
            turn(playerorcomp)

            if(btndp1.text != array2[0].toString()){
                time = time + 250
                delaytext(btndp1,array2[0].toString(),1)
            }
            if(btndp2.text != array2[1].toString()) {
                time = time + 250
                delaytext(btndp2,array2[1].toString(),1)
            }
            if(btndp3.text!= array2[2].toString()){
                time = time + 250
                delaytext(btndp3,array2[2].toString(),1)
            }
            if(btndp4.text != array2[3].toString()) {
                time = time + 250
                delaytext(btndp4,array2[3].toString(),1)
            }
            if(btndp5.text != array2[4].toString()){
                time = time + 250
                delaytext(btndp5,array2[4].toString(),1)
            }
            if(btndp6.text!= array2[5].toString()){
                time = time + 250
                delaytext(btndp6,array2[5].toString(),1)
            }
            if(btndpk.text != array2[6].toString()){
                time = time + 250
                delaytext(btndpk,array2[6].toString(),1)
            }
            if(btnup1.text!= array[0].toString()){
                time = time + 250
                delaytext(btnup1,array[0].toString(),2)
            }
            if(btnup2.text != array[1].toString()){
                time = time + 250
                delaytext(btnup2,array[1].toString(),2)
            }
            if(btnup3.text != array[2].toString()){
                time = time + 250
                delaytext(btnup3,array[2].toString(),2)
            }
            if(btnup4.text != array[3].toString()){
                time = time + 250
                delaytext(btnup4,array[3].toString(),2)
            }
            if(btnup5.text != array[4].toString()){
                time = time + 250
                delaytext(btnup5,array[4].toString(),2)
            }
            if(btnup6.text != array[5].toString()){
                time = time + 250
                delaytext(btnup6,array[5].toString(),2)
            }
            if(btnupk.text != array[6].toString()){
                time = time + 250
                delaytext(btnupk,array[6].toString(),2)
            }
        }

        //Kad tiek aktivizēta ši aktivitāte, tiek automātiski ievadīti visas vērtības
        arraytobtn(matrix,matrix2)


        //funkcija kurā ievadōt skaitli no 0 līdz 5 izvada datora pogas no 1 līdz 6
        fun compbtn(int: Int): Button{
            if(int == 0) return btnup1
            if(int == 1) return btnup2
            if(int == 2) return btnup3
            if(int == 3) return btnup4
            if(int == 4) return btnup5
            if(int == 5) return btnup6
            return btnup1
        }
        //funkcija kurā ievadōt skaitli no 0 līdz 5 izvada spēlētāja pogas no 1 līdz 6
        fun compbtn2(int: Int): Button{
            if(int == 0) return btndp1
            if(int == 1) return btndp2
            if(int == 2) return btndp3
            if(int == 3) return btndp4
            if(int == 4) return btndp5
            if(int == 5) return btndp6
            return btndp1
        }

        // palīg masīvi
        var clone1 = arrayOf(0,0,0,0,0,0,0)
        var clone2 = arrayOf(0,0,0,0,0,0,0)

        // funkcija minimax, kad ir izsaukta izveido spēles koku un iedot katrai virsotnei vērtējumu(10-dators uzvar, -10 spēlētājs uzvar, 0 neizšķirts)
        fun minimax(array1: Array<Int>, array2: Array<Int>,player: Boolean): Int{

            // pārbauda uzvarētāju un izvada rezultātu ja ir
            var score = winner(array1,array2)
            if(score == 10) return score
            if(score== -10) return score
            if(score== 0) return score

            // Ja ir datora gājiens pārbauda visus iespējamos gājienus un novērtē tā gājiena vērtību
            if(player) {
                var best = -1000
                //Apskata katru gājienu
                for (n in 0..5) {
                    if (matrix[n] != 0) {
                        //iekopē pirms gājiena vērtības
                        for (i in 0..6) clone1[i] = array1[i]
                        for (i in 0..6) clone2[i] = array2[i]
                        //veic gājienu
                        move(compbtn(n), array1, array2)
                        // atkārto funkciju, kamēr izvada uzvarētāju
                        var score = minimax(array1, array2, playerorcomp)
                        //Atgriež pirms gājiena vērtības
                        for (i in 0..6) array1[i] = clone1[i]
                        for (i in 0..6) array2[i] = clone2[i]
                        //saglabā maksimālo vērtību
                        best = max(score, best)
                    }
                }
                return best
                //Spēlētāja gājiens. pārbauda visus iespējamos gājienus un novērtē tā gājiena vērtību
            } else {
                var best = 1000
                for (n in 0..5) {
                    if (matrix2[n] != 0) {
                        for (i in 0..6) clone1[i] = array1[i]
                        for (i in 0..6) clone2[i] = array2[i]
                        move(compbtn2(n), array1, array2)
                        var score = minimax(array1,array2, playerorcomp)
                        for (i in 0..6) array1[i] = clone1[i]
                        for (i in 0..6) array2[i] = clone2[i]
                        //izvada minimālo vērtību
                        best = min(score, best)
                    }
                }
                return best
            }
        }

        //extra ir funkcija, kas izmantojot palidinformāciju palīdz izvēlēties labāko gājienu
        // Jo ar minimax funkciju nepietika, lai noteiktu labāko gājienu
       fun extra(btn: Button): Int{
           if(bettermove == 1)return 5 else return 0
           if(bettermove == -1)return -5 else return 0
       }

        //bestmove funkcija novērtē katras datora pogas(gājiena) vērtību un izvada labāko gājienu
        fun bestmove(): Int{
            //Papild masīvi
            var matrixclone = arrayOf(0,0,0,0,0,0,0)
            var matrixclone2 = arrayOf(0,0,0,0,0,0,0)

            var bestVal = -1000
            var bestMove = 0

            //Tiek apskatītas katrs iespejamais gajiens un novērtēts ar minimax funkciju un extra funkciju
            for(n in 0..5){
                if(matrix[n] != 0){
                    //Matricas tiek nokopētas
                    for(n in 0..6) matrixclone[n] = matrix[n]
                    for(n in 0..6) matrixclone2[n] = matrix2[n]
                    //tiek veikts gājiens
                    move(compbtn(n),matrix,matrix2)
                    //nosaka gājiena vērtību
                    var mVal =extra(compbtn(n)) + minimax(matrix,matrix2,playerorcomp)
                    //Iekopē īstajās matricās pirms gājiena vērtības
                    for(i in 0..6)matrix[i] = matrixclone[i]
                    for(i in 0..6)matrix2[i] = matrixclone2[i]
                    //Saglabā lielāko vērtību un tā pogu skaitu
                    if(mVal > bestVal){
                        bestMove = n
                        bestVal = mVal
                    }
                }
            }
            //Izvada pogas numuru, kuram ir labākais vērtējjums
            return bestMove

        }

        //Šī funkcja ļauj datoram veikt papild gājienus, ja tādi ir. Ar kavēšanos
        fun MultMoves(){
            Handler().postDelayed({
            if(playerorcomp == true){
                    move(compbtn(bestmove()),matrix,matrix2)
                MultMoves()
                    arraytobtn(matrix,matrix2)
            }
        }, 2000)
        }


        //šī funkcija kavē datora gājienus
        fun btndelay(){
            Handler().postDelayed({
                MultMoves()
                arraytobtn(matrix,matrix2)
            }, 3000)
        }
        //Ja dotoes sāk spēli ši funkcija veic gājienu
        Handler().postDelayed({
            MultMoves()
            arraytobtn(matrix,matrix2)
        }, time.toLong() + 1000)

        //Kad uz pogas uzspiež tad tā izpilda gājienu, atjaunināt pogu tekstus un veikt datora gājienu
        btndp1.setOnClickListener{
            if(btndp1.text != "0"){
                move(btndp1,matrix,matrix2)
                arraytobtn(matrix,matrix2)
                btndelay()
            }
        }
        //Kad uz pogas uzspiež tad tā izpilda gājienu, atjaunināt pogu tekstus un veikt datora gājienu
        btndp2.setOnClickListener{
            if(btndp2.text != "0"){
                move(btndp2,matrix,matrix2)
                arraytobtn(matrix,matrix2)
                btndelay()
            }
        }
        //Kad uz pogas uzspiež tad tā izpilda gājienu, atjaunināt pogu tekstus un veikt datora gājienu
        btndp3.setOnClickListener{
            if(btndp3.text != "0"){
                move(btndp3,matrix,matrix2)
                arraytobtn(matrix,matrix2)
                btndelay()
            }
        }
        //Kad uz pogas uzspiež tad tā izpilda gājienu, atjaunināt pogu tekstus un veikt datora gājienu
        btndp4.setOnClickListener{
            if(btndp4.text != "0"){
                move(btndp4,matrix,matrix2)
                arraytobtn(matrix,matrix2)
                btndelay()
            }
        }
        //Kad uz pogas uzspiež tad tā izpilda gājienu, atjaunināt pogu tekstus un veikt datora gājienu
        btndp5.setOnClickListener{
            if(btndp5.text != "0"){
                move(btndp5,matrix,matrix2)
                arraytobtn(matrix,matrix2)
                btndelay()
            }
        }
        //Kad uz pogas uzspiež tad tā izpilda gājienu, atjaunināt pogu tekstus un veikt datora gājienu
        btndp6.setOnClickListener{
            if(btndp6.text != "0"){
                move(btndp6,matrix,matrix2)
                arraytobtn(matrix,matrix2)
                btndelay()
            }
        }
        //uzspižot uz "MENU" pogas tā aizved spēlētājus uz pirmo aktivitāti
        btnmenu.setOnClickListener{
            startActivity(Intent(this, MainActivity::class.java))
        }
        //uzspižot uz "AGAIN" pogas tā restartē aktivitāti un atceras kurš sāk spēli
        btnagain.setOnClickListener{
            val intent = Intent(this, MainActivity2::class.java)
            if(starts == "P") intent.putExtra("starts","P")
            if(starts == "C") intent.putExtra("starts","C")
            startActivity(intent)
        }
    }
}