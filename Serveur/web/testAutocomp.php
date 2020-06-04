<?php
include 'script/connexionBDD.php';
$requete = $bdd->query('SELECT codePostal, commune FROM Commune');
$commune = $requete->fetchAll();

$result = array();
foreach ($commune as $com) {
    array_push( $result, $com['commune'] );
}

?>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
</head>
<body>
<form>
    <input type="text" id="recherche"/>
</form>
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script>
var commune = <?echo (json_encode($result))?>
  $(function() {
    $("#recherche").autocomplete({
      source: commune
    });
  });
</script>
</body>
</html>