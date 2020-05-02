<?php
$success = json_encode(array(
	'id_utilisateur' => "1", 
	'mail' => "tempMail", 
	'numero_tel' => "0606060606", 
	'chambre' => "CMA", 
	'code_postal' => "31302", 
	'num_secteur' => "3", 
	'code_act' => 11
));

$response = array('success' => $success);
if(isset($_POST["cle_identification"]))
	echo json_encode($response);
?>