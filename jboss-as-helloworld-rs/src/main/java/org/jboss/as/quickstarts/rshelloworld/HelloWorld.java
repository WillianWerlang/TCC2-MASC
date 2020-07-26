/*
 * JBoss, Home of Professional Open Source
 * Copyright 2012, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the 
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,  
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.as.quickstarts.rshelloworld;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;

import org.json.JSONObject;


/**
 * A simple REST service which is able to say hello to someone using
 * HelloService
 * Please take a look at the web.xml where JAX-RS is enabled
 * 
 * @author gbrey@redhat.com
 * 
 */

@Path("/")
public class HelloWorld {
	@Inject
	HelloService helloService;

	@GET
	@Path("/json")
	@Produces({ "application/json" })
	public String getHelloWorldJSON() {
		return "{\"result\":\"" + helloService.createHelloMessage("World") + "\"}";
	}

	@GET
	@Path("/xml")
	@Produces({ "application/xml" })
	public String getHelloWorldXML() {
		return "<xml><result>" + helloService.createHelloMessage("World")
				+ "</result></xml>";
	}

	@POST
    @Path("/post2")
    //@Consumes(MediaType.TEXT_XML)
    public Response postStrMsg( String msg) {
        String output = "POST:Jersey say : " + msg;
        return Response.status(200).entity(output).build();
    }
	
