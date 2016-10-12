package tikape.runko;

import java.util.*;
import spark.ModelAndView;
import static spark.Spark.*;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import tikape.runko.database.Database;
import tikape.runko.database.KeskustelualueDao;
import tikape.runko.database.ViestiDao;
import tikape.runko.database.ViestiketjuDao;
import tikape.runko.domain.Keskustelualue;
import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {
        Database database = new Database("jdbc:sqlite:keskustelualueet.db");
        database.init();

        KeskustelualueDao keskustelualueDao = new KeskustelualueDao(database);
        ViestiketjuDao viestiketjuDao = new ViestiketjuDao(database, keskustelualueDao);
        ViestiDao viestiDao = new ViestiDao(database, viestiketjuDao);
        
        //System.out.println(keskustelualueDao.countViestit());
        
        get("/", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("keskustelualueet", keskustelualueDao.findAll());
            List<Keskustelualue> keskustelualueet = keskustelualueDao.findAll();
            int i = 0;
            ArrayList<Integer> viestit = new ArrayList<>();
            while (i < keskustelualueet.size()) {
		Integer tunnus = keskustelualueet.get(i).getTunnus();
                Integer lkm = keskustelualueDao.countViestit(tunnus);
                viestit.add(lkm);
                i++;
		}
            map.put("viestit", viestit);
            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine()); 
        
        post("/", (req, res) -> {
            String otsikko = req.queryParams("alue");
            return otsikko;
        });
        
        get("/keskustelualue/:tunnus", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("viestiketjut", viestiketjuDao.findAll());
            map.put("tunnus", req.params(":tunnus"));
            return new ModelAndView(map, "keskustelualue");
        }, new ThymeleafTemplateEngine());

        get("/keskustelualue/:tunnus/:tunnus", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("viestiketju", viestiketjuDao.findOne(Integer.parseInt(req.params("tunnus"))));
            return new ModelAndView(map, "viestiketju");
        }, new ThymeleafTemplateEngine());
    }
}
