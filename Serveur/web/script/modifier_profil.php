<?php
session_start();
/* Connexion à la bdd */
include("connexionBDD.php");

/* ================================================ */

// traitement des chambres
if ($_SESSION['chambre'] == 'CCI' OR $_SESSION['chambre'] == 'CA' OR $_SESSION['chambre'] == 'CMA') {

    if (empty($_POST['mdp'])){
        $retour['success'] = false;
        $retour['message'] = 'Il manque des infos';
    } else {
        // Test du mdp
        $requeteMdp = $bdd->prepare('SELECT mdpGestionnaire FROM Gestionnaire WHERE idGestionnaire = ?');
        $requeteMdp->execute(array($_SESSION['id']));
        $mdpA = $requeteMdp->fetch();
        $mdp = $mdpA['mdpGestionnaire'];

        // mdp incorrect
        if (!password_verify($_POST['mdp'],$mdp) AND !empty($_POST['mdp'])){
            $retour['success'] = false;
            $retour['message'] = 'mdp incorrect';
        } else {
            $retour['success'] = true;
            $retour['message'] = 'Info modifiee(s)';
            $infos = array('nom', 'prenom', 'mail');

            foreach($infos as $info) {
                // securisation faille XSS
                $infosXSS = strip_tags($_POST[$info]);
                if (!empty($infosXSS) && ($infosXSS != $_SESSION[$info])){
                    switch ($info) {
                        case 'nom' :
                            $requeteModifierInfo = $bdd->prepare('UPDATE `Gestionnaire` SET nomGestionnaire = ? WHERE idGestionnaire = ?');
                        break;
                        case 'prenom' :
                            $requeteModifierInfo = $bdd->prepare('UPDATE `Gestionnaire` SET prenomGestionnaire = ? WHERE idGestionnaire = ?');
                        break;
                        case 'mail' :
                            $requeteModifierInfo = $bdd->prepare('UPDATE `Gestionnaire` SET mail = ? WHERE idGestionnaire = ?');
                        break;
                    }
                    $requeteModifierInfo->execute(array($infosXSS, $_SESSION['id']));
                }
            }
             // Cas photo profil 
             if(isset($_FILES['photoProfil'])){ 
                $dossier = '../images/';
                if(move_uploaded_file($_FILES['photoProfil']['tmp_name'], $dossier . $_SESSION['id'] . 'c.jpeg')){ 
                    echo 'Upload effectué avec succès !';
                } else {
                    echo 'Echec de l\'upload !';
                }
            }

            // Cas changement mdp
            if (!empty($_POST['nouveauMdp'])){
                if (empty($_POST['confirmationMdp']) OR $_POST['nouveauMdp'] != $_POST['confirmationMdp']){
                    $retour['success'] = false;
                    $retour['message'] = 'Les mdp ne correspondent pas';
                } else {
                    $requeteModifierMdp = $bdd->prepare('UPDATE `Gestionnaire` SET mdpGestionnaire = ? WHERE idGestionnaire = ?');
                    $requeteModifierMdp->execute(array(password_hash($_POST['nouveauMdp'], PASSWORD_DEFAULT), $_SESSION['id']));
                }
            }
        } 
    }

    if ($retour['success']) {
        $requeteInfo = $bdd->prepare('SELECT nomGestionnaire, prenomGestionnaire, mail FROM Gestionnaire WHERE idGestionnaire = ?');
        $requeteInfo->execute(array($_SESSION['id']));
        $info_gestionnaireDansBDD = $requeteInfo->fetch();
        // maj des infos de session
        $_SESSION['nom']    = $info_gestionnaireDansBDD['nomGestionnaire'];
        $_SESSION['prenom'] = $info_gestionnaireDansBDD['prenomGestionnaire'];
        $_SESSION['mail']   = $info_gestionnaireDansBDD['mail'];
        // refresh la fenetre
        header('Location: ../chambre/profil-ch.php?return=success&e=profil');
        exit();
    }
    else {
        // refresh la fenetre
        header('Location: ../chambre/profil-ch.php?return=error&e=profil');
        exit();
    }
}

