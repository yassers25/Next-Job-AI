package com.ai;

import java.util.HashMap;

public  class HashMapData {
    public static HashMap<String, String> sectorMap = new HashMap<>() {{
        put("0", "activités associatives");
        put("1", "administration publique");
        put("2", "aéronautique, navale");
        put("3", "agriculture, pêche, aquaculture");
        put("4", "agroalimentaire");
        put("5", "ameublement, décoration");
        put("6", "automobile, matériels de transport, réparation");
        put("7", "banque, assurance, finances");
        put("8", "btp, construction");
        put("9", "centres d´appel, hotline, call center");
        put("10", "chimie, pétrochimie, matières premières, mines");
        put("11", "conseil, audit, comptabilité");
        put("12", "distribution, vente, commerce de gros");
        put("13", "édition, imprimerie");
        put("14", "éducation, formation");
        put("15", "électricité, eau, gaz, nucléaire, énergie");
        put("16", "environnement, recyclage");
        put("17", "equip. électriques, électroniques, optiques, précision");
        put("18", "équipements mécaniques, machines");
        put("19", "espaces verts, forêts, chasse");
        put("20", "évènementiel, hôte(sse), accueil");
        put("21", "hôtellerie, restauration");
        put("22", "immobilier, architecture, urbanisme");
        put("23", "import, export");
        put("24", "industrie pharmaceutique");
        put("25", "industrie, production, fabrication, autres");
        put("26", "informatique, ssii, internet");
        put("27", "ingénierie, études développement");
        put("28", "intérim, recrutement");
        put("29", "location");
        put("30", "luxe, cosmétiques");
        put("31", "maintenance, entretien, service après vente");
        put("32", "manutention");
        put("33", "marketing, communication, médias");
        put("34", "métallurgie, sidérurgie");
        put("35", "nettoyage, sécurité, surveillance");
        put("36", "papier, bois, caoutchouc, plastique, verre, tabac");
        put("37", "produits de grande consommation");
        put("38", "qualité, méthodes");
        put("39", "recherche et développement");
        put("40", "santé, pharmacie, hôpitaux, équipements médicaux");
        put("41", "secrétariat");
        put("42", "services aéroportuaires et maritimes");
        put("43", "services autres");
        put("44", "services collectifs et sociaux, services à la personne");
        put("45", "sport, action culturelle et sociale");
        put("46", "télécom");
        put("47", "textile, habillement, cuir, chaussures");
        put("48", "tourisme, loisirs");
        put("49", "transports, logistique, services postaux");
    }};
    
    // Predefined Experience Map
    public static HashMap<String, String> experienceMap = new HashMap<>() {{
        put("0", "fraichement diplômé");
        put("1", "débutant (de 1 à 3 ans)");
        put("2", "junior (de 3 à 5 ans)");
        put("3", "senior (de 5 à 10 ans)");
        put("4", "expert (10 ou plus)");
    }};
    
    // Predefined Study Map
    public static HashMap<String, String> studyMap = new HashMap<>() {{
        
        put("0", "bac");
        put("1", "bac +1");
        put("2", "bac +2");
        put("3", "bac +3");
        put("4", "bac +4");
        put("5", "bac +5 et plus");
        put("6", "doctorat");
        put("7", "autodidacte");
        put("8", "qualification avant bac");
    }};
    
    // Predefined Contract Map
    public static HashMap<String, String> contractMap = new HashMap<>() {{
        put("0", "cdi");
        put("1", "cdd");
        put("2", "intérim");
        put("3", "autre");
        put("4", "stage");
        put("5", "freelance");
        put("6", "lettre d'engagement");
        put("7", "statutaire");
        put("8", "temps partiel");
    }};
    
    // Predefined Remote Map
    public static HashMap<String, String> remoteMap = new HashMap<>() {{
        put("0", "oui");
        put("1", "hybride");
        put("2", "non");
    }};
    
    // Predefined City Map
    public static HashMap<String, String> cityMap = new HashMap<>() {{
        put("0", "rabat");
        put("1", "salé");
        put("2", "nador");
        put("3", "casablanca");
        put("4", "tanger");
        put("5", "tétouan");
        put("6", "mohammédia");
        put("7", "agadir");
        put("8", "marrakech");
        put("9", "témara");
        put("10", "benguérir");
        put("11", "laâyoune");
        put("12", "oujda");
        put("13", "kénitra");
        put("14", "dakhla");
        put("15", "settat");
        put("16", "guelmim");
        put("17", "errachidia");
        put("18", "ifrane");
        put("19", "berrechid");
        put("20", "skhirate");
        put("21", "fès");
        put("22", "meknès");
        put("23", "taliouine");
        put("24", "béni mellal");
        put("25", "el jadida");
        put("26", "guercif");
        put("27", "khémisset");
        put("28", "taghazout");
        put("29", "hoceima");
        put("30", "safi");
        put("31", "zenata");
        put("32", "larache");
        put("33", "tit mellil");
        put("34", "ben guerir");
        put("35", "kalaa des sraghna");
        put("36", "taroudant");
        put("37", "ouarzazate");
        put("38", "berkane");
        put("39", "tamesna");
        put("40", "khouribga");
        put("41", "oued zem");
        put("42", "all");
    }};
    
    


