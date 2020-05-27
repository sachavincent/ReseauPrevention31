<?php
include 'connexionBDD.php';

// faille XSS
$idFil = strip_tags($_GET['idFil']);
$reponse = strip_tags($_POST['reponse-msg']);

if (!(isset($idFil) AND isset($reponse)) OR (empty($reponse))){
    $success = false;
} else {
    $requete = $bdd->prepare('INSERT INTO `MessagePrive`(`idFilDeDiscussion`, `texte`, `emetteur`) VALUES (?,?,"FORCE")');
    $requete->execute(array($idFil, $reponse));
    $success = true;
}

header('Location: ../force/demandes.php?e=prive&m='.$_GET['m']);//A completer
exit();
?>