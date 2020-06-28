<?php
if ($_GET['m'] != 'none') {
    // chargement des informations sur la demande
    $infosUser = $_SESSION[$onglet][$_GET['m']];

    // Date demande
    $dateBrut       = substr($infosUser['created_at'], 0, 10);
    $reverseDateMsg = implode('/', array_reverse(explode('-', $dateBrut)));
    $heureMsg       = substr($infosUser['created_at'], 10, 15);
    $dateMsg        = "Le " . $reverseDateMsg . " à " . $heureMsg;

    // commune + code postal
    $infosUser = infoCommune($infosUser);

    //type d'activite
    $activite = $bdd->prepare("SELECT activite FROM CodeActivite WHERE code = ?");
    $activite->execute(array(
        $infosUser["codeAct"]
    ));
    $infosUser["activite"] = ($activite->fetch() ["activite"]);

    //Creation de la cé de l'utilisateur
    if ($_SESSION[$onglet][$_GET['m']]['cle'] == NULL){
        $requetNbUtilisateurParAct = $bdd->prepare('SELECT * FROM Utilisateur WHERE codeAct = ?  AND cle IS NOT NULL');
        $requetNbUtilisateurParAct->execute(array($infosUser['codeAct']));
        $infosUser['cle'] = sprintf("%03d", $infosUser['codeAct']) . $infosUser['codePostal'] . $infosUser['secteur'] . sprintf("%04d", count($requetNbUtilisateurParAct->fetchAll()) );
        $_SESSION[$onglet][$_GET['m']]['cle'] = $infosUser['cle'];
    }

    // affichage
    echo "
        <section id='pan-demande'>
        <fieldset>
            <time id='date-demande'>" . $dateMsg . "</time>
            <legend>INFORMATION DE LA DEMANDE</legend><br>
            <b>Nom : </b><label>" . $infosUser['nomUtilisateur'] . "<br><br>
            <b>Prénom : </b><label>" . $infosUser['prenomUtilisateur'] . "</label><br><br>
            <b>Nom Société : </b><label>" . $infosUser['nomSociete'] . "</label><br><br>
            <b>Type d'activité : </b><label>" . $infosUser['activite'] . "</label><br><br>
            <b>Numéro Siret : </b><label>" . $infosUser['siret'] . " → </label>
            <em onclick=window.open('https:\/\/www.infogreffe.fr/entreprise-societe/" . $infosUser['siret'] . "') />Cliquez-ici</em> pour vérifier le numéro Siret.<br><br>
            <b>Localisation : </b><label>" . $infosUser['commune'] . " " . $infosUser['codePostal'] . "</label><br><br>
            <b>Téléphone : </b><label>" . $infosUser['telephone'] . "</label><br><br>
            <b>Adresse mail : </b><label>" . $infosUser['mail'] . "</label><br><br>
        </fieldset>
    ";

    // affichage de la clé d'identification
    if ($_GET['e'] != 'refuse') {
        echo "
            <fieldset>
            <legend>CLÉ D'IDENTIFICATION GÉNÉRÉE :</legend><br>
            <label id='cle'>". $infosUser['cle'] . "</label><br>
            </fieldset>
            </section>";
    } else {
        echo "</section>";
    }
}

?>
