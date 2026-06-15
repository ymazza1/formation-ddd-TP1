package com.akei.vente.domain;

import java.util.Objects;

/**
 * Ligne de commande chez AKEI.
 * <p>
 * Choix de modélisation (à débattre en TP) : on la modélise comme un
 * Value Object. Une ligne est entièrement définie par (sku, quantité,
 * prix unitaire). Deux lignes identiques sont interchangeables ; on ne
 * suit pas le « cycle de vie » d'une ligne indépendamment de sa commande.
 * L'identité métier appartient à la Commande (Entity / futur Aggregate Root),
 * pas à ses lignes.
 * <p>
 * Comportement porté par le VO : le sous-total (prix unitaire x quantité).
 */
public record LigneCommande(Sku sku, Quantity quantite, Money prixUnitaire) {

    public LigneCommande {
        Objects.requireNonNull(sku, "Le SKU est obligatoire");
        Objects.requireNonNull(quantite, "La quantité est obligatoire");
        Objects.requireNonNull(prixUnitaire, "Le prix unitaire est obligatoire");
    }

    /** Sous-total de la ligne = prix unitaire x quantité. */
    public Money sousTotal() {
        return prixUnitaire.multiplie(quantite.valeur());
    }
}
