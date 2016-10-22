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
        
        if (System.getenv("PORT") != null) {
            port(Integer.valueOf(System.getenv("PORT")));
        }
        
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
            res.redirect("/");
            return "ok";
        });
        
        get("/keskustelualue/:tunnus", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("viestiketjut", viestiketjuDao.findAll(Integer.parseInt(req.params("tunnus"))));
            map.put("alue", keskustelualueDao.findOne(Integer.parseInt(req.params("tunnus"))));
            return new ModelAndView(map, "keskustelualue");
        }, new ThymeleafTemplateEngine());
        
        post("/keskustelualue/:tunnus", (req, res) -> {
            String viesti = req.queryParams("viesti");
            String lahettaja = req.queryParams("lahettaja");
            String otsikko = req.queryParams("alue");
            viestiketjuDao.submitKetju(otsikko, (Integer.parseInt(req.params("tunnus"))));
            viestiDao.submitViesti(viestiketjuDao.findLatest(), viesti, lahettaja);
            res.redirect("/keskustelualue/" + req.params("tunnus"));
            return "ok";
        });

        get("/keskustelualue/:tunnus/:id/:sivu", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("viestit", viestiDao.findPage(Integer.parseInt(req.params("sivu")), Integer.parseInt(req.params("id"))));
            map.put("seuraavatViestit", viestiDao.findPage((Integer.parseInt(req.params("sivu")) + 1), Integer.parseInt(req.params("id"))));
            map.put("alue", keskustelualueDao.findOne(Integer.parseInt(req.params("tunnus"))));
            map.put("ketju", viestiketjuDao.findOne(Integer.parseInt(req.params("id"))));
            map.put("sivu", (Integer.parseInt(req.params("sivu"))));
            return new ModelAndView(map, "viestiketju");
        }, new ThymeleafTemplateEngine());
        
        post("/keskustelualue/:tunnus/:id/:sivu", (req, res) -> {
            String viesti = req.queryParams("viesti");
            String lahettaja = req.queryParams("lahettaja");
            viestiDao.submitViesti(Integer.parseInt(req.params("id")), viesti, lahettaja);
            res.redirect("/keskustelualue/" + req.params("tunnus") + "/" + req.params("id") + "/"  + req.params("sivu"));
            return "ok";
        });
    }
}
