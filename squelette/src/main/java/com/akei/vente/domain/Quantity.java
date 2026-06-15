package com.akei.vente.domain;

/**
 * Value Object : quantité d'un article.
 * <p>
 * OBJECTIF : une quantité est strictement positive (le 0 et le négatif n'ont
 * pas de sens métier). Immuable : 'plus' renvoie une NOUVELLE Quantity.
 */
public record Quantity(int valeur) {

    public Quantity {
        // TODO : refuser valeur <= 0 (IllegalArgumentException)
        throw new UnsupportedOperationException("À implémenter");
    }

    public static Quantity of(int valeur) {
        return new Quantity(valeur);
    }

    /** Renvoie une NOUVELLE Quantity (ne pas muter this). */
    public Quantity plus(Quantity autre) {
        // TODO : additionner les valeurs et renvoyer une nouvelle Quantity
        throw new UnsupportedOperationException("À implémenter");
    }
}
