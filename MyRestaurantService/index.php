<?php
include_once "config.php";

$url = explode('/',$_SERVER['REQUEST_URI']); 

// xử lý POST JSON
$inputJSON = file_get_contents('php://input');
$_POST = json_decode( $inputJSON, TRUE );

$model = strtolower($url[1]);
$action = strtolower($url[2]);
$path = 't7/'.$model.'/'.$action.'.php';
if(file_exists($path)){
    header('Content-type: application/json; charset=utf-8');    
    $outp = array();
    $outp['hasErr'] = true;
    $outp['result'] = array('message'=>'Method not support');
    $outp['size'] = 0;
    include_once $path;
    echo json_encode($outp,JSON_UNESCAPED_UNICODE);
}else{
    phpinfo();
}
$mysqli->close();
?>