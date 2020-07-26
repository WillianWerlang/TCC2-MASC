package org.example;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;



@RestController
public class Controller {
    JSONArray array = new JSONArray();
    int cmd;
    String comando;
    String result;
    Connection con;
    Statement stmt;
    ResultSet r;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/add")
    public String addCustomer(@RequestParam String nome, @RequestParam String email, @RequestParam String senha, @RequestParam String tipoUser, @RequestParam String tipoDef) {
        _masc_usuario user = new _masc_usuario();
        user.setNome(nome);
        user.setEmail(email);
        user.setSenha(senha);
        user.setTipoUser(tipoUser);
        user.setTipoDef(tipoDef);
        userRepository.save(user);
        return "Added new customer to repo!";
    }

    @GetMapping("/listuser")
    public Iterable<_masc_usuario> getUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/listaUsuario")
    public ArrayList<_masc_usuario> listaUsuario() {
        String comando;
        String nome,email,senha,tipoUser,tipoDef;
        String result="";

        comando = "SELECT nome,email,senha,tipoUser,tipoDef FROM _masc_usuario;";
        System.out.println("comando "+comando);

        try{
            con = Database.getConnection("dissys");
            stmt = con.createStatement();
            ResultSet r;
            r = stmt.executeQuery(comando);
            array = new JSONArray();
            while(r.next()){
                JSONObject record = new JSONObject();

                nome = r.getString("nome");
                email = r.getString("email");
                senha = r.getString("senha");
                tipoUser = r.getString("tipoUser");
                tipoDef = r.getString("tipoDef");

                record.put("tipoUser", tipoUser);
                record.put("tipoDef", tipoDef);
                record.put("senha", senha);
                record.put("email", email);
                record.put("nome", nome);

                array.add(record);

            }
            con.close();

        }catch (Exception e){
            //System.out.println("Erro na comunicação "+sistemaIp +" "+sistemaUser+" "+ e.getMessage());
            //System.out.println("Erro SQL "+comando+"");
            //e.printStackTrace();
            result="no;";
        }
        //result = result+tipoUser+";"+tipoDef;
        return array;
    }

    @GetMapping("/find/{id}")
    public _masc_usuario findUserById(@PathVariable Integer id) {
        return userRepository.findCustomerById(id);
    }

    //serviço 5 /listaRec
    @PostMapping("/listarRec")
    public ArrayList listarRecursos(@RequestParam String data) {
        //http://localhost:8081/testa_masc_server/config/posts.php?operacao=listaRec
        //http://localhost:8089/listarRec
        //data = $data = ['data' => 'PcD;Membros Inferiores;-29.762738198684357;-51.151206493377686'];
        System.out.println("postou "+data);

        String data2=data.replaceAll("'", "");
        String[] valores = data2.split(";");
        String tipoUser, tipoDef;
        String result="";

        comando ="SELECT nome, ((ACOS( SIN( "+valores[2]+" * PI( ) /180 ) * "
                + "SIN( latitude * PI( ) /180 ) + COS( "+valores[2]+" * PI( ) /180 ) * "
                + "COS( latitude * PI( ) /180 ) * COS( ("
                + valores[3] +" - longitude) * PI( ) /180 ) ) *180 / PI( )) * "
                + "60 * 1.1515) *1000 AS `distance`,_masc_tipo_def.descricao,"
                + "_masc_recurso_outdoor.latitude,_masc_recurso_outdoor.longitude,"
                + "_masc_tipo_recurso.icone "
                + "FROM `_masc_recurso_outdoor` "
                + "RIGHT JOIN _masc_tipo_def "
                + "ON _masc_tipo_def.id_tipodef = _masc_recurso_outdoor.id_tipodef "
                + "RIGHT JOIN _masc_tipo_recurso "
                + "ON _masc_tipo_recurso.id_tipoRecurso = _masc_recurso_outdoor.id_tiporecurso "
                + "WHERE _masc_tipo_def.Descricao = '"+valores[1]+"' "
                + "HAVING `distance` <=15000 ORDER BY `distance` ASC ;";

        System.out.println("comando ="+comando);
        String linha,nome,dist,desc,lat,lon,icone;

        try{
            con = Database.getConnection("dissys");
            stmt = con.createStatement();
            r = stmt.executeQuery(comando);
            array = new JSONArray();
            while(r.next()){
                JSONObject record = new JSONObject();
                nome = r.getString("nome");
                dist = r.getString("distance");
                desc = r.getString("descricao");
                lat = r.getString("latitude");
                lon = r.getString("longitude");
                icone = r.getString("icone");
                linha = nome+";"+dist+";"+desc+";"+lat+";"+lon+";"+icone;

                record.put("nome", r.getString("nome"));
                record.put("distance", r.getString("distance"));
                record.put("descricao", r.getString("descricao"));
                record.put("latitude", r.getString("latitude"));
                record.put("longitude", r.getString("longitude"));
                record.put("icone", r.getString("icone"));
                array.add(record);
                result+=linha+"\n";
            }

            con.close();
        }catch (Exception e){
            e.printStackTrace();
            result="no;";
        }
        return array;
    }

