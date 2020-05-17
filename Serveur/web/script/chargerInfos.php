<?php

if ($_SESSION['chambre'] == 'CCI' OR $_SESSION['chambre'] == 'CA' OR $_SESSION['chambre'] == 'CMA') {

    $categorie = array('EN_COURS', 'VALIDE', 'REFUSE');

    foreach ($categorie as $cat) {
        $_SESSION[$cat] = null;

        $requete = $bdd->prepare('SELECT * FROM Utilisateur WHERE demande = ? AND chambre = ? ORDER BY created_at DESC');
        $requete->execute(array($cat, $_SESSION['chambre']));
        while ($resultat = $requete->fetch()) {
            $_SESSION[$cat][] = $resultat;
        }
    }

} else {

    $categorie = array('MESSAGE_PRIVE', 'ANNONCE', 'CONSEIL', 'FIL_DE_DISCUSSION');

    foreach ($categorie as $cat) {
        $_SESSION[$cat] = null;
        switch($cat){
            case 'MESSAGE_PRIVE':
                $requete = $bdd->query('SELECT * FROM MessagePrive WHERE corbeille = 0 ORDER BY created_at DESC');
            break;
            case 'ANNONCE':
                $requete = $bdd->query('SELECT * FROM `Annonce` WHERE corbeille = 0 ORDER BY created_at DESC');
            break;
            case 'CONSEIL':
                $requete = $bdd->query('SELECT * FROM Conseil WHERE corbeille = 0 ORDER BY created_at DESC');
            break;
            case 'FIL_DE_DISCUSSION':
                $requete = $bdd->query('SELECT * FROM FilDeDiscussion ORDER BY created_at DESC');
            break;
        }
        while ($resultat = $requete->fetch()) {
            $_SESSION[$cat][] = $resultat;
        }
    }
    //Chargement de la corbeille 
    $requete=$bdd->query('SELECT * FROM  `Annonce` WHERE corbeille = 1 ORDER BY dateCorbeille DESC');
    while ($resultat = $requete->fetch()) {
        $_SESSION['CORBEILLE'][] = $resultat;
    }
    $requete=$bdd->query('SELECT * FROM  `Conseil` WHERE corbeille = 1 ORDER BY dateCorbeille DESC');
    while ($resultat = $requete->fetch()) {
        $_SESSION['CORBEILLE'][] = $resultat;
    }
}
?>