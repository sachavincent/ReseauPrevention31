<?php
include 'calculerDestinatairesAnnonce.php';

$objet = strip_tags($_POST['input-objet-annonce']);
$texte = strip_tags($_POST['texte']);

if (!(isset($objet) and isset($texte)) or empty($objet) or empty($texte)) {
    $success = false;
    header('Location: ../force/demandes.php?e=new_annonce');
    exit();
}
else {
    $success = true;

    //Creation de l'annonce
    $requeteCreationAnnonce = $bdd->prepare('INSERT INTO `Annonce`(`objet`, `texte`, `nbDestinataire`) VALUES (?,?,?)');
    $requeteCreationAnnonce->execute(array(
        $objet,
        $texte,
        count($listeDestinataire)
    ));

    //Recuperationde l'id de l'annonce
    $requeteIdAnnonce = $bdd->prepare('SELECT idAnnonce FROM Annonce WHERE objet = ? AND texte = ?');
    $requeteIdAnnonce->execute(array(
        $objet,
        $texte
    ));
    $idAnnonce = ($requeteIdAnnonce->fetch() ['idAnnonce']);

    //Creation des lignes de destinationMessage
    foreach ($listeDestinataire as $destinataire) {
        $requeteAjoutDest = $bdd->prepare('INSERT INTO `DestinationAnnonce`(`idAnnonce`, `idUtilisateur`) VALUES (?,?)');
        $requeteAjoutDest->execute(array(
            $idAnnonce,
            $destinataire['idUtilisateur']
        ));
    }
    if (isset($_POST['mail'])) {
        include 'envoiMailAnnonce.php';
    }
    header('Location: ../force/demandes.php?e=prive&m=0');
    exit();
}

?>
