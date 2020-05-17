<?php
// chargement categorie
switch ($_GET['e']) {
    // forces de l'ordre
    case 'prive':
        $onglet = "MESSAGE_PRIVE";
        $lien = 'prive';
    break;
    case 'annonce':
        $onglet = "ANNONCE";
        $lien = 'annonce';
    break;
    case 'conseil':
        $onglet = "CONSEIL";
        $lien = 'conseil';
    break;
    case 'corbeille':
        $onglet = "CORBEILLE";
        $lien = 'corbeille';
    break;
    // chambres
    case 'attente':
        $onglet = "EN_COURS";
        $lien = 'attente';
    break;
    case 'accepte':
        $onglet = "VALIDE";
        $lien = 'accepte';
    break;
    case 'refuse':
        $onglet = "REFUSE";
        $lien = 'refuse';
    break;
}

function trunc($chaine, $nbChar) {
    if(strlen($chaine) >= $nbChar)
        $chaine = substr($chaine, 0, $nbChar).'...';
    return $chaine;
}

// forces 
function priveNonLu($i) {
    include("connexionBDD.php");
    $reqPrive = $bdd->prepare('UPDATE `messageprive` SET `ouvert` = 1 WHERE `messageprive`.`idMessagePrive` = ?');
    $reqPrive->execute(array($_SESSION['MESSAGE_PRIVE'][$i]['idMessagePrive']));
    include("chargerInfos.php");
}

function annonceNonLu($i) {
    include("connexionBDD.php");
    $reqAnnonce = $bdd->prepare('UPDATE `annonce` SET `ouvert` = 1 WHERE `annonce`.`idAnnonce` = ?');
    $reqAnnonce->execute(array($_SESSION['ANNONCE'][$i]['idAnnonce']));
    include("chargerInfos.php");
}

function conseilNonLu($i) {
    include("connexionBDD.php");
    $reqConseil = $bdd->prepare('UPDATE `conseil` SET `ouvert` = 1 WHERE `conseil`.`idConseil` = ?');
    $reqConseil->execute(array($_SESSION['CONSEIL'][$i]['idConseil']));
    include("chargerInfos.php");
}

// chambres
function attenteNonLu($i) {
    include("connexionBDD.php");
    $reqAttente = $bdd->prepare('UPDATE `utilisateur` SET `ouvert` = 1 WHERE `utilisateur`.`idUtilisateur` = ?');
    $reqAttente->execute(array($_SESSION['EN_COURS'][$i]['idUtilisateur']));
    include("chargerInfos.php");
}

function valideNonLu($i) {
    include("connexionBDD.php");
    $reqValide = $bdd->prepare('UPDATE `utilisateur` SET `ouvert` = 1 WHERE `utilisateur`.`idUtilisateur` = ?');
    $reqValide->execute(array($_SESSION['VALIDE'][$i]['idUtilisateur']));
    include("chargerInfos.php");
}

function refuseNonLu($i) {
    include("connexionBDD.php");
    $reqAttente = $bdd->prepare('UPDATE `utilisateur` SET `ouvert` = 1 WHERE `utilisateur`.`idUtilisateur` = ?');
    $reqAttente->execute(array($_SESSION['REFUSE'][$i]['idUtilisateur']));
    include("chargerInfos.php");
}

