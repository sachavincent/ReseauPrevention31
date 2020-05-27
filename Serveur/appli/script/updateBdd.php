<?php

/***
 * FONCTION creerCSV
 * Créer les fichier csv d'une table de la bdd (nom du fichier créer : '$table.csv')
 * Entrées :    $cle                ->  Cle de l'utilisateur
 *              $table              ->  Nom de la table
 *              $bdd                ->  Base de donnee connectée
 *              $idFilAnnonce       ->  Liste des fils de discussion / annonces dont fait partie l'utilisateur
 *              $device_id          ->  identifiant de l'appareilde l'utilisateur
 */
function creerCSV($cle, $table, $bdd, $idFilAnnonce, $device_id){
    header('Content-type: application/csv');
    switch($table){
        case 'Utilisateur':
            $requestSQL = $bdd->prepare('SELECT * FROM Utilisateur WHERE cle = ?');
            $requestSQL->execute(array($cle));
            $fp = fopen("../$device_id/utilisateur.csv", "w");
            $resultatRequete = $requestSQL->fetchAll();
        break;
        case 'CodeActivite':
            $requestSQL = $bdd->query('SELECT * FROM CodeActivite');
            $fp = fopen("../$device_id/CodeActivite.csv", "w");
            $resultatRequete = $requestSQL->fetchAll();
        break;
        case 'Commune':
            $requestSQL = $bdd->query('SELECT * FROM Commune');
            $fp = fopen("../$device_id/Commune.csv", "w");
            $resultatRequete = $requestSQL->fetchAll();
        break;
        case 'Conseil':
            $requestSQL = $bdd->query('SELECT * FROM Conseil');
            $fp = fopen("../$device_id/Conseil.csv", "w");
            $resultatRequete = $requestSQL->fetchAll();
        break;
        case 'FilDeDiscussion':
            $requestSQL = $bdd->prepare('SELECT * FROM FilDeDiscussion WHERE idFilDeDiscussion = ?');

            foreach($idFilAnnonce['fil'] as $idFil){
                $requestSQL->execute(array($idFil));
                $resultatRequete [] = $requestSQL->fetch();
            }

            $fp = fopen("../$device_id/FilDeDiscussion.csv", "w");
        break;
        case 'MessagePrive':
            $requestSQL = $bdd->prepare('SELECT * FROM MessagePrive WHERE idFilDeDiscussion = ?');

            foreach($idFilAnnonce['fil'] as $idFil){
                $requestSQL->execute(array($idFil));
                while ($res = $requestSQL->fetch()){
                    $resultatRequete [] = $res;
                }
            }
            $fp = fopen("../$device_id/MessagePrive.csv", "w");
        break;
        case 'Annonce':
            $requestSQL = $bdd->prepare('SELECT * FROM Annonce WHERE idAnnonce = ?');

            foreach($idFilAnnonce['annonce'] as $idAnnonce){
                $requestSQL->execute(array($idAnnonce));
                while ($res = $requestSQL->fetch()){
                    $resultatRequete [] = $res;
                }
            }
            $fp = fopen("../$device_id/Annonce.csv", "w");
        break;
    }


    $tableauResultat = array(array());
    $tailleResultat = count($resultatRequete);
    $headCSV = array();

    for($i = 0; $i < $tailleResultat; $i++){
        foreach($resultatRequete[$i] as $cle => $value){
            if (!preg_match('#^[0-9]+$#', $cle)){
                $tableauResultat[$i][$cle] = $value;
                if ($i == 0){ //Si premiere ligne recuperation de l'entete
                    $headCSV[] = $cle;
                }
            }
        }
        if ($i == 0) fputcsv($fp, $headCSV); //Si première ligne ajout de l'entete
        fputcsv($fp, $tableauResultat[$i]);
    }
    fclose($fp);
}

/***
 * FONCTION besoinMaj
 * Retourne un booleen -> Est ce que la bdd local de l'utilisateur a besoin d'etre mise a jour?
 * Entrées :    $cle                ->  Cle de l'utilisateur
 *              $table              ->  Nom de la table
 *              $derniereMAJ        ->  Date de la dernère mise a jour de la bdd local a l'utilisateur
 *              $bdd                ->  Base de donnee connectée
 *              $idFilAnnonce   ->  Liste des fils de discussion/annonces dont fait partie l'utilisateur
 */

