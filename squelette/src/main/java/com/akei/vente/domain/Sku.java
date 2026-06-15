package com.akei.vente.domain;

/**
 * Value Object : référence article du catalogue AKEI.
 * <p>
 * Format métier attendu : 3 lettres majuscules + '-' + 4 chiffres (ex. "CAN-0427").
 *
 * OBJECTIF : un Sku ne doit JAMAIS pouvoir exister dans un état invalide.
 * L'invariant de format est porté par le constructeur.
 */
public record Sku(String valeur) {

    // Indice : java.util.regex.Pattern, motif "^[A-Z]{3}-\\d{4}$"

    public Sku {
        // TODO : refuser null (IllegalArgumentException)
        // TODO : refuser une valeur qui ne respecte pas le format AAA-9999
        throw new UnsupportedOperationException("À implémenter");
    }

    public static Sku of(String valeur) {
        return new Sku(valeur);
    }
}
