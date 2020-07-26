# server_masc_2020
Projeto com Exemplos de Chamadas ao Servidor Masc.

Algumas chamdas podem ser executadas pelo link: http://ec2-34-217-138-53.us-west-2.compute.amazonaws.com:8089/

Abaixo seguem algumas url, o conteúdo que consomem e o seu retorno:



$url  = 'http://ec2-user@ec2-34-217-138-53.us-west-2.compute.amazonaws.com:8089';
$url  .= '/listarRec';
$data = ['data' => 'PcD;Membros Inferiores;-29.762738198684357;-51.151206493377686'];
-> retorna:
 {
    "distance": "2052.259127223223",
    "latitude": "-29.792305947916550",
    "nome": "Rampa",
    "icone": "amarelo",
    "descricao": "Membros Inferiores",
    "longitude": "-51.154482307030530"
  },
  
  


$url  .= '/registraTrilha';
$data = ['data' => '29.791959945956947;-51.15311086177826;0; 0;blanch@server.com.br'];
-> retorna:
[
  {
    "email": "blanch@server.com.br",
    "status": "trilha ok"
  }
]


$url  .= '/verificaTrilhaFinal';
$data = ['data' => '-29.762738198684357;-51.151206493377686;marcelojtelles@gmail.com;-29.768084050351185;-51.145241260528564'];
-> retorna:
[
  {
    "id": 0,
    "lat": "-29.762738198684357",
    "lon": "-51.151206493377686"
  },
...
]

$url  .= '/trilhaUsuario';
$data = ['data' => '2015-10-27;Blanch@server.com.br'];
-> retorna:
[
  {
    "id": 1,
    "lat": "-29.762738198684357",
    "lon": "-51.151206493377686"
  },
  ...
]

$url  .= '/rotasMaisAcessadas';
$data = ['data' => '12;-29.763483282226453;-51.14838480949402'];
-> retorna: 5 objetos

[
  [
    {
      "id": 1,
      "lat": "-29.763493142125790",
      "lon": "-51.148388433140630"
    },
    {
      "id": 2,
      "lat": "-29.763493142125790",
      "lon": "-51.148399114608765"
    },
    ..
  ]
 ]

$url  .= '/logar';
$data = ['data' => 'paulo@server.com;aa'];
-> retorna:
[
  {
    "tipoUser": "Estudante",
    "tipoDef": "Visual",
    "status": "ok"
  }
]


$url  .= '/cadastro';
$data = ['nome' => 'Teste',
		'email' => 'teste@server.com',
		'senha' => '123',
		'tipoUser'=> 'PcD',
		'tipoDef'=> 'Membros Inferiores'];
-> retorna:
{
  "status ": "Cadastro ok;"
}


$url  .= '/ultimaPosicao';
$data = ['data' => 'Membros inferiores;Membros superiores#Blanch@server.com.br'];
-> retorna:
[
  {
    "nome": "Blanch@server.com.br",
    "lat": "-29.761266067012090",
    "lon": "-51.150898578151170",
    "dd": "2015-10-19 06:05:10",
    "id": "Blanch@server.com.br 2015-10-19 06:05:10"
  }
]