    public static HashMap<String, Integer> sectorMapName = new HashMap<>() {{
        put("activités associatives", 0);
        put("administration publique", 1);
        put("aéronautique, navale", 2);
        put("agriculture, pêche, aquaculture", 3);
        put("agroalimentaire", 4);
        put("ameublement, décoration", 5);
        put("automobile, matériels de transport, réparation", 6);
        put("banque, assurance, finances", 7);
        put("btp, construction", 8);
        put("centres d´appel, hotline, call center", 9);
        put("chimie, pétrochimie, matières premières, mines", 10);
        put("conseil, audit, comptabilité", 11);
        put("distribution, vente, commerce de gros", 12);
        put("édition, imprimerie", 13);
        put("éducation, formation", 14);
        put("électricité, eau, gaz, nucléaire, énergie", 15);
        put("environnement, recyclage", 16);
        put("equip. électriques, électroniques, optiques, précision", 17);
        put("équipements mécaniques, machines", 18);
        put("espaces verts, forêts, chasse", 19);
        put("évènementiel, hôte(sse), accueil", 20);
        put("hôtellerie, restauration", 21);
        put("immobilier, architecture, urbanisme", 22);
        put("import, export", 23);
        put("industrie pharmaceutique", 24);
        put("industrie, production, fabrication, autres", 25);
        put("informatique, ssii, internet", 26);
        put("ingénierie, études développement", 27);
        put("intérim, recrutement", 28);
        put("location", 29);
        put("luxe, cosmétiques", 30);
        put("maintenance, entretien, service après vente", 31);
        put("manutention", 32);
        put("marketing, communication, médias", 33);
        put("métallurgie, sidérurgie", 34);
        put("nettoyage, sécurité, surveillance", 35);
        put("papier, bois, caoutchouc, plastique, verre, tabac", 36);
        put("produits de grande consommation", 37);
        put("qualité, méthodes", 38);
        put("recherche et développement", 39);
        put("santé, pharmacie, hôpitaux, équipements médicaux", 40);
        put("secrétariat", 41);
        put("services aéroportuaires et maritimes", 42);
        put("services autres", 43);
        put("services collectifs et sociaux, services à la personne", 44);
        put("sport, action culturelle et sociale", 45);
        put("télécom", 46);
        put("textile, habillement, cuir, chaussures", 47);
        put("tourisme, loisirs", 48);
        put("transports, logistique, services postaux", 49);
    }};

    public static HashMap<String, Integer> experienceMapName = new HashMap<>() {{
        put("fraichement diplômé", 0);
        put("débutant (de 1 à 3 ans)", 1);
        put("junior (de 3 à 5 ans)", 2);
        put("senior (de 5 à 10 ans)", 3);
        put("expert (10 ou plus)", 4);
    }};

    public static HashMap<String, Integer> studyMapName = new HashMap<>() {{
        put("bac", 0);
        put("bac +1", 1);
        put("bac +2", 2);
        put("bac +3", 3);
        put("bac +4", 4);
        put("bac +5 et plus", 5);
        put("doctorat", 6);
        put("autodidacte", 7);
        put("qualification avant bac", 8);
    }};

    public static HashMap<String, Integer> contractMapName = new HashMap<>() {{
        put("cdi", 0);
        put("cdd", 1);
        put("intérim", 2);
        put("autre", 3);
        put("stage", 4);
        put("freelance", 5);
        put("lettre d'engagement", 6);
        put("statutaire", 7);
        put("temps partiel", 8);
    }};

    public static HashMap<String, Integer> remoteMapName = new HashMap<>() {{
        put("oui", 0);
        put("hybride", 1);
        put("non", 2);
    }};

    public static HashMap<String, Integer> cityMapName = new HashMap<>() {{
        put("rabat", 0);
        put("salé", 1);
        put("nador", 2);
        put("casablanca", 3);
        put("tanger", 4);
        put("tétouan", 5);
        put("mohammédia", 6);
        put("agadir", 7);
        put("marrakech", 8);
        put("témara", 9);
        put("benguérir", 10);
        put("laâyoune", 11);
        put("oujda", 12);
        put("kénitra", 13);
        put("dakhla", 14);
        put("settat", 15);
        put("guelmim", 16);
        put("errachidia", 17);
        put("ifrane", 18);
        put("berrechid", 19);
        put("skhirate", 20);
        put("fès", 21);
        put("meknès", 22);
        put("taliouine", 23);
        put("béni mellal", 24);
        put("el jadida", 25);
        put("guercif", 26);
        put("khémisset", 27);
        put("taghazout", 28);
        put("hoceima", 29);
        put("safi", 30);
        put("zenata", 31);
        put("larache", 32);
        put("tit mellil", 33);
        put("ben guerir", 34);
        put("kalaa des sraghna", 35);
        put("taroudant", 36);
        put("ouarzazate", 37);
        put("berkane", 38);
        put("tamesna", 39);
        put("khouribga", 40);
        put("oued zem", 41);
        put("all", 42);
    }};
}

