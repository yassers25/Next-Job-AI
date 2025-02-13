package com.parsers;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {

	protected static final Set<String> VALID_LANGUAGES = Set.of(
		"espagnol",
		"anglais",
		"russe",
		"allemand",
		"arabic",
		"français"
	);

	protected static final Map<Pattern, String> LANGUAGE_MAPPER = Map.ofEntries(
		Map.entry(Pattern.compile("çais|fra|fr|français|français", Pattern.CASE_INSENSITIVE), "français"),
		Map.entry(Pattern.compile("gnol|esp|es|espagnol", Pattern.CASE_INSENSITIVE), "espagnol"),
		Map.entry(Pattern.compile("lais|ang|an|eng|english|anglais", Pattern.CASE_INSENSITIVE), "anglais"),
		Map.entry(Pattern.compile("sse|ru|russe", Pattern.CASE_INSENSITIVE), "russe"),
		Map.entry(Pattern.compile("mand|all|allemand", Pattern.CASE_INSENSITIVE), "allemand"),
		Map.entry(Pattern.compile("abe|ar|arabe|arabic|berbère", Pattern.CASE_INSENSITIVE), "arabic")
	);

	protected static String mapLanguage(String language) {
		for (Map.Entry<Pattern, String> entry : LANGUAGE_MAPPER.entrySet()) {
			if (entry.getKey().matcher(language).find()) {
				return entry.getValue();

			}
		}
		return null;
	}


	protected static final Set<String> VALID_CONTRACT_TYPES = Set.of(
			"cdi",
			"cdd",
			"intérim",
			"autre",
			"stage",
			"freelance",
			"lettre d'engagement",
			"statutaire",
			"temps partiel");

	protected static final Map<Pattern, String> CONTRACT_TYPE_MAPPER = Map.ofEntries(
			Map.entry(Pattern.compile("intérim|interim", Pattern.CASE_INSENSITIVE), "intérim"),
			Map.entry(Pattern.compile("freelance|free lance", Pattern.CASE_INSENSITIVE), "freelance"),
			Map.entry(Pattern.compile("stage", Pattern.CASE_INSENSITIVE), "stage"),
			Map.entry(Pattern.compile("lettre", Pattern.CASE_INSENSITIVE), "lettre d'engagement"),
			Map.entry(Pattern.compile("statutaire", Pattern.CASE_INSENSITIVE), "statutaire"),
			Map.entry(Pattern.compile("temps partiel", Pattern.CASE_INSENSITIVE), "temps partiel"),
			Map.entry(Pattern.compile("cdd", Pattern.CASE_INSENSITIVE), "cdd"),
			Map.entry(Pattern.compile("cdi", Pattern.CASE_INSENSITIVE), "cdi"),
			Map.entry(Pattern.compile("autre", Pattern.CASE_INSENSITIVE), "autre")

	);

	protected static final Set<String> VALID_ACTIVITY_SECTORS = Set.of(
			"Activités associatives",
			"Administration publique",
			"Aéronautique, navale",
			"Agriculture, pêche, aquaculture",
			"Agroalimentaire",
			"Ameublement, décoration",
			"Automobile, matériels de transport, réparation",
			"Banque, assurance, finances",
			"BTP, construction",
			"Centres d´appel, hotline, call center",
			"Chimie, pétrochimie, matières premières, mines",
			"Conseil, audit, comptabilité",
			"Distribution, vente, commerce de gros",
			"Édition, imprimerie",
			"Éducation, formation",
			"Electricité, eau, gaz, nucléaire, énergie",
			"Environnement, recyclage",
			"Equip. électriques, électroniques, optiques, précision",
			"Equipements mécaniques, machines",
			"Espaces verts, forêts, chasse",
			"Évènementiel, hôte(sse), accueil",
			"Hôtellerie, restauration",
			"Immobilier, architecture, urbanisme",
			"Import, export",
			"Industrie pharmaceutique",
			"Industrie, production, fabrication, autres",
			"Informatique, SSII, Internet",
			"Ingénierie, études développement",
			"Intérim, recrutement",
			"Location",
			"Luxe, cosmétiques",
			"Maintenance, entretien, service après vente",
			"Manutention",
			"Marketing, communication, médias",
			"Métallurgie, sidérurgie",
			"Nettoyage, sécurité, surveillance",
			"Papier, bois, caoutchouc, plastique, verre, tabac",
			"Produits de grande consommation",
			"Qualité, méthodes",
			"Recherche et développement",
			"Santé, pharmacie, hôpitaux, équipements médicaux",
			"Secrétariat",
			"Services aéroportuaires et maritimes",
			"Services autres",
			"Services collectifs et sociaux, services à la personne",
			"Sport, action culturelle et sociale",
			"Télécom",
			"Textile, habillement, cuir, chaussures",
			"Tourisme, loisirs",
			"Transports, logistique, services postaux");

	protected static final Map<Pattern, String> ACTIVITY_SECTOR_MAPPER = Map.ofEntries(
			Map.entry(Pattern.compile("centre|hotline|centres|center", Pattern.CASE_INSENSITIVE),
					"Centres d´appel, hotline, call center"),
			Map.entry(Pattern.compile("btp", Pattern.CASE_INSENSITIVE), "BTP, construction"),
			Map.entry(Pattern.compile("comptabilité|conseil|consulting", Pattern.CASE_INSENSITIVE),
					"Conseil, audit, comptabilité"),
			Map.entry(Pattern.compile("intérim|recrutement|débutant|fraichement", Pattern.CASE_INSENSITIVE),
					"Intérim, recrutement"),
			Map.entry(Pattern.compile("tourisme|hôtellerie|restauration", Pattern.CASE_INSENSITIVE),
					"Tourisme, loisirs"),
			Map.entry(Pattern.compile("santé|pharmaceutique|médical", Pattern.CASE_INSENSITIVE),
					"Santé, pharmacie, hôpitaux, équipements médicaux"),
			Map.entry(Pattern.compile("éducation|formation", Pattern.CASE_INSENSITIVE),
					"Éducation, formation"),
			Map.entry(Pattern.compile("banque|assurance|finances", Pattern.CASE_INSENSITIVE),
					"Banque, assurance, finances"),
			Map.entry(Pattern.compile("automobile", Pattern.CASE_INSENSITIVE),
					"Automobile, matériels de transport, réparation"),
			Map.entry(Pattern.compile("informatique|offshoring", Pattern.CASE_INSENSITIVE),
					"Informatique, SSII, Internet"),
			Map.entry(Pattern.compile("aéronautique|navale", Pattern.CASE_INSENSITIVE),
					"Aéronautique, navale"),
					Map.entry(Pattern.compile("grande distribution|distribution", Pattern.CASE_INSENSITIVE),
					"Distribution, vente, commerce de gros"),
					Map.entry(Pattern.compile("immobilier", Pattern.CASE_INSENSITIVE),
					"Immobilier, architecture, urbanisme"),
					Map.entry(Pattern.compile("energie|gaz|electricité", Pattern.CASE_INSENSITIVE),
					"Electricité, eau, gaz, nucléaire, énergie"),
					Map.entry(Pattern.compile("alimentaire|agroalimentaire", Pattern.CASE_INSENSITIVE),
					"Agroalimentaire"),
					Map.entry(Pattern.compile("agriculture|environnement", Pattern.CASE_INSENSITIVE),
					"Agriculture, pêche, aquaculture"),
					Map.entry(Pattern.compile("import|export", Pattern.CASE_INSENSITIVE), "Import, export"),
					Map.entry(Pattern.compile(
						"marketing|publicité|pub|communication|multimédia|audiovisuel|allemand|anglais",
						Pattern.CASE_INSENSITIVE), "Marketing, communication, médias"),
						Map.entry(Pattern.compile("transports|transport|ferroviaire", Pattern.CASE_INSENSITIVE),
						"Transports, logistique, services postaux"),
						Map.entry(Pattern.compile("textile", Pattern.CASE_INSENSITIVE),
					"Textile, habillement, cuir, chaussures"),
					Map.entry(Pattern.compile("secrétariat", Pattern.CASE_INSENSITIVE), "Secrétariat"),
					Map.entry(Pattern.compile("service après", Pattern.CASE_INSENSITIVE),
					"Maintenance, entretien, service après vente"),
					Map.entry(Pattern.compile("electro-mécanique|equipements", Pattern.CASE_INSENSITIVE),
					"Equipements mécaniques, machines"),
					Map.entry(Pattern.compile("ingénierie", Pattern.CASE_INSENSITIVE),
					"Ingénierie, études développement"),
					Map.entry(Pattern.compile("ameublement", Pattern.CASE_INSENSITIVE), "Ameublement, décoration"),
					Map.entry(Pattern.compile("papier|plasturgie", Pattern.CASE_INSENSITIVE),
					"Papier, bois, caoutchouc, plastique, verre, tabac"),
					Map.entry(Pattern.compile("nettoyage", Pattern.CASE_INSENSITIVE),
					"Nettoyage, sécurité, surveillance"),
					Map.entry(Pattern.compile("chimie", Pattern.CASE_INSENSITIVE),
					"Chimie, pétrochimie, matières premières, mines"),
					Map.entry(Pattern.compile("luxe", Pattern.CASE_INSENSITIVE), "Luxe, cosmétiques"),
					Map.entry(Pattern.compile("telecom", Pattern.CASE_INSENSITIVE), "Télécom"),
					Map.entry(Pattern.compile("édition|imprimerie", Pattern.CASE_INSENSITIVE),
					"Édition, imprimerie"),
					Map.entry(Pattern.compile("associatives", Pattern.CASE_INSENSITIVE), "Activités associatives"),
					Map.entry(Pattern.compile("métallurgie", Pattern.CASE_INSENSITIVE), "Métallurgie, sidérurgie"),
					Map.entry(Pattern.compile("juridique", Pattern.CASE_INSENSITIVE),
					"Services collectifs et sociaux, services à la personne"),
					Map.entry(Pattern.compile("industrie métallurgique", Pattern.CASE_INSENSITIVE),
					"Services autres"),
					Map.entry(Pattern.compile("autres|extraction|mines|indifférent|^$", Pattern.CASE_INSENSITIVE),
							"Services autres"),
					Map.entry(Pattern.compile("equip", Pattern.CASE_INSENSITIVE),
					"Equip. électriques, électroniques, optiques, précision"));
					
	protected static final Set<String> VALID_CITIES = Set.of(
			"rabat",
			"salé",
			"nador",
			"casablanca",
			"tanger",
			"tétouan",
			"mohammédia",
			"agadir",
			"marrakech",
			"témara",
			"benguérir",
			"laâyoune",
			"oujda",
			"kénitra",
			"dakhla",
			"settat",
			"guelmim",
			"errachidia",
			"ifrane",
			"berrechid",
			"skhirate",
			"fès",
			"meknès",
			"taliouine",
			"béni mellal",
			"el jadida",
			"guercif",
			"khémisset",
			"taghazout",
			"hoceima",
			"safi",
			"zenata",
			"larache",
			"tit mellil",
			"ben guerir",
			"kalaa des sraghna",
			"taroudant",
			"ouarzazate",
			"berkane",
			"tamesna",
			"khouribga",
			"oued zem",
			"all");
	protected static final Map<Pattern, String> CITY_MAPPER = Map.ofEntries(
			Map.entry(Pattern.compile("salé|sale|sala|technopolis", Pattern.CASE_INSENSITIVE), "salé"),
			Map.entry(Pattern.compile("khouribga", Pattern.CASE_INSENSITIVE), "khouribga"),
			Map.entry(Pattern.compile("rabat", Pattern.CASE_INSENSITIVE), "rabat"),
			Map.entry(Pattern.compile("agadir", Pattern.CASE_INSENSITIVE), "agadir"),
			Map.entry(Pattern.compile("kénitra|kenitra", Pattern.CASE_INSENSITIVE), "kénitra"),
			Map.entry(Pattern.compile("marrakech", Pattern.CASE_INSENSITIVE), "marrakech"),
			Map.entry(Pattern.compile("meknès|meknes|meknas", Pattern.CASE_INSENSITIVE), "meknès"),
			Map.entry(Pattern.compile("tétouan|tetouan|cabo negro", Pattern.CASE_INSENSITIVE), "tétouan"),
			Map.entry(Pattern.compile("tanger", Pattern.CASE_INSENSITIVE), "tanger"),
			Map.entry(Pattern.compile("fès|fes|fez", Pattern.CASE_INSENSITIVE), "fès"),
			Map.entry(Pattern.compile("Laayoune", Pattern.CASE_INSENSITIVE), "laâyoune"),
			Map.entry(Pattern.compile("benguerir|benguérir", Pattern.CASE_INSENSITIVE), "benguérir"),
			Map.entry(Pattern.compile("mohammedia|mohammédia|mohamedia|mansouria",
					Pattern.CASE_INSENSITIVE), "mohammédia"),
			Map.entry(Pattern.compile(
					"casablanca|csablanca|jorf|ain sebaa|sebaa|bouskoura|bouznika|nouacer|had soualem|nouaceur|rahal|sebaâ|dar bouazza|jorf lasfar|ain harrouda|maarouf",
					Pattern.CASE_INSENSITIVE), "casablanca"),
			Map.entry(Pattern.compile("temara|témara", Pattern.CASE_INSENSITIVE), "témara"),
			Map.entry(Pattern.compile("skhirat|skhirate|sekhirat", Pattern.CASE_INSENSITIVE), "skhirate"),
			Map.entry(Pattern.compile("béni mellal|beni mellal", Pattern.CASE_INSENSITIVE), "béni mellal"),
			Map.entry(Pattern.compile("khémisset|khemisset", Pattern.CASE_INSENSITIVE), "khémisset"),
			Map.entry(Pattern.compile("guelmim", Pattern.CASE_INSENSITIVE), "guelmim"),
			Map.entry(Pattern.compile("hoceima", Pattern.CASE_INSENSITIVE), "hoceima"),
			Map.entry(Pattern.compile("jadida", Pattern.CASE_INSENSITIVE), "el jadida"),
			Map.entry(Pattern.compile("oujda", Pattern.CASE_INSENSITIVE), "oujda"),
			Map.entry(Pattern.compile("mellil", Pattern.CASE_INSENSITIVE), "tit mellil"),
			Map.entry(Pattern.compile("dakhla", Pattern.CASE_INSENSITIVE), "dakhla"),
			Map.entry(Pattern.compile("ben ahmad", Pattern.CASE_INSENSITIVE), "settat"),
			Map.entry(Pattern.compile("maroc|international|villes|distance|remote", Pattern.CASE_INSENSITIVE), "all"));

	protected static final Set<String> VALID_EXPERIENCE = Set.of(
			"fraichement diplômé",
			"débutant (de 1 à 3 ans)",
			"junior (de 3 à 5 ans)",
			"senior (de 5 à 10 ans)",
			"expert (10 ou plus)"

	);

	protected static final Set<String> VALID_STUDY_LEVELS = Set.of(
			"bac",
			"bac +1",
			"bac +2",
			"bac +3",
			"bac +4",
			"bac +5 et plus",
			"doctorat",
			"autodidacte",
			"qualification avant bac");

	protected static final Set<String> VALID_FUNCTIONS = Set.of(
			"achats",
			"commercial, vente",
			"gestion, comptabilité, finance",
			"informatique, nouvelles technologies",
			"juridique",
			"management, direction générale",
			"marketing, communication",
			"métiers de la santé et du social",
			"métiers des services",
			"métiers du btp",
			"production, maintenance, qualité",
			"r&d, gestion de projets",
			"rh, formation",
			"secrétariat, assistanat",
			"télémarketing, téléassistance",
			"tourisme, hôtellerie, restauration",
			"transport, logistique");

	protected static final Map<Pattern, String> FUNCTION_MAPPER = Map.ofEntries(
			// Supply Chain & Purchasing
			Map.entry(Pattern.compile("achats|supply chain", Pattern.CASE_INSENSITIVE),
					"achats"),

			// Commercial & Sales
			Map.entry(Pattern.compile("commercial|vente|export|distribution|administration des ventes|sav",
					Pattern.CASE_INSENSITIVE),
					"commercial, vente"),

			// Finance & Management
			Map.entry(Pattern.compile("gestion|comptabilité|finance|audit|conseil|banque|assurance",
					Pattern.CASE_INSENSITIVE),
					"gestion, comptabilité, finance"),

			// IT & Technology
			Map.entry(Pattern.compile("informatique|electronique|multimédia|internet|télécoms|réseaux",
					Pattern.CASE_INSENSITIVE),
					"informatique, nouvelles technologies"),

			// Legal
			Map.entry(Pattern.compile("juridique|avocat|juriste|fiscaliste", Pattern.CASE_INSENSITIVE),
					"juridique"),

			// Management
			Map.entry(Pattern.compile("direction|management|dirigeants|responsable de département",
					Pattern.CASE_INSENSITIVE),
					"management, direction générale"),

			// Marketing & Communication
			Map.entry(Pattern.compile("communication|publicité|rp|marketing|journalisme|traduction",
					Pattern.CASE_INSENSITIVE),
					"marketing, communication"),

			// Healthcare & Social
			Map.entry(Pattern.compile("médical|paramédical|santé|social", Pattern.CASE_INSENSITIVE),
					"métiers de la santé et du social"),

			// Services
			Map.entry(Pattern.compile(
					"coursier|gardiennage|propreté|call centers|caméraman|monteur|preneur de son|imprimerie",
					Pattern.CASE_INSENSITIVE),
					"métiers des services"),

			// Construction & Architecture
			Map.entry(Pattern.compile("btp|travaux|chantiers|electricité|urbanisme|architecture",
					Pattern.CASE_INSENSITIVE),
					"métiers du btp"),

			// Production & Quality
			Map.entry(Pattern.compile(
					"production|qualité|maintenance|sécurité|industrialisation|méthodes|process|environnement",
					Pattern.CASE_INSENSITIVE),
					"production, maintenance, qualité"),

			// R&D & Project Management
			Map.entry(Pattern.compile("r&d|gestion projet|etudes|projet", Pattern.CASE_INSENSITIVE),
					"r&d, gestion de projets"),

			// HR & Training
			Map.entry(Pattern.compile("rh|ressources humaines|formation|personnel|enseignement",
					Pattern.CASE_INSENSITIVE),
					"rh, formation"),

			// Administrative Support
			Map.entry(Pattern.compile("assistanat|services généraux|assistanat de direction",
					Pattern.CASE_INSENSITIVE),
					"secrétariat, assistanat"),

			// Call Centers
			Map.entry(Pattern.compile("call centers|télémarketing", Pattern.CASE_INSENSITIVE),
					"télémarketing, téléassistance"),

			// Tourism & Hospitality
			Map.entry(Pattern.compile("tourisme|hôtellerie|restauration", Pattern.CASE_INSENSITIVE),
					"tourisme, hôtellerie, restauration"),

			// Transport & Logistics
			Map.entry(Pattern.compile("logistique|transport", Pattern.CASE_INSENSITIVE),
					"transport, logistique"));


	

	protected static String findClosestFunctionMatch(String input) {
        // Check for pattern matches
        for (Map.Entry<Pattern, String> entry : FUNCTION_MAPPER.entrySet()) {
            if (entry.getKey().matcher(input).find()) {
                return entry.getValue();
            }
        }
        
        // Fallback to similarity matching if no pattern match is found
        return findMostSimilarFunction(input);
    }
    
	private static String findMostSimilarFunction(String input) {
			String mostSimilar = VALID_FUNCTIONS.iterator().next();
			int maxSimilarity = 0;
			
			for (String function : VALID_FUNCTIONS) {
					int similarity = calculateSimilarity(input, function);
					if (similarity > maxSimilarity) {
							maxSimilarity = similarity;
							mostSimilar = function;
					}
			}
			
			return mostSimilar;
	}
	
	private static int calculateSimilarity(String str1, String str2) {
			Set<String> words1 = new HashSet<>(Arrays.asList(str1.split("\\s+|/|,")));
			Set<String> words2 = new HashSet<>(Arrays.asList(str2.split("\\s+|/|,")));
			
			int commonWords = 0;
			for (String word : words1) {
					if (words2.contains(word)) {
							commonWords++;
					}
			}
			
			return commonWords;
	}

	protected static String mapCity(String city) {
		for (Map.Entry<Pattern, String> entry : CITY_MAPPER.entrySet()) {
			if (entry.getKey().matcher(city).find()) {
				return entry.getValue();

			}
		}
		return city;
	}

	protected static String mapSector(String activitySector) {
		for (Map.Entry<Pattern, String> entry : ACTIVITY_SECTOR_MAPPER.entrySet()) {
			if (entry.getKey().matcher(activitySector).find()) {
				return entry.getValue();
			}
		}
		return activitySector;
	}
	protected static String mapContractType(String contract){
		if(contract == null){
				return null;
		}
		for (Map.Entry<Pattern, String> entry : CONTRACT_TYPE_MAPPER.entrySet()) {
				if (entry.getKey().matcher(contract).find()) {
						return entry.getValue();
				}
		}
		return contract;
	}

	public static String extractUsingRegex(String input, String regex) {
		if (input == null || input.isEmpty()) {
			return null; // Avoid processing null or empty input
		}
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(input);
		if (matcher.find()) {
			return matcher.group().trim();
		}
		return null;
	}


	public static Map<Pattern, String> STUDY_LEVEL_MAPPER = Map.ofEntries(
      Map.entry(Pattern.compile("autodidacte", Pattern.CASE_INSENSITIVE), "autodidacte"),
      Map.entry(Pattern.compile("niveau bac|qualification", Pattern.CASE_INSENSITIVE), "qualification avant bac"),
      Map.entry(Pattern.compile("doctorat|doctrat", Pattern.CASE_INSENSITIVE), "doctorat"),
      Map.entry(Pattern.compile("bac\\+1|bac +1", Pattern.CASE_INSENSITIVE), "bac +1"),
      Map.entry(Pattern.compile("bac\\+2|bac +2", Pattern.CASE_INSENSITIVE), "bac +2"),
      Map.entry(Pattern.compile("bac\\+3|bac +3", Pattern.CASE_INSENSITIVE), "bac +3"),
      Map.entry(Pattern.compile("bac\\+4|bac +4", Pattern.CASE_INSENSITIVE), "bac +4"),
      Map.entry(Pattern.compile("bac\\+5|bac +5", Pattern.CASE_INSENSITIVE), "bac +5"));

  protected static String mapStudyLevel(String studyLevel) {
    if (studyLevel == null) {
      return null;
    }
    for (Map.Entry<Pattern, String> entry : STUDY_LEVEL_MAPPER.entrySet()) {
      if (entry.getKey().matcher(studyLevel).find()) {
        return entry.getValue();

      }
    }
    return studyLevel;
  }
	// private static String standardizeRemoteWork(String remoteWork) {
	// if (remoteWork == null || remoteWork.equalsIgnoreCase("Non")) return
	// "On-site";
	// if (remoteWork.equalsIgnoreCase("Hybride")) return "Hybrid";
	// if (remoteWork.equalsIgnoreCase("Oui")) return "Remote";
	// return "unknown";
	// }

	// private static String limitStringLength(String input, int maxLength) {
	// if (input == null) return "No description available";
	// return input.length() > maxLength ? input.substring(0, maxLength) + "..." :
	// input;
	// }

}
