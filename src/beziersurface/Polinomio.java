package beziersurface;

public class Polinomio {

    private double resultX;
    private double resultY;
    private double resultZ;
    
    
    public void pegarPonto(Ponto[][] p, double s,double t) {
        
        double somaInterna1 = 0;
        double somaExterna1 = 0;
        double somaInterna2 = 0;
        double somaExterna2 = 0;
        double somaInterna3 = 0;
        double somaExterna3 = 0;
        
        int n = p.length-1;        
        int m = p.length-1;         
                                                // (1-t)^(n-i) * (t)^i * x
        for(int i=0; i < p.length; i++){            
            
            for(int j=0; j < p.length; j++){
                
                double B1 = valorDeB(i,n);
                double B2 = valorDeB(j,m);
                                                  // PARTE  B(S) de i até n                          x    PARTE B(t) de j até m
                somaInterna1 = somaInterna1 + ( ( ( Math.pow((1-s), (n - i)) * (Math.pow(s, i) * B1) )  * ( Math.pow((1-t), (m - j)) * (Math.pow(t, j) * B2) ) ) * p[i][j].getX());
                
                System.out.println(somaInterna1);
                
                somaInterna2 = somaInterna2 + ( ( ( Math.pow((1-s), (n - i)) * (Math.pow(s, i) * B1) )  * ( Math.pow((1-t), (m - j)) * (Math.pow(t, j) * B2) ) ) * p[i][j].getY());
                
                somaInterna3 = somaInterna3 + ( ( ( Math.pow((1-s), (n - i)) * (Math.pow(s, i) * B1) )  * ( Math.pow((1-t), (m - j)) * (Math.pow(t, j) * B2) ) ) * p[i][j].getZ());
            }
            
            somaExterna1 = somaExterna1 + somaInterna1;
            somaExterna2 = somaExterna2 + somaInterna2;
            somaExterna3 = somaExterna3 + somaInterna3;
            
        }
        resultX = somaExterna1;
        resultY = somaExterna2;
        resultZ = somaExterna3;
    }

    
    
    private double fatorial (double numero){
        
        double resultado = 1;
        
        for(double j = numero; j >=1; j--){
            
            resultado = resultado * j;
        }
        return resultado;
    }
    
    private double valorDeB(double inicio,double fim){
        
        return (fatorial(fim) / ( fatorial(inicio) * fatorial(fim-inicio) ) );
    }
    
    public double getResultZ() {
        return resultZ;
    }

    public double getResultX() {
        return resultX;
    }

    public double getResultY() {
        return resultY;
    }    
    
}
