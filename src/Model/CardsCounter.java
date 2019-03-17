/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author Amirhossein
 */
public class CardsCounter {
    private int infantrycounter = 0;
    private int cavalrycounter = 0;
    private int artillerycounter = 0;

    /**
     * @return the infantrycounter
     */
    public int getInfantrycounter() {
        return infantrycounter;
    }

    /**
     * @param infantrycounter the infantrycounter to set
     */
    public void increaseInfantrycounter() {
        this.infantrycounter ++;
    }

    /**
     * @return the cavalrycounter
     */
    public int getCavalrycounter() {
        return cavalrycounter;
    }

    /**
     * @param cavalrycounter the cavalrycounter to set
     */
    public void increaseCavalrycounter() {
        this.cavalrycounter ++;
    }

    /**
     * @return the artillerycounter
     */
    public int getArtillerycounter() {
        return artillerycounter;
    }

    /**
     * @param artillerycounter the artillerycounter to set
     */
    public void increaseArtillerycounter() {
        this.artillerycounter ++;
    }
 
}