?>

    <!-- pan liste msg -->
    <section id="pan_content">
        <nav id="pan_reception">
        <?php   
            /* ======================== LISTE ONGLETS FORCES ========================== */

            if($_SESSION['chambre'] == 'G' OR $_SESSION['chambre'] == 'P'){ 
                // affichage des demandes
                if (!empty($_SESSION[$onglet])) {

                    $nb = 0;
                    if (isset($_SESSION[$onglet])) $nb = count($_SESSION[$onglet]);
                    for ($i=0; $i < $nb; $i++) {
                        
                        $affiche = $_SESSION[$onglet][$i];
                        $msgOuvert = $_SESSION[$onglet][$i]['ouvert'];

                        // date dans les onglets
                        $dateSQL = substr($affiche['created_at'], 0, 10);
                        $reverseDate = implode('/',array_reverse  (explode('-',$dateSQL)));
                        $date = "<time id='date_reception'>".$reverseDate."</time>";

                        $onglet_affiche = $date.'<h9>'.trunc($affiche['objet'], 17).'</h9>'.'<td>'.'<br/>'.trunc($affiche['texte'], 20);
                        $onglet_affiche_objet = $date.'<h10>'.trunc($affiche['objet'], 17).'</h10>'.'<td>'.'<br/>'.trunc($affiche['texte'], 20);

                        // si selection d'un onglet
                        if ($_GET['m'] != 'none') {

                            // changement msg lu
                            switch ($_GET['e']) {
                                case 'prive':
                                    priveNonLu($_GET['m']);
                                break;
                                case 'annonce': 
                                    annonceNonLu($_GET['m']);
                                break;
                                case 'conseil':
                                    conseilnonLu($_GET['m']);
                                break;
                            }

                            // si le message n'a jamais été ouvert
                            if ($msgOuvert == 0) {
                                if ($i == $_GET['m']) {
                                    echo " <div class='onglet_msg_selected' onclick=window.location.href='demandes.php?e=".$lien."&m="
                                    .$i."'".">$onglet_affiche_objet</div> ";
                                } else {
                                    echo " <div class='onglet_non_lu' onclick=window.location.href='demandes.php?e=".$lien."&m="
                                    .$i."'".">$onglet_affiche</div> ";
                                }
                            } else {
                                if ($i == $_GET['m']) {
                                    echo " <div class='onglet_msg_selected' onclick=window.location.href='demandes.php?e=".$lien."&m="
                                    .$i."'".">$onglet_affiche_objet</div> ";
                                } else {
                                    echo " <div class='onglet_msg' onclick=window.location.href='demandes.php?e=".$lien."&m="
                                    .$i."'".">$onglet_affiche</div> ";
                                }
                            }
                        // si pas de selection d'un onglet
                        } else {
                            if ($msgOuvert == 0) {
                                echo " <div class='onglet_non_lu' onclick=window.location.href='demandes.php?e=".$lien."&m="
                                    .$i."'".">$onglet_affiche</div> ";
                            } else {
                                echo " <div class='onglet_msg' onclick=window.location.href='demandes.php?e=".$lien."&m="
                                .$i."'".">$onglet_affiche</div> ";
                            }
                        }
                    }
                }
            }

            /* ======================== LISTE ONGLETS CHAMBRES ========================== */

            else {
                    // affichage des demandes
                if (!empty($_SESSION[$onglet])) {
                    $nb = 0;
                    if (isset($_SESSION[$onglet])) $nb = count($_SESSION[$onglet]);
                    for ($i=0; $i < $nb; $i++) {

                        $affiche = $_SESSION[$onglet][$i];
                        $msgOuvert = $_SESSION[$onglet][$i]['ouvert'];

                        // date dans les onglets
                        $dateSQL = substr($affiche['created_at'], 0, 10);
                        $reverseDate = implode('/',array_reverse  (explode('-',$dateSQL)));
                        $date = "<time id='date_reception'>".$reverseDate."</time>";

                        // affichage de l'onglet demande
                        $nomPrenom = $affiche['nomUtilisateur'].' '.$affiche['prenomUtilisateur'];
                        $onglet_affiche = $date.'<h9>'.trunc($nomPrenom, 17).'</h9>'.'<td>'.'<br/>'.trunc($affiche['nomSociete'],19).', '.'secteur '.$affiche['secteur'];
                        $onglet_affiche_objet = $date.'<h10>'.trunc($nomPrenom, 17).'</h10>'.'<td>'.'<br/>'.trunc($affiche['nomSociete'],19).', '.'secteur '.$affiche['secteur'];

                        // si selection d'un onglet
                        if ($_GET['m'] != 'none') {

                            // changement msg lu
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

                            // si le message n'a jamais été ouvert
                            if ($msgOuvert == 0) {
                                if ($i == $_GET['m']) {
                                    echo " <div class='onglet_msg_selected' onclick=window.location.href='demandes.php?e=".$lien."&m="
                                    .$i."'".">$onglet_affiche_objet</div> ";
                                } else {
                                    echo " <div class='onglet_non_lu' onclick=window.location.href='demandes.php?e=".$lien."&m="
                                    .$i."'".">$onglet_affiche</div> ";
                                }
                            } else {
                                if ($i == $_GET['m']) {
                                    echo " <div class='onglet_msg_selected' onclick=window.location.href='demandes.php?e=".$lien."&m="
                                    .$i."'".">$onglet_affiche_objet</div> ";
                                } else {
                                    echo " <div class='onglet_msg' onclick=window.location.href='demandes.php?e=".$lien."&m="
                                    .$i."'".">$onglet_affiche</div> ";
                                }
                            }
                        // si pas de selection d'un onglet
                        } else {
                            if ($msgOuvert == 0) {
                                echo " <div class='onglet_non_lu' onclick=window.location.href='demandes.php?e=".$lien."&m="
                                    .$i."'".">$onglet_affiche</div> ";
                            } else {
                                echo " <div class='onglet_msg' onclick=window.location.href='demandes.php?e=".$lien."&m="
                                .$i."'".">$onglet_affiche</div> ";
                            }
                        }
                    }
                }
            }
            ?>
        </nav>
            <?php
                /* ===================== ZONE OUVERTURE DU MESSAGE FORCES ============+============= */

                if($_SESSION['chambre'] == 'G' OR $_SESSION['chambre'] == 'P'){ 
                    echo ('
                        <!-- zone de la demande ouverte -->
                        <!-- <div id="zone_msg"> -->
                            <!-- informations du message -->
                            <!-- <time id="date_msg" datetime="18 avr. 2020 10:39">18 avr. 2020 10:39</time>
                            <div id="objet">Objet du message</div>
                            <div id="infos1">DUDURU Catherine  <Adra31@gmail.com>, <Grida31@outlook.com> - 06 12 45 78 89 / 06 25 14 36 98</div>
                            <div id="infos2"><b>Zone 2</b> - Muret 31600 - Société : Batplus</div> -->
                        <!-- </div> -->
                        ');
                }
                
                /* ===================== ZONE OUVERTURE DU MESSAGE CHAMBRE ========================= */

                else {

                    if ($_GET['m'] == 'none') {
                        echo '';
                    } else {
                        // chargement des informations sur la demande
                        $infosUser = $_SESSION[$onglet][$_GET['m']];
        
                        // commune
                        $requete = $bdd->prepare("SELECT codePostal, commune FROM Commune WHERE idCommune = ?");
                        $requete->execute(array($infosUser["idCommune"]));
                        $resultatRequet = $requete->fetch();
                        $infosUser["codePostal"] = $resultatRequet["codePostal"];
                        $infosUser["commune"] = $resultatRequet["commune"];
        
                        //type d'activite
                        $activite = $bdd->prepare("SELECT activite FROM codeactivite WHERE code = ?");
                        $activite->execute(array($infosUser["codeAct"]));
                        $infosUser["activite"] = ($activite->fetch()["activite"]);
        
                        $_SESSION[$onglet][$_GET['m']]['codePostal'] = $infosUser["codePostal"];
        
                        //Creation de la cé de l'utilisateur
                        $_SESSION[$onglet][$_GET['m']]['cle'] = $infosUser['codeAct'] .
                            $infosUser['codePostal'] .
                            $infosUser['secteur'] .
                            sprintf("%04d", $infosUser['idUtilisateur']);
        
                        // affichage
                        echo "
                            <div id='zone_demande'>
                            <time id='date_demande'>".$infosUser['created_at']."</time>
                            <p><b>Nom : </b><label>".$infosUser['nomUtilisateur']."<br><p>
                            <p><b>Prénom : </b><label>".$infosUser['prenomUtilisateur']."</label><br></p>
                            <p><b>Nom Société : </b><label>".$infosUser['nomSociete']."</label><br></p>
                            <p><b>Type d'activité : </b><label>".$infosUser['activite']."</label><br></p>
                            <p><b>Numéro Siret : </b><label>".$infosUser['siret']."</label><br></p>
                            <p><b>Localisaiton : </b><label>".$infosUser['commune']." ".$infosUser['codePostal']."</label><br></p>
                            <p><b>Téléphone : </b><label>".$infosUser['telephone']."</label><br></p>
                            <p><b>Adresse mail : </b><label>".$infosUser['mail']."</label><br></p>
                            </div>
                        " ;
        
                        // affichage de la clé d'identification
                        if ($_GET['e'] != 'refuse') {
                            echo "<div id='cle'> Clé d'identification :  " . $_SESSION[$onglet][$_GET['m']]['cle'] . "<div>";
                        }
                    }
                }
            ?>

    </section>