package com.akei.vente.domain;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Commande AKEI : une Entity (identité propre via CommandeId).
 * <p>
 * OBJECTIFS :
 * - invariant : au moins une ligne ;
 * - copie défensive de la liste de lignes (pas de partage de l'interne) ;
 * - total = somme des sous-totaux ;
 * - égalité PAR IDENTITÉ (sur l'id), pas par attributs ;
 * - la liste exposée doit être non modifiable.
 */
public final class Commande {

    private final CommandeId id;
    private final List<LigneCommande> lignes;

    public Commande(CommandeId id, List<LigneCommande> lignes) {
        // TODO : valider id non null, lignes non null et non vide
        // TODO : stocker une copie défensive non modifiable (indice : List.copyOf)
        throw new UnsupportedOperationException("À implémenter");
    }

    public static Commande creer(List<LigneCommande> lignes) {
        return new Commande(CommandeId.nouveau(), lignes);
    }

    public Money total() {
        // TODO : sommer les sousTotal() de chaque ligne (partir de Money.zeroEuro())
        throw new UnsupportedOperationException("À implémenter");
    }

    public CommandeId id() {
        return id;
    }

    public List<LigneCommande> lignes() {
        return lignes;
    }

    @Override
    public boolean equals(Object o) {
        // TODO : égalité PAR IDENTITÉ (sur id uniquement)
        throw new UnsupportedOperationException("À implémenter");
    }

    @Override
    public int hashCode() {
        // TODO : cohérent avec equals (sur id)
        throw new UnsupportedOperationException("À implémenter");
    }

    public record CommandeId(UUID valeur) {
        public CommandeId {
            Objects.requireNonNull(valeur, "L'identifiant ne peut pas être null");
        }

        public static CommandeId nouveau() {
            return new CommandeId(UUID.randomUUID());
        }
    }
}
