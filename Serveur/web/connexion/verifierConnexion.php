<?php
session_start();
/* Connexion à la bdd */
include ("../script/connexionBDD.php");

$id   = strip_tags($_POST['id']);
$pass = strip_tags($_POST['mdp']);

/* ================================================ */

if (empty($id) or empty($pass)) {
    $retour['success'] = false;
    $retour['message'] = 'Il manque des infos';
}

else {
    // Forces de l'ordre
    if ($_GET['chambre'] == 'G' or $_GET['chambre'] == 'P') {

        //Recuperation des valeurs de la bdd
        $requete = $bdd->prepare('SELECT * FROM `ForceDeLOrdre` WHERE idForce = ?');
        $requete->execute(array($id));
        $info_ForceeDansBDD = $requete->fetch();
        $retour['success'] = true;

        //Verification de la cx
        if (!isset($info_ForceeDansBDD['idForce'])) {
            $retour['success'] = false;
            $retour['message'] = 'Utilisateur inconnu';
        }
        elseif ($pass != $info_ForceeDansBDD['mdpForce']) {
            $retour['success'] = false;
            $retour['message'] = 'Mdp incorrect';
        }
        elseif ($_SESSION['chambre'] != $info_ForceeDansBDD['force']) {
            $retour['success'] = false;
            $retour['message'] = 'Pas de droit d\'acces pour cette force';
        }

        // si tout est bon, ouverture de la page
        if ($retour['success']) {
            // incrementation de la variable nbConnexion de la bdd
            $requete = $bdd->prepare('UPDATE ForceDeLOrdre SET nbConnexion = ? WHERE idForce = ?');
            $requete->execute(array(
                $info_ForceeDansBDD['nbConnexion'] + 1,
                $id
            ));
            //Definition des variables de session
            $_SESSION['id']     = $info_ForceeDansBDD['idForce'];
            $_SESSION['nom']    = $info_ForceeDansBDD['nomForce'];
            $_SESSION['prenom'] = $info_ForceeDansBDD['prenomForce'];
            $_SESSION['mail']   = $info_ForceeDansBDD['mail'];
            $_SESSION['mdp']    = $info_ForceeDansBDD['mdpForce'];
            $_SESSION['nbConnexion'] = $info_ForceeDansBDD['nbConnexion'] + 1;
            header('Location: ../force/demandes.php?e=prive&m=none');
            exit();
        } else {
            // Si connexion failed = affichage msg
            $_SESSION['connexion'] = true;
            header('Location: identification.php?chambre=' . $_SESSION['chambre']);
            exit();
        }
    }

    //Chambre
    elseif ($_GET['chambre'] == 'CMA' or $_GET['chambre'] == 'CCI' or $_GET['chambre'] == 'CA') {

        //Recuperation des valeurs de la bdd
        $requete = $bdd->prepare('SELECT * FROM Gestionnaire WHERE idGestionnaire = ?');
        $requete->execute(array($id));
        $info_gestionnaireDansBDD = $requete->fetch();

        $retour['success'] = true;

        //Verification de la cx
        if (!isset($info_gestionnaireDansBDD['idGestionnaire'])) {
            $retour['success'] = false;
            $retour['message'] = 'Utilisateur inconnu';
        }
        elseif ($pass != $info_gestionnaireDansBDD['mdpGestionnaire']) {
            $retour['success'] = false;
            $retour['message'] = 'Mdp incorrect';
        }
        elseif ($_SESSION['chambre'] != $info_gestionnaireDansBDD['chambre']) {
            $retour['success'] = false;
            $retour['message'] = 'Pas de droit d\'acces pour cette chambre';
        }

        // si tout est bon, ouverture de la page
        if ($retour['success']) {
            $_SESSION['id']     = $info_gestionnaireDansBDD['idGestionnaire'];
            $_SESSION['nom']    = $info_gestionnaireDansBDD['nomGestionnaire'];
            $_SESSION['prenom'] = $info_gestionnaireDansBDD['prenomGestionnaire'];
            $_SESSION['mail']   = $info_gestionnaireDansBDD['mail'];
            $_SESSION['mdp']    = $info_gestionnaireDansBDD['mdpGestionnaire'];
            header('Location: ../chambre/demandes.php?e=attente&m=none');
            exit();
        } else {
            // Si connexion failed = affichage msg
            $_SESSION['connexion'] = true;
            header('Location: identification.php?chambre=' . $_SESSION['chambre']);
            exit();
        }
    }
}

/* ================================================ */
?>
