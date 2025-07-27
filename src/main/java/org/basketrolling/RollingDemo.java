package org.basketrolling;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.basketrolling.beans.Elternkontakt;
import org.basketrolling.beans.Halle;
import org.basketrolling.beans.Liga;
import org.basketrolling.beans.Login;
import org.basketrolling.beans.MannschaftExtern;
import org.basketrolling.beans.MannschaftIntern;
import org.basketrolling.beans.Mitgliedsbeitrag;
import org.basketrolling.beans.MitgliedsbeitragZuweisung;
import org.basketrolling.beans.Spiele;
import org.basketrolling.beans.Spieler;
import org.basketrolling.beans.Statistik;
import org.basketrolling.beans.Trainer;
import org.basketrolling.beans.Training;
import org.basketrolling.dao.ElternkontaktDAO;
import org.basketrolling.dao.HalleDAO;
import org.basketrolling.dao.LigaDAO;
import org.basketrolling.dao.LoginDAO;
import org.basketrolling.dao.MannschaftExternDAO;
import org.basketrolling.dao.MannschaftInternDAO;
import org.basketrolling.dao.MitgliedsbeitragDAO;
import org.basketrolling.dao.MitgliedsbeitragZuweisungDAO;
import org.basketrolling.dao.SpieleDAO;
import org.basketrolling.dao.SpielerDAO;
import org.basketrolling.dao.StatistikDAO;
import org.basketrolling.dao.TrainerDAO;
import org.basketrolling.dao.TrainingDAO;
import org.basketrolling.enums.Rolle;
import org.basketrolling.service.ElternkontaktService;
import org.basketrolling.service.HalleService;
import org.basketrolling.service.LigaService;
import org.basketrolling.service.LoginService;
import org.basketrolling.service.MannschaftExternService;
import org.basketrolling.service.MannschaftInternService;
import org.basketrolling.service.MitgliedsbeitragService;
import org.basketrolling.service.MitgliedsbeitragZuweisungService;
import org.basketrolling.service.SpieleService;
import org.basketrolling.service.SpielerService;
import org.basketrolling.service.StatistikService;
import org.basketrolling.service.TrainerService;
import org.basketrolling.service.TrainingService;

/**
 *
 * @author Marko
 */
public class RollingDemo {