    //serviço 4 /registraTrilha") _masc_trilha
    @PostMapping("/registraTrilha")
    public ArrayList registraTrilha(@RequestParam String data) {
        //http://localhost:8081/testa_masc_server/config/posts.php?operacao=registraTrilha
        //http://localhost:8089/registraTrilha
        //data = $data = ['data' => 'PcD;Membros Inferiores;-29.762738198684357;-51.151206493377686'];

        System.out.println("postou "+data);
        result="";
        String data2=data.replaceAll("'", "");
        String[] valores = data2.split(";");

        comando = "INSERT into _masc_trilha (latitude,longitude,tag,recs,mail)"
                + "VALUES ("
                + "'"+valores[0]+"','"+valores[1]+"','"+valores[2]+"','"+valores[3]+"','"+valores[4]+"');";

        System.out.println("comando ="+comando);

        String status,email;

        try{
            JSONObject record = new JSONObject();
            array = new JSONArray();
            con = Database.getConnection("dissys");
            stmt = con.createStatement();
            cmd=stmt.executeUpdate(comando);
            if(cmd==1){
                status="trilha ok";
                email=valores[valores.length-1];
                record.put("status", status);
                record.put("email", email);
                array.add(record);
            }else{
                status="no";
                email=valores[valores.length-1];
                record.put("status", status);
                record.put("email", email);
                array.add(record);
            }
            con.close();

        }catch (Exception e){
            e.printStackTrace();
            array.add("no");
            array.add(valores[valores.length-1]);
        }
        return array;
    }

