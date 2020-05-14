<?php
// chargement categorie
switch ($_GET['e']) {
    case 'attente':
        $onglet = "EN_COURS";
    break;
    case 'accepte':
        $onglet = "VALIDE";
    break;
    case 'refuse':
        $onglet = "REFUSE";
    break;
}
?>

<section id="pan_content">
    <nav id="pan_reception">
        <?php
            // affichage des demandes
            if (!empty($_SESSION[$onglet])) {
                $nb = 0;
                if (isset($_SESSION[$onglet])) $nb = count($_SESSION[$onglet]);
                for ($i=0; $i < $nb; $i++) {

                    $affiche = $_SESSION[$onglet][$i];

                    // date dans les onglets
                    $dateSQL = substr($affiche['created_at'], 0, 10);
                    $reverseDate = implode('/',array_reverse  (explode('-',$dateSQL)));
                    $date = "<time id='date_reception'>".$reverseDate."</time>";

                    // affichage de l'onglet demande
                    $onglet_affiche = $date.$affiche['nomUtilisateur'].' '.$affiche['prenomUtilisateur'].' '.'<td>'.'<br/>'.
                    $affiche['nomSociete'].', '.'secteur '.$affiche['secteur'];

                    if ($_GET['m'] != 'none') {

                        switch ($_GET['e']) {
                            case 'attente': // changement de fond sur le demande selectionnee
                                            if ($i == $_GET['m']) {
                                                echo " <div class='onglet_msg_selected' onclick=window.location.href='demandes.php?e=attente&m="
                                                .$i."'".">$onglet_affiche</div> ";}
                                            else {
                                                echo " <div class='onglet_msg' onclick=window.location.href='demandes.php?e=attente&m="
                                                .$i."'".">$onglet_affiche</div> ";}
                            break;
                            case 'accepte': // changement de fond sur le demande selectionnee
                                            if ($i == $_GET['m']) {
                                                echo " <div class='onglet_msg_selected' onclick=window.location.href='demandes.php?e=accepte&m="
                                                .$i."'".">$onglet_affiche</div> ";}
                                            else {
                                                echo " <div class='onglet_msg' onclick=window.location.href='demandes.php?e=accepte&m="
                                                .$i."'".">$onglet_affiche</div> ";}
                            break;
                            case 'refuse' : // changement de fond sur le demande selectionnee
                                            if ($i == $_GET['m']) {
                                                echo " <div class='onglet_msg_selected' onclick=window.location.href='demandes.php?e=refuse&m="
                                                .$i."'".">$onglet_affiche</div> ";}
                                            else {
                                                echo " <div class='onglet_msg' onclick=window.location.href='demandes.php?e=refuse&m="
                                                .$i."'".">$onglet_affiche</div> ";}
                            break;
                        }
                    }
                    else {
                        switch ($_GET['e']) {
                            case 'attente': echo " <div class='onglet_msg' onclick=window.location.href='demandes.php?e=attente&m="
                                                .$i."'".">$onglet_affiche</div> ";
                            break;
                            case 'accepte': echo " <div class='onglet_msg' onclick=window.location.href='demandes.php?e=accepte&m="
                                                .$i."'".">$onglet_affiche</div> ";
                            break;
                            case 'refuse' : echo " <div class='onglet_msg' onclick=window.location.href='demandes.php?e=refuse&m="
                                                .$i."'".">$onglet_affiche</div> ";
                            break;
                        }
                    }
                }
            }
        ?>
    </nav>
        <?php
            if ($_GET['m'] == 'none') {
                echo '';
            }
            else {
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
                echo "<h2> Cle : " . $_SESSION[$onglet][$_GET['m']]['cle'] . "</h2>";
            }
        ?>
</section>
