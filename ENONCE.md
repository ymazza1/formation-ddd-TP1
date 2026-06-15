# TP1 — Modéliser une Commande et ses Value Objects

**Durée indicative :** 1h – 1h15
**Contexte :** AKEI, enseigne omnicanale de meubles. Bounded Context implicite : **la Vente**.
**Stack :** Java 21, Maven, JUnit 5 + AssertJ. Tests unitaires classiques (pas de TDD imposé sur ce TP).

---

## Objectif pédagogique

Mettre en pratique la distinction **Entity / Value Object** et le placement des **invariants** dans le type lui-même, plutôt que dans du code applicatif autour.

À la fin, vous devez avoir compris pourquoi `Money` vaut mieux que `BigDecimal` nu, et pourquoi un objet métier ne doit jamais pouvoir exister dans un état invalide.

---

## Ce qui vous est fourni

- Un squelette Maven (`squelette/`) avec les classes à compléter (cherchez les `// TODO`).
- Une suite de tests JUnit 5 **déjà écrite** (`ValueObjectsTest`). Elle décrit le comportement attendu : faites-la passer.
- Un corrigé (`corrige/`) à ne consulter qu'après avoir cherché.

Lancer les tests : `mvn test` à la racine du module.

---

## Travail demandé

### 1. `Sku` (Value Object)
Référence article. Format métier : **3 lettres majuscules + tiret + 4 chiffres** (ex. `CAN-0427`).
- Invariant : impossible de construire un `Sku` hors format ou `null`.

### 2. `Quantity` (Value Object)
- Invariant : **strictement positive** (zéro et négatif interdits).
- Immuable : `plus(...)` renvoie une **nouvelle** instance.

### 3. `Money` (Value Object)
- Invariants : non null, **jamais négatif**, devise obligatoire.
- Normalisé à 2 décimales.
- `plus`, `multiplie`, et `appliqueRemise` (une remise **ne rend jamais le montant négatif** : on plafonne à zéro).
- **Égalité par valeur** : `10 €` doit égaler `10.00 €`.

### 4. `LigneCommande`
- Composée d'un `Sku`, d'une `Quantity` et d'un prix unitaire (`Money`).
- Calcule son **sous-total**.
- **Question à débattre** : Entity ou Value Object ? Justifiez votre choix.

### 5. `Commande` (Entity)
- Identité propre (`CommandeId`).
- Invariant : **au moins une ligne**.
- `total()` = somme des sous-totaux.
- **Égalité par identité** (deux commandes au contenu identique restent distinctes).
- La liste de lignes exposée est **non modifiable** (copie défensive).

---

## Points de discussion (à garder pour le débrief)

- Pourquoi placer l'invariant dans le constructeur plutôt que dans un service de validation ?
- En quoi le `record` Java aide-t-il pour les Value Objects (et pourquoi `Money` ne l'utilise PAS) ?
- `LigneCommande` : qu'est-ce qui ferait basculer ce choix Entity ↔ Value Object ?

---

## Extension optionnelle (pour les plus rapides)

Ajoutez un plafond de quantité par ligne (ex. 99) sous forme d'invariant, et un test associé. Discutez : est-ce un invariant métier ou une règle de validation ? Où le placer ?
