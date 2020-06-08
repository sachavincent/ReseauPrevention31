<?php
header('Content-type: application/json');

$info = $request->getParsedBody();

if (empty($info['cle_identification'])){
    $retour['error'] = 103;
} elseif(!preg_match(' /^[0-9]{13}$/ ', $info['cle_identification'])) {
    $retour['error'] = 101;
} else{
    $requeteSQL = $bdd->prepare('   SELECT idUtilisateur, cle, nomUtilisateur, prenomUtilisateur, chambre, codeAct, mail, telephone, nomSociete, idCommune, siret, secteur 
                                    FROM Utilisateur WHERE cle = ?');
    $requeteSQL->execute(array($info['cle_identification']));
    $info = $requeteSQL->fetch();
    if (empty($info)){
        $retour['error'] = 100;
    } else {
	$retour['success']['id'] = $info['idUtilisateur'];
        $retour['success']['cle_utilisateur'] = $info['cle'];
        $retour['success']['prenom'] = $info['prenomUtilisateur'];
        $retour['success']['nom'] = $info['nomUtilisateur'];
        $retour['success']['chambre'] = $info['chambre'];
        $retour['success']['code_activite'] = $info['codeAct'];
        $retour['success']['mail'] = $info['mail'];
        $retour['success']['num_telephone'] = $info['telephone'];
        $retour['success']['nom_societe'] = $info['nomSociete'];
        $retour['success']['id_commune'] = $info['idCommune'];
        $retour['success']['num_siret'] = $info['siret'];
        $retour['success']['secteur'] = $info['secteur'];
    }
}
echo json_encode($retour);
?>
