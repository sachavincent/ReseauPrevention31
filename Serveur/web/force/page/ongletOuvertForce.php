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
        // OUVERTURE MSG PRIVES
        case 'prive': ?>
            <!-- zone d'ouverture du msg prive -->
            <section id="zone-msg">
                <!-- champs infos msg prive -->
                <section id="zone-infos-prive">
                    <fieldset>
                        <legend><?=$objet ?></legend>
                        <p><b>Utilisateur : </b><?=$nom ?> <?=$prenom ?></p>
                        <p><b>Adresse Mail : </b><?=$mail ?></p>
                        <p><b>Téléphone : </b><?=$telephone ?></p>
                        <p><b>Localisation : </b>secteur <?=$secteur ?></p>
                        <p><b>Société : </b><?=$societe ?></p>
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
                        echo '<div class=' . $emetteur . '>' . nl2br($msg['texte']) . '</div>';
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
                    <div class="write-msg">
                        <textarea name="reponse-msg" placeholder="Écrivez votre message ici ..." required></textarea>
                    </div>
                    <input type="submit" name="envoyer-msg" value="envoyer">
                </section>
                </form>
            </section>
        <?php break;

        // OUVERTURE ANNONCE
        case 'annonce': ?>
            <section id="zone-annonce-conseil"> 
                <fieldset>
                    <time id="date-msg"><?= $dateMsg ?></time>
                    <legend><?= $infosUser['objet'] ?></legend>
                    <p class="user-concernes">Utilisateurs concernés : <?= $_SESSION['ANNONCE'][$_GET['m']]['nbDestinataire'] ?></p>
                </fieldset>
                <div id="affichage-texte"><?= nl2br($infosUser['texte']) ?></div>
            </section>
        <?php break;

        // OUVERTURE CONSEIL
        case 'conseil': ?>
            <section id="zone-annonce-conseil"> 
                <fieldset>
                    <time id="date-msg"><?= $dateMsg ?></time>
                    <legend><?= $infosUser['objet'] ?></legend>
                    <p class="user-concernes">À destination des utilisateurs de l'application mobile</p>
                </fieldset>
                <div id="affichage-texte"><?= nl2br($infosUser['texte']) ?></div>
            </section>
        <?php break;
    }
}
?>
