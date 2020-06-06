<?php
include './connexionBDD.php';

$term = $_GET['term'];

$requete = $bdd->prepare('SELECT * FROM codeActivite WHERE activite LIKE ? LIMIT 7');
$requete->execute(array('%'.$term.'%'));

$array = array(); 

while($donnee = $requete->fetch()){
    array_push($array, $donnee['code'] . ', ' . $donnee['activite']); 
}

echo json_encode($array); 

?>