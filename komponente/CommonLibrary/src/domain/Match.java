package domain;


import exceptions.IllegalDateException;
import exceptions.IllegalGoalsException;
import exceptions.IllegalMatchStateException;
import java.io.Serializable;
import java.util.Date;

public class Match implements Serializable{

    private int id;
    private Date date;
    private Selection host;
    private Selection away;
    private int hostGoals;
    private int awayGoals;
    private MatchType matchType;
    private static final long serialVersionUID = 123212312L;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }


    public Selection getHost() {
        return host;
    }

    public void setHost(Selection host) {
        this.host = host;
    }

    public Selection getAway() {
        return away;
    }

    public void setAway(Selection away) {
        this.away = away;
    }

    public int getHostGoals() {
        return hostGoals;
    }

    public void setHostGoals(int hostGoals) {
        this.hostGoals = hostGoals;
    }

    public int getAwayGoals() {
        return awayGoals;
    }

    public void setAwayGoals(int awayGoals) {
        this.awayGoals = awayGoals;
    }

    public MatchType getMatchType() {
        return matchType;
    }

    public void setMatchType(MatchType matchType) {
        this.matchType = matchType;
    }

    public int getId() {
        return id;
    }
    
    
    
    

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(!(obj instanceof  Match)) return false;
        Match m = (Match) obj;
        return  this.getDate().equals(m.getDate()) && this.getHost().equals(m.getHost())
                && this.getAway().equals(m.getAway()) && this.getHostGoals()==m.hostGoals
                && this.getAwayGoals()==m.getAwayGoals();
    }

    @Override
    public int hashCode() {
        int result = Integer.hashCode(id);
        result = result * 31 + date.hashCode();
        result = result * 31 + host.hashCode();
        result = result * 31 + away.hashCode();
        result = result * 31 + Integer.hashCode(awayGoals);
        result = result * 31 + Integer.hashCode(hostGoals);
        return result;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static class Builder{
        int id;
        Date date;
        Selection host;
        Selection away;
        int hostGoals = Integer.MIN_VALUE;
        int awayGoals = Integer.MIN_VALUE;
        MatchType matchType;

        public Builder(Selection host,Selection away) {
            this.host = host;
            this.away = away;
        }

        public Builder date(Date date) throws IllegalDateException {
            if(date.after(new Date())) throw new IllegalDateException("Date must be in past");
            this.date = date; return this;
        }

        public Builder host(Selection host) {
            this.host = host; return this;
        }

        public Builder away(Selection away) {
            this.away = away; return this;
        }

        /*
         *@throw IllegalGoalsException if hostGoals are less than 0;
         */
        public Builder hostGoals(int hostGoals) throws IllegalGoalsException {
            if(hostGoals<0) throw new IllegalGoalsException("Number of goals must be 0 or positive number");
            this.hostGoals = hostGoals; return this;
        }


        /*
         *@throw IllegalGoalsException if awayGoals are less than 0;
         */
        public Builder awayGoals(int awayGoals) throws IllegalGoalsException {
            if(awayGoals<0) throw new IllegalGoalsException("Number of goals must be 0 or positive number");
            this.awayGoals = awayGoals; return this;
        }

        public Builder matchType (MatchType matchType) {
            this.matchType = matchType; return this;
        }


        public Match build() throws IllegalMatchStateException {
            return new Match(this);
        }
    }

    /*
     *@throw IllegalMatchStateException if awayGoals or hostGoals are less than 0;
     */
    private Match(Builder builder) throws  IllegalMatchStateException {
        if(builder.awayGoals==Integer.MIN_VALUE || builder.hostGoals==Integer.MIN_VALUE) throw new IllegalMatchStateException("Empty number of goals");
        if(builder.date==null) throw new IllegalMatchStateException("Empty date");
        if (builder.matchType==null) throw new IllegalMatchStateException("Empty match type");
        this.away = builder.away;
        this.awayGoals = builder.awayGoals;
        this.date = builder.date;
        this.host = builder.host;
        this.hostGoals = builder.hostGoals;
        //this.id = builder.id;
        this.matchType = builder.matchType;
    }

    public Selection getOpponent(Selection s) {
        if(s.equals(this.getAway())) return this.getHost();
        return this.getAway();
    }
    
}
