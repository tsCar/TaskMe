            <?php

$servername = "localhost";
$dbname="tasktracker";
$username = "root";
$password = "";

//$_POST['KORISNICKO_IME']="admin";
//$_POST['LOZINKA']="123";
if(isset($_POST['KORISNICKO_IME']))  {
     $userName=$_POST['KORISNICKO_IME'];  
}

else {
 //    echo("nesto nije ok sa korisnickim imenom!");
     $output="-1";                    echo $output;
     break;
}

if(isset($_POST['LOZINKA']))  {
     $pass=$_POST['LOZINKA'];  
}
else {
  //   echo("nesto nije ok sa lozinkom!");
     $output="-1";                    echo $output;
     break;
}


$output="";
                try {
                    $conn = new PDO("mysql:host=$servername;dbname=$dbname", $username, $password);
                    $conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
                    $stmt = $conn->prepare("SELECT  LOZINKA,TIPKORISNIKA_ID  FROM korisnik WHERE KORISNICKO_IME='$userName'");
                    $stmt->execute(); 
                    $result = $stmt->fetchAll(PDO::FETCH_BOTH); 
//var_dump($result);
                    if($result[0][0]==$pass) $output=(string)$result[0][1];
                    else $output="-1";
                   
                    echo $output;
                    }

                catch(PDOException $e)
                    {
                    echo "Error: " . $e->POSTMessage();
                    }
                $conn = null;
            ?> 