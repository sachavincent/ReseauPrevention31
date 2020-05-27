<?php
include 'connexionBDD.php';

if (!(isset($_GET['idFil']) AND isset($_POST['reponse-msg'])) OR (empty($_POST['reponse-msg']))){
    $success = false;
} else {
    $requete = $bdd->prepare('INSERT INTO `MessagePrive`(`idFilDeDiscussion`, `texte`, `emetteur`) VALUES (?,?,0)');
    $requete->execute(array($_GET['idFil'], $_POST['reponse-msg']));
    $success = true;
}

header('Location: ../force/demandes.php?e=prive&m='.$_GET['m']);
exit();
?>