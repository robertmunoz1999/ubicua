<?php
    
    ini_set('display_errors', 1);
    ini_set('display_startup_errors', 1);
    error_reporting(E_ALL);
    
    /*$token ="1562371383:AAECc-TSUOn4zcAq9pnGf-9XGnO82VTrg6Y"    
    $website = "https://api.telegram.org/bot".$token;
    $web = "https://api.telegram.org/file/bot".$token;
    $send_notifications= FALSE;
    $update = file_get_contents('php://input');
    $update = json_decode($update,TRUE);
    */


    /*$chatId = $update["message"]["from"]["id"];
    $name = $updates['message']['from']['first_name'];
    $text = $update["message"]["text"];*/
    $apiToken = "1562371383:AAECc-TSUOn4zcAq9pnGf-9XGnO82VTrg6Y";

$data = [
    'chat_id' => '@Basur-app',
    'text' => 'La temperatura es demasiado elevada, llame a los bomberos'
];

$response = getSslPage("https://api.telegram.org/bot$apiToken/sendMessage?
" . http_build_query($data) );
    
    if(
           !empty($_POST['id_cube']) 
        || !empty($_POST['capacidad']) 
        || !empty($_POST['temperatura']) 
        || !empty($_POST['humo']) 
        || !empty($_POST['metano']) 
        || !empty($_POST['co2']) 
        || !empty($_POST['MQ2_resistencia']) 
        || !empty($_POST['MQ135_resistencia']) 
        ){
        $id_cube = $_POST['id_cube'];
        $capacidad = $_POST['capacidad'];
        $co2 = $_POST['co2'];
        $metano = $_POST['metano'];
        $humo = $_POST['humo'];
        $temperatura = $_POST['temperatura'];
        $resistance1 = $_POST['MQ2_resistencia'];
        $resistance2 = $_POST['MQ135_resistencia'];
        
        /*if(temperatura>50){
            sendMsg($msgID,"Alerta, el cubo $id_cube tiene demasiada temperatura",$website)
        }*/
    
    	//insertCubeDataRecord($id_cube, $capacidad, $co2, $metano, $humo, $temperatura, $resistance1, $resistance2);
    }
function getSslPage($url){
    $ch = curl_init();
    curl_setopt($ch ,CURLOPT_SSL_VERIFYPEER,FALSE); 
    curl_setopt($ch ,CURLOPT_HEADER, false);
    curl_setopt($ch , CURLOPT_FOLLOWLOCATION,true);
    curl_setopt($ch ,CURLOPT_URL, $url);
    curl_setopt($ch ,CURLOPT_REFERER, $url);
    curl_setopt($ch ,CURLOPT_RETURNTRANSFER,TRUE);
    $result= curl_exec($ch);
    curl_close($ch);
    return $result;

}

function insertCubeDataRecord($id_cube, $capacity, $co2, $methane, $smoke, $temperature, $resistance1, $resistance2){
    $host = "localhost";
    $dbname = "postgres";
    $username = "postgres";
    $password = "12345";

    $db_handle=pg_connect("host=$host dbname=$dbname user=$username password=$password");

    $sql = "SELECT MAX(id) FROM cube_data_record;";

    $result = pg_query($db_handle,$sql);

    $id = pg_fetch_row($result)[0]+1;

    $db_handle=pg_connect("host=$host dbname=$dbname user=$username password=$password");

    $timestamp = date("Y-m-d H:i:s");
    $data_timestamp = "'$timestamp'";
    $sql = "insert into CUBE_DATA_RECORD (id_cube, id, capacity, co2, methane, smoke, data_timestamp, temperature, MQ2_resistance, MQ135_resistance) values ($id_cube, $id, $capacity, $co2, $methane, $smoke, $data_timestamp, $temperature, $resistance1, $resistance2)";

    $result = pg_query($db_handle,$sql);
}

function sendMsg($msgID,$text,$website){
    $url= $website."/sendMessage?chat_id=.$msgID&text=".urlencode($text);
    file_get_contents($url);
}

?>