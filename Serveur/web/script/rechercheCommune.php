<?php
include './connexionBDD.php';

$term = $_GET['term'];

$requete = $bdd->prepare('SELECT * FROM Commune WHERE commune LIKE ? LIMIT 7');
$requete->execute(array('%'.$term.'%'));

$array = array(); 

while($donnee = $requete->fetch()){
    array_push($array, $donnee['codePostal'] . ', ' . $donnee['commune'] . ', ' . $donnee['idCommune']); 
}

echo json_encode($array); 

?>