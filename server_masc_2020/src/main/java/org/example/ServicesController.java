package org.example;

import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicLong;


@RestController
public class ServicesController {
    Controller controller;

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @GetMapping("/listaRec")
    public ArrayList listaRec(@RequestParam(value = "data", defaultValue = "PcD;Membros Inferiores;-29.762738198684357;-51.151206493377686") String data) {
        controller = new Controller();
        return controller.listarRecursos(data);
    }

    @GetMapping("/registraTrilha")
    public ArrayList registraTrilha(@RequestParam(value = "data", defaultValue = "29.791959945956947;-51.15311086177826;0; 0;Blanch@server.com.br") String data) {
        controller = new Controller();
        return controller.registraTrilha(data);
    }

    @GetMapping("/verificaTrilhaFinal")
    public ArrayList verificaTrilhaFinal(@RequestParam(value = "data", defaultValue = "-29.762738198684357;-51.151206493377686;marcelojtelles@gmail.com;-29.768084050351185;-51.145241260528564") String data) {
        controller = new Controller();
        return controller.verificaTrilhaFinal(data);
    }
    @GetMapping("/trilhaUsuario")
    public ArrayList trilhaUsuario(@RequestParam(value = "data", defaultValue = "2015-10-27;Blanch@server.com.br") String data) {
        controller = new Controller();
        return controller.trilhaUsuario(data);
    }
    @GetMapping("/rotasMaisAcessadas")
    public ArrayList rotasMaisAcessadas(@RequestParam(value = "data", defaultValue = "12;-29.763483282226453;-51.14838480949402") String data) {
        controller = new Controller();
        return controller.rotasMaisAcessadas(data);
    }
    @GetMapping("/logar")
    public ArrayList logar(@RequestParam(value = "data", defaultValue = "paulo@server.com;aa") String data) {
        controller = new Controller();
        return controller.logar(data);
    }
    @GetMapping("/cadastro")
    public JSONObject cadastro(
            @RequestParam(value = "nome", defaultValue = "Teste44") String nome,
            @RequestParam(value = "email", defaultValue = "teste44@server.com6") String email,
            @RequestParam(value = "senha", defaultValue = "123") String senha,
            @RequestParam(value = "tipoUser", defaultValue = "PcD") String tipoUser,
            @RequestParam(value = "tipoDef", defaultValue = "Membros Membros Inferiores") String tipoDef) {
        controller = new Controller();
        return controller.cadastro(nome,email,senha,tipoUser,tipoDef);
    }
    @GetMapping("/ultimaPosicao")
    public ArrayList ultimaPosicao(@RequestParam(value = "data", defaultValue = "Membros inferiores;Membros superiores#Blanch@server.com.br") String data) {
        controller = new Controller();
        return controller.ultimaPosicao(data);
    }
    @GetMapping("/listUser")
    public Iterable listuser() {
        controller = new Controller();
        return controller.getUsers();
    }

}
