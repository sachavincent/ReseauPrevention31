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
        case 'prive'   :  echo ('
                            <section id="zone-msg"> 
                                <div id="objet-msg"><U>'.$objet.'</U></div><br>
                                <div id="infos-msg">
                                    <b>Utilisateur :</b> '.$nom.', '.$prenom.'<br><br>
                                    <b>Adresse Mail :</b> '. $mail.'<br><br>
                                    <b>Téléphone : </b>'.$telephone.'<br><br>
                                    <b>Localisation :</b> secteur '.$secteur.'<br><br>
                                    <b>Société :</b> '.$societe.
                                '</div>
                                <section id="zone-discussion">
                                    <div class="msg-force">Salut</div>
                                    <div class="msg-user">Bonjour</div>
                                    <div class="msg-user">Comment allez vous ?</div>
                                    <div class="msg-force">Wesh la famille</div>
                                    <div class="msg-force">Salut</div>
                                    <div class="msg-user">Bonjour</div>
                                    <div class="msg-user">Comment allez vous ?</div>
                                    <div class="msg-force">Wesh la famille</div>
                                    <div class="msg-force">Salut</div>
                                    <div class="msg-user">Bonjour</div>
                                    <div class="msg-user">Comment allez vous ? espece de negre des forets, jesper tu meurs fdp de noir de merde de maxou australopitèque</div>
                                    <div class="msg-force">Wesh la famille</div>
                                </section>
                                <!-- Bas de page par defaut des messages -->
                                <script>
                                    var x = document.getElementById("zone-discussion");
                                    x.scrollTop = x.scrollHeight;
                                </script>
                                <section id="zone-reponse">
                                    <textarea id="reponse-msg"></textarea>
                                    <input type="submit" name="envoyer-msg" value="envoyer">
                                </section>
                            </section>
                            ');
        break;
        case 'annonce' :  echo ('
                            <section id="zone-annonce-conseil"> 
                                <time id="date-msg">'.$dateMsg.'</time>
                                <div id="objet-msg"><U>'.$infosUser['objet'].'</U></div>
                                <div id="secteur-annonce">secteurs concernés : 1 - 2 - 6</div>
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