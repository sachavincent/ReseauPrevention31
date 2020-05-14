<?php
session_start();
/* Connexion à la bdd */
include("../../script/connexionBDD.php");

/* ================================================ */

if (empty($_POST['id']) OR empty($_POST['mdp'])){
    $retour['success'] = false;
    $retour['message'] = 'Il manque des infos';
}

else {
    //Recuperation des valeurs de la bdd 
    $requete = $bdd->prepare('SELECT idGestionnaire, mdpGestionnaire, chambre, nomGestionnaire, prenomGestionnaire, mail FROM Gestionnaire WHERE idGestionnaire = ?');
    $requete->execute(array($_POST['id']));
    $info_gestionnaireDansBDD = $requete->fetch();

    $retour['success'] = true;

    //Verification de la cx
    if (!isset($info_gestionnaireDansBDD['idGestionnaire'])){
        $retour['success'] = false;
        $retour['message'] = 'Utilisateur inconnu';   
    }
    elseif ($_POST['mdp'] != $info_gestionnaireDansBDD['mdpGestionnaire']){
        $retour['success'] = false;
        $retour['message'] = 'Mdp incorrect';    
    }
    elseif ($_SESSION['chambre'] != $info_gestionnaireDansBDD['chambre']){
        $retour['success'] = false;
        $retour['message'] = 'Pas de droit d\'acces pour cette chambre';
    }

    // si tout est bon, ouverture de la page
    if ($retour['success']) {
        $_SESSION['id'] = $info_gestionnaireDansBDD['idGestionnaire'];
        $_SESSION['nom'] = $info_gestionnaireDansBDD['nomGestionnaire'];
        $_SESSION['prenom'] = $info_gestionnaireDansBDD['prenomGestionnaire'];
        $_SESSION['mail'] = $info_gestionnaireDansBDD['mail'];
        $_SESSION['mdp'] = $info_gestionnaireDansBDD['mdpGestionnaire'];
        include("script/chargerInfos.php");
        header('Location: ../demandes.php?e=attente&m=none');
        exit();
    }
    else {
        // <!-- Si connexion failed = affichage msg -->
        echo json_encode($retour);
        $_SESSION['connexion'] = true;
        header('Location: ../../connexion/identification.php?chambre='.$_SESSION['chambre']);
        exit();
    }
}

/* ================================================ */
?>
