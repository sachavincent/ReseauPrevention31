<?php
header('Content-type: application/json');

$info = $request->getParsedBody();

//Test si variables recus
if ( (empty($info['prenom']) OR empty($info['nom']) OR empty($info['mail'])
        OR empty($info['chambre'])OR empty($info['code_activite'])OR empty($info['num_telephone'])
        OR empty($info['num_siret'])OR empty($info['secteur'])OR empty($info['nom_societe']))){
    $retour['error'] = 100;
} // Test valididée adresse mail 
elseif(!preg_match("#^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,}$#", strtoupper($info['mail']))) {
    $retour['error'] = 103;
} //Ajout de l'utilisateur a la bdd
else {
    $requeteSQL = $bdd->prepare(' INSERT INTO `Utilisateur` (`nomUtilisateur`, `prenomUtilisateur`, `siret`, `codeAct`, `idCommune`, `secteur`,  `telephone`, `mail`, `chambre`, `nomSociete`)
                                VALUE (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)');
    if (! empty($info['id_commune'])){
        $requeteSQL->execute(array(
            $info['nom'], $info['prenom'], $info['num_siret'],
            $info['code_activite'], $info['id_commune'], $info['secteur'], $info['num_telephone'],
            $info['mail'], $info['chambre'], $info['nom_societe']
        ));
    } else {
        $requeteSQL->execute(array(
            $info['nom'], $info['prenom'], $info['num_siret'],
            $info['code_activite'], NULL, $info['secteur'], $info['num_telephone'],
            $info['mail'], $info['chambre'], $info['nom_societe']
        ));
    }
    $retour['success'] = true;
}

echo json_encode($retour);
?>