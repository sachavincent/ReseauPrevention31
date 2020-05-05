<?php
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

$result = $bdd->query('SELECT * FROM `codeActivite`');
if (!$result) die('Couldn\'t fetch records');
$num_fields = $result->columnCount();
$headers = array();
for ($i = 0; $i < $num_fields; $i++) {
    $headers[] = $result->getColumnMeta($i);
}
$fp = fopen('php://output', 'w');
if ($fp && $result) {
    header('Content-Type: text/csv');
    header('Content-Disposition: attachment; filename="export.csv"');
    header('Pragma: no-cache');
    header('Expires: 0');
    fputcsv($fp, $headers);
    while ($row = $result->fetch()) {
        fputcsv($fp, array_values($row));
    }
    die;
}
?>