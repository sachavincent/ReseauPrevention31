<?php
if (!empty($_POST['commune2'])){
    $idCommune2 = explode(', ', $_POST['commune2'])[2];
echo $idCommune2;
}
$idCommune1 = explode(', ', $_POST['commune1'])[2];
echo $idCommune1;
?>