<?php
use Psr\Http\Message\ResponseInterface as Response;
use Psr\Http\Message\ServerRequestInterface as Request;
use Slim\Factory\AppFactory;

require __DIR__ . '/vendor/autoload.php';

$app = AppFactory::create();

$app->post('/inscription', function (Request $request, Response $response) {
    include 'connexionBdd.php';
    include 'inscription.php';
    return $response;
});

$app->post('/connexion', function (Request $request, Response $response) {
    include 'connexionBdd.php';
    include 'connexionUtilisateur.php';
    return $response;
});

$app->post('/updatebdd', function (Request $request, Response $response) {
    include 'connexionBdd.php';
    include 'updateBdd.php';
    return $response;
});

$app->post('/test', function (Request $request, Response $response) {
    include 'connexionBdd.php';
    include 'test.php';
    return $response;
});


$app->run();

?>