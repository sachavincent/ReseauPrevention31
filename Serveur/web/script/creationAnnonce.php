<?php
include 'connexionBdd.php';
if (!(isset($_POST['objet']) AND isset($_POST['texte']))){
    $success = false;
} else {
    $success = true;

    //Creation de l'annonce
    $requeteCreationAnnonce = $bdd->prepare('INSERT INTO `annonce`(`objet`, `texte`) VALUES (?,?)');
    $requeteCreationAnnonce->execute(array($_POST['objet'], $_POST['texte']));

    //Recuperationde l'id de l'annonce
    $requeteIdAnnonce = $bdd->prepare('SELECT idAnnonce FROM Annonce WHERE objet = ? AND texte = ?');
    $requeteIdAnnonce->execute(array($_POST['objet'], $_POST['texte']));
    $idAnnonce = ($requeteIdAnnonce->fetch()['idAnnonce']);

    if ($_POST['toutes_activites'] AND $_POST['toutes_communes'] AND $_POST['toutes_zones']){
        $requeteListeDestinataire=$bdd->query('SELECT idUtilisateur FROM Utilisateur');

    } elseif ($_POST['toutes_activites'] AND $_POST['toutes_communes']){
        $requeteListeDestinataire=$bdd->prepare('SELECT iUtilisateur FROM Utilisateur WHERE idSecteur = ? OR idSecteur = ? OR idSecteur = ?');
        $requeteListeDestinataire->execute(array($_POST['secteur1'], $_POST['secteur2'], $_POST['secteur3']));

    } elseif($_POST['toutes_activites'] AND $_POST['toutes_zones']){
        $requeteListeDestinataire=$bdd->prepare('SELECT idUtilisateur FROM Utilisateur WHERE idCommune = ? OR idCommune = ? OR idCommune = ?');
        $requeteListeDestinataire->execute(array($_POST['commune1'], $_POST['commune2'], $_POST['commune3']));

    } elseif($_POST['toutes_communes'] AND $_POST['toutes_zones']){
        $requeteListeDestinataire=$bdd->prepare('SELECT idUtilisateur FROM Utilisateur WHERE idActivite = ? OR idActivite = ? OR idActivite = ?');
        $requeteListeDestinataire->execute(array($_POST['activite1'], $_POST['activite2'], $_POST['activite3']));

    } elseif($_POST['toutes_activites']){
        $requeteListeDestinataire = $bdd->prepare(' SELECT idUtilisateur FROM Utilisateur 
                                                    WHERE (idCommune = ? OR idCommune = ? OR idCommune = ?)
                                                    AND (secteur = ? OR secteur = ? OR secteur = ?)');
        $requeteListeDestinataire->execute(array(   $_POST['commune1'], $_POST['commune2'], $_POST['commune3'],
                                                    $_POST['secteur1'], $_POST['secteur2'], $_POST['secteur3']));

    } elseif($_POST['toutes_communes']){
        $requeteListeDestinataire = $bdd->prepare(' SELECT idUtilisateur FROM Utilisateur 
                                                    WHERE (codeAct = ? OR codeAct = ? OR codeAct = ?)
                                                    AND (secteur = ? OR secteur = ? OR secteur = ?)');
        $requeteListeDestinataire->execute(array(   $_POST['activite1'], $_POST['activite2'], $_POST['activite3'],
                                                    $_POST['secteur1'], $_POST['secteur2'], $_POST['secteur3']));
    
    } elseif($_POST['toutes_zones']){
        $requeteListeDestinataire = $bdd->prepare(' SELECT idUtilisateur FROM Utilisateur 
                                                    WHERE (idCommune = ? OR idCommune = ? OR idCommune = ?)
                                                    AND (codeAct = ? OR codeAct = ? OR codeAct = ?)');
        $requeteListeDestinataire->execute(array(   $_POST['commune1'], $_POST['commune2'], $_POST['commune3'],
                                                    $_POST['activite1'], $_POST['activite2'], $_POST['activite3']));
    } else{
        $requeteListeDestinataire = $bdd->prepare(' SELECT idUtilisateur FROM Utilisateur 
                                                    WHERE (idCommune = ? OR idCommune = ? OR idCommune = ?)
                                                    AND (codeAct = ? OR codeAct = ? OR codeAct = ?)
                                                    AND (secteur = ? OR secteur = ? OR secteur = ?)');
        $requeteListeDestinataire->execute(array(   $_POST['commune1'], $_POST['commune2'], $_POST['commune3'],
                                                    $_POST['activite1'], $_POST['activite2'], $_POST['activite3'],
                                                    $_POST['secteur1'], $_POST['secteur2'], $_POST['secteur3']));   
    }

   //Creation des lignes de destinationMessage
    $nbDest = 0;
    while ($resultat = $requeteListeDestinataire->fetch()){
        $requeteAjoutDest = $bdd->prepare('INSERT INTO `destinationannonce`(`idAnnonce`, `idUtilisateur`) VALUES (?,?)');
        $requeteAjoutDest->execute(array($idAnnonce, $resultat['idUtilisateur']));
        $nbDest++;
    }
    echo $nbDest;
}
?>