<?php
if ($_GET['m'] != 'none') {
    // chargement des informations sur la demande
    $infosUser = $_SESSION[$onglet][$_GET['m']];

    // chargement des infos pour les msg prives
    if ($onglet != 'FIL_DE_DISCUSSION') {
        $dateBrut       = substr($infosUser['created_at'], 0, 10);
        $reverseDateMsg = implode('/', array_reverse(explode('-', $dateBrut)));
        $heureMsg       = substr($infosUser['created_at'], 10, 10);
        $dateMsg        = "Le " . $reverseDateMsg . " à " . $heureMsg;
    }
    else {
        // chargement infos pour annonces et conseils
        $infosUser      = $_SESSION['FIL_DE_DISCUSSION'][$_GET['m']]['Utilisateur'];
        $objet          = $_SESSION['FIL_DE_DISCUSSION'][$_GET['m']]['objet'];
        $nom            = $infosUser['nomUtilisateur'];
        $prenom         = $infosUser['prenomUtilisateur'];
        $mail           = $infosUser['mail'];
        $telephone      = $infosUser['telephone'];
        $secteur        = $infosUser['secteur'];
        $societe        = $infosUser['nomSociete'];
    }

    // affichage du contenu de l'onglet selectionné
    switch ($_GET['e']) {
        case 'prive': ?>
            <!-- zone d'ouverture du msg prive -->
            <section id="zone-msg">
              <!-- champs infos msg prive -->
              <section id="zone-infos-prive">
                <fieldset>
                  <legend><?=$objet ?></legend><br>
                    <b>Utilisateur : </b><?=$nom ?> <?=$prenom ?><br><br>
                    <b>Adresse Mail : </b><?=$mail ?><br><br>
                    <b>Téléphone : </b><?=$telephone ?><br><br>
                    <b>Localisation : </b>secteur <?=$secteur ?><br><br>
                    <b>Société : </b><?=$societe ?><br><br>
                </fieldset>
              </section>

              <!-- zone affichage des messages -->
              <section id="zone-discussion">
              <?php // traitement msg forces / user
                $infoFil = $_SESSION['FIL_DE_DISCUSSION'][$_GET['m']];
                foreach ($infoFil['message'] as $msg) {
                    switch ($msg['emetteur']) {
                        case 'FORCE':
                            $emetteur = 'msg-force';
                        break;
                        case 'UTILISATEUR':
                            $emetteur = 'msg-user';
                        break;
                    }
                    echo '<div class=' . $emetteur . '>' . $msg['texte'] . '</div>';
                } ?>
              </section>
              <!-- Bas de page par defaut des messages -->
              <script>
                var x = document.getElementById("zone-discussion");
                x.scrollTop = x.scrollHeight;
              </script>

              <!-- zone de saisie de réponse -->
              <form action="../script/nouveauMessagePrive.php?m=<?=$_GET['m'] . "&idFil=" . $infoFil['idFilDeDiscussion'] ?>" method="POST">
              <section id="zone-reponse">
                <fieldset>
                  <legend>Votre réponse</legend>
                  <textarea id="reponse-msg" name="reponse-msg" required></textarea>
                  <input type="submit" name="envoyer-msg" value="envoyer">
                </fieldset>
              </section>
              </form>
            </section>
<?php
        break;
        case 'annonce':
            echo ('
                    <section id="zone-annonce-conseil"> 
                      <fieldset>
                        <time id="date-msg">' . $dateMsg . '</time>
                        <legend>' . $infosUser['objet'] . '</legend>
                        <p class="user-concernes">Utilisateurs concernés : ' . $_SESSION['ANNONCE'][$_GET['m']]['nbDestinataire'] . '</p>
                      </fieldset>
                    </section>
                    <div id="affichage-texte">' . $infosUser['texte'] . '</div>
                 ');
        break;
        case 'conseil':
            echo ('
                    <section id="zone-annonce-conseil"> 
                      <fieldset>
                        <time id="date-msg">' . $dateMsg . '</time>
                        <legend>' . $infosUser['objet'] . '</legend>
                        <p class="user-concernes"></p>À destination des utilisateurs de l\'application mobile</p>
                    </fieldset>
                    </section>
                    <div id="affichage-texte">' . $infosUser['texte'] . '</div>
                ');
        break;
    }
}
?>
