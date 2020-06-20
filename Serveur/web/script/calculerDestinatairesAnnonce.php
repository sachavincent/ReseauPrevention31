<?php
    include 'connexionBDD.php';

    $idCommune1 = null;
    $idCommune2 = null;
    $idCommune3 = null;
    $codeActivite1 = null;
    $codeActivite2 = null;
    $codeActivite3 = null;

    //Recuperation idCommune et codeActivite 
    
    if (!empty($_POST['commune1'])){
        $commune =  explode(', ', $_POST['commune1']);
        $requeteIdCommune = $bdd->prepare('SELECT idCommune FROM Commune WHERE codePostal = ? AND commune = ?');
        $requeteIdCommune->execute(array($commune[0], $commune[1]));
        $idCommune1 = ($requeteIdCommune->fetch())['idCommune'];
    }
    if (!empty($_POST['commune2'])){
        $commune =  explode(', ', $_POST['commune2']);
        $requeteIdCommune = $bdd->prepare('SELECT idCommune FROM Commune WHERE codePostal = ? AND commune = ?');
        $requeteIdCommune->execute(array($commune[0], $commune[1]));
        $idCommune2 = ($requeteIdCommune->fetch())['idCommune'];    
    }
    if (!empty($_POST['commune3'])){
        $commune =  explode(', ', $_POST['commune3']);
        $requeteIdCommune = $bdd->prepare('SELECT idCommune FROM Commune WHERE codePostal = ? AND commune = ?');
        $requeteIdCommune->execute(array($commune[0], $commune[1]));
        $idCommune3 = ($requeteIdCommune->fetch())['idCommune'];    
    }
    if (!empty($_POST['activite1'])){
        $codeActivite1 =  explode(', ', $_POST['activite1'])[0];
    }
    if (!empty($_POST['activite2'])){
        $codeActivite2 =  explode(', ', $_POST['activite2'])[0];
    }
    if (!empty($_POST['activite3'])){
        $codeActivite3 =  explode(', ', $_POST['activite3'])[0];
    }

    //Suppression des balises html (faille XSS)
    $idCommune1 = strip_tags($idCommune1);
    $idCommune2 = strip_tags($idCommune2);
    $idCommune3 = strip_tags($idCommune3);
    $codeActivite1 = strip_tags($codeActivite1);
    $codeActivite2 = strip_tags($codeActivite2);
    $codeActivite3 = strip_tags($codeActivite3);

    if ($_POST['toutes-activites'] == 'false') $_POST['toutes-activites'] = false; 
    if ($_POST['toutes-communes'] == 'false') $_POST['toutes-communes'] = false; 
    if ($_POST['toutes-zones'] == 'false') $_POST['toutes-zones'] = false; 


    if (isset($_POST['toutes-activites']) and isset($_POST['toutes-communes']) and isset($_POST['toutes-zones'])
            AND $_POST['toutes-activites'] AND $_POST['toutes-communes'] AND $_POST['toutes-zones']) {
        $requeteListeDestinataire = $bdd->query('SELECT idUtilisateur, mail, nomUtilisateur, prenomUtilisateur FROM Utilisateur');

    } elseif (isset($_POST['toutes-activites']) and isset($_POST['toutes-communes'])
            AND $_POST['toutes-activites'] AND $_POST['toutes-communes']) {
        $requeteListeDestinataire = $bdd->prepare('SELECT idUtilisateur, mail, nomUtilisateur, prenomUtilisateur FROM Utilisateur WHERE secteur = ? OR secteur = ? OR secteur = ?');
        $requeteListeDestinataire->execute(array(
            $_POST['secteur1'],
            $_POST['secteur2'],
            $_POST['secteur3']
        ));
    } elseif (isset($_POST['toutes-activites']) and isset($_POST['toutes-zones']) 
            AND $_POST['toutes-activites'] AND $_POST['toutes-zones']) {
        $requeteListeDestinataire = $bdd->prepare('SELECT idUtilisateur, mail, nomUtilisateur, prenomUtilisateur FROM Utilisateur WHERE idCommune = ? OR idCommune = ? OR idCommune = ?');
        $requeteListeDestinataire->execute(array(
            $idCommune1,
            $idCommune2,
            $idCommune3
        ));
    } elseif (isset($_POST['toutes-communes']) and isset($_POST['toutes-zones'])
            AND $_POST['toutes-communes'] AND $_POST['toutes-zones']) {
        $requeteListeDestinataire = $bdd->prepare('SELECT idUtilisateur, mail, nomUtilisateur, prenomUtilisateur FROM Utilisateur WHERE codeAct = ? OR codeAct = ? OR codeAct = ?');
        $requeteListeDestinataire->execute(array(
            $codeActivite1,
            $codeActivite2,
            $codeActivite3
        ));
    } elseif (isset($_POST['toutes-activites'])
            AND $_POST['toutes-activites']) {
        $requeteListeDestinataire = $bdd->prepare(' SELECT idUtilisateur, mail, nomUtilisateur, prenomUtilisateur FROM Utilisateur 
                                                    WHERE (idCommune = ? OR idCommune = ? OR idCommune = ?)
                                                    AND (secteur = ? OR secteur = ? OR secteur = ?)');
        $requeteListeDestinataire->execute(array(
            $idCommune1,
            $idCommune2,
            $idCommune3,
            $_POST['secteur1'],
            $_POST['secteur2'],
            $_POST['secteur3']
        ));
    } elseif (isset($_POST['toutes-communes'])
            AND $_POST['toutes-communes']) {
        $requeteListeDestinataire = $bdd->prepare(' SELECT idUtilisateur, mail, nomUtilisateur, prenomUtilisateur FROM Utilisateur 
                                                    WHERE (codeAct = ? OR codeAct = ? OR codeAct = ?)
                                                    AND (secteur = ? OR secteur = ? OR secteur = ?)');
        $requeteListeDestinataire->execute(array(
            $codeActivite1,
            $codeActivite2,
            $codeActivite3,
            $_POST['secteur1'],
            $_POST['secteur2'],
            $_POST['secteur3']
        ));
    } elseif (isset($_POST['toutes-zones'])
            AND $_POST['toutes-zones']) {
        $requeteListeDestinataire = $bdd->prepare(' SELECT idUtilisateur, mail, nomUtilisateur, prenomUtilisateur FROM Utilisateur 
                                                    WHERE (idCommune = ? OR idCommune = ? OR idCommune = ?)
                                                    AND (codeAct = ? OR codeAct = ? OR codeAct = ?)');
        $requeteListeDestinataire->execute(array(
            $idCommune1,
            $idCommune2,
            $idCommune3,
            $codeActivite1,
            $codeActivite2,
            $codeActivite3
        ));
    } else {
        $requeteListeDestinataire = $bdd->prepare(' SELECT idUtilisateur, mail, nomUtilisateur, prenomUtilisateur FROM Utilisateur 
                                                    WHERE (idCommune = ? OR idCommune = ? OR idCommune = ?)
                                                    AND (codeAct = ? OR codeAct = ? OR codeAct = ?)
                                                    AND (secteur = ? OR secteur = ? OR secteur = ?)');
        $requeteListeDestinataire->execute(array(
            $idCommune1,
            $idCommune2,
            $idCommune3,
            $codeActivite1,
            $codeActivite2,
            $codeActivite3,
            $_POST['secteur1'],
            $_POST['secteur2'],
            $_POST['secteur3']
        ));
    }

    $listeDestinataire = $requeteListeDestinataire->fetchAll();
    
    header('Content-Type: application/json');
    echo json_encode($listeDestinataire);
?>