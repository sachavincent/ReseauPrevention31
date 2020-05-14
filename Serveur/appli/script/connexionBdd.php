<?php

header('Content-type: application/json');

try{
    $bdd = new PDO('mysql:host=localhost;dbname=prevention31', 'root', 'root', array(PDO::ATTR_ERRMODE => PDO::ERRMODE_EXCEPTION));
}
catch(Exception $e){
    $retour['error'] = 102;
    echo json_encode($retour);
    die();
}

?>