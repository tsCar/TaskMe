<?php
$servername = "localhost";
$dbname="tasktracker";
$username = "root";
$password = "";


if(isset($_POST['IME']))  {
     $name=$_POST['IME'];  
}

else {
     echo("nesto nije ok sa imenom!");
     $output="-1"; echo $output;
     break;
}

if(isset($_POST['PREZIME']))  {
     $prezime=$_POST['PREZIME'];  
}
else {
     echo("nesto nije ok s prezimenom!");
     $output="-1";
     break;
}

if(isset($_POST['JMBG']))  {
     $jmbg=$_POST['JMBG'];  
}

else {
     echo("nesto nije ok s JMBG!");
     $output="-1";
     break;
}

if(isset($_POST['BR_LK']))  {
     $brlk=$_POST['BR_LK'];  
}
else {
     echo("nesto nije ok sa brojem osobne!");
     $output="-1";
     break;
}

if(isset($_POST['ADRESA']))  {
     $adresa=$_POST['ADRESA'];  
}

else {
     echo("nesto nije ok s adresom!");
     $output="-1";
     break;
}

if(isset($_POST['EMAIL']))  {
     $email=$_POST['EMAIL'];  
}
else {
     echo("nesto nije ok s emailom!");
     $output="-1";
     break;
}

if(isset($_POST['TELEFON']))  {
     $telefon=$_POST['TELEFON'];  
}

else {
     echo("nesto nije ok s telefonom!");
     $output="-1";
     break;
}

if(isset($_POST['KORISNICKO_IME']))  {
     $korime=$_POST['KORISNICKO_IME'];  
}

else {
     echo("nesto nije ok s kor. imenom!");
     $output="-1";
     break;
}

if(isset($_POST['LOZINKA']))  {
     $lozinka=$_POST['LOZINKA'];  
}
else {
     echo("nesto nije ok s datumom lozinkom!");
     $output="-1";
     break;
}

if(isset($_POST['DATUM_ZAPOSLENJA']))  {
     $datum=$_POST['DATUM_ZAPOSLENJA'];  
}
else {
     echo("nesto nije ok s datumom zaposlenja!");
     $output="-1";
     break;
}

if(isset($_POST['TIP_KORISNIKA']))  {
     $tip=$_POST['TIP_KORISNIKA'];  
}
else {
     echo("nesto nije ok s tipom korisnika!");
     $output="-1";
     break;
}

if(isset($_POST['STARO_IME']))  {
     $staro=$_POST['STARO_IME'];  
}
else {
     echo("nesto nije ok sa starim imenom!");
     $output="-1";
     break;
}

$output = [];
try {
    $conn = new PDO("mysql:host=$servername;dbname=$dbname", $username, $password);
    $conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
    
    $sql = "UPDATE korisnik SET IME='$name',PREZIME='$prezime',JMBG='$jmbg',BR_LK='$brlk',ADRESA='$adresa',
    TELEFON='$telefon',EMAIL='$email',KORISNICKO_IME='$korime',LOZINKA='$lozinka',DATUM_ZAPOSLENJA='$datum',
    TIPKORISNIKA_ID='$tip' WHERE KORISNICKO_IME='$staro' AND VIDLJIVO=1";
    $stmt = $conn->prepare($sql);
    $stmt->execute(); 
    echo "success";//$stmt->rowCount(); 
    
} catch(PDOException $e) {
    echo "Error: " . $e->getMessage();
}
$conn = null;
?> 