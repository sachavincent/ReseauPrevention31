<?php
include 'connexionBDD.php';
if (!(isset($_POST['idFilDeDiscussion']) AND isset($_POST['texte']))){
    $success = false;
} else {
    $requete = $bdd->prepare('INSERT INTO `messageprive`(`idFilDeDiscussion`, `texte`) VALUES (?,?)');
    $requete->execute(array($_POST['idFilDeDiscussion'], $_POST['texte']));
    $success = true;
}
header('');//A completer
exit();
?>