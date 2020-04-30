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

if (empty($_POST['mdp_actuel'])){
    $retour['success'] = false;
    $retour['message'] = 'Il manque des infos';
} else {
    //Test du mdp
    $requeteMdp = $bdd->prepare('SELECT mdp_gestionnaire FROM Gestionnaire WHERE id_gestionnaire = ?');
    $requeteMdp->execute(array($_SESSION['id']));
    $mdpA = $requeteMdp->fetch();
    $mdp = $mdpA['mdp_gestionnaire'];
    if ($mdp != $_POST['mdp_actuel']){
        $retour['success'] = false;
        $retour['message'] = 'mdp incorrect';
    } else {
        $retour['success'] = true;
        $retour['message'] = 'Info modifiee(s)';
        if (!empty($_POST['nouveau_mdp'])){
            if (empty($_POST['confirmation_mdp']) OR $_POST['nouveau_mdp'] != $_POST['confirmation_mdp']){
                $retour['success'] = false;
                $retour['message'] = 'Les mdp ne correspondent pas';
            } else {
                $requeteModifierMdp = $bdd->prepare('UPDATE `Gestionnaire` SET mdp_gestionnaire = ? WHERE id_gestionnaire = ?');
                $requeteModifierMdp->execute(array($_POST['nouveau_mdp'], $_SESSION['id']));
            }
        } if (!empty($_POST['nom'])){
            $requeteModifierMdp = $bdd->prepare('UPDATE `Gestionnaire` SET nom_gestionnaire = ? WHERE id_gestionnaire = ?');
            $requeteModifierMdp->execute(array($_POST['nom'], $_SESSION['id']));
        } if (!empty($_POST['prenom'])){
            $requeteModifierMdp = $bdd->prepare('UPDATE `Gestionnaire` SET prenom_gestionnaire = ? WHERE id_gestionnaire = ?');
            $requeteModifierMdp->execute(array($_POST['prenom'], $_SESSION['id']));
        } if (!empty($_POST['mail'])){
            $requeteModifierMdp = $bdd->prepare('UPDATE `Gestionnaire` SET mail = ? WHERE id_gestionnaire = ?');
            $requeteModifierMdp->execute(array($_POST['mail'], $_SESSION['id']));
        }
    }
}
echo json_encode($retour);
?>
