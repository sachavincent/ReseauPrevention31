<?php
include 'connexionBDD.php';
if (!(isset($_POST['input-objet-conseil']) AND isset($_POST['texte'])) OR empty($_POST['input-objet-conseil']) OR empty($_POST['texte'])){
    $success = false;
    header('Location: ../force/demandes.php?e=new_conseil');
    exit();
} else {
    $success = true;
    $request = $bdd->prepare('INSERT INTO `Conseil`(`objet`, `texte`) VALUES (?,?)');
    $request->execute(array($_POST['input-objet-conseil'], $_POST['texte']));
    header('Location: ../force/demandes.php?e=prive&m=none');
    exit();
}

?>