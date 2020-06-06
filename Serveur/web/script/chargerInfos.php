<?php
//Chambre
if ($_SESSION['chambre'] == 'CCI' or $_SESSION['chambre'] == 'CA' or $_SESSION['chambre'] == 'CMA') {

    $categorie = array(
        'EN_COURS',
        'VALIDE',
        'REFUSE'
    );

    foreach ($categorie as $cat) {
        $_SESSION[$cat] = null;
        $requete = $bdd->prepare('SELECT * FROM Utilisateur WHERE demande = ? AND chambre = ? ORDER BY created_at DESC');
        $requete->execute(array(
            $cat,
            $_SESSION['chambre']
        ));
        while ($resultat = $requete->fetch()) {
            $_SESSION[$cat][] = $resultat;
        }
    }

} 
// forces de l'ordre
else {
    unset($_SESSION['FIL_DE_DISCUSSION']);
    $i          = 0;
    $requeteFil = $bdd->query('SELECT * FROM FilDeDiscussion ORDER BY created_at DESC');
    while ($fil = $requeteFil->fetch()) {
        //Ajout de l'utilisateur a chaque fil
        $requeteUtilisateur = $bdd->prepare('SELECT `nomUtilisateur`, `prenomUtilisateur`, `secteur`, `chambre`, `nomSociete`, telephone, mail FROM Utilisateur WHERE idUtilisateur=?');
        $requeteUtilisateur->execute(array($fil['idUtilisateur']));
        $res = $requeteUtilisateur->fetch();
        $_SESSION['FIL_DE_DISCUSSION'][$i]['Utilisateur'] = $res;

        //Ajout des messages
        $requeteMessage = $bdd->prepare('SELECT * FROM MessagePrive WHERE idFilDeDiscussion = ? ORDER BY created_at ASC');
        $requeteMessage->execute(array($fil['idFilDeDiscussion']));
        while ($message = $requeteMessage->fetch()) {
            $_SESSION['FIL_DE_DISCUSSION'][$i]['message'][] = $message;
        }
        $_SESSION['FIL_DE_DISCUSSION'][$i]['objet']             = $fil['objet'];
        $_SESSION['FIL_DE_DISCUSSION'][$i]['idDernierMessage']  = $fil['idDernierMessage'];
        $_SESSION['FIL_DE_DISCUSSION'][$i]['idFilDeDiscussion'] = $fil['idFilDeDiscussion'];
        $i++;
    }

    $categorie = array(
        'ANNONCE',
        'CONSEIL',
    );

    foreach ($categorie as $cat) {
        $_SESSION[$cat] = null;
        switch ($cat) {
            case 'ANNONCE':
                $requete = $bdd->query('SELECT * FROM `Annonce` ORDER BY created_at DESC');
            break;
            case 'CONSEIL':
                $requete = $bdd->query('SELECT * FROM Conseil ORDER BY created_at DESC');
            break;
        }
        while ($resultat = $requete->fetch()) {
            $_SESSION[$cat][] = $resultat;
        }
    }
}
?>
