<?php
$servername = "localhost";
$dbname="tasktracker";
$username = "root";
$password = "";



if(isset($_POST['KORISNICKO_IME']))  {
     $userName=$_POST['KORISNICKO_IME'];  
}

else {
     echo("nesto nije ok sa korisnickim imenom!");
     $output="-1";                    echo $output;
     break;
}



$output = [];
try {
    $conn = new PDO("mysql:host=$servername;dbname=$dbname", $username, $password);
    $conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
    $stmt = $conn->prepare("SELECT *  FROM korisnik WHERE KORISNICKO_IME='$userName' AND VIDLJIVO=1");
    $stmt->execute(); 
    $result = $stmt->fetchAll(PDO::FETCH_BOTH); 
    
    foreach($result as $r){
        $output[] = $r['IME'];
        $output[] = $r['PREZIME'];
        $output[] = $r['JMBG'];
        $output[] = $r['BR_LK'];
        $output[] = $r['ADRESA'];
        $output[] = $r['TELEFON'];
        $output[] = $r['EMAIL'];
        $output[] = $r['KORISNICKO_IME'];
        $output[] = $r['LOZINKA'];
        $output[] = $r['DATUM_ZAPOSLENJA'];
        $output[] = $r['TIPKORISNIKA_ID'];
    }
    
    $output = implode('|t',$output);
    echo $output;
    
} catch(PDOException $e) {
    echo "Error: " . $e->getMessage();
}
$conn = null;
?> 