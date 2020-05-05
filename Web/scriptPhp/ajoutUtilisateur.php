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
if (!empty($_POST['codeActivite']) AND !empty($_POST['idCommune']) 
    AND !empty($_POST['telephone']) AND !empty($_POST['mail']) AND !empty($_POST['chambre'])){
        
        $requete = $bdd->prepare('  INSERT INTO `Utilisateur`(`codeAct`, `idCommune`, `telephone`, `mail`, `chambre`) 
                                    VALUES (?,?,?,?,?)');
        $requete->execute(array(
            $_POST['codeActivite'],
            $_POST['idCommune'],
            $_POST['telephone'],
            $_POST['mail'],
            $_POST['chambre']
        ));
        $retour['success'] = true;
} else {
    $retour['success'] = false;
    $retour['message'] = 'Il manque des infos';
}

echo json_encode($retour);
?>