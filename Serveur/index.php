<?php
use Psr\Http\Message\ResponseInterface as Response;
use Psr\Http\Message\ServerRequestInterface as Request;
use Slim\Factory\AppFactory;

require __DIR__ . '/vendor/autoload.php';

$app = AppFactory::create();

$app->post('/inscription', function (Request $request, Response $response) {
    include './web/script/connexionBDD.php';
    include './appli/script/inscription.php';
    return $response;
});

$app->post('/connexion', function (Request $request, Response $response) {
    include './web/script/connexionBDD.php';
    include './appli/script/connexionUtilisateur.php';
    return $response;
});

$app->post('/updatebdd', function (Request $request, Response $response) {
    include './web/script/connexionBDD.php';
    include './appli/script/updateBdd.php';
    return $response;
});

$app->run();

?>
