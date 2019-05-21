import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class PED2 {
    
    private static class Game {
        public int quantidade;
        public int mediocre;
        public Double somaScore;
        public List<Double> score;
        public String melhorGame;
        public String piorGame;
        public Double maiorScore;
        public Double piorScore;
        
        public Game(){
            this.quantidade = 0;
            this.mediocre = 0;
            this.somaScore = 0.0;
            this.score = new ArrayList<Double>();
            this.melhorGame = "";
            this.piorGame = "";
            this.maiorScore = null;
            this.piorScore = null;
        }
        
        public Game add(String linha[]){
            this.quantidade++;
            
            if(linha[2].equals("Mediocre"))
                this.mediocre++;
                
            this.somaScore += Double.parseDouble(linha[3]);
            
            this.score.add(Double.parseDouble(linha[3]));
            
            if(this.maiorScore == null || this.maiorScore < Double.parseDouble(linha[3])){
                this.maiorScore = Double.parseDouble(linha[3]);
                this.melhorGame = linha[0];
            }
            
            if(this.piorScore == null || this.piorScore > Double.parseDouble(linha[3])){
                this.piorScore = Double.parseDouble(linha[3]);
                this.piorGame = linha[0];
            }
            
            
            return this;
        }
        
        public String toString(){
            DecimalFormat df = new DecimalFormat("0.##");
            
            String retorno = "";

            retorno += " [ Qtd: " + this.quantidade;
            retorno += " | % mediocre: " + df.format(( this.mediocre * 100.0 ) / this.quantidade) + "%";
            retorno += " | Media : " + df.format(this.somaScore / this.quantidade);
            retorno += " | Desvio Padrão: " + df.format(this.getDesvioPadrao(score, (this.somaScore / this.quantidade)));
            retorno += " | Melhor jogo: " + this.melhorGame;
            retorno += " | Pior jogo: " + this.piorGame;        
            retorno += " ] ";
            
            return retorno;
        }
        
        public Double getDesvioPadrao(List<Double> valor, Double media) {
            int tam = valor.size();
            Double desvPadrao = 0D;
            for (Double vlr : valor) {
                Double aux = vlr - media;
                desvPadrao += aux * aux;
            }
            
            return Math.sqrt(desvPadrao / (tam - 1));
        }
    }
    
    public static void main(String[] args) {
        SimpleReader gameReview = new SimpleReader("C:\\Users\\patricia.schneider1\\Downloads\\game-reviews.csv");
        String line = gameReview.readLine();
        line = gameReview.readLine();
        
        Map<Integer, Game> map = new TreeMap<Integer,Game>();
        
        while (line != null) {
            String[] review = line.split(";");
            
            if(map.get(Integer.parseInt(review[6])) == null){
                map.put(Integer.parseInt(review[6]), new Game());
            } else {
                map.put(Integer.parseInt(review[6]), map.get(Integer.parseInt(review[6])).add(review));
            }

            line = gameReview.readLine();
        }
        gameReview.close();
        
        for (Map.Entry<Integer, Game> game : map.entrySet()) {
            System.out.println("{ " + game.toString() + "}");
        }
    }
}