    public static void main(String[] args) {

        Spieler spieler = new Spieler();
        SpielerDAO spielerDao = new SpielerDAO();
        SpielerService spielerService = new SpielerService(spielerDao);

        Login login = new Login();
        LoginDAO loginDAO = new LoginDAO();
        LoginService loginService = new LoginService(loginDAO);

        Elternkontakt ek = new Elternkontakt();
        ElternkontaktDAO ekDao = new ElternkontaktDAO();
        ElternkontaktService ekService = new ElternkontaktService(ekDao);

        Halle halle = new Halle();
        HalleDAO halleDao = new HalleDAO();
        HalleService halleService = new HalleService(halleDao);

        Liga liga = new Liga();
        LigaDAO ligaDao = new LigaDAO();
        LigaService ligaService = new LigaService(ligaDao);

        MannschaftExtern mannEx = new MannschaftExtern();
        MannschaftExternDAO exDao = new MannschaftExternDAO();
        MannschaftExternService exService = new MannschaftExternService(exDao);

        MannschaftIntern mannIn = new MannschaftIntern();
        MannschaftInternDAO inDao = new MannschaftInternDAO();
        MannschaftInternService inService = new MannschaftInternService(inDao);

        Mitgliedsbeitrag beitrag = new Mitgliedsbeitrag();
        MitgliedsbeitragDAO beitragDao = new MitgliedsbeitragDAO();
        MitgliedsbeitragService beitragService = new MitgliedsbeitragService(beitragDao);

        Spiele spiele = new Spiele();
        SpieleDAO spieleDao = new SpieleDAO();
        SpieleService spieleService = new SpieleService(spieleDao);

        Statistik statistik = new Statistik();
        StatistikDAO statistikDao = new StatistikDAO();
        StatistikService statistikService = new StatistikService(statistikDao);

        Trainer trainer = new Trainer();
        TrainerDAO trainerDAO = new TrainerDAO();
        TrainerService trainerService = new TrainerService(trainerDAO);

        Training training = new Training();
        TrainingDAO trainingDAO = new TrainingDAO();
        TrainingService trainingService = new TrainingService(trainingDAO);
        
        MitgliedsbeitragZuweisung zuweisung = new MitgliedsbeitragZuweisung();
        MitgliedsbeitragZuweisungDAO zuweisungDao = new MitgliedsbeitragZuweisungDAO();
        MitgliedsbeitragZuweisungService zuweisungService = new MitgliedsbeitragZuweisungService(zuweisungDao);
        
       

        //        }
//        spieler.setAktiv(true);
//        spieler.setAlter(22);
//        spieler.setGeburtsdatum(LocalDate.now());
//        spieler.setGroesse(2.03);
//        spieler.setNachname("Begovic");
//        spieler.setVorname("Marko");
//        spieler.seteMail("bego@kanf.com");
//        spielerService.create(spieler);
//
//        ek.setVorname("Max");
//        ek.setNachname("Mustermann");
//        ek.setTelefon("06766535061");
//        ek.setSpieler(spieler);
//        ekService.create(ek);
//        liga.setName("2. Herrenliga (H2)");
//        liga = ligaService.create(liga);
//
//        halle.setName("RH Steigenteschgasse");
//        halle.setStrasse("Steigenteschgasse 1");
//        halle.setPlz(1220);
//        halle.setOrt("Wien");
//        halle = halleService.create(halle);
//
//        trainer.setAktiv(true);
//        trainer.setVorname("Trainer");
//        trainer.setNachname("Hans");
//        trainer.setTelefon("02782199806");
//        trainer = trainerService.create(trainer);
//
//        mannIn.setName("Basket Rolling /2 H2");
//        mannIn.setTrainer(trainer);
//        mannIn.setLiga(liga);
//        mannIn = inService.create(mannIn);
//
//        mannEx.setName("BC Gumpendorf/2 Textil Müller H2");
//        mannEx.setLiga(liga);
//        mannEx = exService.create(mannEx);
//
//        spiele.setDatum(LocalDate.now());
//        spiele.setHalle(halle);
//        spiele.setLiga(liga);
//        spiele.setMannschaftIntern(mannIn);
//        spiele.setMannschaftExtern(mannEx);
//        spiele.setInternPunkte(78);
//        spiele.setExternPunkte(60);
//        spieleService.create(spiele);
//        login.setBenutzername("admin");
//        login.setVorname("Marko");
//        login.setNachname("Begovic");
//        login.setPasswort("admin");
//        login.setRolle(Rolle.ADMIN);
//        loginService.create(login);
//        
        List<Spieler> testSpielerList = spielerService.getAll();
        List<Spiele> testSpieleList = spieleService.getAll();
        List<Halle> testhalleList = halleService.getAll();
        List<Liga> testLigaList = ligaService.getAll();
        List<MannschaftIntern> mannInList = inService.getAll();
        List<MannschaftExtern> mannExList = exService.getAll();
        List<Training> testTrainingList = trainingService.getAll();
        List<Mitgliedsbeitrag> testBeitrag = beitragService.getAll();
        List<Login> testLogin = loginService.getAll();
        
//         zuweisung.setSpieler(testSpielerList.get(0));
//         zuweisung.setMitgliedsbeitrag(testBeitrag.get(0));
//         zuweisungService.create(zuweisung);
//                statistik.setStarter(true);
//                statistik.setSpieler(testSpielerList.get(0));
//                statistik.setSpiel(testSpieleList.get(0));
//                statistik.setPunkte(25);
//                statistik.setFouls(2);
//                statistikService.create(statistik);

//        spieler.setAktiv(true);
//        spieler.setGeburtsdatum(LocalDate.now().minusYears(25));
//        spieler.setGroesse(1.87);
//        spieler.setNachname("Mustermann");
//        spieler.setVorname("Max");
//        spieler.seteMail("Max@Mustermann.com");
//        spieler.setMannschaftIntern(mannInList.get(0));
//        spielerService.create(spieler);

        //        mannEx.setName("UKJ Rockets/3 H2");
        //        mannEx.setLiga(testLigaList.get(0));
        //        mannEx = exService.create(mannEx);
        //        spiele.setDatum(LocalDate.now());
        //        spiele.setHalle(testhalleList.get(0));
        //        spiele.setLiga(testLigaList.get(0));
        //        spiele.setMannschaftIntern(mannInList.get(0));
        //        spiele.setMannschaftExtern(mannExList.get(1));
        //        spiele.setInternPunkte(70);
        //        spiele.setExternPunkte(49);
        //        spieleService.create(spiele);
        //
        //
        
//        List<Spieler> spielerIndex0 =new ArrayList<>();
//        spielerIndex0.add(testSpielerList.get(0));
//        Mitgliedsbeitrag beitrag1 = testBeitrag.get(0);
//        beitrag1.setSpieler(spielerIndex0);
//        beitragService.update(beitrag1);
//                statistik.setStarter(true);
//                statistik.setSpieler(testSpielerList.get(0));
//                statistik.setSpiel(testSpieleList.get(1));
//                statistik.setPunkte(5);
//                statistik.setFouls(4);
//                statistikService.create(statistik);
//        Spieler mare = testSpielerList.get(0);
//        mare.setMannschaftIntern(mannInList.get(0));
//        spielerService.update(mare);
//        training.setDauerInMin(90);
//        training.setDatum(LocalDate.now().minusDays(7));
//        training.setWochentag(LocalDate.now().minusDays(7).getDayOfWeek().toString());
//        training.setHalle(testhalleList.get(0));
//        training.setMannschaftIntern(mannInList.get(0));
//        trainingService.create(training);

//        beitrag.setSaison("2025/26");
//        beitrag.setBetrag(400);
//        beitragService.create(beitrag);

    

////        loginService.registrierung(login);
//        
//        Login ergebnis = loginService.pruefung("admin", "admin");
//
//        if (ergebnis != null) {
//            System.out.println("✅ Login erfolgreich für Benutzer: " + ergebnis.getBenutzername());
//        } else {
//            System.out.println("❌ Login fehlgeschlagen.");
//    MannschaftIntern rollingH2 = mannInList.get(0);
//        rollingH2.setTraining(testTrainingList.get(1));
//        inService.update(rollingH2);

//        List<Spieler> list
//                = service.getByGroesseAb(2.00);
//
//        for (Spieler s : list) {
//            System.out.println(s);
//        }
    }
}
