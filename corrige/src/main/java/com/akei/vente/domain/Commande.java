package com.akei.vente.domain;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Commande AKEI : c'est une Entity (elle a une identité propre, le CommandeId,
 * indépendante de son contenu — deux commandes au contenu identique restent
 * deux commandes distinctes).
 * <p>
 * Au TP1, on l'utilise surtout pour illustrer :
 * - l'identité (l'égalité se fait sur l'id, pas sur les attributs) ;
 * - le calcul du total à partir des Value Objects (composition) ;
 * - un premier invariant : une commande a au moins une ligne.
 * <p>
 * Le rôle d'Aggregate Root et l'encapsulation complète arrivent au TP2/TP3.
 */
public final class Commande {

    private final CommandeId id;
    private final List<LigneCommande> lignes;

    public Commande(CommandeId id, List<LigneCommande> lignes) {
        Objects.requireNonNull(id, "L'identifiant de commande est obligatoire");
        Objects.requireNonNull(lignes, "Les lignes sont obligatoires");
        if (lignes.isEmpty()) {
            throw new IllegalArgumentException("Une commande a au moins une ligne");
        }
        this.id = id;
        // copie défensive : la commande ne partage pas sa liste interne
        this.lignes = List.copyOf(lignes);
    }

    public static Commande creer(List<LigneCommande> lignes) {
        return new Commande(CommandeId.nouveau(), lignes);
    }

    /** Total de la commande = somme des sous-totaux de chaque ligne. */
    public Money total() {
        Money total = Money.zeroEuro();
        for (LigneCommande ligne : lignes) {
            total = total.plus(ligne.sousTotal());
        }
        return total;
    }

    public CommandeId id() {
        return id;
    }

    /** Lecture seule : on n'expose jamais la liste interne en mutable. */
    public List<LigneCommande> lignes() {
        return lignes;
    }

    /** Égalité d'Entity : par identité, jamais par attributs. */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Commande other)) return false;
        return id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    /** Value Object identifiant — typé plutôt qu'un UUID nu (primitive obsession). */
    public record CommandeId(UUID valeur) {
        public CommandeId {
            Objects.requireNonNull(valeur, "L'identifiant ne peut pas être null");
        }

        public static CommandeId nouveau() {
            return new CommandeId(UUID.randomUUID());
        }
    }
}