	@POST
    @Path("/post")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createDataInJSON(String data) { 
		//grava mondo
		String sistemaUser, sistemaSenha, sistemaBase, sistemaIp,sistemaPorta;
        /*sistemaIp = "localhost";
        sistemaUser = "admin";
        sistemaSenha = "...";
        sistemPorta = "27107";
        sistemaBase = "local";
        sistemaIp = "127.11.50.129";
        */
        //ip https://dissys-marcelot.rhcloud.com/rockmongo/
        sistemaIp = "127.11.50.130";
        sistemaUser = "admin";
        sistemaSenha = "...";
        sistemaPorta = "27017";
        sistemaBase = "dissys";
        
        try{
        	Mongo mongo= new Mongo(sistemaIp, 27017);
        	DB db = mongo.getDB(sistemaBase);
            boolean auth = db.authenticate("admin", sistemaSenha.toCharArray());
            DBCollection coll = db.getCollection("sensor");
            BasicDBObject doc = new BasicDBObject("Valores",data);
            coll.insert(doc);
            
            JSONObject json = new JSONObject(data);
            String mn = (String) json.get("Mn");
            //System.out.println("Mn"+mn);
            
            BasicDBObject whereQuery = new BasicDBObject();
        	whereQuery.put("MascNode", mn);
        	DBCollection coll2 = db.getCollection("configs");
        	
        	DBObject removeIdProjection = new BasicDBObject("_id", 0);
        	
        	DBCursor cursor = coll2.find(whereQuery,removeIdProjection);
        	while(cursor.hasNext()) {
        		//System.out.println(cursor.next());
        	    data = data +cursor.next();
        	}
          
          }catch (Exception e){
        	  //System.out.println("Erro na comunicação "+sistemaIp +" "+sistemaUser+" "+ e.getMessage());
        	  //e.printStackTrace();
        	  result="no;";
          }
        String result = "Data post: "+data;
        return Response.status(201).entity(result).build(); 
	}
	@POST
    @Path("/cadastro")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createCadastro(String data) { 
		//grava mondo
		String sistemaUser, sistemaSenha, sistemaIp,sistemaPorta;
		sistemaIp = System.getenv("OPENSHIFT_MYSQL_DB_HOST");
        sistemaPorta = System.getenv("OPENSHIFT_MYSQL_DB_PORT");
        sistemaUser = "admin";
        sistemaSenha = "...";
        
        String comando,comandoSelect,result;
        result="";
        String data2=data.replaceAll("'", "");
    	String[] valores = data2.split(";");
        
        comando = "INSERT into _masc_usuario (nome,email,senha,tipoUser,tipoDef) "
    			+ "VALUES ("
    			+ "'"+valores[0]+"','"+valores[1]+"','"+valores[2]+"','"+valores[3]+"','"+valores[4]+"');";
        
        comandoSelect = "SELECT email FROM _masc_usuario"
        		+ " WHERE email='"+valores[1]+"' ;";
       
        int tem=0;
        try{
        	Connection con;
        	con = DriverManager.getConnection("jdbc:mysql://"+sistemaIp+":"+sistemaPorta+"/dissys", sistemaUser, sistemaSenha);
        	Statement stmt;
        	stmt = con.createStatement();
        	
    
        	ResultSet r;
        	r = stmt.executeQuery(comandoSelect);
        	while(r.next()){
        		if (r.getString("email").equals(valores[1])){
        			result ="Email já cadastrado;";
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
        //result = "Data post: "+comando;
        return Response.status(201).entity(result).build(); 
	}
	@POST
    @Path("/logar")
    @Consumes(MediaType.APPLICATION_JSON)	
	public Response createLog(String data) { 
			//loga
			String sistemaUser, sistemaSenha, sistemaIp,sistemaPorta;
			sistemaIp = System.getenv("OPENSHIFT_MYSQL_DB_HOST");
	        sistemaPorta = System.getenv("OPENSHIFT_MYSQL_DB_PORT");
	        sistemaUser = "admin";
	        sistemaSenha = "...";
	        
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
	        //result = result+comando;
	        
	        System.out.println("erro 3"+result);
	        
	        try{
	        	Connection con;
	        	con = DriverManager.getConnection("jdbc:mysql://"+sistemaIp+":"+sistemaPorta+"/dissys", sistemaUser, sistemaSenha);
	        	Statement stmt;
	        	stmt = con.createStatement();
	        	ResultSet r;
	        	r = stmt.executeQuery(comando);
	        	while(r.next()){
	        		if ( (r.getString("email").equals(valores[0])) && (r.getString("senha").equals(valores[1]))){
	        			tipoUser = r.getString("tipoUser");
	        			tipoDef = r.getString("tipoDef");
	        			result += "ok;";
	        			result += tipoUser+";";
	        			result += tipoDef+";";
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
	        return Response.status(201).entity(result).build(); 
		}
	@POST
    @Path("/listarRecursos")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createListarRecursos(String data) { 
		//loga
		String sistemaUser, sistemaSenha, sistemaIp,sistemaPorta;
		sistemaIp = System.getenv("OPENSHIFT_MYSQL_DB_HOST");
        sistemaPorta = System.getenv("OPENSHIFT_MYSQL_DB_PORT");
        sistemaUser = "admin";
        sistemaSenha = "...";
        
        String comando;
        String tipoUser, tipoDef;
        String result="";
        tipoUser="";
        tipoDef="";
        String data2=data.replaceAll("'", "");
    	String[] valores = data2.split(";");
    	System.out.println("postou "+data);
        
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
                
        System.out.println("resultado ="+result);
        System.out.println("comando ="+comando);
        String linha,nome,dist,desc,lat,lon,icone;
        
        try{
        	Connection con;
        	con = DriverManager.getConnection("jdbc:mysql://"+sistemaIp+":"+sistemaPorta+"/dissys", sistemaUser, sistemaSenha);
        	Statement stmt;
        	stmt = con.createStatement();
        	ResultSet r;
        	r = stmt.executeQuery(comando);
        	while(r.next()){
    			nome = r.getString("nome");
    			dist = r.getString("distance");
    			desc = r.getString("descricao");
    			lat = r.getString("latitude");
    			lon = r.getString("longitude");
    			icone = r.getString("icone");
    			linha = nome+";"+dist+";"+desc+";"+lat+";"+lon+";"+icone;
    			result+=linha+"\n";
        	}
        
        	con.close();
        }catch (Exception e){
        	//System.out.println("Erro na comunicação "+sistemaIp +" "+sistemaUser+" "+ e.getMessage());
        	//System.out.println("Erro SQL "+comando+"");
        	//e.printStackTrace();
        	result="no;";
        }
        result = result+tipoUser+";"+tipoDef;
        return Response.status(201).entity(result).build(); 
	}
	
	@POST
    @Path("/registraTrilha")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createTrilha(String data) { 
		String sistemaUser, sistemaSenha, sistemaIp,sistemaPorta;
		sistemaIp = System.getenv("OPENSHIFT_MYSQL_DB_HOST");
        sistemaPorta = System.getenv("OPENSHIFT_MYSQL_DB_PORT");
        sistemaUser = "admin";
        sistemaSenha = "...";
        int cmd;
        
        String comando,result;
        result="";
        String data2=data.replaceAll("'", "");
    	String[] valores = data2.split(";");
        
        comando = "INSERT into _masc_trilha (latitude,longitude,tag,recs,mail)"
    			+ "VALUES ("
    			+ "'"+valores[0]+"','"+valores[1]+"','"+valores[2]+"','"+valores[3]+"','"+valores[4]+"');";
        
        try{
        	Connection con;
        	con = DriverManager.getConnection("jdbc:mysql://"+sistemaIp+":"+sistemaPorta+"/dissys", sistemaUser, sistemaSenha);
        	Statement stmt;
        	stmt = con.createStatement();
        	cmd=stmt.executeUpdate(comando);
        	if(cmd==1){
        		result = "trilha ok;"+valores[valores.length-1];
        	}else{
        		result = "no;";
        	}
    		
    		con.close();
            
        }catch (Exception e){
        	//System.out.println("Erro na comunicação "+sistemaIp +" "+sistemaUser+" "+ e.getMessage());
        	//System.out.println("Erro SQL "+comando+"");
        	//e.printStackTrace();
        	result="no;";	
        }
        //result = "Data post: "+comando;
        return Response.status(201).entity(result).build(); 
	}
	//Serviço PcD
	@POST
    @Path("/verificaTrilhaFinal")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createverificaTrilhaFinal(String data) {
		String sistemaUser, sistemaSenha, sistemaIp,sistemaPorta;
		sistemaIp = System.getenv("OPENSHIFT_MYSQL_DB_HOST");
        sistemaPorta = System.getenv("OPENSHIFT_MYSQL_DB_PORT");
        sistemaUser = "admin";
        sistemaSenha = "...";
        
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
        
        ArrayList pontos;
        
        boolean achou=false;
            boolean s1,s2,s3;
            s1=true;
            s2=true;
            s3=true;
            String lat,lon,desc,id;
            int i=0;
        
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
            "`distance` ASC";
                
        System.out.println("resultado ="+result);
        System.out.println("comando ="+comando1);
        String linha,nome,dist,desc,lat,lon,icone;
        
        try{
        	Connection con;
        	con = DriverManager.getConnection("jdbc:mysql://"+sistemaIp+":"+sistemaPorta+"/dissys", sistemaUser, sistemaSenha);
        	
        	stmt1 = con.createStatement();
        	rs1 = stmt1.executeQuery(comando1);
        	while(rs1.next()){
    		 i++;
                id=rs1.getString("id");
                dataPrimeiro=rs1.getString("dd");
                
                //valores3 é latitudeFim
        		//valores4 é longitudeFim
                
                comando2="SELECT id,latitude,longitude,date_format(dataHora,'%Y-%m-%d'),"+
                "((ACOS( SIN( "+valores[3]+" * PI( ) /180 ) * SIN( latitude \n" +
                "* PI( ) /180 ) + COS( "++valores[3]+" * PI( ) /180 ) * COS( latitude * PI( ) \n" +
                "/180 ) * COS( ("+valores[4]+" - longitude) * PI( ) /180 ) ) *180 / PI( )) * \n" +
                "60 * 1.1515) *1000 AS `distance` \n" +
                "FROM `_masc_trilhaSiafu27k` \n" +
                "WHERE id > "+id+" \n" +
                "AND date_format(dataHora,'%Y-%m-%d')='"+dataPrimeiro+"' \n" +
                "AND ((ACOS( SIN( "+valores[3]+" * PI( ) /180 ) * SIN( latitude \n" +
                "* PI( ) /180 ) + COS( "+valores[3]+" * PI( ) /180 ) * COS( latitude * PI( ) \n" +
                "/180 ) * COS( ("+valores[4]+" - longitude) * PI( ) /180 ) ) *180 / PI( )) * \n" +
                "60 * 1.1515) *1000 < 10";
                stmt2 = connection.createStatement();
                rs2 = stmt2.executeQuery(comando2);

                while(rs2.next() &&(s2)){
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

                        stmt3 = connection.createStatement();
                        System.out.println("33Select enviado para consulta de trilha 3\n"+comando3);
                        rs3 = stmt3.executeQuery(comando3);
                        rs3.last();
                        int limite = rs3.getRow();
                        rs3.beforeFirst();
                        rs3.next();
                        for (int j = 0; j < limite; j++) {
                            System.out.println("entrou");
                            s2=false;
                            lat = rs3.getString("latitude");
                            lon = rs3.getString("longitude");
                            p=new Pontos(i,lat,lon);
                            pontos.add(p);
                            i++;
                            if(calculos.verificaDistancia(5,Double.parseDouble(lat),Double.parseDouble(lon), Double.parseDouble(txtLatFim.getText()), Double.parseDouble(txtLonFim.getText()))){
                                achou=true;
                            }
                            rs3.next();
                        }

                    }else{
                        break;
                    }
                }
            }
        
        	con.close();
        }catch (Exception e){
        	//System.out.println("Erro na comunicação "+sistemaIp +" "+sistemaUser+" "+ e.getMessage());
        	//System.out.println("Erro SQL "+comando+"");
        	//e.printStackTrace();
        	result="no;";
        }
        for(int i=0;i<pontos.size();i++){
        	result = result+";"+pontos.get(i).getLat()+","+pontos.get(i).getLon();
        }
        return Response.status(201).entity(result).build(); 
	}
	//Serviço Prefeitura 1
	@POST
    @Path("/trilhaUsuario")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createtrilhaUsuario(String data) {
		String sistemaUser, sistemaSenha, sistemaIp,sistemaPorta;
		sistemaIp = System.getenv("OPENSHIFT_MYSQL_DB_HOST");
        sistemaPorta = System.getenv("OPENSHIFT_MYSQL_DB_PORT");
        sistemaUser = "admin";
        sistemaSenha = "..";
        
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
                    
                
       
        String linha,nome,dist,desc,lat,lon,icone;
        
        try{
        	Connection con;
        	con = DriverManager.getConnection("jdbc:mysql://"+sistemaIp+":"+sistemaPorta+"/dissys", sistemaUser, sistemaSenha);
        	
        	stmt = con.createStatement();
        	rs1 = stmt.executeQuery(comando1);
        	
        	String lat,lon,mail,id,data;
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
        for(int i=0;i<pontos.size();i++){
        	result = result+";"+pontos.get(i).getId()+","+pontos.get(i).getLat()+","+pontos.get(i).getLon();
        }
        return Response.status(201).entity(result).build(); 
	}
	
	//Serviço prefeitura 2
	@POST
    @Path("/rotasMaisAcessadas")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createRotasMaisAcessadas(String data) {
		String sistemaUser, sistemaSenha, sistemaIp,sistemaPorta;
		sistemaIp = System.getenv("OPENSHIFT_MYSQL_DB_HOST");
        sistemaPorta = System.getenv("OPENSHIFT_MYSQL_DB_PORT");
        sistemaUser = "admin";
        sistemaSenha = "...";
        
       
        
        
       
        String data2=data.replaceAll("'", "");
    	String[] valores = data2.split(";");
    	System.out.println("postou "+data);
        
        
        Connection connection;
        List<Coordinate> coordinates = new ArrayList<Coordinate>();
        String comando1,comando2;
        ResultSet rs1,rs2,rs3;
        Statement stmt1,stmt2,stmt3;
        tadd = new Vector<>();
        pontos = new ArrayList<Pontos2>();
        
                    
        //valores0 é raio  
        
        //valores1 é latitudeIni1
        //valores2 é longitudeIni1
        //valores3 é email     
       
        String linha,nome,dist,desc,lat,lon,icone;
        
        try{
        	int raio = Integer.parseInt(valores[0]);
        	
        	comando1="SELECT id, latitude, longitude, mail, ((ACOS( SIN( "+valores[1]+" * PI( ) /180 ) * SIN( latitude \n" +
            "* PI( ) /180 ) + COS( "+valores[1]+" * PI( ) /180 ) * COS( latitude * PI( ) \n" +
            "/180 ) * COS( ("+valores[2]+" - longitude) * PI( ) /180 ) ) *180 / PI( )) * \n" +
            "60 * 1.1515) *1000 AS `distance`, date_format(dataHora,'%Y-%m-%d') dd FROM \n" +
            "`_masc_trilhaSiafu27k` HAVING `distance` <= "+raio+" ORDER BY \n" +
            "`distance` ASC";
        	
        	
        	Connection con;
        	con = DriverManager.getConnection("jdbc:mysql://"+sistemaIp+":"+sistemaPorta+"/dissys", sistemaUser, sistemaSenha);
        	
        	stmt = con.createStatement();
        	rs1 = stmt.executeQuery(comando1);
        	
        	String lat,lon,llat,llon,mail,id,data;
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
            
            rs1.last();
            total = rs1.getRow();
            System.out.println("limit="+total);
            rs1.beforeFirst();
          
            llat2=new String[total];
            llon2=new String[total];
            
        	while(rs1.next()){
                i++;
                System.out.println("entrou na primeira consulta");
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
            p = new Pontos(1, txtLatIni1.getText(), txtLonIni1.getText());
            pontos.add(p);
            double rrr;
            rrr = Double.parseDouble(txtRaio.getText())*0.000015;
            Demo16.raio = rrr;
            Demo16.qualArray = pontos;
            
            //fazer n polylines.
            String a,b,c,d;
            Pontos3 p3lido;
            System.out.println("pontos3 tamanho"+pontos3.size());
            
            for (int j = 0; j < pontos3.size(); j++) {
                p3lido=(Pontos3)pontos3.get(j);
                
                comando2="SELECT id, latitude, longitude, mail, date_format(dataHora,'%Y-%m-%d') dd FROM \n" +
                    "`_masc_trilhaSiafu27k` "
                        + "WHERE date_format(dataHora,'%Y-%m-%d')='"+p3lido.dd+"' "
                        + "AND mail='"+p3lido.mail+"'";

                stmt2 = connection.createStatement();
                System.out.println("2Select enviado para consulta de trilha 2\n"+comando2);
                rs2 = stmt2.executeQuery(comando2);
                
                double latk,lonk;
                int t=0;
                coordinates = new ArrayList<Coordinate>();
                
                while(rs2.next()){
                    System.out.println("entrou+++++++++++++++"+t);
                    latk=Double.parseDouble(rs2.getString("latitude"));
                    lonk=Double.parseDouble(rs2.getString("longitude"));
                    coordinates.add(new Coordinate(latk,lonk));
                    p = new Pontos(t, rs2.getString("latitude"), rs2.getString("longitude"));
                    pontos.add(p);
                    t++;
                }
                tadd.add(coordinates);
            }
        
        	con.close();
        }catch (Exception e){
        	//System.out.println("Erro na comunicação "+sistemaIp +" "+sistemaUser+" "+ e.getMessage());
        	//System.out.println("Erro SQL "+comando+"");
        	//e.printStackTrace();
        	result="no;";
        }
        for(int i=0;i<pontos.size();i++){
        	result = result+";"+pontos.get(i).getId()+","+pontos.get(i).getLat()+","+pontos.get(i).getLon();
        }
        return Response.status(201).entity(result).build(); 
	}
	
	


   
}
