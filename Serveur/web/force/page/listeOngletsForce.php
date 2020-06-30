<?php
// affichage des demandes
if (!empty($_SESSION[$onglet])) {
    $nb = 0;
    // Parcours des elements de la variable $_SESSION[$onglet]
    if (isset($_SESSION[$onglet])) 
        $nb = count($_SESSION[$onglet]);

    for ($i  = 0;$i < $nb;$i++) {

        $affiche = $_SESSION[$onglet][$i];
        if ($onglet == 'FIL_DE_DISCUSSION') {
            // recuperation du dernier msg
            $dernierMsg            = count($affiche['message']) - 1;
            $msgOuvert             = $affiche['message'][$dernierMsg]['ouvert'];
            $affiche['created_at'] = $affiche['message'][$dernierMsg]['created_at'];
            // elements de l'onglet
            $objet = trunc($affiche['objet'], 15);
            $texte = trunc($affiche['message'][$dernierMsg]['texte'], 35);
        } else {
            $msgOuvert = $affiche['ouvert'];
            // elements de l'onglet
            $objet = trunc($affiche['objet'], 15);
            $texte = trunc($affiche['texte'], 35);
        }
        // date dans les onglets
        $dateSQL     = substr($affiche['created_at'], 0, 10);
        $reverseDate = implode('/', array_reverse(explode('-', $dateSQL)));
        $date        = "<time id='date-reception'>" . $reverseDate . "</time>";
        // contenu de l'onglet
        $onglet_affiche        = $date . '<h9>' . $objet . '</h9><br>' . '<h10>' . $texte . '</h10>';
        $onglet_affiche_clique = $date . '<h9>' . $objet . '</h9><br>' . '<h10>' . $texte . '</h10>';

        // si selection d'un onglet
        if ($_GET['m'] != 'none') {
            // changement msg lu -> msg non lu
            switch ($_GET['e']) {
                case 'prive':
                    priveNonLu($_GET['m']);
                break;
                case 'annonce':
                    annonceNonLu($_GET['m']);
                break;
                case 'conseil':
                    conseilNonLu($_GET['m']);
                break;
            }
            // chargement des messages non ouverts (non lus)
            if ($msgOuvert == 0) {
                if ($i == $_GET['m']) {
                    echo " <div class='onglet-msg-selected' onclick=window.location.href='demandes.php?e=" . $lien . "&m=" . $i . "'" . ">$onglet_affiche_clique</div> ";
                } else {
                    echo " <div class='onglet-non-lu' onclick=window.location.href='demandes.php?e=" . $lien . "&m=" . $i . "'" . ">$onglet_affiche</div> ";
                }
                // chargement des messages ouverts (lus)
            }
            else {
                if ($i == $_GET['m']) {
                    echo " <div class='onglet-msg-selected' onclick=window.location.href='demandes.php?e=" . $lien . "&m=" . $i . "'" . ">$onglet_affiche_clique</div> ";
                } else {
                    echo " <div class='onglet-msg' onclick=window.location.href='demandes.php?e=" . $lien . "&m=" . $i . "'" . ">$onglet_affiche</div> ";
                }
            }
            // si pas de selection d'un onglet
        }
        else {
            if ($msgOuvert == 0) {
                echo " <div class='onglet-non-lu' onclick=window.location.href='demandes.php?e=" . $lien . "&m=" . $i . "'" . ">$onglet_affiche</div> ";
            } else {
                echo " <div class='onglet-msg' onclick=window.location.href='demandes.php?e=" . $lien . "&m=" . $i . "'" . ">$onglet_affiche</div> ";
            }
        }
    }
}

?>
