package beziersurface;

import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.util.FPSAnimator;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;


public class Main {


    private static GLWindow window;
    public static int screenWidth = 700;
    public static int screenHeight = 500;
    public static Ponto[][] pontos;
    public static Scanner in = new Scanner(System.in);
    public static int tamanho;
    public static boolean leuArquivo = false;
    public static String nomeDoArquvio;
    public static double[][] transformacoes = new double [45][5];
    public static int qtdTransformacoes = 0;

    public static void LerArquivo(String nome) throws IOException {

        File arq = new File(nome + ".obj");
        FileReader fileR = null;
        BufferedReader bufferR = null;
        String readLine = "";
        String coordenadas[] = null;
        String grau[] = null;
        Ponto p;
        double x,y,z;
        int linha = 0, coluna =0;
        
        if (arq.exists()) {
            
            fileR = new FileReader(arq);
            bufferR = new BufferedReader(fileR);
            
            while (readLine != null) {
                
                readLine = bufferR.readLine();
                if (readLine == null) {
                    break;
                }
                if(readLine.charAt(0) == 'g'){ // GRAU DA SURPERFICIE
                                
                    grau = readLine.split("\t");
                    int valor = Integer.parseInt(grau[1]);
                    pontos = new Ponto[valor+1][valor+1];
                }    
                if (readLine.charAt(0) == 'v') { // PEGAR OS VERTICES DO ARQUIVO (PONTOS DA CURVA)

                    coordenadas = readLine.split("\t");
                    
                    x = Double.parseDouble(coordenadas[1]);
                    y = Double.parseDouble(coordenadas[2]);
                    z = Double.parseDouble(coordenadas[3]);
                  
                    if(linha != pontos.length){
                        pontos[linha][coluna] = new Ponto(x,y,z);
                    }
                    if(coluna < pontos.length ){
                                            
                        coluna ++;
                                            
                    }if(coluna == pontos.length){                        
                        linha = linha +1;
                        coluna = 0;
                    }                  
                
                }else if (readLine.charAt(0) == 't') { // PEGAR A TRANSFORMAÇÃO TRANSLAÇÃO 

                    coordenadas = readLine.split("\t");
                    transformacoes[qtdTransformacoes][0] =  1; // IDENTIFICA QUE É UMA TRANSFORMACAO DE TRANSLAÇÃO
                    transformacoes[qtdTransformacoes][1] = Double.parseDouble(coordenadas[1]); // TRANSLAÇÃO EM TORNO DO EIXO X
                    transformacoes[qtdTransformacoes][2] = Double.parseDouble(coordenadas[2]); // TRANSLAÇÃO EM TORNO DO EIXO Y
                    transformacoes[qtdTransformacoes][3] = Double.parseDouble(coordenadas[3]); // TRANSLAÇÃO EM TORNO DO EIXO Z
                    qtdTransformacoes++;
                
                }else if (readLine.charAt(0) == 's') { // PEGAR A TRANSFORMAÇÃO DE ESCALONAMENTO
  
                    coordenadas = readLine.split("\t");
                    transformacoes[qtdTransformacoes][0] =  2; // IDENTIFICA QUE É UMA TRANSFORMACAO DE ESCALONAMENTO
                    transformacoes[qtdTransformacoes][1] = Double.parseDouble(coordenadas[1]); // ESCALONAMENTO EM TORNO DO EIXO X
                    transformacoes[qtdTransformacoes][2] = Double.parseDouble(coordenadas[2]); // ESCALONAMENTO EM TORNO DO EIXO Y
                    transformacoes[qtdTransformacoes][3] = Double.parseDouble(coordenadas[3]); // TRANSLAÇÃO EM TORNO DO EIXO Z
                    qtdTransformacoes++;                
                
                } else if (readLine.charAt(0) == 'r') { // PEGAR A TRANSFORMAÇÃO DE ROTAÇÃO

                    coordenadas = readLine.split("\t");
                    transformacoes[qtdTransformacoes][0] =  3; // IDENTIFICA QUE É UMA TRANSFORMACAO DE ROTACAO
                    transformacoes[qtdTransformacoes][1] = Double.parseDouble(coordenadas[1]); // ROTAÇÃO NO EIXO X
                    transformacoes[qtdTransformacoes][2] = Double.parseDouble(coordenadas[2]); // ROTAÇÃO NO EIXO Y
                    transformacoes[qtdTransformacoes][3] = Double.parseDouble(coordenadas[3]); // ROTAÇÃO NO EIXO Z
                    transformacoes[qtdTransformacoes][4] = Double.parseDouble(coordenadas[4]); // PONTO 4
                    qtdTransformacoes++;
                }                
            }
            tamanho = pontos.length;
            bufferR.close();
            fileR.close();
        }
    }

    public static void init() {
        GLProfile.initSingleton();
        GLProfile profile = GLProfile.get(GLProfile.GL2);
        GLCapabilities caps = new GLCapabilities(profile);
        window = GLWindow.create(caps);
        window.setSize(screenWidth, screenHeight);
        window.setTitle("Bezier Surface");
        window.setResizable(true);
        window.addGLEventListener(new EventListener());
        window.addKeyListener(new KeyBoardInput());
        FPSAnimator animator = new FPSAnimator(window, 60); // FAZER O LOOP a 60 FPF
        animator.start();
        window.setVisible(true);
    }

    public static int getWindowWidth() {
        return window.getWidth();
    }

    public static int getWindowHeight() {
        return window.getHeight();
    }

    public static GLWindow getWindow() {
        return window;
    }

    public static void main(String[] args) throws IOException {
        int resposta;
        do {
            System.out.println("Se quiser Ler o Arquivo digite 1");
            resposta = in.nextInt();
            if (resposta != 1) {
                System.out.println("Numero errado, digite novamente.");
            }
        } while (resposta != 1);

        if (resposta == 1) {
            System.out.print("Nome do arquivo: ");
            nomeDoArquvio = in.next();           
            LerArquivo(nomeDoArquvio);
            leuArquivo = true;
            init();
        }        
    }
}
