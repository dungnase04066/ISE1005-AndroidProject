<?php
$id = intval($_GET['id']);
if($id>0){
    $sql =  "SELECT * FROM `order_detail` WHERE `order_id` = {$id} ";
    $rs = $mysqli->query($sql);
    $details = $rs->fetch_all(MYSQLI_ASSOC);
    $outp['result'] = array();
    foreach($details as $detail){
        $psql =  "SELECT * FROM `products` WHERE `id`={$detail['product_id']}";
        $prs = $mysqli->query($psql);
        $detail['product'] = array_pop($prs->fetch_all(MYSQLI_ASSOC));
        $outp['result'][] = $detail;
    }
    $outp['size'] = $rs->num_rows;
    $outp['hasErr'] = false;
}
?>