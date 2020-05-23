<?php 

// affichage des demandes
if (!empty($_SESSION[$onglet])) {
    $nb = 0;
    // arcours des elements de la variable $_SESSION[$onglet]
    if (isset($_SESSION[$onglet])) $nb = count($_SESSION[$onglet]);
    for ($i=0; $i < $nb; $i++) {
        
        $affiche = $_SESSION[$onglet][$i];
        if ($onglet == 'FIL_DE_DISCUSSION') {
            // recuperation du dernier msg
            $msgOuvert = $affiche['message'][0]['ouvert'];
            $affiche['created_at'] = $affiche['message'][0]['created_at'];
            // elements de l'onglet
            $objet = trunc($affiche['objet'], 17);
            $texte = trunc($affiche['message'][0]['texte'], 17);
        } else {
            $msgOuvert = $affiche['ouvert'];
            // elements de l'onglet
            $objet = trunc($affiche['objet'], 17);
            $texte = trunc($affiche['texte'], 20);
        }
        // date dans les onglets
        $dateSQL = substr($affiche['created_at'], 0, 10);
        $reverseDate = implode('/',array_reverse  (explode('-',$dateSQL)));
        $date = "<time id='date-reception'>".$reverseDate."</time>";
        // contenu de l'onglet
        $onglet_affiche = $date.'<h9>'.$objet.'</h9>'.'<td>'.'<br/>'.$texte;
        $onglet_affiche_clique = $date.'<h10>'.$objet.'</h10>'.'<td>'.'<br/>'.$texte;

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
                    echo " <div class='onglet-msg-selected' onclick=window.location.href='demandes.php?e=".$lien."&m="
                    .$i."'".">$onglet_affiche_clique</div> ";
                } else {
                    echo " <div class='onglet-non-lu' onclick=window.location.href='demandes.php?e=".$lien."&m="
                    .$i."'".">$onglet_affiche</div> ";
                }
            // chargement des messages ouverts (lus)
            } else {
                if ($i == $_GET['m']) {
                    echo " <div class='onglet-msg-selected' onclick=window.location.href='demandes.php?e=".$lien."&m="
                    .$i."'".">$onglet_affiche_clique</div> ";
                } else {
                    echo " <div class='onglet-msg' onclick=window.location.href='demandes.php?e=".$lien."&m="
                    .$i."'".">$onglet_affiche</div> ";
                }
            }
        // si pas de selection d'un onglet
        } else {
            if ($msgOuvert == 0) {
                echo " <div class='onglet-non-lu' onclick=window.location.href='demandes.php?e=".$lien."&m="
                    .$i."'".">$onglet_affiche</div> ";
            } else {
                echo " <div class='onglet-msg' onclick=window.location.href='demandes.php?e=".$lien."&m="
                .$i."'".">$onglet_affiche</div> ";
            }
        }
    }
}

?>