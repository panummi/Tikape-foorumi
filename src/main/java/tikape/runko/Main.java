package tikape.runko;

import java.util.*;
import spark.ModelAndView;
import static spark.Spark.*;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import tikape.runko.database.Database;
import tikape.runko.database.KeskustelualueDao;
import tikape.runko.database.ViestiDao;
import tikape.runko.database.ViestiketjuDao;


public class Main {

    public static void main(String[] args) throws Exception {
        Database database = new Database("jdbc:sqlite:keskustelualueet.db");
        KeskustelualueDao keskustelualueDao = new KeskustelualueDao(database);
        ViestiketjuDao viestiketjuDao = new ViestiketjuDao(database, keskustelualueDao);
        ViestiDao viestiDao = new ViestiDao(database, viestiketjuDao);
        
        get("/", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("keskustelualueet", keskustelualueDao.findAllFrom());
            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine()); 
        
        post("/", (req, res) -> {
            String otsikko = req.queryParams("alue");
            keskustelualueDao.submitAlue(otsikko);
            return "Lisätty alue " + otsikko + ".";
        });
        
        get("/keskustelualue/:tunnus", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("viestiketjut", viestiketjuDao.findAll(Integer.parseInt(req.params("tunnus"))));
            map.put("tunnus", req.params("tunnus"));
            return new ModelAndView(map, "keskustelualue");
        }, new ThymeleafTemplateEngine());
        get("/keskustelualue/:tunnus", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("viestiketjut", viestiketjuDao.findAll(Integer.parseInt(req.params("tunnus"))));
            map.put("tunnus", req.params("tunnus"));
            return new ModelAndView(map, "keskustelualue");
        }, new ThymeleafTemplateEngine());
        
        post("/keskustelualue/:tunnus", (req, res) -> {
            String otsikko = req.queryParams("alue");
            viestiketjuDao.submitKetju(otsikko, (Integer.parseInt(req.params("tunnus"))));
            return "Lisätty alue " + otsikko + ".";
        });

        get("/keskustelualue/:tunnus/:id", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("viestit", viestiDao.findAll(Integer.parseInt(req.params("id"))));
            return new ModelAndView(map, "viestiketju");
        }, new ThymeleafTemplateEngine());
    }
}
