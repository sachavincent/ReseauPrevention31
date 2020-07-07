<?php

// affichage des demandes
if (!empty($_SESSION[$onglet])) {
    $nb = 0;
    if (isset($_SESSION[$onglet]))
        $nb = count($_SESSION[$onglet]);
    
    // parcours de tous les onglets
    for ($i = 0; $i < $nb; $i++) {
        
        $affiche               = $_SESSION[$onglet][$i];
        $msgOuvert             = $_SESSION[$onglet][$i]['ouvert'];
        // date dans les onglets
        $dateSQL               = substr($affiche['created_at'], 0, 10);
        $reverseDate           = implode('/', array_reverse(explode('-', $dateSQL)));
        $date                  = "<time id='date-reception'>" . $reverseDate . "</time>";
        // affichage de l'onglet demande
        $nomPrenom             = $affiche['nomUtilisateur'] . ' ' . $affiche['prenomUtilisateur'];
        $onglet_affiche        = $date . '<h9>' . trunc($nomPrenom, 18) . '</h9><br>' . '<h10>' . trunc($affiche['nomSociete'], 22) . ', ' . 'secteur ' . $affiche['secteur'] . '</h10>';
        $onglet_affiche_clique = $date . '<h9>' . trunc($nomPrenom, 18) . '</h9><br>' . '<h10>' . trunc($affiche['nomSociete'], 22) . ', ' . 'secteur ' . $affiche['secteur'] . '</h10>';

        // si msg est selectionné
        if ($i == $_GET['m'] && $_GET['m'] != 'none') {
            $onglet_class = 'onglet-msg-selected';
        }
        // si message non lu et non selectionné : 0 = message non ouvert, 1 = message ouvert
        elseif ($msgOuvert == 0) {
            $onglet_class = 'onglet-non-lu';
        } 
        // si msg non lu et non selectionné
        else {
            $onglet_class = 'onglet-msg';
        }

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
        }

        // affichage de l'onglet
        echo " <div class=" . $onglet_class . " onclick=window.location.href='demandes.php?e=" . $lien . "&m=" . $i . "'" . ">$onglet_affiche</div> ";
    }
}

?>