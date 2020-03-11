package entities;

public class RaterBuilder {
    private Rater rater;
    public RaterBuilder(){rater = new Rater();}

    public RaterBuilder withValue(double value){
        rater.setValue(value);
        return this;
    }
    public RaterBuilder withMovie_id(Integer movie_id){
        rater.setMovie_id(movie_id);
        return this;
    }
    public Rater build(){return this.rater;}
}
