<?php

if ($_GET['m'] != 'none') {
    // chargement des informations sur la demande
    $infosUser = $_SESSION[$onglet][$_GET['m']];

    if ($onglet != 'FIL_DE_DISCUSSION') {
        $dateBrut = substr($infosUser['created_at'], 0, 10);
        $reverseDateMsg = implode('/',array_reverse  (explode('-',$dateBrut)));
        $heureMsg = substr($infosUser['created_at'], 10, 10);
        $dateMsg = "Le " . $reverseDateMsg . " à " . $heureMsg;
    } else {
        $infosUser = $_SESSION['FIL_DE_DISCUSSION'][$_GET['m']]['Utilisateur'];
        $objet = $_SESSION['FIL_DE_DISCUSSION'][$_GET['m']]['objet'];
        $nom = $infosUser['nomUtilisateur'];
        $prenom = $infosUser['prenomUtilisateur'];
        $mail = $infosUser['mail'];
        $telephone = $infosUser['telephone'];
        $secteur = $infosUser['secteur'];
        $societe = $infosUser['nomSociete'];
    }

    // affichage du contenu de l'onglet selectionné
    switch ($_GET['e']) {
        case 'prive'   :?>  <section id="zone-msg"> 
                                <div id="objet-msg"><U><?=$objet?></U></div><br>
                                <div id="infos-msg">
                                    <b>Utilisateur :</b><?=$nom?> <?=$prenom?><br><br>
                                    <b>Adresse Mail :</b><?=$mail?><br><br>
                                    <b>Téléphone : </b><?=$telephone?><br><br>
                                    <b>Localisation :</b> secteur <?=$secteur?><br><br>
                                    <b>Société :</b><?=$societe?>
                                </div>
                                <section id="zone-discussion">
                                    <?php // traitement msg forces / user
                                        $infoFil = $_SESSION['FIL_DE_DISCUSSION'][$_GET['m']];
                                        foreach ($infoFil['message'] as $msg) {
                                        switch ($msg['emetteur']) {
                                            case 0 :
                                                $emetteur = 'msg-force';
                                            break;
                                            case 1 :
                                                $emetteur = 'msg-user';
                                            break;
                                        }
                                        echo '<div class='.$emetteur.'>'.$msg['texte'].'</div>';
                                    }
                                    ?>
                                </section>
                                <!-- Bas de page par defaut des messages -->
                                <script>
                                    var x = document.getElementById("zone-discussion");
                                    x.scrollTop = x.scrollHeight;
                                </script>

                                <form action="../script/nouveauMessagePrive.php?m=<?=$_GET['m']."&idFil=".$infoFil['idFilDeDiscussion']?>" method="POST">
                                <section id="zone-reponse">
                                    <textarea id="reponse-msg" name="reponse-msg"></textarea>
                                    <input type="submit" name="envoyer-msg" value="envoyer">
                                </section>
                                </form>
                            </section>
<?php 
        break;
        case 'annonce' :  echo ('
                            <section id="zone-annonce-conseil"> 
                                <time id="date-msg">'.$dateMsg.'</time>
                                <div id="objet-msg"><U>'.$infosUser['objet'].'</U></div>
                                <div id="secteur-annonce"> Utilisateurs concernés : ' . $_SESSION['ANNONCE'][$_GET['m']]['nbDestinataire'] . '</div>
                            </section>
                            <div id="affichage-texte">'.$infosUser['texte'].'</div>
                            ');
        break;
        case 'conseil' :  echo ('
                            <section id="zone-annonce-conseil"> 
                                <time id="date-msg">'.$dateMsg.'</time>
                                <div id="objet-msg"><U>'.$infosUser['objet'].'</U></div>
                                <div id="secteur-annonce">À destination des utilisateurs de l\'application mobile</div>
                            </section>
                            <div id="affichage-texte">'.$infosUser['texte'].'</div>
                            ');
        break;
    }
}
?>