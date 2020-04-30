<?php
header('Content-type: application/json');

//Cx a la bdd 
try{
    $bdd = new PDO('mysql:host=localhost;dbname=prevention31', 'root', 'root', array(PDO::ATTR_ERRMODE => PDO::ERRMODE_EXCEPTION));
}
catch(Exception $e){
    $retour['success'] = false;
    $retour['message'] = 'erreur connexion a la base de donnee';
    echo json_encode($retour);
    die();
}

//Test si variable $_POST def
if (empty($_POST['validation']) OR empty($_POST['id_utilisateur']) OR empty($_POST['id_utilisateur'])){
    $retour['sucess'] = false;
    $retour['message'] = 'il manque des infos';
} else {
    $retour['success'] = true;
    $requeteUpdateUtilisateur = $bdd->prepare('UPDATE `Utilisateur` SET `cle` = ?, codeAct = ?, demande = ? WHERE `Utilisateur`.`id_utilisateur` = ?');
    switch ($_POST['validation']){
        case 'true':
            $requeteInfoCle = $bdd->prepare('SELECT codePostal, secteur FROM Utilisateur WHERE id_utilisateur = ?');
            $requeteInfoCle->execute(array($_POST['id_utilisateur']));
            $infoCle = $requeteInfoCle->fetch();

            $cle = $_POST['ape'] . 
                $infoCle['codePostal'] . 
                $infoCle['secteur'] . 
                sprintf("%04d", $_POST['id_utilisateur']);
            $requeteUpdateUtilisateur->execute(array($cle, $_POST['ape'], 'VALIDE', $_POST['id_utilisateur']));
            $retour['message'] = 'Utilisateur valide';
            //TODO envoyer mail a l'utilisateur avec sa cle
        break;
        case 'false':
            $requeteUpdateUtilisateur->execute(array(NULL, NULL, 'REFUSE', $_POST['id_utilisateur']));
            $retour['message'] = 'Utilisateur refuse';
        break;
        default: $retour['message'] = 'gros pb';
    }
}
echo json_encode($retour);
?>