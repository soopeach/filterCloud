package filter;

public class DarkFilter extends FilterData{

    private String degreeOfBrightness = "어두움";

    public DarkFilter(String filterName, float bright, float contrast, float cloudy, float chroma, String madeBy, String degreeOfBrightness) {
        super(filterName, bright, contrast, cloudy, chroma, madeBy);
        this.degreeOfBrightness = degreeOfBrightness;
    }
}
