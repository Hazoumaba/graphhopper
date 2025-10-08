
# Tâche 2 – IFT3913 : Qualité du logiciel et métriques

## Équipe
- Yasmine Ben Youssef (20237210)
- Hazem Ben Amor (20236062)

## Choix du de la classe
#### Classe : `com.graphhopper.util.StopWatch`

La classe `StopWatch` a été choisie pour sa simplicité, sa précision et sa flexibilité, ce qui la rend idéale pour mesurer les performances dans des projets comme GraphHopper.Les tests unitaires réalisés confirment sa robustesse. De plus elle est légère et absente de dépendances externes.


---

## Documentation des tests
#### Test 1 : `testStartAndStopShouldIncreaseTime()`

**Intention du test :**
Ce test vérifie que quand on démarre le chronomètre et qu’on l’arrête, il mesure bien un temps positif.

**Données de test :**
- Un objet StopWatch vide (new StopWatch()).
- Une pause courte de ~2 millisecondes entre le start() et le stop().

**Oracles :** Le comportement attendu est que le temps mesuré par le chronomètre soit strictement positif après un appel à **start()** suivi de **stop()**. Cela repose sur l'hypothèse que l'horloge système **(System.nanoTime())** avance toujours et que le temps écoulé entre les deux appels est non nul.


---

#### Test 2 : `testGetSecondsOracleComparison()`

**Intention du test :**
Ce test compare les deux méthodes de mesure : **getNanos()** et **getSeconds()**.

**Données de test :**
- Un StopWatch démarré, attente d’environ 3 ms, puis arrêté.
- L’oracle est la formule de conversion entre nanosecondes et secondes.

**Oracles :** Le comportement attendu est que la méthode **getSeconds()** retourne une valeur cohérente avec **getNanos() / 1e9**. L'assertion compare ces deux valeurs avec une tolérance **(1e-9)** pour tenir compte des imprécisions dues aux calculs en virgule flottante

---

#### Test 3 : `testGetCurrentSecondsWhileRunning()`

**Intention du test :**
On teste la méthode **getCurrentSeconds()** pendant que le chrono tourne pour prouver que le temps augmente tant que le chrono n’est pas arrêté

**Données de test :**
- Un StopWatch lancé (start()),
- Deux appels espacés de quelques millisecondes à getCurrentSeconds().

**Oracles :**
Le comportement attendu est que la valeur retournée par **getCurrentSeconds()** augmente pendant que le chronomètre est en cours d'exécution. Cela est vérifié en comparant deux appels successifs à **getCurrentSeconds()** avec un délai entre eux.

---

#### Test 4 : `testGetTimeStringCoversAllUnits()`

**Intention du test :**
Ce test vérifie que **getTimeString()** affiche bien une unité de temps correcte.

**Données de test :**
- Cas 1 : StopWatch démarrée et arrêtée immédiatement.
- Cas 2 : StopWatch démarrée, délai de 5 millisecondes (Thread.sleep(5)), puis arrêtée.

**Oracles :**
Le comportement attendu est que la méthode **getTimeString()** retourne une chaîne contenant une unité de temps valide (ns, µs, ms, ou s). Cela est déterminé en vérifiant que la chaîne contient l'une de ces unités, en fonction de la durée mesurée.


---

#### Test 5 : `testToStringIncludesNameIfPresent()`

**Intention du test :**
Ce test vérifie que si on donne un nom au chronomètre (ici "Timer")

**Données de test :**
- StopWatch sw = new StopWatch("Timer");

**Oracles :**
Le comportement attendu est que la méthode **toString()** inclut le nom passé au constructeur si un nom est défini. Cela est vérifié en s'assurant que la sortie commence par le nom donné.

---

#### Test 6 : `testStopWithoutStartDoesNotCrash()`

**Intention du test :**
Ce test vérifie qu’on peut appeler **stop()** même sans avoir appelé **start()** avant.

**Données de test :**
- StopWatch sw = new StopWatch();
- Appel direct à sw.stop() sans jamais appeler start().

