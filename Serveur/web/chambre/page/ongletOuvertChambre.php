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
    } ?>

    <!-- affichage -->
    <section id='pan-demande'>
        <fieldset>
            <time id='date-demande'><?= $dateMsg ?></time>
            <legend>INFORMATION DE LA DEMANDE</legend><br>
            <label><b>Nom : </b><?= $infosUser['nomUtilisateur'] ?></label>
            <label><b>Prénom : </b><?= $infosUser['prenomUtilisateur'] ?></label>
            <label><b>Nom Société : </b><?= $infosUser['nomSociete'] ?></label>
            <label><b>Type d'activité : </b><?= $infosUser['activite'] ?></label>
            <label><b>Numéro Siret : </b><?= $infosUser['siret'] ?> → 
            <a href="https://www.infogreffe.fr/entreprise-societe/<?= $infosUser['siret'] ?>" target="_blank">Cliquez-ici</a> pour vérifier le numéro Siret.</label>
            <label><b>Localisation : </b><?php echo $infosUser['commune'] . " " . $infosUser['codePostal']; ?></label>
            <label><b>Téléphone : </b><?= $infosUser['telephone'] ?></label>
            <label><b>Adresse mail : </b><?= $infosUser['mail'] ?></label>
        </fieldset>

    <!-- affichage de la clé d'identification -->
    <?php if ($_GET['e'] != 'refuse') { ?>
        <fieldset>
            <legend>CLÉ D'IDENTIFICATION GÉNÉRÉE :</legend>
            <label id='cle'><?= $infosUser['cle'] ?></label>
        </fieldset>
    <?php } ?>

    </section> 

<?php } ?>
