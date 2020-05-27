<?php
include 'connexionBDD.php';

// faille XSS
$objet = strip_tags($_POST['input-objet-conseil']);
$texte = strip_tags($_POST['texte']);

if (!(isset($objet) AND isset($texte)) OR empty($objet) OR empty($texte)){
    $success = false;
    header('Location: ../force/demandes.php?e=new_conseil');
    exit();
} else {
    $success = true;
    $request = $bdd->prepare('INSERT INTO `Conseil`(`objet`, `texte`) VALUES (?,?)');
    $request->execute(array($objet, $texte));
    header('Location: ../force/demandes.php?e=prive&m=none');
    exit();
}
?>