function besoinMaj($cle, $table, $derniereMAJ, $bdd, $idFilAnnonce){
    $resultatRequete = array();

    switch($table){
        case 'CodeActivite':
            $requestSQL = $bdd->prepare('SELECT * FROM CodeACtivite WHERE updated_at > ?');
            $requestSQL->execute(array($derniereMAJ));
            $resultatRequete = $requestSQL->fetchAll();
        break;
        case 'Commune':
            $requestSQL = $bdd->prepare('SELECT * FROM Commune WHERE updated_at > ?');
            $requestSQL->execute(array($derniereMAJ));
            $resultatRequete = $requestSQL->fetchAll();
        break;
        case 'Conseil':
            $requestSQL = $bdd->prepare('SELECT * FROM Conseil WHERE updated_at > ?');
            $requestSQL->execute(array($derniereMAJ));
            $resultatRequete = $requestSQL->fetchAll();
        break;
        case 'FilDeDiscussion':
            $requestSQL = $bdd->prepare('SELECT * FROM FilDeDiscussion WHERE updated_at > ? AND idFilDeDiscussion = ?');
            foreach($idFilAnnonce['fil'] as $idFil){
                $requestSQL->execute(array($derniereMAJ, $idFil));
                $resultatRequete [] = $requestSQL->fetch();
            }
        break;
        case 'MessagePrive':
            $requestSQL = $bdd->prepare('SELECT * FROM MessagePrive WHERE created_at > ? AND idFilDeDiscussion = ?');

            foreach($idFilAnnonce['fil'] as $idFil){
                $requestSQL->execute(array($derniereMAJ, $idFil));
                $resultatRequete [] = $requestSQL->fetch();
            }
        break;
        case 'Annonce':
            $requestSQL = $bdd->prepare('SELECT * FROM Annonce WHERE created_at > ? AND idAnnonce = ?');
            foreach($idFilAnnonce['annonce'] as $idAnnonce){
                $requestSQL->execute(array($derniereMAJ, $idAnnonce));
                $resultatRequete [] = $requestSQL->fetch();
            }

    }
    return (count($resultatRequete) != 0);
}

/***
 * FONCTION listeFilAnnonce
 * Créer la liste des fils de discussion/annonces auquels appartient l'utilisateur
 * Entrées :    $cle    ->  Cle de l'utilisateur
 *              $bdd    ->  Base de donnee connectée
 */
function listeFilAnnonce($cle, $bdd){
    //Recuperation de l'idUtilisateur
    $requestSQL = $bdd->prepare('SELECT idUtilisateur FROM Utilisateur WHERE cle = ?');
    $requestSQL->execute(array($cle));
    $idUtilisateur = ($requestSQL->fetch())['idUtilisateur'];

    //Recuperation des fils de discussion
    $requestSQL = $bdd->prepare('SELECT idFilDeDiscussion FROM FilDeDiscussion WHERE idUtilisateur = ?');
    $requestSQL->execute(array($idUtilisateur));

    $idFilAnnonce = array(array());
    while ($idFil = $requestSQL->fetch()){
        $idFilAnnonce['fil'][] = $idFil['idFilDeDiscussion'];
    }

    //Recuperation des annonces
    $requestSQL = $bdd->prepare('SELECT idAnnonce FROM DestinationAnnonce WHERE idUtilisateur = ?');
    $requestSQL->execute(array($idUtilisateur));

    while ($idAnnonce = $requestSQL->fetch()){
        $idFilAnnonce['annonce'][] = $idAnnonce['idAnnonce'];
    }
    return $idFilAnnonce;
}

/***
 * Debut du script
 * Information attendu :
 *      Derniere mise a jour de la bdd local a l'utilisateur
 *      Cle de l'utilisateur (Facultatif)
 */

$info = strip_tags($request->getParsedBody());

//Creation de la date de derniere mise a jour au bon format
$dateDerniereMAJEpoch = $info['derniere_mise_a_jour'];
$dateDerniereMAJ = new DateTime("@$dateDerniereMAJEpoch");
$dateDerniereMAJ = $dateDerniereMAJ->format('Y-m-d H:i:s');

$idFilAnnonce = array();

if (empty($info['derniere_mise_a_jour']) OR empty($info['device_id'])){//Si date de derniere mise a jour non renseignée
    $retour['error'] = 100;
} elseif (!preg_match('#^[0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}$#', $dateDerniereMAJ)){
    $retour['error'] = 103;
} elseif (!empty($info['cle_identification'])) { //Verification de la clé de l'utilisateur si renseignée
    $requestSQL = $bdd->prepare('SELECT cle FROM Utilisateur WHERE cle = ?');
    $requestSQL->execute(array($info['cle_identification']));
    $resultatRequete = $requestSQL->fetch();

    if (empty($resultatRequete['cle'])){
        $retour['error'] = 101;
    } else {
            $tables = array('Utilisateur','CodeActivite', 'Commune', 'Conseil', 'FilDeDiscussion', 'MessagePrive', 'Annonce');
            $idFilAnnonce = listeFilAnnonce($info['cle_identification'], $bdd);
    }
} else {
    $info['cle_identification'] = NULL;
    $tables = array('CodeActivite', 'Commune', 'Conseil');
}
if (empty($retour['error'])){
    $retour['success'] = true;
    $device_id =  $info['device_id'];
    if (!file_exists('../$device_id')) mkdir("../$device_id");
    foreach($tables as $table){
        if (besoinMaj($info['cle_identification'], $table, $dateDerniereMAJ, $bdd, $idFilAnnonce)){
            creerCSV($info['cle_identification'], $table, $bdd, $idFilAnnonce, $device_id);
            $retour['success'] = false;
        }
    }
}

header('Content-type: application/json');

echo json_encode($retour);
?>
