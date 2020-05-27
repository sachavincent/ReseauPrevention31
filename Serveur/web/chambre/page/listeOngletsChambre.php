<?php

// affichage des demandes
if (!empty($_SESSION[$onglet])) {
    $nb = 0;
    if (isset($_SESSION[$onglet]))
        $nb = count($_SESSION[$onglet]);
    for ($i = 0; $i < $nb; $i++) {
        
        $affiche               = $_SESSION[$onglet][$i];
        $msgOuvert             = $_SESSION[$onglet][$i]['ouvert'];
        // date dans les onglets
        $dateSQL               = substr($affiche['created_at'], 0, 10);
        $reverseDate           = implode('/', array_reverse(explode('-', $dateSQL)));
        $date                  = "<time id='date-reception'>" . $reverseDate . "</time>";
        // affichage de l'onglet demande
        $nomPrenom             = $affiche['nomUtilisateur'] . ' ' . $affiche['prenomUtilisateur'];
        $onglet_affiche        = $date . '<h9>' . trunc($nomPrenom, 17) . '</h9>' . '<td>' . '<br/>' . trunc($affiche['nomSociete'], 19) . ', ' . 'secteur ' . $affiche['secteur'];
        $onglet_affiche_clique = $date . '<h10>' . trunc($nomPrenom, 17) . '</h10>' . '<td>' . '<br/>' . trunc($affiche['nomSociete'], 19) . ', ' . 'secteur ' . $affiche['secteur'];
        
        // si selection d'un onglet
        if ($_GET['m'] != 'none') {
            // changement msg lu -> msg non lu
            switch ($_GET['e']) {
                case 'attente':
                    attenteNonLu($_GET['m']);
                    break;
                case 'accepte':
                    valideNonLu($_GET['m']);
                    break;
                case 'refuse':
                    refuseNonLu($_GET['m']);
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
            } else {
                if ($i == $_GET['m']) {
                    echo " <div class='onglet-msg-selected' onclick=window.location.href='demandes.php?e=" . $lien . "&m=" . $i . "'" . ">$onglet_affiche_clique</div> ";
                } else {
                    echo " <div class='onglet-msg' onclick=window.location.href='demandes.php?e=" . $lien . "&m=" . $i . "'" . ">$onglet_affiche</div> ";
                }
            }
            // si pas de selection d'un onglet
        } else {
            if ($msgOuvert == 0) {
                echo " <div class='onglet-non-lu' onclick=window.location.href='demandes.php?e=" . $lien . "&m=" . $i . "'" . ">$onglet_affiche</div> ";
            } else {
                echo " <div class='onglet-msg' onclick=window.location.href='demandes.php?e=" . $lien . "&m=" . $i . "'" . ">$onglet_affiche</div> ";
            }
        }
    }
}

?>