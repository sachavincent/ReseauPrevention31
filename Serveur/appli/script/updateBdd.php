<?php

/***
 * FONCTION creerCSV
 * Créer les fichier csv d'une table de la bdd (nom du fichier créer : '$table.csv')
 * Entrées :    $cle                ->  Cle de l'utilisateur
 *              $table              ->  Nom de la table
 *              $bdd                ->  Base de donnee connectée
 *              $idFilUtilisateur   ->  Liste des fils de discussion dont fait partie l'utilisateur
 */
function creerCSV($cle, $table, $bdd, $idFilUtilisateur){
    header('Content-type: application/csv');
    switch($table){
        case 'Utilisateur': 
            $requestSQL = $bdd->prepare('SELECT * FROM Utilisateur WHERE cle = ?');
            $requestSQL->execute(array($cle));
            $fp = fopen("utilisateur.csv", "w");
            $resultatRequete = $requestSQL->fetchAll();
        break;
        case 'CodeActivite':
            $requestSQL = $bdd->query('SELECT * FROM CodeActivite');
            $fp = fopen("CodeActivite.csv", "w");
            $resultatRequete = $requestSQL->fetchAll();
        break;
        case 'Commune':
            $requestSQL = $bdd->query('SELECT * FROM Commune');
            $fp = fopen("Commune.csv", "w");
            $resultatRequete = $requestSQL->fetchAll();
        break;
        case 'Conseil':
            $requestSQL = $bdd->query('SELECT * FROM Conseil');
            $fp = fopen("Conseil.csv", "w");
            $resultatRequete = $requestSQL->fetchAll();
        break;
        case 'FilDeDiscussion':
            $requestSQL = $bdd->prepare('SELECT * FROM FilDeDiscussion WHERE idFilDeDiscussion = ?');

            foreach($idFilUtilisateur as $idFil){
                $requestSQL->execute(array($idFil)); 
                $resultatRequete [] = $requestSQL->fetch();
            }

            $fp = fopen("FilDeDiscussion.csv", "w");
        break;
        case 'MessagePrive':
            $requestSQL = $bdd->prepare('SELECT * FROM MessagePrive WHERE idFilDeDiscussion = ?');

            foreach($idFilUtilisateur as $idFil){
                $requestSQL->execute(array($idFil)); 
                while ($res = $requestSQL->fetch()){
                    $resultatRequete [] = $res;
                }
            }
            $fp = fopen("MessagePrive.csv", "w");
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
 *              $idFilUtilisateur   ->  Liste des fils de discussion dont fait partie l'utilisateur
 */

function besoinMaj($cle, $table, $derniereMAJ, $bdd, $idFilUtilisateur){
    $resultatRequete = array();

    switch($table){
        case 'Utilisateur':
            $requestSQL = $bdd->prepare('SELECT * FROM Utilisateur WHERE updated_at > ? AND cle = ? ');
            $requestSQL->execute(array($derniereMAJ, $cle));
            $resultatRequete = $requestSQL->fetchAll();
        break;
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
            foreach($idFilUtilisateur as $idFil){
                $requestSQL->execute(array($derniereMAJ, $idFil)); 
                $resultatRequete [] = $requestSQL->fetch();
            }
        break;
        case 'MessagePrive':
            $requestSQL = $bdd->prepare('SELECT * FROM MessagePrive WHERE created_at > ? AND idFilDeDiscussion = ?');

            foreach($idFilUtilisateur as $idFil){
                $requestSQL->execute(array($derniereMAJ, $idFil)); 
                $resultatRequete [] = $requestSQL->fetch();
            }
        break;
    }
    return (count($resultatRequete) != 0);
}

/*** 
 * FONCTION listeFildeDiscussion
 * Créer la liste des fil de discussion auquelle appartient l'utilisateur
 * Entrées :    $cle    ->  Cle de l'utilisateur
 *              $bdd    ->  Base de donnee connectée
 */
function listeFildeDiscussion($cle, $bdd){
    $requestSQL = $bdd->prepare('SELECT idUtilisateur FROM Utilisateur WHERE cle = ?');
    $requestSQL->execute(array($cle));
    $idUtilisateur = ($requestSQL->fetch())['idUtilisateur'];

    $requestSQL = $bdd->prepare('SELECT idFilDeDiscussion FROM FilDeDiscussion WHERE idUtilisateur = ?');
    $requestSQL->execute(array($idUtilisateur));
    
    $idFilUtilisateur = array();
    while ($idFil = $requestSQL->fetch()){
        $idFilUtilisateur[] = $idFil['idFilDeDiscussion'];
    }
    return $idFilUtilisateur;
}

/***
 * Debut du script
 * Information attendu :
 *      Derniere mise a jour de la bdd local a l'utilisateur 
 *      Cle de l'utilisateur (Facultatif)
 */

$info = $request->getParsedBody();

//Creation de la date de derniere mise a jour au bon format
$dateDerniereMAJEpoch = $info['derniere_mise_a_jour'];
$dateDerniereMAJ = new DateTime("@$dateDerniereMAJEpoch");
$dateDerniereMAJ = $dateDerniereMAJ->format('Y-m-d H:i:s'); 

$idFilUtilisateur = array();

if (empty($info['derniere_mise_a_jour'])){//Si date de derniere mise a jour non renseignée
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
            $tables = array('Utilisateur','CodeActivite', 'Commune', 'Conseil', 'FilDeDiscussion', 'MessagePrive');
            $idFilUtilisateur = listeFildeDiscussion($info['cle_identification'], $bdd);
    }
} else {
    $info['cle_identification'] = NULL;
    $tables = array('CodeActivite', 'Commune', 'Conseil');
}
if (empty($retour['error'])){
    $retour['success'] = true;

    foreach($tables as $table){
        if (besoinMaj($info['cle_identification'], $table, $dateDerniereMAJ, $bdd, $idFilUtilisateur)){
            creerCSV($info['cle_identification'], $table, $bdd, $idFilUtilisateur);
            $retour['success'] = false;
        }
    }
}

header('Content-type: application/json');

echo json_encode($retour);
?>