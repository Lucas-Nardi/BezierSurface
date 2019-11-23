package beziersurface;

import static beziersurface.KeyBoardInput.transformei;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.gl2.GLUT;

public class EventListener implements GLEventListener {

    public static Desenho surperficieDepois;             // É A CURVA ORIGINAL QUE SOFRERA TRANSFORMAÇÕES SE EXISTIREM
    public static Desenho surperficieAntes;             // CURVA AS DA TRANSFORMAÇÃO
    public static int qtdPonto;                   // QUANTOS PONTO EU TENHO NA TELA
    public static int queDesenho = 0;                    // PEGA UMA CURVA DO VETOR DE CURVAS
    boolean tenhoDesenho = false;
    int percorrerPontos = 0;
    boolean criei2Telas = false;
    int i = 0;
    public static GLU glu = new GLU();
    private double rotatex = 1;
    public static GL2 gl;
    GLUT glut = new GLUT();
    public static Ponto[][] pontosOriginais = Main.pontos;

    @Override
    public void display(GLAutoDrawable drawable) {

        if (Main.leuArquivo) { // JA TENHO O MEU VETOR DE PONTO PREENCHIDOS POIS PREECHI LENDO O ARQUIVO

            if (tenhoDesenho == false) {
                surperficieDepois = new Desenho(pontosOriginais);
                surperficieDepois.setGl(gl);
                tenhoDesenho = true;
                surperficieAntes = new Desenho(pontosOriginais);
                surperficieAntes.setGl(gl);
                surperficieAntes.setRed(0);
                surperficieAntes.setGreen(255);
                surperficieAntes.setBlue(0);
            }

        }
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();
        gl.glTranslated(-1.1, 0, -2.1);
        //Rotate The Cube On X, Y & Z
        gl.glRotated(1.0,-70, 60, 1.0);

        surperficieDepois.Draw(30.0);

        if (KeyBoardInput.aparecerPolinomios && transformei == false) {  // APARECER O POLINOMIO 

            // CRIA UMA LINHA ENTRE O PONTO DE CONTROLE
            for (int i = 0; i < pontosOriginais.length; i++) {

                for (int j = 0; j < pontosOriginais[0].length - 1; j++) {

                    gl.glColor3d(1, 0, 0);// COR DOS PONTOS DE CONTROLE
                    gl.glBegin(GL2.GL_LINES);
                    gl.glVertex3d(pontosOriginais[i][j].getX(), pontosOriginais[i][j].getY(), pontosOriginais[i][j].getZ());
                    gl.glVertex3d(pontosOriginais[i][j+1].getX(), pontosOriginais[i][j+1].getY(), pontosOriginais[i][j+1].getZ());
                    gl.glEnd();
                }
            }
            for (int j = 0; j < pontosOriginais[0].length; j++) {

                for (int i = 0; i < pontosOriginais[0].length - 1; i++) {

                    gl.glColor3d(1, 0, 0);// COR DOS PONTOS DE CONTROLE
                    gl.glBegin(GL2.GL_LINES);
                    gl.glVertex3d(pontosOriginais[i][j].getX(), pontosOriginais[i][j].getY(), pontosOriginais[i][j].getZ());
                    gl.glVertex3d(pontosOriginais[i+1][j].getX(), pontosOriginais[i+1][j].getY(), pontosOriginais[i+1][j].getZ());
                    gl.glEnd();
                }
            }
        }
        rotatex -= 1;

    }

    @Override
    public void dispose(GLAutoDrawable drawable) {
        // TODO Auto-generated method stub
    }

    @Override
    public void init(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        if (Main.leuArquivo) {
            qtdPonto = pontosOriginais.length;
        }
        gl = drawable.getGL().getGL2();
        gl.glShadeModel(GL2.GL_SMOOTH);
        gl.glClearColor(0, 0, 0, 0); //Background Color
        gl.glClearDepth(1.0);
        gl.glEnable(GL2.GL_DEPTH_TEST);
        gl.glDepthFunc(GL2.GL_LEQUAL);
        gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL2.GL_NICEST);
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {

        // TODO Auto-generated method stub
        gl = drawable.getGL().getGL2();
        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        //gl.glOrtho(-250, 250, -350, 350,-1,50);
        glu.gluPerspective(85, 1.0, 1.0, 60.0); //glu.gluPerspective(fovy, aspect, zNear, zFar);
                                                // Quanto mais fovy mais longe fica o objeto
//       glu.gluLookAt(3.00, 3.50, -5.00, // EYE
//                     0.00, 0.00, 0.00, // CENTER
//                     0.00, 0.00, 0.00); // UP

        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

    public void pontosDeControle(GL2 gl) {

        // EXIBIR OS PONTOS DE CONTROLE
        for (int i = 0; i < pontosOriginais.length; i++) {

            for (int j = 0; j < pontosOriginais[0].length - 1; j++) {

                gl.glColor3d(1, 0, 0);// COR DOS PONTOS DE CONTROLE
                gl.glBegin(GL2.GL_LINES);
                gl.glVertex3d(pontosOriginais[i][j].getX(), pontosOriginais[i][j].getY(), pontosOriginais[i][j].getZ());
                gl.glVertex3d(pontosOriginais[i][j+1].getX(), pontosOriginais[i][j+1].getY(), pontosOriginais[i][j+1].getZ());
                gl.glEnd();
            }
        }
        for (int j = 0; j < pontosOriginais[0].length; j++) {

            for (int i = 0; i < pontosOriginais[0].length - 1; i++) {

                gl.glColor3d(1, 0, 0);// COR DOS PONTOS DE CONTROLE
                gl.glBegin(GL2.GL_LINES);
                gl.glVertex3d(pontosOriginais[i][j].getX(), pontosOriginais[i][j].getY(), pontosOriginais[i][j].getZ());
                gl.glVertex3d(pontosOriginais[i+1][j].getX(), pontosOriginais[i+1][j].getY(), pontosOriginais[i+1][j].getZ());
                gl.glEnd();
            }
        }
    }
}