    //Serviço PcD, demora um pouco, mas vem
    @PostMapping("/verificaTrilhaFinal")
    public ArrayList verificaTrilhaFinal(@RequestParam String data) {
        //http://localhost:8081/testa_masc_server/config/posts.php?operacao=verificaTrilhaFinal
        //http://localhost:8089/verificaTrilhaFinal
        //data = $data = "-29.762738198684357;-51.151206493377686;marcelojtelles@gmail.com;-29.768084050351185;-51.145241260528564"

        System.out.println("postou "+data);

        Calculos calculos;
        calculos = new Calculos();
        String comando1,comando2,comando3,dataPrimeiro;
        String tipoUser, tipoDef;
        String result="";
        tipoUser="";
        tipoDef="";
        ResultSet rs1,rs2,rs3;
        Statement stmt1,stmt2,stmt3;
        ArrayList res1;
        res1=new ArrayList();
        ArrayList res2;
        res2=new ArrayList();
        ArrayList res3;
        res3=new ArrayList();
        Pontos p;

        ArrayList<Pontos> pontos=new ArrayList<>();

        boolean achou=false;
        boolean s1,s2,s3;
        s1=true;
        s2=true;
        s3=true;
        String lat,lon,desc,id;

        String data2=data.replaceAll("'", "");
        String[] valores = data2.split(";");
        System.out.println("postou "+data);

        //valores0 é latitudeIni
        //valores1 é longitudeIni
        //valores2 é email

        comando1="SELECT id,((ACOS( SIN( "+valores[0]+" * PI( ) /180 ) * SIN( latitude \n" +
                "* PI( ) /180 ) + COS( "+valores[0]+" * PI( ) /180 ) * COS( latitude * PI( ) \n" +
                "/180 ) * COS( ("+valores[1]+" - longitude) * PI( ) /180 ) ) *180 / PI( )) * \n" +
                "60 * 1.1515) *1000 AS `distance`, date_format(dataHora,'%Y-%m-%d') dd FROM \n" +
                "`_masc_trilhaSiafu27k` WHERE (SELECT tipoDef FROM _masc_usuario WHERE \n" +
                "email='"+valores[2]+"' LIMIT 1)=(SELECT tipoDef FROM _masc_usuario \n" +
                "WHERE email=mail LIMIT 1) HAVING `distance` <= 100 ORDER BY \n" +
                "`distance` ASC LIMIT 50";

        //System.out.println("comando ="+comando1);
        String linha,nome,dist,icone;
        int k=0;

        try{
            con = Database.getConnection("dissys");
            array = new JSONArray();

            stmt1 = con.createStatement();
            rs1 = stmt1.executeQuery(comando1);
            while(rs1.next()){
                id=rs1.getString("id");
                //System.out.println("id "+id);
                dataPrimeiro=rs1.getString("dd");

                //valores3 é latitudeFim
                //valores4 é longitudeFim

                comando2="SELECT id,latitude,longitude,date_format(dataHora,'%Y-%m-%d'),"+
                        "((ACOS( SIN( "+valores[3]+" * PI( ) /180 ) * SIN( latitude \n" +
                        "* PI( ) /180 ) + COS( "+valores[3]+" * PI( ) /180 ) * COS( latitude * PI( ) \n" +
                        "/180 ) * COS( ("+valores[4]+" - longitude) * PI( ) /180 ) ) *180 / PI( )) * \n" +
                        "60 * 1.1515) *1000 AS `distance` \n" +
                        "FROM `_masc_trilhaSiafu27k` \n" +
                        "WHERE id > "+id+" \n" +
                        "AND date_format(dataHora,'%Y-%m-%d')='"+dataPrimeiro+"' \n" +
                        "AND ((ACOS( SIN( "+valores[3]+" * PI( ) /180 ) * SIN( latitude \n" +
                        "* PI( ) /180 ) + COS( "+valores[3]+" * PI( ) /180 ) * COS( latitude * PI( ) \n" +
                        "/180 ) * COS( ("+valores[4]+" - longitude) * PI( ) /180 ) ) *180 / PI( )) * \n" +
                        "60 * 1.1515) *1000 < 10";
                stmt2 = con.createStatement();
                rs2 = stmt2.executeQuery(comando2);
                s2=true;
                while(rs2.next()){
                    s1=false;
                    if(rs2.getString("id")!=null){
                        comando3="SELECT id,latitude,longitude \n" +
                                "FROM `_masc_trilhaSiafu27k` \n" +
                                "WHERE id >= "+id+" AND id <= (SELECT id \n" +
                                "FROM `_masc_trilhaSiafu27k` \n" +
                                "WHERE id > "+id+" \n" +
                                "AND date_format(dataHora,'%Y-%m-%d')='"+dataPrimeiro+"'\n" +
                                "AND ((ACOS( SIN( "+valores[3]+" * PI( ) /180 ) * SIN( latitude \n" +
                                "* PI( ) /180 ) + COS( "+valores[3]+" * PI( ) /180 ) * COS( latitude * PI( ) \n" +
                                "/180 ) * COS( ("+valores[4]+" - longitude) * PI( ) /180 ) ) *180 / PI( )) * \n" +
                                "60 * 1.1515) *1000 < 10 LIMIT 1)";

                        stmt3 = con.createStatement();
                        //System.out.println("33Select enviado para consulta de trilha 3\n"+comando3);
                        rs3 = stmt3.executeQuery(comando3);
                        int conta=0;
                        while(rs3.next()){
                            conta++;
                        }
                        rs3 = stmt3.executeQuery(comando3);
                        int limite = conta;
                        //rs3.next();
                        if(limite>3){
                            limite=3;
                        }
                        //System.out.println("Limite "+limite);
                        //for (int j = 0; j < limite; j++) {
                        int j=0;
                        while(rs3.next()){
                            //System.out.println("entrou");
                            s2=false;
                            lat = rs3.getString("latitude");
                            lon = rs3.getString("longitude");
                            p=new Pontos(k++,lat,lon);
                            pontos.add(p);
                            //if(calculos.verificaDistancia(5000,Double.parseDouble(lat),Double.parseDouble(lon), Double.parseDouble(valores[0]), Double.parseDouble(valores[1]))){
                            //   achou=true;
                            //}
                            //rs3.next();
                            //if(k==3){
                            //    break;
                            //}
                        }
                    }else{
                        System.out.println("Saiu 2");
                        break;

                    }
                    //System.out.println("w 2");
                }
                //System.out.println("w 1");
            }

            con.close();
        }catch (Exception e){
            //System.out.println("Erro na comunicação "+sistemaIp +" "+sistemaUser+" "+ e.getMessage());
            //System.out.println("Erro SQL "+comando+"");
            //e.printStackTrace();
            result="no;";
        }

        return pontos;
    }

