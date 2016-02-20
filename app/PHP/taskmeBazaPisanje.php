<?php

$servername = "localhost";
$dbname="tasktracker";
$username = "root";
$password = "";

if(isset($_POST['imeTablice']))  {
     $imeTablice=$_POST['imeTablice'];  
}
else {
     echo("nesto nije ok sa imenom tablice!");
}

try {
    $conn = new PDO("mysql:host=$servername;dbname=$dbname", $username, $password);
    // set the PDO error mode to exception
    $conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
    //echo "Connected successfully";

    if($imeTablice=="emaillog"){
	$stmt = $conn->prepare("INSERT INTO $imeTablice (EMAILLOG_ID ,DATUM)    VALUES (:a, :b)");
	$stmt->bindParam(':a', $a);
	$stmt->bindParam(':b', $b);
   	if(isset($_POST['EMAILLOG_ID'])) $a= $_POST['EMAILLOG_ID'];
      	else $a=0000;
      	if(isset($_POST['DATUM'])) $b=$_POST['DATUM'];
       	else $b=0;
   $stmt->execute();

    }

    if($imeTablice=="klijent"){
	$stmt = $conn->prepare("INSERT INTO $imeTablice (KLIJENT_ID ,NAZIV ,ADRESA, BROJ_TELEFONA,EMAIL,VIDLJIVO)    VALUES (:a, :b, :c, :d,:e,:f)");
	$stmt->bindParam(':a', $a);
	$stmt->bindParam(':b', $b);
	$stmt->bindParam(':c', $c);
	$stmt->bindParam(':d', $d);
	$stmt->bindParam(':e', $e);
	$stmt->bindParam(':f', $f);


   	if(isset($_POST['KLIJENT_ID'])) $a= $_POST['KLIJENT_ID'];
      	else $a=0000;
      	if(isset($_POST['NAZIV'])) $b=$_POST['NAZIV'];
       	else $b="default";
       	if(isset($_POST['ADRESA'])) $c=$_POST['ADRESA'];
       	else $c="default";
    	if(isset($_POST['BROJ_TELEFONA'])) $d=$_POST['BROJ_TELEFONA'];
        else $d="default";
   	if(isset($_POST['EMAIL'])) $e=$_POST['EMAIL'];
	else $e="default";
	if(isset($_POST['VIDLJIVO'])) $f=$_POST['VIDLJIVO'];
	else $f=1;
   $stmt->execute();
    }

    if($imeTablice=="korisnik"){
	$stmt = $conn->prepare("INSERT INTO $imeTablice (KORISNIK_ID ,IME ,PREZIME, JMBG, BR_LK, ADRESA, TELEFON, EMAIL, KORISNICKO_IME, LOZINKA, DATUM_ZAPOSLENJA, VIDLJIVO, TIPKORISNIKA_ID)    VALUES (:a, :b, :c, :d, :e,:f, :g, :h, :i, :j, :k, :l, :m)");
	$stmt->bindParam(':a', $a);
	$stmt->bindParam(':b', $b);
	$stmt->bindParam(':c', $c);
	$stmt->bindParam(':d', $d);
	$stmt->bindParam(':e', $e);
	$stmt->bindParam(':f', $f);	
	$stmt->bindParam(':g', $g);
	$stmt->bindParam(':h', $h);
	$stmt->bindParam(':i', $i);
	$stmt->bindParam(':j', $j);
	$stmt->bindParam(':k', $k);
	$stmt->bindParam(':l', $l);
	$stmt->bindParam(':m', $m);

	if(isset($_POST['KORISNIK_ID'])) $a=$_POST['KORISNIK_ID'];
      	else $a= 0000;
      	if(isset($_POST['IME'])) $b=$_POST['IME'];
       	else $b="default";
       	if(isset($_POST['PREZIME'])) $c=$_POST['PREZIME'];
       	else $c="default";
    	if(isset($_POST['JMBG'])) $d=$_POST['JMBG'];
        else $d="default";
   	if(isset($_POST['BR_LK'])) $e=$_POST['BR_LK'];
	else $e="default";
	if(isset($_POST['ADRESA'])) $f=$_POST['ADRESA'];
	else $f="default";
	if(isset($_POST['TELEFON'])) $g=$_POST['TELEFON'];
	else $g="default";
	if(isset($_POST['EMAIL'])) $h=$_POST['EMAIL'];
	else $h="default";
	if(isset($_POST['KORISNICKO_IME'])) $i= $_POST['KORISNICKO_IME'];
	else $i="default";
	if(isset($_POST['LOZINKA'])) $j=$_POST['LOZINKA'];
	else $j="default";
	if(isset($_POST['DATUM_ZAPOSLENJA'])) $k=$_POST['DATUM_ZAPOSLENJA'];
	else $k=0;
	if(isset($_POST['VIDLJIVO'])) $l=$_POST['VIDLJIVO'];
	else $l=1;
	if(isset($_POST['TIPKORISNIKA_ID'])) $m=$_POST['TIPKORISNIKA_ID'];
	else $m=0;
   $stmt->execute();
    }

    if($imeTablice=="obavljeniposao"){
	$stmt = $conn->prepare("INSERT INTO $imeTablice (OBAVLJENIPOSAO_ID, RASPOREDJENIZADATAK_ID ,VRSTAUSLUGE_ID, BROJSATI, DATUMUNOSA, DATUMOBAVLJANJA, OPISA, VIDLJIVO)    VALUES (:a, :b, :c, :d,:e,:f, :g, :h)");
	$stmt->bindParam(':a', $a);
	$stmt->bindParam(':b', $b);
	$stmt->bindParam(':c', $c);
	$stmt->bindParam(':d', $d);
	$stmt->bindParam(':e', $e);
	$stmt->bindParam(':f', $f);	
	$stmt->bindParam(':g', $g);
	$stmt->bindParam(':h', $h);   	

	if(isset($_POST['OBAVLJENIPOSAO_ID'])) $a=$_POST['OBAVLJENIPOSAO_ID'];
      	else $a=0000;
      	if(isset($_POST['RASPOREDJENIZADATAK_ID'])) $b=$_POST['RASPOREDJENIZADATAK_ID'];
       	else $b=0;
       	if(isset($_POST['VRSTAUSLUGE_ID'])) $c=$_POST['VRSTAUSLUGE_ID'];
       	else $c=0;
    	if(isset($_POST['BROJSATI'])) $d=$_POST['BROJSATI'];
	else $d=0;
   	if(isset($_POST['DATUMUNOSA'])) $e=$_POST['DATUMUNOSA'];
	else $e=0;
	if(isset($_POST['DATUMOBAVLJANJA'])) $f=$_POST['DATUMOBAVLJANJA'];
	else $f=1;
	if(isset($_POST['OPISA'])) $g=$_POST['OPISA'];
	else $g="default";
	if(isset($_POST['VIDLJIVO'])) $h=$_POST['VIDLJIVO'];
	else $h=1;
   $stmt->execute();
    }

if($imeTablice=="postavkamail"){
echo "prije st";
	$stmt = $conn->prepare("INSERT INTO $imeTablice (POSTAVKAMAIL_ID, ROKUNOS, ROKPREUZIMANJE, OPOMENA, OBAVIJEST)    VALUES (:a, :b, :c, :d,:e)");
echo "prije bind";
	$stmt->bindParam(':a', $a);
	$stmt->bindParam(':b', $b);
	$stmt->bindParam(':c', $c);
	$stmt->bindParam(':d', $d);
	$stmt->bindParam(':e', $e);
echo "nakon bind";

   	if(isset($_POST['POSTAVKAMAIL_ID'])) $a=$_POST['POSTAVKAMAIL_ID'];
  	else $a=0000;
        if(isset($_POST['ROKUNOS'])) $b=$_POST['ROKUNOS'];
   	else $b=0;
   	if(isset($_POST['ROKPREUZIMANJE'])) $c=$_POST['ROKPREUZIMANJE'];
      	else $c=0;
      	if(isset($_POST['OPOMENA'])) $d=$_POST['OPOMENA'];
       	else $d="default";
      	if(isset($_POST['OBAVIJEST'])) $e=$_POST['OBAVIJEST'];
       	else $e="default";
   $stmt->execute();
    }

    if($imeTablice=="radnizadatak"){
	$stmt = $conn->prepare("INSERT INTO $imeTablice (RADNIZADATAK_ID ,KLIJENT_ID ,KORISNIK_ID, BROJSERVISERA, STATUSDODJELJENOSTI, DATUMUNOSA, KRAJNJIDATUMIZVRSENJA, POTPUNODODJELJEN, STATUSIZVRSENOSTI, VIDLJIVO, OPIS, VRSTAZADATKA)    VALUES (:a, :b, :c, :d, :e,:f, :g, :h, :i, :j, :k, :l)");
	$stmt->bindParam(':a', $a);
	$stmt->bindParam(':b', $b);
	$stmt->bindParam(':c', $c);
	$stmt->bindParam(':d', $d);
	$stmt->bindParam(':e', $e);
	$stmt->bindParam(':f', $f);	
	$stmt->bindParam(':g', $g);
	$stmt->bindParam(':h', $h);
	$stmt->bindParam(':i', $i);
	$stmt->bindParam(':j', $j);
	$stmt->bindParam(':k', $k);
	$stmt->bindParam(':l', $l);

	if(isset($_POST['RADNIZADATAK_ID'])) $a=$_POST['RADNIZADATAK_ID'];
      	else $a=0000;
      	if(isset($_POST['KLIJENT_ID'])) $b=$_POST['KLIJENT_ID'];
       	else $sb=0;
       	if(isset($_POST['KORISNIK_ID'])) $c=$_POST['KORISNIK_ID'];
       	else $c=0;
    	if(isset($_POST['BROJSERVISERA'])) $d=$_POST['BROJSERVISERA'];
          	else $d=0;
   	if(isset($_POST['STATUSDODJELJENOSTI'])) $e=$_POST['STATUSDODJELJENOSTI'];
	else $e=0;
	if(isset($_POST['DATUMUNOSA'])) $f=$_POST['DATUMUNOSA'];
	else $f= "default";
	if(isset($_POST['KRAJNJIDATUMIZVRSENJA'])) $g=$_POST['KRAJNJIDATUMIZVRSENJA'];
	else $g=0;
	if(isset($_POST['POTPUNODODJELJEN'])) $h=$_POST['POTPUNODODJELJEN'];
	else $h=1;
	if(isset($_POST['STATUSIZVRSENOSTI'])) $i=$_POST['STATUSIZVRSENOSTI'];
	else $i=1;
	if(isset($_POST['VIDLJIVO'])) $j=$_POST['VIDLJIVO'];
	else $j=1;
	if(isset($_POST['OPIS'])) $k=$_POST['OPIS'];
	else $k="default";
	if(isset($_POST['VRSTAZADATKA'])) $l=$_POST['VRSTAZADATKA'];
	else $l="default";
     $stmt->execute();
  }

    if($imeTablice=="rasporedjenizadatak"){
	$stmt = $conn->prepare("INSERT INTO $imeTablice (RASPOREDJENIZADATAK_ID, RADNIZADATAK_ID , KORISNIK_ID, STATUSPRIHVACENOSTI,VIDLJIVO, DATUMPRIHVATANJA)    VALUES (:a, :b, :c, :d,:e,:f)");
	$stmt->bindParam(':a', $a);
	$stmt->bindParam(':b', $b);
	$stmt->bindParam(':c', $c);
	$stmt->bindParam(':d', $d);
	$stmt->bindParam(':e', $e);
	$stmt->bindParam(':f', $f);	

   	if(isset($_POST['RASPOREDJENIZADATAK_ID'])) $a=$_POST['RASPOREDJENIZADATAK_ID'];
      	else $a=0;
      	if(isset($_POST['RADNIZADATAK_ID'])) $b=$_POST['RADNIZADATAK_ID'];
       	else $b=0;
       	if(isset($_POST['KORISNIK_ID'])) $c=$_POST['KORISNIK_ID'];
       	else $c=0;
    	if(isset($_POST['STATUSPRIHVACENOSTI'])) $d=$_POST['STATUSPRIHVACENOSTI'];
        	else $d=1;
   	if(isset($_POST['VIDLJIVO'])) $e=$_POST['VIDLJIVO'];
	else $e=1;
	if(isset($_POST['DATUMPRIHVATANJA'])) $f=$_POST['DATUMPRIHVATANJA'];
	else $f=0;
      $stmt->execute();
 }

    if($imeTablice=="tipkorisnika"){
	$stmt = $conn->prepare("INSERT INTO $imeTablice (TIPKORISNIKA_ID , NAZIV, VIDLJIVO)    VALUES (:a, :b, :c)");
	$stmt->bindParam(':a', $a);
	$stmt->bindParam(':b', $b);
	$stmt->bindParam(':c', $c);
   	if(isset($_POST['TIPKORISNIKA_ID'])) $a=$_POST['TIPKORISNIKA_ID'];
      	else $a=0000;
      	if(isset($_POST['NAZIV'])) $b=$_POST['NAZIV'];
       	else $b="default";
      	if(isset($_POST['VIDLJIVO'])) $c=$_POST['VIDLJIVO'];
       	else $c=1;
   $stmt->execute();
    }

    if($imeTablice=="vrstausluge"){
	$stmt = $conn->prepare("INSERT INTO $imeTablice (VRSTAUSLUGE_ID , NAZIV, VIDLJIVO)    VALUES (:a, :b, :c)");
$stmt->bindParam(':a', $a);
	$stmt->bindParam(':b', $b);
	$stmt->bindParam(':c', $c);
   	if(isset($_POST['VRSTAUSLUGE_ID'])) $a=$_POST['VRSTAUSLUGE_ID'];
      	else $a=0000;
      	if(isset($_POST['NAZIV'])) $b=$_POST['NAZIV'];
       	else $b="default";
      	if(isset($_POST['VIDLJIVO'])) $c=$_POST['VIDLJIVO'];
       	else $c=1;
   $stmt->execute();
    }



    echo "New records created successfully";
    
}
catch(PDOException $e)
    {
    echo "Error: " . $e->getMessage();
    }
$conn = null;
?> 