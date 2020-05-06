package domain;

import exceptions.IllegalPointsRanking;
import exceptions.IllegalRankingException;
import exceptions.IllegalSelectionException;
import java.io.Serializable;
import java.util.List;



public class Selection implements Serializable{

    private int id;
    private Confederation confederation;
    private String name;
    private int rang;
    private int points;
    private List<Match> matches;
    private static final long serialVersionUID = 12029997L;

    
    public static Selection createNewSelection(String name,Confederation confederation) throws IllegalSelectionException {
        if(name.isEmpty() || confederation==null) throw new IllegalSelectionException("Popunite obavezne atribute.");
        return new Selection(name, confederation);
    }
    
    public static Selection createExistingSelection( String name, Confederation confederation,int rang, int points) throws IllegalSelectionException, IllegalRankingException, IllegalPointsRanking {
        if(name.isEmpty() || confederation==null) throw new IllegalSelectionException("Popunite obavezne atribute.");
        if(rang<0) throw new IllegalRankingException("Rang mora biti veci od 0,");
        if(points<0) throw new IllegalPointsRanking("Poeni moraju biti veci od 0.");
        return new Selection(confederation, name, rang, points);
    }

    private Selection(String name,Confederation confederation) {
        this.name = name;
        this.confederation = confederation;
        this.rang = 0;
        this.points = 0;
    }

    private Selection(Confederation confederation, String name, int rang, int points) {
        this.confederation = confederation;
        this.name = name;
        this.rang = rang;
        this.points = points;
    }
    
    public Selection(String name) {
        this.name = name;
    }
 
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Confederation getConfederation() {
        return confederation;
    }

    public void setConfederation(Confederation confederation) {
        this.confederation = confederation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRang() {
        return rang;
    }

    public void setRang(int rang) {
        this.rang = rang;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(!(o instanceof Selection)) return false;
        Selection s = (Selection) o;
        return this.getName().equals(s.getName())
                && this.getConfederation().equals(s.getConfederation());
    }

    @Override
    public int hashCode() {
        int result = Integer.hashCode(id);
        result = 31 * result + name.hashCode();
        result = 31 * result + confederation.hashCode();
        result = 31 * result + Integer.hashCode(rang);
        result = 31 * result + Integer.hashCode(points);
        return result;
    }

    @Override
    public String toString() {
        /*return "Selection{" +
                "id=" + id +
                ", confederation=" + confederation +
                ", name='" + name + '\'' +
                ", rang=" + rang +
                ", points=" + points +
                '}';*/
        return name;
    }

    public List<Match> getMatches() {
        return matches;
    }

    public void setMatches(List<Match> matches) {
        this.matches = matches;
    }

   
}
