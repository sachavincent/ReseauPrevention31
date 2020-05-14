<?php

// include("../../script/connexionBDD.php");

$categorie = array('EN_COURS', 'VALIDE', 'REFUSE');

foreach ($categorie as $cat) {
    $_SESSION[$cat] = null;

    $requete = $bdd->prepare('SELECT * FROM Utilisateur WHERE demande = ? AND chambre = ? ORDER BY created_at DESC');
    $requete->execute(array($cat, $_SESSION['chambre']));
    // echo $cat;
    while ($resultat = $requete->fetch()) {
        $_SESSION[$cat][] = $resultat;
    }
    // print_r($_SESSION[$cat]);
}

?>