    //Serviço Prefeitura 1
    @PostMapping("/trilhaUsuario")
    public ArrayList trilhaUsuario(String data) {
        //http://localhost:8081/testa_masc_server/config/posts.php?operacao=trilhaUsuario
        //http://localhost:8089/trilhaUsuario
        //data = $data = "2015-10-27;Blanch@server.com.br"

        String comando1;
        ResultSet rs1;
        Statement stmt1;
        Pontos p;
        ArrayList pontos;
        pontos = new ArrayList<Pontos2>();
        String data2=data.replaceAll("'", "");
        String[] valores = data2.split(";");
        System.out.println("postou "+data);

        //valores0 é data
        //valores1 é email
        comando1="SELECT id, mail, latitude, longitude, date_format(dataHora,'%Y-%m-%d') dd "
                + "FROM `_masc_trilhaSiafu27k` "
                + "WHERE date_format(dataHora,'%Y-%m-%d')='"+valores[0]+"' "
                + "AND mail='"+valores[1]+"'";

        System.out.println("comando1 ="+comando1);

        String linha,nome,dist,desc,lat,lon,icone;
        try{
            con = Database.getConnection("dissys");
            stmt = con.createStatement();

            rs1 = stmt.executeQuery(comando1);

            String mail,id;
            int i=0;
            while(rs1.next()){
                i++;
                id=rs1.getString("id");
                mail =rs1.getString("mail");
                lat = rs1.getString("latitude");
                lon = rs1.getString("longitude");
                data = rs1.getString("dd");
                p=new Pontos(i,lat,lon);
                pontos.add(p);
            }

            con.close();
        }catch (Exception e){
            //System.out.println("Erro na comunicação "+sistemaIp +" "+sistemaUser+" "+ e.getMessage());
            //System.out.println("Erro SQL "+comando+"");
            //e.printStackTrace();
            result="no;";
        }
        return pontos;
    }

