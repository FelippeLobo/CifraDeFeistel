/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package cifradefeistel;
import java.util.Arrays;

/**
 *
 * @author felip
 */
public class CifraDeFeistel {

    // Função de Feistel
    public static int[] feistelFunction(int[] direita, int[] chave) {
        // Exemplo simples de uma caixa S-BOX
        int[] sBox = {2, 3, 1, 0}; // Mapeamento de entrada para saída
        int[] resultado = new int[direita.length];
        for (int i = 0; i < direita.length; i++) {
            resultado[i] = sBox[direita[i] ^ chave[i]];
        }
        return resultado;
    }

    // Função para realizar uma rodada da cifra de Feistel
    public static int[] feistelRound(int[] esquerda, int[] direita, int[] chave) {
        int[] resultado = new int[direita.length];
        int[] funcaoFeistel = feistelFunction(direita, chave);
        for (int i = 0; i < direita.length; i++) {
            resultado[i] = esquerda[i] ^ funcaoFeistel[i];
        }
        return resultado;
    }

    // Função para executar a cifra de Feistel
    public static int[] cifraDeFeistel(int[] bloco, int[][] chaves) {
        int[] esquerda = Arrays.copyOfRange(bloco, 0, bloco.length / 2);
        int[] direita = Arrays.copyOfRange(bloco, bloco.length / 2, bloco.length);

        for (int i = 0; i < 16; i++) {
            int[] temp = direita.clone();
            direita = feistelRound(esquerda, direita, chaves[i]);
            esquerda = temp;
        }

        int[] resultado = new int[bloco.length];
        System.arraycopy(direita, 0, resultado, 0, direita.length);
        System.arraycopy(esquerda, 0, resultado, direita.length, esquerda.length);
        return resultado;
    }

    public static void main(String[] args) {
        // Exemplo de entrada (bloco de 8 bits)
        int[] bloco = {1, 0, 1, 0, 0, 1, 0, 1};

        // Exemplo de chaves (16 chaves de 8 bits cada)
        int[][] chaves = new int[16][8];
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 8; j++) {
                chaves[i][j] = (i + j) % 2; // Exemplo simples de geração de chaves
            }
        }

        // Execução da cifra de Feistel
        int[] textoCifrado = cifraDeFeistel(bloco, chaves);

        // Exibição do resultado
        System.out.println("Texto cifrado: " + Arrays.toString(textoCifrado));
    }
}
