package com.akei.vente.domain;

import java.util.Objects;

/**
 * Ligne de commande AKEI.
 * <p>
 * QUESTION À DÉBATTRE EN TP : Entity ou Value Object ?
 * Ici on propose un Value Object (défini par sku + quantité + prix unitaire).
 *
 * OBJECTIF : valider les champs obligatoires et calculer le sous-total.
 */
public record LigneCommande(Sku sku, Quantity quantite, Money prixUnitaire) {

    public LigneCommande {
        // TODO : refuser sku / quantite / prixUnitaire null
        throw new UnsupportedOperationException("À implémenter");
    }

    /** Sous-total = prix unitaire x quantité. */
    public Money sousTotal() {
        // TODO : utiliser Money.multiplie avec la valeur de la quantité
        throw new UnsupportedOperationException("À implémenter");
    }
}