    //Serviço prefeitura 2  vem as 5 mais perto
    @PostMapping("/rotasMaisAcessadas")
    public ArrayList rotasMaisAcessadas(String data) {
        //http://localhost:8081/testa_masc_server/config/posts.php?operacao=rotasMaisAcessadas
        //http://localhost:8089/rotasMaisAcessadas
        //data = $data = "12;-29.763483282226453;-51.14838480949402"

        String data2=data.replaceAll("'", "");
        String[] valores = data2.split(";");
        System.out.println("postou "+data);

        Connection connection;
        List<Coordinate> coordinates = new ArrayList<Coordinate>();
        String comando1,comando2;
        ResultSet rs1,rs2,rs3;
        Statement stmt1,stmt2,stmt3;
        Vector tadd = new Vector<>();
        ArrayList pontos = new ArrayList<Pontos>();
        ArrayList pontos3 = new ArrayList<Pontos3>();
        ArrayList pontosResultado = new ArrayList<Pontos>();


        //valores0 é raio
        //valores1 é latitudeIni1
        //valores2 é longitudeIni1

        String linha,nome,dist,desc,lat,lon;

        try{
            int raio = Integer.parseInt(valores[0]);

            comando1="SELECT id, latitude, longitude, mail, ((ACOS( SIN( "+valores[1]+" * PI( ) /180 ) * SIN( latitude \n" +
                    "* PI( ) /180 ) + COS( "+valores[1]+" * PI( ) /180 ) * COS( latitude * PI( ) \n" +
                    "/180 ) * COS( ("+valores[2]+" - longitude) * PI( ) /180 ) ) *180 / PI( )) * \n" +
                    "60 * 1.1515) *1000 AS `distance`, date_format(dataHora,'%Y-%m-%d') dd FROM \n" +
                    "`_masc_trilhaSiafu27k` HAVING `distance` <= "+raio+" ORDER BY \n" +
                    "`distance` ASC";

            //System.out.println("Comando 1"+comando1);
            con = Database.getConnection("dissys");
            stmt = con.createStatement();
            rs1 = stmt.executeQuery(comando1);
            String llat,llon,mail,id;
            llat="";
            llon="";
            String[] llat2;
            String[] llon2;
            llat2=new String[10];
            llon2=new String[10];

            Rotinas r;
            r = new Rotinas();
            int total;
            int i=0;

            Pontos3 p3;
            Pontos p;

            while(rs1.next()){
                i++;
                //System.out.println("entrou na primeira consulta");
                id=rs1.getString("id");
                mail =rs1.getString("mail");
                lat = rs1.getString("latitude");
                lon = rs1.getString("longitude");
                data = rs1.getString("dd");
                p=new Pontos(i,lat,lon);
                p3 = new Pontos3(id,mail,lat,lon,data);
                pontos.add(p);
                pontos3.add(p3);
            }

            Pontos3 p3lido;
            int limite=pontos3.size();
            if(pontos3.size()>5){
                limite=5;
            }
            for (int j = 0; j < limite; j++) {
                p3lido=(Pontos3)pontos3.get(j);

                comando2="SELECT id, latitude, longitude, mail, date_format(dataHora,'%Y-%m-%d') dd FROM \n" +
                        "`_masc_trilhaSiafu27k` "
                        + "WHERE date_format(dataHora,'%Y-%m-%d')='"+p3lido.dd+"' "
                        + "AND mail='"+p3lido.mail+"'";

                stmt2 = con.createStatement();
                //System.out.println("2Select enviado para consulta de trilha 2\n"+comando2);
                rs2 = stmt2.executeQuery(comando2);

                double latk,lonk;
                int t=0;
                coordinates = new ArrayList<Coordinate>();

                while(rs2.next()){
                    //System.out.println(""+t);
                    latk=Double.parseDouble(rs2.getString("latitude"));
                    lonk=Double.parseDouble(rs2.getString("longitude"));
                    //coordinates.add(new Coordinate(latk,lonk));
                    p = new Pontos(t, rs2.getString("latitude"), rs2.getString("longitude"));
                    pontos.add(p);
                    t++;
                }
                pontosResultado.add(pontos);
                //tadd.add(coordinates);
            }

            con.close();
        }catch (Exception e){
            //System.out.println("Erro na comunicação "+sistemaIp +" "+sistemaUser+" "+ e.getMessage());
            //System.out.println("Erro SQL "+comando+"");
            //e.printStackTrace();
            result="no;";
        }

        return pontosResultado;
    }

    //logar
    @PostMapping("/logar")
    public ArrayList logar(String data) {
        //http://localhost:8081/testa_masc_server/config/posts.php?operacao=rotasMaisAcessadas
        //http://localhost:8089/rotasMaisAcessadas
        //data = $data = "paulo@server.com;aa"

        String comando;
        String tipoUser, tipoDef;
        String result="";
        tipoUser="";
        tipoDef="";
        String data2=data.replaceAll("'", "");
        String[] valores = data2.split(";");

        comando = "SELECT nome,email,senha,tipoUser,tipoDef FROM _masc_usuario"
                + " WHERE email='"+valores[0]+"' AND "
                + " senha='"+valores[1]+"';";

        System.out.println("comando "+comando);

        try{
            con = Database.getConnection("dissys");
            stmt = con.createStatement();
            ResultSet r;
            r = stmt.executeQuery(comando);
            array = new JSONArray();
            while(r.next()){
                JSONObject record = new JSONObject();
                if ( (r.getString("email").equals(valores[0])) && (r.getString("senha").equals(valores[1]))){
                    tipoUser = r.getString("tipoUser");
                    tipoDef = r.getString("tipoDef");
                    result += "ok;";
                    result += tipoUser+";";
                    result += tipoDef+";";

                    record.put("status", "ok");
                    record.put("tipoUser", tipoUser);
                    record.put("tipoDef", tipoDef);

                    array.add(record);
                }
            }
            con.close();

        }catch (Exception e){
            //System.out.println("Erro na comunicação "+sistemaIp +" "+sistemaUser+" "+ e.getMessage());
            //System.out.println("Erro SQL "+comando+"");
            //e.printStackTrace();
            result="no;";
        }
        //result = result+tipoUser+";"+tipoDef;
        return array;
    }

