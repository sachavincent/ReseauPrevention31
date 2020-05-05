<?php
header('Content-type: application/json');

//Cx a la bdd 
try{
    $bdd = new PDO('mysql:host=localhost;dbname=prevention31', 'root', 'root', array(PDO::ATTR_ERRMODE => PDO::ERRMODE_EXCEPTION));
}
catch(Exception $e){
    $retour['success'] = false;
    $retour['message'] = 'erreur connexion a la base de donnee';
    echo json_encode($retour);
    die();
}

if (empty($_POST['demande'])){
    $retour['success'] = false;
    $retour['message'] = 'Il manque des infos';
} else {
    $requeteDemande = $bdd->prepare('SELECT * FROM Utilisateur WHERE demande = ?');
    $requeteDemande->execute(array($_POST['demande']));
    $resultat = $requeteDemande->fetchAll();
    $retour['success'] = true;
    $retour['resultat'] = $resultat;
}
echo json_encode($retour);
?>