<?php
session_start();
//Pour les testes avec Postman
$_SESSION['id'] =12345;

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

if (empty($_POST['mdpActuel'])){
    $retour['success'] = false;
    $retour['message'] = 'Il manque des infos';
} else {
    //Test du mdp
    $requeteMdp = $bdd->prepare('SELECT mdpGestionnaire FROM Gestionnaire WHERE idGestionnaire = ?');
    $requeteMdp->execute(array($_SESSION['id']));
    $mdpA = $requeteMdp->fetch();
    $mdp = $mdpA['mdpGestionnaire'];
    if ($mdp != $_POST['mdpActuel']){
        $retour['success'] = false;
        $retour['message'] = 'mdp incorrect';
    } else {
        $retour['success'] = true;
        $retour['message'] = 'Info modifiee(s)';
        if (!empty($_POST['nouveauMdp'])){
            if (empty($_POST['confirmationMdp']) OR $_POST['nouveauMdp'] != $_POST['confirmationMdp']){
                $retour['success'] = false;
                $retour['message'] = 'Les mdp ne correspondent pas';
            } else {
                $requeteModifierMdp = $bdd->prepare('UPDATE `Gestionnaire` SET mdpGestionnaire = ? WHERE idGestionnaire = ?');
                $requeteModifierMdp->execute(array($_POST['nouveauMdp'], $_SESSION['id']));
            }
        } if (!empty($_POST['nom'])){
            $requeteModifierMdp = $bdd->prepare('UPDATE `Gestionnaire` SET nomGestionnaire = ? WHERE idGestionnaire = ?');
            $requeteModifierMdp->execute(array($_POST['nom'], $_SESSION['id']));
        } if (!empty($_POST['prenom'])){
            $requeteModifierMdp = $bdd->prepare('UPDATE `Gestionnaire` SET prenomGestionnaire = ? WHERE idGestionnaire = ?');
            $requeteModifierMdp->execute(array($_POST['prenom'], $_SESSION['id']));
        } if (!empty($_POST['mail'])){
            $requeteModifierMdp = $bdd->prepare('UPDATE `Gestionnaire` SET mail = ? WHERE idGestionnaire = ?');
            $requeteModifierMdp->execute(array($_POST['mail'], $_SESSION['id']));
        }
    }
}
echo json_encode($retour);
?>
