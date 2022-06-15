package filter;

public class BrightFilter extends FilterData{

    private String degreeOfBrightness = "밝음";

    public BrightFilter(String filterName, float bright, float contrast, float cloudy, float chroma, String madeBy, String degreeOfBrightness) {
        super(filterName, bright, contrast, cloudy, chroma, madeBy);
        this.degreeOfBrightness = degreeOfBrightness;
    }
}
