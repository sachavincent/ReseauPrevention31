<?php
include 'connexionBdd.php';
if (!(isset($_POST['objet']) AND isset($_POST['texte']))){
    $success = false;
} else {
    $success = true;
    $request = $bdd->prepare('INSERT INTO `Conseil`(`objet`, `texte`) VALUES (?,?)');
    $request->execute(array($_POST['objet'], $_POST['texte']));
}
//header(...);
?>