**Oracles :**
Le comportement attendu est que l'appel à **stop()** avant **start()** ne provoque pas d'erreur et que le temps mesuré reste valide (supérieur ou égal à zéro). Cela repose sur la gestion correcte des états internes de la classe.

---

#### Test 7 : `testWithFakerGeneratedName()`

**Intention du test :**
Ce test Vérifie que le nom aléatoire crée par un faker se retrouve bien dans la sortie texte

**Données de test :**
- String randomName = new Faker().app().name();
- StopWatch sw = new StopWatch(randomName);

**Oracles :**
Le comportement attendu est que le nom aléatoire généré par Faker apparaisse dans la sortie de la méthode **toString()**. Cela est vérifié en s'assurant que la chaîne retournée contient le nom généré ou une partie standard de la sortie **(time:)**.


## Pitest et ses mutants

### Vue d'ensemble globale
#### `com.graphhopper.util.StopWatch`
### Avant
| Métrique |  | 
|----------|-------|
| **Line Coverage** | 24% (10/42) | 
| **Mutation Coverage** | 3% (2/65) |
| **Test Strength** | 25% (2/8) |

### Après
| Métrique |  | 
|----------|-------|
| **Line Coverage** | 64% (27/342) | 
| **Mutation Coverage** | 28% (18/65) |
| **Test Strength** | 49% (18/37) |

### Évolution
| Métrique | Évolution |
|----------|-----------|
| **Line Coverage** | + 40% |
| **Mutation Coverage** | + 25% |
| **Test Strength** | + 24% |

---

### Détails
Lors de l’exécution de PIT, la classe StopWatch a généré **65 mutants**. Les tests d’origine n’en détectaient que **2**, ce qui correspondait à une couverture de mutation très faible **(3 %)**. Après l’ajout de nos **7** nouveaux tests, nous avons réussi à tuer **18 mutants**, soit une amélioration significative **(28 % de couverture de mutation)**.
Les nouveaux tests ont permis de repérer plusieurs types d’erreurs potentielles dans la classe.
Par exemple, le test `testGetSecondsOracleComparison()` détecte les **mutants arithmétiques** qui modifient la conversion entre nanosecondes et secondes.
Le test `testGetCurrentSecondsWhileRunning()` détecte les **mutations logiques** où les conditions if **(running)** sont inversées, tandis que `testStopWithoutStartDoesNotCrash()` élimine les mutants qui suppriment **les vérifications internes de robustesse**.
Les tests `testGetTimeStringCoversAllUnits()` et `testToStringIncludesNameIfPresent()` capturent des mutants liés au **formatage du texte** (unités erronées, chaînes nulles, etc.).
Enfin, le test avec Java Faker valide la résistance du code face à des entrées dynamiques, ce qui aide à détecter les mutants liés à des chaînes aléatoires.
En résumé, nos ajouts ont permis de couvrir plusieurs aspects auparavant non testés :
**les calculs temporels, la gestion des conditions logiques, les conversions d’unités, et le formatage du texte.**
Il reste quelques mutants survivants, principalement associés aux conversions en minutes et heures, que nous n’avons pas testées pour éviter d’allonger inutilement le temps d’exécution.


## Java Faker
L'utilisation de la bibliothèque Faker dans les tests unitaires est justifiée par sa capacité à générer des données aléatoires réalistes, ce qui permet de tester des cas variés et imprévisibles. Dans le contexte de la classe StopWatch, Faker est utilisé pour créer des noms aléatoires via la méthode `faker.app().name()`, ce qui garantit que les tests ne se limitent pas à des valeurs statiques ou prédéfinies. Cela renforce la robustesse des tests en vérifiant que la méthode `toString()` inclut correctement un nom généré dynamiquement, peu importe sa valeur. De plus, Faker simplifie la création de données de test tout en rendant les tests plus représentatifs de scénarios réels, sans nécessiter d'efforts supplémentaires pour définir manuellement des données variées