    //cadastro
    @PostMapping("/cadastro")
    public JSONObject cadastro(@RequestParam String nome, @RequestParam String email, @RequestParam String senha, @RequestParam String tipoUser, @RequestParam String tipoDef) {
        //http://localhost:8081/testa_masc_server/config/posts.php?operacao=cadastro
        //http://localhost:8089/cadastro
        //data = $data = ['nome' => 'Teste',
        //			'email' => 'teste@server.com',
        //			'senha' => '123',
        //			'tipoUser'=> 'PcD',
        //			'tipoDef'=> 'Membros Inferiores'];

        _masc_usuario user = new _masc_usuario();
        user.setNome(nome);
        user.setEmail(email);
        user.setSenha(senha);
        user.setTipoUser(tipoUser);
        user.setTipoDef(tipoDef);
        JSONObject record = new JSONObject();

        try {
            //userRepository.save(user);
            comando = "INSERT into _masc_usuario (nome,email,senha,tipoUser,tipoDef) "
                    + "VALUES ("
                    + "'"+nome+"','"+email+"','"+senha+"','"+tipoUser+"','"+tipoDef+"');";

            String comandoSelect = "SELECT email FROM _masc_usuario"
                    + " WHERE email='"+email+"' ;";

            int tem=0;
            try{
                con = Database.getConnection("dissys");
                stmt = con.createStatement();

                ResultSet r;
                r = stmt.executeQuery(comandoSelect);
                while(r.next()){
                    if (r.getString("email").equals(email)){
                        result ="Email ja cadastrado;";
                        tem++;
                    }
                }
                if (tem==0){
                    stmt.executeUpdate(comando);
                    result = "Cadastro ok;";
                }
                con.close();

            }catch (Exception e){
                //System.out.println("Erro na comunicação "+sistemaIp +" "+sistemaUser+" "+ e.getMessage());
                //System.out.println("Erro SQL "+comando+"");
                //e.printStackTrace();
                result="no;";
            }

            record.put("status ", result);
        }catch (Exception e){
            record.put("status ", result);
        }
        return record;
    }

    //prof Saude 1
    @PostMapping("/ultimaPosicao")
    public ArrayList ultimaPosicao(String data){
        //http://localhost:8081/testa_masc_server/config/posts.php?operacao=ultimaPosicao
        //http://localhost:8089/ultimaPosicao
        //$data = ['data' => 'Membros inferiores;Membros superiores#Blanch@server.com.br'];

        String comando1,comando2;
        ResultSet rs1,rs2;
        Statement stmt1,stmt2;
        ArrayList pontos2;

        Calculos calculos;
        calculos = new Calculos();
        ArrayList res1;
        res1=new ArrayList();
        boolean achou=false;
        pontos2 = new ArrayList<Pontos2>();
        String con1="";
        String con2="";

        String data2=data.replaceAll("'", "");
        String[] valores = data2.split("#");
        String[] valoresDefs = valores[0].split(";");
        String[] valoresUsers = valores[1].split(";");
        System.out.println("postou "+data);
        String result="";
        Pontos2 p2;


        try {
            con = Database.getConnection("dissys");
            String lat,lon,mail,id;

            comando2="SELECT mail, latitude, longitude, date_format(dataHora,'%Y-%m-%d %H:%i:%s') dd FROM `_masc_trilhaSiafu27k` WHERE mail='"+valores[1]+"' Order by id ASC LIMIT 1" ;
            System.out.println("c2");
            System.out.println(comando2);
            stmt2 = con.createStatement();
            rs2 = stmt2.executeQuery(comando2);

            while(rs2.next()){
                System.out.println("TEM");
                mail =rs2.getString("mail");
                lat = rs2.getString("latitude");
                lon = rs2.getString("longitude");
                data = rs2.getString("dd");
                p2=new Pontos2(mail,lat,lon,data);
                pontos2.add(p2);
            }


        } catch (Exception ex) {
            result="no;";
        }

        return pontos2;

    }


}
