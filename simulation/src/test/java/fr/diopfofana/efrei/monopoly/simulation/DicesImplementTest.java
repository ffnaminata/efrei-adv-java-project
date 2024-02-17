package fr.diopfofana.efrei.monopoly.simulation;
import fr.arsenelapostolet.efrei.monopoly.Dices;
import fr.diopfofana.efrei.monopoly.simulation.DicesImplement;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
public class DicesImplementTest {
    @Test
    public void testThrowOneSixSidedDices() {
        DicesImplement dices = new DicesImplement();

        // On simule un dé constant pour s'assurer que le résultat est prévisible
        // En utilisant un mock, vous pourriez simuler un dé qui renvoie des valeurs spécifiques pour des tests plus précis
        // Mais ici, on suppose simplement que la méthode throwOneSixSidedDices() fonctionne correctement

        int result = dices.throwOneSixSidedDices();
        assertTrue(result >= 1 && result <= 6, "Le résultat d'un lancer de dé doit être compris entre 1 et 6");
    }

    @Test
    public void testThrowTwoSixSidedDices() {
        Dices dices = new DicesImplement();

        // On simule deux dés constants pour s'assurer que le résultat est prévisible
        // En utilisant un mock, vous pourriez simuler des dés qui renvoient des valeurs spécifiques pour des tests plus précis
        // Mais ici, on suppose simplement que la méthode throwOneSixSidedDices() fonctionne correctement

        int result = dices.throwTwoSixSidedDices();
        assertTrue(result >= 2 && result <= 12, "Le résultat de deux lancers de dés doit être compris entre 2 et 12");
    }

}
