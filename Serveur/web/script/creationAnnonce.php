<?php

session_start();
include 'connexionBdd.php';

function listeDestinataire($formulaire, $bdd){

    if (isset($formulaire['toutes-activites']) AND isset($formulaire['toutes-communes']) AND isset($formulaire['toutes-zones'])){
        $requeteListeDestinataire=$bdd->query('SELECT idUtilisateur FROM Utilisateur');

    } elseif (isset($formulaire['toutes-activites']) AND isset($formulaire['toutes-communes'])){
        $requeteListeDestinataire=$bdd->prepare('SELECT idUtilisateur FROM Utilisateur WHERE secteur = ? OR secteur = ? OR secteur = ?');
        $requeteListeDestinataire->execute(array($formulaire['secteur1'], $formulaire['secteur2'], $formulaire['secteur3']));

    } elseif(isset($formulaire['toutes-activites']) AND isset($formulaire['toutes-zones'])){
        $requeteListeDestinataire=$bdd->prepare('SELECT idUtilisateur FROM Utilisateur WHERE idCommune = ? OR idCommune = ? OR idCommune = ?');
        $requeteListeDestinataire->execute(array($formulaire['commune1'], $formulaire['commune2'], $formulaire['commune3']));

    } elseif(isset($formulaire['toutes-communes']) AND isset($formulaire['toutes-zones'])){
        $requeteListeDestinataire=$bdd->prepare('SELECT idUtilisateur FROM Utilisateur WHERE codeAct = ? OR codeAct = ? OR codeAct = ?');
        $requeteListeDestinataire->execute(array($formulaire['activite1'], $formulaire['activite2'], $formulaire['activite3']));

    } elseif(isset($formulaire['toutes-activites'])){
        $requeteListeDestinataire = $bdd->prepare(' SELECT idUtilisateur FROM Utilisateur 
                                                    WHERE (idCommune = ? OR idCommune = ? OR idCommune = ?)
                                                    AND (secteur = ? OR secteur = ? OR secteur = ?)');
        $requeteListeDestinataire->execute(array(   $formulaire['commune1'], $formulaire['commune2'], $formulaire['commune3'],
                                                    $formulaire['secteur1'], $formulaire['secteur2'], $formulaire['secteur3']));

    } elseif(isset($formulaire['toutes-communes'])){
        $requeteListeDestinataire = $bdd->prepare(' SELECT idUtilisateur FROM Utilisateur 
                                                    WHERE (codeAct = ? OR codeAct = ? OR codeAct = ?)
                                                    AND (secteur = ? OR secteur = ? OR secteur = ?)');
        $requeteListeDestinataire->execute(array(   $formulaire['activite1'], $formulaire['activite2'], $formulaire['activite3'],
                                                    $formulaire['secteur1'], $formulaire['secteur2'], $formulaire['secteur3']));
    
    } elseif(isset($formulaire['toutes-zones'])){
        $requeteListeDestinataire = $bdd->prepare(' SELECT idUtilisateur FROM Utilisateur 
                                                    WHERE (idCommune = ? OR idCommune = ? OR idCommune = ?)
                                                    AND (codeAct = ? OR codeAct = ? OR codeAct = ?)');
        $requeteListeDestinataire->execute(array(   $formulaire['commune1'], $formulaire['commune2'], $formulaire['commune3'],
                                                    $formulaire['activite1'], $formulaire['activite2'], $formulaire['activite3']));
    } else{
        $requeteListeDestinataire = $bdd->prepare(' SELECT idUtilisateur FROM Utilisateur 
                                                    WHERE (idCommune = ? OR idCommune = ? OR idCommune = ?)
                                                    AND (codeAct = ? OR codeAct = ? OR codeAct = ?)
                                                    AND (secteur = ? OR secteur = ? OR secteur = ?)');
        $requeteListeDestinataire->execute(array(   $formulaire['commune1'], $formulaire['commune2'], $formulaire['commune3'],
                                                    $formulaire['activite1'], $formulaire['activite2'], $formulaire['activite3'],
                                                    $formulaire['secteur1'], $formulaire['secteur2'], $formulaire['secteur3']));   
    }
    return $requeteListeDestinataire;
}

//Envoyer
if (isset($_POST['envoyer'])){
    if (!(isset($_POST['input-objet-annonce']) AND isset($_POST['texte']))){
        $success = false;
    } else {
        $success = true;
    
        //Creation de l'annonce
        $requeteCreationAnnonce = $bdd->prepare('INSERT INTO `annonce`(`objet`, `texte`) VALUES (?,?)');
        $requeteCreationAnnonce->execute(array($_POST['input-objet-annonce'], $_POST['texte']));
    
        //Recuperationde l'id de l'annonce
        $requeteIdAnnonce = $bdd->prepare('SELECT idAnnonce FROM Annonce WHERE objet = ? AND texte = ?');
        $requeteIdAnnonce->execute(array($_POST['input-objet-annonce'], $_POST['texte']));
        $idAnnonce = ($requeteIdAnnonce->fetch()['idAnnonce']);

        $requeteListeDestinataire = listeDestinataire($_POST, $bdd);
    
       //Creation des lignes de destinationMessage
        $nbDest = 0;
        while ($resultat = $requeteListeDestinataire->fetch()){
            $requeteAjoutDest = $bdd->prepare('INSERT INTO `destinationannonce`(`idAnnonce`, `idUtilisateur`) VALUES (?,?)');
            $requeteAjoutDest->execute(array($idAnnonce, $resultat['idUtilisateur']));
            $nbDest++;
        }
        echo 'Message envoye a : ' . $nbDest . ' utilisateurs';
    } 
    unset($_SESSION['nbDestinataire']);
} //NbDestinataire
elseif (isset($_POST['nbDestinataire'])){
    $requeteListeDestinataire = listeDestinataire($_POST, $bdd);
    
    $_SESSION['nbDestinataire'] = count($requeteListeDestinataire->fetchAll());

    $success = true;

    header('Location: ../force/demandes.php?e=new_annonce');
    exit();
} else {
    $success = false;
}
?>