/* ================================================ */

// traitement des forces de l'ordre
else {

    if (empty($_POST['mdp'])){
        $retour['success'] = false;
        $retour['message'] = 'Il manque des infos';
    } else {
        // Test du mdp
        $requeteMdp = $bdd->prepare('SELECT mdpForce FROM ForceDeLOrdre WHERE idForce = ?');
        $requeteMdp->execute(array($_SESSION['id']));
        $mdpA = $requeteMdp->fetch();
        $mdp  = $mdpA['mdpForce'];

        // mdp incorrect
        if (!password_verify($_POST['mdp'],$mdp) AND !empty($_POST['mdp'])){
            $retour['success'] = false;
            $retour['message'] = 'mdp incorrect';
        } else {
            $retour['success'] = true;
            $retour['message'] = 'Info modifiee(s)';
            $infos = array('nom', 'prenom', 'mail');

            foreach($infos as $info) {
                $infosXSS = strip_tags($_POST[$info]);
                if (!empty($infosXSS) && ($infosXSS != $_SESSION[$info])){
                    switch ($info) {
                        case 'nom' :
                            $requeteModifierInfo = $bdd->prepare('UPDATE `ForceDeLOrdre` SET nomForce = ? WHERE idForce = ?');
                        break;
                        case 'prenom' :
                            $requeteModifierInfo = $bdd->prepare('UPDATE `ForceDeLOrdre` SET prenomForce = ? WHERE idForce = ?');
                        break;
                        case 'mail' :
                            $requeteModifierInfo = $bdd->prepare('UPDATE `ForceDeLOrdre` SET mail = ? WHERE idForce = ?');
                        break;
                    }
                    $requeteModifierInfo->execute(array($infosXSS, $_SESSION['id']));
                }
            }
             // Cas photo profil 
             if(isset($_FILES['photoProfil'])){ 
                $dossier = '../images/';
                if(move_uploaded_file($_FILES['photoProfil']['tmp_name'], $dossier . $_SESSION['id'] . 'f.jpeg')){ 
                    echo 'Upload effectué avec succès !';
                } else {
                    echo 'Echec de l\'upload !';
                }
            }

            // Cas changement mdp
            if (!empty($_POST['nouveauMdp'])){
                if (empty($_POST['confirmationMdp']) OR $_POST['nouveauMdp'] != $_POST['confirmationMdp']){
                    $retour['success']  = false;
                    $retour['message']  = 'Les mdp ne correspondent pas';
                } else {
                    $requeteModifierMdp = $bdd->prepare('UPDATE `ForceDeLOrdre` SET mdpForce = ? WHERE idForce = ?');
                    $requeteModifierMdp->execute(array(password_hash($_POST['nouveauMdp'], PASSWORD_DEFAULT), $_SESSION['id']));
                }
            }
        } 
    }

    if ($retour['success']) {
        $requeteInfo = $bdd->prepare('SELECT nomForce, prenomForce, mail FROM ForceDeLOrdre WHERE idForce = ?');
        $requeteInfo->execute(array($_SESSION['id']));
        $info_gestionnaireDansBDD = $requeteInfo->fetch();
        // maj des infos de session
        $_SESSION['nom']    = $info_gestionnaireDansBDD['nomForce'];
        $_SESSION['prenom'] = $info_gestionnaireDansBDD['prenomForce'];
        $_SESSION['mail']   = $info_gestionnaireDansBDD['mail'];
        // refresh la fenetre
        header('Location: ../force/profil-fo.php?return=success&e=profil');
        exit();
    }
    else {
        // refresh la fenetre
        header('Location: ../force/profil-fo.php?return=error&e=profil');
        exit();
    }
}

?>
