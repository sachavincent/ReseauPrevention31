<?php
session_start();
//Pour les testes avec Postman
$_SESSION['chambre'] = 'CA';

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

if (empty($_POST['id']) OR empty($_POST['mdp'])){
    $retour['success'] = false;
    $retour['message'] = 'Il manque des infos';
} else {
    //Recuperation des valeurs de la bdd 
    $requete = $bdd->prepare('SELECT id_gestionnaire, mdp_gestionnaire, chambre FROM Gestionnaire WHERE id_gestionnaire = ?');
    $requete->execute(array($_POST['id']));
    $info_gestionnaireDansBDD = $requete->fetch();
    
    $retour['success'] = true;

    //Verification de la cx
    if (!isset($info_gestionnaireDansBDD['id_gestionnaire'])){
        $retour['success'] = false;
        $retour['message'] = 'Utilisateur inconnu';   
    }
    elseif ($_POST['mdp'] != $info_gestionnaireDansBDD['mdp_gestionnaire']){
        $retour['success'] = false;
        $retour['message'] = 'Mdp incorrect';    
    }
    elseif ($_SESSION['chambre'] != $info_gestionnaireDansBDD['chambre']){
        $retour['success'] = false;
        $retour['message'] = 'Pas de droit d\'acces pour cette chambre';
    }
}
echo json_encode($retour);
?>