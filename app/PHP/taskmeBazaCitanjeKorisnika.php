<?php
$servername = "localhost";
$dbname="tasktracker";
$username = "root";
$password = "";


$output = [];
try {
    $conn = new PDO("mysql:host=$servername;dbname=$dbname", $username, $password);
    $conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
    $stmt = $conn->prepare("SELECT KORISNICKO_IME  FROM korisnik");
    $stmt->execute(); 
    $result = $stmt->fetchAll(PDO::FETCH_BOTH); 
    
    foreach($result as $r){
        $output[] = $r['KORISNICKO_IME'];
    }
    $output=implode(',', $output);
    echo $output;
} catch(PDOException $e) {
    echo "Error: " . $e->getMessage();
}
$conn = null;
?> 