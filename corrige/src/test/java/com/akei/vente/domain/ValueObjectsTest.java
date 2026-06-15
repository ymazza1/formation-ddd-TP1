package com.akei.vente.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("TP1 — Value Objects & Commande")
class ValueObjectsTest {

    @Nested
    @DisplayName("Sku")
    class SkuTest {

        @Test
        @DisplayName("accepte un format valide AAA-9999")
        void formatValide() {
            assertThat(Sku.of("CAN-0427").valeur()).isEqualTo("CAN-0427");
        }

        @Test
        @DisplayName("refuse un format invalide")
        void formatInvalide() {
            assertThatThrownBy(() -> Sku.of("canape"))
                    .isInstanceOf(IllegalArgumentException.class);
            assertThatThrownBy(() -> Sku.of("CA-0427"))
                    .isInstanceOf(IllegalArgumentException.class);
            assertThatThrownBy(() -> Sku.of("CAN-42"))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("refuse null")
        void refuseNull() {
            assertThatThrownBy(() -> Sku.of(null))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("égalité par valeur")
        void egaliteParValeur() {
            assertThat(Sku.of("CAN-0427")).isEqualTo(Sku.of("CAN-0427"));
        }
    }

    @Nested
    @DisplayName("Quantity")
    class QuantityTest {

        @Test
        @DisplayName("accepte une valeur strictement positive")
        void positive() {
            assertThat(Quantity.of(3).valeur()).isEqualTo(3);
        }

        @Test
        @DisplayName("refuse zéro et négatif")
        void refuseZeroEtNegatif() {
            assertThatThrownBy(() -> Quantity.of(0))
                    .isInstanceOf(IllegalArgumentException.class);
            assertThatThrownBy(() -> Quantity.of(-1))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("plus renvoie une nouvelle quantité (immutabilité)")
        void plusImmutable() {
            Quantity deux = Quantity.of(2);
            Quantity resultat = deux.plus(Quantity.of(3));
            assertThat(resultat).isEqualTo(Quantity.of(5));
            assertThat(deux.valeur()).isEqualTo(2); // inchangée
        }
    }

    @Nested
    @DisplayName("Money")
    class MoneyTest {

        @Test
        @DisplayName("refuse un montant négatif")
        void refuseNegatif() {
            assertThatThrownBy(() -> Money.euros("-1.00"))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("égalité par valeur indépendante du nombre de décimales")
        void egaliteParValeur() {
            assertThat(Money.euros("10")).isEqualTo(Money.euros("10.00"));
            assertThat(Money.euros("10").hashCode())
                    .isEqualTo(Money.euros("10.00").hashCode());
        }

        @Test
        @DisplayName("addition de même devise")
        void addition() {
            assertThat(Money.euros("10.00").plus(Money.euros("2.50")))
                    .isEqualTo(Money.euros("12.50"));
        }

        @Test
        @DisplayName("multiplication par un entier")
        void multiplication() {
            assertThat(Money.euros("10.00").multiplie(3))
                    .isEqualTo(Money.euros("30.00"));
        }

        @Test
        @DisplayName("une remise ne rend jamais le montant négatif (plafonné à zéro)")
        void remisePlafonneeAZero() {
            assertThat(Money.euros("10.00").appliqueRemise(Money.euros("15.00")))
                    .isEqualTo(Money.zeroEuro());
        }
    }

    @Nested
    @DisplayName("LigneCommande")
    class LigneCommandeTest {

        @Test
        @DisplayName("sous-total = prix unitaire x quantité")
        void sousTotal() {
            LigneCommande ligne = new LigneCommande(
                    Sku.of("CAN-0427"), Quantity.of(3), Money.euros("199.99"));
            assertThat(ligne.sousTotal()).isEqualTo(Money.euros("599.97"));
        }
    }

    @Nested
    @DisplayName("Commande")
    class CommandeTest {

        @Test
        @DisplayName("le total est la somme des sous-totaux")
        void total() {
            Commande commande = Commande.creer(List.of(
                    new LigneCommande(Sku.of("CAN-0427"), Quantity.of(2), Money.euros("199.99")),
                    new LigneCommande(Sku.of("TAB-0010"), Quantity.of(1), Money.euros("49.50"))
            ));
            assertThat(commande.total()).isEqualTo(Money.euros("449.48"));
        }

        @Test
        @DisplayName("une commande a au moins une ligne")
        void auMoinsUneLigne() {
            assertThatThrownBy(() -> Commande.creer(List.of()))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("deux commandes au contenu identique restent distinctes (identité)")
        void identite() {
            List<LigneCommande> memesLignes = List.of(
                    new LigneCommande(Sku.of("CAN-0427"), Quantity.of(1), Money.euros("199.99")));
            Commande a = Commande.creer(memesLignes);
            Commande b = Commande.creer(memesLignes);
            assertThat(a).isNotEqualTo(b);   // identités différentes
            assertThat(a).isEqualTo(a);
        }

        @Test
        @DisplayName("la liste de lignes exposée est non modifiable")
        void listeNonModifiable() {
            Commande commande = Commande.creer(List.of(
                    new LigneCommande(Sku.of("CAN-0427"), Quantity.of(1), Money.euros("199.99"))));
            assertThatThrownBy(() -> commande.lignes().add(
                    new LigneCommande(Sku.of("TAB-0010"), Quantity.of(1), Money.euros("49.50"))))
                    .isInstanceOf(UnsupportedOperationException.class);
        }
    }
}
