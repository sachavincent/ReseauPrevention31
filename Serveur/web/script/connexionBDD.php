<?php
/* Connexion à la bdd */
try{
    $bdd = new PDO('mysql:host=localhost:3308;dbname=prevention31', 'root', '', array(PDO::ATTR_ERRMODE => PDO::ERRMODE_EXCEPTION));
}
catch(Exception $e){
    $retour['success'] = false;
    $retour['message'] = 'erreur connexion a la base de donnee';
    echo json_encode($retour);
    die();
}
?>
