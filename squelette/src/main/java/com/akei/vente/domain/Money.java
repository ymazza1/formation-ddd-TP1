package com.akei.vente.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;
import java.util.Objects;

/**
 * Value Object : un montant monétaire (montant + devise).
 * <p>
 * OBJECTIFS / invariants à garantir :
 * - montant et devise non null ;
 * - montant jamais négatif ;
 * - normaliser le montant à 2 décimales (RoundingMode.HALF_UP) ;
 * - n'additionner / comparer que des montants de MÊME devise ;
 * - une remise ne rend JAMAIS le montant négatif (plafonner à zéro) ;
 * - égalité PAR VALEUR : 10 == 10.00, indépendamment des décimales.
 *
 * Astuce : ici on n'utilise pas le record car on veut normaliser le BigDecimal
 * et maîtriser equals/hashCode (comparaison numérique, pas textuelle).
 */
public final class Money {

    public static final Currency EURO = Currency.getInstance("EUR");

    private final BigDecimal montant;
    private final Currency devise;

    private Money(BigDecimal montant, Currency devise) {
        // TODO : valider non null, refuser négatif, normaliser à 2 décimales
        throw new UnsupportedOperationException("À implémenter");
    }

    public static Money euros(String montant) {
        return new Money(new BigDecimal(montant), EURO);
    }

    public static Money euros(BigDecimal montant) {
        return new Money(montant, EURO);
    }

    public static Money zeroEuro() {
        return new Money(BigDecimal.ZERO, EURO);
    }

    public Money plus(Money autre) {
        // TODO : exiger même devise, additionner, renvoyer un nouveau Money
        throw new UnsupportedOperationException("À implémenter");
    }

    public Money multiplie(int facteur) {
        // TODO : refuser un facteur négatif, multiplier, renvoyer un nouveau Money
        throw new UnsupportedOperationException("À implémenter");
    }

    public Money appliqueRemise(Money remise) {
        // TODO : soustraire la remise ; si le résultat est négatif, plafonner à zéro
        throw new UnsupportedOperationException("À implémenter");
    }

    public BigDecimal montant() {
        return montant;
    }

    public Currency devise() {
        return devise;
    }

    @Override
    public boolean equals(Object o) {
        // TODO : égalité par valeur (montant comparé numériquement) + devise
        throw new UnsupportedOperationException("À implémenter");
    }

    @Override
    public int hashCode() {
        // TODO : cohérent avec equals
        throw new UnsupportedOperationException("À implémenter");
    }

    @Override
    public String toString() {
        return montant.toPlainString() + " " + devise.getCurrencyCode();
    }
}
