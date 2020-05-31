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
    $infosUser['cle'] = sprintf("%03d", $infosUser['codeAct']) . $infosUser['codePostal'] . $infosUser['secteur'] . sprintf("%04d", $infosUser['idUtilisateur']);
    $_SESSION[$onglet][$_GET['m']]['cle'] = $infosUser['cle'];

    // affichage
    echo "
        <section id='pan-demande'>
          <fieldset>
            <time id='date-demande'>" . $dateMsg . "</time>
            <legend>INFORMATION DE LA DEMANDE</legend><br>
            <b>Nom : </b><label>" . $infosUser['nomUtilisateur'] . "<br><p>
            <b>Prénom : </b><label>" . $infosUser['prenomUtilisateur'] . "</label><br></p>
            <b>Nom Société : </b><label>" . $infosUser['nomSociete'] . "</label><br></p>
            <b>Type d'activité : </b><label>" . $infosUser['activite'] . "</label><br></p>
            <b>Numéro Siret : </b><label>" . $infosUser['siret'] . "</label><br></p>
            <b>Localisaiton : </b><label>" . $infosUser['commune'] . " " . $infosUser['codePostal'] . "</label><br></p>
            <b>Téléphone : </b><label>" . $infosUser['telephone'] . "</label><br></p>
            <b>Adresse mail : </b><label>" . $infosUser['mail'] . "</label><br></p>
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
