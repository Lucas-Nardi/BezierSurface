package beziersurface;

import static beziersurface.Main.qtdTransformacoes;
import static beziersurface.Main.transformacoes;
import static beziersurface.EventListener.pontosOriginais;
import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;
import com.jogamp.newt.opengl.GLWindow;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class KeyBoardInput implements KeyListener {

    public static boolean mexerPonto = false;         // MEXE OS PONTOS DE CONTROLE DA CURVA
    private GLWindow window = Main.getWindow();
    public static boolean aparecerPolinomios = false;
    public static int sumirFrase = 0;               // FAZ APARECER OU SUMIR POLINOMIO 
    public static int percorrerTrans = 0;              // PERCORRE O VETOR DE TRANSFORMAÇÃO
    public static boolean transformei = false;
    public static String poly = "Depois da transformação";

    public void EscreverArquivo() throws IOException {

        File arq = new File(Main.nomeDoArquvio + ".obj");
        FileWriter fileW = null;
        BufferedWriter bufferW = null;
        int i = 0;
        String x, y, z, vertice, transformacao = null, etapa, parametro1 = null, parametro2 = null, parametro3 = null, parametro4;

        if (!arq.exists()) { // ARQUIVO NAO EXISTE POR ISSO CRIO ELE
            arq.createNewFile();
        }
        fileW = new FileWriter(arq);
        bufferW = new BufferedWriter(fileW);

        bufferW.write("#--------------------------------------------------------------------------");
        bufferW.newLine();
        bufferW.write("# " + Main.nomeDoArquvio + ".obj");
        bufferW.newLine();
        bufferW.write("# Pontos de controle para uma superficie de grau " + (Main.tamanho - 1));
        bufferW.newLine();
        bufferW.write("g\t" + (Main.tamanho - 1));
        bufferW.newLine();
        bufferW.write("#--------------------------------------------------------------------------");
        bufferW.newLine();
        bufferW.write("# Lista de vértices:");
        bufferW.newLine();
        bufferW.write("#---------------------------------------------------------------------------");
        bufferW.newLine();
        bufferW.flush();

        for (i = 0; i < pontosOriginais.length; i++) {

            for (int j = 0; j < pontosOriginais[0].length; j++) {

                x = Double.toString(pontosOriginais[i][j].getX());
                y = Double.toString(pontosOriginais[i][j].getY());
                z = Double.toString(pontosOriginais[i][j].getZ());
                vertice = "v\t" + x + "\t" + y + "\t" + z;
                bufferW.write(vertice);
                bufferW.newLine();
                bufferW.flush();
            }
        }
        bufferW.write("#---------------------------------------------------------------------------");
        bufferW.newLine();
        bufferW.write("# Transformações em 2D:");
        bufferW.newLine();
        bufferW.write("#---------------------------------------------------------------------------");
        bufferW.newLine();
        bufferW.flush();

        for (i = 0; i < qtdTransformacoes; i++) {

            parametro1 = Double.toString(transformacoes[i][1]); // PEGA O VALORE DE X NAS TRANSFORMAÇÕES
            parametro2 = Double.toString(transformacoes[i][2]); // PEGA O VALORE DE Y NAS TRANSFORMAÇÕES
            if (transformacoes[i][0] == 1 || transformacoes[i][0] == 2 ) {
                parametro3 = Double.toString(transformacoes[i][3]); // PEGA O VALORE DE Z NAS TRANSFORMAÇÕES
            }
            if (transformacoes[i][0] == 1) { // TRANSLAÇÃO

                transformacao = "t\t" + parametro1 + "\t" + parametro2 + "\t" + parametro3 + "\t#Translação";

            } else if (transformacoes[i][0] == 2) { // ESCALONAMENTO

                transformacao = "s\t" + parametro1 + "\t" + parametro2 + "\t" + parametro3 + "\t#Escalonamento";

            } else if (transformacoes[i][0] == 3) { // ROTAÇÃO 

                if (transformacoes[i][1] != 0) { // NO EIXO X

                    transformacao = "r\t" + "x" + "\t" + parametro2 + "\t#Rotação de " + parametro2 + " graus";

                } else if (transformacoes[i][2] != 0) { // NO IXO Y

                    transformacao = "r\t" + "y" + "\t" + parametro2 + "\t#Rotação de " + parametro2 + " graus";

                } else {                                               // NO EIXO Z
                    transformacao = "r\t" + "z" + "\t" + parametro2 + "\t#Rotação de " + parametro2 + " graus";
                }
            }
            bufferW.write(transformacao);
            bufferW.newLine();
            bufferW.flush();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_P) {  // MOSTRAR OS PONTOS DE CONTROLE COM UMA LINHA LIGANDO ELES

            if (sumirFrase == 0) {
                aparecerPolinomios = true;
                sumirFrase = 1;
            } else {
                aparecerPolinomios = false;
                sumirFrase = 0;
            }

        }
        if (e.getKeyCode() == KeyEvent.VK_CONTROL) { // PODER MOVER OS PONTOS

            mexerPonto = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_Q || e.getKeyCode() == KeyEvent.VK_ESCAPE) { // FECHAR O PROGRAMA

            try {
                EscreverArquivo();
            } catch (IOException ex) {

            }
            window.getAnimator().stop();
            window.destroy();
        }
        if (e.getKeyCode() == KeyEvent.VK_F) { // DEIXAR FULL SCREEN
            window.setFullscreen(true);
        }
        if (e.getKeyCode() == KeyEvent.VK_M) { // TIRAR O FULL SCREEN
            window.setFullscreen(false);
        }
        if (e.getKeyCode() == KeyEvent.VK_SPACE) { // COMEÇA AS TRANSFORMAÇÕES 

            if (percorrerTrans < qtdTransformacoes) {

                transformei = true;

                if (transformacoes[percorrerTrans][0] == 1) { // TRANSLAÇÃO TEXTO

                    poly = "Transformcação de Translação no eixo x " + transformacoes[percorrerTrans][1] + " , no eixo y " + transformacoes[percorrerTrans][2] + " e no eixo z " + transformacoes[percorrerTrans][3];

                } else if (transformacoes[percorrerTrans][0] == 2) { // ESCALONAMENTO TEXTO

                    poly = "Transformcação de Scalonamento no eixo x " + transformacoes[percorrerTrans][1] + " , no eixo y " + transformacoes[percorrerTrans][2] + " e no eixo z " + transformacoes[percorrerTrans][3];

                } else { // ROTAÇÃO TEXTO

                    if (transformacoes[percorrerTrans][1] != 0) {

                        poly = "Transformcação de Rotação de " + transformacoes[percorrerTrans][1] + " graus no eixo x ";

                    } else if (transformacoes[percorrerTrans][2] != 0) {

                        poly = "Transformcação de Rotação de " + transformacoes[percorrerTrans][2] + " graus no eixo y ";

                    } else {

                        poly = "Transformcação de Rotação de " + transformacoes[percorrerTrans][3] + " graus no eixo z ";
                    }
                }
                percorrerTrans++;

            } else { // JÁ FIZ TODAS AS TRANSFORMAÇÕES LOGO, PRECISO VOLTAR PARA A CURVA PARA A POSIÇÃO ORIGINAL
                transformei = false;
                percorrerTrans = 0;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

}
